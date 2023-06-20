package com.fyanes.bci.service;

import org.springframework.http.ResponseEntity;

import com.fyanes.bci.dto.req.SignUpRequestDto;
import com.fyanes.bci.dto.res.SignUpResponseDto;

public interface SignUpService {

    public ResponseEntity<SignUpResponseDto> createAccount(SignUpRequestDto req);
}
