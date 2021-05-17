package com.blps.app.securty;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_USER, ROLE_MANAGER, ROLE_ADMIN_COMPANY, ROLE_ADMIN_GLOBAL;

    @Override
    public String getAuthority() {
        return name();
    }
}
