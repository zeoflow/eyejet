package com.zeoflow.eyejet.demo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.zeoflow.app.Activity;
import com.zeoflow.eyejet.Eyejet;
import com.zeoflow.eyejet.EyejetField;
import com.zeoflow.eyejet.annotation.ShareProperty;
import com.zeoflow.eyejet.annotation.ShareType;

import java.util.Random;

@UserScope
public class SecondActivity extends Activity
{

    @ShareProperty(value = "user", shareType = ShareType.KEEP_ACTIVE)
    public EyejetField<User> user = new EyejetField<>();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Eyejet.shareLifecycle(this, this);
        TextView txtV = findViewById(R.id.txtV);
        user.observe(this, obj -> txtV.setText("txt: " + obj.id + " > " + obj.firstName));
        user.setValue(
                User.create(
                        new Random().nextInt(99999999),
                        "Teodor",
                        "Grigor"
                )
        );
        findViewById(R.id.button).setOnClickListener(v -> configureNewActivity(ThirdActivity.class).start());
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

}
