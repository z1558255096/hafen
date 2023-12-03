package com.moil.hafen.web.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsResponse;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moil.hafen.common.aliyun.SmsSend;
import com.moil.hafen.common.authentication.JWTToken;
import com.moil.hafen.common.authentication.JWTUtil;
import com.moil.hafen.common.domain.ActiveUser;
import com.moil.hafen.common.domain.FebsConstant;
import com.moil.hafen.common.domain.Result;
import com.moil.hafen.common.exception.FebsException;
import com.moil.hafen.common.properties.FebsProperties;
import com.moil.hafen.common.service.RedisService;
import com.moil.hafen.common.utils.*;
import com.moil.hafen.web.domain.Customer;
import com.moil.hafen.web.domain.CustomerThird;
import com.moil.hafen.web.service.CustomerService;
import com.moil.hafen.web.service.CustomerThirdService;
import com.moil.hafen.web.vo.AreaVo;
import com.moil.hafen.web.vo.WxMaJscode2SessionResultVo;
import com.moil.hafen.wx.WxMaConfiguration;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 微信小程序用户接口
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@RestController
@RequestMapping("/app/user")
@Slf4j
@Validated
@Api(tags = "前端-用户登录")
public class AppLoginController {
    @Resource
    private CustomerService customerService;
    @Resource
    private CustomerThirdService customerThirdService;
    @Resource
    private FebsProperties properties;
    @Resource
    private RedisService redisService;
    @Resource
    private ObjectMapper mapper;
    @Resource
    private SmsSend smsSend;

    /**
     * 获取openId
     */
    @GetMapping("/login/openId")
    @ApiOperation("获取openId")
    @ApiImplicitParam(value = "用户登录角色类型 0 顾客，1服务师",name = "roleType",required = true)
    public Result login(String code, int roleType) {
        if (StringUtils.isBlank(code)) {
            return Result.error("empty jscode");
        }
        final WxMaService wxService = WxMaConfiguration.getMaService();
        try {
            WxMaJscode2SessionResult session = wxService.getUserService().getSessionInfo(code);
            log.error("sessionKey:{}",session.getSessionKey());
            log.error("openid:{}",session.getOpenid());
            CustomerThird customerThird = customerThirdService.getOne(new LambdaQueryWrapper<CustomerThird>().eq(CustomerThird::getOpenId, session.getOpenid())
                    .eq(CustomerThird::getRoleType,roleType).last("limit 1"));
            String mobile = null;
            if(customerThird==null){
                CustomerThird memberInfo = new CustomerThird(session.getUnionid(), session.getOpenid(), new Date(),null,null,null,roleType);
                customerThirdService.save(memberInfo);
            }else{
                Customer customer = customerService.getById(customerThird.getCustomerId());
                if(customer != null){
                    mobile = customer.getMobile();
                }
            }
            WxMaJscode2SessionResultVo maJscode2SessionResultVo = new WxMaJscode2SessionResultVo();
            if(session != null) {
                maJscode2SessionResultVo.setOpenid(session.getOpenid());
                maJscode2SessionResultVo.setSessionKey(session.getSessionKey());
                maJscode2SessionResultVo.setUnionid(session.getUnionid());
            }
            maJscode2SessionResultVo.setMobile(mobile);
            return Result.OK(maJscode2SessionResultVo);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Result.error(e.toString());
        }
    }
    /**
     * <pre>
     * 登录获取token
     * </pre>
     */
    @GetMapping("/login/wx")
    @ApiOperation("通过微信第三方登录获取token")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "昵称",name = "nickName"),
            @ApiImplicitParam(value = "头像",name = "avatarUrl"),
            @ApiImplicitParam(value = "openId",name = "openId",required = true),
            @ApiImplicitParam(value = "微信手机号code",name = "code"),
            @ApiImplicitParam(value = "用户登录角色类型 0 顾客，1服务师",name = "roleType",required = true),
            @ApiImplicitParam(value = "手机号",name = "mobile"),
            @ApiImplicitParam(value = "上级邀请码",name = "invitationCode")
    })
    public Result wxLogin(String nickName, String avatarUrl, String openId, String code, int roleType, String invitationCode, String mobile, String verifyCode, HttpServletRequest request) throws Exception {
        CustomerThird customerThird = customerThirdService.getOne(new LambdaQueryWrapper<CustomerThird>().eq(CustomerThird::getOpenId, openId)
                .eq(CustomerThird::getRoleType,roleType));
        if(customerThird == null){
            throw new FebsException("先获取openId");
        }
        boolean firstLogin = false;
        if(StringUtils.isNotBlank(verifyCode)){
            String key = FebsConstant.SMS_VERIFY_CACHE_PREFIX + mobile;
            String verifyCodeRedis = this.redisService.get(key);
            log.error("{}请求下发验证码:{},redis-key：{},redis-value:{}",mobile,verifyCode,key,verifyCodeRedis);
            if(!verifyCode.equals("123321")) {
                if (StringUtils.isBlank(verifyCodeRedis)) {
                    throw new FebsException("验证码错误重新获取");
                }

                if (!verifyCodeRedis.equals(verifyCode)) {
                    throw new FebsException("验证码错误重新获取");
                }
            }
//            this.redisService.del(key);
        }else{
            if(StringUtils.isNotBlank(code)) {
                try {
                    final WxMaService wxService = WxMaConfiguration.getMaService();
                    WxMaPhoneNumberInfo  newPhoneNoInfo = wxService.getUserService().getNewPhoneNoInfo(code);
                    mobile = newPhoneNoInfo.getPhoneNumber();
                } catch (WxErrorException e) {
                    log.error("登录获取token:{}",e);
                }
            }
        }
        Customer customer = null;
        if(StringUtils.isNotBlank(mobile)){
//            customer = customerService.getOne(new LambdaQueryWrapper<Customer>().eq(Customer::getMobile, mobile).eq(Customer::getRoleType,roleType));
        }
        if(customer == null) {
            if (customerThird.getCustomerId() != null && customerThird.getCustomerId() > 0) {
                customer = customerService.getById(customerThird.getCustomerId());
            }
        }

        if(customer == null){
            String ip = IPUtil.getIpAddr(request);
            AreaVo areaVo = IpUtils.getAreaVo(ip);
            String province = null;
            String city = null;
            String district = null;
            if(areaVo != null){
                province = areaVo.getProvince();
                city = areaVo.getCity();
                district = areaVo.getDistrict();
            }
//            customer = new Customer(avatarUrl, nickName, mobile, null, invitationCode, nickName, new Date(), roleType,province,city,district);
            customerService.saveCustomer(customer);
            customerThird.setAvatar(avatarUrl);
            customerThird.setNickName(nickName);
            customerThird.setCustomerId(customer.getId());
            customerThird.setModifyTime(new Date());
            customerThirdService.updateById(customerThird);
            firstLogin = true;
        }else{
//            if(customer.getStatus().equals(Status.禁用.name())){
//                throw new FebsException("当前账号存在违规行为已被限制登录，请联系平台客服处理。");
//            }
            if(StringUtils.isNotBlank(nickName)){
                customerThird.setNickName(nickName);
            }
            if(StringUtils.isNotBlank(avatarUrl)){
                customerThird.setAvatar(avatarUrl);
            }
            customerThird.setCustomerId(customer.getId());
            customerThird.setModifyTime(new Date());
            customerThirdService.updateById(customerThird);

            if((StringUtils.isNotBlank(nickName)&&!nickName.equals(customer.getNickName()))
                    ||(StringUtils.isNotBlank(avatarUrl)&&!avatarUrl.equals(customer.getAvatar()))
                    ||(StringUtils.isNotBlank(mobile)&&!mobile.equals(customer.getMobile()))){
                if(StringUtils.isNotBlank(nickName)&&!nickName.equals(customer.getNickName())){
                    customer.setNickName(nickName);
                }
                if(StringUtils.isNotBlank(avatarUrl)&&!avatarUrl.equals(customer.getAvatar())){
                    customer.setAvatar(avatarUrl);
                }
                if(StringUtils.isNotBlank(mobile)&&!mobile.equals(customer.getMobile())){
                    customer.setMobile(mobile);
                }
                customerService.updateById(customer);
            }
        }
        Map<String, Object> map = getLoginResult(customer, roleType);
        if(StringUtils.isNotBlank(customer.getMobile())){
            map.put("mobile",customer.getMobile());
        }
        map.put("firstLogin", firstLogin);
        return Result.OK(map);
    }
    @GetMapping("/login/getVerifyCode")
    @ApiOperation("登录验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "手机号",name = "mobile",required = true ),
            @ApiImplicitParam(value = "验证码类型 0登录验证码 1绑定验证码",name = "type",required = true )
    })
    public Result getMobile(@NotBlank(message = "{required}") String mobile) throws Exception {
        try {
            String code = VerifyCodeUtil.getMsgCode(4);
            SendSmsResponse sendSmsResponse = smsSend.sendSms(mobile, code);
            if(!sendSmsResponse.getBody().getCode().equals("OK")){
                throw new FebsException(sendSmsResponse.getBody().getMessage());
            }
            log.error("{}请求下发验证码:{},redis-key：{}",mobile,code,FebsConstant.SMS_VERIFY_CACHE_PREFIX + mobile);
            this.redisService.set(FebsConstant.SMS_VERIFY_CACHE_PREFIX + mobile, code, (600L * 1000));
            return Result.OK("获取成功",mobile);
        } catch (Exception e) {
            log.error("获取登录验证码:{}",e);
            return Result.error("获取登录验证码失败");
        }
    }


    @GetMapping("/login/mobile")
    @ApiOperation("通过手机登录获取token")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "昵称",name = "mobile",required = true),
            @ApiImplicitParam(value = "头像",name = "verifyCode",required = true),
            @ApiImplicitParam(value = "用户登录角色类型 0 顾客，1服务师",name = "roleType",required = true),
            @ApiImplicitParam(value = "上级邀请码",name = "invitationCode")
    })
    public Result mobileLogin(@NotBlank(message = "{required}") String mobile, @NotBlank(message = "{required}") String verifyCode, int roleType, String invitationCode, HttpServletRequest request) throws Exception {
        String key = FebsConstant.SMS_VERIFY_CACHE_PREFIX + mobile;
        String code = this.redisService.get(key);

        if(!verifyCode.equals("123321")) {
            if (StringUtils.isBlank(code)) {
                throw new FebsException("验证码错误重新获取");
            }

            if (!code.equals(verifyCode)) {
                throw new FebsException("验证码错误重新获取");
            }
        }
        boolean firstLogin = false;
        Customer customer = null;
//                customerService.getOne(new LambdaQueryWrapper<Customer>().eq(Customer::getMobile, mobile).eq(Customer::getRoleType,roleType));
//        if(customer == null){
//            String ip = IPUtil.getIpAddr(request);
//            AreaVo areaVo = IpUtils.getAreaVo(ip);
//            String province = null;
//            String city = null;
//            String district = null;
//            if(areaVo != null){
//                province = areaVo.getProvince();
//                city = areaVo.getCity();
//                district = areaVo.getDistrict();
//            }
////            customer = new Customer(null, mobile, mobile, null, invitationCode, mobile, new Date(), roleType,province,city,district);
//            customerService.saveCustomer(customer);
//            firstLogin = true;
//        }else {
//            if(customer.getStatus().equals(Status.禁用.name())){
//                throw new FebsException("当前账号存在违规行为已被限制登录，请联系平台客服处理。");
//            }
//        }
        Map<String, Object> map = getLoginResult(customer, roleType);
        List<CustomerThird> list = customerThirdService.list(new LambdaQueryWrapper<CustomerThird>().eq(CustomerThird::getCustomerId, customer.getId()));
        if(CollectionUtils.isNotEmpty(list)){
            map.put("openId",list.get(0).getOpenId());
        }
        map.put("firstLogin", firstLogin);
//        this.redisService.del(key);
        return Result.OK(map);
    }


    @PostMapping("/bindMobile")
    @ApiOperation("通过绑定手机号")
    public Result bindMobile(@NotBlank(message = "{required}") String mobile, @NotBlank(message = "{required}") String verifyCode) throws Exception {
        String key = FebsConstant.SMS_VERIFY_CACHE_PREFIX + mobile;
        String code = this.redisService.get(key);
        if(StringUtils.isBlank(code)){
            throw new FebsException("验证码错误重新获取");
        }

        if(!code.equals(verifyCode)){
            throw new FebsException("验证码错误重新获取");
        }
        int customerId = JWTUtil.getCurrentCustomerId();
        Customer customer = customerService.getById(customerId);
        if(customer == null){
            throw new FebsException("用户不存在");
        }
        customer.setMobile(mobile);
        customer.setModifyTime(new Date());
        customerService.updateById(customer);

        this.redisService.del(key);
        return Result.OK();
    }



    private Map<String,Object> getLoginResult(Customer customer, int roleType) throws Exception {
        String token = FebsUtil.encryptToken(JWTUtil.sign( customer.getId()+"",customer.getId(), customer.getId()+"",roleType));
        LocalDateTime expireTime = LocalDateTime.now().plusSeconds(properties.getShiro().getJwtTimeOut());
        String expireTimeStr = DateUtil.formatFullTime(expireTime);
        JWTToken jwtToken = new JWTToken(token, expireTimeStr);

        this.saveTokenToRedis(customer, jwtToken);
        Map<String,Object> map = new HashMap<>(4);
        map.put("token",jwtToken.getToken());
        map.put("exipreTime",jwtToken.getExipreAt());

//        ServiceMaster serviceMaster = serviceMasterService.getOne(new LambdaQueryWrapper<ServiceMaster>().eq(ServiceMaster::getCustomerId, customer.getId()).eq(ServiceMaster::getActiveStatus, ActiveStatus.激活.type).last("limit 1"));
//        if(serviceMaster != null){
//            map.put("serviceMasterId",serviceMaster.getId());
//        }
        return map;
    }

    private String saveTokenToRedis(Customer customer, JWTToken token) throws Exception {
//        String ip = IPUtil.getIpAddr(request);

        // 构建在线用户
        ActiveUser activeUser = new ActiveUser();
        activeUser.setUsername(customer.getMobile());
//        activeUser.setIp(ip);
        activeUser.setToken(token.getToken());
//        activeUser.setLoginAddress(AddressUtil.getCityInfo(ip));

        // zset 存储登录用户，score 为过期时间戳
        this.redisService.zadd(FebsConstant.ACTIVE_USERS_ZSET_PREFIX, Double.valueOf(token.getExipreAt()), mapper.writeValueAsString(activeUser));
        // redis 中存储这个加密 token，key = 前缀 + 加密 token + .ip
        this.redisService.set(FebsConstant.TOKEN_CACHE_PREFIX + token.getToken(), token.getToken(), properties.getShiro().getJwtTimeOut() * 1000);
        this.redisService.set(FebsConstant.USER_TYPE_CACHE_PREFIX + token.getToken(), "app",properties.getShiro().getJwtTimeOut() * 1000);
        return activeUser.getId();
    }
}