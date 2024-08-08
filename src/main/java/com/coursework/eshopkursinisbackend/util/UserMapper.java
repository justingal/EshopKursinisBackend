package com.coursework.eshopkursinisbackend.util;

import com.coursework.eshopkursinisbackend.dto.AdminDTO;
import com.coursework.eshopkursinisbackend.dto.BaseUserDTO;
import com.coursework.eshopkursinisbackend.dto.CustomerDTO;
import com.coursework.eshopkursinisbackend.dto.ManagerDTO;
import com.coursework.eshopkursinisbackend.model.Admin;
import com.coursework.eshopkursinisbackend.model.Customer;
import com.coursework.eshopkursinisbackend.model.Manager;
import com.coursework.eshopkursinisbackend.model.User;

public class UserMapper {

    public static BaseUserDTO toDTO(User user) {
        return switch (user) {
            case Admin admin -> toAdminDTO(admin);
            case Manager manager -> toManagerDTO(manager);
            case Customer customer -> toCustomerDTO(customer);
            case null, default -> {
                assert user != null;
                throw new IllegalArgumentException("Invalid user type: " + user.getClass().getSimpleName());
            }
        };
    }

    private static CustomerDTO toCustomerDTO(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        dto.setUsername(customer.getUsername());
        dto.setBirthDate(customer.getBirthDate());
        dto.setName(customer.getName());
        dto.setSurname(customer.getSurname());
        dto.setAddress(customer.getAddress());
        dto.setCardNo(customer.getCardNo());
        return dto;
    }

    private static ManagerDTO toManagerDTO(Manager manager) {
        ManagerDTO dto = new ManagerDTO();
        dto.setUsername(manager.getUsername());
        dto.setBirthDate(manager.getBirthDate());
        dto.setName(manager.getName());
        dto.setSurname(manager.getSurname());
        dto.setEmployeeId(manager.getEmployeeId());
        dto.setMedCertificate(manager.getMedCertificate());
        dto.setEmploymentDate(manager.getEmploymentDate());
        return dto;
    }

    private static AdminDTO toAdminDTO(Admin admin) {
        AdminDTO dto = new AdminDTO();
        dto.setUsername(admin.getUsername());
        dto.setBirthDate(admin.getBirthDate());
        dto.setName(admin.getName());
        dto.setSurname(admin.getSurname());
        dto.setEmployeeId(admin.getEmployeeId());
        dto.setMedCertificate(admin.getMedCertificate());
        dto.setEmploymentDate(admin.getEmploymentDate());
        return dto;
    }
}
