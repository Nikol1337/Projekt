package com.example.projekt;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class VocabularyListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private VocabularyListAdapter adapter;
    private BookContainer przeslanyObiekt;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private BottomNavigationView bottomNavigationView;
    final static int TEST_ID = R.id.navigation_test;

    final static int SLOWNIK_ID = R.id.navigation_slownik;
    final static int SLOWKA_ID = R.id.navigation_slowka;
    final static  int HOME_ID = R.id.navigation_home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("TagInfo","odpalamy vocabularylistactivity");
        setContentView(R.layout.activity_vacabulary_list);
        Log.d("TagInfo","odpalamy vocabularylistactivity");
        Intent intent = getIntent();
        przeslanyObiekt = null;
        if (intent != null) {
            przeslanyObiekt = intent.getParcelableExtra("obiekt2");
            if(przeslanyObiekt!=null)
            Log.d("TagInfo", String.valueOf(przeslanyObiekt.getVocabularyList().size()));
            else Log.d("TagInfo","Null przyslany zostal");

        }

        // Sprawdzenie, czy obiekt został przesłany
        if (przeslanyObiekt == null) {
            Log.d("TagInfo","przeslanybobiekt jest nullem");
            przeslanyObiekt = new BookContainer(Parcel.obtain()); // lub użyj odpowiedniej metody inicjalizacji
        }

        Toolbar toolbar = findViewById(R.id.toolbarVocabularyList);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recyclerviewVocabularyList);
        adapter = new VocabularyListAdapter(przeslanyObiekt.getVocabularyList());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        drawerLayout = findViewById(R.id.drawerLayout);
        toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                R.string.open_drawer,
                R.string.close_drawer
        );

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = findViewById(R.id.navigationView);

        Menu menu = navigationView.getMenu();
        MenuItem naukaMenuItem = menu.findItem(R.id.nav_settings);
        SpannableString s = new SpannableString("Nauka");
        s.setSpan(new TextAppearanceSpan(this, R.style.NavigationViewTextStyle), 0, s.length(), 0);
        naukaMenuItem.setTitle(s);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {


                if(menuItem.getItemId()==R.id.nav_home) {
                    Intent intent = new Intent(VocabularyListActivity.this, MainActivity.class);
                    intent.putExtra("obiekt2", przeslanyObiekt);
                    startActivity(intent);
                    // Obsługa dla elementu "Słownik-Wyszukaj słówko"
                    // ...
                }

                  else if(menuItem.getItemId()==R.id.nav_settings) {



                    SubMenu subMenu = menuItem.getSubMenu();


                        boolean isSubMenuVisible = subMenu.hasVisibleItems();
                        // Zmień widoczność menu
                        subMenu.setGroupVisible(R.id.submenu_group, !isSubMenuVisible);


                    return true;
                    // ...
                }
                else if(menuItem.getItemId()==R.id.fiszki){
                    Intent intent2 = new Intent(VocabularyListActivity.this, FlashcardActivity.class);
                    // Przesyłamy dane fiszek do nowego activity
                    intent2.putExtra("flashcards", przeslanyObiekt);
                    startActivity(intent2);



                }else if(menuItem.getItemId()==R.id.gra){
                    GoToGame();





                }

                    return true;

        }});

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.getMenu().findItem(R.id.navigation_slowka).setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int itemId = menuItem.getItemId();
                if(itemId==HOME_ID) {
                    Intent intent = new Intent(VocabularyListActivity.this, MenuActivity.class);
                    intent.putExtra("obiekt", przeslanyObiekt);
                    // Tutaj możesz przekazać dodatkowe dane za pomocą putExtra, jeśli są potrzebne
                    startActivity(intent);
                }else if(itemId==TEST_ID){
                    goToTestActivity();
                }else if(itemId==SLOWNIK_ID){
                    goToMainActivity();
                }

                return true;
            }

        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Obsługa kliknięcia ikony hamburgera w pasku akcji
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void GoToGame(){

        Intent intent=new Intent(this,GameActivity.class);
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
    private void goToMainActivity() {
        // Utwórz intent do przejścia do MainActivity
        Intent intent = new Intent(this, MainActivity.class);


        intent.putExtra("obiekt2", przeslanyObiekt);


        // Dodaj dodatkowe informacje, jeśli są potrzebne

        // Uruchom nową aktywność
        startActivity(intent);
    }
    public class VocabularyListAdapter extends RecyclerView.Adapter<VocabularyListAdapter.VocabularyViewHolder> {

        private List<Vocabulary> vocabularyList;


        // Konstruktor, aby przekazać dane do adaptera
        public VocabularyListAdapter(List<Vocabulary> vocabularyList) {
            this.vocabularyList = vocabularyList;

        }

        @NonNull
        @Override
        public VocabularyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vocabulary_list_item, parent, false);
            return new VocabularyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull VocabularyViewHolder holder, int position) {
            Vocabulary vocabulary = vocabularyList.get(position);


            // Ustawianie danych dla pojedynczego elementu listy
            holder.textViewVocabularyWord.setText(vocabulary.getWord());
            holder.textViewVocabularyTranslation.setText(vocabulary.getTranslation());
            holder.fabButton.setTag(vocabulary);
            Log.d("Dupa",vocabulary.getWord());
            if(MenuActivity.appDatabase.interfejsDAO().getUrlPoNazwie(vocabulary.getWord())!=null){

                Picasso.get()
                        .load(MenuActivity.appDatabase.interfejsDAO().getUrlPoNazwie(vocabulary.getWord()))// Opcjonalnie: dostosuj rozmiar obrazu// Opcjonalnie: dostosuj sposób kadrowania obrazu
                        .into(holder.fabButton);

                holder.fabButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        ViewGroup.LayoutParams nazwa = holder.zdjecie.getLayoutParams();
                        float dpvalue = 200;
                        float scale = getResources().getDisplayMetrics().density;
                        int pixeles = (int)(dpvalue*scale+0.5f);
                        nazwa.width = pixeles;
                        nazwa.height =pixeles;
                        holder.zdjecie.setLayoutParams(nazwa);
                        holder.zdjecie.requestLayout();
                        Picasso.get()
                                .load(MenuActivity.appDatabase.interfejsDAO().getUrlPoNazwie(vocabulary.getWord()))// Opcjonalnie: dostosuj rozmiar obrazu// Opcjonalnie: dostosuj sposób kadrowania obrazu
                                .resize(200,200)
                                .into(holder.zdjecie);

                        if(holder.zdjecie.getVisibility() == View.VISIBLE) {
                            holder.zdjecie.setVisibility(View.INVISIBLE);
                            nazwa.height=30;

                           holder.zdjecie.setLayoutParams(nazwa);

                        } else {
                            holder.zdjecie.setVisibility(View.VISIBLE);
                        }
                        //holder.zdjecie.setVisibility(View.VISIBLE);


                    }
                });

            }
        }

        @Override
        public int getItemCount() {
            return vocabularyList.size();
        }

        // Klasa ViewHolder, reprezentująca pojedynczy element listy
        public  class VocabularyViewHolder extends RecyclerView.ViewHolder {
            TextView textViewVocabularyWord;
            TextView textViewVocabularyTranslation;
            FloatingActionButton fabButton;

            ImageView zdjecie;
            private void GoToCamera()
            {
                Intent intent=new Intent(itemView.getContext(),CameraActivity.class);
                intent.putExtra("id",MenuActivity.appDatabase.interfejsDAO().getID(textViewVocabularyWord.getText().toString()));
                itemView.getContext().startActivity(intent);

            }
            public  VocabularyViewHolder(@NonNull View itemView) {
                super(itemView);
Log.d("TagInfo","Wypisuje slowo");
                textViewVocabularyWord = itemView.findViewById(R.id.book_title);
                textViewVocabularyTranslation = itemView.findViewById(R.id.book_author);

                fabButton = itemView.findViewById(R.id.fab);
                zdjecie = itemView.findViewById(R.id.centerImageView);

                // Dodaj obsługę kliknięcia do przycisku

                fabButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Tutaj umieść kod, który ma zostać wykonany po kliknięciu na przycisk fab
                        // Przykładowy kod:
                        Toast.makeText(view.getContext(), "Kliknięto przycisk fab"+textViewVocabularyWord.getText(), Toast.LENGTH_SHORT).show();
                        GoToCamera();
                    }
                });
            }

        }
    }
}
