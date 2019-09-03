package com.github.losemy.sso.client.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Description: sso
 * Created by lose on 2019/8/27 15:07
 */
public class JwtTokenUtil {
    public static final String UID = "uid";
    private static final String SECRET = "WgtqaT1HNTZPZNMDJu3k";
    /*
     * 60 * 60 * 24 * 1000 一天的有效期
     */
    private static final long EXPIRE = 86400000;

    /**
     * 生成token
     * 需要从token中解析出来 name 也就意味着需要
     * 只需要uid uname等信息
     * @param uid
     * @return token
     */
    public static String generate(Long uid) {
        Date nowDate = new Date();
        //过期时间
        Date expireDate = new Date(nowDate.getTime() + EXPIRE );
        Map<String, Object> claims = new HashMap<>();
        claims.put(UID, uid);
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
    public static Long getUid(String token) {
        return Long.parseLong((String)getClaim(token).get(UID));
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
        } catch (ExpiredJwtException expiredJwtException) {
            return true;
        }
    }
}
