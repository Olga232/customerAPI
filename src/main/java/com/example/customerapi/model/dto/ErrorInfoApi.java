package com.example.customerapi.model.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * Model for communication with Client if any application exception occurs.
 *
 * @author Olha Ryzhkova
 * @version 1.0
 */
@Builder
@Getter
public class ErrorInfoApi {

    private final String url;
    private final String exceptionMessage;

}
