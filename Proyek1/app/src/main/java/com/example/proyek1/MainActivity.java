package com.example.proyek1;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_STORAGE = 100;
    private ListView listView;
    String pathDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/vsgaproyek1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("Aplikasi Catatan Proyek 1");

        listView = findViewById(R.id.listView);

        listView.setOnItemClickListener((parent, view, position, id) -> {

            Map<String, Object> data = (Map<String, Object>) parent.getAdapter().getItem(position);

            Intent intent = new Intent(this, InsertAndViewActivity.class);
            intent.putExtra("filename", data.get("name").toString());
            startActivity(intent);
        });

        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            Map<String, Object> data = (Map<String, Object>) parent.getAdapter().getItem(position);

            String namaFile = data.get("name").toString();

            new AlertDialog.Builder(this)
                    .setTitle("Konfirmasi hapus")
                    .setMessage(String.format("hapus catatan %s", namaFile))
                    .setIcon(R.drawable.icon)
                    .setPositiveButton("YES", (dialog, whichButton) -> hapusFile(namaFile))
                    .setNegativeButton("NO", null).show();

            return true;
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= 23) {
            if (periksaIzinPenyimpanan()) {
                showListFiles();
            }
        } else {
            showListFiles();
        }
    }

    public boolean periksaIzinPenyimpanan() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                return true;
            }
            else {
                ActivityCompat.requestPermissions(this,
                        new String[]{WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_STORAGE);

                return false;
            }
        } else {
            return true;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_STORAGE)
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showListFiles();
            }
    }


    void showListFiles() {
        File directory = new File(pathDir);

        if (directory.exists()) {
            File[] files = directory.listFiles();
            String[] filenames = new String[files.length];
            String[] dateCreated = new String[files.length];
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM YYYY HH:mm:ss");
            ArrayList<Map<String, Object>> itemDataList = new ArrayList<Map<String, Object>>();

            for (int i = 0; i < files.length; i++) {
                filenames[i] = files[i].getName();
                Date lastModDate = new Date(files[i].lastModified());
                dateCreated[i] = simpleDateFormat.format(lastModDate);

                Map<String, Object> listItemMap = new HashMap<>();
                listItemMap.put("name", filenames[i]);
                listItemMap.put("date", dateCreated[i]);
                itemDataList.add(listItemMap);
            }

            SimpleAdapter simpleAdapter = new SimpleAdapter(this,
                    itemDataList, android.R.layout.simple_list_item_2,
                    new String[]{"name", "date"},
                    new int[]{android.R.id.text1, android.R.id.text2});

            listView.setAdapter(simpleAdapter);
            simpleAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.miTambah)
            startActivity(new Intent(this, InsertAndViewActivity.class));

        return super.onOptionsItemSelected(item);
    }

    void hapusFile(String filename) {
        File file = new File(pathDir, filename);
        if (file.exists() && file.delete())
            System.out.println("file telah dihapus");

        showListFiles();
    }

}