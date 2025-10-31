package com.example.tpinmobiliaria.ui.inquilinos;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tpinmobiliaria.R;
import com.example.tpinmobiliaria.databinding.FragmentInquilinoBinding;
import com.example.tpinmobiliaria.models.Inquilino;

public class InquilinoFragment extends Fragment {

    private InquilinoViewModel vm;
    private FragmentInquilinoBinding binding;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        vm = new ViewModelProvider(this).get(InquilinoViewModel.class);
        binding = FragmentInquilinoBinding.inflate(inflater, container, false);

        vm.recuperarInquilino(getArguments());
        vm.getmInquilino().observe(getViewLifecycleOwner(), new Observer<Inquilino>() {
            @Override
            public void onChanged(Inquilino inquilino) {
                binding.tvApellido.setText(inquilino.getApellido());
                binding.tvNombre.setText(inquilino.getNombre());
                binding.tvDNI.setText(inquilino.getDni());
                binding.tvEmail.setText(inquilino.getEmail());
                binding.tvTelefono.setText(inquilino.getTelefono());
            }
        });


        return binding.getRoot();
    }


    }



