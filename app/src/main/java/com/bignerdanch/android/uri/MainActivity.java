package com.bignerdanch.android.uri;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.bignerdanch.android.uri.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private boolean autoRecognizing = true;

    Fragment f1, f2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        f1 = new AutoRecognizing();
        f2 = new ManualRecognizing();
        var fm = getSupportFragmentManager();
        fm.findFragmentById(binding.layout.getId());
        fm.beginTransaction().add(binding.layout.getId(), f1).commit();

        binding.switchView.setOnCheckedChangeListener((a, view) -> {
            if (autoRecognizing) {
                binding.switchText.setText(getResources().getString(R.string.switchTextManual));
                fm.beginTransaction().replace(binding.layout.getId(), f2).commit();
            } else {
                binding.switchText.setText(getResources().getString(R.string.switchTextAuto));
                fm.beginTransaction().replace(binding.layout.getId(), f1).commit();
            }
            autoRecognizing = !autoRecognizing;
        });
    }
}