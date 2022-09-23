package com.cloud.pay.security;

import org.springframework.security.core.GrantedAuthority;


public class UrlGrantedAuthority implements GrantedAuthority {

    private String permissionUrl;

    public String getPermissionUrl() {
        return permissionUrl;
    }

    public void setPermissionUrl(String permissionUrl) {
        this.permissionUrl = permissionUrl;
    }

    public UrlGrantedAuthority (String permissionUrl, String method) {
        this.permissionUrl = permissionUrl;
    }

    @Override
    public String getAuthority() {
        return this.permissionUrl;
    }
}
