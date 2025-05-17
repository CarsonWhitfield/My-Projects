package edu.montana.csci.csci440.model;

import edu.montana.csci.csci440.util.DB;

import java.sql.*;
import java.util.*;

public class Artist extends Model {

    Long artistId;
    String name;

    // Original values for optimistic concurrency
    private String originalName;


    public Artist() {
    }

    private Artist(ResultSet results) throws SQLException {
        name = results.getString("Name");
        artistId = results.getLong("ArtistId");
        originalName = name;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public List<Album> getAlbums(){
        return Album.getForArtist(artistId);
    }

    public Long getArtistId() {
        return artistId;
    }

    public void setArtistId(Long id) {
        this.artistId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static List<Artist> all() {
        return all(0, Integer.MAX_VALUE);
    }

    public static List<Artist> all(int page, int count) {
        try {

            try (Connection conn = DB.connect();
                 PreparedStatement stmt = conn.prepareStatement(
                         "SELECT * FROM artists LIMIT ? OFFSET ?")) {
                int offset = (page - 1) * count;
                stmt.setInt(1, count);
                stmt.setInt(2, offset);
                ResultSet resultSet = stmt.executeQuery();

                ArrayList<Artist> artists = new ArrayList<>();
                while (resultSet.next()) {
                    artists.add(new Artist(resultSet));
                }
                return artists;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static Artist find(long i) {
        try {
            try (Connection conn = DB.connect();
                 PreparedStatement stmt = conn.prepareStatement(
                         "SELECT * FROM artists WHERE ArtistId = ?")) {
                stmt.setLong(1, i);
                ResultSet resultSet = stmt.executeQuery();
                if (resultSet.next()) {
                    return new Artist(resultSet);
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean create() {
        if (verify()) {
            try (Connection conn = DB.connect();
                 PreparedStatement stmt = conn.prepareStatement(
                         "INSERT INTO artists (Name) VALUES (?)")) {
                stmt.setString(1, this.getName());
                stmt.executeUpdate();

                // Retrieve the generated artistId
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        this.artistId = generatedKeys.getLong(1);  // Set the artistId to the generated value
                    } else {
                        throw new SQLException("Creating artist failed, no ID obtained.");
                    }
                }
                return true;
            } catch (SQLException sqlException) {
                throw new RuntimeException(sqlException);
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean verify() {
        _errors.clear();
        if (name == null || name.isEmpty()) {
            addError("Title is required");
        }
        return !hasErrors();
    }

    public boolean update() {
        if (verify()) {
            try (Connection conn = DB.connect();
                 PreparedStatement stmt = conn.prepareStatement(
                         "UPDATE artists SET Name=? WHERE ArtistId=? AND Name=?")) {
                stmt.setString(1, this.getName());
                stmt.setLong(2, this.getArtistId());
                stmt.setString(3, this.getOriginalName()); // Check original value

                int rowsUpdated = stmt.executeUpdate();

                if (rowsUpdated == 1) {
                    // Update succeeded; set originalName to new name
                    this.originalName = this.name;
                    return true;
                } else {
                    return false; // Update failed due to mismatch
                }
            } catch (SQLException sqlException) {
                throw new RuntimeException(sqlException);
            }
        } else {
            return false;
        }
   }


    public void delete() {
        try (Connection conn = DB.connect();
             PreparedStatement stmt = conn.prepareStatement(
                     "DELETE FROM artists WHERE ArtistId = ?")) {
            stmt.setLong(1, this.getArtistId());

            int rowsDeleted = stmt.executeUpdate();

            // If no rows were deleted, throw an exception indicating the artist wasn't found
            if (rowsDeleted == 0) {
                throw new RuntimeException("Artist not found, deletion failed.");
            }
        } catch (SQLException sqlException) {
            throw new RuntimeException("Error deleting artist", sqlException);
        }
    }

}


