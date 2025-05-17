package edu.montana.csci.csci440.model;

import edu.montana.csci.csci440.util.DB;
import org.eclipse.jetty.http.MetaData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class MediaType extends Model {

    private Long mediaTypeId;
    private String name;

    private MediaType(ResultSet results) throws SQLException {
        name = results.getString("Name");
        mediaTypeId = results.getLong("MediaTypeId");
    }

    public Long getMediaTypeId() {
        return mediaTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static List<MediaType> all() {
        return all(0, Integer.MAX_VALUE);
    }

    public static List<MediaType> all(int page, int count) {
        try {

            try (Connection conn = DB.connect();
                 PreparedStatement stmt = conn.prepareStatement(
                         "SELECT * FROM media_types LIMIT ? OFFSET ?")) {
                int offset = (page - 1) * count;
                stmt.setInt(1, count);
                stmt.setInt(2, offset);
                ResultSet resultSet = stmt.executeQuery();

                ArrayList<MediaType> mediaType = new ArrayList<>();
                while (resultSet.next()) {
                    mediaType.add(new MediaType(resultSet));
                }
                return mediaType;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static MediaType fromResultSet(ResultSet results) throws SQLException {
        return new MediaType(results);
    }
}
