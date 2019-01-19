package com.oauthclient.dto.response;

import com.oauthclient.config.security.oauth2.user.UserPrincipal;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class PrincipalResponse {

    private Long id;
    private String email;
    private Collection<? extends GrantedAuthority> authorities;

    public PrincipalResponse(Long id, String email, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.authorities = authorities;
    }

    public static PrincipalResponse buildFromPrincipal(UserPrincipal principal) {
        return new PrincipalResponse(principal.getId(), principal.getEmail(), principal.getAuthorities());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }
}
