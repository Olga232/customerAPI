package com.example.customerapi.service.util.jsonpatch.converter;

import com.example.customerapi.exception.custom.exception.AppInappropriateParamException;
import com.example.customerapi.service.util.jsonpatch.validator.AbstractJsonPatchValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.example.customerapi.exception.custom.enums.CustomExceptionMessage.JSONPATCH_EXCEPTION;

/**
 * Abstract class to apply JsonPatch to a model.
 *
 * @author Olha Ryzhkova
 * @version 1.0
 */
@Component
@RequiredArgsConstructor
public abstract class AbstractJsonPatchConverter<T> {

    private final AbstractJsonPatchValidator jsonPatchValidator;
    private final ObjectMapper objectMapper;
    private final Class<T> clazz;

    /**
     * Applies JsonPatch to model.
     *
     * @param patch data to update model properties
     * @param model model, that should be updated
     * @return model of model with updated properties
     */
    public T jsonPatchToModel(final JsonPatch patch, final T model) {
        jsonPatchValidator.validate(patch);

        try {
            final JsonNode jsonNode = objectMapper.convertValue(model, JsonNode.class);

            final JsonNode patchedJsonNode = patch.apply(jsonNode);

            return objectMapper.treeToValue(patchedJsonNode, clazz);
        } catch (JsonPatchException | JsonProcessingException e) {
            throw new AppInappropriateParamException(JSONPATCH_EXCEPTION, e.getMessage());
        }
    }
}
