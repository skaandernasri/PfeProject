package tn.temporise.infrastructure.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import tn.temporise.application.exception.*;
import tn.temporise.domain.model.Response;

@ControllerAdvice(annotations = RestController.class)
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Response> handleBadRequestException(BadRequestException ex) {
        Response response = new Response();
        response.setCode(ex.getCode());
        response.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(PasswordException.class)
    public ResponseEntity<Response> handelPasswordException(PasswordException ex) {
        Response response = new Response();
        response.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    @ExceptionHandler(ProductNotFound.class)
    public ResponseEntity<Response> handelProductNotFound(ProductNotFound ex) {
        Response response = new Response();
        response.setMessage(ex.getMessage());
        response.setCode(ex.getCode());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Response> handelNotFoundException(NotFoundException ex) {
        Response response = new Response();
        response.setMessage(ex.getMessage());
        response.setCode(ex.getCode());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }



    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Response> handleUnauthorizedException(UnauthorizedException ex) {
        Response response = new Response();
        response.setCode(ex.getCode());
        response.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Response> handleAccessDeniedException(AccessDeniedException ex) {
        Response response = new Response();
        response.setCode("4003");
        response.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<Response> handleInternalServerErrorException(InternalServerErrorException ex) {
        Response response = new Response();
        response.setCode(ex.getCode());
        response.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(LogoutException.class)
    public ResponseEntity<Response> handleLogoutException(LogoutException ex) {
        Response response = new Response();
        response.setCode(ex.getCode());
        response.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(NonLocalProviderException.class)
    public ResponseEntity<Response> handleNonLocalProviderException(NonLocalProviderException ex) {
        Response response = new Response();
        response.setCode(ex.getCode());
        response.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<Response> handleConflictException(ConflictException ex) {

        Response response = new Response();
        response.setCode(ex.getCode());
        response.setMessage(ex.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Response> handleUsernameNotFoundException(UsernameNotFoundException ex) {

        Response response = new Response();
        response.setCode(ex.getCode());
        response.setMessage(ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Response> handleBadCredentialsException(BadCredentialsException ex) {

        Response response = new Response();
        response.setCode(ex.getCode());
        response.setMessage(ex.getMessage());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

}