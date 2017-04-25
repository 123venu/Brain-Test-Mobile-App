package com.example.venu.braintest;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.Toast;

public class  MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnNew;
    private Button btnCont;
    private Button btnAbout;
    private Button btnEx;
    public Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnNew = (Button) findViewById(R.id.btnNew);
        btnCont = (Button) findViewById(R.id.btnCont);
        btnAbout = (Button) findViewById(R.id.btnAbout);
        btnEx = (Button) findViewById(R.id.btnEx);

        btnNew.setOnClickListener(this);
        btnCont.setOnClickListener(this);
        btnAbout.setOnClickListener(this);
        btnEx.setOnClickListener(this);
    }

    // method for onclick buttons in main menu
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // passing the number of games for each level (10)
            case R.id.btnNew:
                //show the game level screen
                intent = new Intent(this, GameActivity.class);
                intent.putExtra("gameCount", 10);
                startActivity(intent);
                break;
            // to continue previous saved game
            case R.id.btnCont:
                //show the previously saved screen
                continueGame();
                break;
            // to learn about the game
            case R.id.btnAbout:
                //show the about pop up window
                intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                break;
            // to exit
            case R.id.btnEx:
                finishAffinity();
        }
    }

    //to disable back press in the main menu
    @Override
    public void onBackPressed() {
        Toast.makeText(MainActivity.this, "Press EXIT to quit", Toast.LENGTH_SHORT).show();
    }

    //method when continue is pressed
    public void continueGame() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("Data", Context.MODE_PRIVATE);
        if (pref.contains("gameCount")) {

            int gameCount = pref.getInt("gameCount", 0);
            int level = pref.getInt("level", 0);
            boolean hints = pref.getBoolean("hints", false);

            //passing values to the required activity
            intent = new Intent(MainActivity.this, CalculatorActivity.class);
            intent.putExtra("gameCount", gameCount);
            intent.putExtra("level", level);
            intent.putExtra("hints", hints);
            intent.putExtra("checkStatus", true);
            startActivity(intent);

        } else { //if no game is saved
            Toast.makeText(MainActivity.this, "No game has been saved! ", Toast.LENGTH_SHORT).show();
        }
    }

}
