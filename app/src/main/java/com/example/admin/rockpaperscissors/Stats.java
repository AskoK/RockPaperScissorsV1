package com.example.admin.rockpaperscissors;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Stats extends AppCompatActivity {

    Button b_back;
    ListView stats;
    Integer score;
    String userName;

    ArrayList<String> listItems = new ArrayList<String>();
    ArrayAdapter<String> adapter;

    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        stats = (ListView) findViewById(R.id.stats);
        b_back = (Button) findViewById(R.id.b_back);

        b_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMain();
            }
        });

        stats = (ListView) findViewById(R.id.stats);
        userName = getIntent().getStringExtra("player.name");
        score = getIntent().getIntExtra("player.score", 0);


        SharedPreferences playerData = getSharedPreferences("playerInfo", Context.MODE_PRIVATE);
        editor = playerData.edit();
        editor.putString("playerInfo", "        " + userName + "                                                           " + score + "");
        editor.apply();
        editor.commit();
        Map<String, ?> allEntries = playerData.getAll();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
        stats.setAdapter(adapter);

        for(Map.Entry<String, ?> entry : allEntries.entrySet()){
            listItems.add(entry.getValue().toString());
        }

        adapter.notifyDataSetChanged();

        /*
        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent playMore = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(playMore);
            }
        });*/
    }

    public void openMain() {
        Intent intent =  new Intent(this, MainActivity.class);

        intent.putExtra("player.name", userName);
        startActivity(intent);
    }

}
