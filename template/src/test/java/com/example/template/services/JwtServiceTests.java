package com.example.template.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.TestPropertySource;

import java.util.Collection;

@SpringBootTest
@TestPropertySource(properties = {"jws.tokenExpire = 60000", "jws.refreshTokenExpire = 60000"})
public class JwtServiceTests {

    @Autowired
    JwtService jwtService;

    @Nested
    @SpringBootTest
    @TestPropertySource(properties = {"jws.tokenExpire = 60000", "jws.refreshTokenExpire = 60000"})
    public class JwtServiceTestsTokenExpiration {

    }

    @Test
    public void testTokenActive() {
        String token = jwtService.generateToken("admin");
        Assertions.assertFalse(token.isEmpty());
        Assertions.assertFalse(jwtService.isTokenExpired(token));
    }

    @Test
    public void testRefreshTokenExpire() {
        String token = jwtService.generateRefreshToken("admin");
        Assertions.assertTrue(jwtService.refreshTokenActive(token, "admin"));
        jwtService.expireRefreshToken("admin");
        Assertions.assertFalse(jwtService.refreshTokenActive(token, "admin"));
    }

    @Test
    public void testTokenValidation() {
        String token = jwtService.generateRefreshToken("admin");
        Assertions.assertTrue(jwtService.validateToken(token, mockUserDetail()));

        String invalidToken = jwtService.generateRefreshToken("anonymous");
        Assertions.assertFalse(jwtService.validateToken(invalidToken, mockUserDetail()));
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
