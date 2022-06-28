package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    TextView textCity;
    TextView textLat,weather,temp;
    TextView textLong;
    Button zipcodeButton;
    String zipcode,clear,city,icon;
    Double lat,lon,currentemp;
    EditText editZip;
    JSONObject dataone, cdata, day;
    JSONArray jarray;
    ListView listView;
    ImageView imageView;

    TextView weather1,time1,temp1;
    ImageView icon1;

    TextView weather2,time2,temp2;
    ImageView icon2;

    TextView weather3,time3,temp3;
    ImageView icon3;

    TextView weather4,time4,temp4;
    ImageView icon4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textCity = findViewById(R.id.textCity);
        textLat = findViewById(R.id.textLat);
        textLong = findViewById(R.id.textLong);
        editZip = findViewById(R.id.editZip);
        temp = findViewById(R.id.temp);
        weather = findViewById(R.id.weather);
        imageView = findViewById(R.id.iconMain);
        weather1 = findViewById(R.id.weather1);
        temp1 = findViewById(R.id.temp1);
        time1 = findViewById(R.id.time1);
        icon1 = findViewById(R.id.icon1);
        weather2 = findViewById(R.id.weather2);
        temp2 = findViewById(R.id.temp2);
        time2 = findViewById(R.id.time2);
        icon2 = findViewById(R.id.icon2);
        weather3 = findViewById(R.id.weather3);
        temp3 = findViewById(R.id.temp3);
        time3 = findViewById(R.id.time3);
        icon3 = findViewById(R.id.icon3);

        zipcodeButton = findViewById(R.id.zipcodeButton);
        clear="";
        //lat=0.0;
        //lon=0.0;
        //listWeather = new ArrayList<weather>();
        //listView = findViewById(R.id.listView);
        zipcodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zipcode = editZip.getText().toString();
                try{
                    new AsyncThread().execute(zipcode);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public double KtF(double kelvin){
        return Math.round(((kelvin-273.15)* 9 / 5 +32)*100.00)/100.00;
    }
    public void setImage(String condition, ImageView image){
        switch (condition){
            case "thunderstorm with light rain":
            case "thunderstorm with rain":
            case "thunderstorm with heavy rain":
            case "light thunderstorm":
            case "thunderstorm":
            case "heavy thunderstorm":
            case "ragged thunderstorm":
            case "thunderstorm with light drizzle":
            case "thunderstorm with drizzle":
            case "thunderstorm with heavy drizzle":
                image.setImageResource(R.drawable.thunderstorms);
                break;
            case "light snow":
            case "Snow":
            case "Heavy snow":
            case "Sleet":
            case "Light shower sleet":
            case "Shower sleet":
            case "Light rain and snow":
            case "Rain and snow":
            case "Light shower snow":
            case "Shower snow":
            case "Heavy shower snow":
                image.setImageResource(R.drawable.snow);
                break;
            case "few clouds":
            case "overcast clouds":
            case "broken clouds":
            case "scattered clouds":
                image.setImageResource(R.drawable.clouds);
                break;
            case "light rain":
            case "moderate rain":
            case "heavy intensity rain":
            case "very heavy rain":
            case "extreme rain":
            case "freezing rain":
            case "light intensity shower rain":
            case "shower rain":
            case "heavy intensity shower rain":
            case "ragged shower rain":
                    image.setImageResource(R.drawable.rainnight);
                break;
            case "clear sky":
                    image.setImageResource(R.drawable.clearnight);
                break;
            case "mist":
                image.setImageResource(R.drawable.mist);
        }
    }
    public class AsyncThread extends AsyncTask<String, Void, Void> {
        String data = "";
        String data2 ="";

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (dataone != null){
                try {
                    jarray = cdata.getJSONArray("hourly");


                    currentemp = jarray.getJSONObject(0).getDouble("temp");
                    long unixTime = jarray.getJSONObject(1).getLong("dt");
                    SimpleDateFormat smp1 = new SimpleDateFormat("hh:mm");
                    Date date1 = new Date(unixTime*1000L);
                    String t1 = smp1.format(date1);
                    currentemp = KtF(currentemp);
                    temp.setText(currentemp+" °F");
                    setImage(jarray.getJSONObject(0).getJSONArray("weather").getJSONObject(0).getString("description"), imageView);
                    weather.setText(jarray.getJSONObject(0).getJSONArray("weather").getJSONObject(0).get("description").toString().toUpperCase(Locale.ROOT));
                    textLat.setText("Lat: "+lat.toString());
                    textLong.setText("Lon: "+lon.toString());

                    textCity.setText("CITY: "+city.toUpperCase(Locale.ROOT));

                    temp1.setText(""+KtF(jarray.getJSONObject(1).getDouble("temp"))+" °F");
                    setImage(jarray.getJSONObject(1).getJSONArray("weather").getJSONObject(0).getString("description"), icon1);
                    weather1.setText(jarray.getJSONObject(1).getJSONArray("weather").getJSONObject(0).get("description").toString().toUpperCase(Locale.ROOT));

                    long unixTime2 = jarray.getJSONObject(2).getLong("dt");
                    SimpleDateFormat smp2 = new SimpleDateFormat("hh:mm");
                    Date date2 = new Date(unixTime2*1000L);
                    String t2 = smp2.format(date2);

                    temp2.setText(""+KtF(jarray.getJSONObject(2).getDouble("temp"))+" °F");
                    setImage(jarray.getJSONObject(2).getJSONArray("weather").getJSONObject(0).getString("description"), icon2);
                    weather2.setText(jarray.getJSONObject(2).getJSONArray("weather").getJSONObject(0).get("description").toString().toUpperCase(Locale.ROOT));

                    long unixTime3 = jarray.getJSONObject(3).getLong("dt");
                    SimpleDateFormat smp3 = new SimpleDateFormat("hh:mm");
                    Date date3 = new Date(unixTime3*1000L);
                    String t3 = smp3.format(date3);

                    temp3.setText(""+KtF(jarray.getJSONObject(3).getDouble("temp"))+" °F");
                    setImage(jarray.getJSONObject(3).getJSONArray("weather").getJSONObject(0).getString("description"), icon3);
                    weather3.setText(jarray.getJSONObject(3).getJSONArray("weather").getJSONObject(0).get("description").toString().toUpperCase(Locale.ROOT));

                    time1.setText(t1);
                    time2.setText(t2);
                    time3.setText(t3);







                    //Log.d("name",city);
                    //Log.d("lat","lat");
                    //Log.d("lon","lon");
                    //weather weather = new weather(jarray.getJSONObject(0).getJSONArray("weather").getJSONObject(0).get("description").toString(),icon,unixTime,KtF(currentemp));
                    //imageView.setImageResource(weather.getImage());
                    //weatheradapter weatherAdapter = new weatheradapter(MainActivity.this, R.layout.adapter_layout, listWeather);
                    //listView.setAdapter(weatherAdapter);

                    dataone = null;
                } catch (JSONException e) {
                    e.printStackTrace();
                    //Log.d("error","wrong");
                }
            }

        }

        @Override
        protected Void doInBackground(String... zipcode) {

            try{
                URL url = new URL("http://api.openweathermap.org/geo/1.0/zip?zip="+zipcode[0]+",US&appid=2bdf9844db8cd4de40707eeb2d4b4c9b");
                URLConnection urlConnection = url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                data = bufferedReader.readLine();
                dataone = new JSONObject(data);
                 city = dataone.getString("name");
                 lat = dataone.getDouble("lat");
                 lon = dataone.getDouble("lon");
                URL url2 = new URL("https://api.openweathermap.org/data/2.5/onecall?lat="+lat.toString()+"&lon="+lon.toString()+"&exclude=&appid=2bdf9844db8cd4de40707eeb2d4b4c9b");
                Log.d("url",url2.toString());
                URLConnection urlConnection2 = url2.openConnection();
                InputStream inputStream2 = urlConnection2.getInputStream();
                BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(inputStream2));
                data2 = bufferedReader2.readLine();
                cdata = new JSONObject(data2);


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

    }
}
