package tn.temporise.domain.model;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;


import java.util.Collections;

public record CustomUserDetails(
        String email,
        String password,
        Collection<? extends GrantedAuthority> authorities,
        String providerId,
        boolean isAccountNonLocked,
        boolean isAccountNonExpired,
        boolean isEnabled,
        boolean isCredentialsNonExpired
) implements UserDetails {



    // Constructor for the record
    public CustomUserDetails(
            String email,
            String password,
            Collection<? extends GrantedAuthority> authorities,
            String providerId
    ) {
        this(email, password, authorities , providerId, true, true, true, true);
    }
    public CustomUserDetails(
            String email,
            Collection<? extends GrantedAuthority> authorities
    ) {
        this(email, null, authorities , null, true, true, true, true);
    }

    // Constructor for cases where only email and providerId are provided
    public CustomUserDetails(String email, String providerId) {
        this(email, null, Collections.emptyList(),providerId, true, true, true, true);
    }

    // Getter for providerId
    public String getProviderId() {
        return providerId;
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
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
