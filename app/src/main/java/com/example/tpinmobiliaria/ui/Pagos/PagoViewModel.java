package com.example.tpinmobiliaria.ui.Pagos;

import android.app.Application;
import android.os.Bundle;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tpinmobiliaria.models.Pago;
import com.example.tpinmobiliaria.request.ApiClient;

import java.util.List;

public class PagoViewModel extends AndroidViewModel {

    private MutableLiveData<List<Pago>> listaPagos = new MutableLiveData<>();
    public PagoViewModel(@NonNull Application application) {
        super(application);
    }
    public MutableLiveData<List<Pago>> getListaPagos() {
        return listaPagos;
    }

    public void recuperarListaPagos(Bundle bundle){
        int idContrato = bundle.getInt("idContrato");
        Log.d("recuperarListaPagos", "idContrato: " + idContrato);
        if (idContrato != 0){
            String token = ApiClient.leerToken(getApplication());
            ApiClient.InmoServis api = ApiClient.getInmoServis();
            Call<List<Pago>> call = api.getPagosByContrato(idContrato, "Bearer "+ token);

            call.enqueue(new Callback<List<Pago>>() {
                @Override
                public void onResponse(Call<List<Pago>> call, Response<List<Pago>> response) {
                    if(response.isSuccessful()){
                        listaPagos.setValue(response.body());
                    }else{
                        Log.d("recuperarListaPagos", "error en la respuesta: "+ response.code());
                    }
                }

                @Override
                public void onFailure(Call<List<Pago>> call, Throwable throwable) {
                        Log.d("recuperarListaPagos", "Error: " + throwable.getMessage());
                }
            });
        }



    }

}