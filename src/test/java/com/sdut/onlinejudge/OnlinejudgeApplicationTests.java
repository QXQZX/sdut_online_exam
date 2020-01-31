package com.sdut.onlinejudge;


import com.sdut.onlinejudge.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.junit.Test;

import javax.crypto.SecretKey;
import java.util.UUID;

//@SpringBootTest
public class OnlinejudgeApplicationTests {


    @Test
    public void test01() {
        String token = JwtUtils.createJWT(UUID.randomUUID().toString(), "11111", 10 * 60 * 1000);
        SecretKey Key = JwtUtils.generalKey();
        System.out.println(token);
        JwtParser jwtParser = Jwts.parser().setSigningKey(Key);


        Claims body = jwtParser.parseClaimsJws(token).getBody();
        String subject = body.getSubject();
        System.out.println(body);

    }


}
