package com.bigcompany.employeeanalyzer.util;

import com.bigcompany.employeeanalyzer.model.Employee;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class CsvReader {

    private static final int COL_ID = 0;
    private static final int COL_FIRST_NAME = 1;
    private static final int COL_LAST_NAME = 2;
    private static final int COL_SALARY = 3;
    private static final int COL_MANAGER_ID = 4;

    public static Map<Integer, Employee> readEmployees(String filePath) throws IOException {
        var employees = Files.lines(Path.of(filePath))
                .skip(1)
                .map(CsvReader::parseEmployee)
                .collect(Collectors.toMap(Employee::getId, e -> e));

        employees.values().forEach(e ->
                Optional.ofNullable(e.getManagerId())
                        .map(employees::get)
                        .ifPresent(manager -> manager.getSubordinates().add(e)));

        return employees;
    }

    private static Employee parseEmployee(String line) {
        var parts = line.split(",");
        var id = Integer.parseInt(parts[COL_ID].trim());
        var firstName = parts[COL_FIRST_NAME].trim();
        var lastName = parts[COL_LAST_NAME].trim();
        var salary = Double.parseDouble(parts[COL_SALARY].trim());
        Integer managerId = (parts.length > COL_MANAGER_ID && !parts[COL_MANAGER_ID].trim().isEmpty())
                ? Integer.parseInt(parts[COL_MANAGER_ID].trim())
                : null;

        return new Employee(id, firstName, lastName, salary, managerId);
    }
}
