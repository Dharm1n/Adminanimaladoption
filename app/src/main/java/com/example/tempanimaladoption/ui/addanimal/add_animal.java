package com.example.tempanimaladoption.ui.addanimal;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tempanimaladoption.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class add_animal extends Fragment {

    private AddAnimalViewModel mViewModel;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mDatabase = database.getReference();

    EditText name,type,link,age,desc;
    Spinner spin;
    Context context;

    public void addanimalinfo(){
        String tn,ty,tl,ta,td;

        tn=name.getText().toString();
        ty=type.getText().toString();
        tl=link.getText().toString();
        ta=age.getText().toString();
        td=desc.getText().toString();
        if(tn.isEmpty())
        {
            Toast.makeText(context, "Enter name of animal", Toast.LENGTH_SHORT).show();
        }
        else if(ty.isEmpty())
        {
            Toast.makeText(context, "Enter animal type", Toast.LENGTH_SHORT).show();
        }
        else if(tl.isEmpty())
        {
            Toast.makeText(context, "Enter the image link", Toast.LENGTH_SHORT).show();
        }
        else if(ta.isEmpty())
        {
            Toast.makeText(context, "Enter age of animal", Toast.LENGTH_SHORT).show();
        }
        else if(td.isEmpty())
        {
            Toast.makeText(context, "Enter the description", Toast.LENGTH_SHORT).show();
        }
        else {

            mDatabase.child("index").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(context, "Please try again later", Toast.LENGTH_SHORT).show();
                    } else {
                        String[] i;
                        i = String.valueOf(task.getResult().getValue()).split("=");

                        //String ind = Integer.toString(index[0]+1);
                        String str=i[1];
                        str = str.replace(str.substring(str.length()-1), "");


                        int index=Integer.parseInt(str);
                        index++;
                        str=Integer.toString(index);

                        String ind=str;
                        //Log.e(TAG,str);
                        String gende=spin.getSelectedItem().toString();
                        Map<String, Object> map = new HashMap<>();
                        map.put("name", tn);
                        map.put("age", ta);
                        map.put("desc", td);
                        map.put("url", tl);
                        map.put("atype", ty);
                        map.put("gender",gende);
                        map.put("status","not adopted");

                        mDatabase.child("animal").child(ind).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(context, "Animal is added successfully!", Toast.LENGTH_LONG).show();
                                        name.setText("");
                                        type.setText("");
                                        link.setText("");
                                        age.setText("");
                                        desc.setText("");
                                        mDatabase.child("index").child("id").setValue(ind);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        if (e instanceof FirebaseNetworkException) {
                                            Toast.makeText(context, "check your Internet Connection", Toast.LENGTH_SHORT).show();
                                        }
                                        else {
                                            Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                }
            });

        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mViewModel = new ViewModelProvider(this).get(AddAnimalViewModel.class);
        View root = inflater.inflate(R.layout.add_animal_fragment, container, false);

        context=root.getContext();
        spin=(Spinner) root.findViewById(R.id.spinner);

        ArrayAdapter<String> adp = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.genders));
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adp);

        name=  root.findViewById(R.id.name1);
        type= (EditText) root.findViewById(R.id.atype1);
        link= (EditText) root.findViewById(R.id.imagelink1);
        age= (EditText) root.findViewById(R.id.age1);
        desc= (EditText) root.findViewById(R.id.desc1);

        Button button = root.findViewById(R.id.sub);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addanimalinfo();
            }
        });
        return root;
    }


}