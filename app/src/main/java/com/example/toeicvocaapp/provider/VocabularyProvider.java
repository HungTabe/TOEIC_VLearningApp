package com.example.toeicvocaapp.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.example.toeicvocaapp.db.DatabaseHelper;

public class VocabularyProvider extends ContentProvider {
    private static final int VOCABULARY = 100;
    private static final int VOCABULARY_WITH_TOPIC = 101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sUriMatcher.addURI(VocabularyContract.AUTHORITY, VocabularyContract.PATH_VOCABULARY, VOCABULARY);
        sUriMatcher.addURI(VocabularyContract.AUTHORITY, VocabularyContract.PATH_VOCABULARY + "/#", VOCABULARY_WITH_TOPIC);
    }

    private DatabaseHelper dbHelper;

    @Override
    public boolean onCreate() {
        dbHelper = new DatabaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor;

        int match = sUriMatcher.match(uri);
        switch (match) {
            case VOCABULARY:
                cursor = db.query(VocabularyContract.VocabularyEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case VOCABULARY_WITH_TOPIC:
                selection = VocabularyContract.VocabularyEntry.COLUMN_TOPIC_ID + "=?";
                selectionArgs = new String[]{uri.getLastPathSegment()};
                cursor = db.query(VocabularyContract.VocabularyEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        int match = sUriMatcher.match(uri);
        switch (match) {
            case VOCABULARY:
                return "vnd.android.cursor.dir/vnd." + VocabularyContract.AUTHORITY + "." + VocabularyContract.PATH_VOCABULARY;
            case VOCABULARY_WITH_TOPIC:
                return "vnd.android.cursor.item/vnd." + VocabularyContract.AUTHORITY + "." + VocabularyContract.PATH_VOCABULARY;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        throw new UnsupportedOperationException("Insert not supported");
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException("Delete not supported");
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException("Update not supported");
    }
}
