package com.example.template.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.TestPropertySource;

import java.util.Collection;

@SpringBootTest
@TestPropertySource(properties = {"jws.tokenExpire = 0", "jws.refreshTokenExpire = 0"})
@TestPropertySource(properties = {"spring.jpa.properties.hibernate.javax.cache.uri = classpath://hibernateCache.xml"})
public class JwtServiceExpireTests {

    @Autowired
    JwtService jwtService;

    @Test
    public void testTokenExpire() {
        String token = jwtService.generateToken("admin");
        Assertions.assertFalse(token.isEmpty());
        Assertions.assertTrue(jwtService.isTokenExpired(token));
    }

    @Test
    public void testTokenValidationExpire(){
        String token = jwtService.generateRefreshToken("admin");
        Assertions.assertFalse(jwtService.validateToken(token, mockUserDetail()));
    }

    private UserDetails mockUserDetail() {
        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public String getPassword() {
                return null;
            }

            @Override
            public String getUsername() {
                return "admin";
            }
        };
    }

}
