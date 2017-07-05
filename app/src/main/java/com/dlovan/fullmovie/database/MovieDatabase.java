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
        @Table(Columns.ListMovie.class)
        @IfNotExists
        public static final String LIST_MOVIE = "list_movie";

        @Table(Columns.DetailMovie.class)
        @IfNotExists
        public static final String DETAIL_MOVIE = "detail_movie";
    }

}
