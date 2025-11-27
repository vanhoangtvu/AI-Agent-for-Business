package com.business.aiagent.security;

import com.business.aiagent.entity.User;
import com.business.aiagent.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    
    private final UserRepository userRepository;
    
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        
        Set<GrantedAuthority> authorities = user.getRoles().stream()
                .flatMap(role -> {
                    var roleAuthorities = role.getPermissions().stream()
                            .map(permission -> new SimpleGrantedAuthority(permission.name()));
                    var roleAuthority = java.util.stream.Stream.of(new SimpleGrantedAuthority("ROLE_" + role.getName().name()));
                    return java.util.stream.Stream.concat(roleAuthority, roleAuthorities);
                })
                .collect(Collectors.toSet());
        
        // Return custom UserPrincipal that implements UserDetails and contains User entity
        return new UserPrincipal(user, authorities);
    }
}
