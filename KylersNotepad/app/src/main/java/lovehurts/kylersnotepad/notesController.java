package lovehurts.kylersnotepad;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Kyler on 7/10/2016.
 */
public class notesController extends ContentProvider {

    private static final String AUTHORITY = "com.example.KylersNotepad.notesController";
    private static final String BASE_PATH = "notes";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH );

    // Constant to identify the requested operation
    private static final int NOTES = 1;
    private static final int NOTES_ID = 2;

    private static final UriMatcher urimatcher =
            new UriMatcher(UriMatcher.NO_MATCH);

    public static final String CONTENT_ITEM_TYPE = "contentItemType";

    static {
        urimatcher.addURI(AUTHORITY, BASE_PATH, NOTES);
        urimatcher.addURI(AUTHORITY, BASE_PATH + "/#", NOTES_ID);
    }

    private SQLiteDatabase db;

    @Override
    public boolean onCreate() {
        dbOpenHelper helper = new dbOpenHelper(getContext());
        db = helper.getWritableDatabase();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {

        if(urimatcher.match(uri) == NOTES_ID){
                s = dbOpenHelper.NOTE_ID + "=" + uri.getLastPathSegment();
        }

        return db.query(dbOpenHelper.TABLE_NOTES, dbOpenHelper.ALL_COLUMNS,
              s, null, null, null,
                dbOpenHelper.NOTE_CREATED + " DESC");
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        Long id = db.insert(dbOpenHelper.TABLE_NOTES, null, contentValues);
        return Uri.parse(BASE_PATH + "/" + id);
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return db.delete(dbOpenHelper.TABLE_NOTES, s, strings);
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return db.update(dbOpenHelper.TABLE_NOTES, contentValues, s, strings);
    }
}
