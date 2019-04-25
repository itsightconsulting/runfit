package com.itsight.advice;

import com.itsight.domain.pojo.ApiError;
import com.itsight.domain.pojo.ApiSubError;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = "Malformed JSON request";
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, error, ex));
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = "Validation has failed";
        List<ApiSubError> errors = new ArrayList<>();
        for(FieldError x :  ex.getBindingResult().getFieldErrors()){
            errors.add(new ApiSubError(x.getField(), x.getObjectName(), x.getRejectedValue(), x.getDefaultMessage()));
        }
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, error, ex, errors));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = "Los argumentos enviados son inválidos";
        List<ApiSubError> errors = new ArrayList<>();
        for(FieldError x :  ex.getBindingResult().getFieldErrors()){
            errors.add(new ApiSubError(x.getField(), x.getObjectName(), x.getRejectedValue(), x.getDefaultMessage()));
        }
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, error, errors));
    }
/*@Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = "handleMissingServletRequestParameter";
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, error, ex));
        //return super.handleMissingServletRequestParameter(ex, headers, status, request);
    }*/
}
