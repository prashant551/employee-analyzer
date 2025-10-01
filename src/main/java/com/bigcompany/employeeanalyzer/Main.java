package com.bigcompany.employeeanalyzer;

import com.bigcompany.employeeanalyzer.model.Employee;
import com.bigcompany.employeeanalyzer.service.EmployeeAnalyzer;
import com.bigcompany.employeeanalyzer.util.CsvReader;

import java.util.Map;

public class Main {

	public static void main(String[] args) throws Exception {
		if (args.length != 1) {
			System.err.println("Usage: java -jar employee-analyzer.jar <employees.csv>");
			System.exit(1);
		}

		Map<Integer, Employee> employees = CsvReader.readEmployees(args[0]);

		System.out.println("Managers underpaid:");
		EmployeeAnalyzer.findUnderpaidManagers(employees.values()).forEach(System.out::println);

		System.out.println("\nManagers overpaid:");
		EmployeeAnalyzer.findOverpaidManagers(employees.values()).forEach(System.out::println);

		System.out.println("\nEmployees with too long reporting line:");
		EmployeeAnalyzer.findTooDeepEmployees(employees).forEach(System.out::println);
	}

}
