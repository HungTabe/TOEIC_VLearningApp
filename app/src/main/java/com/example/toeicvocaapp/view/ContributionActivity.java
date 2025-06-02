package com.example.toeicvocaapp.view;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.toeicvocaapp.R;
import com.example.toeicvocaapp.viewmodel.VocabularyViewModel;

import java.util.Arrays;
import java.util.Calendar;

public class ContributionActivity extends AppCompatActivity {
    private VocabularyViewModel viewModel;
    private Spinner monthSpinner, yearSpinner;
    private GridLayout contributionGrid;
    private TextView contributionTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contribution);

        contributionTitle = findViewById(R.id.contributionTitle);
        monthSpinner = findViewById(R.id.monthSpinner);
        yearSpinner = findViewById(R.id.yearSpinner);
        contributionGrid = findViewById(R.id.contributionGrid);

        viewModel = new ViewModelProvider(this).get(VocabularyViewModel.class);

        // Setup spinners
        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        ArrayAdapter<String> monthAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, months);
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthSpinner.setAdapter(monthAdapter);

        Integer[] years = {2023, 2024, 2025};
        ArrayAdapter<Integer> yearAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, years);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(yearAdapter);

        // Set default to current month/year
        Calendar calendar = Calendar.getInstance();
        monthSpinner.setSelection(calendar.get(Calendar.MONTH));
        yearSpinner.setSelection(Arrays.asList(years).indexOf(calendar.get(Calendar.YEAR)));

        // Update grid when spinner changes
        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateContributionGrid();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateContributionGrid();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        updateContributionGrid();
    }

    private void updateContributionGrid() {
        contributionGrid.removeAllViews();
        int month = monthSpinner.getSelectedItemPosition();
        int year = (Integer) yearSpinner.getSelectedItem();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, 1);
        int maxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        // Add empty cells for days before the 1st
        for (int i = 1; i < firstDayOfWeek; i++) {
            View emptyView = new View(this);
            emptyView.setLayoutParams(new GridLayout.LayoutParams(
                    GridLayout.spec(GridLayout.UNDEFINED, 1f),
                    GridLayout.spec(GridLayout.UNDEFINED, 1f)));
            emptyView.setMinimumWidth(20);
            emptyView.setMinimumHeight(20);
            contributionGrid.addView(emptyView);
        }

        // Fetch contribution dates
        viewModel.getContributionDates(year, month + 1).observe(this, dates -> {
            for (int day = 1; day <= maxDays; day++) {
                String date = String.format("%04d-%02d-%02d", year, month + 1, day);
                View cell = new View(this);
                cell.setLayoutParams(new GridLayout.LayoutParams(
                        GridLayout.spec(GridLayout.UNDEFINED, 1f),
                        GridLayout.spec(GridLayout.UNDEFINED, 1f)));
                cell.setMinimumWidth(20);
                cell.setMinimumHeight(20);

                if (dates.contains(date)) {
                    boolean completedTest = viewModel.hasCompletedTest(date);
                    cell.setBackgroundColor(completedTest ? getResources().getColor(R.color.green_dark) : getResources().getColor(R.color.green_light));
                } else {
                    cell.setBackgroundColor(getResources().getColor(R.color.gray));
                }

                contributionGrid.addView(cell);
            }
        });
    }
}