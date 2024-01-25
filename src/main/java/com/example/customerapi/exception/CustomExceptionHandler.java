package com.example.customerapi.exception;


import com.example.customerapi.exception.custom.exception.AppEntityNotFoundException;
import com.example.customerapi.model.dto.ErrorInfoApi;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Handles application exceptions.
 *
 * @author Olha Ryzhkova
 * @version 1.0
 */
@ControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ErrorInfoApi handleException(final HttpServletRequest req, final Exception ex) {
        log.error(ex.getMessage());

        return ErrorInfoApi.builder()
                           .url(req.getRequestURL().toString())
                           .exceptionMessage(ex.getMessage())
                           .build();
    }

    @ExceptionHandler(value = AppEntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    ErrorInfoApi handlePlanningSystemEntityNotFoundException(final HttpServletRequest req, final Exception ex) {
        log.error(ex.getMessage());

        return ErrorInfoApi.builder()
                           .url(req.getRequestURL().toString())
                           .exceptionMessage(ex.getMessage())
                           .build();
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ErrorInfoApi handleMethodArgumentNotValidException(final HttpServletRequest req,
                                                       final MethodArgumentNotValidException ex) {
        log.error(ex.getMessage());

        BindingResult bindingResult = ex.getBindingResult();
        StringBuilder sb = new StringBuilder("Validation failed: ");
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            sb.append(fieldError.getField())
              .append("：")
              .append(fieldError.getDefaultMessage())
              .append(", ");
        }
        String msg = sb.toString();

        return ErrorInfoApi.builder()
                           .url(req.getRequestURL().toString())
                           .exceptionMessage(msg)
                           .build();
    }
}