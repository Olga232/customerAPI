package com.example.customerapi.service.util.jsonpatch.converter;

import com.example.customerapi.model.entity.Customer;
import com.example.customerapi.service.util.jsonpatch.validator.CustomerJsonPatchValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

/**
 * Applies JsonPatch to customer model.
 *
 * @author Olha Ryzhkova
 * @version 1.0
 */
@Component
public class CustomerJsonPatchConverter extends AbstractJsonPatchConverter<Customer>{

    public CustomerJsonPatchConverter(final CustomerJsonPatchValidator patchValidator,
                                      final ObjectMapper objectMapper) {
        super(patchValidator, objectMapper, Customer.class);
    }
}
