package com.golan.amit.simon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class WonGameActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView ivOk;
    Button btnPlayAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_won_game);

        ivOk = findViewById(R.id.ivOk);
        btnPlayAgain = findViewById(R.id.btnPlayAgainWonId);
        btnPlayAgain.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == btnPlayAgain) {
            Intent i = new Intent(this, SimonMainActivity.class);
            startActivity(i);
        }
    }
}
