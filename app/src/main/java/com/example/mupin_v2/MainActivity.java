package com.example.mupin_v2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import com.example.mupin_v2.holders.MupiViewHolder;
import com.example.mupin_v2.models.MupiModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private String userEmail, userName;
    private GoogleApiClient googleApiClient;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore rootRef;
    private FirebaseAuth.AuthStateListener authStateListener;
    private CollectionReference mupisListRef;
    private  FirestoreRecyclerAdapter<MupiModel, MupiViewHolder> firestoreRecyclerAdapter;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

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

              buscarMupin(mupi);
                startActivity(new Intent(MainActivity.this, MupiActivity.class ));
            });

            builder.setNegativeButton("Cancelar", (dialogInterface, i) -> dialogInterface.dismiss());

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });

            //mupisListRef = rootRef.collection("mupis").document(userEmail).collection("users");

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        TextView emptyView = findViewById(R.id.empty_view);
        ProgressBar progressBar = findViewById(R.id.progress_bar);

       Query query = rootRef.collection("mupis").orderBy("estacion");

       FirestoreRecyclerOptions<MupiModel> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<MupiModel>()
              .setQuery(query, MupiModel.class)
              .build();

        firestoreRecyclerAdapter =
                new FirestoreRecyclerAdapter<MupiModel, MupiViewHolder>(firestoreRecyclerOptions) {
                    @Override
                    protected void onBindViewHolder(@NonNull MupiViewHolder mupiViewHolder, int i, @NonNull MupiModel mupiModel) {
                        mupiViewHolder.setMupiList(getApplicationContext(), mupiModel);

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
                });
    }

    private void buscarMupin(String idMupi) {

        //mupiBuscado = rootRef.collection("mupis").document(idMupi);
       // String idmupibuscado = mupiBuscado.getId();
       // return mupiBuscado;
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
