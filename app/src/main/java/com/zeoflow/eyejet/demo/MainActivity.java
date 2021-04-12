package com.zeoflow.eyejet.demo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.zeoflow.app.Activity;

import java.util.Random;

public class MainActivity extends Activity
{

    private final MainActivityRepository repository = new MainActivityRepository(this);

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView txtV = findViewById(R.id.txtV);
        findViewById(R.id.button).setOnClickListener(v -> configureNewActivity(SecondActivity.class).start());
        repository.user.observe(this, obj -> txtV.setText("txt: " + obj.id + " > " + obj.firstName));
        repository.user.setValue(
                User.create(
                        new Random().nextInt(99999999),
                        "Teodor",
                        "Grigor"
                )
        );
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }
}
