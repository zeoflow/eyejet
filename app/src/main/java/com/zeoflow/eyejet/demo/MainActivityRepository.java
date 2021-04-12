package com.zeoflow.eyejet.demo;

import androidx.lifecycle.LifecycleOwner;

import com.zeoflow.eyejet.Eyejet;
import com.zeoflow.eyejet.EyejetField;
import com.zeoflow.eyejet.annotation.ShareProperty;

@UserScope // custom scope
public class MainActivityRepository
{

    @ShareProperty("user")
    public EyejetField<User> user = new EyejetField<>();

    public MainActivityRepository(LifecycleOwner lifecycleOwner)
    {
        Eyejet.shareLifecycle(
                this,
                lifecycleOwner
        );
    }
}
