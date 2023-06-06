package com.example.orvrs;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class UserAdapter extends FirebaseRecyclerAdapter<UserModel,UserAdapter.ViewHolder> {

    public UserAdapter(@NonNull FirebaseRecyclerOptions<UserModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull UserAdapter.ViewHolder holder, int position, @NonNull UserModel model) {


        holder.txtUName.setText(model.getName());
        holder.txtUEmail.setText(model.getEmail());
        holder.txtUPhone.setText(model.getMobile());

    }

    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_display_sheet,parent,false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtUName,txtUEmail,txtUPhone;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtUName = itemView.findViewById(R.id.txtUsersName);
            txtUEmail = itemView.findViewById(R.id.txtUsersEmail);
            txtUPhone = itemView.findViewById(R.id.txtUsersPhone);
        }
    }
}
