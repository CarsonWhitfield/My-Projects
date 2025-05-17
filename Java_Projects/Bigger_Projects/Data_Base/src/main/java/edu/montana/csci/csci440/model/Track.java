package edu.montana.csci.csci440.model;

import edu.montana.csci.csci440.util.DB;
import redis.clients.jedis.Client;
import redis.clients.jedis.Jedis;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Track extends Model {

    private Long trackId;
    private Long albumId;
    private Long mediaTypeId;
    private Long genreId;
    private String name;
    private Long milliseconds;
    private Long bytes;
    private BigDecimal unitPrice;
    public static final String REDIS_CACHE_KEY = "cs440-tracks-count-cache";


    private String cachedArtistName = null;
    private String cachedTitleName = null;

    public Track() {
        mediaTypeId = 1l;
        genreId = 1l;
        milliseconds  = 0l;
        bytes  = 0l;
        unitPrice = new BigDecimal("0");
    }

    public Track(ResultSet results) throws SQLException {
        name = results.getString("Name");
        milliseconds = results.getLong("Milliseconds");
        bytes = results.getLong("Bytes");
        unitPrice = results.getBigDecimal("UnitPrice");
        trackId = results.getLong("TrackId");
        albumId = results.getLong("AlbumId");
        mediaTypeId = results.getLong("MediaTypeId");
        genreId = results.getLong("GenreId");
    }

    public static Track find(long i) {
        try (Connection conn = DB.connect();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM tracks WHERE TrackId = ?")) {

            stmt.setLong(1, i);
            ResultSet resultSet = stmt.executeQuery();


            if (resultSet.next()) {
                Track track = new Track(resultSet);

                // Populate cachedArtistName with the artist's name from the associated album
                track.cachedArtistName = track.getAlbum().getArtist().getName();
                track.cachedTitleName = track.getAlbum().getTitle();

                return track;
            } else {
                return null;
            }


        } catch (SQLException e) {
            // Log error with additional context to help debug
            throw new RuntimeException("Error fetching track with TrackId: " + i, e);
        }
    }


    public static Long count() {
        Jedis jedis = new Jedis();

        try {
            // Check if the count is cached in Redis
            String cachedCount = jedis.get(REDIS_CACHE_KEY);

            if (cachedCount != null) {
                // If found in the cache, return the cached count
                return Long.parseLong(cachedCount);
            } else {
                // If not found in the cache, query the database for the count

                String query = "SELECT COUNT(*) FROM tracks";

                try (Connection conn = DB.connect();
                     PreparedStatement stmt = conn.prepareStatement(query)) {

                    ResultSet resultSet = stmt.executeQuery();

                    if (resultSet.next()) {
                        Long count = resultSet.getLong(1);  // Retrieve the count value from the query result

                        // Save the count to Redis cache with an expiration time (e.g., 60 seconds)
                        jedis.setex(REDIS_CACHE_KEY, 60, count.toString());
                        System.out.println("Database count fetched: " + count);
                        System.out.println("Cache updated with new count");


                        return count;
                    } else {
                        return 0L;  // If no result, return 0
                    }
                } catch (SQLException e) {
                    throw new RuntimeException("Error fetching track count", e);
                }
            }
        } finally {
            jedis.close();  // Ensure Redis connection is closed
        }
    }

    public Album getAlbum() {
        return Album.find(albumId);
    }

    public MediaType getMediaType() {
        String query = "SELECT * FROM media_types WHERE MediaTypeId = ?";

        try (Connection conn = DB.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {


            stmt.setLong(1, this.mediaTypeId);


            ResultSet resultSet = stmt.executeQuery();


            if (resultSet.next()) {
                return MediaType.fromResultSet(resultSet);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching media type for track", e);
        }
    }

    public Genre getGenre() {
        // SQL to fetch MediaType based on the MediaTypeId of this track
        String query = "SELECT * FROM genres WHERE GenreId = ?";

        try (Connection conn = DB.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {


            stmt.setLong(1, this.genreId);


            ResultSet resultSet = stmt.executeQuery();


            if (resultSet.next()) {
                return Genre.fromResultSet(resultSet);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching media type for track", e);
        }
    }

    public List<Playlist> getPlaylists(){
        try (Connection conn = DB.connect();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT p.* FROM playlists p " +
                             "JOIN playlist_track pt ON pt.PlaylistId = p.PlaylistId " +
                             "WHERE pt.TrackId = ?")) {

            stmt.setLong(1, this.trackId);
            ResultSet resultSet = stmt.executeQuery();

            List<Playlist> playlists = new ArrayList<>();
            while (resultSet.next()) {
                playlists.add(Playlist.fromResultSet(resultSet));
            }

            return playlists;
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching playlists for track " + trackId, e);
        }
    }

    public Long getTrackId() {
        return trackId;
    }

    public void setTrackId(Long trackId) {
        this.trackId = trackId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getMilliseconds() {
        return milliseconds;
    }

    public void setMilliseconds(Long milliseconds) {
        this.milliseconds = milliseconds;
    }

    public Long getBytes() {
        return bytes;
    }

    public void setBytes(Long bytes) {
        this.bytes = bytes;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

    public void setAlbum(Album album) {
        albumId = album.getAlbumId();
    }

    public Long getMediaTypeId() {
        return mediaTypeId;
    }

    public void setMediaTypeId(Long mediaTypeId) {
        this.mediaTypeId = mediaTypeId;
    }

    public Long getGenreId() {
        return genreId;
    }

    public void setGenreId(Long genreId) {
        this.genreId = genreId;
    }

    public String getArtistName() {
        if (cachedArtistName == null) {
            cachedArtistName = getAlbum().getArtist().getName();
        }
        return cachedArtistName;
    }



    public String getAlbumTitle() {
        if(cachedTitleName == null){
            cachedTitleName = getAlbum().getTitle();
        }
        return cachedTitleName;
    }

    public static List<Track> advancedSearch(int page, int count,
                                             String search, Integer artistId, Integer albumId,
                                             Integer maxRuntime, Integer minRuntime) {

        try {
            try (Connection connect = DB.connect();
                 PreparedStatement stmt = connect.prepareStatement(
                         "SELECT * FROM tracks " +
                                 "WHERE Name LIKE ? " +
                                 "AND (AlbumId = ? OR ? IS NULL) " +
                                 "AND (? IS NULL OR Milliseconds >= ?) " +
                                 "AND (? IS NULL OR Milliseconds <= ?) " +
                                 "AND (AlbumId IN (SELECT AlbumId FROM albums WHERE ArtistId = ?) OR ? IS NULL) " +
                                 "LIMIT ? OFFSET ?")) {

                ArrayList<Track> result = new ArrayList<>();
                int offset = (page - 1) * count;

                // Set the search term
                stmt.setString(1, "%" + search + "%");

                // Set albumId with null checking
                stmt.setObject(2, albumId);
                stmt.setObject(3, albumId);

                // Set minRuntime and maxRuntime, allowing them to be null
                if (minRuntime != null) {
                    stmt.setInt(4, minRuntime);
                    stmt.setInt(5, minRuntime);
                } else {
                    stmt.setNull(4, java.sql.Types.INTEGER); // Set null for minRuntime
                    stmt.setNull(5, java.sql.Types.INTEGER); // Set null for minRuntime
                }

                if (maxRuntime != null) {
                    stmt.setInt(6, maxRuntime);
                    stmt.setInt(7, maxRuntime);
                } else {
                    stmt.setNull(6, java.sql.Types.INTEGER); // Set null for maxRuntime
                    stmt.setNull(7, java.sql.Types.INTEGER); // Set null for maxRuntime
                }

                // Allow for null values in the artistId check
                stmt.setObject(8, artistId);
                stmt.setObject(9, artistId);

                // Set limit and offset for pagination
                stmt.setInt(10, count);
                stmt.setInt(11, offset);

                ResultSet resultSet = stmt.executeQuery();

                while (resultSet.next()) {
                    result.add(new Track(resultSet));
                }

                return result;

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Track> search(int page, int count, String orderBy, String search) {
        try {
            try(Connection connect = DB.connect();
                PreparedStatement stmt = connect.prepareStatement( "SELECT * FROM tracks " +
                        "WHERE Name LIKE ? " +
                        (orderBy != null && !orderBy.isEmpty() ? "ORDER BY " + orderBy + " " : "") +
                        "LIMIT ? OFFSET ?")) {
                ArrayList<Track> result = new ArrayList<>();
                int offset = (page - 1) * count;

                stmt.setString(1, "%" + search + "%");
                stmt.setInt(2, count);
                stmt.setInt(3, offset);
                ResultSet resultSet = stmt.executeQuery();
                while (resultSet.next()) {
                    result.add(new Track(resultSet));
                }
                return result;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Track> forAlbum(Long albumId) {
        List<Track> tracks = new ArrayList<>();

        String query = "SELECT * FROM tracks WHERE AlbumId = ?";

        try (Connection conn = DB.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setLong(1, albumId);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                Track track = new Track();
                track.setTrackId(resultSet.getLong("TrackId"));
                track.setName(resultSet.getString("Name"));
                track.setAlbumId(resultSet.getLong("AlbumId"));
                track.setMediaTypeId(resultSet.getLong("MediaTypeId"));
                track.setGenreId(resultSet.getLong("GenreId"));
                track.setMilliseconds(resultSet.getLong("Milliseconds"));
                track.setBytes(resultSet.getLong("Bytes"));
                track.setUnitPrice(resultSet.getBigDecimal("UnitPrice"));

                tracks.add(track);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching tracks for album " + albumId, e);
        }

        return tracks;
    }


    public static List<Track> all() {
        return all(0, Integer.MAX_VALUE);
    }

    public static List<Track> all(int page, int count) {
        return all(page, count, "TrackId");
    }

    public static List<Track> all(int page, int count, String orderBy) {
        int offset = (page - 1) * count;  // Calculate offset
        String query = "SELECT * FROM tracks ORDER BY " + orderBy + " LIMIT ? OFFSET ?";

        try (Connection connect = DB.connect();
             PreparedStatement stmt = connect.prepareStatement(query)) {
            ArrayList<Track> result = new ArrayList<>();
            stmt.setInt(1, count);
            stmt.setInt(2, offset);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                result.add(new Track(resultSet));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean create() {
        if (verify()) {
            try (Connection conn = DB.connect();
                 PreparedStatement stmt = conn.prepareStatement(
                         "INSERT INTO tracks (Name, AlbumId, MediaTypeId, GenreId, Milliseconds, Bytes, UnitPrice) VALUES (?, ?, ?, ?, ?, ?, ?)")) {

                stmt.setString(1, this.getName());
                stmt.setLong(2, this.getAlbumId());
                stmt.setLong(3, this.getMediaTypeId());
                stmt.setLong(4, this.getGenreId());
                stmt.setLong(5, this.getMilliseconds());
                stmt.setLong(6, this.getBytes());
                stmt.setBigDecimal(7, this.getUnitPrice());
                stmt.executeUpdate();

                trackId = DB.getLastID(conn);

                // Cache invalidation after track creation
                Jedis jedis = new Jedis();
                jedis.del(REDIS_CACHE_KEY);  // Clear the cache
                jedis.close();

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
        if (this.name == null || this.name.isEmpty()) {
            _errors.add("Title is required");
        }
        if (this.albumId == null) {
            _errors.add("Album is required");
        }
        // Continue adding other validations here
        return _errors.isEmpty();
    }

    public void delete() {
        // Ensure trackId is set before attempting to delete
        if (trackId == null) {
            throw new IllegalStateException("Track ID must be set before deleting");
        }

        String query = "DELETE FROM tracks WHERE TrackId = ?";
        Jedis jedis = new Jedis();

        try (Connection conn = DB.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {


            stmt.setLong(1, trackId);


            int rowsAffected = stmt.executeUpdate();


            if (rowsAffected == 0) {
                throw new SQLException("No rows were deleted, possibly invalid TrackId");
            }


            jedis.del(REDIS_CACHE_KEY);

        } catch (SQLException e) {
            throw new RuntimeException("Error deleting track: " + e.getMessage());
        } finally {
            jedis.close();
        }
    }

    public boolean update() {
        if (trackId == null) {
            throw new IllegalStateException("Track ID must be set before updating");
        }

        String query = "UPDATE tracks SET Name = ?, AlbumId = ?, MediaTypeId = ?, GenreId = ?, Milliseconds = ?, Bytes = ?, UnitPrice = ? WHERE TrackId = ?";

        try (Connection conn = DB.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Set the updated values in the prepared statement
            stmt.setString(1, this.getName());
            stmt.setLong(2, this.getAlbumId());
            stmt.setLong(3, this.getMediaTypeId());
            stmt.setLong(4, this.getGenreId());
            stmt.setLong(5, this.getMilliseconds());
            stmt.setLong(6, this.getBytes());
            stmt.setBigDecimal(7, this.getUnitPrice());
            stmt.setLong(8, this.getTrackId());


            int rowsAffected = stmt.executeUpdate();


            if (rowsAffected == 0) {
                throw new SQLException("No rows were updated, possibly invalid TrackId");
            }

            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error updating track: " + e.getMessage());
        }
    }

}
