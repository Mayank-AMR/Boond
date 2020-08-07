package com.example.findmyblod;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.List;

public class DonaterDataRecyclerAdapter extends RecyclerView.Adapter<DonaterDataRecyclerAdapter.DonaterDataHolder>{
    private static final String TAG = "DonaterDataRecyclerAdap";

    private Context mContext;
    private List<DonaterData> mDonaterDataList;

    public DonaterDataRecyclerAdapter(Context mContext, List<DonaterData> mDonaterDataList) {
        this.mContext = mContext;
        this.mDonaterDataList = mDonaterDataList;
    }

    @NonNull
    @Override
    public DonaterDataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.donater_detail, parent, false);
        return new DonaterDataHolder(view);
    }

    void loadNewData(List<DonaterData> donaterData) {
        mDonaterDataList = donaterData;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull DonaterDataHolder holder, int position) {
        holder.name.setText(mDonaterDataList.get(position).getmName());
        holder.email.setText(mDonaterDataList.get(position).getmEmail());
        holder.mono.setText(mDonaterDataList.get(position).getmMobileNo());
        holder.bloodgroup.setText(mDonaterDataList.get(position).getmBloodGroup());
        holder.address.setText(mDonaterDataList.get(position).getmAddress());
        holder.city.setText(mDonaterDataList.get(position).getmCity());
        holder.state.setText(mDonaterDataList.get(position).getmState());
        holder.pincode.setText(String.valueOf(mDonaterDataList.get(position).getmPin()));
    }

    @Override
    public int getItemCount() {
        return mDonaterDataList.size();
    }

    public class DonaterDataHolder extends RecyclerView.ViewHolder {
        TextView name,email,mono,bloodgroup,address,city,state,pincode;

        public DonaterDataHolder(@NonNull View itemView) {
            super(itemView);

            this.name = itemView.findViewById(R.id.textViewDName);
            this.email = itemView.findViewById(R.id.textViewDEmail);
            this.mono = itemView.findViewById(R.id.textViewDMoNo);
            this.bloodgroup = itemView.findViewById(R.id.textViewDBloodGroup);
            this.address = itemView.findViewById(R.id.textViewDAddress);
            this.city = itemView.findViewById(R.id.textViewDCity);
            this.state = itemView.findViewById(R.id.textViewDState);
            this.pincode = itemView.findViewById(R.id.textViewDPin);
        }
    }
}
