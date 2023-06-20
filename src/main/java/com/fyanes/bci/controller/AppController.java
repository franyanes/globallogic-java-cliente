package com.fyanes.bci.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.fyanes.bci.dto.req.SignUpRequestDto;
import com.fyanes.bci.dto.res.LoginResponseDto;
import com.fyanes.bci.dto.res.SignUpResponseDto;
import com.fyanes.bci.service.LoginService;
import com.fyanes.bci.service.SignUpService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Validated
public class AppController {

    private final SignUpService signUpService;
    private final LoginService loginService;
    
    @PostMapping("/sign_up")
    public ResponseEntity<SignUpResponseDto> createAccount(@RequestBody @Valid SignUpRequestDto req) {
    	ResponseEntity<SignUpResponseDto> response = signUpService.createAccount(req);
        return response;
    }

	@GetMapping("/login")
	public ResponseEntity<LoginResponseDto> loginAccount(@RequestHeader(value = "Authorization", required = true) String authorizationHeader) {
		ResponseEntity<LoginResponseDto> response = loginService.loginAccount(authorizationHeader);
		return response;
	}
}
