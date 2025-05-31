package com.example.toeicvocaapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.toeicvocaapp.db.DatabaseHelper;
import com.example.toeicvocaapp.model.Vocabulary;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FlashcardActivity extends AppCompatActivity {
    private TextView wordText;
    private EditText inputAnswer;
    private Button flipButton, checkButton, nextButton;
    private DatabaseHelper db;
    private List<Vocabulary> vocabList;
    private int currentIndex = 0;
    private boolean isEnglishToVietnamese = true;
    private int topicId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard);

        wordText = findViewById(R.id.wordText);
        inputAnswer = findViewById(R.id.inputAnswer);
        flipButton = findViewById(R.id.flipButton);
        checkButton = findViewById(R.id.checkButton);
        nextButton = findViewById(R.id.nextButton);
        db = new DatabaseHelper(this);
        topicId = getIntent().getIntExtra("topic_id", -1);

        // Kiểm tra giới hạn 10 từ/ngày
        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        if (db.countWordsToday(today) >= 10) {
            Toast.makeText(this, "You've learned 10 words today!", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        vocabList = db.getVocabByTopic(topicId);
        if (vocabList.isEmpty()) {
            Toast.makeText(this, "No vocabulary available!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        showWord();

        flipButton.setOnClickListener(v -> {
            Vocabulary vocab = vocabList.get(currentIndex);
            wordText.setText(isEnglishToVietnamese ? vocab.getVietnamese() : vocab.getEnglish());
            isEnglishToVietnamese = !isEnglishToVietnamese;
        });

        checkButton.setOnClickListener(v -> {
            String answer = inputAnswer.getText().toString().trim();
            Vocabulary vocab = vocabList.get(currentIndex);
            String correctAnswer = isEnglishToVietnamese ? vocab.getVietnamese() : vocab.getEnglish();
            if (answer.equalsIgnoreCase(correctAnswer)) {
                Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Wrong! Correct: " + correctAnswer, Toast.LENGTH_SHORT).show();
            }
        });

        nextButton.setOnClickListener(v -> {
            db.addProgress(vocabList.get(currentIndex).getId(), today);
            currentIndex++;
            if (currentIndex >= vocabList.size() || db.countWordsToday(today) >= 10) {
                Toast.makeText(this, "Done for today!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                showWord();
                inputAnswer.setText("");
            }
        });
    }

    private void showWord() {
        Vocabulary vocab = vocabList.get(currentIndex);
        wordText.setText(vocab.getEnglish());
        isEnglishToVietnamese = true;
    }
}
