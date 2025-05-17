package edu.montana.csci.csci440.model;

import edu.montana.csci.csci440.util.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Customer extends Model {

    private Long customerId;
    private Long supportRepId;
    private String firstName;
    private String lastName;
    private String email;

    public Employee getSupportRep() {
         return Employee.find(supportRepId);
    }

    public List<Invoice> getInvoices() {
        List<Invoice> invoices = new ArrayList<>();
        try (Connection conn = DB.connect();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT * FROM invoices WHERE CustomerId = ?")) {
            stmt.setLong(1, this.customerId);  // Use the current customer's ID
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                invoices.add(new Invoice(resultSet)); // Pass ResultSet to Invoice constructor
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching invoices: ", e);
        }
        return invoices;
    }

    public Customer(){
    }
    private Customer(ResultSet results) throws SQLException {
        firstName = results.getString("FirstName");
        lastName = results.getString("LastName");
        customerId = results.getLong("CustomerId");
        supportRepId = results.getLong("SupportRepId");
        email = results.getString("Email");
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

    public Long getCustomerId() {
        return customerId;
    }

    public Long getSupportRepId() {
        return supportRepId;
    }

    public static List<Customer> all() {
        return all(0, Integer.MAX_VALUE);
    }

    public static List<Customer> all(int page, int count) {
        List<Customer> customers = new ArrayList<>();
        int offset = (page - 1) * count; // Calculate the offset for pagination

        try (Connection conn = DB.connect();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT * FROM Customers ORDER BY CustomerId LIMIT ? OFFSET ?")) {
            stmt.setInt(1, count);
            stmt.setInt(2, offset);

            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                customers.add(new Customer(resultSet)); // Create Customer instances from ResultSet
            }
        } catch (Exception e) {
            throw new RuntimeException("Error fetching customers: ", e);
        }

        return customers;
    }

    public static Customer find(long customerId) {
        try {
            try (Connection conn = DB.connect();
                 PreparedStatement stmt = conn.prepareStatement(
                         "SELECT * FROM Customers WHERE CustomerId = ?")) {
                stmt.setLong(1, customerId);
                ResultSet resultSet = stmt.executeQuery();
                if (resultSet.next()) {
                    return new Customer(resultSet);
                } else {
                    return null;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static List<Customer> forEmployee(long employeeId) {
        List<Customer> customers = new ArrayList<>();
        try (Connection conn = DB.connect();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT * FROM customers WHERE SupportRepId = ?")) {
            stmt.setLong(1, employeeId);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                customers.add(new Customer(resultSet));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return customers;
    }
}
