package com.example.tpinmobiliaria.ui.login;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.tpinmobiliaria.MainActivity;
import com.example.tpinmobiliaria.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivityViewModel extends AndroidViewModel {
    private MutableLiveData<String> mensajeErr;
    public LoginActivityViewModel(@NonNull Application application) {
        super(application);
    }


    public LiveData<String> getMensajeErr(){
        if (mensajeErr==null){
            mensajeErr=new MutableLiveData<>();
        }
        return mensajeErr;
    }



    public void logueo(String usuario,String contrasenia){
        if (usuario.isEmpty() || contrasenia.isEmpty()){
            mensajeErr.setValue("Ambos campos deben estar completos.");
            return;
        }


        ApiClient.InmoServis  inmoServis = ApiClient.getInmoServis();
        Call<String> call = inmoServis.loginForm(usuario,contrasenia);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    Log.d("token", "REVISAR DATOS");
                    if (response.isSuccessful()) {
                        String token = response.body();
                        Log.d("token", token);
                        Log.d("token", "DATOS CORRECTOS");
                        ApiClient.guardarToken(getApplication(), token);
                        Log.d("token", "DATOS CORRECTOS 2");
                        Intent intent = new Intent(getApplication(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        getApplication().startActivity(intent);
                    } else {
                         mensajeErr.setValue("Usuario o Contraseña incorrectos");
                        Log.d("token", response.message());
                        Log.d("token", response.code() + "");
                        Log.d("token", response.errorBody() + "");
                    }
                }catch(Exception e){
                        Log.e("login", "EXCEPCION EN onResponse", e);
                    }
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                Log.d("token",throwable.getMessage());
                mensajeErr.setValue("Error: \n "+throwable.getMessage());
            }
        });
    }

}
