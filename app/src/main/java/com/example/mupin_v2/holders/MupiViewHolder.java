package com.example.mupin_v2.holders;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mupin_v2.R;
import com.example.mupin_v2.models.MupiModel;

public class MupiViewHolder extends RecyclerView.ViewHolder {

    private TextView idMupiTextView, estacionTextView, ciudadTextView, carasTextView, ubciacionTextView
            , limpiezaTextView, observacionesTextView, tipoMuebleTextView, mantenimientoTextView;

    public MupiViewHolder(@NonNull View itemView) {
        super(itemView);
        idMupiTextView = itemView.findViewById(R.id.id_mupi_text_view);
        estacionTextView = itemView.findViewById(R.id.estacion_text_view);
        ciudadTextView = itemView.findViewById(R.id.ciudad_text_view);
        carasTextView = itemView.findViewById(R.id.caras_text_view);
        ubciacionTextView = itemView.findViewById(R.id.ubicacion_text_view);
        limpiezaTextView = itemView.findViewById(R.id.limpieza_text_view);
        observacionesTextView = itemView.findViewById(R.id.observaciones_text_view);
        tipoMuebleTextView = itemView.findViewById(R.id.tipo_mueble_text_view);
        mantenimientoTextView = itemView.findViewById(R.id.mantenimiento_text_view);

    }

    public void setMupiList(Context context, MupiModel mupiModel){

        String idMupi = "ID: " + mupiModel.getIdMupi();
        idMupiTextView.setText(idMupi);

        String estacion = "Estación: " + mupiModel.getEstacion();
        estacionTextView.setText(estacion);

        String ciudad = "Ciudad: " + mupiModel.getCiudad();
        ciudadTextView.setText(ciudad);

        String caras = "Caras: " + mupiModel.getCaras();
        carasTextView.setText(caras);

        String ubicacion = "Ubicación: " + mupiModel.getUbicacion();
        ubciacionTextView.setText(ubicacion);

        String limpieza = "Limpieza: " + mupiModel.getLimpieza();
        limpiezaTextView.setText(limpieza);

        String observaciones = "Observaciones: " + mupiModel.getObservaciones();
        observacionesTextView.setText(observaciones);

        String tipoMueble = "Tipo de Mueble: " + mupiModel.getTipoMueble();
        tipoMuebleTextView.setText(tipoMueble);

        String mantenimiento = "Mantenimiento: " + mupiModel.getMantenimiento();
        mantenimientoTextView.setText(mantenimiento);
    }
}
