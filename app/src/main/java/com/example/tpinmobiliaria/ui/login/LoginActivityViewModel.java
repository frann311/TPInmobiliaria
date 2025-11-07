package com.example.tpinmobiliaria.ui.login;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.tpinmobiliaria.MainActivity;
import com.example.tpinmobiliaria.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivityViewModel extends AndroidViewModel {

    private static final float LIMITE_ACELERACION = 100f;
    private final String numero = "2664319160";
    private MutableLiveData<String> mensajeErr;
    private MutableLiveData<Boolean> mshake = new MutableLiveData<>();
    private SensorManager manager;
    private List<Sensor> sensores;
    private ManejaEventos maneja;


    public LoginActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Boolean> getmshake(){
        return mshake;
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


    public void escuchaShake(){
         manager = (SensorManager) getApplication().getSystemService(Context.SENSOR_SERVICE);
        sensores = manager.getSensorList(Sensor.TYPE_ACCELEROMETER);
        if(sensores.size()>0){
            maneja = new ManejaEventos();
            Log.d("ESCUCHA", "Registrando listener en: " + sensores.get(0).getName());
            manager.registerListener(maneja, sensores.get(0), SensorManager.SENSOR_DELAY_GAME);
        } else {
            Log.d("ESCUCHA", "No hay sensores disponibles");
        }
    }
    public void desactivaEscucha(){
        manager.unregisterListener(maneja);
    }



    private class ManejaEventos implements SensorEventListener{

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            float aceleracion = (float) Math.sqrt(x*x + y*y + z*z);
            if (aceleracion > LIMITE_ACELERACION) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + numero));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplication().startActivity(intent);
            }


        }
    }
}
