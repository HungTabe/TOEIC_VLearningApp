package com.example.toeicvocaapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toeicvocaapp.R;
import com.example.toeicvocaapp.adapter.TopicAdapter;
import com.example.toeicvocaapp.db.DatabaseHelper;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TopicAdapter adapter;
    private DatabaseHelper db;
    private TextView textViewWelcome;
    private Button buttonClose;

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

        textViewWelcome = findViewById(R.id.textViewWelcome);
        buttonClose = findViewById(R.id.buttonClose);

        // get data from intent LoginActivity
        String name = getIntent().getStringExtra("name");
        textViewWelcome.setText("Welcome, " + name + "!");

        buttonClose.setOnClickListener(v -> {
            finishAffinity(); // Close
        });

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