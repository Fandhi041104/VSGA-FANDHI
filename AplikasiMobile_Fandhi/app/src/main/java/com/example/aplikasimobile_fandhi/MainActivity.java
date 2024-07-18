package com.example.aplikasimobile_fandhi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    EditText TextNama;
    TextView Hasil;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextNama=(EditText)findViewById(R.id.TxtNama);
        Hasil=(TextView)findViewById(R.id.Lbl_Nama);
    }
    public void TampilNama(View v) {
        Hasil.setText("Nama Anda: "+TextNama.getText());
    }
}