package dev.lewi.backend.appuser;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    // Error message for user not found
    private final static String USER_NOT_FOUND_MSG = "user with email %s not found";

    // Repository for accessing AppUser data
    private final AppUserRepository appUserRepository;

    // Method to load user by username (email in this case)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Find user by email in the repository
        return appUserRepository.findByEmail(username)
                // Throw exception if user is not found
                .orElseThrow(() ->
                        new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, username)));
    }
}
