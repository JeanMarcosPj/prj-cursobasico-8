package com.example.jeanmarcos.prj_cursobasico_8;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, DialogInterface.OnClickListener {

    //Creating all needed objects
    private Button allButtons [][] = new Button[3][3];
    private Button btn_reset;
    private boolean playerSwitch = true;
    private int roundCount = 0;
    private int p1Points = 0;
    private int p2Points = 0;
    private TextView txt_p1, txt_p2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Getting both txt and reset button
        txt_p1 = findViewById(R.id.txt_xpy);
        txt_p2 = findViewById(R.id.txt_opy);
        btn_reset = findViewById(R.id.btn_reset);

        //Getting all buttons in a bidimensional array and setting onClickListener
        for (int x = 0; x < 3; x++){
            for (int y = 0; y < 3; y++){
                String idButtons = "btn_" + x + y;
                int ids = getResources().getIdentifier(idButtons, "id", getPackageName());
                allButtons[x][y] = findViewById(ids);
                allButtons[x][y].setOnClickListener(this);
            }
        }

        //Setting onClick function for reset button
        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int x = 0; x < 3; x++){
                    for (int y = 0; y < 3; y++){
                        allButtons[x][y].setText("");
                    }
                }
                roundCount = 0;
                txt_p1.setText("0");
                txt_p2.setText("0");
                p1Points = 0;
                p2Points = 0;
            }
        });
    }

    //onClick function for buttons
    @Override
    public void onClick(View v) {
        //Checking if the button has O or X and then painting if empty
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }
        if (playerSwitch) {
            ((Button) v).setText("X");
            ((Button) v).setTextColor(Color.parseColor("#1e5ca2"));
        } else {
            ((Button) v).setText("O");
            ((Button) v).setTextColor(Color.parseColor("#bc2026"));
        }
        roundCount++;

        //Checking if we have a winner or a draw, if winner then dropping Alert and resetting the table + adding points + switching players
        if (checkWin()) {
            if (playerSwitch) {
                p1Points++;
                new AlertDialog.Builder(this)
                        .setTitle("X Wins this one! Congrats.")
                        .setPositiveButton("Ok", this)
                        .create().show();
                //reset(playerSwitch, p1Points);
            } else {
                p2Points++;
                new AlertDialog.Builder(this)
                        .setTitle("O Wins this one! Congrats.")
                        .setPositiveButton("Ok", this)
                        .create().show();
                //reset(playerSwitch, p2Points);
            }
        } else if (roundCount == 9) {
            roundCount++;
            new AlertDialog.Builder(this)
                    .setTitle("Draw, I'm sorry!")
                    .setPositiveButton("Ok", this)
                    .create().show();
            //reset(false, p2Points);
        } else {
            playerSwitch = !playerSwitch;
        }
    }

    //checkWin function, return true if there is a winner
    private boolean checkWin(){
        //Getting the strings of all the buttons
        String[][] fields = new String[3][3];
        for (int x = 0; x < 3; x++){
            for (int y = 0; y < 3; y++) {
                fields[x][y] = allButtons[x][y].getText().toString();
            }
        }
        //Checking rows and columns
        for (int i = 0; i < 3; i++){
            if (fields[i][0].equals(fields[i][1]) && fields[i][0].equals(fields[i][2]) && !fields[i][0].equals("")){
                return true;
            }
            if (fields[0][i].equals(fields[1][i]) && fields[0][i].equals(fields[2][i]) && !fields[0][i].equals("")){
                return true;
            }
        }

        //Checking diagonals
        if (fields[0][0].equals(fields[1][1]) && fields[0][0].equals(fields[2][2]) && !fields[0][0].equals("")){
            return true;
        }
        if (fields[2][0].equals(fields[1][1]) && fields[2][0].equals(fields[0][2]) && !fields[2][0].equals("")){
            return true;
        }
        return false;
    }

    //updatePoints function
    private void updatePoints(int points){
        if(playerSwitch){
            txt_p1.setText(String.valueOf(points));
        }else{
            txt_p2.setText(String.valueOf(points));
        }
    }

    //reset function, this resets the table to blank, make X the next player and reset roundCount
    private void reset(int points){
        for (int x = 0; x < 3; x++){
            for (int y = 0; y < 3; y++){
                allButtons[x][y].setText("");
            }
        }
        if (roundCount == 10){
            playerSwitch = true;
            roundCount = 0;
        }else{
            updatePoints(points);
            playerSwitch = true;
            roundCount = 0;
        }
    }

    //onClick function needed for the Alerts
    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (playerSwitch){
            reset(p1Points);
        }else{
            reset(p2Points);
        }
    }
}
