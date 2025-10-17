package com.example.tpinmobiliaria.request;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.tpinmobiliaria.models.Inmueble;
import com.example.tpinmobiliaria.models.Propietario;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public class ApiClient {
    private static String UR_BASE="https://inmobiliariaulp-amb5hwfqaraweyga.canadacentral-01.azurewebsites.net/";


    public static void guardarToken(Context context, String token) {
        SharedPreferences sp = context.getSharedPreferences("token.xml", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("token", token);
        editor.apply();
    }

    public static String leerToken(Context context) {
        SharedPreferences sp = context.getSharedPreferences("token.xml", Context.MODE_PRIVATE);
        return sp.getString("token", null);
    }





    public static InmoServis getInmoServis(){
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UR_BASE)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(InmoServis.class);
    }
    public interface  InmoServis{


        @FormUrlEncoded
        @POST("api/propietarios/login")
        Call<String> loginForm(@Field("Usuario") String usuario, @Field("Clave") String clave);

        @GET("api/propietarios")
        Call<Propietario> getPropietario(@Header("Authorization") String token);

        @PUT("api/Propietarios/actualizar")
        Call<Propietario> ActualizarPropietario(@Header("Authorization") String token, @Body Propietario p);

        @GET("api/inmuebles")
        Call<List<Inmueble>> getInmueble(@Header("Authorization") String token);

        @PUT("api/Inmuebles/actualizar")
        Call<Inmueble> ActualizarInmueble(@Header("Authorization") String token, @Body Inmueble i);
    }

}
