package com.coursework.eshopkursinisbackend.dto;

import com.coursework.eshopkursinisbackend.model.CustomerOrder;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class ManagerDTO extends BaseUserDTO {
    @NotBlank(message = "Employee ID is required")
    private String employeeId;

    @NotBlank(message = "Medical Certificate is required")
    private String medCertificate;

    @NotNull(message = "Employment date is required")
    private LocalDate employmentDate;

    private List<CustomerOrder> managedOrders;
}
