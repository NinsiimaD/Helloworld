package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class top_activity extends AppCompatActivity {
    MediaPlayer M;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top);
        Button pl =(Button)findViewById(R.id.button);
        pl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                M=MediaPlayer.create(getApplicationContext(),R.raw.me);
                M.start();
            }
        });
        Button test =(Button)findViewById(R.id.po);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (M !=null&& M.isPlaying()){
                    M.stop();
                }
            }
        });

    }
}
