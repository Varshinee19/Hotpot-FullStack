package com.hexaware.hotpot.security.config;

import java.util.Collection;
import java.util.Collections;
import java.util.List;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.hexaware.hotpot.entities.Customer;



public class CustomerUserDetails implements UserDetails{
    private String email;
    private String password;
    private List<GrantedAuthority> authorities;

    public CustomerUserDetails(Customer customer) {
        this.email = customer.getEmail();
        this.password = customer.getPassword();
        authorities= Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + customer.getRole().name().toUpperCase()));
        System.out.println("User: " + customer.getEmail() + ", Role: " + customer.getRole());
        System.out.println("Authorities: " + authorities);
                       
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
        return email;
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
	


