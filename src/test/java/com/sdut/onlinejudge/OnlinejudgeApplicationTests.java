package com.sdut.onlinejudge;


import com.alibaba.fastjson.JSON;
import com.sdut.onlinejudge.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.junit.Test;

import javax.crypto.SecretKey;
import java.text.ParseException;
import java.util.Date;
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

    @Test
    public void test02() throws ParseException {
        String s = "1577456277000";
        Date date = JSON.parseObject(s, Date.class);
        System.out.println(date);
    }

}
