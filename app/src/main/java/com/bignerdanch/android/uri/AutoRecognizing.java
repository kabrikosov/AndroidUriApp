package com.bignerdanch.android.uri;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.bignerdanch.android.uri.databinding.FragmentAutoRecognizingBinding;

import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AutoRecognizing#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AutoRecognizing extends Fragment {

    private FragmentAutoRecognizingBinding binding;

    public AutoRecognizing() {
        // Required empty public constructor
    }


    public static AutoRecognizing newInstance() {
        return new AutoRecognizing();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAutoRecognizingBinding.inflate(inflater, container, false);

        binding.submit.setOnClickListener(view -> {
            var text = binding.editText.getText().toString();
            var geo = Pattern.compile("-?\\d+\\.\\d+,-?\\d+\\.\\d+").matcher(text);
            var url = Pattern.compile("^\\w*\\.\\w+[\\w/.?&]*").matcher(text);
            var phone = Pattern.compile("\\+?[0-9]{10,11}").matcher(text);
            Intent intent = null;

            if (geo.find()) {
                var uri = Uri.parse(String.format("geo:%s", text));
                intent = new Intent(Intent.ACTION_VIEW, uri);
            } else if (url.find()) {
                var uri = Uri.parse(String.format("http:%s", text));
                intent = new Intent(Intent.ACTION_VIEW, uri);

            } else if (phone.find()) {
                var uri = Uri.parse(String.format("tel:%s", text));
                intent = new Intent(Intent.ACTION_VIEW, uri);
            }

            if (intent == null){
                Log.e("ERROR", "NO DATA RECOGNIZED");
            } else {
                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    Log.e("ERROR", "NO ACTIVITY RESOLVED");
                }
            }
        });

        return binding.getRoot();
    }
}