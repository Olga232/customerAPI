package com.example.customerapi.repository;

import com.example.customerapi.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for {@link Customer} entity.
 *
 * @author Olha Ryzhkova
 * @version 1.0
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> getCustomerById(Long id);

    @Query(value = "SELECT c.email FROM Customer c where c.email = ?1")
    String findEmail(String email);
}
