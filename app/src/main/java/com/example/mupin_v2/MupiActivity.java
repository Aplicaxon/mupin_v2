package com.example.mupin_v2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.mupin_v2.models.Mupins;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter_LifecycleAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MupiActivity extends AppCompatActivity {

    RecyclerView recyclerViewMupis;
    MupisAdapter mupisAdapter;
    FirebaseFirestore miFirebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mupi);

        recyclerViewMupis = findViewById(R.id.recyclerMupi);
        recyclerViewMupis.setLayoutManager(new LinearLayoutManager(this));

        Query query = miFirebaseFirestore.collection("mupis");

        FirestoreRecyclerOptions<Mupins> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Mupins>()
                .setQuery(query,Mupins.class).build();
        mupisAdapter = new MupisAdapter(firestoreRecyclerOptions);
        mupisAdapter.notifyDataSetChanged();
        recyclerViewMupis.setAdapter(mupisAdapter);


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
}