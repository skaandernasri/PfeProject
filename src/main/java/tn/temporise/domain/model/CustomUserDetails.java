package tn.temporise.domain.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUserDetails implements UserDetails {
    private final String email;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;
    @Getter
    @Setter
    private  String providerId; // Add providerId
    private final boolean isAccountNonLocked;
    private final boolean isAccountNonExpired;
    private final boolean isEnabled;
    private final boolean isCredentialsNonExpired;


    public CustomUserDetails(String email, String password, Collection<? extends GrantedAuthority> authorities, String providerId) {
        this.email = email;
        this.password = password;
        this.authorities = authorities;
        this.providerId = providerId;
        this.isAccountNonLocked = true;
        this.isAccountNonExpired = true;
        this.isEnabled = true;
        this.isCredentialsNonExpired = true;
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
        return isAccountNonExpired; // Modify as per your logic
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked; // Modify as per your logic
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired; // Modify as per your logic
    }

    @Override
    public boolean isEnabled() {
        return isEnabled; // Modify as per your logic
    }
}
