package com.github.losemy.sso.client.jwt;

import cn.hutool.core.bean.BeanUtil;
import com.github.losemy.sso.client.bean.User;
import com.github.losemy.sso.client.exception.SSOException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Description: sso
 * Created by lose on 2019/8/27 15:07
 * @author lose
 */
public class JwtTokenUtil {
    private static final String SECRET = "WgtqaT1HNTZPZNMDJu3k";
    /*
     * 60 * 60 * 24 * 1000 一天的有效期
     */
    private static final long EXPIRE = 86400000;

    /**
     * 生成token
     * @param user
     * @return token
     */
    public static String generate(User user) {
        Date nowDate = new Date();
        //过期时间
        Date expireDate = new Date(nowDate.getTime() + EXPIRE );
        Map<String, Object> claims = new HashMap<>();
        claims.put("user", user);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    /**
     * 解析Claims
     *
     * @param token
     * @return
     */
    public static Claims getClaim(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            throw new SSOException("jwt解析token异常");
        }
        return claims;
    }

    /**
     * 获取jwt发布时间
     */
    public static Date getIssuedAt(String token) {
        return getClaim(token).getIssuedAt();
    }

    /**
     * 获取UID
     */
    public static User getUser(String token) {
        // 在jwt中 会自动将对象转换成map
        // 也就是在放入对象的情况下 需要通过map转一下对象
        Map<String,Object> mapUser = (Map<String, Object>) getClaim(token).get("user");
        return BeanUtil.mapToBean(mapUser,User.class,true);
    }

    /**
     * 获取jwt失效时间
     */
    public static Date getExpiration(String token) {
        return getClaim(token).getExpiration();
    }

    /**
     * 验证token是否失效
     *
     * @param token
     * @return true:过期   false:没过期
     */
    public static boolean isExpired(String token) {
        try {
            final Date expiration = getExpiration(token);
            return expiration.before(new Date());
        } catch (Exception exception) {
            return true;
        }
    }
}
