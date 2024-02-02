package com.example.projekt;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

// ...

public class ApiWordOfTheDay extends AsyncTask<Void, Void, String> {

    private Exception exception;
    private Callback callback;


    public interface Callback {
        void onTaskCompleted(String result);
    }

    public ApiWordOfTheDay(Callback callback) {
        this.callback = callback;
    }

    @Override
    protected String doInBackground(Void... params) {
        try {
            URL url = new URL("https://random-word-api.herokuapp.com/word");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Ustawienia metody na GET
            connection.setRequestMethod("GET");

            // Sprawdź, czy odpowiedź serwera jest OK (kod 200)
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line;



                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }

                // Zamknięcie zasobów
                bufferedReader.close();
                inputStream.close();
                connection.disconnect();

                return stringBuilder.toString();
            } else {
                // W przypadku nieudanej odpowiedzi serwera, zwróć informację o błędzie
                return "Błąd połączenia. Kod odpowiedzi: " + responseCode;
            }
        } catch (Exception e) {
            // Przechwyć wyjątek i zwróć informację o błędzie
            this.exception = e;
            return "Błąd podczas pobierania danych: " + e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        // Wywołaj callback, przekazując wynik
        if (callback != null) {
            callback.onTaskCompleted(result);
        }
    }


}

