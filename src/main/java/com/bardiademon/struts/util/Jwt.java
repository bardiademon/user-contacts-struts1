package com.bardiademon.struts.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

public class Jwt {

    private Jwt() {

    }

    public static String createToken(final long id) {
        try {
            final Algorithm algorithm = Algorithm.HMAC256("bardiademon");
            return JWT.create()
                    .withIssuer("bardiademon")
                    .withClaim("userId", id)
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            return null;
        }
    }

    public static Long getUserId(final String token) {
        DecodedJWT decodedJWT;
        try {
            Algorithm algorithm = Algorithm.HMAC256("bardiademon");
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("bardiademon")
                    .build();
            decodedJWT = verifier.verify(token);
            return decodedJWT.getClaim("userId").asLong();
        } catch (JWTVerificationException e) {
            // Invalid signature/claims
        }
        return null;
    }
}
