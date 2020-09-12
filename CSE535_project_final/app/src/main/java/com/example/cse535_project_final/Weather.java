package com.example.cse535_project_final;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Weather extends AppCompatActivity{
    String CITY = "Tempe,us";
    String API = "7ca6a16081348e5e86aae1d0ede84e73";

    TextView addressTxt, updated_atTxt, statusTxt, tempTxt, temp_minTxt, temp_maxTxt, sunriseTxt,
            sunsetTxt, windTxt, pressureTxt, humidityTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        addressTxt = (TextView)findViewById(R.id.address);
        updated_atTxt = (TextView)findViewById(R.id.updated_at);
        statusTxt = (TextView)findViewById(R.id.status);
        tempTxt = (TextView)findViewById(R.id.temp);
        temp_minTxt = (TextView)findViewById(R.id.temp_min);
        temp_maxTxt = (TextView)findViewById(R.id.temp_max);
        sunriseTxt = (TextView)findViewById(R.id.sunrise);
        sunsetTxt = (TextView)findViewById(R.id.sunset);
        windTxt = (TextView)findViewById(R.id.wind);
        pressureTxt = (TextView)findViewById(R.id.pressure);
        humidityTxt = (TextView)findViewById(R.id.humidity);

        new weatherTask().execute();

    }
    class weatherTask extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... args) {
            String request = "https://api.openweathermap.org/data/2.5/weather?q=" + CITY + "&units=metric&appid=" + API;

            try {
                URL url = new URL(request);

                URLConnection connection = url.openConnection();

                InputStream inputStream = connection.getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = reader.readLine())!=null){

                    result.append(line);
                }
//                Log.d("tag", result.toString());
                return result.toString();

            } catch (Exception e) {
                Toast.makeText(Weather.this, e.getMessage(), Toast.LENGTH_LONG).show();//report problem
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {


            try {
                JSONObject jsonObj = new JSONObject(result);
                JSONObject main = jsonObj.getJSONObject("main");
                JSONObject sys = jsonObj.getJSONObject("sys");
                JSONObject wind = jsonObj.getJSONObject("wind");
                JSONObject weather = jsonObj.getJSONArray("weather").getJSONObject(0);

                Long updatedAt = jsonObj.getLong("dt");
                String updatedAtText = "Updated at: " + new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(new Date(updatedAt * 1000));
                String temp = main.getString("temp") + "°C";
                String tempMin = "Min Temp: " + main.getString("temp_min") + "°C";
                String tempMax = "Max Temp: " + main.getString("temp_max") + "°C";
                String pressure = main.getString("pressure");
                String humidity = main.getString("humidity");

                Long sunrise = sys.getLong("sunrise");
                Long sunset = sys.getLong("sunset");
                String windSpeed = wind.getString("speed");
                String weatherDescription = weather.getString("description");

                String address = jsonObj.getString("name") + ", " + sys.getString("country");
                /* Populating extracted data into our views */
                addressTxt.setText(address);
                updated_atTxt.setText(updatedAtText);
                statusTxt.setText(weatherDescription.toUpperCase());
                tempTxt.setText(temp);
                temp_minTxt.setText(tempMin);
                temp_maxTxt.setText(tempMax);
                sunriseTxt.setText(new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(sunrise * 1000)));
                sunsetTxt.setText(new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(sunset * 1000)));
                windTxt.setText(windSpeed);
                pressureTxt.setText(pressure);
                humidityTxt.setText(humidity);


            } catch (JSONException e) {
                Toast.makeText(Weather.this, e.getMessage(), Toast.LENGTH_LONG).show();//report problem
            }

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Weather.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
