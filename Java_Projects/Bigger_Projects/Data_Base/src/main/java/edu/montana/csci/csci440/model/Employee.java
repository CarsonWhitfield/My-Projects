package edu.montana.csci.csci440.model;

import edu.montana.csci.csci440.util.DB;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Employee extends Model {

    private Long employeeId;
    private Long reportsTo;
    private String firstName;
    private String lastName;
    private String email;
    private String title;


    public Employee() {
        // new employee for insert
    }

    private Employee(ResultSet results) throws SQLException {
        firstName = results.getString("FirstName");
        lastName = results.getString("LastName");
        email = results.getString("Email");
        employeeId = results.getLong("EmployeeId");
        reportsTo = results.getLong("ReportsTo");
        title = results.getString("Title");
    }

    public static List<Employee.SalesSummary> getSalesSummaries() {
        List<Employee.SalesSummary> summaries = new ArrayList<>();
        String sql = "SELECT e.FirstName, e.LastName, e.Email, " +
                "COUNT(i.InvoiceId) AS SalesCount, " +
                "SUM(i.Total) AS SalesTotal " +
                "FROM employees e " +
                "JOIN customers c ON e.EmployeeId = c.SupportRepId " +
                "JOIN invoices i ON c.CustomerId = i.CustomerId " +
                "GROUP BY e.EmployeeId";

        try (Connection conn = DB.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                summaries.add(new SalesSummary(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching sales summaries", e);
        }
        return summaries;
    }

    @Override
    public boolean verify() {
        _errors.clear(); // Clear any existing errors

        // Validate first name
        if (firstName == null || "".equals(firstName)) {
            addError("FirstName can't be null or blank!");
        }

        // Validate last name
        if (lastName == null || "".equals(lastName)) {
            addError("LastName can't be null or blank!");
        }

        // Validate email
        if (email == null || "".equals(email)) {
            addError("Email can't be null or blank!");
        } else if (!email.contains("@")) {
            addError("Email must contain an '@' symbol!");
        }

        // Return true if no errors were added, false otherwise
        return !hasErrors();
    }

    public boolean  update() {
        if (verify()) {
            try (Connection conn = DB.connect();
                 PreparedStatement stmt = conn.prepareStatement(
                         "UPDATE employees SET FirstName=?, LastName=?, Email=? WHERE EmployeeId=?")) {
                stmt.setString(1, this.getFirstName());
                stmt.setString(2, this.getLastName());
                stmt.setString(3, this.getEmail());
                stmt.setLong(4, this.getEmployeeId());
                stmt.executeUpdate();
                return true;
            } catch (SQLException sqlException) {
                throw new RuntimeException(sqlException);
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean create() {
        if (verify()) {
            try (Connection conn = DB.connect();
                 PreparedStatement stmt = conn.prepareStatement(
                         "INSERT INTO employees (FirstName, LastName, Email, ReportsTo) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, this.getFirstName());
                stmt.setString(2, this.getLastName());
                stmt.setString(3, this.getEmail());

                // Check if ReportsTo is null before setting it
                if (this.getReportsTo() != null) {
                    stmt.setLong(4, this.getReportsTo());
                } else {
                    stmt.setNull(4, java.sql.Types.BIGINT); // Set as NULL if ReportsTo is null
                }

                stmt.executeUpdate();

                // Retrieve the auto-generated employeeId
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        this.employeeId = generatedKeys.getLong(1);
                    } else {
                        throw new SQLException("Creating employee failed, no ID obtained.");
                    }
                }

                return true;
            } catch (SQLException sqlException) {
                throw new RuntimeException("Error creating employee", sqlException);
            }
        } else {
            return false;
        }
    }


    @Override
    public void delete() {
        try (Connection conn = DB.connect();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM employees WHERE EmployeeId = ?")) {
            stmt.setLong(1, this.getEmployeeId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting employee", e);
        }
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public List<Customer> getCustomers() {
        return Customer.forEmployee(employeeId);
    }

    public Long getReportsTo() {
        return reportsTo;
    }

    public void setReportsTo(Long reportsTo) {
        this.reportsTo = reportsTo;
    }

    public  List<Employee> getReports() {
        try {
            try(Connection connect = DB.connect();
                PreparedStatement stmt = connect.prepareStatement("SELECT  * FROM employees WHERE ReportsTo = ?")) {
                ArrayList<Employee> result = new ArrayList<>();
                stmt.setLong(1, getEmployeeId());
                ResultSet resultSet = stmt.executeQuery();
                while (resultSet.next()) {
                    result.add(new Employee(resultSet));
                }
                return result;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Employee getBoss() {
        return Employee.find(reportsTo);
    }

    public static List<Employee> all() {
        return all(0, Integer.MAX_VALUE);
    }

    public static List<Employee> all(int page, int count) {
         try {
            try(Connection connect = DB.connect();
                PreparedStatement stmt = connect.prepareStatement("SELECT  * FROM employees LIMIT ? OFFSET ?")) {
                ArrayList<Employee> result = new ArrayList<>();
                int offset = (page -1) * count;
                stmt.setInt(1, count);
                stmt.setInt(2,offset);
                ResultSet resultSet = stmt.executeQuery();
                while (resultSet.next()) {
                    result.add(new Employee(resultSet));
                }
                return result;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Employee findByEmail(String newEmailAddress) {
        try (Connection conn = DB.connect();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT * FROM Employees WHERE Email = ?")) {
            stmt.setString(1, newEmailAddress);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                return new Employee(resultSet); // Return the Employee object
            } else {
                return null; // No employee found with that email
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Employee find(long employeeId) {
        try {
            try (Connection conn = DB.connect();
                 PreparedStatement stmt = conn.prepareStatement(
                         "SELECT * FROM Employees WHERE EmployeeId = ?")) {
                stmt.setLong(1, employeeId);
                ResultSet resultSet = stmt.executeQuery();
                if (resultSet.next()) {
                    return new Employee(resultSet);
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setTitle(String programmer) {
        title = programmer;
    }

    public void setReportsTo(Employee employee) {
        this.reportsTo = employee.getEmployeeId();
    }

    public static class SalesSummary {
        private String firstName;
        private String lastName;
        private String email;
        private Long salesCount;
        private BigDecimal salesTotals;

        private SalesSummary(ResultSet results) throws SQLException {
            firstName = results.getString("FirstName");
            lastName = results.getString("LastName");
            email = results.getString("Email");
            salesCount = results.getLong("SalesCount");
            salesTotals = results.getBigDecimal("SalesTotal");
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getEmail() {
            return email;
        }

        public Long getSalesCount() {
            return salesCount;
        }

        public BigDecimal getSalesTotals() {
            return salesTotals;
        }


    }
}
