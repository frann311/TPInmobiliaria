package com.example.tpinmobiliaria.ui.contratos;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tpinmobiliaria.R;
import com.example.tpinmobiliaria.databinding.FragmentContratoBinding;
import com.example.tpinmobiliaria.models.Inmueble;

import java.util.List;

public class ContratoFragment extends Fragment {

    private ContratoViewModel vm;
    private FragmentContratoBinding binding;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
    vm = new ViewModelProvider(this).get(ContratoViewModel.class);
    binding = FragmentContratoBinding.inflate(inflater, container, false);
    View root = binding.getRoot();

    vm.getListaInmuebles().observe(getViewLifecycleOwner(), new Observer<List<Inmueble>>() {
        @Override
        public void onChanged(List<Inmueble> inmuebles) {
            ContratoAdapter adapter = new ContratoAdapter(inmuebles,getContext(),getLayoutInflater());
            GridLayoutManager glm=new GridLayoutManager(getContext(),2,GridLayoutManager.VERTICAL,false);
            binding.rvListaInmueblesCContrato.setLayoutManager(glm);
            binding.rvListaInmueblesCContrato.setAdapter(adapter);
        }
    });
    vm.obtenerListaInmueblesCContrato();
    return root;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}