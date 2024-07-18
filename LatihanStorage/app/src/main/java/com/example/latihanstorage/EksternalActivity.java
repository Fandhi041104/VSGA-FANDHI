package com.example.latihanstorage;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


public class EksternalActivity extends AppCompatActivity {
    public static final String FILENAME = "fileExternalStorage.txt";
    public static final int REQUEST_CODE_STORAGE = 100;
    public int event = 0;
    private String lineSeparator = System.getProperty("line.separator");

    TextView textBaca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eksternal);

        //views to object
        Button btBuatFile = findViewById(R.id.buttonBuatFile);
        Button btUbahFile = findViewById(R.id.buttonUbahFile);
        Button btBacaFile = findViewById(R.id.buttonBacaFile);
        Button btHapusFile = findViewById(R.id.buttonHapusFile);

        textBaca = findViewById(R.id.textBaca);

        //event handler with lambda
        btBuatFile.setOnClickListener(v -> {
            if (periksaIzinPenyimpanan()) {
                event = R.id.buttonBuatFile;
                buatFile();
            }
        });

        btUbahFile.setOnClickListener(v -> {
            if (periksaIzinPenyimpanan()) {
                event = R.id.buttonUbahFile;
                ubahFile();
            }
        });
        btBacaFile.setOnClickListener(v -> {
            if (periksaIzinPenyimpanan())
                event = R.id.buttonBacaFile;
            bacaFile();
        });
        btHapusFile.setOnClickListener(v -> {
            if (periksaIzinPenyimpanan())
                event = R.id.buttonHapusFile;
            hapusFile();
        });
    }

    public boolean periksaIzinPenyimpanan() {

        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                return true;
            }
            else {
                ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_STORAGE);
                return false;
            }
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                izinGranted(event);
        }
    }

    private void izinGranted(int event) {
        if (event == R.id.buttonBuatFile) {
            buatFile();
        } else if (event == R.id.buttonUbahFile) {
            ubahFile();
        } else if (event == R.id.buttonBacaFile) {
            bacaFile();
        } else if (event == R.id.buttonHapusFile) {
            hapusFile();
        }
    }

    void buatFile() {
        String isiFile = "Isi konten default file external storage!";
        String state = Environment.getExternalStorageState();

        if (!Environment.MEDIA_MOUNTED.equals(state)) return;

        File extstorage = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);

        File file = new File(extstorage, FILENAME);

        //isi file akan ditambah (karena append: true)
        try(FileOutputStream fos = new FileOutputStream(file, true)) {
            fos.write(isiFile.getBytes());
            fos.write(lineSeparator.getBytes()); //tambahkan line separator
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void ubahFile() {
        String ubah = "Isi diubah menjadi Selamat Datang di DTS Kominfo 2024!";
        String state = Environment.getExternalStorageState();

        if (!Environment.MEDIA_MOUNTED.equals(state)) return;

        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), FILENAME);

        //isi file dibuat baru (karena append: false)
        try (FileOutputStream fos = new FileOutputStream(file, false)){
            fos.write(ubah.getBytes());
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void bacaFile() {
        File sdcard = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        File file = new File(sdcard, FILENAME);

        if (file.exists()) {
            StringBuilder text = new StringBuilder();

            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line = br.readLine();

                while (line != null) {
                    text.append(line);
                    text.append("\r\n"); //tambahkan baris baru
                    line = br.readLine();
                }
                br.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

            textBaca.setText(text.toString());
        }
    }

    void hapusFile() {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), FILENAME);
        if (file.exists() && file.delete())
            textBaca.setText("");
    }
}