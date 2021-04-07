package com.example.tempanimaladoption.ui.adopt;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tempanimaladoption.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class adopted extends Fragment {

    private AdoptedViewModel mViewModel;

    Context context;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;

    private DatabaseReference animalref = FirebaseDatabase.getInstance().getReference();
    Adapter adapter;
    private ArrayList<ModelClass> _petss;

    public static adopted newInstance() {
        return new adopted();
    }

    private void getadoptedanimals(){
        animalref.child("animal").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ModelClass pet = snapshot.getValue(ModelClass.class);
                pet.setAid(snapshot.getKey());
                if(pet.getStatus().equals("adopted"))
                {_petss.add(pet);
                    adapter.notifyDataSetChanged();}
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                //TODO
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                //TODO
                ModelClass pet=snapshot.getValue(ModelClass.class);
                _petss.remove(pet);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel =
                new ViewModelProvider(this).get(AdoptedViewModel.class);
        View root = inflater.inflate(R.layout.adopted_fragment, container, false);
        context=root.getContext();

        recyclerView= root.findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        _petss = new ArrayList<>();
        adapter = new Adapter(context , _petss);
        recyclerView.setAdapter(adapter);
        getadoptedanimals();

        adapter.setOnItemClickListener(position -> {
            Bundle b = new Bundle();

            b.putParcelable("adopted pet", _petss.get(position));
            Navigation.findNavController(root).navigate(R.id.nav_adopted_description, b);
        });

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AdoptedViewModel.class);
        // TODO: Use the ViewModel
    }

}