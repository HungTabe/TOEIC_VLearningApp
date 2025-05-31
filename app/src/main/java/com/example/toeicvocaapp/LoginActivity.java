package com.example.toeicvocaapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Log.d("Lifecycle", "Activity được tạo!");


        editTextText = findViewById(R.id.editTextText);
        Button loginButton = findViewById(R.id.button);

        loginButton.setOnClickListener(v -> {
            String name = editTextText.getText().toString();
            if (!name.isEmpty()) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("name", name);
                startActivity(intent);
                finish();
            } else {
                editTextText.setError("Please enter your name");
            }
        });
    }

    // Khi app bị thu nhỏ rồi quay lại → clear dữ liệu
    @Override
    protected void onResume() {
        super.onResume();
        editTextText.setText(""); // Xóa nội dung mỗi khi mở lại
        Log.d("Lifecycle", "Activity sẵn sàng tương tác!");

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Lifecycle", "Activity bắt đầu hiển thị!");
    }
}

