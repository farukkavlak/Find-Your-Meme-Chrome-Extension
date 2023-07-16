package com.extension.findyourmeme.generic.exceptions;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@ResponseStatus(HttpStatus.BAD_REQUEST)
@RequiredArgsConstructor
public class RequestValidationException extends RuntimeException{
    private final BaseErrorMessage baseErrorMessage;
}