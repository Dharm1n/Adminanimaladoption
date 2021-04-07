package com.example.tempanimaladoption.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tempanimaladoption.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    Context context;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;

    private DatabaseReference animalref = FirebaseDatabase.getInstance().getReference();
    Adapter adapter;
    private ArrayList<ModelClass> petss;

    private  void getanimalinfo(){
        animalref.child("animal").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ModelClass pet = snapshot.getValue(ModelClass.class);
                pet.setAid(snapshot.getKey());
                if(pet.getStatus().equals("not adopted"))
                {petss.add(pet);
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
                petss.remove(pet);
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
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        context=root.getContext();

        recyclerView= root.findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        petss = new ArrayList<>();
        adapter = new Adapter(context , petss);
        recyclerView.setAdapter(adapter);


        getanimalinfo();
        adapter.setOnItemClickListener(position -> {
            Bundle b = new Bundle();

            b.putParcelable("pet", petss.get(position));
            Navigation.findNavController(root).navigate(R.id.action_nav_home_to_petDescriptionFragment, b);
        });

        return root;
    }

}
