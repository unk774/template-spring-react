package com.example.template.controllers;


import com.example.template.contollers.AuthController;
import com.example.template.services.InternalUserDetailService;
import com.example.template.services.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collection;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
public class AuthControllerTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach()
    public void setup()
    {
        //Init MockMvc Object and build
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private  InternalUserDetailService internalUserDetailService;

    @MockBean
    private AuthenticationManager authenticationManager;

    @Test
    void testTest() throws Exception {
        mockAuth();
        mockGenerateAccessToken();
        mockGenerateRefreshToken();
        Mockito.when(this.jwtService.generateRefreshToken("admin"))
                .thenReturn("encrypted_refresh_token");
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"admin\", \"password\": \"!test!\"}"))
                .andExpect(status().isOk())
                .andExpect(matchLoginTokens("$"));
    }

    public static ResultMatcher matchLoginTokens(String prefix) {
        return ResultMatcher.matchAll(
                jsonPath(prefix + ".accessToken").value("encrypted_access_token"),
                jsonPath(prefix + ".refreshToken").value("encrypted_refresh_token")
        );
    }

    private void mockGenerateAccessToken () {
        Mockito.when(this.jwtService.generateToken("admin"))
                .thenReturn("encrypted_access_token");
    }

    private void mockGenerateRefreshToken () {
        Mockito.when(this.jwtService.generateRefreshToken("admin"))
                .thenReturn("encrypted_refresh_token");
    }

    private void mockAuth() {
        Mockito.when(this.authenticationManager
                        .authenticate(new UsernamePasswordAuthenticationToken("admin", "!test!")))
                .thenReturn(new Authentication() {
                    @Override
                    public Collection<? extends GrantedAuthority> getAuthorities() {
                        return null;
                    }

                    @Override
                    public Object getCredentials() {
                        return null;
                    }

                    @Override
                    public Object getDetails() {
                        return null;
                    }

                    @Override
                    public Object getPrincipal() {
                        return null;
                    }

                    @Override
                    public boolean isAuthenticated() {
                        return true;
                    }

                    @Override
                    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

                    }

                    @Override
                    public String getName() {
                        return null;
                    }
                });
    }

}
