package com.coursework.eshopkursinisbackend.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@Entity
@DiscriminatorValue("Admin")
public class Admin extends Manager {
    public Admin(String employeeId, String medCertificate, LocalDate employmentDate) {
        super(employeeId, medCertificate, employmentDate);
    }

    public Admin(String username, String password, LocalDate birthDate, String name, String surname) {
        super(username, password, birthDate, name, surname);
    }

    public Admin(String username, String password, LocalDate birthDate, String name, String surname, String employeeId, String medCertificate, LocalDate employmentDate) {
        super(username, password, birthDate, name, surname, employeeId, medCertificate, employmentDate);
    }

    public Admin() {
        super();
    }
}