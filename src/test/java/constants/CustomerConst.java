package constants;

import com.example.customerapi.model.dto.CustomerDto;
import com.example.customerapi.model.entity.Customer;

/**
 * Constants Customer models.
 *
 * @author Olha Ryzhkova
 * @version 1.0
 */
public class CustomerConst {

    public static final CustomerDto CUSTOMER_DTO;
    public static final Customer CUSTOMER;

    static {
        CUSTOMER_DTO = CustomerDto.builder()
                                  .id(1L)
                                  .fullName("Petro Petrenko")
                                  .email("mymail@mail.com")
                                  .phone("+12345678")
                                  .build();

        CUSTOMER = Customer.builder()
                           .id(1L)
                           .fullName("Petro Petrenko")
                           .email("mymail@mail.com")
                           .phone("+12345678")
                           .created(1706208990L)
                           .isActive(true)
                           .build();
    }
}
