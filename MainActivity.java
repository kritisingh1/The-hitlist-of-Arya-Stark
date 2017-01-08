package com.example.dell.aryashitlist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView aryaStarkTextView;
    ImageView aryaStarkImageView;
    Button hitlistButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        aryaStarkTextView = (TextView)findViewById(R.id.aryaStarkTextView);
        aryaStarkImageView = (ImageView)findViewById(R.id.aryaStarkImageView);
        hitlistButton = (Button)findViewById(R.id.hitlistButton);
    }

    public void nextActivity(View view){
        Intent intent = new Intent(this,Hitlist.class);
        startActivity(intent);
    }
}

