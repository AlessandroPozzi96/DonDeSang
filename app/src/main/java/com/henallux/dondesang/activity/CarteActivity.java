package com.henallux.dondesang.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.henallux.dondesang.R;
import com.henallux.dondesang.model.Application;

public class CarteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carte);

        Application application = (Application) this.getApplicationContext();
        Toast.makeText(CarteActivity.this, "" + application.getLatitude(), Toast.LENGTH_LONG).show();
    }
}
