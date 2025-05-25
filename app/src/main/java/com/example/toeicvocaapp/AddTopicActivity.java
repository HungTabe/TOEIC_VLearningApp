package com.example.toeicvocaapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.toeicvocaapp.db.DatabaseHelper;

public class AddTopicActivity extends AppCompatActivity {
    private EditText topicNameEditText;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_topic);

        topicNameEditText = findViewById(R.id.topicNameEditText);
        Button saveTopicButton = findViewById(R.id.saveTopicButton);
        db = new DatabaseHelper(this);

        saveTopicButton.setOnClickListener(v -> {
            String topicName = topicNameEditText.getText().toString();
            if (!topicName.isEmpty()) {
                db.addTopic(topicName);
                finish();
            }
        });
    }
}
