package com.example.projekt;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class MainActivity extends AppCompatActivity {
   // public static final String IMAGE_URL_BASE = "http://covers.openlibrary.org/b/id/";
    public static final String PONS_API_URL = "https://api.pons.com/v1/";
    public static final String IMAGE_URL_BASE = "https://api.pons.com/v1/dictionary";
    public BookContainer baza;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();

        if (intent != null) {
            baza = intent.getParcelableExtra("obiekt2");
            // Log.d("TagInfo",przeslanyObiekt.getVocabularyList().get(0).getWord());
        }
        if (baza == null) {
            Log.d("TagInfo","przeslanybobiekt jest nullem w mainactivity");
            baza = new BookContainer(Parcel.obtain());

            List<Slowko> vocabularyList = MenuActivity.appDatabase.interfejsDAO().getAllData();
            Log.d("TagInfo", String.valueOf(baza.getVocabularyList().size()) + "YUUUU");
            for (Slowko slowozbazy : vocabularyList) {
                baza.addWordToVocabulary(slowozbazy.getWord(), slowozbazy.getTranslation());
            }
        }
            Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        findViewById(R.id.buttonGoToMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMenuActivity();
            } });

    }
    private void openMenuActivity() {
        Intent intent = new Intent(this, VocabularyListActivity.class);

        intent.putExtra("obiekt2", baza);

        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.book_menu, menu);
        Log.d("TagInfo", "Jestem w search");
        MenuItem searchItem = menu.findItem(R.id.menu_item_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("TagInfo", "Jebac");
                fetchBooksData(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void fetchBooksData(String query) {
        String finalQuery = prepareQuery(query);
        BookService ponsService = RetrofitInstance.getRetrofitInstance().create(BookService.class);
        Call<List<ApiResponse>> booksApiCall = ponsService.findBooks("enpl", finalQuery);

        booksApiCall.enqueue(new Callback<List<ApiResponse>>() {
            @Override
            public void onResponse(Call<List<ApiResponse>> call, Response<List<ApiResponse>> response) {
                if (response.isSuccessful()) {
                    List<ApiResponse> apiResponses = response.body();
                    if (apiResponses != null && !apiResponses.isEmpty()) {
                        // Poprawna odpowiedź, przetwarzaj dane
                        setupPonsListView(apiResponses.get(0).getHits());
                    } else {
                        // Błąd w formacie danych
                        handleErrorResponse();
                    }
                } else {
                    // Błąd HTTP (np. 404 Not Found)
                    handleHttpError(response.code());
                }
            }
            /*
            public void onResponse(Call<List<ApiResponse>> call, Response<List<ApiResponse>> response) {
                if (response.isSuccessful()) {
                    List<ApiResponse> apiResponses = response.body();
                    if (apiResponses != null && !apiResponses.isEmpty()) {
                        // Poprawna odpowiedź, przetwarzaj dane
                        for (ApiResponse apiResponse : apiResponses) {
                            for (Hit hit : apiResponse.getHits()) {
                                for (Rom rom : hit.getRoms()) {
                                    for (Arab arab : rom.getArabs()) {
                                        Log.d("ApiResponse", "Arab: " + arab.toString());
                                        for (Translation translation : arab.getTranslations()) {
                                            Log.d("ApiResponse", "Translation: " + translation.toString());
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        // Błąd w formacie danych
                        handleErrorResponse();
                    }
                } else {
                    // Błąd HTTP (np. 404 Not Found)
                    handleHttpError(response.code());
                }
            }
*/

            // ... pozostała część kodu



        private void handleErrorResponse() {
                Snackbar.make(findViewById(R.id.main_view),
                        getString(R.string.invalid_response),
                        Snackbar.LENGTH_LONG).show();
            }

            private void handleHttpError(int httpCode) {
                Snackbar.make(findViewById(R.id.main_view),
                        getString(R.string.http_error),
                        Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<List<ApiResponse>> call, Throwable t) {
                Log.e("API Failure", "Błąd zapytania: " + t.getMessage());
                Snackbar.make(findViewById(R.id.main_view),
                        getString(R.string.book_download_failure),
                        Snackbar.LENGTH_LONG).show();
            }
        });
    }


    private String prepareQuery(String query) {
        String[] queryParts = query.split("\\s+");
        return TextUtils.join("+", queryParts);
    }
    private void setupPonsListView(List<Hit> hits) {
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final BookAdapter adapter = new BookAdapter();
        adapter.setHits(hits);  // Zmiana setApiResponses na setHits
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }




    /*
    private void setupBookListView(List<Book> books) {
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final BookAdapter adapter = new BookAdapter();
        adapter.setBooks(books);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }*/
    public boolean checkNullOrEmpty(String text) {
        return text != null && !TextUtils.isEmpty(text);
    }


    private class BookHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView headwordTextView;
        private TextView translationTextView;
        private Hit hit;

        public BookHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.book_list_item, parent, false));
            itemView.setOnClickListener(this);
            FloatingActionButton fab = itemView.findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   Log.d("TagInfo",headwordTextView.getText().toString()+translationTextView.getText().toString());
                    Vocabulary vocabulary = new Vocabulary(headwordTextView.getText().toString(),translationTextView.getText().toString());
                baza.addWordToVocabulary(headwordTextView.getText().toString(),translationTextView.getText().toString());
                Slowko vocabularyEntity = Slowko.fromVocabulary(vocabulary);
                    MenuActivity.appDatabase.interfejsDAO().insert(vocabularyEntity);
TextView tekst=itemView.findViewById(R.id.textView);
                    Snackbar snackbar = Snackbar.make(view, "Dodane", Snackbar.LENGTH_LONG);

                   // tekst.setText("Dodane!");
                    snackbar.show();
                }
            });
            headwordTextView = itemView.findViewById(R.id.book_title);
            translationTextView = itemView.findViewById(R.id.book_author);
        }

        public void bind(Hit hit) {
            if (hit != null && hit.getRoms() != null) {
                this.hit = hit;
                headwordTextView.setText(getSourceWord());

                // Uwaga: Tutaj zakładam, że poniżej to pierwsze tłumaczenie, możesz dostosować to do rzeczywistych danych
                List<Rom> roms = hit.getRoms();
                if (roms != null && !roms.isEmpty()) {
                    List<Arab> arabs = roms.get(0).getArabs();
                    if (arabs != null && !arabs.isEmpty()) {
                        List<Translation> translations = arabs.get(0).getTranslations();
                        if (translations != null && !translations.isEmpty()) {
                            StringBuilder translationText = new StringBuilder();
                            Translation firstTranslation = translations.get(0);
                            translationTextView.setText(firstTranslation.getCleanedTarget());
                            Log.d("TagInfo","dom"+"house");
                           // baza.addWordToVocabulary("dom","house");
                           // for (Translation translation : translations) {
                            //    translationText.append(translation.getCleanedTarget()).append(", ");
                          //  }
                            // Usuń ostatnią przecinkę i spację
                            if (translationText.length() > 2) {
                                translationText.setLength(translationText.length() - 2);
                            }
                            //translationTextView.setText(translationText.toString());
                        } else {
                            translationTextView.setText("");
                        }
                    } else {
                        translationTextView.setText("");
                    }
                } else {
                    translationTextView.setText("");
                }
            }
        }

        private String getSourceWord() {
            List<Rom> roms = hit.getRoms();
            if (roms != null && !roms.isEmpty()) {
                List<Arab> arabs = roms.get(0).getArabs();
                if (arabs != null && !arabs.isEmpty()) {
                    List<Translation> translations = arabs.get(0).getTranslations();
                    if (translations != null && !translations.isEmpty()) {
                        return translations.get(0).getCleanedSource();
                    }
                }
            }
            return "";
        }

        @Override
        public void onClick(View v) {
            // Obsługa kliknięcia
        }
    }






    public class BookAdapter extends RecyclerView.Adapter<BookHolder> {
        private List<Hit> hits;  // Dodaj to pole

        @NonNull
        @Override
        public BookHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new BookHolder(getLayoutInflater(), parent);
        }

        @Override
        public void onBindViewHolder(@NonNull BookHolder holder, int position) {
            if (hits != null) {
                Hit hit = hits.get(position);
                holder.bind(hit);
            } else {
                Log.d("MainActivity", "No hits");
            }
        }

        @Override
        public int getItemCount() {
            if (hits != null)
                return hits.size();
            return 0;
        }

        void setHits(List<Hit> hits) {
            this.hits = hits;
            if (hits != null && !hits.isEmpty()) {
                Hit firstHit = hits.get(0);
                Log.d("TagInfo", "Ustawienie Hit: " +
                        "Type: " + firstHit.getType());
            }
            notifyDataSetChanged();
        }
    }




}