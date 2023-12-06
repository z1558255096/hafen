package com.moil.hafen.system.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moil.hafen.common.annotation.Limit;
import com.moil.hafen.common.authentication.JWTToken;
import com.moil.hafen.common.authentication.JWTUtil;
import com.moil.hafen.common.domain.ActiveUser;
import com.moil.hafen.common.domain.FebsConstant;
import com.moil.hafen.common.domain.FebsResponse;
import com.moil.hafen.common.domain.router.VueRouter;
import com.moil.hafen.common.enums.RoleType;
import com.moil.hafen.common.properties.FebsProperties;
import com.moil.hafen.common.service.RedisService;
import com.moil.hafen.common.utils.DateUtil;
import com.moil.hafen.common.utils.FebsUtil;
import com.moil.hafen.common.utils.IPUtil;
import com.moil.hafen.system.dao.LoginLogMapper;
import com.moil.hafen.system.domain.LoginLog;
import com.moil.hafen.system.domain.Menu;
import com.moil.hafen.system.domain.User;
import com.moil.hafen.system.manager.UserManager;
import com.moil.hafen.system.service.LoginLogService;
import com.moil.hafen.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @Author 陈子杰
 * @Description 管理后台/登录
 * @Version 1.0.0
 * @Date 2023/12/06 09:38
 */
@Validated
@RestController
@RequestMapping("/backend")
@Api("管理后台/登录")
public class LoginController {

    @Resource
    private RedisService redisService;
    @Resource
    private UserManager userManager;
    @Resource
    private UserService userService;
    @Resource
    private LoginLogService loginLogService;
    @Resource
    private LoginLogMapper loginLogMapper;
    @Resource
    private FebsProperties properties;
    @Resource
    private ObjectMapper mapper;

    @PostMapping("/login")
    @Limit(key = "login", period = 60, count = 20, name = "登录接口", prefix = "limit")
    @ApiOperation("用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "string", paramType = "param"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "string", paramType = "param")
    })
    public FebsResponse login(
            @NotBlank(message = "{required}") String username,
            @NotBlank(message = "{required}") String password, HttpServletRequest request) throws Exception {

        final String errorMessage = "用户名或密码错误";
        User user = this.userManager.getUser(username);

        if (user == null)
            return new FebsResponse().message(errorMessage);
        if (!StringUtils.equals(user.getPassword(), password))
            return new FebsResponse().message(errorMessage);
        if (User.STATUS_LOCK.equals(user.getStatus()))
            return new FebsResponse().message("账号已被锁定,请联系管理员！");

        // 保存登录记录
        LoginLog loginLog = new LoginLog();
        loginLog.setUsername(username);
        this.loginLogService.saveLoginLog(loginLog);

        String token = FebsUtil.encryptToken(JWTUtil.sign(username,user.getId(), password, RoleType.后台用户.type));
        LocalDateTime expireTime = LocalDateTime.now().plusSeconds(properties.getShiro().getJwtTimeOut());
        String expireTimeStr = DateUtil.formatFullTime(expireTime);
        JWTToken jwtToken = new JWTToken(token, expireTimeStr);

        String userId = this.saveTokenToRedis(user, jwtToken, request);

        Map<String, Object> userInfo = this.generateUserInfo(jwtToken, user);
        return new FebsResponse().message("认证成功").data(userInfo);
    }

//    @GetMapping("index/{username}")
//    public FebsResponse index(@NotBlank(message = "{required}") @PathVariable String username) {
//        Map<String, Object> data = new HashMap<>();
//        // 获取系统访问记录
//        Long totalVisitCount = loginLogMapper.findTotalVisitCount();
//        data.put("totalVisitCount", totalVisitCount);
//        Long todayVisitCount = loginLogMapper.findTodayVisitCount();
//        data.put("todayVisitCount", todayVisitCount);
//        Long todayIp = loginLogMapper.findTodayIp();
//        data.put("todayIp", todayIp);
//        // 获取近期系统访问记录
//        List<Map<String, Object>> lastSevenVisitCount = loginLogMapper.findLastSevenDaysVisitCount(null);
//        data.put("lastSevenVisitCount", lastSevenVisitCount);
//        User param = new User();
//        param.setUsername(username);
//        List<Map<String, Object>> lastSevenUserVisitCount = loginLogMapper.findLastSevenDaysVisitCount(param);
//        data.put("lastSevenUserVisitCount", lastSevenUserVisitCount);
//        return new FebsResponse().data(data);
//    }

    @RequiresPermissions("user:online")
    @GetMapping("online")
    public FebsResponse userOnline(String username) throws Exception {
        String now = DateUtil.formatFullTime(LocalDateTime.now());
        Set<String> userOnlineStringSet = redisService.zrangeByScore(FebsConstant.ACTIVE_USERS_ZSET_PREFIX, now, "+inf");
        List<ActiveUser> activeUsers = new ArrayList<>();
        for (String userOnlineString : userOnlineStringSet) {
            ActiveUser activeUser = mapper.readValue(userOnlineString, ActiveUser.class);
            activeUser.setToken(null);
            if (StringUtils.isNotBlank(username)) {
                if (StringUtils.equalsIgnoreCase(username, activeUser.getUsername()))
                    activeUsers.add(activeUser);
            } else {
                activeUsers.add(activeUser);
            }
        }
        return new FebsResponse().data(activeUsers);
    }

    @DeleteMapping("kickout/{id}")
    @RequiresPermissions("user:kickout")
    public void kickout(@NotBlank(message = "{required}") @PathVariable String id) throws Exception {
        String now = DateUtil.formatFullTime(LocalDateTime.now());
        Set<String> userOnlineStringSet = redisService.zrangeByScore(FebsConstant.ACTIVE_USERS_ZSET_PREFIX, now, "+inf");
        ActiveUser kickoutUser = null;
        String kickoutUserString = "";
        for (String userOnlineString : userOnlineStringSet) {
            ActiveUser activeUser = mapper.readValue(userOnlineString, ActiveUser.class);
            if (StringUtils.equals(activeUser.getId(), id)) {
                kickoutUser = activeUser;
                kickoutUserString = userOnlineString;
            }
        }
        if (kickoutUser != null && StringUtils.isNotBlank(kickoutUserString)) {
            // 删除 zset中的记录
            redisService.zrem(FebsConstant.ACTIVE_USERS_ZSET_PREFIX, kickoutUserString);
            // 删除对应的 token缓存
            redisService.del(FebsConstant.TOKEN_CACHE_PREFIX + kickoutUser.getToken() + "." + kickoutUser.getIp());
        }
    }

    @GetMapping("logout")
    public void logout(@RequestHeader("Authentication")String Authentication) throws Exception {
        redisService.del(FebsConstant.TOKEN_CACHE_PREFIX + Authentication);
    }

    private String saveTokenToRedis(User user, JWTToken token, HttpServletRequest request) throws Exception {
        String ip = IPUtil.getIpAddr(request);

        // 构建在线用户
        ActiveUser activeUser = new ActiveUser();
        activeUser.setUsername(user.getUsername());
        activeUser.setIp(ip);
        activeUser.setToken(token.getToken());
//        activeUser.setLoginAddress(AddressUtil.getCityInfo(ip));

        // zset 存储登录用户，score 为过期时间戳
        this.redisService.zadd(FebsConstant.ACTIVE_USERS_ZSET_PREFIX, Double.valueOf(token.getExipreAt()), mapper.writeValueAsString(activeUser));
        // redis 中存储这个加密 token，key = 前缀 + 加密 token + .ip
        this.redisService.set(FebsConstant.TOKEN_CACHE_PREFIX + token.getToken(), token.getToken(), properties.getShiro().getJwtTimeOut() * 1000);

        return activeUser.getId();
    }

    /**
     * 生成前端需要的用户信息，包括：
     * 1. token
     * 2. Vue Router
     * 3. 用户角色
     * 4. 用户权限
     * 5. 前端系统个性化配置信息
     *
     * @param token token
     * @param user  用户信息
     * @return UserInfo
     */
    private Map<String, Object> generateUserInfo(JWTToken token, User user) {
        String username = user.getUsername();
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("token", token.getToken());
        userInfo.put("exipreTime", token.getExipreAt());

        Set<String> roles = this.userManager.getUserRoles(username);
        userInfo.put("roles", roles);

        List<VueRouter<Menu>> menu = this.userManager.getUserRouters(username);
        userInfo.put("menu", menu);

        Set<String> permissions = this.userManager.getUserPermissions(username);
        userInfo.put("permissions", permissions);

        user.setPassword("it's a secret");
        userInfo.put("user", user);
        return userInfo;
    }
}
