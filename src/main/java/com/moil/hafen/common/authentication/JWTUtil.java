package com.moil.hafen.common.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.moil.hafen.common.properties.FebsProperties;
import com.moil.hafen.common.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;

import java.util.Date;

@Slf4j
public class JWTUtil {

    private static final long EXPIRE_TIME = SpringContextUtil.getBean(FebsProperties.class).getShiro().getJwtTimeOut() * 1000;

    /**
     * 校验 token是否正确
     *
     * @param token  密钥
     * @param secret 用户的密码
     * @return 是否正确
     */
    public static boolean verify(String token, String username,int userId,  String secret) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("username", username)
                    .withClaim("userid", userId)
                    .build();
            verifier.verify(token);
            log.info("token is valid");
            return true;
        } catch (Exception e) {
            log.info("token is invalid{}", e.getMessage());
            return false;
        }
    }

    /**
     * 从 token中获取用户名
     *
     * @return token中包含的用户名
     */
    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("username").asString();
        } catch (JWTDecodeException e) {
            log.error("error：{}", e.getMessage());
            return null;
        }
    }
    public static int getUserId(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("userid").asInt();
        } catch (JWTDecodeException e) {
            log.error("error：{}", e.getMessage());
            return 0;
        }
    }
    public static Integer getRoleType(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("roleType").asInt();
        } catch (JWTDecodeException e) {
            log.error("error：{}", e.getMessage());
            return null;
        }
    }
    /**
     * 生成 token
     *
     * @param username 用户名
     * @param secret   用户的密码
     * @return token
     */
    public static String sign(String username,int userId, String secret,int roleType) {
        try {
            username = StringUtils.lowerCase(username);
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withClaim("username", username)
                    .withClaim("userid", userId)
                    .withClaim("roleType", roleType)
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (Exception e) {
            log.error("error：{}", e);
            return null;
        }
    }

    public static int getCurrentCustomerId(){
        String token = (String) SecurityUtils.getSubject().getPrincipal();
        int userId = 0;
        if (StringUtils.isNotBlank(token)) {
            userId = JWTUtil.getUserId(token);
        }
        return userId;
    }
    public static String getCurrentUserName(){
        String token = (String) SecurityUtils.getSubject().getPrincipal();
        String userName = null;
        if (StringUtils.isNotBlank(token)) {
            userName = JWTUtil.getUsername(token);
        }
        return userName;
    }
    public static int getCurrentRoleType(){
        String token = (String) SecurityUtils.getSubject().getPrincipal();
        int roleType = 0;
        if (StringUtils.isNotBlank(token)) {
            roleType = JWTUtil.getRoleType(token);
        }
        return roleType;
    }
}
