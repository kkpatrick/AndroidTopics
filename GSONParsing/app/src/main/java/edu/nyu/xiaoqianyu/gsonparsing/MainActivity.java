package edu.nyu.xiaoqianyu.gsonparsing;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;


public class MainActivity extends Activity {

    private TextView textE;
    private TextView textF;
    private TextView textG;
    private TextView textH;
    private TextView textI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textE = (TextView)findViewById(R.id.textview1);
        textF = (TextView)findViewById(R.id.textview2);
        textG = (TextView)findViewById(R.id.textview3);
        textH = (TextView)findViewById(R.id.textview4);
        textI = (TextView)findViewById(R.id.textview5);

        new TestGsonTask().execute();
    }

    private class TestGsonTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... notUsed) {
            TestPojo tp = new TestPojo();
            Gson gson = new Gson();
            String result = null;
            try {
                String gsonToJsonTmp = gson.toJson(tp);
                String json = URLEncoder.encode(gson.toJson(tp), "UTF-8");
                String url = String.format("%s%s", Constants.JsonTestUrl, json);
                result = getStream(url);
            } catch (Exception ex) {
                Log.v(Constants.LOG_TAG, "Error " + ex.getMessage());
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JsonValidate jv = convertFromJson(result);
                if (jv != null) {
                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();

                    JSONObject J = new JSONObject(result);
                    String x = J.getString("object_or_array");
                    String y = J.getString("parse_time_nanoseconds");
                    String z = J.getString("validate");

                    String B = J.getString("size");
                    textF.setText("OBJECT_RO_ARRAY: " + x);
                    textF.setTextColor(Color.parseColor("#ff0f0e0d"));
                    textF.setTextSize(20.0f);
                    textG.setText("PARSE_TIME_NANOSECONDS: " + y);
                    textG.setTextColor(Color.parseColor("#ff0f0e0d"));
                    textG.setTextSize(20.0f);
                    textH.setText("VALIDATE: " + z);
                    textH.setTextColor(Color.parseColor("#ff0f0e0d"));
                    textH.setTextSize(20.0f);
                    textI.setText("size: " + y);
                    textI.setTextColor(Color.parseColor("#ff0f0e0d"));
                    textI.setTextSize(20.0f);
                } else {
                    Log.v(Constants.LOG_TAG, "Conversion Failed");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private static JsonValidate convertFromJson(String result) {
        JsonValidate jv = null;
        if(result != null && result.length() > 0) {
            try {
                Gson gson = new Gson();
                jv = gson.fromJson(result, JsonValidate.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return jv;
    }

    private static String getStream(String url) {
        String response = "";
        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        try {
            HttpResponse execute = client.execute(httpGet);
            InputStream content = execute.getEntity().getContent();

            BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
            String s = "";
            while((s = buffer.readLine()) != null) {
                response += s;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
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
