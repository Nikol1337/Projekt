package com.example.projekt;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class TestSelectionFragment extends Fragment  {
private BookContainer przeslanyObiekt;
    TestFragment selectedFragment=null;


    public TestSelectionFragment(BookContainer przeslanyObiekt) {
        this.przeslanyObiekt=przeslanyObiekt;


        // Pusty konstruktor wymagany dla fragmentu.
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test_selection, container, false);

        RadioGroup radioGroup = view.findViewById(R.id.radioGroup);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {



                     if(checkedId==R.id.radioButtonTestEnPl)
                        selectedFragment = new TestFragment(przeslanyObiekt,TestType.EnPl);
              else  if(checkedId==R.id.radioButtonTestPlEn)
                        selectedFragment = new TestFragment(przeslanyObiekt,TestType.PlEn);
                       else
                        Toast.makeText(getContext() ,"Wybierz tryb!", Toast.LENGTH_SHORT).show();


                    if(selectedFragment!=null){
                // Zamień fragment w zależności od wyboru.
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, selectedFragment)
                        .addToBackStack(null) // Jeśli chcesz dodawać do stosu cofania.
                        .commit();}

            }

        });

        return view;
    }

}

