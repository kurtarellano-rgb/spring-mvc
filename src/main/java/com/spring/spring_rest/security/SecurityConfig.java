
// package com.spring.spring_rest.security;

// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.config.Customizer;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.web.SecurityFilterChain;

// import com.spring.spring_rest.service.CustomUserDetailsService;

// /**
//  * Configures Spring Security authentication and authorization.
//  * Defines security filters, password encoding, and authentication.
//  */
// @EnableWebSecurity
// @Configuration
// public class SecurityConfig {

//     /**
//      * Configures HTTP security rules for incoming requests.
//      * Allows public access to authentication endpoints and
//      * requires authentication for all other requests.
//      *
//      * @param security the HTTP security configuration
//      * @return the configured security filter chain
//      * @throws Exception if security configuration fails
//      */
//     @Bean
//     SecurityFilterChain filterChain(HttpSecurity security)
//             throws Exception {
//         return security
//                 .csrf(AbstractHttpConfigurer::disable)
//                 .authorizeHttpRequests(auth -> auth
//                         .requestMatchers("/api/auth/**").permitAll()
//                         .anyRequest().authenticated()
//                 )
//                 .httpBasic(Customizer.withDefaults())
//                 .build();
//     }

//     private CustomUserDetailsService userDetailsService;

//     /**
//      * Initializes the security configuration with the user
//      * details service.
//      *
//      * @param userDetailsService service for retrieving user details
//      */
//     public SecurityConfig(CustomUserDetailsService userDetailsService) {
//         this.userDetailsService = userDetailsService;
//     }

//     /**
//      * Creates a BCrypt password encoder for hashing passwords.
//      *
//      * @return the BCrypt password encoder
//      */
//     @Bean
//     BCryptPasswordEncoder passwordEncoder() {
//         return new BCryptPasswordEncoder();
//     }

//     /**
//      * Retrieves the authentication manager from the
//      * Spring Security authentication configuration.
//      *
//      * @param authentication the authentication configuration
//      * @return the configured authentication manager
//      * @throws Exception if authentication manager retrieval fails
//      */
//     @Bean
//     AuthenticationManager authenticationManager(
//             AuthenticationConfiguration authentication)
//             throws Exception {
//         return authentication.getAuthenticationManager();
//     }
// }
