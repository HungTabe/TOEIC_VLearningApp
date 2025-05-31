package com.example.toeicvocaapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toeicvocaapp.db.DatabaseHelper;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TopicAdapter adapter;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //        prepare code
        db = new DatabaseHelper(this);

        db.initFromJson(this); // Nạp dữ liệu từ JSON

        recyclerView = findViewById(R.id.topicRecyclerView);
        Button addTopicButton = findViewById(R.id.addTopicButton);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        updateTopicList();

        addTopicButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddTopicActivity.class);
            startActivity(intent);
        });

        //        end
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateTopicList();
    }

    private void updateTopicList() {
        List<String> topics = db.getAllTopics();
        adapter = new TopicAdapter(topics, position -> {
            Intent intent = new Intent(MainActivity.this, VocabularyListActivity.class);
            intent.putExtra("topic_id", position + 1);
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);
    }
}