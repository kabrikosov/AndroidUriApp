package com.bignerdanch.android.uri;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bignerdanch.android.uri.databinding.FragmentManualRecognizingBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ManualRecognizing#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ManualRecognizing extends Fragment {

    private FragmentManualRecognizingBinding binding;

    public ManualRecognizing() {
        // Required empty public constructor
    }

    public static ManualRecognizing newInstance() {
        return new ManualRecognizing();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentManualRecognizingBinding.inflate(inflater, container, false);

        binding.editText.setInputType(InputType.TYPE_NULL);
        binding.editText.setOnClickListener(view -> {
            if (binding.rGroup.getCheckedRadioButtonId() == -1){
                binding.editText.setText("Выберите опцию");
                binding.editText.setTextColor(ContextCompat.getColor(getContext(), android.R.color.holo_red_dark));
            } else {
                binding.editText.setTextColor(ContextCompat.getColor(getContext(), android.R.color.white));
            }
        });

        binding.submit.setOnClickListener(view -> {
            binding.editText.performClick();
            if (binding.rGroup.getCheckedRadioButtonId() != -1){
                binding.editText.setFocusable(true);
                Uri uri = null;
                var text = binding.editText.getText().toString();
                if (binding.rGroup.getCheckedRadioButtonId() == binding.geoRadio.getId()){
                    uri = Uri.parse(String.format("geo:%s", text));
                    binding.editText.setInputType(InputType.TYPE_CLASS_TEXT);
                } else if (binding.rGroup.getCheckedRadioButtonId() == binding.webRadio.getId()){
                    uri = Uri.parse(String.format("http:%s", text));
                    binding.editText.setInputType(InputType.TYPE_CLASS_TEXT);
                } else if (binding.rGroup.getCheckedRadioButtonId() == binding.phoneRadio.getId()){
                    uri = Uri.parse(String.format("tel:%s", text));
                    binding.editText.setInputType(InputType.TYPE_CLASS_PHONE);
                }

                if (uri != null){
                    var i = new Intent(Intent.ACTION_VIEW, uri);
                    var tmp = i.resolveActivity(binding.getRoot().getContext().getPackageManager());
                    if (tmp != null) {
                        startActivity(i);
                    } else {
                        Log.e("Intent", "Не получается обработать намерение!");
                    }
                } else Log.e("ERROR", "CANNOT START INTENT");
            }
        });

        binding.rGroup.setOnCheckedChangeListener((radioGroup, pos) -> {
            binding.editText.setInputType(InputType.TYPE_CLASS_TEXT);
            binding.editText.setText("");
            binding.editText.setTextColor(ContextCompat.getColor(getContext(), android.R.color.white));
        });

        return binding.getRoot();
    }
}