package edu.nyu.xiaoqianyu.ipcclient.client;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import edu.nyu.xiaoqianyu.ipcclient.R;

public class DownloadBindingDemo extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_binding_demo);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new DownloadFragment())
                    .commit();
        }
    }
}
