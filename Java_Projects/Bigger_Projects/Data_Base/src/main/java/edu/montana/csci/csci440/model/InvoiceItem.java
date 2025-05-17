package edu.montana.csci.csci440.model;
import edu.montana.csci.csci440.util.DB;

import java.sql.*;
import java.math.BigDecimal;

public class InvoiceItem extends Model {

    Long invoiceLineId;
    Long invoiceId;
    Long trackId;
    BigDecimal unitPrice;
    Long quantity;

    // Adding Track and Invoice objects
    private Track track;
    private Invoice invoice;

    public Track getTrack() {
        if (track == null) {
            // Lazy loading: fetch the Track from the database if not already loaded
            track = loadTrack();
        }
        return track;
    }

    // Helper method to load Track from database based on trackId
    private Track loadTrack() {
        String query = "SELECT * FROM tracks WHERE TrackId = ?";
        try (Connection conn = DB.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setLong(1, this.trackId);  // Use the current trackId
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Track track = new Track();  // Assuming Track has a constructor or setter methods
                track.setTrackId(rs.getLong("TrackId"));
                track.setName(rs.getString("Name"));
                track.setAlbumId(rs.getLong("AlbumId"));
                // Set other properties as needed
                return track;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;  // Return null if no track found
    }

    // Helper method to load Invoice from database based on invoiceId
    private Invoice loadInvoice() {
        String query = "SELECT * FROM invoices WHERE InvoiceId = ?";
        try (Connection conn = DB.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setLong(1, this.invoiceId);  // Use the current invoiceId
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Invoice invoice = new Invoice();  // Assuming Invoice has a constructor for ResultSet
                return invoice;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;  // Return null if no invoice found
    }

    public Invoice getInvoice() {
        if (invoice == null) {
            // Lazy loading: fetch the Invoice from the database if not already loaded
            invoice = loadInvoice();
        }
        return invoice;
    }



    public Long getInvoiceLineId() {
        return invoiceLineId;
    }

    public void setInvoiceLineId(Long invoiceLineId) {
        this.invoiceLineId = invoiceLineId;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Long getTrackId() {
        return trackId;
    }

    public void setTrackId(Long trackId) {
        this.trackId = trackId;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
