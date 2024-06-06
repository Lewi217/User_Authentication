package dev.lewi.backend.appuser;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class AppUser implements UserDetails {

    // Define a sequence generator for the primary key
    @SequenceGenerator(
            name = "student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_sequence"
    )
    private Long id; // Primary key

    private String name; // User's full name
    private String username; // Username for authentication
    private String email; // Email address
    private String password; // Password (should be hashed)

    // Enumerated type for user roles
    @Enumerated(EnumType.STRING)
    private AppUserRole appUserRole;

    private Boolean locked = false; // Account lock status
    private Boolean enabled = false; // Account enabled status

    // Constructor with parameters
    public AppUser(String name,
                   String username,
                   String email,
                   String password,
                   AppUserRole appUserRole,
                   Boolean locked,
                   Boolean enabled) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.appUserRole = appUserRole;
        this.locked = locked;
        this.enabled = enabled;
    }

    // UserDetails methods
    @Override
    public boolean isAccountNonExpired() {
        // Define if the account is expired or not
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // Define if the account is locked or not
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // Define if the credentials are expired or not
        return true;
    }

    @Override
    public boolean isEnabled() {
        // Define if the account is enabled or not
        return enabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Define authorities (roles) for the user
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(appUserRole.name());
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        // Return the user's password
        return password;
    }

    @Override
    public String getUsername() {
        // Return the user's username
        return username;
    }
}
