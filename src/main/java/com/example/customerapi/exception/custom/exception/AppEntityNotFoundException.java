package com.example.customerapi.exception.custom.exception;

import com.example.customerapi.exception.custom.enums.CustomExceptionMessage;

/**
 * Thrown to indicate that entity is not found.
 *
 * @author Olha Ryzhkova
 * @version 1.0
 */
public class AppEntityNotFoundException extends RuntimeException {

    public AppEntityNotFoundException(final CustomExceptionMessage message, final Object... params) {
        super(String.format(message.getText(), params));
    }
}
