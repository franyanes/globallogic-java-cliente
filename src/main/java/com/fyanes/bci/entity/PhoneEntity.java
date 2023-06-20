package com.fyanes.bci.entity;


import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="phones")
@Data
@ToString(exclude = "account")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PhoneEntity {

	@Id
	@GeneratedValue(generator = "UUID")
	private UUID id;

    @Column(name = "number")
    private Long number;
    
    @Column(name = "city_code")
    private Integer cityCode;
    
    @Column(name = "country_code")
    private String countryCode;
    
    @ManyToOne
    @JoinColumn(name = "account_id")
    private AccountEntity account;
}
