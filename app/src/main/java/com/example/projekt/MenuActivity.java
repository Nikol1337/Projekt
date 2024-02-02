package com.example.projekt;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.SystemClock;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class MenuActivity extends AppCompatActivity implements ApiWordOfTheDay.Callback {
    BookContainer przeslanyObiekt;
    private RecyclerView recyclerView;
    private VocabularyListActivity.VocabularyListAdapter adapter;
    private Button buttonGoToVocabularyList;
    public static BazaDanych appDatabase;
    PendingIntent pendingIntent;
    private BottomNavigationView bottomNavigationView;

    final static int TEST_ID = R.id.navigation_test;

    final static int SLOWNIK_ID = R.id.navigation_slownik;
    final static int SLOWKA_ID = R.id.navigation_slowka;
    final static  int HOME_ID = R.id.navigation_home;
    private TextView slowkodnia;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setDailyNotificationAlarm();
        setContentView(R.layout.activity_menu);

/////Animacja na przycisk GoToMainActivity
        Animation slideInAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_in);
        View myView = findViewById(R.id.yt);
        myView.startAnimation(slideInAnimation);
        //////Animacje

        Button yt = findViewById(R.id.yt);
        yt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openYouTube();

            }
        });
        appDatabase = Room.databaseBuilder(getApplicationContext(), BazaDanych.class, "BazaDanychSlowka")
                .allowMainThreadQueries()
                // Ta linia jest używana tylko do celów demonstracyjnych, należy unikać pracy na głównym wątku.
                .build();

        List<Slowko> vocabularyList = MenuActivity.appDatabase.interfejsDAO().getAllData();

        if (przeslanyObiekt == null) {

           przeslanyObiekt = new BookContainer(Parcel.obtain());

        }
        for (Slowko slowozbazy: vocabularyList) {
            przeslanyObiekt.addWordToVocabulary(slowozbazy.getWord(),slowozbazy.getTranslation());
        }

        slowkodnia  = findViewById(R.id.textWordOfTheDay);
        ApiWordOfTheDay apiWordOfTheDay = new ApiWordOfTheDay(this);
        apiWordOfTheDay.execute();

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int itemId = menuItem.getItemId();
                if(itemId==HOME_ID) {
                    // Obsługa kliknięcia na zakładkę "Home"
                    // Przełącz się do odpowiedniego fragmentu lub aktywności
                    // Możesz użyć goToMainActivity(), aby przenieść się do MainActivity
                }else if(itemId==TEST_ID){
                    goToTestActivity();
                }else if(itemId==SLOWNIK_ID){
                    goToMainActivity();
                }else if(itemId==SLOWKA_ID){
                    Intent intent = new Intent(MenuActivity.this, VocabularyListActivity.class);
                    intent.putExtra("obiekt2", przeslanyObiekt);
                    // Tutaj możesz przekazać dodatkowe dane za pomocą putExtra, jeśli są potrzebne
                    startActivity(intent);
                }

                return true;
            }

        });}

    private void setDailyNotificationAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Ustaw alarm na wywoływanie co 24 godziny
        //long interval = AlarmManager.INTERVAL_FIFTEEN_MINUTES;
        long interval =  60 * 1000;
        long triggerTime = SystemClock.elapsedRealtime() + interval;
        Log.d("AlarmManager", "Ustawianie alarmu");
        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime, interval, pendingIntent);
        Log.d("AlarmManager", "Alarm ustawiony");
    }
    @Override
    public void onTaskCompleted(String result) {
        // Obsłuż wynik, na przykład, ustaw go w TextView
        slowkodnia.setText(result.replaceAll("^\\[\"|\"\\]$", ""));
    }
    private void goToMainActivity() {
        // Utwórz intent do przejścia do MainActivity
        Intent intent = new Intent(this, MainActivity.class);


        intent.putExtra("obiekt2", przeslanyObiekt);


        // Dodaj dodatkowe informacje, jeśli są potrzebne

        // Uruchom nową aktywność
        startActivity(intent);
    }
    private void goToTestActivity() {
        // Utwórz intent do przejścia do MainActivity
        Intent intent = new Intent(this, TestActivity.class);


        intent.putExtra("obiekt3", przeslanyObiekt);


        // Dodaj dodatkowe informacje, jeśli są potrzebne

        // Uruchom nową aktywność
        startActivity(intent);
    }
    public void openYouTube() {
        // Tworzymy Intencję do otwarcia aplikacji YouTube
        Intent intent = new Intent(Intent.ACTION_VIEW);

        // Ustawiamy URI wideo. Możesz użyć identyfikatora wideo lub pełnego URL-a wideo.
        String videoId = "8l9eyNTYTpw"; // Zastąp to odpowiednim identyfikatorem wideo lub URL-em
        Uri videoUri = Uri.parse("https://www.youtube.com/watch?v=" + videoId);

        // Ustawiamy URI dla intencji
        intent.setData(videoUri);

        // Sprawdzamy, czy użytkownik ma zainstalowaną aplikację YouTube
       // if (intent.resolveActivity(getPackageManager()) != null) {
            // Otwieramy aplikację YouTube
         //   startActivity(intent);
       // } else {
            // Jeśli YouTube nie jest zainstalowane, otwieramy przeglądarkę internetową
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + videoId));
            startActivity(intent);
       // }
    }



}