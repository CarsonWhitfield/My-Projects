package edu.montana.csci.csci440.helpers;

import edu.montana.csci.csci440.model.Employee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeHelper {

    public static String makeEmployeeTree() {
        Employee boss = Employee.find(1);
        Map<Long, List<Employee>> employeeMap = new HashMap<>();
        List<Employee> all = Employee.all();
        for (Employee emp : all) {
            List<Employee> employees = employeeMap.get(emp.getReportsTo());
            if (employees == null) {
                employees = new ArrayList<>();
                employeeMap.put(emp.getReportsTo(), employees);
            }
            employees.add(emp);
        }

        return "<ul>" + makeTree(boss, employeeMap) + "</ul>";
    }


    public static String makeTree(Employee employee, Map<Long, List<Employee>> employeeMap) {
        String list = "<li><a href='/employees/" + employee.getEmployeeId() + "'>"
                + employee.getEmail() + "</a><ul>";


        List<Employee> reports = employeeMap.get(employee.getEmployeeId());

        if (reports != null) {
            for (Employee report : reports) {
                list += makeTree(report, employeeMap);
            }
        }

        return list + "</ul></li>";
    }
}
