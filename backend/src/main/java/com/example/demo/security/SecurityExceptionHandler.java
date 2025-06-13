package com.example.demo.security;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class SecurityExceptionHandler {

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<Void> handleAccessDeniedException(HttpServletRequest req, AccessDeniedException ex) {
    log.error("Access Denied on URL: {}", req.getRequestURI(), ex);
    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
  }

  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<Void> handleAuthenticationException(HttpServletRequest req, AuthenticationException ex) {
    log.error("Authentication failed on URL: {}", req.getRequestURI(), ex);
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Void> handleGenericException(HttpServletRequest req, Exception ex) {
    log.error("Exception on URL: {}", req.getRequestURI(), ex);
    return ResponseEntity.internalServerError().build();
  }
}
