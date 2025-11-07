package com.example.tpinmobiliaria.ui.inmuebles;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Instrumentation;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tpinmobiliaria.R;
import com.example.tpinmobiliaria.databinding.FragmentAgregarInmuebleBinding;
import com.example.tpinmobiliaria.models.Inmueble;

public class AgregarInmueble extends Fragment {

    private AgregarInmuebleViewModel vm;
    private FragmentAgregarInmuebleBinding binding;
    private Intent intent;
    private ActivityResultLauncher<Intent> arl;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        vm = new ViewModelProvider(this).get(AgregarInmuebleViewModel.class);
        binding = FragmentAgregarInmuebleBinding.inflate(inflater, container, false);

        // Inicializar la galería
        abrirGaleria();

        vm.getMensajeErr().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvMensajeErr.setText(s);
            }
        });

       binding.btBuscarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arl.launch(intent);
            }
        });


        binding.btGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String direccion = binding.etDireccion.getText().toString();
                String uso = binding.etUso.getText().toString();
                String tipo = binding.etTipo.getText().toString();
                String ambientes = binding.etAmbientes.getText().toString();
                String superficie = binding.etSuperficie.getText().toString();
                String valor = binding.etValor.getText().toString();
                String latitudStr = binding.etLatitud.getText().toString();
                String longitudStr = binding.etLongitud.getText().toString();
                boolean disponible = binding.cbDisponible.isChecked();
                vm.agregarInmueble(direccion, uso, tipo, ambientes, superficie, valor,
                        disponible, latitudStr, longitudStr);
            }

        });

        vm.getUriMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Uri>() {
            @Override
            public void onChanged(Uri uri) {
                binding.ivPortada.setImageURI(uri);
            }
        });



        vm.getmInmuebleAgregado().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean ok) {
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main).navigate(R.id.nav_inmuebles);
            }
        });

        vm.getCargando().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                
            }
        });
        return binding.getRoot();

    }

    private void abrirGaleria() {
        intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        arl=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                vm.recibirFoto(result);
            }
        });
    }
}