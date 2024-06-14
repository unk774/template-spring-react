package com.example.template.services;

import com.example.template.components.UserPrincipal;
import com.example.template.model.entities.DBUser;
import com.example.template.repositories.DBUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InternalUserDetailService implements UserDetailsService {
    @Autowired
    DBUserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<DBUser> dbUser = userRepository.findByUsername(username);
        return dbUser.map(user -> new UserPrincipal(user.getUsername(),
                user.getPassword(),
                user.getDbAuthorities()
                        .stream().map(dbAuthority -> new SimpleGrantedAuthority(dbAuthority.getName()))
                        .collect(Collectors.toSet())
        )).orElse(null);
    }
}
