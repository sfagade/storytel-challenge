package com.app.storytel.challenge.exceptions;

import com.app.storytel.challenge.payload.response.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

/**
 * Custom exception handler class. This would ideally be used to perform domain
 * specific logic when certain exceptions occur. I've put this in here to
 * exhibition purposes
 *
 * @author samsonfagade
 */
@Slf4j
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleAnyException(Exception ex,
            WebRequest request) {
        log.error("Handling exception: {}", ex.getMessage());
        String errorMessageDescription = ex.getLocalizedMessage();

        if (errorMessageDescription == null) {
            errorMessageDescription = ex.toString();
        }

        ErrorMessage errorMessage
                = new ErrorMessage(errorMessageDescription, new Date());

        return new ResponseEntity<>(errorMessage, new HttpHeaders(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {NullPointerException.class})
    public ResponseEntity<Object> handleNullPointerException(NullPointerException ex,
            WebRequest request) {
        log.error("Handling null-pointer-exception: {}", ex.getMessage());
        String errorMessageDescription = ex.getLocalizedMessage();

        if (errorMessageDescription == null) {
            errorMessageDescription = ex.toString();
        }

        ErrorMessage errorMessage
                = new ErrorMessage(errorMessageDescription, new Date());

        return new ResponseEntity<>(errorMessage, new HttpHeaders(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
