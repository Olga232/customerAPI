package com.example.customerapi.controller;

import com.example.customerapi.model.dto.CustomerDto;
import com.example.customerapi.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static constants.CustomerConst.CUSTOMER_DTO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void postCreateCustomer() throws Exception {
        //GIVEN
        final CustomerDto dto = CustomerDto.builder()
                                                .fullName("New Name")
                                                .email(Math.random() + "new@email.com")
                                                .phone("+1234567")
                                                .build();
        final String jsonBody = objectMapper.writeValueAsString(dto);
        final int initSize = customerService.getAllCustomers().size();

        //WHEN
        mockMvc.perform(
                post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
               .andExpect(status().isCreated());

        //THEN
        final List<CustomerDto> result = customerService.getAllCustomers();
        assertThat(result)
                .isNotEmpty()
                .hasSize(initSize + 1);
    }

    @Test
    void getAllCustomers() throws Exception {
        mockMvc.perform(get("/api/customers"))
               .andExpect(status().isOk());
    }

    @Test
    void getCustomerById() throws Exception {
        //GIVEN
        final CustomerDto created = createNewCustomer();

        //WHEN
        //THEN
        mockMvc.perform(get("/api/customers/" + created.getId()))
               .andExpect(status().isOk())
               .andExpect(content().json(objectMapper.writeValueAsString(created)));
    }

    @Test
    void deleteCustomer() throws Exception {
        //GIVEN
        final CustomerDto created = createNewCustomer();

        //WHEN
        mockMvc.perform(delete("/api/customers/" + created.getId()))
               .andExpect(status().isOk());

        //THEN
        mockMvc.perform(get("/api/customers/" + created.getId()))
               .andExpect(status().isOk())
               .andExpect(content().json(objectMapper.writeValueAsString(created)));
    }

    private CustomerDto createNewCustomer() throws Exception {
        final CustomerDto dto = CustomerDto.builder()
                                           .fullName("New Name")
                                           .email(Math.random() + CUSTOMER_DTO.getEmail())
                                           .phone("+1234567")
                                           .build();
        final String jsonRequestBody = objectMapper.writeValueAsString(dto);

        final var resultActions = mockMvc.perform(
                post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                                         .andExpect(status().isCreated());

        final String responseAsString = resultActions.andReturn()
                                                     .getResponse()
                                                     .getContentAsString();
        return objectMapper.readValue(responseAsString, CustomerDto.class);
    }
}