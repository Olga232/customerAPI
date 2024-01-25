package com.example.customerapi.exception.custom.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enumeration of custom exception messages.
 *
 * @author Olha Ryzhkova
 * @version 1.0
 */
@AllArgsConstructor
@Getter
public enum CustomExceptionMessage {

    ENTITY_NOT_FOUND_BY("%s not found by %s=%s"),
    EMAIL_ALREADY_EXISTS("Email '%s' already exists"),
    ID_NOT_NUMBER("ID='%s' is not a number"),

    // Related to JsonPatchValidation:
    JSONPATCH_EXCEPTION("Exception related to JsonPatch: "),
    OP_NOT_SUPPORTED("Operation %s is not supported"),
    PATH_NOT_VALID("Not valid path: %s"),
    FULL_NAME_VALUE_NOT_VALID("Not valid value of fullName: %s"),
    PHONE_VALUE_NOT_VALID("Not valid value of phone: %s");

    private final String text;
}
