package com.example.toeicvocaapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.toeicvocaapp.R;
import com.example.toeicvocaapp.model.Topic;
import com.example.toeicvocaapp.model.Vocabulary;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "vocab.db";
    private static final int DB_VERSION = 1;

    // Bảng và cột
    private static final String TABLE_TOPICS = "Topics";
    private static final String TABLE_VOCAB = "Vocabulary";
    private static final String TABLE_PROGRESS = "LearningProgress";
    private static final String COL_ID = "id";
    private static final String COL_NAME = "name";
    private static final String COL_TOPIC_ID = "topic_id";
    private static final String COL_ENGLISH = "english";
    private static final String COL_VIETNAMESE = "vietnamese";
    private static final String COL_WORD_ID = "word_id";
    private static final String COL_DATE = "date";
    private static final String COL_STATUS = "status";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng Topics
        db.execSQL("CREATE TABLE " + TABLE_TOPICS + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAME + " TEXT)");
        // Tạo bảng Vocabulary
        db.execSQL("CREATE TABLE " + TABLE_VOCAB + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_TOPIC_ID + " INTEGER, " +
                COL_ENGLISH + " TEXT, " +
                COL_VIETNAMESE + " TEXT)");
        // Tạo bảng LearningProgress
        db.execSQL("CREATE TABLE " + TABLE_PROGRESS + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_WORD_ID + " INTEGER, " +
                COL_DATE + " TEXT, " +
                COL_STATUS + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TOPICS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VOCAB);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROGRESS);
        onCreate(db);
    }

    // Thêm chủ đề
    public void addTopic(String name) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAME, name);
        db.insert(TABLE_TOPICS, null, values);
        db.close();
    }

    // Lấy danh sách chủ đề
    public List<Topic> getAllTopics() {
        List<Topic> topics = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_TOPICS, new String[]{COL_ID, COL_NAME}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            topics.add(new Topic(cursor.getInt(0), cursor.getString(1)));
        }
        cursor.close();
        db.close();
        return topics;
    }

    // Thêm từ vựng
    public void addVocabulary(int topicId, String english, String vietnamese) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_TOPIC_ID, topicId);
        values.put(COL_ENGLISH, english);
        values.put(COL_VIETNAMESE, vietnamese);
        db.insert(TABLE_VOCAB, null, values);
        db.close();
    }

    // Lấy từ vựng theo chủ đề
    public List<Vocabulary> getVocabByTopic(int topicId) {
        List<Vocabulary> vocabList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_VOCAB, new String[]{COL_ID, COL_ENGLISH, COL_VIETNAMESE},
                COL_TOPIC_ID + "=?", new String[]{String.valueOf(topicId)}, null, null, null);
        while (cursor.moveToNext()) {
            vocabList.add(new Vocabulary(cursor.getInt(0), cursor.getString(1), cursor.getString(2)));
        }
        cursor.close();
        db.close();
        return vocabList;
    }

    // Lưu tiến độ học
    public void addProgress(int wordId, String date) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_WORD_ID, wordId);
        values.put(COL_DATE, date);
        values.put(COL_STATUS, "learned");
        db.insert(TABLE_PROGRESS, null, values);
        db.close();
    }

    // Đếm số từ đã học hôm nay
    public int countWordsToday(String date) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_PROGRESS, new String[]{COL_ID},
                COL_DATE + "=?", new String[]{date}, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }

    public List<Vocabulary> getLearnedWords(int topicId) {
        List<Vocabulary> learnedWords = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT v." + COL_ID + ", v." + COL_ENGLISH + ", v." + COL_VIETNAMESE +
                " FROM " + TABLE_VOCAB + " v INNER JOIN " + TABLE_PROGRESS + " p ON v." + COL_ID + "=p." + COL_WORD_ID +
                " WHERE v." + COL_TOPIC_ID + "=?", new String[]{String.valueOf(topicId)});
        while (cursor.moveToNext()) {
            learnedWords.add(new Vocabulary(cursor.getInt(0), cursor.getString(1), cursor.getString(2)));
        }
        cursor.close();
        db.close();
        return learnedWords;
    }

    public void initFromJson(Context context) {
        try {
            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_TOPICS, null);
            cursor.moveToFirst();
            if (cursor.getInt(0) > 0) { // Không thêm nếu đã có dữ liệu
                cursor.close();
                db.close();
                return;
            }
            cursor.close();

            // Đọc file JSON từ res/raw
            InputStream is = context.getResources().openRawResource(R.raw.sample_data);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");

            // Phân tích JSON
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject topicObj = jsonArray.getJSONObject(i);
                String topicName = topicObj.getString("topic");
                addTopic(topicName);
                int topicId = i + 1; // Giả sử ID bắt đầu từ 1

                JSONArray words = topicObj.getJSONArray("words");
                for (int j = 0; j < words.length(); j++) {
                    JSONObject word = words.getJSONObject(j);
                    String english = word.getString("english");
                    String vietnamese = word.getString("vietnamese");
                    addVocabulary(topicId, english, vietnamese);
                }
            }
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Error loading JSON: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

}


