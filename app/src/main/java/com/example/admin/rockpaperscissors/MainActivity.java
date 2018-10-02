package com.example.admin.rockpaperscissors;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button b_rock, b_paper, b_scissors, b_stop;
    ImageView cpu, player;

    String playerChoice, cpuChoice, result, playerName;
    Random r;
    TextView pltxt;

    Integer total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cpu = (ImageView) findViewById(R.id.iv_cpu);
        player = (ImageView) findViewById(R.id.iv_me);

        pltxt = (TextView) findViewById(R.id.pltxt);
        b_rock = (Button) findViewById(R.id.b_rock);
        b_paper = (Button) findViewById(R.id.b_paper);
        b_scissors = (Button) findViewById(R.id.b_scissors);
        b_stop = (Button) findViewById(R.id.b_stop);

        r = new Random();

        if(getIntent().hasExtra("player.name")){
            playerName = getIntent().getStringExtra("player.name");
            pltxt.setText(playerName);
        }



        total = 0;

        b_rock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerChoice = "rock";
                player.setImageResource(R.drawable.rock);
                calculate();
            }
        });

        b_paper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerChoice = "paper";
                player.setImageResource(R.drawable.paper);
                calculate();
            }
        });

        b_scissors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerChoice = "scissors";
                player.setImageResource(R.drawable.scissors);
                calculate();
            }
        });

        b_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStats();
            }
        });
    }

    public void openStats() {
        /*Intent intent = new Intent(this, Stats.class);
        startActivity(intent);*/

        String currentPlayer = playerName;
        Intent intent = new Intent(getApplicationContext(), Stats.class);

        intent.putExtra("player.name", currentPlayer);
        intent.putExtra("player.score", total);
        startActivity(intent);
    }


    public void calculate(){
        int cpu = r.nextInt(3);
        if(cpu == 0) {
            cpuChoice = "rock";
            this.cpu.setImageResource(R.drawable.rock);
        } else if (cpu == 1) {
            cpuChoice = "paper";
            this.cpu.setImageResource(R.drawable.paper);
        } else if (cpu == 2) {
            cpuChoice = "scissors";
            this.cpu.setImageResource(R.drawable.scissors);
        }

        if (playerChoice.equals("rock") && cpuChoice.equals("paper")) {
            result = "You lose";
        } else if (playerChoice.equals("rock") && cpuChoice.equals("scissors")) {
            result = "You win";
            total++;
        } else if (playerChoice.equals("paper") && cpuChoice.equals("rock")) {
            result = "You win";
            total++;
        } else if (playerChoice.equals("paper") && cpuChoice.equals("scissors")) {
            result = "You lose";
        } else if (playerChoice.equals("scissors") && cpuChoice.equals("paper")) {
            result = "You win";
            total++;
        } else if (playerChoice.equals("scissors") && cpuChoice.equals("rock")) {
            result = "You lose";
        } else if (playerChoice.equals("scissors") && cpuChoice.equals("scissors")) {
            result = "Draw";
        } else if (playerChoice.equals("rock") && cpuChoice.equals("rock")) {
            result = "Draw";
        } else if (playerChoice.equals("paper") && cpuChoice.equals("paper")) {
            result = "Draw";
        }

        Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();


    }
}
