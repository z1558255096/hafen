package com.moil.hafen.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moil.hafen.common.authentication.JWTToken;
import com.moil.hafen.common.authentication.JWTUtil;
import com.moil.hafen.common.domain.ActiveUser;
import com.moil.hafen.common.domain.FebsConstant;
import com.moil.hafen.common.enums.RoleType;
import com.moil.hafen.common.properties.FebsProperties;
import com.moil.hafen.common.service.RedisService;
import com.moil.hafen.common.utils.DateUtil;
import com.moil.hafen.common.utils.FebsUtil;
import com.moil.hafen.common.utils.MD5Util;
import com.moil.hafen.system.domain.User;
import com.moil.hafen.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.time.LocalDateTime;

/**
 * @Author 陈子杰
 * @Description 授权
 * @Version 1.0.0
 * @Date 2023/12/6 14:48
 */
@RestController
@RequestMapping
@Api("授权")
public class OAuthController {
    @Resource
    private RedisService redisService;
    @Resource
    private FebsProperties properties;
    @Resource
    private ObjectMapper mapper;
    @Resource
    private UserService userService;

    @RequestMapping("oauth")
    @ApiOperation("授权")
    public void oauth(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) throws Exception {
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        PrintWriter out = httpServletResponse.getWriter();

        String workNo = httpServletRequest.getParameter("workNo");
        String sign = httpServletRequest.getParameter("sign");
        String responseJson = "<script>" +
                "localStorage.removeItem('token');" +
                "location.href='/login.html'" +
                "</script>";
        if(StringUtils.isBlank(workNo)||StringUtils.isBlank(sign)){
            out.print(responseJson);
            return;
        }
        if(!MD5Util.encrypt(workNo,"honor_encrypt").equals(sign)){
            out.print(responseJson);
            return;
        }
        User user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getWorkNum,workNo).last("limit 1"));
        String token = FebsUtil.encryptToken(JWTUtil.sign(user.getUsername(),user.getId(), user.getPassword(), RoleType.后台用户.type));
        LocalDateTime expireTime = LocalDateTime.now().plusSeconds(properties.getShiro().getJwtTimeOut());
        String expireTimeStr = DateUtil.formatFullTime(expireTime);
        JWTToken jwtToken = new JWTToken(token, expireTimeStr);
        // 构建在线用户
        ActiveUser activeUser = new ActiveUser();
        activeUser.setUsername(user.getUsername());
        activeUser.setToken(jwtToken.getToken());
        // zset 存储登录用户，score 为过期时间戳
        this.redisService.zadd(FebsConstant.ACTIVE_USERS_ZSET_PREFIX, Double.valueOf(jwtToken.getExipreAt()), mapper.writeValueAsString(activeUser));
        // redis 中存储这个加密 token，key = 前缀 + 加密 token + .ip
        this.redisService.set(FebsConstant.TOKEN_CACHE_PREFIX + jwtToken.getToken(), jwtToken.getToken(), properties.getShiro().getJwtTimeOut() * 1000);
        responseJson = "<script>" +
                "localStorage.setItem('token', '"+jwtToken.getToken()+"');" +
                "location.href='honor/index.html'" +
                "</script>";
        out.print(responseJson);
    }
}
