package com.example.demo.exception;

import com.example.demo.entity.User;
import com.example.demo.service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    CustomUserDetailService customUserDetailService;

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
                                                                          HttpHeaders headers, HttpStatus status, WebRequest request) {
        String message = RestApiErrorMessages.MISSING_PARAMETER_MESSAGE + ex.getParameterName();
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        String message = RestApiErrorMessages.VALIDATION_ERROR + ex.getMessage();
        return new ResponseEntity<>(message, BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers,
                                                               HttpStatus status, WebRequest request) {
        String message = RestApiErrorMessages.MISSING_PARAMETER_MESSAGE + ex.getVariableName();
        return new ResponseEntity<>(message, BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
                                                                   HttpStatus status, WebRequest request) {
        String message = String.format(RestApiErrorMessages.NOT_FOUND_MESSAGE, ex.getHttpMethod(), ex.getRequestURL());
        return new ResponseEntity<>(message, BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex) {

        StringBuilder message = new StringBuilder("FORBIDDEN, login with ROLE_XXX\n");
        Optional<User> user = customUserDetailService.getCurrentUser();
        message.append(RestApiErrorMessages.USER_ACCESS_FORBIDDEN);

        if (user.isPresent()) {
            message.append("\nuserName: " + user.get().getUsername() + "\n");
            user.get().getRoles().forEach(message::append);
        } else {
            message.append("\nuserName: anonymousUser\nROLE_ANONYMOUS");
        }
        return new ResponseEntity<>(message.toString(), FORBIDDEN);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    protected ResponseEntity<Object> handleProductNotFoundException(ProductNotFoundException ex) {
        String message = RestApiErrorMessages.PRODUCT_NOT_FOUND + ex.getMessage();
        return new ResponseEntity<>(message, NOT_FOUND);
    }

    @ExceptionHandler(InternalServerException.class)
    protected ResponseEntity<Object> handleInternalServerException(InternalServerException ex) {
        String message = RestApiErrorMessages.INTERNAL_SERVER_ERROR + ex.getMessage();
        return new ResponseEntity<>(message, INTERNAL_SERVER_ERROR);
    }

}