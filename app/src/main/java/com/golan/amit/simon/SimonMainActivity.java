package com.golan.amit.simon;

import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Build;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

public class SimonMainActivity extends AppCompatActivity implements View.OnClickListener {

    int tmpTestIndex;

    Button btnTryId;
    Button[] btnCoard;
    //    TextView tvInfo;
    SoundPool sp;
    int csp[];
    Animation animFadeout, animReverse, animRotate;
    SimonHelper sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simon_main);

        init();
        init_sound();

        btnTryId.setText(sh.h_representation());
        moveArroundAsync();
    }

    private void init_sound() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes aa = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_GAME).build();
            sp = new SoundPool.Builder()
                    .setMaxStreams(10).setAudioAttributes(aa).build();
        } else {
            sp = new SoundPool(10, AudioManager.STREAM_MUSIC, 1);
        }

        csp = new int[] {
          sp.load(this, R.raw.do_sound,1), sp.load(this, R.raw.re,1),
                sp.load(this, R.raw.me, 1), sp.load(this, R.raw.fa, 1),
                sp.load(this, R.raw.sol, 1)
        };
    }

    private void init() {
        sh = new SimonHelper();
        sh.fill();

        btnCoard = new Button[]{
                findViewById(R.id.btnCoardId0), findViewById(R.id.btnCoardId1), findViewById(R.id.btnCoardId2),
                findViewById(R.id.btnCoardId3), findViewById(R.id.btnCoardId4)
        };

        for (int i = 0; i < btnCoard.length; i++) {
            btnCoard[i].setOnClickListener(this);
        }

        btnTryId = (Button) findViewById(R.id.btnTryId);
        btnTryId.setOnClickListener(this);

        animFadeout = AnimationUtils.loadAnimation(this, R.anim.fadeout_anim);

        animReverse = AnimationUtils.loadAnimation(this, R.anim.reverse_anim);
        animRotate = AnimationUtils.loadAnimation(this, R.anim.rotate_anim);
    }

    private void moveArroundAsync() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                setText(btnTryId, sh.h_representation());
                SystemClock.sleep(550);
                try {
                    moveArround();
                } catch (Exception e) {
                    Log.d(MainActivity.DEBUGTAG, "move arround exception");
                }
                setText(btnTryId, "");
                return null;
            }
        }.execute();
    }

    private void setText(final Button text,final String value){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                boolean isClickable = true;
                if(value.length() > 0)
                    isClickable = false;
                for(int i = 0; i < btnCoard.length; i++) {
                    btnCoard[i].setClickable(isClickable);
                }
                text.setText(value);
            }
        });
    }


    private void moveArround() {
        Log.d(MainActivity.DEBUGTAG, "i: " + sh.getOut_index() + ", generated: " + sh.h_representation());
        for (int i = 0; i < sh.getOut_index() + 1; i++) {
            int tmpCrd = sh.getCoardByIndex(i);
            float y = btnCoard[tmpCrd].getY();
            Log.d(MainActivity.DEBUGTAG, "i: " + i + ", index coard: " + tmpCrd + ", y location: " + y);

            for (int j = 0; j < 100; j += 5) {
                btnCoard[tmpCrd].setY(y + (float) j);
                SystemClock.sleep(20);
            }
            btnCoard[tmpCrd].setY(y);
        }
    }

    @Override
    public void onClick(View v) {

        if (v == btnTryId) {
            Toast.makeText(this, sh.h_representation(), Toast.LENGTH_SHORT).show();
            moveArroundAsync();
            return;
        } else {
            int actual_val = -1;
            for (int i = 0; i < SimonHelper.COARDS; i++) {
                if (v == btnCoard[i]) {
                    sp.play(csp[i], 1, 1, 0, 0, 1);
                    v.setAlpha(1);
                    try {
                        btnCoard[i].startAnimation(animRotate);
                    } catch (Exception e) {
                        Log.d(MainActivity.DEBUGTAG, "animation rotate exception");
                    }
                    actual_val = i;
                    break;
                }
            }
            if (actual_val == -1) {
                Log.d(MainActivity.DEBUGTAG, "actual value is -1, not possible, error");
                return;
            }
            if (actual_val == sh.getCoardByIndex(sh.getRunning_index())) {
                if (sh.getRunning_index() == SimonHelper.ROUNDS - 1) {
                    Toast.makeText(this, "Won", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(this, WonGameActivity.class);
                    startActivity(i);
                } else {
                    if (sh.getRunning_index() == sh.getOut_index()) {
                        sh.setRunning_index(0);
                        sh.increaseOut_index();
                        Toast.makeText(this, "Next round. " + sh.h_representation(), Toast.LENGTH_SHORT).show();
                        btnTryId.setText(sh.h_representation());
                        try {
                            moveArroundAsync();
                        } catch (Exception e) {
                            Log.d(MainActivity.DEBUGTAG, "move arround async exception");
                        }
                    } else {
                        sh.increaseRunning_index();
                    }
                }
            } else {
                Intent i = new Intent(this, LostGameActivity.class);
                startActivity(i);
            }
        }
    }
}
