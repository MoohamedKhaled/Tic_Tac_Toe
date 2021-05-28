package com.example.hciproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener {// View.onclick we use it to make click event
    private TextView playerOneScore,playerTwoScore,playerStatus; //the text view that we will change the text's content
    private Button[] buttons = new Button [9];//array for the X/O input buttons
    private Button resetGame ,backMenu; //the reset and back buttons
    private int playerOneSCoreCount,playerTwoScoreCount, roundCount;
    private boolean activePlayer; //to switch between players
    // we will use an array to check where the player put his shape the box have p1 or p2 or empty so we will give empty=0 p1=1 p2=2
    int [] gameState = {0,0,0,0,0,0,0,0,0}; //the game start with empty boxes
    //the winning positions
    int [][] winningPositions = {
            {0,1,2},{3,4,5},{6,7,8},
            {0,3,6},{1,4,7},{2,5,8},
            {0,4,8},{2,4,6}
    };
    String colorOfX = "#42b90b";
    String colorOfO = "#e3052e";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        playerOneScore = (TextView) findViewById(R.id.playerOneScore);
        playerTwoScore = (TextView) findViewById(R.id.playerTwoScore);
        playerStatus = (TextView) findViewById(R.id.playerStatus);
        resetGame = (Button) findViewById(R.id.restBut);
        backMenu = (Button) findViewById(R.id.backBut);
        buttons[0] = (Button) findViewById(R.id.but0);
        buttons[1] = (Button) findViewById(R.id.but1);
        buttons[2] = (Button) findViewById(R.id.but2);
        buttons[3] = (Button) findViewById(R.id.but3);
        buttons[4] = (Button) findViewById(R.id.but4);
        buttons[5] = (Button) findViewById(R.id.but5);
        buttons[6] = (Button) findViewById(R.id.but6);
        buttons[7] = (Button) findViewById(R.id.but7);
        buttons[8] = (Button) findViewById(R.id.but8);
        for (int i=0;i<buttons.length;i++){
            buttons[i].setOnClickListener(this);
        }
        roundCount = 0;
        playerOneSCoreCount = 0;
        playerTwoScoreCount = 0;
        activePlayer = true;//its true for the first player now than we will make it false

    }

    @Override
    public void onClick(View v) {
        if (!((Button)v).getText().toString().equals("")) { //if the button empty return don't make it click
            return;
        }
        String buttonID = v.getResources().getResourceEntryName(v.getId());
        int gameStatePointer = Integer.parseInt( buttonID.substring(3,4));//5od el string kolo w rg3li a5r char aly hwa elrakm
        if(activePlayer){
            ((Button)v).setText("X");
            ((Button)v).setTextColor(Color.parseColor(colorOfX));
            gameState[gameStatePointer] = 1;
        }
        else {
            ((Button)v).setText("O");
            ((Button)v).setTextColor(Color.parseColor(colorOfO));
            gameState[gameStatePointer] = 2;
        }
        roundCount++;//that mean a player has been played
        if(checkWinner()){
            if(activePlayer){
                playerOneSCoreCount++;
                updatePlayerScore();
                playAgain();
            }
            else{
                playerTwoScoreCount++;
                updatePlayerScore();
                playAgain();
            }
        }
        else if(roundCount == 9){
            playAgain();
        }
        else{
            activePlayer = !activePlayer;
        }
        resetGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAgain();
                playerOneSCoreCount= 0;
                playerTwoScoreCount=0;
                updatePlayerScore();
            }
        });
        backMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(),MainActivity.class);
                startActivity(myIntent);
            }
        });


    }
    public boolean checkWinner(){
        boolean winnerResult = false;
        for(int [] winningPosition : winningPositions){//in this for we check the all nested  array
            if(gameState[winningPosition[0]]==gameState[winningPosition[1]]&&// if we have the first button =  to the second
                    gameState[winningPosition[1]]==gameState[winningPosition[2]]&& // and if we have the second one =  to the third
                    gameState[winningPosition[2]] != 0 ){//and if this buttons not empty which is = 0 than we have winner
                winnerResult = true;

            }
        }
        return winnerResult;

    }
    public void updatePlayerScore(){
        playerOneScore.setText(Integer.toString(playerOneSCoreCount));
        playerTwoScore.setText(Integer.toString(playerTwoScoreCount));
    }
    public void playAgain(){
        roundCount = 0;
        activePlayer = true;
        for(int i = 0; i<buttons.length;i++){
            gameState[i]= 0;
            buttons[i].setText("");
        }
    }




    }
