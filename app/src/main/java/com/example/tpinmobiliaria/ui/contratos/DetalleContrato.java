package com.example.tpinmobiliaria.ui.contratos;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tpinmobiliaria.R;
import com.example.tpinmobiliaria.databinding.FragmentDetalleContratoBinding;
import com.example.tpinmobiliaria.models.Contrato;

public class DetalleContrato extends Fragment {

    private DetalleContratoViewModel vm;
    private FragmentDetalleContratoBinding binding;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
    vm = new ViewModelProvider(this).get(DetalleContratoViewModel.class);
    binding = FragmentDetalleContratoBinding.inflate(inflater, container, false);
    vm.getMContrato().observe(getViewLifecycleOwner(), new Observer<Contrato>() {
        @Override
        public void onChanged(Contrato contrato) {
            binding.tvFechaInicio.setText(contrato.getFechaInicio());
            binding.tvFechaFinalizacion.setText(contrato.getFechaFinalizacion());
            binding.tvMonto.setText(String.valueOf(contrato.getMontoAlquiler()));
            binding.tvInquilino.setText(contrato.getInquilino().getNombre());
            binding.tvInmueble.setText(contrato.getInmueble().getDireccion());

            binding.btPagos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("idContrato",contrato.getIdContrato()
                    );
                    Log.d("recuperarListaPagos", "idContratoEnvio: " + contrato.getIdContrato());
                    Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main).navigate(R.id.pagoFragment,bundle);
                }
            });
            binding.btInquilino.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("inquilino", contrato.getInquilino());
                    Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main).navigate(R.id.nav_inquilino,bundle);
                }
            });
        }
    });


    vm.recuperarContrato(getArguments());

    return binding.getRoot();
    }



}