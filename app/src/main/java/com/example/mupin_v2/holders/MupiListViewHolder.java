package com.example.mupin_v2.holders;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mupin_v2.R;
import com.example.mupin_v2.models.Mupins;

public class MupiListViewHolder extends RecyclerView.ViewHolder {

    private TextView idMupiTextView, estacionTextView, ciudadTextView;

    public MupiListViewHolder(@NonNull View itemView) {
        super(itemView);
        idMupiTextView = itemView.findViewById(R.id.id_mupi_text_view);
        estacionTextView = itemView.findViewById(R.id.estacion_text_view);
        ciudadTextView = itemView.findViewById(R.id.ciudad_text_view);
    }

    public void setMupiList(Context context, Mupins mupins ){

        String idMupi = "ID: " + mupins.getIdMupi();
        idMupiTextView.setText(idMupi);

        String estacion = "Estación: " + mupins.getEstacion();
        estacionTextView.setText(estacion);

        String ciudad = "Ciudad: " + mupins.getCiudad();
        ciudadTextView.setText(ciudad);

    }
}
