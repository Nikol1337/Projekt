package com.example.projekt;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcel;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FlashcardActivity extends AppCompatActivity {

        private RecyclerView recyclerView;
        private FlashcardAdapter adapter;
        private BookContainer przeslanyObiekt;
        private int currentIndex = 0;
        private GestureDetector gestureDetector;



    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_flashcard);

            Intent intent = getIntent();

            if (intent != null) {
                przeslanyObiekt = intent.getParcelableExtra("flashcards");
            }

            if (przeslanyObiekt == null) {
                przeslanyObiekt = new BookContainer(Parcel.obtain());
            }

            List<Vocabulary> flashcards = przeslanyObiekt.getVocabularyList();

            recyclerView = findViewById(R.id.recyclerView);
            adapter = new FlashcardAdapter(flashcards,this);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));

            PagerSnapHelper snapHelper = new PagerSnapHelper();
            snapHelper.attachToRecyclerView(recyclerView);



        MyGestureListener gestureListener = new MyGestureListener();
            gestureListener.setRunnable(new Runnable() {
                @Override
                public void run() {
                    gestureListener.isFlinging = false; // Ustawienie isFlinging z powrotem na false po opóźnieniu
                }
            });

            gestureDetector = new GestureDetector(this, new MyGestureListener());
            startFlashcards();
        }


    public void displayWord(int index) {
        adapter.displayWord(index);
    }
    private void startFlashcards() {
        if (currentIndex < adapter.getItemCount()) {
            Vocabulary initialFlashcard = adapter.getItem(currentIndex);
            adapter.setFlashcard(initialFlashcard);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event) || super.onTouchEvent(event);
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        private boolean isFlinging = false;
        private Handler handler = new Handler();
        private Runnable runnable;

        @Override
        public boolean onDown(MotionEvent e) {
            isFlinging = false;
            Log.d("TagInfo","Wykonuje onDown");
            return super.onDown(e);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY){
        synchronized (this) {
            if (!isFlinging) {
                float deltaX = e2.getX() - e1.getX();
                Log.d("TagInfo","Wykonuje OnFling");
                if (Math.abs(deltaX) > 100) {
                    isFlinging = true;

                    if (deltaX < 0) {
                        // Przewijanie w lewo
Log.d("TagInfo","Wykonam zaraz getItem Count");
                        if (currentIndex < adapter.getItemC() - 1) {
                            Log.d("TagInfo","Wykonuje OnFling w lewo przwijam");
                            currentIndex++;
                            displayWord(currentIndex);
                        }
                    } else {
                        // Przewijanie w prawo

                        if (currentIndex > 0) {
                            Log.d("TagInfo","Wykonuje OnFling w prawo przewijam");
                            currentIndex--;
                            displayWord(currentIndex);
                        }
                    }
                    handler.removeCallbacks(runnable);
                    handler.postDelayed(runnable, 1000);
                    return true;
                }
            }}

            return false;
        }
        // Dodaj tę metodę wewnątrz MyGestureListener
        public void setRunnable(Runnable runnable) {
            this.runnable = runnable;
        }
    }

}

