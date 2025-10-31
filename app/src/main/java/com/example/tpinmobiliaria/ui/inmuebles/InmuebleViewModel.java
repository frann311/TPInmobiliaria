package com.example.tpinmobiliaria.ui.inmuebles;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;

import com.example.tpinmobiliaria.R;
import com.example.tpinmobiliaria.models.Inmueble;
import com.example.tpinmobiliaria.models.Propietario;
import com.example.tpinmobiliaria.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InmuebleViewModel extends AndroidViewModel {

    private MutableLiveData<List<Inmueble>> listaInmuebles = new MutableLiveData<>();
    public InmuebleViewModel(@NonNull Application application) {
        super(application);
    }
    public LiveData<List<Inmueble>> getListaInmuebles(){
        return listaInmuebles;
    }

    public void obtenerListaInmuebles(){
        String token = ApiClient.leerToken(getApplication());
        ApiClient.InmoServis api = ApiClient.getInmoServis();
        Call <List<Inmueble>> call = api.getInmueble("Bearer "+ token);

        call.enqueue(new Callback<List<Inmueble>>() {
            @Override
            public void onResponse(Call<List<Inmueble>> call, Response<List<Inmueble>> response) {
                if (response.isSuccessful()){
                    listaInmuebles.postValue(response.body());
                }else {
                    Toast.makeText(getApplication(),"no se obtuvieron Inmuebles",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Inmueble>> call, Throwable throwable) {
                Log.d("errorInmueble",throwable.getMessage());
                Toast.makeText(getApplication(),"Error al obtener Inmuebles",Toast.LENGTH_LONG).show();
            }
        });
    }





}