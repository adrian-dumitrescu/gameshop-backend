package com.gamekeys.gameshop.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Data
public class AppUserDetails implements UserDetails {
    private AppUser appUser;

    public AppUserDetails(AppUser user) {
        this.appUser = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<AppRole> roles = appUser.getRoles();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (AppRole role : roles) {
            //authorities.add(new SimpleGrantedAuthority(role.getRole().name()));
            authorities.add(new SimpleGrantedAuthority(role.getRole().name()));
        }
        return authorities;

        // Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        // stream(roles).forEach(role -> {
        // authorities.add(new SimpleGrantedAuthority(role.getRole().toString()));

        // return stream(this.appUser.getAuthorities()).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.appUser.getPassword();
    }

    @Override
    public String getUsername() {
        return this.appUser.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.appUser.getIsNotLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.appUser.getIsEnabled();
    }

}
