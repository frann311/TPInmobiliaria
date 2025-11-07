package com.example.tpinmobiliaria.ui.Pagos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tpinmobiliaria.R;
import com.example.tpinmobiliaria.models.Pago;
import com.example.tpinmobiliaria.ui.inmuebles.InmuebleAdapter;

import java.util.List;

public class PagoAdapter  extends RecyclerView.Adapter<PagoAdapter.ViewHolderPago> {
    private List<Pago> listPagos;
    private Context context;
    private LayoutInflater inflater;

    public PagoAdapter(List<Pago> listPagos, Context context, LayoutInflater inflater) {
        this.listPagos = listPagos;
        this.context = context;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public ViewHolderPago onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=inflater.inflate(R.layout.itempago,parent,false);
        return new PagoAdapter.ViewHolderPago(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderPago holder, int position) {
    Pago pagoActual = listPagos.get(position);
    holder.fecha.setText(pagoActual.getFechaPago());
    holder.monto.setText(pagoActual.getMonto()+"");
    holder.detalle.setText(pagoActual.getDetalle());
    // holder.numPago.setText(listPagos.size()+"");
    holder.estado.setChecked(pagoActual.isEstado());
    }

    @Override
    public int getItemCount() {
        return listPagos.size();
    }

    public class ViewHolderPago extends RecyclerView.ViewHolder  {

        private TextView fecha, monto, numPago, detalle;
        private CheckBox estado;
        public ViewHolderPago(@NonNull View itemView) {
            super(itemView);
            fecha = itemView.findViewById(R.id.tvFechaInicio);
            monto = itemView.findViewById(R.id.tvMonto);

            detalle = itemView.findViewById(R.id.tvDetalle);
            estado = itemView.findViewById(R.id.cbEstado);
        }
    }


}
