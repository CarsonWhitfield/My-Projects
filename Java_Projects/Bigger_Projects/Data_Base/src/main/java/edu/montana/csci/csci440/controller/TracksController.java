package edu.montana.csci.csci440.controller;

import edu.montana.csci.csci440.model.Track;
import edu.montana.csci.csci440.util.Web;
import redis.clients.jedis.Jedis;

import java.math.BigDecimal;
import java.util.List;

import static spark.Spark.get;
import static spark.Spark.post;

public class TracksController extends BaseController {
    public static final String REDIS_CACHE_KEY = "cs440-tracks-count-cache";

    public static void init() {
        /* CREATE */
        get("/tracks/new", (req, resp) -> {
            Track track = new Track();
            return renderTemplate("templates/tracks/new.vm", "album", track);
        });

        post("/tracks/new", (req, resp) -> {
            Track track = new Track();



            // Extract parameters from the request
            String name = req.queryParams("Name");
            String albumIdParam = req.queryParams("AlbumId");
            String genreIdParam = req.queryParams("GenreId");
            String mediaTypeIdParam = req.queryParams("MediaTypeId");
            String millisecondsParam = req.queryParams("Milliseconds");
            String bytesParam = req.queryParams("Bytes");
            String unitPriceParam = req.queryParams("UnitPrice");

            // Populate the track fields from request parameters
            track.setName(name);

            // Set AlbumId, GenreId, and other fields with validation
            if (albumIdParam != null && !albumIdParam.isEmpty()) {
                try {
                    track.setAlbumId(Long.valueOf(albumIdParam));
                } catch (NumberFormatException e) {
                    Web.showErrorMessage("Invalid Album ID!");
                    return renderTemplate("templates/tracks/new.vm", "track", track);
                }
            }

            if (genreIdParam != null && !genreIdParam.isEmpty()) {
                try {
                    track.setGenreId(Long.valueOf(genreIdParam));
                } catch (NumberFormatException e) {
                    Web.showErrorMessage("Invalid Genre ID!");
                    return renderTemplate("templates/tracks/new.vm", "track", track);
                }
            }

            if (mediaTypeIdParam != null && !mediaTypeIdParam.isEmpty()) {
                try {
                    track.setMediaTypeId(Long.valueOf(mediaTypeIdParam));
                } catch (NumberFormatException e) {
                    Web.showErrorMessage("Invalid Media Type ID!");
                    return renderTemplate("templates/tracks/new.vm", "track", track);
                }
            }

            if (millisecondsParam != null && !millisecondsParam.isEmpty()) {
                try {
                    track.setMilliseconds(Long.valueOf(millisecondsParam));
                } catch (NumberFormatException e) {
                    Web.showErrorMessage("Invalid Milliseconds value!");
                    return renderTemplate("templates/tracks/new.vm", "track", track);
                }
            }

            if (bytesParam != null && !bytesParam.isEmpty()) {
                try {
                    track.setBytes(Long.valueOf(bytesParam));
                } catch (NumberFormatException e) {
                    Web.showErrorMessage("Invalid Bytes value!");
                    return renderTemplate("templates/tracks/new.vm", "track", track);
                }
            }

            if (unitPriceParam != null && !unitPriceParam.isEmpty()) {
                try {
                    track.setUnitPrice(new BigDecimal(unitPriceParam));
                } catch (NumberFormatException e) {
                    Web.showErrorMessage("Invalid Unit Price!");
                    return renderTemplate("templates/tracks/new.vm", "track", track);
                }
            }



            if (track.create()) {
                Web.showMessage("Created A Track!");
                return Web.redirect("/tracks/" + track.getTrackId());
            } else {
                Web.showErrorMessage("Could Not Create A Track!");
                return renderTemplate("templates/tracks/new.vm",
                        "track", track);
            }
        });

        /* READ */
        get("/tracks", (req, resp) -> {
            String search = req.queryParams("q");
            String orderBy = req.queryParams("o");
            List<Track> tracks;
            if (search != null) {
                tracks = Track.search(Web.getCurrentPage(), Web.PAGE_SIZE, orderBy, search);
            } else {
                tracks = Track.all(Web.getCurrentPage(), Web.PAGE_SIZE, orderBy);
            }

            long totalTracks = getTrackCountFromCache();
            return renderTemplate("templates/tracks/index.vm",
                    "tracks", tracks, "totalTracks", totalTracks);
        });

        get("/tracks/search", (req, resp) -> {
            // Extract query parameters
            String search = req.queryParams("q");
            String albumIdParam = req.queryParams("AlbumId");
            String artistIdParam = req.queryParams("ArtistId");
            String maxParam = req.queryParams("max");
            String minParam = req.queryParams("min");

            // Set parameters to null if empty, except for 'q'
            albumIdParam = (albumIdParam != null && !albumIdParam.isEmpty()) ? albumIdParam : null;
            artistIdParam = (artistIdParam != null && !artistIdParam.isEmpty()) ? artistIdParam : null;
            maxParam = (maxParam != null && !maxParam.isEmpty()) ? maxParam : null;
            minParam = (minParam != null && !minParam.isEmpty()) ? minParam : null;

            // Perform the search with the updated parameters
            List<Track> tracks = Track.advancedSearch(Web.getCurrentPage(), Web.PAGE_SIZE,
                    search,
                    asInt(artistIdParam),
                    asInt(albumIdParam),
                    asInt(maxParam),
                    asInt(minParam));

            return renderTemplate("templates/tracks/search.vm",
                    "tracks", tracks);
        });

        get("/tracks/:id", (req, resp) -> {
            Track track = Track.find(asInt(req.params(":id")));
            return renderTemplate("templates/tracks/show.vm",
                    "track", track);
        });

        /* UPDATE */
        get("/tracks/:id/edit", (req, resp) -> {
            Track track = Track.find(asInt(req.params(":id")));
            return renderTemplate("templates/tracks/edit.vm",
                    "track", track);
        });

        post("/tracks/:id", (req, resp) -> {
            Track track = Track.find(asInt(req.params(":id")));
            track.setName(req.queryParams("Name"));
            track.setMilliseconds(Long.parseLong(req.queryParams("Milliseconds")));
            track.setBytes(Long.parseLong(req.queryParams("Bytes")));
            track.setUnitPrice(new BigDecimal(req.queryParams("UnitPrice")));
            if (track.update()) {
                Web.showMessage("Updated Track!");
                return Web.redirect("/tracks/" + track.getTrackId());
            } else {
                Web.showErrorMessage("Could Not Update Track!");
                return renderTemplate("templates/tracks/edit.vm",
                        "track", track);
            }
        });

        /* DELETE */
        get("/tracks/:id/delete", (req, resp) -> {
            Track track = Track.find(asInt(req.params(":id")));
            track.delete();
            Web.showMessage("Deleted Track " + track.getName());
            return Web.redirect("/tracks");
        });
    }
    // Caching track count using Redis
    private static long getTrackCountFromCache() {
        try (Jedis jedis = new Jedis("localhost")) {
            String cachedCount = jedis.get(REDIS_CACHE_KEY);

            if (cachedCount != null) {
                return Long.parseLong(cachedCount);
            } else {
                long count = Track.count();
                jedis.set(REDIS_CACHE_KEY, String.valueOf(count));
                return count;
            }
        }
    }

}
