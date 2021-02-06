package com.example.mupin_v2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mupin_v2.models.MupiModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class MupisAdapter extends FirestoreRecyclerAdapter<MupiModel, MupisAdapter.ViewHolder> {

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MupisAdapter(@NonNull FirestoreRecyclerOptions<MupiModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull MupiModel mupiModel) {

        holder.observaciones_text_view.setText(mupiModel.getObservaciones());
        holder.caras_text_view.setText(mupiModel.getCaras());
        holder.ciudad_text_view.setText(mupiModel.getCiudad());
        holder.tipo_mueble_text_view.setText(mupiModel.getTipoMueble());
        holder.limpieza_text_view.setText(mupiModel.getLimpieza());
        holder.mantenimiento_text_view.setText(mupiModel.getMantenimiento());
        holder.estacion_text_view.setText(mupiModel.getEstacion());
        holder.ubicacion_text_view.setText(mupiModel.getUbicacion());
        holder.id_text_view.setText(mupiModel.getIdMupi());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_publicidad, parent, false );
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView observaciones_text_view;
        TextView caras_text_view;
        TextView ciudad_text_view;
        TextView tipo_mueble_text_view;
        TextView limpieza_text_view;
        TextView mantenimiento_text_view;
        TextView estacion_text_view;
        TextView ubicacion_text_view;
        TextView id_text_view;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            observaciones_text_view = itemView.findViewById(R.id.observaciones_text_view);
            caras_text_view = itemView.findViewById(R.id.caras_text_view);
            ciudad_text_view = itemView.findViewById(R.id.ciudad_text_view);
            tipo_mueble_text_view = itemView.findViewById(R.id.tipo_mueble_text_view);
            limpieza_text_view = itemView.findViewById(R.id.limpieza_text_view);
            mantenimiento_text_view = itemView.findViewById(R.id.mantenimiento_text_view);
            estacion_text_view = itemView.findViewById(R.id.estacion_text_view);
            ubicacion_text_view = itemView.findViewById(R.id.ubicacion_text_view);
            id_text_view = itemView.findViewById(R.id.id_text_view);

        }
    }

}
