package it.unical.demacs.backend;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

@Service
public class JwtService {
    private final String secret;
    private final long exp;

    @Autowired
    public JwtService(@Value("${jwt.secret}") String secret, @Value("${jwt.expiration}") long exp){
        this.exp = exp;
        this.secret = secret;
    }

    public String generateToken(Long id, String role) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + exp);
        System.out.println(expiryDate);
        SecretKey key = new SecretKeySpec(secret.getBytes(), SignatureAlgorithm.HS512.getJcaName());

        return Jwts.builder()
                .setSubject(id.toString())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key)
                .claim("Role: ", role)
                .compact();
    }

    /*
    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

     */
}
