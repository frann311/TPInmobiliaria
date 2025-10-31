package com.example.tpinmobiliaria.ui.inquilinos;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tpinmobiliaria.models.Inquilino;

public class InquilinoViewModel extends AndroidViewModel {

    private MutableLiveData<Inquilino> mInquilino = new MutableLiveData<>();

    public InquilinoViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Inquilino> getmInquilino() {
        return mInquilino;
    }

    public void recuperarInquilino(Bundle bundle){
        Inquilino inquilino = (Inquilino) bundle.getSerializable("inquilino");
        if (inquilino == null) {
            Log.d("recuperarInquilino", "Inquilino nulo");
            return;
        }
        mInquilino.setValue(inquilino);



    }

}