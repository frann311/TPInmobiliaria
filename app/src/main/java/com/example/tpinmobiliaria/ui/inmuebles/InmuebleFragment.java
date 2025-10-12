package com.example.tpinmobiliaria.ui.inmuebles;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import com.example.tpinmobiliaria.databinding.FragmentInmueblesBinding;

public class InmuebleFragment extends Fragment {

    private FragmentInmueblesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        InmuebleViewModel galleryViewModel =
                new ViewModelProvider(this).get(InmuebleViewModel.class);

        binding = FragmentInmueblesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textGallery;
        galleryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}