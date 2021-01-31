package com.example.mupin_v2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.example.mupin_v2.holders.MupiListViewHolder;
import com.example.mupin_v2.models.Mupins;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Transaction;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Activity_Query";
    private String userEmail, userName;
    GoogleApiClient googleApiClient;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore rootRef;
    private FirebaseAuth.AuthStateListener authStateListener;
    private CollectionReference userMupinListRef;
    private CollectionReference mupisListRef;
    private  FirestoreRecyclerAdapter<Mupins, MupiListViewHolder> firestoreRecyclerAdapter;
    private Context context;
    Button btnMostrarMupis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (googleSignInAccount != null){
             userEmail = googleSignInAccount.getEmail();
             userName = googleSignInAccount.getDisplayName();
            Toast.makeText(this,"Bienvenido " + userName, Toast.LENGTH_LONG).show();

        }

        googleApiClient = new  GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .build();

        firebaseAuth = FirebaseAuth.getInstance();
        rootRef = FirebaseFirestore.getInstance();

        authStateListener = firebaseAuth -> {
            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
            if (firebaseUser == null) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        };
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
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Buscar un Mupi");

            EditText editText = new EditText(MainActivity.this);
            editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
            editText.setHint("Escriba el ID del Mupi");
            editText.setHintTextColor(Color.GRAY);
            builder.setView(editText);

            builder.setPositiveButton("Buscar", (dialog, i) -> {
                String mupi = editText.getText().toString().trim();
                //int mupiNumero = Integer.parseInt(mupi);
            //  buscarMupin();
            });

            builder.setNegativeButton("Cancelar", (dialogInterface, i) -> dialogInterface.dismiss());

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });

            //mupisListRef = rootRef.collection("mupis").document(userEmail).collection("users");




        ListenerRegistration listener = rootRef.collection("mupis")
                .addSnapshotListener(new EventListener<QuerySnapshot>(){
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e){
                        if(e != null){
                            return;
                        }
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots){
                            Object listaRecibida = document.get("mupis");
                            ArrayList<String> lista = (ArrayList<String>) listaRecibida;
                        }
                    }
                });



        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        TextView emptyView = findViewById(R.id.empty_view);
        ProgressBar progressBar = findViewById(R.id.progress_bar);

       Query query = rootRef.collection("mupis").orderBy("idMupi");






       FirestoreRecyclerOptions<Mupins> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Mupins>()
              .setQuery(query, Mupins.class)
              .build();

        firestoreRecyclerAdapter =
                new FirestoreRecyclerAdapter<Mupins, MupiListViewHolder>(firestoreRecyclerOptions) {
                    @Override
                    protected void onBindViewHolder(@NonNull MupiListViewHolder mupiListViewHolder, int i, @NonNull Mupins mupins) {
                        mupiListViewHolder.setMupiList(getApplicationContext(), mupins);

                    }

                    @NonNull
                    @Override
                    public MupiListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mupi, parent, false);
                        return new MupiListViewHolder(view);
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
    }


    private void signOut(){

        Map<String, Object> map = new HashMap<>();
        map.put("tokenId", FieldValue.delete());

        rootRef.collection("users").document(userEmail).update(map)
                .addOnSuccessListener(aVoid -> {
                  firebaseAuth.signOut();

                  if(googleApiClient.isConnected()){
                      Auth.GoogleSignInApi.signOut(googleApiClient);
                  }
                }
                );
    }


    public static List<String> querySnapshotToIds(QuerySnapshot querySnapshot) {
        List<String> res = new ArrayList<>();
        for (DocumentSnapshot doc : querySnapshot) {
            res.add(doc.getId());
        }
        return res;
    }


    public void obtenerDocumentos() {

        rootRef.collection("mupis").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private void buscarMupin( ) {

        String idMupi = "000273Y";
        int caras = 2;
        String tipoMueble = "Mupi pared";
        String estacion = "Alcalá de Henares" ;
        String ubicacion = "Exterior Estación";
        String ciudad = "Madrid";
        String limpieza = "Ext.x1,7.07+Int.";
        String observaciones = "Cristal estropeado por ácido - Cristal arañado";
        String mantenimiento= "";


        //String id = userMupinListRef.document().getId();
        //Mupins mupins = new Mupins(idMupi, caras, tipoMueble, estacion, ubicacion, ciudad, limpieza, observaciones, mantenimiento, userName);
        //userMupinListRef.document(idMupi).set(mupins).addOnSuccessListener(aVoid -> Log.d("TAG", "Mupi creado de manera exitosa!"));

        Map<String, Object> map = new HashMap<>();
        map.put("caras", caras);
        map.put("tipoMueble", tipoMueble);
        map.put("estacion", estacion);
        map.put("ubicacion", ubicacion);
        map.put("ciudad", ciudad);
        map.put("limpieza", limpieza);
        map.put("observaciones", observaciones);
        map.put("mantenimiento", mantenimiento);

        rootRef.collection("mupins").document().set(map);

    }


    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
        firebaseAuth.addAuthStateListener(authStateListener);
        firestoreRecyclerAdapter.startListening();

    }

    @Override
    protected void onStop() {
        super.onStop();
        if(googleApiClient.isConnected()){
            googleApiClient.disconnect();
        }

        if (firestoreRecyclerAdapter != null) {
          firestoreRecyclerAdapter.stopListening();
       }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.sign_out_button:
                signOut();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
