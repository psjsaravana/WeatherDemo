package com.psj.saravana.weatherapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class WeatherActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_details);
        Intent intent=getIntent();
        String[] details=intent.getStringArrayExtra("details");
        TextView cityName= (TextView) findViewById(R.id.cityValue);
        TextView cityTemp= (TextView) findViewById(R.id.tempValue);
        TextView cityPressure= (TextView) findViewById(R.id.pressureValue);
        TextView cityHumidity= (TextView) findViewById(R.id.humidityValue);
       // Log.i("asd", "wsdasd" + details[0]);
        cityName.setText(details[0]);
        cityTemp.setText(details[1]);
        cityPressure.setText(details[2]);
        cityHumidity.setText(details[3]);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_weather, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
