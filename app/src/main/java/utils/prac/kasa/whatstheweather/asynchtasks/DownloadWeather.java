package utils.prac.kasa.whatstheweather.asynchtasks;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by kasa2 on 9/22/2016.
 */

public class DownloadWeather extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... city) {
        String message = "";
        URL url;
        HttpURLConnection httpURLConnection;

        try {
            String result = "";
            String path = "http://api.openweathermap.org/data/2.5/weather?q=" + city[0] + "&APPID=e00d6166d5a2cc7acd31371ca9c2b0f9";
            Log.i("url", path);


            url = new URL(path);
            httpURLConnection = (HttpURLConnection)url.openConnection();
            //Log.i("url", urls[0]);

            InputStream in = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(in);
            int data = inputStreamReader.read();

            while(data != -1){
                char current = (char) data;
                result += current;
                data = inputStreamReader.read();
                //Log.i("result", result);
            }

            JSONObject json = new JSONObject(result);
            String weatherInfo = json.getString("weather");

            JSONArray array = new JSONArray(weatherInfo);

            for(int a = 0; a < array.length(); a++){
                JSONObject object = array.getJSONObject(a);
                Log.i("main", object.getString("main"));
                Log.i("descrition", object.getString("description"));

                String main = object.getString("main");
                String description = object.getString("description");

                if(main.length() > 0 && description.length() > 0){
                    //the city exists
                    message += main + " : " + description;
                }

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return message;
    }

    /*
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        try {
            JSONObject json = new JSONObject(s);

            String weatherInfo = json.getString("weather");

            JSONArray array = new JSONArray(weatherInfo);

            for(int a = 0; a < array.length(); a++){
                JSONObject object = array.getJSONObject(a);
                Log.i("main", object.getString("main"));
                Log.i("descrition", object.getString("description"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    */
}
