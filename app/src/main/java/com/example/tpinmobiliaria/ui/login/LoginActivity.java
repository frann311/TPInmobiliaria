package com.example.tpinmobiliaria.ui.login;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.tpinmobiliaria.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private LoginActivityViewModel vm;
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(LoginActivityViewModel.class);


        binding.btIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuario = binding.etMail.getText().toString();
                String clave = binding.etPassword.getText().toString();
                vm.logueo(usuario,clave);
            }
        });

        vm.getMensajeErr().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvMensaje.setText(s);
            }
        });


    }
}