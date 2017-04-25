package com.example.venu.braintest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.Switch;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnNovice;
    private Button btnEasy;
    private Button btnMedium;
    private Button btnGuru;
    public Intent intent;
    public int gameCount; //to receive the number of games per level
    boolean hints; //to check if hints are on or off

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // getting the game count value passed from the main menu
        intent = getIntent();
        gameCount = intent.getIntExtra("gameCount", 0);

        btnNovice = (Button) findViewById(R.id.btnNovice);
        btnEasy = (Button) findViewById(R.id.btnEasy);
        btnMedium = (Button) findViewById(R.id.btnMedium);
        btnGuru = (Button) findViewById(R.id.btnGuru);

        btnNovice.setOnClickListener(this);
        btnEasy.setOnClickListener(this);
        btnMedium.setOnClickListener(this);
        btnGuru.setOnClickListener(this);

    }

    // method for onclick buttons for selecting a level to play
    // passing the level number to the next step to determine the expression
    @Override
    public void onClick(View v) {
        //show the game screen
        intent = new Intent(this, CalculatorActivity.class);

        switch (v.getId()) {
            case R.id.btnNovice:
                intent.putExtra("gameCount", gameCount);
                intent.putExtra("level", 1);
                break;
            case R.id.btnEasy:
                intent.putExtra("gameCount", gameCount);
                intent.putExtra("level", 2);
                break;
            case R.id.btnMedium:
                intent.putExtra("gameCount", gameCount);
                intent.putExtra("level", 3);
                break;
            case R.id.btnGuru:
                intent.putExtra("gameCount", gameCount);
                intent.putExtra("level", 4);
                break;
        }

        //pass the hint value to the next step
        if (hintMethod()) {
            //hints on
            intent.putExtra("hints", true);
        } else {
            //hints off
            intent.putExtra("hints", false);
        }
        startActivity(intent);
    }

    //method for hints
    public boolean hintMethod() {
        Switch switchArea = (Switch) findViewById(R.id.swtHint);
        hints = switchArea.isChecked();
        return hints;
    }

}
