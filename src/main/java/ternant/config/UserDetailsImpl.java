package ternant.config;

import lombok.*;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ternant.userModule.Roles;
import ternant.userModule.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDetailsImpl implements UserDetails {

    private UUID id;

    private String email;

    private String password;

    private List<Roles> appRoles = new ArrayList<>();

    public static UserDetailsImpl build(User user){
        return UserDetailsImpl.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .appRoles(user.getRoles())
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return appRoles.stream()
                .map(roles -> new SimpleGrantedAuthority(roles.getRoleName().toString()))
                .toList();
    }

    @Override
    public @Nullable String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
