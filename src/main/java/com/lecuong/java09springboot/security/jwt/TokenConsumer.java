package com.lecuong.java09springboot.security.jwt;

import com.lecuong.java09springboot.security.jwt.model.JwtPayLoad;
import com.lecuong.java09springboot.security.jwt.util.JwtClaimKey;
import org.jose4j.jwt.consumer.InvalidJwtException;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TokenConsumer {

    //Nhận về một token và chuyển token đó sang dạng map

    private String audience;
    private JwtParser parser;

    public TokenConsumer(String audience, PublicKey publicKey) {
        this.audience = audience;
        parser = new JwtParser(publicKey);
    }

    public JwtPayLoad consume(String token) throws InvalidJwtException {
        Map<String, Object> claims = parser.parseToken(token, audience);

        JwtPayLoad jwtPayload = new JwtPayLoad();

        jwtPayload.setId((Long) claims.get(JwtClaimKey.ID.getValue()));
        jwtPayload.setUserName((String) claims.get(JwtClaimKey.USERNAME.getValue()));
        jwtPayload.setFullName((String) claims.get(JwtClaimKey.FULLNAME.getValue()));
        jwtPayload.setRole((String) claims.get(JwtClaimKey.ROLE.getValue()));
//        List<String> roles = (List<String>) claims.get(JwtClaimKey.ROLE.getValue());
//        jwtPayload.setRole(roles);

        return jwtPayload;
    }
}
