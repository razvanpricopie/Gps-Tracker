package org.scd.config;

import org.scd.repository.RoleRepository;
import org.scd.repository.UserRepository;
import org.scd.service.UserService;
import org.scd.service.UserServiceImpl;
import org.scd.service.security.CustomUserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class AppConfig {
    @Bean
    public UserService userService(final UserRepository userRepository,
                                   final RoleRepository roleRepository,
                                   final BCryptPasswordEncoder passwordEncoder) {
        return new UserServiceImpl(userRepository, roleRepository, passwordEncoder);
    }

    /**
     * Create user service bean used for find the user by email
     *
     * @return
     */
    @Bean("customUserDetailsService")
    public UserDetailsService createUserDetailsService(final UserRepository userRepository) {
        return new CustomUserDetailsServiceImpl(userRepository);
    }

    /**
     * Create password encoder bean used for encrypting the password
     *
     * @return
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
