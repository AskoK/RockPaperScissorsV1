package com.example.admin.rockpaperscissors;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class StartPage extends AppCompatActivity {

    Button b_save;
    EditText mEdit;
    TextView textView;

    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);

        textView = (TextView) findViewById(R.id.txt);
        b_save = (Button) findViewById(R.id.b_save);
        mEdit = (EditText) findViewById(R.id.editText);
        mEdit.getText().toString();


        b_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMain();
                savedInfo();
            }
        });


    }


    public void openMain() {
        Intent intent =  new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void savedInfo() {

        Intent startIntent = new Intent(getApplicationContext(), MainActivity.class);
        startIntent.putExtra("player.name", mEdit.getText().toString());
        startActivity( startIntent);
        Toast.makeText(this,"Saved", Toast.LENGTH_LONG).show();

        /*SharedPreferences sharedPref = getSharedPreferences("nameID", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("name", mEdit.getText().toString());
        editor.apply();

        Toast.makeText(this,"Saved", Toast.LENGTH_LONG).show();*/
    }

    public void saveData(View view) {

    }

    /*
    public void saveData(View view) {
        SharedPreferences names = getSharedPreferences("playerName", Context.MODE_PRIVATE);
        editor = names.edit();
        editor.putString("playerName", mEdit.getText().toString());
        editor.apply();
        mEdit.setText("");

        Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show();
    }


    public void displayName(View view) {
        SharedPreferences names = getSharedPreferences("playerName", Context.MODE_PRIVATE);
        String name = names.getString("playerName", "");
        String msg = "Saved " + name;
        textView.setText(msg);
    }
    */
}
