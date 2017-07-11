package com.dlovan.fullmovie.database;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.Check;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.DataType.Type;
import net.simonvt.schematic.annotation.PrimaryKey;

/**
 * this interface hold all columns number for the tables in database
 * Created by dlovan on 7/5/17.
 */
public interface Columns {

    /**
     * hold all columns for listMovie table
     */
    interface ListMovie {

        String POPULAR = "popular";
        String TOP_RATED = "top_rated";

        @DataType(Type.INTEGER)
        @PrimaryKey
        @AutoIncrement
        String _ID = "_id";

        @DataType(Type.INTEGER)
        String ID = "movieId";

        @DataType(Type.TEXT)
        String TITLE = "title";

        @DataType(Type.TEXT)
        String POSTER_PATH = "poster_path";

        @DataType(Type.TEXT)
        @Check(ListMovie.TYPE + " in ('" + POPULAR + "', '" + TOP_RATED + "')")
        String TYPE = "type";
    }

    /**
     * hold all columns for detailMovie table
     */
    interface DetailMovie {

        @DataType(Type.INTEGER)
        @PrimaryKey
        @AutoIncrement
        String _ID = "_id";

        @DataType(Type.INTEGER)
        String ID = "movieId";

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
