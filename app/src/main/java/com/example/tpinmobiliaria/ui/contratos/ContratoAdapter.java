package com.example.tpinmobiliaria.ui.contratos;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tpinmobiliaria.R;
import com.example.tpinmobiliaria.models.Inmueble;

import java.util.List;

public class ContratoAdapter extends RecyclerView.Adapter<ContratoAdapter.ViewHolderInmuebleCContrato> {
    private List<Inmueble> listaInmueblesCContratos;
    private Context context;
    private LayoutInflater inflater;

    public ContratoAdapter(List<Inmueble> listaInmueblesCContratos, Context context, LayoutInflater inflater) {
        this.listaInmueblesCContratos = listaInmueblesCContratos;
        this.context = context;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public ViewHolderInmuebleCContrato onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= inflater.inflate(R.layout.iteminmuebleccontrato,parent,false);
        return new ViewHolderInmuebleCContrato(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderInmuebleCContrato holder, int position) {
        Inmueble inmActual = listaInmueblesCContratos.get(position);
        holder.direccion.setText(inmActual.getDireccion());
        Glide.with(context)
                .load("https://inmobiliariaulp-amb5hwfqaraweyga.canadacentral-01.azurewebsites.net/" + inmActual.getImagen())
                .placeholder(null)
                .error("null")
                .into(holder.portada);
        holder.btContrato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("idInmueble", inmActual.getIdInmueble());
                Navigation.findNavController((Activity)context, R.id.nav_host_fragment_content_main).navigate(R.id.detalleContrato,bundle);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listaInmueblesCContratos.size();
    }

    public class ViewHolderInmuebleCContrato extends RecyclerView.ViewHolder {

        private TextView direccion;
        private ImageView portada;
        private Button btContrato;

        public ViewHolderInmuebleCContrato(@NonNull View itemView) {
            super(itemView);
            direccion = itemView.findViewById(R.id.tvDirecciondetalle);
            portada= itemView.findViewById(R.id.imgPortadadetalle);
            btContrato = itemView.findViewById(R.id.btContrato);
        }
    }
}
