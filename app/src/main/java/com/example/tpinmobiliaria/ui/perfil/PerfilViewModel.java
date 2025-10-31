package com.example.tpinmobiliaria.ui.perfil;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tpinmobiliaria.models.Propietario;
import com.example.tpinmobiliaria.request.ApiClient;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilViewModel extends AndroidViewModel {

    private MutableLiveData<Boolean> mEstado = new MutableLiveData<>();
    private MutableLiveData<String> mTextBtn = new MutableLiveData<>();
    private MutableLiveData<Propietario> propietario = new MutableLiveData<>();

    private MutableLiveData<String> mensajeErr = new MutableLiveData<>();
    public PerfilViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Propietario> getPropietario() {
        return propietario;
    }

    public LiveData<String> getmTextBtn() {
        return mTextBtn;
    }

    public LiveData<Boolean> getmEstado() {
        return mEstado;
    }

    public LiveData<String> getMensajeErr(){
        return mensajeErr;
    }

    public  void cambiarBoton(String nombreBoton,String nombre, String apellido, String telefono, String dni, String email){
        if (nombreBoton.equalsIgnoreCase("Editar")){
            mEstado.setValue(true);
            mTextBtn.setValue("Guardar");
        }else {
            mEstado.setValue(false);
            mTextBtn.setValue("Editar");


            if (nombre.isEmpty() || apellido.isEmpty() || dni.isEmpty() || telefono.isEmpty() || email.isEmpty()) {
                mensajeErr.setValue("Todos los campos deben estar completos.");
                return;
            }


            try {
                Integer.parseInt(dni);
            } catch (NumberFormatException e) {
                mensajeErr.setValue("El DNI debe ser un número entero.");
                return;
            }


            Pattern pattern = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
            Matcher matcher = pattern.matcher(email);
            if (!matcher.matches()) {
                mensajeErr.setValue("El correo electrónico no tiene un formato válido.");
                return;
            }

            Propietario nuevoProp = new Propietario();
            nuevoProp.setIdPropietario(propietario.getValue().getIdPropietario());
            nuevoProp.setNombre(nombre);
            nuevoProp.setApellido(apellido);
            nuevoProp.setDni(dni);
            nuevoProp.setTelefono(telefono);
            nuevoProp.setEmail(propietario.getValue().getEmail());

            String token = ApiClient.leerToken(getApplication());
            Log.d("ActualizaPerfilVM","TOKEN: "+ token);
            ApiClient.InmoServis api = ApiClient.getInmoServis();
            Call<Propietario> call = api.ActualizarPropietario("Bearer " + token,nuevoProp);
            call.enqueue(new Callback<Propietario>() {
                @Override
                public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                    if (response.isSuccessful()){
                        Toast.makeText(getApplication(),"Datos Guardados",Toast.LENGTH_SHORT).show();
                        mensajeErr.setValue("");
                    }else {
                        Toast.makeText(getApplication(),"No se pudo acceder al EndPoint",Toast.LENGTH_SHORT).show();
                        mensajeErr.setValue("No se pudo acceder al EndPoint");
                    }
                }

                @Override
                public void onFailure(Call<Propietario> call, Throwable throwable) {
                    Log.d("errorPerfil",throwable.getMessage());
                }
            });
        }
    }

    public void obtenerPerfil(){
        String token = ApiClient.leerToken(getApplication());
        Log.d("PerfilVM","TOKEN: "+ token);
        ApiClient.InmoServis api = ApiClient.getInmoServis();
        Call<Propietario> call = api.getPropietario("Bearer "+ token);

        call.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                if (response.isSuccessful()){

                    propietario.postValue(response.body());
                    Log.d("PerfilVM","PROPIETARIO: "+ propietario.getValue());
                }else {
                    Log.d("PerfilVM","Error en Peticion de Perfil");
                }
            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable throwable) {

            }
        });
    }
}