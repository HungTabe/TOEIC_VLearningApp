package com.example.toeicvocaapp.provider;

import android.net.Uri;

public class VocabularyContract {
    public static final String AUTHORITY = "com.example.toeicvocaapp.provider";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_VOCABULARY = "vocabulary";

    public static class VocabularyEntry {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_VOCABULARY)
                .build();

        public static final String TABLE_NAME = "Vocabulary";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_TOPIC_ID = "topic_id";
        public static final String COLUMN_ENGLISH = "english";
        public static final String COLUMN_VIETNAMESE = "vietnamese";
    }
}
