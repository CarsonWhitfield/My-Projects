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

public class Playlist extends Model {

    Long playlistId;
    String name;

    public Playlist() {
    }

    private Playlist(ResultSet results) throws SQLException {
        name = results.getString("Name");
        playlistId = results.getLong("PlaylistId");
    }


    public List<Track> getTracks() {
        List<Track> tracks = new ArrayList<>();
        String query = """
        SELECT t.TrackId, t.Name, t.AlbumId, t.MediaTypeId, t.GenreId, t.Composer, t.Milliseconds, t.Bytes, t.UnitPrice
        FROM tracks t
        INNER JOIN playlist_track pt ON t.TrackId = pt.TrackId
        WHERE pt.PlaylistId = ?
        ORDER BY t.Name
    """;

        try (Connection conn = DB.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setLong(1, playlistId);
            ResultSet results = stmt.executeQuery();

            while (results.next()) {
                tracks.add(new Track(results)); // Assuming Track class has a ResultSet constructor
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tracks;
    }

    public static Playlist fromResultSet(ResultSet results) throws SQLException {
        return new Playlist(results);  // Use the private constructor here
    }

    public Long getPlaylistId() {
        return playlistId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static List<Playlist> all() {
        return all(0, Integer.MAX_VALUE);
    }

    public static List<Playlist> all(int page, int count) {
        List<Playlist> playlists = new ArrayList<>();
        String query = "SELECT * FROM Playlists LIMIT ? OFFSET ?";

        try (Connection connect = DB.connect();
             PreparedStatement stmt = connect.prepareStatement(query)) {

            // Calculate the offset for pagination
            int offset = (page - 1) * count;

            stmt.setInt(1, count);   // Set the limit
            stmt.setInt(2, offset);  // Set the offset for pagination

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                playlists.add(new Playlist(rs));  // Create an Invoice from the ResultSet
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return playlists;

    }

    public static Playlist find(int i) {
        String sql = "SELECT * FROM Playlists WHERE PlaylistId = ?";
        try (Connection conn = DB.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, i);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Playlist(rs);  // Return the Playlist from the ResultSet
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Handle exception properly in real code
        }
        return null;  // Return null if not found
    }


}
