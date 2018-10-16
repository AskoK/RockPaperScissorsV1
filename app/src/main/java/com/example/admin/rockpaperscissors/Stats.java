package com.example.admin.rockpaperscissors;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Stats extends AppCompatActivity {

    Button b_back, b_show, b_save;
    ListView previousScores;
    Integer score;
    String userName;

    ArrayList<String> listItems = new ArrayList<String>();
    ArrayAdapter<String> adapter;

    TextView currentText;

    String line = null;

    private static final int REQUEST_CODE_PERMISSION = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        previousScores = (ListView) findViewById(R.id.previousScores);
        b_back = (Button) findViewById(R.id.b_back);
        b_save = (Button) findViewById(R.id.b_save);
        b_show = (Button) findViewById(R.id.b_show);

        currentText = (TextView) findViewById(R.id.currentText);

        b_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMain();
            }
        });

        userName = getIntent().getStringExtra("player.name");
        score = getIntent().getIntExtra("player.score", 0);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
        previousScores.setAdapter(adapter);

        currentText.setText(userName + "            " + score);

        adapter.notifyDataSetChanged();


        // Checking whether this app has write external storage permission or not.
        int writeExternalStoragePermission = ContextCompat.checkSelfPermission(Stats.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        // If external storage write permission is not granted.
        if (writeExternalStoragePermission != PackageManager.PERMISSION_GRANTED)
            // Request user to grant write external storage permission.
            ActivityCompat.requestPermissions(Stats.this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE }, REQUEST_CODE_PERMISSION);
        // showing /storage/emulated/0/DCIM folder files
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            File externalDirectory = new File(String.valueOf(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DCIM)));
            ShowDirectoryFilesInList(externalDirectory);
        }
    }


    // method for cheching if the external storage is writeable
    private boolean isExternalStorageWriteable(){
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            Log.i("State", "Writeable");
            return true;
        } else return false;
    }

    // method for cheching if the external storage is readable
    private boolean isExternalStorageReadable(){
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(Environment.getExternalStorageState())){
            Log.i("State", "Readable");
            return true;
        } else return false;
    }

    // method for saving the entered text message under a specific file name
    public void onSave(View view) {
        if(isExternalStorageWriteable()){
            // Save file to /storage/emulated/0/DCIM folder
            File textFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),
                    userName + "            " +score.toString());
            try{
                FileOutputStream fileOutputStream = new FileOutputStream(textFile);
                fileOutputStream.write((userName.toString() + "            " + score).getBytes());
                fileOutputStream.close();
                Toast.makeText(this, "File saved", Toast.LENGTH_LONG).show();
                currentText.setText("");
            } catch (IOException ex) { ex.printStackTrace();}
        } else Toast.makeText(this, "External storage isn't mounted", Toast.LENGTH_LONG).show();
    }

    //Show name + score
    public void onShow(View view) {
        if (isExternalStorageReadable()){
            StringBuilder stringBuilder = new StringBuilder();
            try{
                File textFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),
                        userName + "            " + score.toString());
                FileInputStream fileInputStream = new FileInputStream(textFile);
                if (fileInputStream != null){
                    InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    while ((line = bufferedReader.readLine()) != null){
                        stringBuilder.append(line + "\n");
                    }
                    fileInputStream.close();
                    currentText.setText(stringBuilder);
                }
            } catch (IOException ex) { ex.printStackTrace();}
        } else Toast.makeText(this, "Can't read from external storage", Toast.LENGTH_SHORT).show();
    }


    public void ShowDirectoryFilesInList(File externalDirectory){
        File listFile[] = externalDirectory.listFiles();
        if (listFile != null){
            for (int i = 0; i < listFile.length; i++){
                if (listFile[i].isDirectory()){
                    ShowDirectoryFilesInList(listFile[i]);
                } else listItems.add(listFile[i].getName());
            }
        }
    }

    public void openMain() {
        Intent intent =  new Intent(this, MainActivity.class);

        intent.putExtra("player.name", userName);
        startActivity(intent);
    }

}
