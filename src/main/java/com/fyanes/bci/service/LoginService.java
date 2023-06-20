package com.fyanes.bci.service;

import org.springframework.http.ResponseEntity;

import com.fyanes.bci.dto.res.LoginResponseDto;

public interface LoginService {

	public ResponseEntity<LoginResponseDto> loginAccount(String authorizationHeader);
}
