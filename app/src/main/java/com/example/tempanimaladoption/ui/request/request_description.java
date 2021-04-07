package com.example.tempanimaladoption.ui.request;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class request_description extends Fragment {

    private RequestDescriptionViewModel mViewModel;

    private ModelClassreq req;

    private DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    private Context context;


    public static request_description newInstance() {
        return new request_description();
    }
    private String status="pending";

    public void acceptit(View view){
        if(status.equals("pending"))
        {
            status="accepted";
            ref.child("request").child(req.getParentid()).child("status").setValue("accepted");
            ref.child("animal").child(req.getId()).child("status").setValue("adopted");
            ref.child("animal").child(req.getId()).child("assigned").setValue(req.getPhoneno());
            ref.child("request").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    ModelClassreq tempreq = snapshot.getValue(ModelClassreq.class);
                    tempreq.setParentid(snapshot.getKey());
                    if(tempreq.getId().equals(req.getId()) && (!tempreq.getParentid().equals(req.getParentid())))
                    {
                        ref.child("request").child(tempreq.getParentid()).child("status").setValue("rejected");
                    }
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    //TODO
                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                    //TODO
                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            Toast.makeText(context, "This request is accepeted...", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(context, "The request is already " + status + "...", Toast.LENGTH_LONG).show();
        }
    }

    public void rejectit(View view){
        if(status.equals("pending"))
        {
            status="rejected";
            ref.child("request").child(req.getParentid()).child("status").setValue("rejected");
            Toast.makeText(context, "This request is rejected...", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(context, "The request is already " + status + "...", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.request_description_fragment, container, false);


        context=root.getContext();
        req = getArguments().getParcelable("request");


        ImageView imagev= (ImageView) root.findViewById(R.id.imageviewt);
        Glide.with(context).load(req.getUrl()).into(imagev);
        TextView _name,_age,_type,_gender,_phoneno;
        _name=root.findViewById(R.id.namet);
        _age=root.findViewById(R.id.aget);
        _type=root.findViewById(R.id.typet);
        _gender=root.findViewById(R.id.gendert);
        _phoneno=root.findViewById(R.id.phonenot);

        _name.setText(req.getName());
        _age.setText(req.getAge());
        _type.setText(req.getAtype());
        _gender.setText(req.getGender());
        _phoneno.setText(req.getPhoneno());

        Button button = root.findViewById(R.id.acceptbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acceptit(v);
            }
        });
        Button buttonrej = root.findViewById(R.id.rejectbutton);
        buttonrej.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rejectit(v);
            }
        });
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(RequestDescriptionViewModel.class);
        // TODO: Use the ViewModel
    }

}