package com.example.customerapi.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

/**
 * Customer DTO.
 *
 * @author Olha Ryzhkova
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class CustomerDto {

    // Validation constants: fullName
    public static final String FULL_NAME_REGEXP = "^(?=[a-zA-Z -]{2,50}$)[a-zA-Z]+([a-zA-Z -]+)*[a-zA-Z]+";
    private static final String FULL_NAME_CONSTRAINT = "must be 2-50 chars including whitespaces";
    // Validation constants: email
    private static final String EMAIL_REGEXP = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*" +
            "@[a-zA-Z0-9][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    private static final String EMAIL_LENGTH = "length must be 2-100 chars";
    private static final String EMAIL_CONSTRAINT = "must be valid, should include exactly one @";
    // Validation constants: phone
    public static final String PHONE_REGEXP = "^(?=[+0-9]{6,14}$)^\\+[0-9]{5,13}";
    private static final String PHONE_CONSTRAINT = "must be 6-14 chars, only digits, should start from +";

    private Long id;

    @NotNull
    @Pattern(regexp = FULL_NAME_REGEXP, message = FULL_NAME_CONSTRAINT)
    private String fullName;

    @NotNull
    @Length(min = 2, max = 100, message = EMAIL_LENGTH)
    @Pattern(regexp = EMAIL_REGEXP, message = EMAIL_CONSTRAINT)
    private String email;

    @Pattern(regexp = PHONE_REGEXP, message = PHONE_CONSTRAINT)
    private String phone;
}