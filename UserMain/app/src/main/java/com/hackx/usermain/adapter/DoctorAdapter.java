package com.hackx.usermain.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.hackx.usermain.R;
import com.hackx.usermain.model.Doctor;
import java.util.List;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder> {
    private List<Doctor> doctorList;
    private Context context;

    public DoctorAdapter(Context context, List<Doctor> doctorList) {
        this.context = context;
        this.doctorList = doctorList;
    }

    @NonNull
    @Override
    public DoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_doctor, parent, false);
        return new DoctorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorViewHolder holder, int position) {
        Doctor doctor = doctorList.get(position);
        holder.nameTextView.setText(doctor.getName());
        holder.phoneTextView.setText(doctor.getPhoneNumber());
//        holder.emailTextView.setText(doctor.getEmail());

        holder.callImageView.setOnClickListener(v -> {
            String phoneNumber = doctor.getPhoneNumber();
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://api.whatsapp.com/send?phone=" + phoneNumber));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return doctorList.size();
    }

    public static class DoctorViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, phoneTextView;
//        , emailTextView;
        ImageView callImageView;

        public DoctorViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.tvDoctorName);
            phoneTextView = itemView.findViewById(R.id.tvDoctorPhone);
//            emailTextView = itemView.findViewById(R.id.tvDoctorEmail);
            callImageView = itemView.findViewById(R.id.ivCall);
        }
    }
}