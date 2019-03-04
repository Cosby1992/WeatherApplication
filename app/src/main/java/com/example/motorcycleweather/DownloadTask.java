package com.example.motorcycleweather;


import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadTask extends AsyncTask<String,Void,String> {



    @Override
    protected String doInBackground(String... urls) {

        StringBuilder result = new StringBuilder();
        URL url;
        HttpURLConnection urlConnection = null;

        try {
            url = new URL(urls[0]);

            urlConnection = (HttpURLConnection) url.openConnection();

            InputStream in = urlConnection.getInputStream();

            InputStreamReader reader = new InputStreamReader(in);

            int data = reader.read();

            while (data != -1){

                char current = (char) data;

                result.append(current);

                data = reader.read();

            }

            return result.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        String temperatureString = "";

        try {
            JSONObject jsonObject = new JSONObject(result);

            JSONObject weatherData = new JSONObject(jsonObject.getString("main"));

            double tempDouble = Double.parseDouble(weatherData.getString("temp"));

            int tempInt = (int) (tempDouble-273.15);

            temperatureString =  String.valueOf(tempInt);

            MainActivity.temperatureText.setText(temperatureString + " C");

            MainActivity.nameText.setText(jsonObject.getString("name"));


        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
