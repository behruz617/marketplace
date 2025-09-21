package az.gov.marketplace.auth.service;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtProperties jwtProp;


    private Key getSigngKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProp.getSecret()); // ✅ Base64 decode
        return Keys.hmacShaKeyFor(keyBytes);
    }

    //Access token
    public String generateAccessToken(String email) {

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtProp.getAccessExpiration()))
                .signWith(getSigngKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    //generate Refresh token
    public String generateRefreshToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtProp.getRefreshExpiration()))
                .signWith(getSigngKey(), SignatureAlgorithm.HS256)
                .compact();

    }

    //tokenden email cixarmaq
    public String extractEmail(String token) {
        try {
            String subjects= Jwts.parserBuilder()
                    .setSigningKey(getSigngKey())
                    .setAllowedClockSkewSeconds(60)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            System.out.println("Extracted email from token:"+subjects);
            return subjects;
        }
        catch (ExpiredJwtException e) {
            System.out.println("Token expired at: " + e.getClaims().getExpiration());
            return null;
        } catch (JwtException e) {
            System.out.println("Invalid token: " + e.getMessage());
            return null;
        }
    }

    //token yoxlamaq(etibarlidirmi)
    public boolean isTokenValid(String token, String email) {
        try {
            String extracted = extractEmail(token);
            return (extracted.equals(email) && !isTokenExpired(token));
        }catch (Exception e){
            return false;//hər cür JWT exception-da invalid olsun
        }

    }

    private boolean isTokenExpired(String token) {
        Date exp = Jwts.parserBuilder()
                .setSigningKey(getSigngKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return exp.before(new Date());
    }


}
