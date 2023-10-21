package group.serverhotelbooking.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtHelper {
    @Value(("${custom.token.key}"))
    private String securityKey;
    private long expiredTime = 8 * 60 * 60 * 1000;

    public String generateToken(String data) {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(securityKey));

        Date date = new Date();
        long newDate = date.getTime() + expiredTime;
        Date newExpiredDate = new Date(newDate);

        String token = Jwts.builder()
                .setSubject(data)
                .signWith(key)
                .setExpiration(newExpiredDate)
                .compact();

        return token;
    }

    public String parseToken(String token){
        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(securityKey));
        String data = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody().getSubject();
        return data;
    }
}
