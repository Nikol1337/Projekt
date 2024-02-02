package com.example.projekt;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class TestActivity extends AppCompatActivity  {


    final static int TEST_ID = R.id.navigation_test;

    final static int SLOWNIK_ID = R.id.navigation_slownik;
    final static int SLOWKA_ID = R.id.navigation_slowka;
    final static  int HOME_ID = R.id.navigation_home;

    private BookContainer przeslanyObiekt;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Log.d("TagInfo", "odpala sie test activity");
        // Usuń warunek sprawdzający null przed inicjalizacją
        przeslanyObiekt = new BookContainer(Parcel.obtain());

        Intent intent = getIntent();
        if (intent != null) {
            przeslanyObiekt = intent.getParcelableExtra("obiekt3");
            if (przeslanyObiekt == null) {
                Log.d("TagInfo", "przeslanyObiekt jest nullem w TestActivity");
            }
        }


        TestSelectionFragment fragment = new TestSelectionFragment(przeslanyObiekt);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment); // R.id.fragmentContainer to id miejsca w layout, gdzie chcesz umieścić fragment
        transaction.commit();

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.getMenu().findItem(R.id.navigation_test).setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int itemId = menuItem.getItemId();
                if(itemId==HOME_ID) {
                    Intent intent2 = new Intent(TestActivity.this, MenuActivity.class);
                    // Przesyłamy dane fiszek do nowego activity
                    intent2.putExtra("obiekt", przeslanyObiekt);
                    // Uruchamiamy nowe activity
                    startActivity(intent2);
                }else if(itemId==TEST_ID){
                    goToTestActivity();
                }else if(itemId==SLOWNIK_ID){
                    goToMainActivity();
                }else if(itemId==SLOWKA_ID){
                    Intent intent = new Intent(TestActivity.this, VocabularyListActivity.class);
                    intent.putExtra("obiekt2", przeslanyObiekt);
                    // Tutaj możesz przekazać dodatkowe dane za pomocą putExtra, jeśli są potrzebne
                    startActivity(intent);
                }

                return true;
            }

        });


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

}

