package com.example.orvrs;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class ServicesAdapter extends FirebaseRecyclerAdapter<ServicesModel,ServicesAdapter.ViewHolder> {

    public ServicesAdapter(@NonNull FirebaseRecyclerOptions<ServicesModel> options)
    {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ServicesAdapter.ViewHolder holder, int position, @NonNull ServicesModel model)
    {

        holder.txtService_name.setText(model.getName());
        holder.txtService_price.setText(model.getPrice());
        holder.txtService_desc.setText(model.getDesc());
        Glide.with(holder.img.getContext()).load(model.getPurl()).into(holder.img);

    }

    @NonNull
    @Override
    public ServicesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.our_services_layout,parent,false);
        return new ViewHolder(view);
    }



    class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView img;
        TextView txtService_name,txtService_price,txtService_desc;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            img = itemView.findViewById(R.id.imgServices);
            txtService_name = itemView.findViewById(R.id.txtService_Name);
            txtService_price = itemView.findViewById(R.id.txtTotal_Price);
            txtService_desc = itemView.findViewById(R.id.txtService_description);
        }
    }

}
