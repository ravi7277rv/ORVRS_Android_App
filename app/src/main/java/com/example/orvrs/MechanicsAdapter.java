package com.example.orvrs;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class MechanicsAdapter extends FirebaseRecyclerAdapter<MechanicsModel,MechanicsAdapter.ViewHolder> {


    public MechanicsAdapter(@NonNull FirebaseRecyclerOptions<MechanicsModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull MechanicsModel model) {

       String mechanic_name = model.getName();  // you can get data into a variable
       String mechanic_email = model.getEmail();
       String mechanic_phone = model.getPhone();

        holder.txtName.setText(mechanic_name);  // now pass the data into the setText() method
        holder.txtEmail.setText(mechanic_email);
        holder.txtPhone.setText(mechanic_phone);
        holder.txtCharges.setText(model.getCharges()); // you can get data from database like this
        holder.txtExpYears.setText(model.getExp_year());
        holder.txtExpertise.setText(model.getExpertIn());
        holder.txtAddress.setText(model.getAddress());
        //holder.rootView.getContext();    this is the way of getting the context
        Intent iNext = new Intent(holder.rootView.getContext(),RequestService.class);

        iNext.putExtra("email",mechanic_email); // passing the extra data to the next Activity through intent
        iNext.putExtra("phone",mechanic_phone);

        holder.btnRequestService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 holder.rootView.getContext().startActivity(iNext);
            }
        });

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mechanic_item_display,parent,false);
        return new ViewHolder(view);
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtName,txtEmail,txtPhone,txtCharges,txtExpYears,txtExpertise,txtAddress;
        Button btnRequestService;
        View rootView;  // for getting context of parent in adapter

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            rootView = itemView;   //  get the context of parent in adapter
            txtName = itemView.findViewById(R.id.txt_mechanic_name);
            txtEmail = itemView.findViewById(R.id.txt_mechanic_email);
            txtPhone = itemView.findViewById(R.id.txt_mechanic_phone);
            txtCharges = itemView.findViewById(R.id.txt_mechanic_charge);
            txtExpYears = itemView.findViewById(R.id.txt_mechanic_expYear);
            txtExpertise = itemView.findViewById(R.id.txt_mechanic_expertise);
            txtAddress = itemView.findViewById(R.id.txt_mechanic_address);
            btnRequestService = itemView.findViewById(R.id.btn_serviceRequest);

        }
    }
}
