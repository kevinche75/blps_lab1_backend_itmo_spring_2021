package com.blps.app.secure;

import com.blps.app.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserDetailsImpl implements UserDetails {
    private final List<GrantedAuthority> roles;
    private final String username;
    private final String password;

    public UserDetailsImpl(User user, int numSubordinates){
        this.username = user.getLogin();
        this.password = user.getPassword();
        this.roles = new ArrayList<>();
        if(user.getCompany() != null){
            roles.add(Role.ROLE_USER);
        }
        if(numSubordinates>0){
            roles.add(Role.ROLE_MANAGER);
        }
        if(user.isAdmin() && user.getCompany() == null){
            roles.add(Role.ROLE_ADMIN_GLOBAL);
            roles.add(Role.ROLE_ADMIN_COMPANY);
        }
        if(user.isAdmin() && user.getCompany() != null){
            roles.add(Role.ROLE_ADMIN_COMPANY);
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
