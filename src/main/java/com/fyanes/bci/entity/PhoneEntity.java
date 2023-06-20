package com.fyanes.bci.entity;


import java.util.UUID;

import javax.persistence.*;

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
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "BINARY(16)")
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
