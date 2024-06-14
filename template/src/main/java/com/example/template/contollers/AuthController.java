package com.example.template.contollers;

import com.example.template.model.dto.AuthRequestDTO;
import com.example.template.model.dto.JwtResponseDTO;
import com.example.template.model.dto.RefreshRequestDTO;
import com.example.template.services.InternalUserDetailService;
import com.example.template.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private InternalUserDetailService internalUserDetailService;

    @PostMapping("/login")
    public JwtResponseDTO getAccessToken(@RequestBody AuthRequestDTO authRequestDTO){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));
        if(authentication != null && authentication.isAuthenticated()){
            JwtResponseDTO dto = new JwtResponseDTO();
            dto.setAccessToken(jwtService.generateToken(authRequestDTO.getUsername()));
            dto.setRefreshToken(jwtService.generateRefreshToken(authRequestDTO.getUsername()));
            return dto;
        } else {
            throw new UsernameNotFoundException("invalid user request!");
        }
    }


    @PostMapping("/refresh")
    public JwtResponseDTO refreshAccessToken(@RequestBody RefreshRequestDTO refreshRequestDTO) {
        String token = refreshRequestDTO.getRefreshToken();
        String username = jwtService.extractUsername(token);
        if (username != null
                && jwtService.refreshTokenActive(token, username)
                && jwtService.validateToken(token, internalUserDetailService.loadUserByUsername(username))) {
            JwtResponseDTO dto = new JwtResponseDTO();
            dto.setAccessToken(jwtService.generateToken(username));
            return dto;
        } else {
            throw new UsernameNotFoundException("invalid user request!");
        }
    }

    @PostMapping("/expire")
    public String expireRefreshToken(@RequestParam final String username) {
        return jwtService.expireRefreshToken(username)
                ? "jwt token expire for user " + username
                : "no active token found";
    }

    @GetMapping("/user")
    public Object getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return authentication.getPrincipal();
        } else {
            throw new UsernameNotFoundException("Unauthorized");
        }
    }

    @GetMapping("/ping")
    public String test() {
        try {
            return "Welcome";
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
