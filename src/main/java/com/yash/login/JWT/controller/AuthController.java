package com.yash.login.JWT.controller;


import com.yash.login.JWT.dto.LoginRequestDTO;
import com.yash.login.JWT.dto.LoginResponseDTO;
import com.yash.login.JWT.dto.SignUpResponseDTO;
import com.yash.login.JWT.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {


    private  AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO){
        return ResponseEntity.ok(authService.login(loginRequestDTO));

    }


    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDTO> signup(@RequestBody LoginRequestDTO signUpRequestDTO){
        return ResponseEntity.ok(authService.signup(signUpRequestDTO));

    }

}
