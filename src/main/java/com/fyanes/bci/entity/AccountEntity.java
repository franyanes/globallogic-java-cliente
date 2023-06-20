package com.fyanes.bci.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="accounts")
@Data
@ToString(exclude = "phones")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountEntity {

	@Id
	@GeneratedValue(generator = "UUID")
	private UUID id;

    @Column(name="name")
    private String name;

    @Column(name="email")
    private String email;

    @Column(name="password")
    private String password;

    @Column(name="token")
    private String token;

    @Column(name="created")
    private LocalDateTime created;

    @Column(name="last_login")
    private LocalDateTime lastLogin;

    @Column(name="is_active")
    private Boolean isActive;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "account")
    private List<PhoneEntity> phones;
}
