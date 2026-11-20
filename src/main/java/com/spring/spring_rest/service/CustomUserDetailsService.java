
package com.spring.spring_rest.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.spring.spring_rest.entity.Auth_Role;
import com.spring.spring_rest.entity.Auth_User;
import com.spring.spring_rest.repository.AuthUserRepository;

/**
 * Provides user details for Spring Security authentication.
 * Retrieves user credentials and converts roles into authorities.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AuthUserRepository authUserRepo;

    /**
     * Initializes the service with the user repository.
     *
     * @param authUserRepo repository for retrieving authorized users
     */
    public CustomUserDetailsService(AuthUserRepository authUserRepo) {
        this.authUserRepo = authUserRepo;
    }

    /**
     * Loads user details based on the provided username.
     * Retrieves user credentials and assigned authorities.
     *
     * @param theUsername the username to search for
     * @return the user details required for authentication
     * @throws UsernameNotFoundException if the user does not exist
     */
    @Override
    public UserDetails loadUserByUsername(String theUsername) {

        // Retrieve the user or throw an exception if not found.
        Auth_User user = authUserRepo.findByUsername(theUsername)
                .orElseThrow(
                        () -> new UsernameNotFoundException("Username " +
                                theUsername + " does not exist")
                );

        // Return user credentials with their granted authorities.
        return new User(user.getUsername(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    /**
     * Converts assigned user roles into Spring Security authorities.
     *
     * @param roles the list of roles assigned to the user
     * @return the collection of granted authorities
     */
    private Collection<GrantedAuthority> mapRolesToAuthorities(
            List<Auth_Role> roles) {

        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}
