package com.example.asus.subthreemvvm;

import android.annotation.SuppressLint;
import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.asus.subthreemvvm.database.AppDatabase;
import com.example.asus.subthreemvvm.database.MovieDAO;
import com.example.asus.subthreemvvm.database.MovieModelDb;

import java.util.ArrayList;

@SuppressLint("Registered")
public class MovieCatalogueProvider extends ContentProvider {

    public static final String AUTHORITY = "com.example.asus.subthreemvvm";

    public static final Uri URI_FAVORITE = Uri.parse(
            "content://" + AUTHORITY + "/" + "favorite_movie"
    );

    private static final int CODE_MENU_DIR = 1;
    private static final int CODE_MENU_ITEM = 2;

    private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        MATCHER.addURI(AUTHORITY, "favorite_movie", CODE_MENU_DIR);
        MATCHER.addURI(AUTHORITY, "favorite_movie/*", CODE_MENU_ITEM);
    }

    @Override
    public boolean onCreate() {
        return true;
    }


    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final int code = MATCHER.match(uri);
        if (code == CODE_MENU_DIR || code == CODE_MENU_ITEM) {
            final Context context = getContext();
            if (context == null) {
                return null;
            }

            MovieDAO dao = AppDatabase.initDatabase(context).movieDAO();
            final Cursor cursor;
            if (code == CODE_MENU_DIR) {
                cursor = dao.getAllMovie();
            } else {
                cursor = dao.getMovieById(ContentUris.parseId(uri));
            }

            cursor.setNotificationUri(context.getContentResolver(), uri);
            return cursor;
        } else {
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public String getType(@NonNull Uri uri) {
        switch (MATCHER.match(uri)) {
            case CODE_MENU_DIR:
                return "vnd.android.cursor.dir/" + AUTHORITY + "." + "favorite_movie";
            case CODE_MENU_ITEM:
                return "vnd.android.cursor.item/" + AUTHORITY + "." + "favorite_movie";
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        switch (MATCHER.match(uri)) {
            case CODE_MENU_DIR:
                final Context context = getContext();
                if (context == null) {
                    return null;
                }
                final int id = values.getAsInteger("id");

                AppDatabase.initDatabase(context).movieDAO()
                        .insertMovie(MovieModelDb.fromContentValues(values));
                return ContentUris.withAppendedId(uri, id);

            case CODE_MENU_ITEM:
                throw new IllegalArgumentException("Invalid URI, cannot insert with ID: " + uri);
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    //    dibawah ini masih opsional
    @NonNull
    @Override
    public ContentProviderResult[] applyBatch(
            @NonNull ArrayList<ContentProviderOperation> operations)
            throws OperationApplicationException {
        final Context context = getContext();
        if (context == null) {
            return new ContentProviderResult[0];
        }
        final AppDatabase database = AppDatabase.initDatabase(context);
        database.beginTransaction();
        try {
            final ContentProviderResult[] result = super.applyBatch(operations);
            database.setTransactionSuccessful();
            return result;
        } finally {
            database.endTransaction();
        }
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] valuesArray) {
        switch (MATCHER.match(uri)) {
            case CODE_MENU_DIR:
                final Context context = getContext();
                if (context == null) {
                    return 0;
                }
                final AppDatabase database = AppDatabase.initDatabase(context);
                final MovieModelDb[] movie = new MovieModelDb[valuesArray.length];
                for (int i = 0; i < valuesArray.length; i++) {
                    movie[i] = MovieModelDb.fromContentValues(valuesArray[i]);
                }
                return database.movieDAO().insertAll(movie).length;
            case CODE_MENU_ITEM:
                throw new IllegalArgumentException("Invalid URI, cannot insert with ID: " + uri);
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }
}
