package com.example.tempanimaladoption.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tempanimaladoption.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.myviewholder>
{

    private Context context;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onClick(int position);
    }

    private ArrayList<ModelClass> pets = new ArrayList<>();

    public Adapter(Context context , ArrayList<ModelClass> list)
    {
        this.context = context;
        this.pets = list;
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public void setPets(ArrayList<ModelClass> pets){
        this.pets = pets;
        notifyDataSetChanged();
    }

    public ModelClass getPetAtPosition(int position){
        return pets.get(position);
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_design,parent,false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        ModelClass model = pets.get(position);
        holder.name1.setText(model.getName());
        holder.age1.setText(model.getAge());
        holder.atype1.setText(model.getAtype());
        Glide.with(holder.image1.getContext()).load(model.getUrl()).into(holder.image1);
    }

    @Override
    public int getItemCount() {
        return pets.size();
    }

    class myviewholder extends RecyclerView.ViewHolder
    {
        ImageView image1;
        TextView name1,age1,atype1;
        public myviewholder(@NonNull View itemView)
        {
            super(itemView);
            image1=(ImageView)itemView.findViewById(R.id.imageviewt);
            name1=(TextView)itemView.findViewById(R.id.name);
            age1=(TextView)itemView.findViewById(R.id.aget);
            atype1=(TextView)itemView.findViewById(R.id.animaltype);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            mListener.onClick(position);
                        }
                    }
                }
            });
        }
    }
}
