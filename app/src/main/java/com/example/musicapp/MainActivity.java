package com.example.musicapp;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    ImageView songImage;
    TextView songTitle;
    SeekBar seekBar;
    FloatingActionButton btnPlay, btnPause, btnNext, btnPrev;

    MediaPlayer mediaPlayer;

    int[] songs = {
            R.raw.chinna_chinna,
            R.raw.kadhal_kavithaigal,
            R.raw.kadhalikkum_pennin,
            R.raw.ottagathai_kattiko,
            R.raw.roja_roja
    };

    int[] images = {
            R.drawable.chinna_chinna,
            R.drawable.kadhal_kavithaigal,
            R.drawable.kadhalikkum_pennin,
            R.drawable.otagatha_kattiko,
            R.drawable.roja_roja
    };

    String[] titles = {
            "Chinna Chinna Aasai",
            "Kadhal Kavithaigal",
            "Kadhalikkum Pennin Kaigal",
            "Ottagatha Kattiko",
            "Roja Roja"
    };

    int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        songImage = findViewById(R.id.songImage);
        songTitle = findViewById(R.id.songTitle);
        seekBar = findViewById(R.id.seekBar);
        btnPlay = findViewById(R.id.btnPlay);
        btnPause = findViewById(R.id.btnPause);
        btnNext = findViewById(R.id.btnNext);
        btnPrev = findViewById(R.id.btnPrev);

        loadSong(currentIndex);

        btnPlay.setOnClickListener(v -> mediaPlayer.start());

        btnPause.setOnClickListener(v -> mediaPlayer.pause());

        btnNext.setOnClickListener(v -> {
            currentIndex = (currentIndex + 1) % songs.length;
            loadSong(currentIndex);
        });

        btnPrev.setOnClickListener(v -> {
            currentIndex = (currentIndex - 1 + songs.length) % songs.length;
            loadSong(currentIndex);
        });
    }

    private void loadSong(int index) {

        if (mediaPlayer != null) {
            mediaPlayer.release();
        }

        mediaPlayer = MediaPlayer.create(this, songs[index]);
        songImage.setImageResource(images[index]);
        songTitle.setText(titles[index]);

        seekBar.setMax(mediaPlayer.getDuration());

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null) {
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    handler.postDelayed(this, 1000);
                }
            }
        }, 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }
}