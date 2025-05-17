package edu.montana.csci.csci440.homeworks.hwk5;

import edu.montana.csci.csci440.DBTest;
import edu.montana.csci.csci440.model.Track;
import edu.montana.csci.csci440.util.DB;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Homework5 extends DBTest {

    @Test
    /*
     * Select tracks that have been sold more than once (> 1)
     *
     * Select the albums that have tracks that have been sold more than once (> 1)
     *   NOTE: This is NOT the same as albums whose tracks have been sold more than once!
     *         An album could have had three tracks, each sold once, and should not be included
     *         in this result.  It should only include the albums of the tracks found in the first
     *         query.
     * */
    public void selectPopularTracksAndTheirAlbums() throws SQLException {

        String albums_sold = """
                SELECT
                    t.TrackId,
                    t.Name AS TrackName,
                    SUM(ii.Quantity) AS TimesSold
                FROM
                    tracks t
                JOIN
                    invoice_items ii ON t.TrackId = ii.TrackId
                GROUP BY
                    t.TrackId, t.Name
                HAVING
                    SUM(ii.Quantity) > 1
    """;

        // HINT: join to invoice items and do a group by/having to get the right answer
        List<Map<String, Object>> tracks = exec(albums_sold);
        assertEquals(256, tracks.size());


        String check_albums_for_dup = """
                SELECT DISTINCT
                       a.AlbumId,
                       a.Title
                   FROM
                       albums a
                   JOIN
                       tracks t ON a.AlbumId = t.AlbumId
                   WHERE
                       t.TrackId IN (
                           SELECT
                               ii.TrackId
                           FROM
                               invoice_items ii
                           GROUP BY
                               ii.TrackId
                           HAVING
                               SUM(ii.Quantity) > 1
                   )""";


        // HINT: join to tracks and invoice items and do a group by/having to get the right answer
        //       note: you will need to use the DISTINCT operator to get the right result!
        List<Map<String, Object>> albums = exec(check_albums_for_dup);
        assertEquals(166, albums.size());
    }

    @Test
    /*
     * Select customers emails who are assigned to Jane Peacock as a Rep and
     * who have purchased something from the 'Rock' Genre
     *
     * Please use an IN clause and a sub-select to generate customer IDs satisfying the criteria
     * */
    public void selectCustomersMeetingCriteria() throws SQLException {
        // HINT: join to invoice items and do a group by/having to get the right answer

        String check_albums_for_dup = """
                 SELECT
                        c.Email
                    FROM
                        customers c
                    WHERE
                        c.CustomerId IN (
                            SELECT DISTINCT i.CustomerId
                            FROM invoice_items ii
                            JOIN invoices i ON ii.InvoiceId = i.InvoiceId
                            JOIN tracks t ON ii.TrackId = t.TrackId
                            JOIN genres g ON t.GenreId = g.GenreId
                            WHERE g.Name = 'Rock'
                        )
                    AND
                        c.SupportRepId = (
                            SELECT EmployeeId
                            FROM employees
                            WHERE FirstName = 'Jane' AND LastName = 'Peacock'
                        )
                """;

        List<Map<String, Object>> tracks = exec(check_albums_for_dup );
        assertEquals(21, tracks.size());
    }

}
