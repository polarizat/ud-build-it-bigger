package com.udacity.gradle.builditbigger;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class JokesActivity extends AppCompatActivity {

    public static final String EXTRA_JOKE = "EXTRA_JOKE";

    TextView jokeTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jokes);

        jokeTv = findViewById(R.id.jokes_joke_tv);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String joke = extras.getString(EXTRA_JOKE);
            jokeTv.setText(joke);
        }

    }
}