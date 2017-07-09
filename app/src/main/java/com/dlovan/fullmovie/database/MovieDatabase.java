package com.dlovan.fullmovie.database;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.IfNotExists;
import net.simonvt.schematic.annotation.Table;

/**
 * Created by dlovan on 7/5/17.
 */

@Database(version = MovieDatabase.VERSION, packageName = MovieContentProvider.DATABASE_NAME)
public class MovieDatabase {

    static final int VERSION = 1;

    private MovieDatabase() {
    }

    static class Tables {
        @Table(Columns.ListMoviePopular.class)
        @IfNotExists
        public static final String LIST_MOVIE_POPULAR = "list_movie_popular";

        @Table(Columns.ListMovieTopRate.class)
        @IfNotExists
        public static final String LIST_MOVIE_TOP_RATE = "list_movie_top_rate";

        @Table(Columns.DetailMovie.class)
        @IfNotExists
        public static final String DETAIL_MOVIE = "detail_movie";
    }

}
