package com.example.latihanstorage;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //views to object
        Button btInternalStorage = findViewById(R.id.bt_internal_storage);
        Button btExternalStorage = findViewById(R.id.bt_external_storage);

        //event handler
        btInternalStorage.setOnClickListener(v -> {
            Intent intent = new Intent(this, InternalActivity.class);
            startActivity(intent);
        });

        btExternalStorage.setOnClickListener(v -> {
            Intent intent = new Intent(this, EksternalActivity.class);
            startActivity(intent);
        });

    }
}