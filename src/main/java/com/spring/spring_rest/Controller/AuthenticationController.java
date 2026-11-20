
// package com.spring.spring_rest.Controller;

// import java.util.Collections;

// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import com.spring.spring_rest.dto.AuthLoginDto;
// import com.spring.spring_rest.entity.Auth_Role;
// import com.spring.spring_rest.entity.Auth_User;
// import com.spring.spring_rest.repository.AuthRoleRepository;
// import com.spring.spring_rest.dto.AuthRoleRegisterDto;
// import com.spring.spring_rest.dto.AuthUserRegisterDto;
// import com.spring.spring_rest.repository.AuthUserRepository;

// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;

// @RestController
// @RequestMapping("/api/auth")
// public class AuthenticationController {

//     private AuthenticationManager authenticationManager;
//     private AuthUserRepository authUserRepo;
//     private AuthRoleRepository authRoleRepo;
//     private BCryptPasswordEncoder passwordEncoder;

//     /**
//      * Initializes the controller with authentication dependencies.
//      *
//      * @param authenticationManager manages user authentication
//      * @param authUserRepo repository for authorized users
//      * @param authRoleRepo repository for authorized roles
//      * @param passwordEncoder encodes user passwords
//      */
//     public AuthenticationController(
//             AuthenticationManager authenticationManager,
//             AuthUserRepository authUserRepo,
//             AuthRoleRepository authRoleRepo,
//             BCryptPasswordEncoder passwordEncoder) {

//         this.authenticationManager = authenticationManager;
//         this.authUserRepo = authUserRepo;
//         this.authRoleRepo = authRoleRepo;
//         this.passwordEncoder = passwordEncoder;
//     }

//     /**
//      * Registers a new authorized role if it does not already exist.
//      *
//      * @param authRoleRegisterDto contains role registration details
//      * @return response indicating the registration result
//      */
//     @PostMapping("/register_authorized_role")
//     public ResponseEntity<String> register_authRole(
//             @RequestBody AuthRoleRegisterDto authRoleRegisterDto) {

//         // Check if the role already exists.
//         if (authRoleRepo.existsByName(
//                 authRoleRegisterDto.getName())) {

//             return new ResponseEntity<>(
//                     authRoleRegisterDto.getName() + " already created.",
//                     HttpStatus.BAD_REQUEST);
//         }

//         Auth_Role auth_role = new Auth_Role();
//         auth_role.setName(authRoleRegisterDto.getName());

//         // Save the new role to the database.
//         authRoleRepo.save(auth_role);

//         return new ResponseEntity<>(
//                 auth_role.getName() + " is successfully created",
//                 HttpStatus.OK);
//     }

//     /**
//      * Registers a new user and assigns the default USER role.
//      * Checks the username and encodes the password before saving.
//      *
//      * @param authUserRegisterDto contains user registration details
//      * @return response indicating the registration result
//      */
//     @PostMapping("/register_authorized_user")
//     public ResponseEntity<String> register_authUser(
//             @RequestBody AuthUserRegisterDto authUserRegisterDto) {

//         // Check if the username is already taken.
//         if (authUserRepo.existsByUsername(
//                 authUserRegisterDto.getUsername())) {

//             return new ResponseEntity<>(
//                     authUserRegisterDto.getUsername() +
//                     " is already taken.",
//                     HttpStatus.BAD_REQUEST);
//         }

//         Auth_User auth_user = new Auth_User();
//         auth_user.setUsername(authUserRegisterDto.getUsername());

//         // Encode the password before saving.
//         auth_user.setPassword(
//                 passwordEncoder.encode(
//                         authUserRegisterDto.getPassword()));

//         // Retrieve the default USER role.
//         Auth_Role auth_role = authRoleRepo.findByName("USER")
//                 .orElseThrow(() ->
//                         new RuntimeException("Roles USER not found"));

//         // Assign the USER role to the new user.
//         auth_user.setRoles(Collections.singletonList(auth_role));

//         // Save the registered user to the database.
//         authUserRepo.save(auth_user);

//         return new ResponseEntity<>(
//                 auth_user.getUsername() + " is successfully registered",
//                 HttpStatus.OK);
//     }

//     /**
//      * Authenticates a user using their username and password.
//      * Stores successful authentication in the security context.
//      *
//      * @param authLoginDto contains user login credentials
//      * @return response indicating successful authentication
//      */
//     @PostMapping("/auth_login")
//     public ResponseEntity<String> login(
//             @RequestBody AuthLoginDto authLoginDto) {

//         // Authenticate the user using their credentials.
//         Authentication authenticate = authenticationManager.authenticate(
//                 new UsernamePasswordAuthenticationToken(
//                         authLoginDto.getUsername(),
//                         authLoginDto.getPassword()));

//         // Store the authentication in the security context.
//         SecurityContextHolder.getContext()
//                 .setAuthentication(authenticate);

//         return new ResponseEntity<>(
//                 "Sign in successfully!",
//                 HttpStatus.OK);
//     }
// }
