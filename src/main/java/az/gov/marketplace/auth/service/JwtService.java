package az.gov.marketplace.auth.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
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
        return Keys.hmacShaKeyFor(jwtProp.getSecret().getBytes());
    }

    //token yaratmaq
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email) //tokenin kim ucun oldugunu gosterir
                .setIssuedAt(new Date())  //yaradilma vaxti
                .setExpiration(new Date(System.currentTimeMillis() + jwtProp.getExpiration())) //bitme tarixi
                .signWith(getSigngKey(), SignatureAlgorithm.HS256)//tokeni imzalayiriq
                .compact();//hamsin birlesdirib String formatinda token qaytarir   //getSignKey bizim gizli acarimiz  hs256 imzalama alqoritmi
    }

    //tokenden email cixarmaq
    public String extractEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigngKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    //token yoxlamaq(etibarlidirmi)
    public boolean isTokenValid(String token, String email) {
        String extracted = extractEmail(token);
        return (extracted.equals(email) && !isTokenExpired(token));

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
