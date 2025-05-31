package com.example.toeicvocaapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.toeicvocaapp.db.DatabaseHelper;

public class AddVocabularyActivity extends AppCompatActivity {
    private EditText englishEditText, vietnameseEditText;
    private DatabaseHelper db;
    private int topicId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vocabulary);

        englishEditText = findViewById(R.id.englishEditText);
        vietnameseEditText = findViewById(R.id.vietnameseEditText);
        Button saveVocabButton = findViewById(R.id.saveVocabButton);
        db = new DatabaseHelper(this);
        topicId = getIntent().getIntExtra("topic_id", -1);

        saveVocabButton.setOnClickListener(v -> {
            String english = englishEditText.getText().toString();
            String vietnamese = vietnameseEditText.getText().toString();
            if (!english.isEmpty() && !vietnamese.isEmpty()) {
                db.addVocabulary(topicId, english, vietnamese);
                finish();
            }
        });
    }
}
