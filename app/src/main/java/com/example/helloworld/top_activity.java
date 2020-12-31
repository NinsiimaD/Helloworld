package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class top_activity extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    private Button playing, play;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top);
        playing = findViewById(R.id.playing);
        play = findViewById(R.id.play);
    }
    public void stop(View view) {
        stopService(new Intent(this, MusicService.class));
        playing.setVisibility(View.GONE);
        play.setVisibility(View.VISIBLE);
    }

    public void play(View view) {
        startService(new Intent(this, MusicService.class));
        Toast.makeText(this, "Playing...", Toast.LENGTH_SHORT).show();
        view.setVisibility(View.GONE);
        playing.setVisibility(View.VISIBLE);
    }
    @Override
    protected void onPause() {
        super.onPause();
        playing.setVisibility(View.GONE);
        play.setVisibility(View.VISIBLE);
        stopService(new Intent(this, MusicService.class));
    }
}



