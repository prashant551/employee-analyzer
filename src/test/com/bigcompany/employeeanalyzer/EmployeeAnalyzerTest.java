package com.bigcompany.employeeanalyzer;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.bigcompany.employeeanalyzer.model.Employee;
import com.bigcompany.employeeanalyzer.service.EmployeeAnalyzer;



import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

class EmployeeAnalyzerTest {

    @Test
    void testUnderpaidManager() {
        Employee manager = new Employee(1, "Joe", "Boss", 48000, null); // underpaid
        Employee e1 = new Employee(2, "Bob", "Emp", 40000, 1);
        Employee e2 = new Employee(3, "Alice", "Emp", 42000, 1);

        manager.getSubordinates().addAll(Arrays.asList(e1, e2));

        List<String> result = EmployeeAnalyzer.findUnderpaidManagers(Arrays.asList(manager, e1, e2));

        assertFalse(result.isEmpty(), "Manager should be flagged as underpaid");
        assertTrue(result.get(0).contains("Joe Boss"));
    }

    @Test
    void testManagerNotUnderpaid() {
        Employee manager = new Employee(1, "Joe", "Boss", 50000, null); // not underpaid
        Employee e1 = new Employee(2, "Bob", "Emp", 40000, 1);
        Employee e2 = new Employee(3, "Alice", "Emp", 42000, 1);

        manager.getSubordinates().addAll(Arrays.asList(e1, e2));

        List<String> result = EmployeeAnalyzer.findUnderpaidManagers(Arrays.asList(manager, e1, e2));

        assertTrue(result.isEmpty(), "Manager should NOT be flagged as underpaid");
    }

    @Test
    void testOverpaidManager() {
        Employee manager = new Employee(1, "Jane", "Boss", 100000, null); // clearly overpaid
        Employee e1 = new Employee(2, "Bob", "Emp", 40000, 1);
        Employee e2 = new Employee(3, "Alice", "Emp", 42000, 1);

        manager.getSubordinates().addAll(Arrays.asList(e1, e2));

        List<String> result = EmployeeAnalyzer.findOverpaidManagers(Arrays.asList(manager, e1, e2));

        assertFalse(result.isEmpty(), "Manager should be flagged as overpaid");
        assertTrue(result.get(0).contains("Jane Boss"), "Output should contain manager name");
        assertTrue(result.get(0).contains("more than allowed"), "Output should explain overpaid status");
    }


    @Test
    void testTooDeepEmployee() {
        Employee ceo = new Employee(1, "CEO", "One", 100000, null);
        Employee m1 = new Employee(2, "M1", "L1", 90000, 1);
        Employee m2 = new Employee(3, "M2", "L2", 80000, 2);
        Employee m3 = new Employee(4, "M3", "L3", 70000, 3);
        Employee m4 = new Employee(5, "M4", "L4", 60000, 4);
        Employee e   = new Employee(6, "Deep", "Emp", 50000, 5);

        Map<Integer, Employee> map = Map.of(1, ceo, 2, m1, 3, m2, 4, m3, 5, m4, 6, e);

        List<String> result = EmployeeAnalyzer.findTooDeepEmployees(map);

        assertFalse(result.isEmpty(), "Result should not be empty");
        assertTrue(result.get(0).contains("Deep Emp"), "Result should mention employee name");
        assertTrue(result.get(0).contains("5 managers above"), "Result should include depth");
    }
}
