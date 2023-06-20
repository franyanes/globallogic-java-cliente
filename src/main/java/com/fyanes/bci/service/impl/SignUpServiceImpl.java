package com.fyanes.bci.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fyanes.bci.dto.PhoneDto;
import com.fyanes.bci.dto.req.SignUpRequestDto;
import com.fyanes.bci.dto.res.SignUpResponseDto;
import com.fyanes.bci.entity.AccountEntity;
import com.fyanes.bci.entity.PhoneEntity;
import com.fyanes.bci.exceptions.AccountAlreadyExistsException;
import com.fyanes.bci.repository.AccountRepository;
import com.fyanes.bci.service.SignUpService;
import com.fyanes.bci.utils.PasswordEncryption;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SignUpServiceImpl implements SignUpService {

    private final AccountRepository accountRepository;

    @Override
    public ResponseEntity<SignUpResponseDto> createAccount(SignUpRequestDto req) {
    	if (accountRepository.findByEmail(req.getEmail()) != null) {
    		throw new AccountAlreadyExistsException("An account with that email already exists");
    	}
    	AccountEntity createdAccount = addAccountToRepository(req);
        return new ResponseEntity<>(
    		SignUpResponseDto.builder()
                .id(createdAccount.getId())
                .created(createdAccount.getCreated())
                .lastLogin(createdAccount.getLastLogin())
                .token(createdAccount.getToken())
                .isActive(createdAccount.getIsActive())
                .build(),
            HttpStatus.OK);
    }

    private AccountEntity addAccountToRepository(SignUpRequestDto req) {
    	String encryptedPassword = PasswordEncryption.encrypt(req.getPassword());
    	String token = Jwts.builder()
    			.claim("email", req.getEmail())
    			.signWith(Keys.secretKeyFor(SignatureAlgorithm.HS256))
    			.compact();
    	AccountEntity account = AccountEntity.builder()
	        .name(req.getName())
	        .email(req.getEmail())
	        .password(encryptedPassword)
            .token(token)
	        .created(LocalDateTime.now())
	        .lastLogin(null)
	        .isActive(true)
	        .build();
        List<PhoneEntity> phones = new ArrayList<>();
        for (PhoneDto p : (req.getPhones() == null ? new ArrayList<PhoneDto>() : req.getPhones())) {
        	PhoneEntity newPhoneEntity = PhoneEntity.builder()
                .number(p.getNumber())
                .cityCode(p.getCityCode())
                .countryCode(p.getCountryCode())
                .account(account)
                .build();
            phones.add(newPhoneEntity);
        }
        account.setPhones(phones);
        return accountRepository.save(account);
    }
}
