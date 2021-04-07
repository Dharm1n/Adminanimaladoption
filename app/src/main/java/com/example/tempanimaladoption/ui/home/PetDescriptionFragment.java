package com.example.tempanimaladoption.ui.home;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.graphics.ColorSpace;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.tempanimaladoption.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PetDescriptionFragment extends Fragment {

     private PetDescriptionViewModel mViewModel;

     private ModelClass pet;

     public static PetDescriptionFragment newInstance() {
          return new PetDescriptionFragment();
     }

     private DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

     private Context context;
     private int count=0;
     public void deleteit(View view){

          count=count+1;
          if(count==1)
          {
               ref.child("animal").child(pet.getAid()).removeValue();
               Toast.makeText(context, "Animal data deleted successfully.Go back to see changes", Toast.LENGTH_LONG).show();
          }
          else
          {
               Toast.makeText(context, "Animal data is already deleted...", Toast.LENGTH_LONG).show();
          }
     }
     @Override
     public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                              @Nullable Bundle savedInstanceState) {
          View root = inflater.inflate(R.layout.pet_description_fragment, container, false);


          context=root.getContext();
          pet = getArguments().getParcelable("pet");


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


          Button button = root.findViewById(R.id.deletebutton);
          button.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                    deleteit(v);
               }
          });
          return root;
     }

     @Override
     public void onActivityCreated(@Nullable Bundle savedInstanceState) {
          super.onActivityCreated(savedInstanceState);
          mViewModel = new ViewModelProvider(this).get(PetDescriptionViewModel.class);
          // TODO: Use the ViewModel
     }

}