package com.example.toeicvocaapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.toeicvocaapp.R;
import com.example.toeicvocaapp.model.Vocabulary;

import java.util.List;

public class VocabularyAdapter extends RecyclerView.Adapter<VocabularyAdapter.ViewHolder> {
    private List<Vocabulary> vocabList;

    public VocabularyAdapter(List<Vocabulary> vocabList) {
        this.vocabList = vocabList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_vocabulary, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Vocabulary vocab = vocabList.get(position);
        holder.englishTextView.setText(vocab.getEnglish());
        holder.vietnameseTextView.setText(vocab.getVietnamese());
    }

    @Override
    public int getItemCount() {
        return vocabList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView englishTextView, vietnameseTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            englishTextView = itemView.findViewById(R.id.englishTextView);
            vietnameseTextView = itemView.findViewById(R.id.vietnameseTextView);
        }
    }
}
