package com.dlovan.fullmovie.database;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.InexactContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

/**
 * Created by dlovan on 7/5/17.
 */

@ContentProvider(
        authority = MovieContentProvider.AUTHORITY,
        database = MovieDatabase.class,
        packageName = MovieContentProvider.DATABASE_NAME)
public class MovieContentProvider {

    static final String AUTHORITY = "com.dlovan.fullmovie.MovieContentProvider";
    static final String DATABASE_NAME = "com.dlovan.fullmovie.provider";
    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);


    interface Path {
        String LIST_MOVIE_POPULAR = "list_movie_popular";
        String LIST_MOVIE_TOP_RATE = "list_movie_top_rate";
        String DETAIL_MOVIE = "detail_movie";
    }

    private MovieContentProvider() {
    }

    private static Uri buildUri(String... p) {
        Uri.Builder builder = BASE_CONTENT_URI.buildUpon();
        for (String path : p) {
            builder.appendPath(path);
        }
        return builder.build();
    }

    @TableEndpoint(table = MovieDatabase.Tables.LIST_MOVIE_POPULAR)
    public static class ListMoviePopular {

        @ContentUri(
                path = Path.LIST_MOVIE_POPULAR,
                type = "vnd.android.cursor.dir/list")
        public static final Uri CONTENT_URI = buildUri(Path.LIST_MOVIE_POPULAR);
    }


    @TableEndpoint(table = MovieDatabase.Tables.LIST_MOVIE_TOP_RATE)
    public static class ListMovieTopRate {

        @ContentUri(
                path = Path.LIST_MOVIE_TOP_RATE,
                type = "vnd.android.cursor.dir/list")
        public static final Uri CONTENT_URI = buildUri(Path.LIST_MOVIE_TOP_RATE);
    }

    @TableEndpoint(table = MovieDatabase.Tables.DETAIL_MOVIE)
    public static class DetailMovie {

        @ContentUri(
                path = Path.DETAIL_MOVIE,
                type = "vnd.android.cursor.dir/list")
        public static final Uri CONTENT_URI = buildUri(Path.DETAIL_MOVIE);

        @InexactContentUri(name = "NOTE_ID",
                path = Path.DETAIL_MOVIE + "/#",
                type = "vnd.android.cursor.item/list",
                whereColumn = Columns.ListMoviePopular._ID,
                pathSegment = 1)
        public static Uri withId(long id) {
            return buildUri(Path.DETAIL_MOVIE, String.valueOf(id));
        }
    }
}
