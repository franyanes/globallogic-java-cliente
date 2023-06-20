package com.fyanes.bci.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fyanes.bci.entity.AccountEntity;
import com.fyanes.bci.entity.PhoneEntity;

public interface PhoneRepository extends JpaRepository<PhoneEntity, Long> {

	public List<PhoneEntity> findByAccount(AccountEntity account);
}
