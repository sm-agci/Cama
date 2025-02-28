//package tapis.gmlc.mock.controller.responses;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import tapis.gmlc.mock.exceptions.*;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//class RestExceptionHandlerTest {
//    private final RestExceptionHandler handler = new RestExceptionHandler();
//
//    @Test
//    void handle_invalid_credentials() {
//        ResponseEntity<ErrorMessage> response = handler.handleException(new InvalidCredentialsException());
//        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
//        assertThat(response.getHeaders().get(HttpHeaders.CONTENT_TYPE)).contains("application/json");
//        assertThat(response.getBody().toString()).isEqualTo("ErrorMessage(code=41, message=Invalid credentials, description=The requested service needs credentials, but none were provided.)");
//    }
//
//    @Test
//    void handle_missing_credentials() {
//        ResponseEntity<ErrorMessage> response = handler.handleMissingCredentialsException(new MissingCredentialsException());
//        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
//        assertThat(response.getHeaders().get(HttpHeaders.CONTENT_TYPE)).contains("application/json");
//        assertThat(response.getBody().toString()).isEqualTo("ErrorMessage(code=40, message=Missing credentials, description=The requested service needs credentials, but none were provided.)");
//    }
//
//    @Test
//    void handle_invalid_msisdn_format() {
//        ResponseEntity<ErrorMessage> response = handler.handleInvalidMsisdnFormatException(new InvalidMsisdnFormatException());
//        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
//        assertThat(response.getHeaders().get(HttpHeaders.CONTENT_TYPE)).contains("application/json");
//        assertThat(response.getBody().toString()).isEqualTo("ErrorMessage(code=31, message=Wrong parameter format, description=Parameter MSISDN consists of 11 digits: CountryCode + 9 digits)");
//    }
//
//    @Test
//    void handle_missing_msisdn() {
//        ResponseEntity<ErrorMessage> response = handler.handleMissingMsisdnException(new MissingMsisdnException());
//        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
//        assertThat(response.getHeaders().get(HttpHeaders.CONTENT_TYPE)).contains("application/json");
//        assertThat(response.getBody().toString()).isEqualTo("ErrorMessage(code=30, message=Missing parameters, description=Parameter MSISDN is required)");
//    }
//
//    @Test
//    void handle_bad_request() {
//        ResponseEntity<ErrorMessage> response = handler.handleBadRequestException(new BadRequestException());
//        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
//        assertThat(response.getHeaders().get(HttpHeaders.CONTENT_TYPE)).contains("application/json");
//        assertThat(response.getBody().toString()).isEqualTo("ErrorMessage(code=31, message=Wrong parameter format, description=Parameter MSISDN consists of 11 digits: CountryCode + 9 digits)");
//    }
//
//    @Test
//    void handle_forbidden() {
//        ResponseEntity<ErrorMessage> response = handler.handleForbiddenException(new ForbiddenException());
//        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.FORBIDDEN.value());
//        assertThat(response.getHeaders().get(HttpHeaders.CONTENT_TYPE)).contains("application/json");
//        assertThat(response.getBody().toString()).isEqualTo("ErrorMessage(code=50, message=Access denied, description=The application that makes the request is not authorized to access this endpoint)");
//    }
//
//    @Test
//    void handle_internal_server_error() {
//        ResponseEntity<ErrorMessage> response = handler.handleInternalServerErrorException(new InternalServerErrorException());
//        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
//        assertThat(response.getHeaders().get(HttpHeaders.CONTENT_TYPE)).contains("application/json");
//        assertThat(response.getBody()).isNull();
//    }
//
//    @Test
//    void handle_method_not_allowed() {
//        ResponseEntity<ErrorMessage> response = handler.handleMethodNotAllowedException(new MethodNotAllowedException());
//        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.METHOD_NOT_ALLOWED.value());
//        assertThat(response.getHeaders().get(HttpHeaders.CONTENT_TYPE)).contains("application/json");
//        assertThat(response.getBody().toString()).isEqualTo("ErrorMessage(code=61, message=Method not allowed, description=The URI does not support the requested method)");
//    }
//
//    @Test
//    void handle_not_found() {
//        ResponseEntity<ErrorMessage> response = handler.handleNotFoundException(new NotFoundException());
//        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
//        assertThat(response.getHeaders().get(HttpHeaders.CONTENT_TYPE)).contains("application/json");
//        assertThat(response.getBody().toString()).isEqualTo("ErrorMessage(code=60, message=Resource not found, description=The requested resource does not exist)");
//    }
//
//    @Test
//    void handle_service_unavailable() {
//        ResponseEntity<ErrorMessage> response = handler.handleServiceUnavailableException(new ServiceUnavailableException());
//        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.SERVICE_UNAVAILABLE.value());
//        assertThat(response.getHeaders().get(HttpHeaders.CONTENT_TYPE)).contains("application/json");
//        assertThat(response.getBody()).isNull();
//    }
//}