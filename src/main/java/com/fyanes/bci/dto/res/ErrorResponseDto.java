package com.fyanes.bci.dto.res;

import java.util.List;

import com.fyanes.bci.dto.ErrorDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor

public class ErrorResponseDto {

	private List<ErrorDto> error;
}
