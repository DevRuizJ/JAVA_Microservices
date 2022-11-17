package com.example.OAuthservice.Service;

import com.example.OAuthservice.Client.UserFeignClient;
import com.example.commonslibrary.Entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService{

    private Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserFeignClient client;

    @Override
    public UserDetails loadUserByUsername(String nombreU) throws UsernameNotFoundException {

        User user = client.findByUsername(nombreU);

        if (user == null){
            throw new UsernameNotFoundException("Error en el LOGIN, usuario: " + nombreU);
        }

        List<GrantedAuthority> authorities = user.getRoleList()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .peek(authority -> log.info("Role: " + authority.getAuthority()))
                .collect(Collectors.toList());

        log.info("Usuario autenticado: " + nombreU);

        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), user.getEnabled(), true, true, true, authorities);
    }
}
