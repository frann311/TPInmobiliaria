package com.example.tpinmobiliaria.ui.Pagos;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tpinmobiliaria.R;
import com.example.tpinmobiliaria.databinding.FragmentPagoBinding;
import com.example.tpinmobiliaria.models.Pago;

import java.util.List;

public class PagoFragment extends Fragment {

    private PagoViewModel vm;
    private FragmentPagoBinding binding;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        vm = new ViewModelProvider(this).get(PagoViewModel.class);
        binding = FragmentPagoBinding.inflate(inflater, container, false);
        vm.getListaPagos().observe(getViewLifecycleOwner(), new Observer<List<Pago>>() {
            @Override
            public void onChanged(List<Pago> pagos) {
                PagoAdapter adapter = new PagoAdapter(pagos,getContext(),getLayoutInflater());
                GridLayoutManager glm=new GridLayoutManager(getContext(),1,GridLayoutManager.VERTICAL,false);
                binding.rvListaPagos.setLayoutManager(glm);
                binding.rvListaPagos.setAdapter(adapter);

            }
        });
        vm.recuperarListaPagos(getArguments());

        return binding.getRoot();
    }



}