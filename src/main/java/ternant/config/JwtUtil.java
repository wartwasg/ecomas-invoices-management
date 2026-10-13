package ternant.config;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

    public static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    @Value("${cookie.name}")
    private String cookieName;

    @Value("${app.expiration}")
    public Long expirationMs;

    @Value("${app.secret}")
    public String secretKey;

    public String getTokenFromCookie(HttpServletRequest request){
        Cookie cookie = WebUtils.getCookie(request,cookieName);

        return cookie == null ? null : cookie.getValue();
    }
    public ResponseCookie generateCookieFromJwtToken(UserDetails userDetails){
        String token = Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + expirationMs))
                .signWith(key())
                .compact();
        return ResponseCookie.from(cookieName,token)
                .httpOnly(false)
                .maxAge(expirationMs)
                .path("/api")
                .build();
    }
    public ResponseCookie invalidateCookie(){
        return ResponseCookie.from(cookieName,"")
                .httpOnly(false)
                .maxAge(expirationMs)
                .path("/api")
                .build();
    }
    public Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }
    public boolean validateToken(String token){
        try{
            Jwts.parser()
                    .verifyWith((SecretKey) key())
                    .build()
                    .parseSignedClaims(token);
            return true;
        }
        catch(JwtException e){
            logger.error("An error occured : {}",e.getMessage());
        } catch (Exception e) {
            logger.error("An error occurred :{} ",e.getMessage());
        }
        return false;
    }
    public String getUsername(String token){
        return Jwts.parser()
                .verifyWith((SecretKey) key())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}
