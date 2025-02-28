package tapis.cama.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import cama.otp.mock.generate.dto.ErrorInfo;
import tapis.cama.api.exceptions.BadCredentialsException;
import tapis.cama.api.exceptions.InvalidOtpCodeException;
import tapis.cama.api.exceptions.StorageException;

@Slf4j
@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler({BadCredentialsException.class})
    protected ResponseEntity<ErrorInfo> handleBadCredentialsException(BadCredentialsException ex) {
        log.error("Unable to authenticate: {}", ex);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorInfo(401, "UNAUTHENTICATED", "Request not authenticated due to missing, invalid, or expired credentials"));
    }

    @ExceptionHandler({StorageException.class})
    protected ResponseEntity<ErrorInfo> handleGenericException(StorageException ex) {
        log.error("Unable to process request: {}", ex);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorInfo(500, "INTERNAL", " Server error"));
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ResponseEntity<ErrorInfo> handleException(InvalidOtpCodeException ex) {
        log.error("Unable to validate code: {}", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorInfo(400, "ONE_TIME_PASSWORD_SMS.INVALID_OTP", "The provided OTP is not valid for this authenticationId"));
    }
}
