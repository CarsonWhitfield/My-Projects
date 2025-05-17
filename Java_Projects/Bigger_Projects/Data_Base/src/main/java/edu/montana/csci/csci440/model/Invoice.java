package edu.montana.csci.csci440.model;

import edu.montana.csci.csci440.util.DB;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Invoice extends Model {

    Long invoiceId;
    String billingAddress;
    String billingCity;
    String billingState;
    String billingCountry;
    String billingPostalCode;
    BigDecimal total;
    Long customerId;



    public Invoice() {
        // new employee for insert
    }

    public Invoice(ResultSet results) throws SQLException {
        billingAddress = results.getString("BillingAddress");
        billingCity = results.getString("BillingCity");
        billingState = results.getString("BillingState");
        billingCountry = results.getString("BillingCountry");
        billingPostalCode = results.getString("BillingPostalCode");
        total = results.getBigDecimal("Total");
        invoiceId = results.getLong("InvoiceId");
        customerId = results.getLong("CustomerId");
    }

    public List<InvoiceItem> getInvoiceItems() {
        List<InvoiceItem> items = new ArrayList<>();

        // SQL query to fetch invoice items based on the current InvoiceId
        String query = "SELECT * FROM invoice_items WHERE InvoiceId = ?";

        try (Connection conn = DB.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setLong(1, this.invoiceId);  // Use the current invoice's ID
            ResultSet rs = stmt.executeQuery();

            // Process the result set and populate the list
            while (rs.next()) {
                InvoiceItem item = new InvoiceItem();  // Create InvoiceItem from ResultSet
                item.setInvoiceLineId(rs.getLong("InvoiceLineId"));
                item.setInvoiceId(rs.getLong("InvoiceId"));
                item.setTrackId(rs.getLong("TrackId"));
                item.setUnitPrice(rs.getBigDecimal("UnitPrice"));
                item.setQuantity(rs.getLong("Quantity"));
                items.add(item);
            }

        } catch (SQLException e) {
            e.printStackTrace();  // Handle exception properly
        }

        return items;
    }


    public Customer getCustomer() {
        // SQL query to fetch customer based on the CustomerId from the current Invoice
        String query = "SELECT * FROM customers WHERE CustomerId = ?";

        try (Connection conn = DB.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Assuming the Invoice has a field 'customerId'
            stmt.setLong(1, this.customerId);  // Use the current invoice's customerId
            ResultSet rs = stmt.executeQuery();

            // Check if a customer record is found
            if (rs.next()) {
                return new Customer();  // Create and return a Customer object from the ResultSet
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Handle exception properly
        }

        return null;  // Return null if no customer is found
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getBillingCity() {
        return billingCity;
    }

    public void setBillingCity(String billingCity) {
        this.billingCity = billingCity;
    }

    public String getBillingState() {
        return billingState;
    }

    public void setBillingState(String billingState) {
        this.billingState = billingState;
    }

    public String getBillingCountry() {
        return billingCountry;
    }

    public void setBillingCountry(String billingCountry) {
        this.billingCountry = billingCountry;
    }

    public String getBillingPostalCode() {
        return billingPostalCode;
    }

    public void setBillingPostalCode(String billingPostalCode) {
        this.billingPostalCode = billingPostalCode;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public static List<Invoice> all() {
        return all(0, Integer.MAX_VALUE);
    }

    public static List<Invoice> all(int page, int count) {
        List<Invoice> invoices = new ArrayList<>();
        String query = "SELECT * FROM invoices LIMIT ? OFFSET ?";

        try (Connection connect = DB.connect();
             PreparedStatement stmt = connect.prepareStatement(query)) {

            // Calculate the offset for pagination
            int offset = (page - 1) * count;

            stmt.setInt(1, count);   // Set the limit
            stmt.setInt(2, offset);  // Set the offset for pagination

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                invoices.add(new Invoice(rs));  // Create an Invoice from the ResultSet
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return invoices;
    }

    public static Invoice find(long invoiceId) {
        String query = "SELECT * FROM invoices WHERE InvoiceId = ?";

        try (Connection conn = DB.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setLong(1, invoiceId);  // Set the invoiceId in the query
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Log the retrieved data for debugging
                System.out.println("Billing City: " + rs.getString("BillingCity"));
                return new Invoice(rs);  // Create and return the invoice object
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Log any SQL errors
        }

        return null;  // Return null if no invoice found
      }
}