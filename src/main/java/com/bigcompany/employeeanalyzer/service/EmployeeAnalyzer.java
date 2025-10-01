package com.bigcompany.employeeanalyzer.service;

import com.bigcompany.employeeanalyzer.model.Employee;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class EmployeeAnalyzer {

    public static List<String> findUnderpaidManagers(Collection<Employee> employees) {
        return employees.stream()
                .filter(m -> !m.getSubordinates().isEmpty())
                .map(m -> {
                    var avg = m.getSubordinates().stream()
                            .mapToDouble(Employee::getSalary)
                            .average()
                            .orElse(0.0);
                    var minRequired = avg * 1.2;
                    if (m.getSalary() < minRequired) {
                        return String.format("%s %s earns %.2f less than required",
                                m.getFirstName(), m.getLastName(), (minRequired - m.getSalary()));
                    }
                    return null;
                }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public static List<String> findOverpaidManagers(Collection<Employee> employees) {
        return employees.stream()
                .filter(m -> !m.getSubordinates().isEmpty())
                .map(m -> {
                    var avg = m.getSubordinates().stream()
                            .mapToDouble(Employee::getSalary)
                            .average()
                            .orElse(0.0);
                    var maxAllowed = avg * 1.5;
                    if (m.getSalary() > maxAllowed) {
                        return String.format("%s %s earns %.2f more than allowed",
                                m.getFirstName(), m.getLastName(), (m.getSalary() - maxAllowed));
                    }
                    return null;
                }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public static List<String> findTooDeepEmployees(Map<Integer, Employee> employees) {
        return employees.values().stream()
                .map(e -> {
                    var depth = getDepth(e, employees);
                    if (depth > 4) {
                        return String.format("%s %s has %d managers above (limit 4)",
                                e.getFirstName(), e.getLastName(), depth);
                    }
                    return null;
                }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    private static int getDepth(Employee e, Map<Integer, Employee> employees) {
        var depth = 0;
        var managerId = e.getManagerId();
        while (managerId != null) {
            depth++;
            var manager = employees.get(managerId);
            managerId = manager != null ? manager.getManagerId() : null;
        }
        return depth;
    }
}
