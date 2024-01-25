package com.example.customerapi.exception.custom.exception;

import com.example.customerapi.exception.custom.enums.CustomExceptionMessage;

/**
 * Thrown to indicate that inappropriate argument was passed to a method.
 *
 * @author Olha Ryzhkova
 * @version 1.0
 */
public class AppInappropriateParamException extends RuntimeException{

    public AppInappropriateParamException(final CustomExceptionMessage message, final Object... param) {
        super(String.format(message.getText(), param));
    }
}
