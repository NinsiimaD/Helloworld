package com.example.helloworld;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MusicService extends Service {
    MediaPlayer myPlayer;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate() {
        myPlayer = MediaPlayer.create(this, R.raw.theway);
        myPlayer.setLooping(false);
    }
    @Override
    public void onStart(Intent intent, int startid) {
        myPlayer.start();
    }
    @Override
    public void onDestroy() {
        Toast.makeText(this, "Music Stopped", Toast.LENGTH_LONG).show();
        myPlayer.stop();
    }
}