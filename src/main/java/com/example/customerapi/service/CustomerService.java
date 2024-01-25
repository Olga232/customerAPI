package com.example.customerapi.service;

import com.example.customerapi.model.dto.CustomerDto;
import com.github.fge.jsonpatch.JsonPatch;

import java.util.List;

/**
 * Does business logic for Customer model.
 *
 * @author Olha Ryzhkova
 * @version 1.0
 */
public interface CustomerService {

    /**
     * Creates new customer.
     *
     * @param customerDto customer to create
     * @return created {@link CustomerDto}
     */
    CustomerDto createCustomer(final CustomerDto customerDto);

    /**
     * Reads all customers.
     *
     * @return list of {@link CustomerDto}
     */
    List<CustomerDto> getAllCustomers();

    /**
     * Reads the customer by the provided ID.
     *
     * @param id to get customer
     * @return {@link CustomerDto} by ID
     */
    CustomerDto getCustomerById(final String id);

    /**
     * Update separate properties of existing customer.
     *
     * @param id ID of an existing customer, that should be updated
     * @param patch data from request body to update customer properties
     * @return updated {@link CustomerDto}
     */
    CustomerDto patchCustomer(final String id, final JsonPatch patch);

    /**
     * Marks a customer as deleted, but leave his data in DB.
     *
     * @param id customer ID to mark as deleted
     */
    void deleteCustomer(final String id);
}
