package com.example.projekt;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;



public class FlashcardAdapter extends RecyclerView.Adapter<FlashcardAdapter.FlashcardViewHolder> {

    private List<Vocabulary> flashcards;
    private int currentWordIndex = 0;
    private Context context;

    public class FlashcardViewHolder extends RecyclerView.ViewHolder {
        public TextView wordTextView;
        public TextView translationTextView;

        public FlashcardViewHolder(View view) {
            super(view);
            wordTextView = view.findViewById(R.id.book_title);
            translationTextView = view.findViewById(R.id.book_author);
        }
    }

    public FlashcardAdapter(List<Vocabulary> flashcards, Context context) {

        this.flashcards = flashcards;
        Log.d("TagInfo","w konstruktorze mamy "+this.flashcards.size());
        this.context = context;
    }
    public void setFlashcard(Vocabulary flashcard) {
        //flashcards.clear();
        flashcards.add(flashcard);
        notifyDataSetChanged();
    }

    @Override
    public FlashcardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.flashcard_item, parent, false);

        return new FlashcardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FlashcardViewHolder holder, int position) {
        Vocabulary flashcard = flashcards.get(position);
        holder.wordTextView.setText(flashcard.getWord());
        holder.translationTextView.setText(flashcard.getTranslation());
    }
    public void displayWord(int index) {
        Vocabulary flashcard = flashcards.get(index);
        TextView wordTextView = ((FlashcardActivity) context).findViewById(R.id.book_title);
        TextView wordTextView2 = ((FlashcardActivity) context).findViewById(R.id.book_author);

        wordTextView.setText(flashcard.getWord());
        wordTextView2.setText(flashcard.getTranslation());
        Log.d("TagInfo",flashcard.getWord());
    }



    public int getItemC() {
        Log.d("TagInfo","jestem tyle slow-"+flashcards.size());
        return flashcards.size();
    }
    @Override
    public int getItemCount() {
        Log.d("TagInfo","jestem tyle slow w COUNT OVERRADIOWYM-"+flashcards.size());
        return flashcards.size();
    }
public Vocabulary getItem(int x){
        return flashcards.get(x);

}

}

