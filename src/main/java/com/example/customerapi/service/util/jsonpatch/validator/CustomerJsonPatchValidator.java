package com.example.customerapi.service.util.jsonpatch.validator;

import com.example.customerapi.exception.custom.exception.AppInappropriateParamException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.example.customerapi.exception.custom.enums.CustomExceptionMessage.FULL_NAME_VALUE_NOT_VALID;
import static com.example.customerapi.exception.custom.enums.CustomExceptionMessage.PATH_NOT_VALID;
import static com.example.customerapi.exception.custom.enums.CustomExceptionMessage.PHONE_VALUE_NOT_VALID;
import static com.example.customerapi.model.dto.CustomerDto.FULL_NAME_REGEXP;
import static com.example.customerapi.model.dto.CustomerDto.PHONE_REGEXP;

/**
 * Validates JsonPatch parameters to update customer model.
 *
 * @author Olha Ryzhkova
 * @version 1.0
 */
@Component
public class CustomerJsonPatchValidator extends AbstractJsonPatchValidator{

    public CustomerJsonPatchValidator(final ObjectMapper objectMapper) {
        super(objectMapper);
    }

    /**
     * Checks value of field 'path'.
     *
     * @param node an element of JsonPatch.
     */
    @Override
    public void checkPathValue(final JsonNode node) {
        final String path = node.get("path").asText().toLowerCase();

        if (Objects.equals(path, "/fullname")) {
            checkFullNameValue(node);
        } else if (Objects.equals(path, "/phone")) {
            checkPhoneValue(node);
        } else {
            throw new AppInappropriateParamException(PATH_NOT_VALID, path);
        }
    }

    /**
     * Checks field 'value' if field 'path' is 'fullName'.
     *
     * @param node an element of JsonPatch.
     */
    private void checkFullNameValue(final JsonNode node) {
        final String value = node.get("value").asText();

        if (Objects.isNull(value)
                || !value.matches(FULL_NAME_REGEXP)) {
            throw new AppInappropriateParamException(FULL_NAME_VALUE_NOT_VALID, value);
        }
    }

    /**
     * Checks field 'value' if field 'path' is 'phone'.
     *
     * @param node an element of JsonPatch.
     */
    private void checkPhoneValue(final JsonNode node) {
        final String value = node.get("value").asText();

        if (!value.matches(PHONE_REGEXP)) {
            throw new AppInappropriateParamException(PHONE_VALUE_NOT_VALID, value);
        }
    }
}
