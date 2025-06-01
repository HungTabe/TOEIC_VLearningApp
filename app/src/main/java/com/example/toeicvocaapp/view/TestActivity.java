package com.example.toeicvocaapp.view;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toeicvocaapp.R;
import com.example.toeicvocaapp.db.DatabaseHelper;
import com.example.toeicvocaapp.model.Vocabulary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestActivity extends AppCompatActivity {
    private TextView questionText;
    private RadioGroup answerGroup;
    private RadioButton option1, option2, option3, option4;
    private Button submitButton;
    private DatabaseHelper db;
    private List<Vocabulary> vocabList;
    private int currentQuestion = 0;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        questionText = findViewById(R.id.questionText);
        answerGroup = findViewById(R.id.answerGroup);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);
        submitButton = findViewById(R.id.submitButton);
        db = new DatabaseHelper(this);

        vocabList = db.getVocabByTopic(getIntent().getIntExtra("topic_id", -1));
        if (vocabList.size() < 10) {
            Toast.makeText(this, "Not enough words for a test!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        Collections.shuffle(vocabList);
        vocabList = vocabList.subList(0, 10);
        showQuestion();

        submitButton.setOnClickListener(v -> {
            int selectedId = answerGroup.getCheckedRadioButtonId();
            if (selectedId == -1) {
                Toast.makeText(this, "Please select an answer!", Toast.LENGTH_SHORT).show();
                return;
            }
            RadioButton selected = findViewById(selectedId);
            if (selected.getText().equals(vocabList.get(currentQuestion).getVietnamese())) {
                score++;
            }
            currentQuestion++;
            if (currentQuestion >= 10) {
                Toast.makeText(this, "Test finished! Score: " + score + "/10", Toast.LENGTH_LONG).show();
                finish();
            } else {
                showQuestion();
                answerGroup.clearCheck();
            }
        });
    }

    private void showQuestion() {
        Vocabulary vocab = vocabList.get(currentQuestion);
        questionText.setText(vocab.getEnglish());
        List<String> options = new ArrayList<>();
        options.add(vocab.getVietnamese());
        for (int i = 0; i < 3; i++) {
            options.add(vocabList.get((currentQuestion + i + 1) % vocabList.size()).getVietnamese());
        }
        Collections.shuffle(options);
        option1.setText(options.get(0));
        option2.setText(options.get(1));
        option3.setText(options.get(2));
        option4.setText(options.get(3));
    }
}
