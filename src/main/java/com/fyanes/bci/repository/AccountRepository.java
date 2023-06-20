package com.fyanes.bci.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fyanes.bci.entity.AccountEntity;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

	public AccountEntity findByEmail(String email);

	public AccountEntity findByToken(String token);
}
