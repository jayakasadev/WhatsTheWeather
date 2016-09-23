package utils.prac.kasa.whatstheweather;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

import utils.prac.kasa.whatstheweather.asynchtasks.DownloadWeather;

import static java.net.URLEncoder.encode;

public class MainActivity extends AppCompatActivity {

    private EditText cityText;
    private TextView displayInfo;
    private Button go;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityText = (EditText)findViewById(R.id.cityname);
        displayInfo = (TextView)findViewById(R.id.displayinfo);
        go = (Button)findViewById(R.id.go);


    }

    public void findCity(View view){
        DownloadWeather weather = new DownloadWeather();

        InputMethodManager manager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(cityText.getWindowToken(), 0);

        String message = "";
        try {
            String city = cityText.getText().toString();

            city = encode(city, "UTF-8").toString();

            message = weather.execute(city).get();

            Log.d("result", message);


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //Log.i("city", cityText.getText().toString());
        //cityText.setText("Getting Info");

        if(message.length() > 0) {
            displayInfo.setText(message);
        }
        else{
            displayInfo.setText("Please Enter A Valid City");
        }
    }
}
