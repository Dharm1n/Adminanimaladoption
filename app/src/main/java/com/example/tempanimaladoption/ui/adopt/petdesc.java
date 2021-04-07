package com.example.tempanimaladoption.ui.adopt;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.tempanimaladoption.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class petdesc extends Fragment {

    private PetdescViewModel mViewModel;
    private ModelClass pet;

    private DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    private Context context;

    public static petdesc newInstance() {
        return new petdesc();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.petdesc_fragment, container, false);


        context=root.getContext();
        pet = getArguments().getParcelable("adopted pet");


        ImageView imagev= (ImageView) root.findViewById(R.id.imageviewt);
        Glide.with(context).load(pet.getUrl()).into(imagev);
        TextView _name,_age,_desc,_type,_gender;
        _name=root.findViewById(R.id.namet);
        _age=root.findViewById(R.id.aget);
        _desc=root.findViewById(R.id.desct);
        _type=root.findViewById(R.id.typet);
        _gender=root.findViewById(R.id.gendert);

        _name.setText(pet.getName());
        _age.setText(pet.getAge());
        _desc.setText(pet.getDesc());
        _type.setText(pet.getAtype());
        _gender.setText(pet.getGender());

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PetdescViewModel.class);
        // TODO: Use the ViewModel
    }

}