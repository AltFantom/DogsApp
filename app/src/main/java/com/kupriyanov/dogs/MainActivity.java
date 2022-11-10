package com.kupriyanov.dogs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private static final String BASE_URL = "https://dog.ceo/api/breeds/image/random";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadImage();
    }

    private void loadImage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(BASE_URL);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream inputStream = urlConnection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                    String result;
                    StringBuilder data = new StringBuilder();
                    do {
                        result = bufferedReader.readLine();
                        if (result != null) {
                            data.append(result);
                        }
                    }while (result != null);

                    JSONObject jsonObject = new JSONObject(data.toString());
                    String message = jsonObject.getString("message");
                    String status = jsonObject.getString("status");
                    DogImage dogImage = new DogImage(message, status);


                    Log.d("MainActivity", dogImage.toString());
                } catch (Exception e) {
                    Log.d("MainActivity", e.toString());
                }
            }
        }).start();

    }
}