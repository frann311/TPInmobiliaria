package com.example.tpinmobiliaria.ui.contratos;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tpinmobiliaria.databinding.FragmentContratoBinding;
import com.example.tpinmobiliaria.models.Inmueble;
import com.example.tpinmobiliaria.request.ApiClient;

import java.util.List;

public class ContratoViewModel extends AndroidViewModel {

    private MutableLiveData<List<Inmueble>> listaInmuebles = new MutableLiveData<>();
    public ContratoViewModel(@NonNull Application application) {
        super(application);
    }
    public LiveData<List<Inmueble>> getListaInmuebles(){
        return listaInmuebles;
    }
    public void obtenerListaInmueblesCContrato(){
        String token = ApiClient.leerToken(getApplication());
        ApiClient.InmoServis api = ApiClient.getInmoServis();
        Call<List<Inmueble>> call = api.getInmuebleCContrato("Bearer "+ token);

        call.enqueue(new Callback<List<Inmueble>>() {
            @Override
            public void onResponse(Call<List<Inmueble>> call, Response<List<Inmueble>> response) {
                if (response.isSuccessful()){
                    listaInmuebles.postValue(response.body());
                }else {
                    Toast.makeText(getApplication(),"no se obtuvieron Inmuebles con contrato",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Inmueble>> call, Throwable throwable) {
                Log.d("errorInmuebleCContrato",throwable.getMessage());
                Toast.makeText(getApplication(),"Error al obtener Inmuebles con contrato",Toast.LENGTH_LONG).show();
            }
        });
    }

}