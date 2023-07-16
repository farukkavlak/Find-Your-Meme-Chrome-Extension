package com.extension.findyourmeme.generic.exceptions;
import com.extension.findyourmeme.generic.response.ExceptionResponse;
import com.extension.findyourmeme.generic.response.RestResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestController
@ControllerAdvice
@RequiredArgsConstructor
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest webRequest){

        Date errorDate = new Date();
        String message = ex.getMessage();
        String description = webRequest.getDescription(false);
        return getResponseEntity(errorDate, message, description, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleBusinessExceptions(BusinessException ex, WebRequest webRequest){
        Date errorDate = new Date();
        String message = ex.getBaseErrorMessage().toString();
        String description = webRequest.getDescription(false);

        return getResponseEntity(errorDate, message, description, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler
    public final ResponseEntity<Object> handleRequestValidationExceptions(RequestValidationException ex, WebRequest webRequest){
        Date errorDate = new Date();
        String message = ex.getBaseErrorMessage().toString();
        String description = webRequest.getDescription(false);

        return getResponseEntity(errorDate, message, description, HttpStatus.BAD_REQUEST);
    }
    private ResponseEntity<Object> getResponseEntity(Date errorDate, String message, String description, HttpStatus internalServerError) {
        ExceptionResponse genExceptionResponse = new ExceptionResponse(errorDate, message, description);

        RestResponse<ExceptionResponse> restResponse = RestResponse.error(genExceptionResponse);

        ResponseEntity<RestResponse<ExceptionResponse>> response = new ResponseEntity<>(restResponse, internalServerError);


        return new ResponseEntity<>(restResponse, internalServerError);
    }

    private String getObjectString(Object object) {
        String errorMessageBody = "";
        try {
            ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
            errorMessageBody = objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {

        }
        return errorMessageBody;
    }


}

