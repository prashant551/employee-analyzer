package com.bigcompany.employeeanalyzer.util;

import com.bigcompany.employeeanalyzer.model.Employee;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class CsvReader {

    public static Map<Integer, Employee> readEmployees(String filePath) throws Exception {
        Map<Integer, Employee> employees = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0].trim());
                String firstName = parts[1].trim();
                String lastName = parts[2].trim();
                double salary = Double.parseDouble(parts[3].trim());
                Integer managerId = parts.length > 4 && !parts[4].trim().isEmpty()
                        ? Integer.parseInt(parts[4].trim()) : null;

                employees.put(id, new Employee(id, firstName, lastName, salary, managerId));
            }
        }

        // Build hierarchy
        for (Employee e : employees.values()) {
            if (e.getManagerId() != null) {
                Employee manager = employees.get(e.getManagerId());
                if (manager != null) {
                    manager.getSubordinates().add(e);
                }
            }
        }
        return employees;
    }
}

