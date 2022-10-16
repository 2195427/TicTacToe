package com.example.tic_tac_toe_2195427;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class PlayerSetup extends AppCompatActivity {

    private EditText playerRed;
    private EditText playerBlue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_setup);

        playerRed = findViewById(R.id.playerRedName);
        playerBlue = findViewById(R.id.playerBlueName);
    }
    public void submitButtonClick(View view){
        String playerRedName = playerRed.getText().toString();
        String playerBlueName = playerBlue.getText().toString();

        Intent intent = new Intent(this, GamePage.class);
        intent.putExtra("PLAYER_NAMES", new String[] {playerRedName, playerBlueName});
        startActivity(intent);
    }
}