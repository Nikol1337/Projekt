package com.example.projekt;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Random;

public class GameActivity extends AppCompatActivity {
    private ImageView imageView;
    private TextView leftAnswerTextView, rightAnswerTextView;
    private List<Integer> listaWszystkichid;
    private List<String> listaSlowek;
    private int MAX=3;
    private boolean tiltDetected = false;
    private int tiltDirection = 0;
    private static final float TILT_THRESHOLD = 1.3f;  // Prog przechylenia
    private static final int TILT_RIGHT = 1;  // Kierunek przechylenia w prawo
    private static final int TILT_LEFT = -1;
    private SensorManager sensorManager;
    private Sensor rotationSensor;
    private int punkty = 0;
    private int pytanie = 0;
    private String poprawna;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);




        // Inicjalizacja widoków
        imageView = findViewById(R.id.imageView);
        leftAnswerTextView = findViewById(R.id.leftAnswerTextView);
        rightAnswerTextView = findViewById(R.id.rightAnswerTextView);
        listaWszystkichid=MenuActivity.appDatabase.interfejsDAO().getWszystkieID();
        listaSlowek=MenuActivity.appDatabase.interfejsDAO().getSlowa();
        //WylosujLiczbePoprawnaOdpowiedz();
        Log.d("TagInfo","Zaczynam game "+listaSlowek.size());

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        rotationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);

        if (rotationSensor != null) {
            sensorManager.registerListener(sensorEventListener, rotationSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
        setQuizContent();
    }
    private final SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
                float[] rotationMatrix = new float[9];
                float[] orientationValues = new float[3];

                SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values);
                SensorManager.getOrientation(rotationMatrix, orientationValues);

                float roll = orientationValues[2];

               // Log.d("RotationValues", "Roll: " + roll);

                handleRotationData(roll);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // Nie musisz implementować, ale musi być zdefiniowane
        }
    };
    @Override
    protected void onResume() {
        super.onResume();
        Sensor rotationVectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);

        if (rotationVectorSensor != null) {
            sensorManager.registerListener(sensorEventListener, rotationVectorSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(sensorEventListener);
    }

    // ... pozostała część kodu ...

    private void handleRotationData(float roll) {
        // Przykładowa logika dostosowująca interakcję w zależności od kąta obrotu (roll)
        if (Math.abs(roll) > TILT_THRESHOLD) {
            if (!tiltDetected) {
                tiltDetected = true;
                if (roll > 0) {
                    tiltDirection = TILT_RIGHT;
                } else {
                    tiltDirection = TILT_LEFT;
                }
                handleTilt();
            }
        } else {
            tiltDetected = false;
            tiltDirection = 0;
        }
    }

    private void handleTilt() {
        // Obsługa przechylenia w bok, np. przejście do następnej lub poprzedniej fiszki
        if (tiltDirection == TILT_RIGHT) {
            sprawdz(1);
        } else if (tiltDirection == TILT_LEFT) {
            sprawdz(0);
        }
    }
    private void sprawdz(int kierunek){
        if(kierunek==0){
            if(leftAnswerTextView.getText()==poprawna){
                Log.d("Gra","Dobra odpoweidz lewa");
                punkty++;
            }
            Log.d("Gra","Zla odpoweidz lewa");
            pytanie++;
        }else {
            if(rightAnswerTextView.getText()==poprawna){
                punkty++;
                Log.d("Gra","Dobra odpoweidz prawa");
            }
            Log.d("Gra","Zla odpoweidz prawa");
            pytanie++;
        }
        if(MAX==pytanie){
            Log.d("Gra","Wychodze");
            Intent nowy = new Intent(GameActivity.this, MenuActivity.class);
            startActivity(nowy);

        }else {
            setQuizContent();
        }
    }
    private Integer WylosujLiczbePoprawnaOdpowiedz(){
        Random random = new Random();
        int randomId = random.nextInt(listaWszystkichid.size());
        Log.d("Gra","Zaczynam game "+randomId + "Zostalo -"+listaWszystkichid.size());

        return randomId;
    }
    private Integer WylosujLiczbe(){
        Random random = new Random();
        int randomId;
        if(listaSlowek.size()!=0){
         randomId = random.nextInt(listaSlowek.size());
        }
        else {
            Log.d("Gra","lista slowek jest rozna 0");
        return 0;}
        return randomId;
    }
    private void ZaładujZdjecie(int id){
        zdjecie_do_slowka zdjecie =MenuActivity.appDatabase.interfejsDAO().getZdjecieBySlowkoId(id);

        Picasso.get()
                .load(zdjecie.url)// Opcjonalnie: dostosuj rozmiar obrazu
                .resize(300, 300)
                .centerCrop()     // Opcjonalnie: dostosuj sposób kadrowania obrazu
                .into(imageView);
        // Ustawienie obrazka i odpowiedzi

    }


    private void setQuizContent() {
        int poprawnaOdpowiedz=WylosujLiczbePoprawnaOdpowiedz();
        ZaładujZdjecie(listaWszystkichid.get(poprawnaOdpowiedz));

        poprawna = MenuActivity.appDatabase.interfejsDAO().getTranslation(listaWszystkichid.get(poprawnaOdpowiedz));
        int liczba=WylosujLiczbe();
        Log.d("TagInfo","zaladowalem fote ");

        Random random = new Random();
        int liczbarandom = random.nextInt(2);
        if(liczbarandom==0){
            leftAnswerTextView.setText(poprawna);
            rightAnswerTextView.setText(listaSlowek.get(liczba));
        }else{
            rightAnswerTextView.setText(poprawna);
            leftAnswerTextView.setText(listaSlowek.get(liczba));

        }
        listaWszystkichid.remove(poprawnaOdpowiedz);
        listaSlowek.remove(liczba);
    }
}