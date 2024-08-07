package com.coursework.eshopkursinisbackend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;


@Setter
@Getter
@Entity
public class Manager extends User {
    @OneToMany(mappedBy = "responsibleManager")
    private List<CustomerOrder> managedOrders;
    private String employeeId;
    private String medCertificate;
    private LocalDate employmentDate;

    public Manager() {

    }

    public Manager(String login, String password, LocalDate birthDate, String name, String surname) {
        super(login, password, birthDate, name, surname);
    }

    public Manager(String employeeId, String medCertificate, LocalDate employmentDate) {
        this.employeeId = employeeId;
        this.medCertificate = medCertificate;
        this.employmentDate = employmentDate;
    }

    public Manager(String login, String password, LocalDate birthDate, String name, String surname, String employeeId, String medCertificate, LocalDate employmentDate) {
        super(login, password, birthDate, name, surname);
        this.employeeId = employeeId;
        this.medCertificate = medCertificate;
        this.employmentDate = employmentDate;
    }

    public String toString() {
        return getFullName();
    }
}