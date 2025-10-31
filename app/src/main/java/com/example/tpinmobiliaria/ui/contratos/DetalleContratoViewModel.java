package com.example.tpinmobiliaria.ui.contratos;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tpinmobiliaria.models.Contrato;
import com.example.tpinmobiliaria.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleContratoViewModel extends AndroidViewModel {
    private MutableLiveData<Contrato> mContrato = new MutableLiveData<>();
    public DetalleContratoViewModel(@NonNull Application application) {
        super(application);
    }
    public LiveData<Contrato> getMContrato(){
        return mContrato;
    }
    public void recuperarContrato(Bundle bundle) {
    int idInmueble = bundle.getInt("idInmueble");
        Log.d("recuperarContrato", "idInmueble: " + idInmueble);
        if (idInmueble != 0){
            ApiClient.InmoServis api = ApiClient.getInmoServis();
            String token = ApiClient.leerToken(getApplication());
            Call<Contrato> call = api.getContratoById(idInmueble, "Bearer " + token);

            call.enqueue(new Callback<Contrato>() {
                @Override
                public void onResponse(Call<Contrato> call, Response<Contrato> response) {
                    if(response.isSuccessful()){
                        mContrato.setValue(response.body());
                    }else{
                        Log.d("recuperarContrato", "error en la respuesta: "+ response.code());
                    }
                }

                @Override
                public void onFailure(Call<Contrato> call, Throwable throwable) {
                    Log.d("recuperarContrato", "Error: " + throwable.getMessage());
                }
            });

        }

    }


}