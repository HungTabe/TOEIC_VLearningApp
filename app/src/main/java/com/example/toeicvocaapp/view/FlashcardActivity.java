package com.example.toeicvocaapp.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.toeicvocaapp.R;
import com.example.toeicvocaapp.db.DatabaseHelper;
import com.example.toeicvocaapp.model.Vocabulary;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.example.toeicvocaapp.viewmodel.VocabularyViewModel;
import com.google.android.material.card.MaterialCardView;

public class FlashcardActivity extends AppCompatActivity {
    private TextView wordText;
    private EditText inputAnswer;
    private Button flipButton, checkButton, nextButton;
    private MaterialCardView flashcardView; // Khai báo biến flashcardView
    private VocabularyViewModel viewModel;
    private List<Vocabulary> vocabList;
    private int currentIndex = 0;
    private boolean isEnglishToVietnamese = true;
    private int topicId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard);

        // Ánh xạ các view
        wordText = findViewById(R.id.wordText);
        inputAnswer = findViewById(R.id.inputAnswer);
        flipButton = findViewById(R.id.flipButton);
        checkButton = findViewById(R.id.checkButton);
        nextButton = findViewById(R.id.nextButton);
        flashcardView = findViewById(R.id.flashcardView); // Ánh xạ flashcardView
        topicId = getIntent().getIntExtra("topic_id", -1);

        viewModel = new ViewModelProvider(this).get(VocabularyViewModel.class);
        viewModel.loadVocabByTopic(topicId);
        viewModel.getVocabList().observe(this, vocab -> {
            vocabList = vocab;
            if (vocabList.isEmpty()) {
                Toast.makeText(this, "No vocabulary available!", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }
            showWord();
        });

        viewModel.getTodayWordCount().observe(this, count -> {
            if (count >= 10) {
                Toast.makeText(this, "You've learned 10 words today!", Toast.LENGTH_LONG).show();
                finish();
            }
        });

        flipButton.setOnClickListener(v -> {
            Vocabulary vocab = vocabList.get(currentIndex);
            wordText.setText(isEnglishToVietnamese ? vocab.getVietnamese() : vocab.getEnglish());
            isEnglishToVietnamese = !isEnglishToVietnamese;

            // Hiệu ứng lật
            flashcardView.animate()
                    .rotationY(90)
                    .setDuration(200)
                    .withEndAction(() -> {
                        flashcardView.setRotationY(-90);
                        flashcardView.animate()
                                .rotationY(0)
                                .setDuration(200)
                                .start();
                    })
                    .start();
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
            viewModel.addProgress(vocabList.get(currentIndex).getId());
            currentIndex++;
            if (currentIndex >= vocabList.size()) {
                Toast.makeText(this, "No more words!", Toast.LENGTH_SHORT).show();
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
