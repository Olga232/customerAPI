package com.example.customerapi.service.impl;

import com.example.customerapi.exception.custom.exception.AppEntityNotFoundException;
import com.example.customerapi.exception.custom.exception.AppInappropriateParamException;
import com.example.customerapi.model.dto.CustomerDto;
import com.example.customerapi.model.entity.Customer;
import com.example.customerapi.model.mapper.CustomerMapper;
import com.example.customerapi.repository.CustomerRepository;
import com.example.customerapi.service.util.jsonpatch.converter.CustomerJsonPatchConverter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.example.customerapi.exception.custom.enums.CustomExceptionMessage.EMAIL_ALREADY_EXISTS;
import static com.example.customerapi.exception.custom.enums.CustomExceptionMessage.ENTITY_NOT_FOUND_BY;
import static constants.CustomerConst.CUSTOMER;
import static constants.CustomerConst.CUSTOMER_DTO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link CustomerServiceImpl}
 *
 * @author Olha Ryzhkova
 * @version 1.0
 */
@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @InjectMocks
    private CustomerServiceImpl unit;
    @Mock
    private CustomerRepository mockCustomerRepository;
    @Mock
    private CustomerMapper mockCustomerMapper;
    @Mock
    private CustomerJsonPatchConverter mockCustomerJsonPatchConverter;

    @Test
    void createCustomer_nominal() {
        //GIVEN
        when(mockCustomerRepository.findEmail(CUSTOMER_DTO.getEmail())).thenReturn(null);
        when(mockCustomerMapper.toEntity(CUSTOMER_DTO)).thenReturn(CUSTOMER);
        when(mockCustomerRepository.save(CUSTOMER)).thenReturn(CUSTOMER);
        when(mockCustomerMapper.toDto(CUSTOMER)).thenReturn(CUSTOMER_DTO);

        //WHEN
        final CustomerDto result = unit.createCustomer(CUSTOMER_DTO);

        //THEN
        assertThat(result)
                .isNotNull()
                .isEqualTo(CUSTOMER_DTO);
        verify(mockCustomerRepository).findEmail(CUSTOMER_DTO.getEmail());
        verify(mockCustomerMapper).toEntity(CUSTOMER_DTO);
        verify(mockCustomerRepository).save(CUSTOMER);
        verify(mockCustomerMapper).toDto(CUSTOMER);
        verifyNoMoreInteractions(mockCustomerRepository);
        verifyNoMoreInteractions(mockCustomerMapper);
    }

    @Test
    void createCustomer_throws_exception_if_customer_exists() {
        //GIVEN
        when(mockCustomerRepository.findEmail(CUSTOMER_DTO.getEmail())).thenReturn(CUSTOMER_DTO.getEmail());

        //WHEN
        final Executable executableTask = () -> unit.createCustomer(CUSTOMER_DTO);

        //THEN
        final var exception =
                assertThrows(AppInappropriateParamException.class, executableTask);
        assertEquals(exception.getMessage(),
                String.format(EMAIL_ALREADY_EXISTS.getText(), CUSTOMER_DTO.getEmail()));
        verify(mockCustomerRepository).findEmail(CUSTOMER_DTO.getEmail());
        verify(mockCustomerMapper, never()).toEntity(CUSTOMER_DTO);
        verify(mockCustomerRepository, never()).save(CUSTOMER);
        verify(mockCustomerMapper, never()).toDto(CUSTOMER);
        verifyNoMoreInteractions(mockCustomerRepository);
        verifyNoMoreInteractions(mockCustomerMapper);
    }

    @Test
    void getAllCustomers_nominal() {
        //GIVEN
        final List<Customer> list = List.of(CUSTOMER);
        final List<CustomerDto> dtoList = List.of(CUSTOMER_DTO);
        when(mockCustomerRepository.findAll()).thenReturn(list);
        when(mockCustomerMapper.toDtoList(list)).thenReturn(dtoList);

        //WHEN
        final List<CustomerDto> result = unit.getAllCustomers();

        //THEN
        assertThat(result)
                .isNotEmpty()
                .hasSize(1)
                .contains(CUSTOMER_DTO);
        verify(mockCustomerRepository).findAll();
        verify(mockCustomerMapper).toDtoList(list);
        verifyNoMoreInteractions(mockCustomerRepository);
        verifyNoMoreInteractions(mockCustomerMapper);
    }

    @Test
    void getCustomerById_nominal() {
        //GIVEN
        when(mockCustomerRepository.getCustomerById(CUSTOMER_DTO.getId())).thenReturn(Optional.of(CUSTOMER));
        when(mockCustomerMapper.toDto(CUSTOMER)).thenReturn(CUSTOMER_DTO);

        //WHEN
        final CustomerDto result = unit.getCustomerById(CUSTOMER_DTO.getId().toString());

        //THEN
        assertThat(result)
                .isNotNull()
                .isEqualTo(CUSTOMER_DTO);
        verify(mockCustomerRepository).getCustomerById(CUSTOMER_DTO.getId());
        verify(mockCustomerMapper).toDto(CUSTOMER);
        verifyNoMoreInteractions(mockCustomerRepository);
        verifyNoMoreInteractions(mockCustomerMapper);
    }

    @Test
    void getCustomerById_throws_exception_if_customer_not_found() {
        //GIVEN
        when(mockCustomerRepository.getCustomerById(CUSTOMER_DTO.getId())).thenReturn(Optional.empty());

        //WHEN
        final Executable executableTask = () -> unit.getCustomerById(CUSTOMER_DTO.getId().toString());

        //THEN
        final var exception =
                assertThrows(AppEntityNotFoundException.class, executableTask);
        assertEquals(exception.getMessage(),
                String.format(ENTITY_NOT_FOUND_BY.getText(),
                        Customer.class.getSimpleName(), "id", CUSTOMER_DTO.getId().toString()));
        verify(mockCustomerRepository).getCustomerById(CUSTOMER_DTO.getId());
        verify(mockCustomerMapper, never()).toDto(CUSTOMER);
        verifyNoMoreInteractions(mockCustomerRepository);
        verifyNoMoreInteractions(mockCustomerMapper);
    }

    @Test
    void deleteCustomer_nominal() {
        //GIVEN
        final Customer customer = CUSTOMER.toBuilder().isActive(Boolean.TRUE).build();
        when(mockCustomerRepository.getCustomerById(CUSTOMER_DTO.getId())).thenReturn(Optional.of(customer));
        when(mockCustomerRepository.save(customer)).thenReturn(customer);

        //WHEN
        unit.deleteCustomer(CUSTOMER_DTO.getId().toString());

        //THEN
        assertFalse(customer.getIsActive());
        verify(mockCustomerRepository).getCustomerById(CUSTOMER_DTO.getId());
        verify(mockCustomerRepository).save(customer);
    }

    @Test
    void deleteCustomer_throws_exception_if_customer_not_found() {
        //GIVEN
        when(mockCustomerRepository.getCustomerById(CUSTOMER_DTO.getId())).thenReturn(Optional.empty());

        //WHEN
        final Executable executableTask = () -> unit.deleteCustomer(CUSTOMER_DTO.getId().toString());

        //THEN
        final var exception =
                assertThrows(AppEntityNotFoundException.class, executableTask);
        assertEquals(exception.getMessage(),
                String.format(ENTITY_NOT_FOUND_BY.getText(),
                        Customer.class.getSimpleName(), "id", CUSTOMER_DTO.getId().toString()));
        verify(mockCustomerRepository).getCustomerById(CUSTOMER_DTO.getId());
        verify(mockCustomerRepository, never()).save(any(Customer.class));
        verifyNoMoreInteractions(mockCustomerRepository);
    }
}