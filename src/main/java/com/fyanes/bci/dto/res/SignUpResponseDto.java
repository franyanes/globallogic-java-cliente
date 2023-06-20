package com.fyanes.bci.dto.res;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignUpResponseDto {

    private UUID id;
    private String created;
    private String lastLogin;
    private String token;
    private Boolean isActive;

    // This override is for handling the dates of AccountEntity that are of type LocalDateTime.
    // I also later created the utility class CustomDateFormatter that addresses this concern, but I still choose to leave this implementation regardless.
    public static class SignUpResponseDtoBuilder {

    	public SignUpResponseDtoBuilder created(LocalDateTime date) {
        	created = date == null ? null : formatDate(date);
        	return this;
        }

        public SignUpResponseDtoBuilder lastLogin(LocalDateTime date) {
        	lastLogin = date == null ? null : formatDate(date);
        	return this;
        }

        private String formatDate(LocalDateTime date) {
        	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy hh:mm:ss a", Locale.US);
        	return date.format(formatter);
        }
    }
}
