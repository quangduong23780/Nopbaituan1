package com.example.myapplication.manhinhchao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.myapplication.DangNhapActivity;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

public class ManhinhchaoMainActivity extends AppCompatActivity {
    ImageView imgwelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manhinhchao_main);
        imgwelcome=findViewById(R.id.imgwelcome);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.welcome);
        imgwelcome.startAnimation(animation);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(ManhinhchaoMainActivity.this, DangNhapActivity.class);
                startActivity(intent);
                finish();
            }
        },2000);
    }
}