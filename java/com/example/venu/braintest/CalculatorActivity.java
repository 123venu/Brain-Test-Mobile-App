package com.example.venu.braintest;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.TextView;

public class CalculatorActivity extends AppCompatActivity implements View.OnClickListener {
    public Intent intent;
    CountDownTimer countDownTimer; //for the timer
    int countDownTime; //to get the remaining time
    int counter = -1; //to get the game count

    boolean isChecked = false; //for hash button click
    boolean hints; //for hints
    boolean checkStatus = false; //used for continue game
    int tries = 0; //for hint attempts
    String inputNum = new String(); //for the calculator keypad
    String expressionStr = new String(); //to put the expression
    int level; //to decide the game level
    int qusAnswer; //for getting the answer for the question
    int score = 0; //to calculate the score

    private Button one;
    private Button two;
    private Button three;
    private Button four;
    private Button five;
    private Button six;
    private Button seven;
    private Button eight;
    private Button nine;
    private Button del;
    private Button zero;
    private Button hash;
    private Button minus;

    private TextView tvAnswer; //for the answer
    private TextView tvQuestion; //for the question
    private TextView tvTime; //to add the countdown time
    private TextView tvResult; //to show whether answer is correct or incorrect
    private TextView tvHint; //to show the hint

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        tvAnswer = (TextView) findViewById(R.id.txtAnswer);
        tvQuestion = (TextView) findViewById(R.id.txtQuestion);
        tvTime = (TextView) findViewById(R.id.txtTimer);
        tvResult = (TextView) findViewById(R.id.txtResult);
        tvHint = (TextView) findViewById(R.id.txtHint);

        one = (Button) findViewById(R.id.btnOne);
        two = (Button) findViewById(R.id.btnTwo);
        three = (Button) findViewById(R.id.btnThree);
        four = (Button) findViewById(R.id.btnFour);
        five = (Button) findViewById(R.id.btnFive);
        six = (Button) findViewById(R.id.btnSix);
        seven = (Button) findViewById(R.id.btnSeven);
        eight = (Button) findViewById(R.id.btnEight);
        nine = (Button) findViewById(R.id.btnNine);
        del = (Button) findViewById(R.id.btnDel);
        zero = (Button) findViewById(R.id.btnZero);
        hash = (Button) findViewById(R.id.btnHash);
        minus = (Button) findViewById(R.id.btnMinus);

        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);
        five.setOnClickListener(this);
        six.setOnClickListener(this);
        seven.setOnClickListener(this);
        eight.setOnClickListener(this);
        nine.setOnClickListener(this);
        del.setOnClickListener(this);
        zero.setOnClickListener(this);
        hash.setOnClickListener(this);
        minus.setOnClickListener(this);


        //get the game status to continue game
        Intent intent = getIntent();
        checkStatus = intent.getBooleanExtra("checkStatus", false);

        generateQuestion(); //call the generate question method on create

    }

    // method for calculator on click
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnOne:
                inputNum += "1";
                tvAnswer.setText(inputNum);
                break;
            case R.id.btnTwo:
                inputNum += "2";
                tvAnswer.setText(inputNum);
                break;
            case R.id.btnThree:
                inputNum += "3";
                tvAnswer.setText(inputNum);
                break;
            case R.id.btnFour:
                inputNum += "4";
                tvAnswer.setText(inputNum);
                break;
            case R.id.btnFive:
                inputNum += "5";
                tvAnswer.setText(inputNum);
                break;
            case R.id.btnSix:
                inputNum += "6";
                tvAnswer.setText(inputNum);
                break;
            case R.id.btnSeven:
                inputNum += "7";
                tvAnswer.setText(inputNum);
                break;
            case R.id.btnEight:
                inputNum += "8";
                tvAnswer.setText(inputNum);
                break;
            case R.id.btnNine:
                inputNum += "9";
                tvAnswer.setText(inputNum);
                break;
            case R.id.btnZero:
                inputNum += "0";
                tvAnswer.setText(inputNum);
                break;
            case R.id.btnDel:
                tvAnswer.setText("");
                inputNum = "";
                break;
            case R.id.btnHash:
                onHashClick();
                break;
            case R.id.btnMinus:
                onMinusClick();
                break;
        }
    }

    // method to add the minus mark and remove it
    public void onMinusClick() {
        tvAnswer = (TextView) findViewById(R.id.txtAnswer);

        String addMin = tvAnswer.getText().toString();

        StringBuilder sb = new StringBuilder(addMin);

        if (!inputNum.equals("")) {
            // validation for clicking minus mark button
            if (tvAnswer.getText().toString().contains("-")) {
                sb.deleteCharAt(0);
                inputNum = sb.toString();
                tvAnswer.setText(inputNum);
            } else {
                sb.insert(0, "-");
                inputNum = sb.toString();
                tvAnswer.setText(inputNum);
            }
        }
    }

    // method to generate the question depending on each level
    public void generateQuestion() {
        //bump game count
        counter++;

        // get the level passed from the previous activity
        // get whether hints are on or off
        Intent intent = getIntent();
        level = intent.getIntExtra("level", 0);
        hints = intent.getBooleanExtra("hints", false);

        if (counter < 10) {
            //count down of 10 seconds
            countDownTimer = new CountDownTimer(10000, 1000) {
                //to show the remaining time
                @Override
                public void onTick(long millisUntilFinished) {
                    tvTime.setText(Long.toString(millisUntilFinished / 1000) + " sec");
                    countDownTime = (int) (millisUntilFinished / 1000);
                }

                //to reset and add the next question when time is over
                @Override
                public void onFinish() {
                    countDownTimer.cancel(); //stop timer
                    tvResult.setText("");
                    tvAnswer.setText(" ?");
                    inputNum = "";
                    tries = 0;
                    generateQuestion();
                }
            }.start(); //start the count down timer

            //to differentiate between continue game and new game
            if (!checkStatus) {
                switch (level) {
                    // Novice level
                    case 1:
                        // 2 integer expression
                        generateValues(2);
                        break;
                    // Easy level
                    case 2:
                        // 2 or 3 integer expression
                        generateValues((int) (Math.random() * 2) + 2);
                        break;
                    // Medium level
                    case 3:
                        // 2 or 3 or 4 integer expression
                        generateValues((int) (Math.random() * 3) + 2);
                        break;
                    // Guru level
                    case 4:
                        // 4 or 5 or 6 integer expression
                        generateValues((int) (Math.random() * 3) + 4);
                        break;
                }
            } else { //load from the saved file
                loadSharedPreferences();
                counter--;
            }
        } else {
            countDownTimer.cancel(); //stop timer
            // Show result screen passing score
            intent = new Intent(this, ResultActivity.class);
            intent.putExtra("score", score);
            startActivity(intent);
        }
    }

    // method to generate the values needed for each expression (<100)
    public void generateValues(int val) {

        // determine the operators
        String[] operator = {"+", "-", "*", "/"};
        StringBuilder expression = new StringBuilder("");

        int[] values = new int[val];

        // random values
        for (int x = 0; x < val; x++) {
            values[x] = (int) Math.round((Math.random() * 98) + 1);
        }

        double z = values[0];
        expression.append((int) z); // set the question

        // selecting the operation and doing the necessary steps
        for (int x = 0; x < val - 1; x++) {
            int operation = (int) (Math.random() * 4);
            String s = operator[operation];

            int a = values[x + 1];
            switch (s) {
                case "+":
                    z += a;
                    expression.append("+");
                    break;
                case "-":
                    z -= a;
                    expression.append("-");
                    break;
                case "*":
                    z *= a;
                    expression.append("*");
                    break;
                case "/":
                    z /= a;
                    expression.append("/");
                    break;
            }
            expression.append(a);
        }

        qusAnswer = (int) Math.round(z);
        expressionStr = expression.toString(); //setting expression to the string variable

        // final question generation
        tvQuestion.setText("Guess : " + expression.toString() + " =");

        // only for test purposes to get the value of the expression
        tvHint.setText(Integer.toString(qusAnswer));
    }


    //method when hash button is pressed to enter the answer
    public void onHashClick() {

        //check first whether a value is added to the answer area
        if (!(tvAnswer.getText().equals("")) && !(tvAnswer.getText().toString().trim().equals("?"))) {

            int input = Integer.parseInt(tvAnswer.getText().toString());

            //when hash is pressed to check the answer
            if (!isChecked) {
                //hints are off
                if (!hints) {
                    countDownTimer.cancel(); //stop timer
                    isChecked = true;
                    //when answer is correct
                    if (input == qusAnswer) {
                        tvResult.setTextColor(Color.rgb(0, 153, 26));
                        tvResult.setText("CORRECT");
                        calculateMarks(countDownTime); //pass the remaining time
                    } else { //when answer is wrong
                        tvResult.setTextColor(Color.rgb(255, 0, 0));
                        tvResult.setText("WRONG");
                    }
                } else { //hints are on
                    //check for number of hint attempts
                    if (tries < 4) {
                        //value is greater than answer
                        if (input > qusAnswer) {
                            tries++;
                            tvResult.setTextColor(Color.rgb(255, 0, 0));
                            tvResult.setText("WRONG");
                            tvHint.setText("Lesser! " + (4 - tries) + " attempts");
                        } else if (input < qusAnswer) { //value is lesser than answer
                            tries++;
                            tvResult.setTextColor(Color.rgb(255, 0, 0));
                            tvResult.setText("WRONG");
                            tvHint.setText("Greater! " + (4 - tries) + " attempts");
                        } else { //value is equal to answer
                            tvResult.setTextColor(Color.rgb(0, 153, 26));
                            tvResult.setText("CORRECT");
                            calculateMarks(countDownTime); //pass remaining time
                            tries = 0;
                            countDownTimer.cancel(); //stop timer
                            isChecked = true;
                            tvHint.setText(""); //to show that the value is correct
                        }
                    } else { //when attempts are over
                        //reset values for next
                        tries = 0;
                        countDownTimer.cancel(); //stop timer
                        tvHint.setText("");
                        tvResult.setText("");
                        tvAnswer.setText(" ?");
                        inputNum = "";
                        generateQuestion();
                    }
                }
            } else { //to go to the next round when hash is pressed
                countDownTimer.cancel(); //stop timer
                isChecked = false;
                tvResult.setText("");
                tvAnswer.setText(" ?");
                inputNum = "";
                generateQuestion();
            }
        }
    }

    //method to calculate the final score after 10 rounds are over
    public void calculateMarks(int time) {
        if (time != 10) {
            if (time != 0) {
                score += (100 / (10 - time));
            } else {
                score += 1;
            }
        } else {
            score += 100;
        }

    }

    //method when back is pressed
    @Override
    public void onBackPressed() {
        final AlertDialog.Builder message = new AlertDialog.Builder(CalculatorActivity.this);
        message.setTitle("Save Game");
        message.setMessage("Do you want to save your game? ");
        message.setCancelable(false);
        //when clicked on yes go to main menu
        message.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                storeSharedPreferences();
                countDownTimer.cancel(); //stop timer
                intent = new Intent(CalculatorActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        //when clicked on no stay on current screen
        message.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        message.show();
    }

    //method to save data
    public void storeSharedPreferences() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("Data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.clear();
        editor.putInt("gameCount", counter);
        editor.putInt("qusAnswer", qusAnswer);
        editor.putBoolean("hints", hints);
        editor.putInt("level", level);
        editor.putInt("currentScore", score);
        editor.putString("problemExpression", expressionStr);

        editor.commit();
    }

    //method to load the saved data
    public void loadSharedPreferences() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("Data", Context.MODE_PRIVATE);
        checkStatus = false;
        counter = pref.getInt("gameCount", 0);
        qusAnswer = pref.getInt("qusAnswer", 0);
        hints = pref.getBoolean("hints", false);
        level = pref.getInt("level", 0);
        score = pref.getInt("currentScore", 0);
        expressionStr = pref.getString("problemExpression", "");

        tvQuestion.setText("Guess : " + expressionStr + " =");

        // only for test purposes to get the value of the expression
        TextView hint = (TextView) findViewById(R.id.txtHint);
        hint.setText(Integer.toString(qusAnswer));

        pref.edit().clear().commit();
    }
}
