package com.musfiul.chatbotpsychiatrist.service.impl;

import com.musfiul.chatbotpsychiatrist.constant.UserRole;
import com.musfiul.chatbotpsychiatrist.dto.request.auth.AuthRequest;
import com.musfiul.chatbotpsychiatrist.dto.response.auth.LoginResponse;
import com.musfiul.chatbotpsychiatrist.dto.response.auth.RegisterResponse;
import com.musfiul.chatbotpsychiatrist.entity.Role;
import com.musfiul.chatbotpsychiatrist.entity.User;
import com.musfiul.chatbotpsychiatrist.entity.UserAccount;
import com.musfiul.chatbotpsychiatrist.repository.UserAccountRepository;
import com.musfiul.chatbotpsychiatrist.service.AuthService;
import com.musfiul.chatbotpsychiatrist.service.JwtService;
import com.musfiul.chatbotpsychiatrist.service.RoleService;
import com.musfiul.chatbotpsychiatrist.service.UserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserAccountRepository userAccountRepository;
    private final RoleService roleService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    @Value("${auth.username.admin}")
    private String adminUsername;
    @Value("${auth.password.admin}")
    private String adminPassword;

    @Transactional(rollbackFor = Exception.class)
    @PostConstruct
    public void initSuperAdmin() {
        Optional<UserAccount> currentUser = userAccountRepository.findByUsername(adminUsername);
        if (currentUser.isPresent()) return;

        Role admin = roleService.getOrSave(UserRole.ROLE_ADMIN);
        Role customer = roleService.getOrSave(UserRole.ROLE_CUSTOMER);

        UserAccount account = UserAccount.builder()
                .username(adminUsername)
                .password(passwordEncoder.encode(adminPassword))
                .role(List.of( admin, customer))
                .isEnabled(true)
                .build();

        userAccountRepository.save(account);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public RegisterResponse register(AuthRequest request) {
        Role role = roleService.getOrSave(UserRole.ROLE_CUSTOMER);
        String hashPassword = passwordEncoder.encode(request.getPassword());

        UserAccount account = UserAccount.builder()
                .username(request.getUsername())
                .password(hashPassword)
                .role(List.of(role))
                .isEnabled(true)
                .build();

        userAccountRepository.saveAndFlush(account);

        User customer = User.builder()
                .status(true)
                .userAccount(account)
                .build();
        userService.create(customer);

        List<String> roles = account.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList();

        return RegisterResponse.builder()
                .username(account.getUsername())
                .roles(roles)
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public RegisterResponse registerAdmin(AuthRequest request) {
        Role roleAdmin = roleService.getOrSave(UserRole.ROLE_ADMIN);
        Role roleCustomer = roleService.getOrSave(UserRole.ROLE_CUSTOMER);

        String hashPassword = passwordEncoder.encode(request.getPassword());

        UserAccount account = UserAccount.builder()
                .username(request.getUsername())
                .password(hashPassword)
                .role(List.of(roleAdmin, roleCustomer))
                .isEnabled(true)
                .build();

        userAccountRepository.saveAndFlush(account);

        User customer = User.builder()
                .status(true)
                .userAccount(account)
                .build();
        userService.create(customer);

        List<String> roles = account.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList();

        return RegisterResponse.builder()
                .username(account.getUsername())
                .roles(roles)
                .build();
    }
    @Transactional(readOnly = true)
    @Override
    public LoginResponse login(AuthRequest request) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        );
        Authentication authenticate = authenticationManager.authenticate(authentication);
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        UserAccount userAccount = (UserAccount) authenticate.getPrincipal();

        String token = jwtService.generateToken(userAccount);
        return LoginResponse.builder()
                .username(userAccount.getUsername())
                .roles(userAccount.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .token(token)
                .build();
    }
}
