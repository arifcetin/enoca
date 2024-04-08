package com.example.enoca.security;

import com.example.enoca.entity.Customer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class JwtUserDetails implements UserDetails {

    public Long user_id;
    private String name;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public JwtUserDetails(Long user_id, String name, String password, Collection<? extends GrantedAuthority> authorities) {
        this.user_id = user_id;
        this.name = name;
        this.password = password;
        this.authorities = authorities;
    }

    public static JwtUserDetails create(Customer customer) {
        List<GrantedAuthority> authoritiesList = new ArrayList<>();
        authoritiesList.add(new SimpleGrantedAuthority("user"));
        return new JwtUserDetails(customer.getCustomerId(), customer.getName(), customer.getPassword(), authoritiesList);
    }
    public Long getId() {
        return user_id;
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
        return name;
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
        return true;
    }
}
