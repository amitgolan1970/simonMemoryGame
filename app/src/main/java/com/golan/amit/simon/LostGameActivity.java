package com.golan.amit.simon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class LostGameActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView ivKhatfani, ivNotok;
    Button btnPlayAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_game);

        init();
    }

    private void init() {
        ivKhatfani = findViewById(R.id.ivKhatfaniId);
        ivNotok = findViewById(R.id.ivNotokId);
        btnPlayAgain = findViewById(R.id.btnPlayAgainId);
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
