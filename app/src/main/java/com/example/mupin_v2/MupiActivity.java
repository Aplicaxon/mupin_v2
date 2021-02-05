package com.example.mupin_v2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mupin_v2.models.MupiModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MupiActivity extends AppCompatActivity {

    private FirebaseFirestore rootRef;
    DocumentReference mupiBuscado;
    MupiModel mupiEncontrado;
    MupiModel mupinImprmir;

    private TextView idMupiTextView, estacionTextView, ciudadTextView, carasTextView, ubciacionTextView
            , limpiezaTextView, observacionesTextView, tipoMuebleTextView, mantenimientoTextView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mupi);
        //  initListener();


        idMupiTextView = findViewById(R.id.id_mupi_text_view);
        estacionTextView = findViewById(R.id.estacion_text_view);
        ciudadTextView = findViewById(R.id.ciudad_text_view);
        carasTextView = findViewById(R.id.caras_text_view);
        ubciacionTextView = findViewById(R.id.ubicacion_text_view);
        limpiezaTextView = findViewById(R.id.limpieza_text_view);
        observacionesTextView = findViewById(R.id.observaciones_text_view);
        tipoMuebleTextView = findViewById(R.id.tipo_mueble_text_view);
        mantenimientoTextView = findViewById(R.id.mantenimiento_text_view);


/*
        btnMostrarMupis = findViewById(R.id.btnMostrarMupi);
        btnMostrarMupis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MupiActivity.class ));
            }
        });*/


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(MupiActivity.this);
            builder.setTitle("Buscar un Mupi");

            EditText editText = new EditText(MupiActivity.this);
            editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
            editText.setHint("Escriba el ID del Mupi");
            editText.setHintTextColor(Color.GRAY);
            builder.setView(editText);

            builder.setPositiveButton("Buscar", (dialog, i) -> {
                String mupi = editText.getText().toString().trim();

                  mupinImprmir = buscarMupin(mupi);
               // startActivity(new Intent(MainActivity.this, MupiActivity.class ));

                String idMupi = "ID: " +  mupinImprmir.getIdMupi();
                String estacion = mupinImprmir.getEstacion();
                idMupiTextView.setText(idMupi);
                estacionTextView.setText(estacion);

            });



            builder.setNegativeButton("Cancelar", (dialogInterface, i) -> dialogInterface.dismiss());

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });










    }



        private MupiModel buscarMupin(String idMupi) {

            mupiBuscado = rootRef.collection("mupis").document(idMupi);
            mupiBuscado.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    mupiEncontrado = documentSnapshot.toObject(MupiModel.class);
                }

            });
            return mupiEncontrado;

            //String idmupibuscado = mupiBuscado.getId();
            //return mupiBuscado;
    }


  /*  private  FirestoreRecyclerAdapter<MupiModel, MupiViewHolder> firestoreRecyclerAdapter;
    RecyclerView recyclerViewMupis;
    MupisAdapter mupisAdapter;
    FirebaseFirestore miFirebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mupi);

        recyclerViewMupis = findViewById(R.id.recyclerMupi);
        recyclerViewMupis.setLayoutManager(new LinearLayoutManager(this));
        ProgressBar progressBar = findViewById(R.id.progress_bar);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        TextView emptyView = findViewById(R.id.empty_view);


        Query query = miFirebaseFirestore.collection("mupis").orderBy("idMupi");

        FirestoreRecyclerOptions<MupiModel> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<MupiModel>()
                .setQuery(query, MupiModel.class)
                .build();

        firestoreRecyclerAdapter =
                new FirestoreRecyclerAdapter<MupiModel, MupiViewHolder>(firestoreRecyclerOptions) {
                    @Override
                    protected void onBindViewHolder(@NonNull MupiViewHolder mupiListViewHolder, int i, @NonNull MupiModel mupins) {
                        mupiListViewHolder.setMupiList(getApplicationContext(), mupins);

                    }

                    @NonNull
                    @Override
                    public MupiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mupi, parent, false);
                        return new MupiViewHolder(view);
                    }

                    @Override
                    public void onDataChanged() {
                        if (progressBar != null) {
                            progressBar.setVisibility(View.GONE);
                        }

                        if (getItemCount() == 0) {
                            recyclerView.setVisibility(View.GONE);
                            emptyView.setVisibility(View.VISIBLE);
                        } else {
                            recyclerView.setVisibility(View.VISIBLE);
                            emptyView.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public int getItemCount() { return super.getItemCount();}
                };

        recyclerView.setAdapter(firestoreRecyclerAdapter);










/*
        Query query = miFirebaseFirestore.collection("mupis");

        FirestoreRecyclerOptions<MupiModel> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<MupiModel>()
                .setQuery(query,MupiModel.class).build();
        mupisAdapter = new MupisAdapter(firestoreRecyclerOptions);
        mupisAdapter.notifyDataSetChanged();
        recyclerViewMupis.setAdapter(mupisAdapter);
*/
/*
    }

    @Override
    protected void onStart() {
        super.onStart();
        mupisAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mupisAdapter.stopListening();
    }
    */
}