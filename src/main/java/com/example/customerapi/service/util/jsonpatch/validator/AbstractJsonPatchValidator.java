package com.example.customerapi.service.util.jsonpatch.validator;

import com.example.customerapi.exception.custom.exception.AppInappropriateParamException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Objects;

import static com.example.customerapi.exception.custom.enums.CustomExceptionMessage.OP_NOT_SUPPORTED;

/**
 * Validates JsonPatch parameters.
 *
 * @author Olha Ryzhkova
 * @version 1.0
 */
@Component
@RequiredArgsConstructor
public abstract class AbstractJsonPatchValidator {

    private final ObjectMapper objectMapper;

    /**
     * Iterateі over elements of JsonPatch to validate parameters.
     *
     * @param patch data to update model properties.
     */
    public void validate(final JsonPatch patch) {
        final JsonNode jsonNodeFromPatch = objectMapper.convertValue(patch, JsonNode.class);

        final Iterator<JsonNode> elements = jsonNodeFromPatch.elements();

        while (elements.hasNext()) {
            final JsonNode node = elements.next();
            checkOperation(node);
            checkPathValue(node);
        }
    }

    /**
     * Checks field 'op'.
     *
     * @param node an element of JsonPatch.
     */
    public void checkOperation(final JsonNode node) {
        final String opValue = node.get("op").asText().toLowerCase();

        if (!Objects.equals(opValue, "replace")) {
            throw new AppInappropriateParamException(OP_NOT_SUPPORTED, opValue);
        }
    }

    /**
     * Checks value of field 'path'.
     *
     * @param node an element of JsonPatch.
     */
    public abstract void checkPathValue(final JsonNode node);
}
