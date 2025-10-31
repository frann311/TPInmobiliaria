package com.example.tpinmobiliaria.ui.inmuebles;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tpinmobiliaria.models.Inmueble;
import com.example.tpinmobiliaria.request.ApiClient;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgregarInmuebleViewModel extends AndroidViewModel {
    private MutableLiveData<String> mensajeErr = new MutableLiveData<>();
    private MutableLiveData<Boolean> mInmuebleAgregado = new MutableLiveData<>();

    private MutableLiveData<Uri> uriMutableLiveData = new MutableLiveData<>();

    public AgregarInmuebleViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<String> getMensajeErr() {
        return mensajeErr;
    }

    public LiveData<Boolean> getmInmuebleAgregado() {
        return mInmuebleAgregado;
    }


    public LiveData<Uri> getUriMutableLiveData() {
        return uriMutableLiveData;
    }

    public void agregarInmueble(String direccion, String uso, String tipo, String ambientes, String superficie, String valor, boolean disponible, String latitud, String longitud) {
        if (direccion.isEmpty() || uso.isEmpty() || tipo.isEmpty() ||
                ambientes.isEmpty() || superficie.isEmpty() || valor.isEmpty()) {
            mensajeErr.setValue("Todos los campos son obligatorios");
            Log.d("agregarInmueble", "Todos los campos son obligatorios");
            return;
        }

        // Validar que los campos numéricos sean números válidos
        try {
            int ambientesInt = Integer.parseInt(ambientes);
            if (ambientesInt <= 0) {
                mensajeErr.setValue("Los ambientes deben ser mayor a 0");
                Log.d("agregarInmueble", "Los ambientes deben ser mayor a 0");
                return;
            }
        } catch (NumberFormatException e) {
            mensajeErr.setValue("Ambientes debe ser un número válido");
            Log.d("agregarInmueble", "Ambientes debe ser un número válido");
            return;
        }

        try {
            int superficieInt = Integer.parseInt(superficie);
            if (superficieInt <= 0) {
                mensajeErr.setValue("La superficie debe ser mayor a 0");
                Log.d("agregarInmueble", "La superficie debe ser mayor a 0");
                return;
            }
        } catch (NumberFormatException e) {
            mensajeErr.setValue("Superficie debe ser un número válido");
            Log.d("agregarInmueble", "Superficie debe ser un número válido");
            return;
        }

        try {
            double valorDouble = Double.parseDouble(valor);
            if (valorDouble <= 0) {
                mensajeErr.setValue("El valor debe ser mayor a 0");
                Log.d("agregarInmueble", "El valor debe ser mayor a 0");
                return;
            }
        } catch (NumberFormatException e) {
            mensajeErr.setValue("Valor debe ser un número válido");
            Log.d("agregarInmueble", "Valor debe ser un número válido");
            return;
        }

        // Latitud y longitud son opcionales, pero si se ingresan deben ser números válidos
        double latitudDouble = 0;
        double longitudDouble = 0;

        if (!latitud.isEmpty()) {
            try {
                latitudDouble = Double.parseDouble(latitud);
            } catch (NumberFormatException e) {
                mensajeErr.setValue("Latitud debe ser un número válido");
                Log.d("agregarInmueble", "Latitud debe ser un número válido");
                return;
            }
        }

        if (!longitud.isEmpty()) {
            try {
                longitudDouble = Double.parseDouble(longitud);
            } catch (NumberFormatException e) {
                mensajeErr.setValue("Longitud debe ser un número válido");
                Log.d("agregarInmueble", "Longitud debe ser un número válido");
                return;
            }
        }
        Log.d("agregarInmueble", "Creando inmueble datos correctos");

        Inmueble inmueble = new Inmueble();
        inmueble.setDireccion(direccion.trim());
        inmueble.setUso(uso.trim());
        inmueble.setTipo(tipo.trim());
        inmueble.setAmbientes(Integer.parseInt(ambientes));
        inmueble.setSuperficie(Integer.parseInt(superficie));
        inmueble.setValor(Double.parseDouble(valor));
        inmueble.setDisponible(disponible);
        inmueble.setLatitud(latitudDouble);
        inmueble.setLongitud(longitudDouble);

        // convertir la imagen en bits
        Log.d("agregarInmueble", "convertir la imagen en bits");
        byte[] imagen = transImagen();
        if (imagen.length == 0){

            mensajeErr.setValue("Debe seleccionar una imagen");
            return;
        }
        String inmuebleJson = new Gson().toJson(inmueble);
        RequestBody inmuebleBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), inmuebleJson);
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), imagen);
        MultipartBody.Part imagenPart = MultipartBody.Part.createFormData("imagen", "imagen.jpg", requestFile);
        Log.d("agregarInmueble", "imagenPart: " + imagenPart);
        //LLAMA AL API
        Log.d("agregarInmueble", "LLAMA AL API");
        ApiClient.InmoServis api = ApiClient.getInmoServis();
        String token = ApiClient.leerToken(getApplication());
        Call<Inmueble> call = api.CargarInmueble("Bearer " + token,imagenPart, inmuebleBody);
        call.enqueue(new Callback<Inmueble>() {
            @Override
            public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                if (response.isSuccessful()){
                    Toast.makeText(getApplication(), "Inmueble guardado correctamente", Toast.LENGTH_LONG).show();
                    Log.d("agregarInmueble", "AGREGADO CORRECATAMENTE");
                    mInmuebleAgregado.setValue(true);

                }else {
                    Toast.makeText(getApplication(), "Error al guardar el inmueble", Toast.LENGTH_LONG).show();
                    Log.d("agregarInmueble", "ERROR AL AGREGAR");
                }
            }

            @Override
            public void onFailure(Call<Inmueble> call, Throwable throwable) {
                Toast.makeText(getApplication(), "Error grave al guardar el inmueble", Toast.LENGTH_LONG).show();
                Log.d("agregarInmueble", "ERROR GRAVE AL AGREGAR");
            }
        });
    }

    private byte[] transImagen() {

        try {
            Uri uri = uriMutableLiveData.getValue();
            if (uri == null) {
                Log.e("agregarInmueble", "URI es null - No se seleccionó imagen");

                return new byte[]{};
            }
            InputStream inputStream = getApplication().getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (FileNotFoundException ex) {
            Toast.makeText(getApplication(), "Error al cargar la imagen", Toast.LENGTH_LONG).show();
            return new byte[]{};
        }


    }

    public void recibirFoto(ActivityResult result) {
        if (result.getResultCode() == Activity.RESULT_OK) {
            Intent data = result.getData();
            if (data != null) {
                Uri uri = data.getData();
                Log.d("recibirFoto", uri.toString());
                uriMutableLiveData.setValue(uri);
            }
        }
    }
}

