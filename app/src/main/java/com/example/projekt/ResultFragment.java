package com.example.projekt;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class ResultFragment extends Fragment {

    private int totalQuestions;
    private int correctAnswers;
    private BookContainer przeslanyobiekt;
    public ResultFragment(BookContainer dbHelper) {
        this.przeslanyobiekt = dbHelper;



    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result, container, false);

        // Inicjalizacja elementów interfejsu użytkownika, np. TextView itp.

        // Wyświetlenie wyników
        TextView resultTextView = view.findViewById(R.id.resultTextView);
        resultTextView.setText("Your Score: " + correctAnswers + "/" + totalQuestions);


        ImageButton exitButton = view.findViewById(R.id.exitButton);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TagInfo","Jestemw exit");
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, new TestSelectionFragment(przeslanyobiekt))
                        .addToBackStack(null) // Jeśli chcesz dodawać do stosu cofania.
                        .commit();
            }
        });
        return view;
    }

    // Metoda, aby ustawić wyniki przed wyświetleniem fragmentu

    public void setResults(int totalQuestions, int correctAnswers) {
        this.totalQuestions = totalQuestions;
        this.correctAnswers = correctAnswers;

        // Jeśli chcesz, możesz od razu wyświetlić wyniki, gdy zostanie ustawiony fragment
        updateResultsView();
    }

    // Metoda do aktualizacji widoku wyników
    private void updateResultsView() {
        if (getView() != null) {
            TextView resultTextView = getView().findViewById(R.id.resultTextView);
            resultTextView.setText("Your Score: " + correctAnswers + "/" + totalQuestions);
        }
    }
}