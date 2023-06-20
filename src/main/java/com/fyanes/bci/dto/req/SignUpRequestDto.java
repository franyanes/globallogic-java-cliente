package com.fyanes.bci.dto.req;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.fyanes.bci.dto.PhoneDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequestDto {

    private String name;

    @Valid
    @NotEmpty(message = "The email field is required")
    @Pattern(regexp = "^(.+)@(.+)\\.(.+)$", message = "The email format is invalid")
    private String email;

    @Valid
    @NotEmpty(message = "The password field is required")
    @Pattern(regexp = "^[A-Za-z0-9]{8,12}$", message = "The password must be between 8 and 12 characters long and can only contain alphanumeric characters")
    @Pattern(regexp = "^([a-z0-9])*[A-Z]([a-z0-9])*$", message = "The password must contain only one uppercase letter")
    @Pattern(regexp = "^([a-zA-Z])*[0-9]{2}([a-zA-Z])*$", message = "The password must contain two digits")
    private String password;

    private List<PhoneDto> phones;
}
