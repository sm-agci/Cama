package cama.api.controller;

import cama.api.exceptions.BadCredentialsException;
import cama.api.generate.dto.ErrorInfo;
import ch.qos.logback.core.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Slf4j
@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler({BadCredentialsException.class})
    protected ResponseEntity<ErrorInfo> handleBadCredentialsException(BadCredentialsException ex) {
        log.error("Unable to authenticate: {}", ex);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorInfo(401, "UNAUTHENTICATED", "Request not authenticated due to missing, invalid, or expired credentials"));
    }
    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<ErrorInfo> handleException(WebClientResponseException ex) {
        log.error("Unable to get CAMA OTP MOCK response, {}", ex.getMessage());
        ErrorInfo errorInfo = ex.getResponseBodyAs(ErrorInfo.class);
        if (errorInfo != null && !StringUtil.isNullOrEmpty(errorInfo.getCode())) {
            log.debug("Error body: {}", errorInfo);
            return ResponseEntity.status(ex.getStatusCode()).body(errorInfo);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorInfo(500, "INTERNAL", ex.getMessage()));
        }
    }
}
