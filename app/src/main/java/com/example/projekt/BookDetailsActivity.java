package com.example.projekt;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BookDetailsActivity extends AppCompatActivity {
    public static final String EXTRA_BOOK_DETAILS_TITLE = "com.modzel.BOOK_DETAILS_TITLE";
    public static final String EXTRA_BOOK_DETAILS_AUTHOR = "com.modzel.BOOK_DETAILS_AUTHOR";
    public static final String EXTRA_BOOK_DETAILS_COVER_ID = "com.modzel.BOOK_DETAILS_COVER_ID";
    private TextView titleTextView;
    private TextView authorTextView;
    private ImageView cover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        titleTextView = findViewById(R.id.book_title);
        authorTextView = findViewById(R.id.book_author);
        cover = findViewById(R.id.img_cover);

        Intent starter = getIntent();
        titleTextView.setText(starter.getStringExtra(EXTRA_BOOK_DETAILS_TITLE));
        authorTextView.setText(starter.getStringExtra(EXTRA_BOOK_DETAILS_AUTHOR));
        String coverId = starter.getStringExtra(EXTRA_BOOK_DETAILS_COVER_ID);






    }
}
