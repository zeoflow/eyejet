package com.zeoflow.eyejet.demo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.zeoflow.app.Fragment;
import com.zeoflow.eyejet.Eyejet;
import com.zeoflow.eyejet.EyejetField;
import com.zeoflow.eyejet.annotation.EyejetScope;
import com.zeoflow.eyejet.annotation.ShareProperty;
import com.zeoflow.eyejet.demo.databinding.FragmentMainBinding;

@EyejetScope
public class MainFragment extends Fragment
{

    @ShareProperty("user")
    public static EyejetField<User> user = new EyejetField<>();
    private FragmentMainBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        binding = FragmentMainBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Eyejet.shareLifecycle(
                this,
                this
        );

        user.observe(this, new Observer<User>()
        {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChanged(User user)
            {
                if (user == null)
                {
                    return;
                }
                binding.txtV.setText("txt: " + user.id + " > " + user.firstName);
            }
        });
    }

}
