package com.yash.login.JWT.service;

import com.yash.login.JWT.Util.AuthUtil;
import com.yash.login.JWT.dto.LoginRequestDTO;
import com.yash.login.JWT.dto.LoginResponseDTO;
import com.yash.login.JWT.dto.SignUpResponseDTO;
import com.yash.login.JWT.entity.User;
import com.yash.login.JWT.repo.UserRepo;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Data
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final AuthUtil authUtil;
    private final UserRepo userRepo;


    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {


        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(), loginRequestDTO.getPassword())
        );

        User user = (User) authentication.getPrincipal();

        String token = authUtil.generateKey(user);

        return new LoginResponseDTO(token, user.getId());
    }

    public SignUpResponseDTO signup(LoginRequestDTO signUpRequestDTO) {
        User user = userRepo.findByUsername(signUpRequestDTO.getUsername()).orElse(null);
        if (user != null) {
            throw new RuntimeException("Username already exists");
        }

        user = userRepo.save(User.builder()
                .username(signUpRequestDTO.getUsername())
                .password(signUpRequestDTO.getPassword())
                .build());
        return new SignUpResponseDTO(user.getId(), user.getUsername());
    }
}
