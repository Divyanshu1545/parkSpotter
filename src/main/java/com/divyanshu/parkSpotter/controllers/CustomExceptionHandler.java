package com.divyanshu.parkSpotter.controllers;


import com.divyanshu.parkSpotter.dto.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import io.jsonwebtoken.security.SignatureException;

import java.nio.file.AccessDeniedException;
import java.sql.SQLException;

@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public ProblemDetail handleAccessDeniedException(AccessDeniedException ex) {
        log.warn("403 hit");
        if (ex instanceof AccessDeniedException) {
            ErrorResponse errorResponse = new ErrorResponse(
                    "Access denied. You do not have permission to access this resource.",
                    HttpStatus.FORBIDDEN.value(),
                    System.currentTimeMillis()
            );
            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), ex.getMessage());
            return problemDetail;
        } else {
            return ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), ex.getMessage());
        }
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ProblemDetail expiredJwtException(ExpiredJwtException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "JWT provided is already expired on:" +ex.getClaims().getExpiration().toString());
    }

    @ExceptionHandler(SignatureException.class)
    public  ProblemDetail invalidJwtException(SignatureException ex){
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage());
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail databaseException(DataIntegrityViolationException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), ex.getMessage());

    }
}