package com.example.tpinmobiliaria.ui.logOut;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tpinmobiliaria.R;
import com.example.tpinmobiliaria.request.ApiClient;
import com.example.tpinmobiliaria.ui.login.LoginActivity;


public class LogOutFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        new AlertDialog.Builder(getActivity())
                .setTitle("Salir")
                .setMessage("¿Desea salir de la aplicación?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ApiClient.eliminarToken(getActivity());
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Navigation.findNavController((Activity)getActivity(), R.id.nav_host_fragment_content_main).navigate(R.id.nav_home);

                    }
                })
                .show();

        return inflater.inflate(R.layout.fragment_log_out, container, false);
    }
}