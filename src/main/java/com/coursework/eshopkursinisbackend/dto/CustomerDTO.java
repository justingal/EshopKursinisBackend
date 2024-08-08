package com.coursework.eshopkursinisbackend.dto;

import com.coursework.eshopkursinisbackend.model.CustomerOrder;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CustomerDTO extends BaseUserDTO {
    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "Card number is required")
    private String cardNo;

    private List<CustomerOrder> userCustomerOrder;
}
