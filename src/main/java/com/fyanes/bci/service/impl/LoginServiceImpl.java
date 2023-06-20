package com.fyanes.bci.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fyanes.bci.dto.PhoneDto;
import com.fyanes.bci.dto.res.LoginResponseDto;
import com.fyanes.bci.entity.AccountEntity;
import com.fyanes.bci.entity.PhoneEntity;
import com.fyanes.bci.exceptions.TokenDoesNotMatchAnyAccountException;
import com.fyanes.bci.repository.AccountRepository;
import com.fyanes.bci.repository.PhoneRepository;
import com.fyanes.bci.service.LoginService;
import com.fyanes.bci.utils.CustomDateFormatter;
import com.fyanes.bci.utils.PasswordEncryption;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

	private final AccountRepository accountRepository;
	private final PhoneRepository phoneRepository;

	@Override
	public ResponseEntity<LoginResponseDto> loginAccount(String authorizationHeader) {
		String token = authorizationHeader.contains("Bearer ") ? authorizationHeader.replace("Bearer ", "") : authorizationHeader;
		AccountEntity account = accountRepository.findByToken(token);
		if (account == null) {
    		throw new TokenDoesNotMatchAnyAccountException("The token does not match any account");
    	}
		LocalDateTime lastLoginAux = account.getLastLogin();
		updateAccountData(account);
		List<PhoneDto> phones = createPhoneDtoList(phoneRepository.findByAccount(account));
		return new ResponseEntity<>(LoginResponseDto.builder()
                .id(account.getId())
                .created(CustomDateFormatter.convertDateToString(account.getCreated()))
                .lastLogin(CustomDateFormatter.convertDateToString(lastLoginAux))
                .token(account.getToken())
                .isActive(account.getIsActive())
                .name(account.getName())
                .email(account.getEmail())
				.password(PasswordEncryption.decrypt(account.getPassword()) + " (no es buena práctica retornar la contraseña pero el enunciado lo requería)")
                .phones(phones)
                .build(),
            HttpStatus.OK);
	}

	private void updateAccountData(AccountEntity account) {
		account.setToken(Jwts.builder()
    			.claim("email", account.getEmail())
    			.signWith(Keys.secretKeyFor(SignatureAlgorithm.HS256))
    			.compact());
		account.setLastLogin(LocalDateTime.now());
		accountRepository.save(account);
	}

	private List<PhoneDto> createPhoneDtoList(List<PhoneEntity> phones) {
		List<PhoneDto> phoneList = new ArrayList<>();
		for (PhoneEntity p : phones) {
			phoneList.add(PhoneDto.builder()
					.number(p.getNumber())
					.cityCode(p.getCityCode())
					.countryCode(p.getCountryCode())
					.build());
		}
		return phoneList;
	}
}
