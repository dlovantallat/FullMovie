package com.dlovan.fullmovie;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.test.runner.AndroidJUnit4;

import com.dlovan.fullmovie.database.Columns;
import com.dlovan.fullmovie.database.MovieContentProvider;
import com.dlovan.fullmovie.provider.MovieDatabase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashSet;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test exam database to be ensure that all tables work correctly with CRUD.
 * <p>
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(AndroidJUnit4.class)
public class DatabaseTest {

    private Context mContext = getTargetContext();

    @Before
    public void setUp() throws Exception {
        mContext.deleteDatabase(MovieDatabase.getInstance(mContext).getDatabaseName());
    }

    /**
     * Helper method to insert data to a table.
     *
     * @param uri    Table content uri.
     * @param values Values to be inserted.
     */
    private void insert(Uri uri, ContentValues[] values) {

        int inserted = mContext.getContentResolver().bulkInsert(uri, values);
        assertEquals(values.length, inserted);
    }

    /**
     * Helper method to read data from a table.
     *
     * @param uri      Table content uri.
     * @param expected Expected number of rows should be returned.
     */
    private void read(Uri uri, int expected) {

        Cursor cursor = mContext.getContentResolver().query(uri, null, null, null, null);
        assertEquals(expected, cursor.getCount());
        cursor.close();
    }

    /**
     * Helper method to delete rows in a table.
     *
     * @param uri      Table content uri.
     * @param expected Expected number of rows should be deleted.
     */
    private void delete(Uri uri, int expected) {

        int deleted = mContext.getContentResolver().delete(uri, null, null);
        assertEquals(expected, deleted);
    }

    /**
     * Test the database whether is created or not also test tables whether is created or not.
     */
    @Test
    public void isTableCreated() {

        // open the database.
        SQLiteDatabase db = MovieDatabase.getInstance(mContext).getWritableDatabase();
        assertEquals(true, db.isOpen());

        // list of all tables.
        final HashSet<String> tableNameHashSet = new HashSet<>();
        tableNameHashSet.add("list_movie_popular");
        tableNameHashSet.add("list_movie_top_rate");
        tableNameHashSet.add("detail_movie");

        // have we created the tables we want?
        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        assertTrue("Error: This means that the database has not been created correctly",
                c.moveToFirst());

        // verify that the tables have been created
        do {
            tableNameHashSet.remove(c.getString(0));
        } while (c.moveToNext());

        // if this fails, it means that your database doesn't contain all tables
        assertTrue("Error: Your database was created without all tables",
                tableNameHashSet.isEmpty());

        c.close();
    }

    /**
     * Test tables for CRUD operations.
     */
    @Test
    public void crudTables() {
        listMoviePopular();
        detailMovie();
    }

    /**
     * Test listMovie table for CRUD operations.
     */
    public void listMoviePopular() {
        ContentValues cv = new ContentValues();
        cv.put(Columns.ListMoviePopular._ID, 1);
        cv.put(Columns.ListMoviePopular.TITLE, "wonder woman");
        cv.put(Columns.ListMoviePopular.POSTER_PATH, "http");

        ContentValues cv1 = new ContentValues();
        cv1.put(Columns.ListMoviePopular._ID, 2);
        cv1.put(Columns.ListMoviePopular.TITLE, "wonder woman 2");
        cv1.put(Columns.ListMoviePopular.POSTER_PATH, "http//");


        ContentValues[] values = {cv, cv1};

        Uri uri = MovieContentProvider.ListMoviePopular.CONTENT_URI;
        insert(uri, values);
        read(uri, values.length);
        delete(uri, values.length);
    }

    public void listMovieTopRate() {
        ContentValues cv = new ContentValues();
        cv.put(Columns.ListMovieTopRate._ID, 1);
        cv.put(Columns.ListMovieTopRate.TITLE, "wonder woman");
        cv.put(Columns.ListMovieTopRate.POSTER_PATH, "http");

        ContentValues cv1 = new ContentValues();
        cv1.put(Columns.ListMovieTopRate._ID, 2);
        cv1.put(Columns.ListMovieTopRate.TITLE, "wonder woman 2");
        cv1.put(Columns.ListMovieTopRate.POSTER_PATH, "http//");


        ContentValues[] values = {cv, cv1};

        Uri uri = MovieContentProvider.ListMovieTopRate.CONTENT_URI;
        insert(uri, values);
        read(uri, values.length);
        delete(uri, values.length);
    }

    /**
     * Test detailMovie table for CRUD operations.
     */
    public void detailMovie() {
        ContentValues cv = new ContentValues();
        cv.put(Columns.DetailMovie._ID, 1);
        cv.put(Columns.DetailMovie.TITLE, "die hard");
        cv.put(Columns.DetailMovie.OVERVIEW, "this is good movie");
        cv.put(Columns.DetailMovie.POSTER_PATH, "http");
        cv.put(Columns.DetailMovie.BACKDROP_PATH, "http2");
        cv.put(Columns.DetailMovie.VOTE, 7.1);

        ContentValues cv1 = new ContentValues();
        cv1.put(Columns.DetailMovie._ID, 2);
        cv1.put(Columns.DetailMovie.TITLE, "die hard 2");
        cv1.put(Columns.DetailMovie.OVERVIEW, "this is good movie");
        cv1.put(Columns.DetailMovie.POSTER_PATH, "http");
        cv1.put(Columns.DetailMovie.BACKDROP_PATH, "http2");
        cv1.put(Columns.DetailMovie.VOTE, 7.1);


        ContentValues[] values = {cv, cv1};

        Uri uri = MovieContentProvider.DetailMovie.CONTENT_URI;

        Uri uri1 = MovieContentProvider.DetailMovie.withId(1);

        insert(uri, values);
        read(uri, values.length);
        read(uri1, 1);
        delete(uri, values.length);

    }
}
