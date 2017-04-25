package com.example.venu.braintest;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ResultActivity extends AppCompatActivity implements View.OnClickListener {

    public Intent intent;
    public int marks;
    private Button btnMenu;
    private TextView txtScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        intent = getIntent();
        marks = intent.getIntExtra("score", 10);

        btnMenu = (Button) findViewById(R.id.btnMain);
        txtScore = (TextView) findViewById(R.id.txtScore);
        btnMenu.setOnClickListener(this);

        showResult(marks);

    }

    @Override
    public void onClick(View v) {
        intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    //to display the score
    public void showResult(int mark) {

        txtScore.setText(mark + " points");
    }

    //to disable back press in the result screen
    @Override
    public void onBackPressed() {
        Toast.makeText(ResultActivity.this, "Go to Main Menu", Toast.LENGTH_SHORT).show();
    }
}
