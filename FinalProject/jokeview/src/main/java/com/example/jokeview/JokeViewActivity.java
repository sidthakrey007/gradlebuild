package com.example.jokeview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class JokeViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_view);
        Intent message = getIntent();
        String joke = message.getStringExtra(getString(R.string.intent_key));
        TextView jokeTextView = (TextView) findViewById(R.id.jokeText);
        jokeTextView.setText(joke);

    }
}
