package com.example.projekt;



// TestFragment.java

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TestFragment extends Fragment implements OnTestInterfaceListener {

    private List<Vocabulary> words; // Twoja lista słówek
    private BookContainer przeslanyobiekt;
    private int currentIndex;
    private int correctAnswers;

    private Button option1Button;
    private Button option2Button;
    TextView questionTextView;
    TextView efekt;
    boolean isCorrect;
    int odpowiedzianoNaPytanie=-1;
   private String selectedAnswer;
    ResultFragment resultFragment;
    private int totalQuestions = 0; // Inicjalizuj totalQuestions
    private int correctAnswers2 = 0; // Inicjalizuj correctAnswers
    private int MAX_QUESTIONS = 3;

   int losuj2;
   TestType type;
    List<Integer> myList = new ArrayList<>();

    public TestFragment(BookContainer dbHelper,TestType type) {
        this.przeslanyobiekt = dbHelper;
        resultFragment=new ResultFragment(przeslanyobiekt);
        if (przeslanyobiekt != null) {
            Log.d("TagInfo","Nie przyszzedl null baza");
            words = przeslanyobiekt.getVocabularyList();
        }
        this.type=type;
      uzupelnijListeWyboru();

    }
    public String GetQuestionWord(Vocabulary voc){
        if(type==TestType.PlEn)
        {
            return voc.getTranslation();
        }else if (type==TestType.EnPl){
            return voc.getWord();
        }else return " ";
    }
    public String GetOptionWord(Vocabulary voc){
        if(type==TestType.PlEn){
            return voc.getWord();
        }else if(type==TestType.EnPl){
            return voc.getTranslation();
        }else return " ";
    }
public String GetCorrectAnswer(Vocabulary voc){
        if(type==TestType.PlEn)
        {
            return voc.getWord();
        }else if(type==TestType.EnPl){
            return voc.getTranslation();

        }else return"";
}
public void uzupelnijListeWyboru(){
for(int i=0;i<words.size();i++){
    myList.add(i);
}
}
    // Metoda do losowego ustawiania słowa i opcji odpowiedzi
    public void setRandomWord() {
        odpowiedzianoNaPytanie=-1;
        Random random = new Random();
        Log.d("TagInfo","Wchodze do setrandomword");
        // Implementacja zależna od Twoich potrzeb
        if (words != null && words.size() > 0) {

            Collections.shuffle(myList); // Przemieszaj listę przed rozpoczęciem testu

            currentIndex = 0; // Ustaw indeks na pierwszy element

            // Teraz możesz używać currentIndex do pobierania kolejnych słów
             currentIndex= myList.get(currentIndex);



        }


            Vocabulary randomWord = words.get(currentIndex);
            Log.d("TagInfo","zainijcowalem zmienna randomword");
            // Tu możesz zaktualizować elementy interfejsu użytkownika, aby pokazać pytanie

            if (questionTextView != null) {
                Log.d("TagInfo","Ustawiam pytanie");
                questionTextView.setText(GetQuestionWord(randomWord));
            }


            // Zaktualizuj opcje odpowiedzi
            if (option1Button != null) {
                Vocabulary voc=words.get(currentIndex);
                Log.d("TagInfo","Ustawiam odpowiedz"+GetOptionWord(voc));
                option1Button.setText(GetOptionWord(voc));
           }
            Log.d("TagInfo","LALAALALALAL");
            losuj2=currentIndex;
            while(losuj2==currentIndex )
                losuj2=random.nextInt(words.size());
            if (option2Button != null) {
                Vocabulary voc2=words.get(losuj2);
                option2Button.setText(GetOptionWord(voc2));
            }

        }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, container, false);

        // Inicjalizacja elementów interfejsu użytkownika
        questionTextView = view.findViewById(R.id.questionTextView);
        option1Button = view.findViewById(R.id.option1Button);
        option2Button = view.findViewById(R.id.option2Button);
        efekt=view.findViewById(R.id.efekt);

        Log.d("TagInfo","jESTEM TUTAJ");
        // Ustaw pierwsze pytanie
        setRandomWord();

        Button nextButton = view.findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Sprawdź odpowiedź
                Log.d("TagInfo","Wcisnalem nextbutton");
                if(odpowiedzianoNaPytanie==-1) {
                    isCorrect = checkAnswer("Brak");
                }
                // Przekaż informację o poprawności odpowiedzi do aktywności

                   onAnswerSelected(isCorrect);

efekt.setText("");
                // Przejdź do kolejnego pytania
                setRandomWord();
            }
        });

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




        // Obsługa kliknięcia na opcję 1
        option1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tutaj dodaj logikę związaną z kliknięciem na opcję 1
                odpowiedzianoNaPytanie=1;
                Vocabulary voc=words.get(currentIndex);
                // Możesz skorzystać z metody checkAnswer() w zależności od potrzeb
               if( checkAnswer(GetOptionWord(voc))==true)
               {
efekt.setText("Doskonale");
               }
               else{
                   efekt.setText("Żle");
               }
            }
        });

        // Obsługa kliknięcia na opcję 2
        option2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                odpowiedzianoNaPytanie=1;
                // Tutaj dodaj logikę związaną z kliknięciem na opcję 2
                // Możesz skorzystać z metody checkAnswer() w zależności od potrzeb
Vocabulary voc=words.get(losuj2);
                if( checkAnswer(GetOptionWord(voc))==true)
                {
                    efekt.setText("Doskonale");
                }
                else{
                    efekt.setText("Żle");
                }
            }
        });

        return view;
    }

    // Implementacja metody do sprawdzania odpowiedzi
    private boolean checkAnswer(String selectedAnswer) {
        // Implementacja zależna od Twoich potrzeb
        // Poniżej znajdziesz przykładową implementację, dostosuj ją do logiki swojej aplikacji
        if (words != null && words.size() > 0) {
            Vocabulary currentWord = words.get(currentIndex);

            // Załóżmy, że masz odpowiedź przechowywaną w zmiennej selectedAnswer
            // Poniżej jest tylko przykład, dostosuj to do swojej logiki
            //String selectedAnswer = "Wybrana odpowiedź";

            // Sprawdź, czy wybrana odpowiedź jest równa poprawnej odpowiedzi
             isCorrect = selectedAnswer.equals(GetCorrectAnswer(currentWord));
Log.d("TagInfo","sprawdzam czy odpowiedzia poprawna jest to co wybralem--"+selectedAnswer);
            // Aktualizuj zmienną correctAnswers, jeśli odpowiedź jest poprawna
            if (isCorrect) {
                correctAnswers++;
            }

            // Zwróć true, jeśli odpowiedź jest poprawna, w przeciwnym razie false
            return isCorrect;
        }

        // Zwróć false w przypadku braku pytań lub problemu z danymi
        return false;
    }
    @Override
    public void onAnswerSelected(boolean isCorrect) {
        totalQuestions++;
        Log.d("TagInfo","Dodaje onanswerSelected"+ isCorrect);
        if (isCorrect) {
            correctAnswers2++;
            Log.d("TagInfo","Dodaje do poprawnych");
        }

        if (totalQuestions < MAX_QUESTIONS) {
            Log.d("TagInfo", "mamay pytanie nr "+ totalQuestions);
            setRandomWord();
        } else {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, resultFragment)
                    .commit();
            resultFragment.setResults(totalQuestions, correctAnswers);
        }
    }
}
