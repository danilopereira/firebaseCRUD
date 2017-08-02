package br.com.fiap.firebasecrud.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.fiap.firebasecrud.R;
import br.com.fiap.firebasecrud.adapter.RecyclerViewAdapter;
import br.com.fiap.firebasecrud.model.Carro;

/**
 * A simple {@link Fragment} subclass.
 */
public class CarroFragment extends Fragment {

    private RecyclerView rvCarros;

    private RecyclerViewAdapter recyclerViewAdapter;
    private EditText etCarro;
    private Button btAdd;

    private DatabaseReference databaseReference;
    private List<Carro> carros;

    public CarroFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_carro, container, false);

        carros = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("carros");

        etCarro = (EditText) v.findViewById(R.id.etCarro);
        rvCarros = (RecyclerView)v.findViewById(R.id.rvCarros);
        btAdd = (Button) v.findViewById(R.id.btAdd);

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String descricao = etCarro.getText().toString();

                if(TextUtils.isEmpty(descricao)){
                    Toast.makeText(getContext(), "Informe um carro", Toast.LENGTH_LONG).show();
                    return;
                }

                Carro taskObject = new Carro(descricao);
                databaseReference.push().setValue(taskObject);
                etCarro.setText("");

            }
        });

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                getAll(dataSnapshot);
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                getAll(dataSnapshot);
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                delete(dataSnapshot);
            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        return v;
    }
    private void getAll(DataSnapshot dataSnapshot){
        for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
            String modelo = singleSnapshot.getValue(String.class);
            carros.add(new Carro(modelo));
        }
        recyclerViewAdapter = new RecyclerViewAdapter(getContext(), carros);
        rvCarros.setLayoutManager(new LinearLayoutManager(getContext()));
        rvCarros.setAdapter(recyclerViewAdapter);
    }

    private void delete(DataSnapshot dataSnapshot){
        for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
            String taskTitle = singleSnapshot.getValue(String.class);
            for(int i = 0; i < carros.size(); i++){
                if(carros.get(i).getModelo().equals(taskTitle)){
                    carros.remove(i);
                }
            }
            recyclerViewAdapter.notifyDataSetChanged();
            recyclerViewAdapter = new RecyclerViewAdapter(getContext(), carros);
            rvCarros.setAdapter(recyclerViewAdapter);
        }
    }
}
