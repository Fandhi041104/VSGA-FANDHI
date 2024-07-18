package com.example.latihanstorage;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class InternalActivity extends AppCompatActivity {
    public static final String FILENAME = "fileInternalStorage.txt";
    private final String lineSeparator = System.getProperty("line.separator");
    private TextView textBaca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internal);

        //views to object
        Button btBuatFile = findViewById(R.id.buttonBuatFile);
        Button btUbahFile = findViewById(R.id.buttonUbahFile);
        Button btBacaFile = findViewById(R.id.buttonBacaFile);
        Button btHapusFile = findViewById(R.id.buttonHapusFile);
        textBaca = findViewById(R.id.textBaca);

        //event handler with lambda
        btBuatFile.setOnClickListener(v -> buatFile());

        btUbahFile.setOnClickListener(v -> ubahFile());

        btBacaFile.setOnClickListener(v -> bacaFile());

        btHapusFile.setOnClickListener(v -> hapusFile());
    }

    void buatFile() {
        String isiFile = "Hai, saya adalah konten file - internal storage!";

        //method openFileOutput untuk membuat/membuka file di internal storage
        try (FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_APPEND)){
            fos.write(isiFile.getBytes());  //isiFile diubah ke bytes kemudian tulis ke file
            fos.write(lineSeparator.getBytes()); //tambahkan line separator
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void ubahFile() {
        String isiFileBaru = "Dirgahayu Republik Indonesia ke 76, Indonesia Bangkit!";

        //konten file diganti
        try (FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE)){
            fos.write(isiFileBaru.getBytes());
            fos.write(lineSeparator.getBytes());
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void bacaFile() {
        StringBuilder sb = new StringBuilder();
        try (FileInputStream fis = openFileInput(FILENAME)) {
            int read;
            while ((read = fis.read()) != -1) {
                sb.append(((char)read));
            }
            textBaca.setText(sb);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void hapusFile() {
        //metdho deleteFile() untuk hapus private file yang berkaitan dengan context app ini
        if (deleteFile(FILENAME)) {
            Toast.makeText(this, String.format("%s file has been deleted!", FILENAME), Toast.LENGTH_SHORT).show();
            textBaca.setText("");
        }

    }

}