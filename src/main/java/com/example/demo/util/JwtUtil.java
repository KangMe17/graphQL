package com.example.demo.util;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.*;
import com.nimbusds.jwt.*;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    private final String secret = "123456";

    public String generateToken(String email, String role) {
        try {
            JWSSigner signer = new MACSigner(secret.getBytes());

            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(email)
                .claim("role", role)
                .expirationTime(new Date(System.currentTimeMillis() + 3600000)) // 1 hour
                .build();

            SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
            signedJWT.sign(signer);

            return signedJWT.serialize();
        } catch (Exception e) {
            throw new RuntimeException("Error generating JWT token", e);
        }
    }

    public boolean validateToken(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWSVerifier verifier = new MACVerifier(secret.getBytes());
            return signedJWT.verify(verifier) && !isTokenExpired(signedJWT);
        } catch (Exception e) {
            return false;
        }
    }

    public String extractEmail(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            return signedJWT.getJWTClaimsSet().getSubject();
        } catch (Exception e) {
            return null;
        }
    }

    private boolean isTokenExpired(SignedJWT signedJWT) throws java.text.ParseException {
        Date expiration = signedJWT.getJWTClaimsSet().getExpirationTime();
        return expiration.before(new Date());
    }
}
