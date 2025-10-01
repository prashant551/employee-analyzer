package com.bigcompany.employeeanalyzer.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Employee {

    private final int id;

    private final String firstName;

    private final String lastName;

    private final double salary;

    private final Integer managerId;

    private final List<Employee> subordinates = new ArrayList<>();

    public Employee(int id, String firstName, String lastName, double salary, Integer managerId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.managerId = managerId;
    }


}
