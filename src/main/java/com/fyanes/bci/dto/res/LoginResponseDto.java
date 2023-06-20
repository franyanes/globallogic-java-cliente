package com.fyanes.bci.dto.res;

import java.util.List;
import java.util.UUID;

import com.fyanes.bci.dto.PhoneDto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDto {

    private UUID id;
    private String created;
    private String lastLogin;
    private String token;
    private Boolean isActive;
    private String name;
    private String email;
    private String password;
    private List<PhoneDto> phones;
}
