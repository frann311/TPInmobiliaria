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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
                        List<Pago> pagos = response.body();

                        for (Pago p : pagos) {
                            if (p.getFechaPago() != null) {
                                p.setFechaPago(formatearFecha(p.getFechaPago()));
                            }
                        }

                        listaPagos.setValue(pagos);
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
    private String formatearFecha(String fechaOriginal) {
        if (fechaOriginal == null || fechaOriginal.isEmpty()) return fechaOriginal;

        try {
            SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            SimpleDateFormat nuevoFormat = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());


            Date date = originalFormat.parse(fechaOriginal);
            Log.d("formatearFecha", "fechaOriginal: " + fechaOriginal);
            Log.d("formatearFecha", "date: " + nuevoFormat.format(date));

            return nuevoFormat.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
            Log.d("formatearFecha", "ERROR AL FORMATEAR FECHA");
            return fechaOriginal; // Si falla, devuelve tal cual
        }
    }

}