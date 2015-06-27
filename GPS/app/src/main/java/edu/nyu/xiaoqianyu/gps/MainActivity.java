package edu.nyu.xiaoqianyu.gps;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends Activity {

    private double latitude;
    private double longitude;
    Button btn;
    LocationFetcher locationFetcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button)findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationFetcher = new LocationFetcher(MainActivity.this);

                latitude = locationFetcher.getLatitude();
                longitude = locationFetcher.getLongitude();

                Toast.makeText(getApplicationContext(), "Latitude: " + latitude + "\nLontitude: " + longitude, Toast.LENGTH_LONG).show();

                SharedPreferences sharedPref = getSharedPreferences("location", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                float lat = (float)latitude;
                float longi = (float)longitude;
                editor.putFloat("latitude", lat);
                editor.putFloat("longitude", longi);
                editor.apply();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
