package com.example.customerapi.service.impl;

import com.example.customerapi.exception.custom.exception.AppEntityNotFoundException;
import com.example.customerapi.exception.custom.exception.AppInappropriateParamException;
import com.example.customerapi.model.dto.CustomerDto;
import com.example.customerapi.model.entity.Customer;
import com.example.customerapi.model.mapper.CustomerMapper;
import com.example.customerapi.repository.CustomerRepository;
import com.example.customerapi.service.CustomerService;
import com.example.customerapi.service.util.jsonpatch.converter.CustomerJsonPatchConverter;
import com.github.fge.jsonpatch.JsonPatch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static com.example.customerapi.exception.custom.enums.CustomExceptionMessage.EMAIL_ALREADY_EXISTS;
import static com.example.customerapi.exception.custom.enums.CustomExceptionMessage.ENTITY_NOT_FOUND_BY;
import static com.example.customerapi.exception.custom.enums.CustomExceptionMessage.ID_NOT_NUMBER;

/**
 * Does business logic for Customer model.
 *
 * @author Olha Ryzhkova
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final CustomerJsonPatchConverter customerJsonPatchConverter;

    /**
     * Creates new customer.
     *
     * @param customerDto customer to create
     * @return created customer
     */
    @Override
    @Transactional
    public CustomerDto createCustomer(final CustomerDto customerDto) {
        final String emailFromDb = customerRepository.findEmail(customerDto.getEmail());

        if (!Objects.isNull(emailFromDb)) {
            throw new AppInappropriateParamException(EMAIL_ALREADY_EXISTS, customerDto.getEmail());
        }

        final Customer toCreate = customerMapper.toEntity(customerDto);
        final Customer created = customerRepository.save(toCreate);
        return customerMapper.toDto(created);
    }

    /**
     * Reads all customers.
     *
     * @return list of {@link CustomerDto}.
     */
    @Override
    public List<CustomerDto> getAllCustomers() {
        final List<Customer> customers = customerRepository.findAll();
        return customerMapper.toDtoList(customers);
    }

    /**
     * Reads the customer by the provided ID.
     *
     * @param id to get customer
     * @return {@link CustomerDto} by ID
     */
    @Override
    public CustomerDto getCustomerById(final String id) {
        final Long idL = parseStrToLong(id);

        final Customer customerById = customerRepository
                .getCustomerById(idL)
                .orElseThrow(() -> new AppEntityNotFoundException(
                        ENTITY_NOT_FOUND_BY, Customer.class.getSimpleName(), "id", id));

        return customerMapper.toDto(customerById);
    }

    /**
     * Update separate properties of existing customer.
     *
     * @param id    ID of an existing customer, that should be updated
     * @param patch data from request body to update customer properties
     * @return updated {@link CustomerDto}
     */
    @Override
    @Transactional
    public CustomerDto patchCustomer(final String id, final JsonPatch patch) {
        final Long idL = parseStrToLong(id);

        final Customer customerById = customerRepository
                .getCustomerById(idL)
                .orElseThrow(() -> new AppEntityNotFoundException(
                        ENTITY_NOT_FOUND_BY, Customer.class.getSimpleName(), "id", id));

        final Customer toUpdate = customerJsonPatchConverter.jsonPatchToModel(patch, customerById);

        final Customer updated = customerRepository.save(toUpdate);
        return customerMapper.toDto(updated);
    }

    /**
     * Marks a customer as deleted, but leave his data in DB.
     *
     * @param id customer ID to mark as deleted
     */
    @Override
    @Transactional
    public void deleteCustomer(final String id) {
        final Long idL = parseStrToLong(id);

        final Customer customerById = customerRepository
                .getCustomerById(idL)
                .orElseThrow(() -> new AppEntityNotFoundException(
                        ENTITY_NOT_FOUND_BY, Customer.class.getSimpleName(), "id", id));

        customerById.setIsActive(Boolean.FALSE);
        customerRepository.save(customerById);
    }

    private Long parseStrToLong(final String id) {
        final long idL;
        try {
            idL = Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new AppInappropriateParamException(ID_NOT_NUMBER, id);
        }
        return idL;
    }
}
