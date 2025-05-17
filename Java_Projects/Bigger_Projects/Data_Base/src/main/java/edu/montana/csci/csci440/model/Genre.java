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

public class Genre extends Model {

    private Long genreId;
    private String name;

    private Genre(ResultSet results) throws SQLException {
        name = results.getString("Name");
        genreId = results.getLong("GenreId");
    }

    public Long getGenreId() {
        return genreId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static List<Genre> all() {
        return all(0, Integer.MAX_VALUE);
    }
    public static List<Genre> all(int page, int count) {
        List<Genre> genre = new ArrayList<>();
        String query = "SELECT * FROM Genres LIMIT ? OFFSET ?";

        try (Connection connect = DB.connect();
             PreparedStatement stmt = connect.prepareStatement(query)) {

            // Calculate the offset for pagination
            int offset = (page - 1) * count;

            stmt.setInt(1, count);   // Set the limit
            stmt.setInt(2, offset);  // Set the offset for pagination

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                genre.add(new Genre(rs));  // Create an Invoice from the ResultSet
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return genre;

    }
    public static Genre fromResultSet(ResultSet results) throws SQLException {
        return new Genre(results);
    }

}
