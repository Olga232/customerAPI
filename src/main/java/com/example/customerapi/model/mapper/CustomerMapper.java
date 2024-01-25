package com.example.customerapi.model.mapper;

import com.example.customerapi.model.dto.CustomerDto;
import com.example.customerapi.model.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Objects;

/**
 * Maps Customer model within layers.
 *
 * @author Olha Ryzhkova
 * @version 1.0
 */
@Mapper(componentModel = "spring")
public interface CustomerMapper {

    /**
     * Maps CustomerDTO to Customer entity.
     *
     * @param customerDto {@link CustomerDto}
     * @return {@link Customer} entity.
     */
    Customer toEntity(final CustomerDto customerDto);

    /**
     * Maps Customer entity to CustomerDTO.
     *
     * @param customer {@link Customer} entity.
     * @return {@link CustomerDto}
     */
    @Mapping(target = "phone", source = "phone", qualifiedByName = "toDtoPhone")
    CustomerDto toDto(final Customer customer);

    /**
     * Maps list of Customer entities to list of CustomerDTOs.
     *
     * @param customerList list of {@link Customer} entities.
     * @return List of {@link CustomerDto}.
     */
    List<CustomerDto> toDtoList(final List<Customer> customerList);

    @Named(value = "toDtoPhone")
    default String toDtoPhone(final String customerPhone) {
        if (Objects.isNull(customerPhone)) {
            return "";
        }
        return customerPhone;
    }
}
