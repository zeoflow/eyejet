package com.zeoflow.eyejet.demo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.zeoflow.app.Activity;
import com.zeoflow.app.Fragment;
import com.zeoflow.eyejet.Eyejet;
import com.zeoflow.eyejet.EyejetField;
import com.zeoflow.eyejet.annotation.EyejetScope;
import com.zeoflow.eyejet.annotation.ShareProperty;
import com.zeoflow.eyejet.demo.databinding.ActivityMainBinding;

import java.util.Random;
import java.util.Timer;

@EyejetScope
public class MainActivity extends Activity
{

    @ShareProperty("user")
    public static EyejetField<User> user = new EyejetField<>();

    private ActivityMainBinding binding;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Eyejet.shareLifecycle(
                this,
                this
        );

        // Set the layout for the main activity
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        MainFragment fragment = new MainFragment();
        getSupportFragmentManager().beginTransaction().replace(binding.container.getId(), fragment).commit();

        binding.button.setOnClickListener(v -> configureNewActivity(SecondActivity.class).start());
        user.observe(this, obj -> binding.txtV.setText("txt: " + obj.id + " > " + obj.firstName));

        user.setValue(
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
