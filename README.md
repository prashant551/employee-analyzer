# employee-analyzer

A simple Java 11 command-line application that analyzes employee hierarchies from a CSV file and reports:

Underpaid managers: managers earning less than 20% above their team’s average.

Overpaid managers: managers earning more than 50% above their team’s average.

Employees with too long reporting lines: employees with more than 4 managers above them.

# Requirements

Java 11+

Maven 3.6+

# Build
mvn clean package

This generates the runnable JAR:
target/employee-analyzer-1.0-SNAPSHOT.jar

# Run

java -jar target/employee-analyzer-1.0-SNAPSHOT.jar employees.csv

# Testing

mvn test


