package com.example.orvrs;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;


public class VehicleAdapter extends FirebaseRecyclerAdapter<VehicleModel,VehicleAdapter.ViewHolder> {



    public VehicleAdapter(@NonNull FirebaseRecyclerOptions<VehicleModel> options) {
        super(options);
    }


    @NonNull
    @Override
    public VehicleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vehicle_item_display,parent,false);
        return new ViewHolder(view);
    }


    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull VehicleModel model) {


        holder.vehicleOwnerName.setText(model.getOwnerName());
        holder.vehicleNumber.setText(model.getVehicleNumber());
        holder.vehicleModel.setText(model.getVehicleModel());
        holder.vehicleChassisNo.setText(model.getVehicleChassisNo());

    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView vehicleOwnerName,vehicleNumber,vehicleModel,vehicleChassisNo;
        //private ImageView vehicleImage;
        private RelativeLayout vehicleItemDisplay;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            vehicleOwnerName = itemView.findViewById(R.id.txtVehicleOwnerName);
            vehicleNumber = itemView.findViewById(R.id.txtVehicleNumber);
            vehicleModel = itemView.findViewById(R.id.txtVehicleModel);
            vehicleChassisNo = itemView.findViewById(R.id.txtVehicleChassisNo);
//            vehicleImage = itemView.findViewById(R.id.imgVehicle);
            vehicleItemDisplay = itemView.findViewById(R.id.relative_vehicle_display);
        }
    }


}
