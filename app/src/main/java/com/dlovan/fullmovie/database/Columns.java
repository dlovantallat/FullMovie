package com.dlovan.fullmovie.database;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.Check;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.DataType.Type;
import net.simonvt.schematic.annotation.PrimaryKey;

/**
 * Created by dlovan on 7/5/17.
 */

public interface Columns {

    interface ListMovie {

        String STATUS_POPULAR = "popular";
        String STATUS_TOPRATED = "top_rated";

        @DataType(Type.INTEGER)
        @PrimaryKey
        @AutoIncrement
        String _ID = "id";

        @DataType(Type.TEXT)
        String TITLE = "title";

        @DataType(Type.TEXT)
        String POSTER_PATH = "poster_path";

        @DataType(Type.TEXT)
        @Check(Columns.ListMovie.STATUS + " in ('" + ListMovie.STATUS_POPULAR + "', '" + ListMovie.STATUS_TOPRATED + "')")
        String STATUS = "status";
    }

    interface DetailMovie {

        @DataType(Type.INTEGER)
        @PrimaryKey
        @AutoIncrement
        String _ID = "id";

        @DataType(Type.TEXT)
        String TITLE = "title";

        @DataType(Type.TEXT)
        String OVERVIEW = "overview";

        @DataType(Type.TEXT)
        String POSTER_PATH = "poster_path";

        @DataType(Type.TEXT)
        String BACKDROP_PATH = "backdrop_path";

        @DataType(Type.REAL)
        String VOTE = "vote";
    }
}
