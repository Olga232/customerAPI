package com.example.customerapi.controller;

import com.example.customerapi.model.dto.CustomerDto;
import com.example.customerapi.service.CustomerService;
import com.github.fge.jsonpatch.JsonPatch;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Customer's API layer.
 *
 * @author Olha Ryzhkova
 * @version 1.0
 */
@RestController
@RequestMapping("/api/customers")
@AllArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    /**
     * Creates new customer.
     *
     * @param customerDto data from request body to create a customer
     * @return {@link CustomerDto} an object that contains created customer with all properties
     */
    @PostMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDto createCustomer(@RequestBody @Valid final CustomerDto customerDto) {
        return customerService.createCustomer(customerDto);
    }

    /**
     * Reads all customers.
     *
     * @return list of {@link CustomerDto}.
     */
    @GetMapping(produces = "application/json")
    public List<CustomerDto> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    /**
     * Reads the customer by the provided ID.
     *
     * @param id to get customer
     * @return {@link CustomerDto} by ID
     */
    @GetMapping(value = "/{id}", produces = "application/json")
    public CustomerDto getCustomerById(@PathVariable(name = "id") final String id) {
        return customerService.getCustomerById(id);
    }

    /**
     * Update separate properties of existing customer.
     *
     * @param id ID of an existing customer, that should be updated
     * @param patch data from request body to update customer properties
     * @return updated {@link CustomerDto}
     */
    @PatchMapping(value = "/{id}", produces = "application/json")
    public CustomerDto patchNameOrPhone(@PathVariable(name = "id") final String id,
                                        @RequestBody final JsonPatch patch) {
        return customerService.patchCustomer(id, patch);
    }

    /**
     * Marks a customer as deleted, but leave his data in DB.
     *
     * @param id customer ID to mark as deleted
     */
    @DeleteMapping(value = "/{id}")
    public void deleteCustomer(@PathVariable(name = "id") final String id) {
        customerService.deleteCustomer(id);
    }
}
