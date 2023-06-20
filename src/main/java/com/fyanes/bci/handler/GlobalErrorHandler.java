package com.fyanes.bci.handler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fyanes.bci.exceptions.AccountAlreadyExistsException;
import com.fyanes.bci.exceptions.TokenDoesNotMatchAnyAccountException;
import com.fyanes.bci.utils.CustomDateFormatter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.fyanes.bci.dto.ErrorDto;
import com.fyanes.bci.dto.res.ErrorResponseDto;

@ControllerAdvice
public class GlobalErrorHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponseDto> handleException(Exception ex, WebRequest request) {
        return createErrorResponse(ex, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccountAlreadyExistsException.class)
    protected ResponseEntity<ErrorResponseDto> handleAccountAlreadyExistsException(AccountAlreadyExistsException ex, WebRequest request) {
        return createErrorResponse(ex, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(TokenDoesNotMatchAnyAccountException.class)
    protected ResponseEntity<ErrorResponseDto> handleTokenDoesNotMatchAnyAccountException(TokenDoesNotMatchAnyAccountException ex, WebRequest request) {
        return createErrorResponse(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    protected ResponseEntity<ErrorResponseDto> handleMissingRequestHeaderException(MissingRequestHeaderException ex, WebRequest request) {
        return createErrorResponse(ex, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponseDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        String info = "======================\nINFO:\n" + ex;
        System.out.println(info);
        System.out.println(ex.getMessage());
        return createErrorResponse(ex.getAllErrors(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    private ResponseEntity<ErrorResponseDto> createErrorResponse(Exception ex, HttpStatus httpStatus) {
        String info = "======================\nINFO:\n" + ex;
        System.out.println(info);
        System.out.println(ex.getMessage());
        List<ErrorDto> errors = new ArrayList<>();
        errors.add(ErrorDto.builder()
                .timestamp(CustomDateFormatter.convertDateToString(LocalDateTime.now()))
                .code(httpStatus.value())
                .detail(ex.getMessage())
                .build());
        return new ResponseEntity<>(ErrorResponseDto.builder()
                .error(errors)
                .build(),
                httpStatus);
    }

    private ResponseEntity<ErrorResponseDto> createErrorResponse(List<ObjectError> exceptionErrors, HttpStatus httpStatus) {
        List<ErrorDto> errors = new ArrayList<>();
        for (ObjectError o : exceptionErrors) {
            errors.add(ErrorDto.builder()
                    .timestamp(CustomDateFormatter.convertDateToString(LocalDateTime.now()))
                    .code(httpStatus.value())
                    .detail(o.getDefaultMessage())
                    .build());
        }
        return new ResponseEntity<>(ErrorResponseDto.builder()
                    .error(errors)
                    .build(),
                httpStatus);
    }
}
