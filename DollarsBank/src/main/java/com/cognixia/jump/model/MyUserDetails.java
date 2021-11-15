package com.cognixia.jump.model;

import java.util.Arrays;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
public class MyUserDetails implements UserDetails {

    private String userName;
    private String password;
    private boolean active;
    private List<GrantedAuthority> authorities;

    public MyUserDetails(Customer customer) {
        this.userName = customer.getUsername();
        this.password = customer.getPassword();
        this.active = true;
      this.authorities = Arrays.asList(new SimpleGrantedAuthority(  "ROLE_USER" ));
//        this.authorities = Arrays.stream(customer.getRole())
//        .map(SimpleGrantedAuthority::new)
//        .collect(Collectors.toList());
      
//        Arrays.stream(customer.getRoles().split(","))
//        .map(SimpleGrantedAuthority::new)
//        .collect(Collectors.toList())
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }
}