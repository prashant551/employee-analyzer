package com.bigcompany.employeeanalyzer.service;

import com.bigcompany.employeeanalyzer.model.Employee;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class EmployeeAnalyzer {

    // Managers earning less than required
    public static List<String> findUnderpaidManagers(Collection<Employee> employees) {
        List<String> result = new ArrayList<>();
        for (Employee m : employees) {
            if (!m.getSubordinates().isEmpty()) {
                double avg = m.getSubordinates().stream()
                        .mapToDouble(Employee::getSalary).average().orElse(0.0);
                double minRequired = avg * 1.2;
                if (m.getSalary() < minRequired) {
                    result.add(m.getFirstName()+" "+m.getLastName() + " earns " + (minRequired - m.getSalary()) + " less than required");
                }
            }
        }
        return result;
    }

    // Managers earning more than required
    public static List<String> findOverpaidManagers(Collection<Employee> employees) {
        List<String> result = new ArrayList<>();
        for (Employee m : employees) {
            if (!m.getSubordinates().isEmpty()) {
                double avg = m.getSubordinates().stream()
                        .mapToDouble(Employee::getSalary).average().orElse(0.0);
                double maxAllowed = avg * 1.5;
                if (m.getSalary() > maxAllowed) {
                    result.add(m.getFirstName()+" "+m.getLastName()  + " earns " + (m.getSalary() - maxAllowed) + " more than allowed");
                }
            }
        }
        return result;
    }

    // Employees with reporting line too long
    public static List<String> findTooDeepEmployees(Map<Integer, Employee> employees) {
        List<String> result = new ArrayList<>();
        for (Employee e : employees.values()) {
            int depth = getDepth(e, employees);
            if (depth > 4) {
                result.add(e.getFirstName()+" "+e.getLastName()  + " has " + depth + " managers above (limit 4)");
            }
        }
        return result;
    }

    private static int getDepth(Employee e, Map<Integer, Employee> employees) {
        int depth = 0;
        Integer managerId = e.getManagerId();
        while (managerId != null) {
            depth++;
            Employee manager = employees.get(managerId);
            managerId = manager != null ? manager.getManagerId() : null;
        }
        return depth;
    }
}
