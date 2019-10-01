package com.example.lionortiger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    enum Player{
        ONE, TWO, NO;
    }
    Player  currentPlayer = Player.ONE;

    Player[] playerChoices = new Player[9];

    private  boolean gameOver = false;
    private GridLayout gridLayout;


    int [][] winnerRowColumn = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{2,4,6},{0,4,8}};
    private Button btnReset;


    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        count = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        for(int index = 0; index<playerChoices.length; index++) {
            playerChoices[index] = Player.NO;
        }
        gridLayout = findViewById(R.id.gridLayout);
        btnReset = findViewById(R.id.button);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame(v);
            }
        });

    }
    public void imgIsTapped(View imageView){

        ImageView tappedImageView = (ImageView) imageView;
        int tiTag = Integer.parseInt(tappedImageView.getTag().toString());
        if(playerChoices[tiTag] == Player.NO && gameOver==false) {
            count = count + 1;
            tappedImageView.setTranslationX(-2000);
            playerChoices[tiTag] = currentPlayer;
            if (currentPlayer == Player.ONE) {
                tappedImageView.setImageResource(R.drawable.lion);
                currentPlayer = Player.TWO;
            } else if (currentPlayer == Player.TWO) {
                tappedImageView.setImageResource(R.drawable.tiger);
                currentPlayer = Player.ONE;
            }
            tappedImageView.animate().translationXBy(2000).alpha(1).rotation(3600).setDuration(1000);

            //Toast.makeText(this, tappedImageView.getTag().toString(), Toast.LENGTH_SHORT).show();
            for (int[] winnerColumns : winnerRowColumn) {
                if (playerChoices[winnerColumns[0]] == playerChoices[winnerColumns[1]]
                        && playerChoices[winnerColumns[1]] == playerChoices[winnerColumns[2]] && playerChoices[winnerColumns[0]] != Player.NO) {
                    String winner = "";
                    gameOver = true;
                    btnReset.setVisibility(View.VISIBLE);
                    if (currentPlayer == Player.ONE) {
                        winner = "Player Two is  winner";
                    } else if (currentPlayer == Player.TWO) {
                        winner = "Player One is winner";
                    }

                    Toast.makeText(this, winner , Toast.LENGTH_LONG).show();

                }





            }


        }




        if(count == playerChoices.length) {

            btnReset.setVisibility(View.VISIBLE);
            gameOver = true;
            Toast.makeText(this, "It's a draw!", Toast.LENGTH_LONG).show();
            count = 0;
        }



    }
    public void resetGame(View buttonView){

        count =0;
        for(int index=0; index<gridLayout.getChildCount(); index++){
            ImageView imageView = (ImageView) gridLayout.getChildAt(index);
            imageView.setImageDrawable(null);
            imageView.setAlpha(0.2f);
        }
        currentPlayer = Player.ONE;
        for(int index = 0; index<playerChoices.length; index++) {
            playerChoices[index] = Player.NO;
        }

        gameOver = false;
        btnReset.setVisibility(View.GONE);

    }
}


