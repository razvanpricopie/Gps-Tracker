package org.scd.service.security;

import org.scd.model.User;
import org.scd.model.security.CustomUserDetails;
import org.scd.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Objects;

/**
 * UserDetailsService is a spring security class which is used under the hood for retrieving the user from database
 */
public class CustomUserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User user = userRepository.findByEmail(username);

        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("User not found");
        }

        return new CustomUserDetails(user);
    }
}
