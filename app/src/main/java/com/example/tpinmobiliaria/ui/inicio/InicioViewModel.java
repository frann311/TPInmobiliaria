package com.example.tpinmobiliaria.ui.inicio;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class InicioViewModel extends AndroidViewModel {

    private MutableLiveData<MapaActual> mutableMapaActual;
    public InicioViewModel(@NonNull Application application) {
        super(application);
    }
    public LiveData<MapaActual> getMutableMapaActual(){
        if (mutableMapaActual == null){
            mutableMapaActual = new MutableLiveData<>();

        }
        return mutableMapaActual;
    }
    public void cargarMapa(){
        MapaActual mapaActual = new MapaActual();
        mutableMapaActual.setValue(mapaActual);
    }
    public class MapaActual implements OnMapReadyCallback{
        LatLng BUENOS_AIRES = new LatLng(-34.603722, -58.381592); // Obelisco
        LatLng CORDOBA = new LatLng(-31.420083, -64.188776);      // Córdoba Capital
        LatLng MENDOZA = new LatLng(-32.889458, -68.845839);      // Mendoza Capital
        LatLng ROSARIO = new LatLng(-32.958702, -60.693041);      // Monumento a la Bandera
        LatLng BARILOCHE = new LatLng(-41.133472, -71.310278);

        @Override
        public void onMapReady(@NonNull GoogleMap googleMap) {
            googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            googleMap.addMarker(new MarkerOptions().position(BUENOS_AIRES).title("Obelisco, Buenos Aires"));
            googleMap.addMarker(new MarkerOptions().position(CORDOBA).title("Córdoba Capital"));
            googleMap.addMarker(new MarkerOptions().position(MENDOZA).title("Mendoza Capital"));
            googleMap.addMarker(new MarkerOptions().position(ROSARIO).title("Monumento a la Bandera, Rosario"));
            googleMap.addMarker(new MarkerOptions().position(BARILOCHE).title("Bariloche, Río Negro"));
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(BUENOS_AIRES)      // Sets the center of the map to Mountain View
                    .zoom(10)                   // Sets the zoom
                    .bearing(0)                // Sets the orientation of the camera to east
                    .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                    .build();                   // Creates a CameraPosition from the builder
            CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition( cameraPosition);
            googleMap.animateCamera(cameraUpdate);
        }
    }



}
