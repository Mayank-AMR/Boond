package com.example.findmyblod;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BankRecyclerviewAdapter extends RecyclerView.Adapter<BankRecyclerviewAdapter.BankDataHolder> {
    private static final String TAG = "BankRecyclerviewAdapter";
    private List<LocationData> mBankLocationDataList;
    private Context mContext;

    public BankRecyclerviewAdapter(Context mContext, List<LocationData> locationData) {
        this.mBankLocationDataList = locationData;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public BankDataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bank_view, parent, false);
        return new BankDataHolder(view);
    }

    void loadNewData(List<LocationData> locationData) {
        mBankLocationDataList = locationData;
        notifyDataSetChanged();
    }

    public LocationData getBank(int position) {
        return ((mBankLocationDataList != null) && (mBankLocationDataList.size() !=0) ? mBankLocationDataList.get(position) : null);
    }

    @Override
    public void onBindViewHolder(@NonNull BankDataHolder holder, int position) {
        LocationData bloodBankItem = mBankLocationDataList.get(position);
        Log.d(TAG, "onBindViewHolder: " +  " --> " + position);
        //holder.bankName.setText(bloodBankItem.getmName());
        holder.bankName.setText(mBankLocationDataList.get(position).getmDestinationAddress());

        holder.bankName.setClickable(true);
        final String destinationCordinates = mBankLocationDataList.get(position).getmBankCordinates();
        holder.bankName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("geo:"+destinationCordinates+"?q="+destinationCordinates);
                //Uri gmmIntentUri = Uri.parse("geo:27.598142,76.6280483?q=1600 Amphitheatre Parkway, Mountain+View, California");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(mContext.getPackageManager()) != null) {
                    mContext.startActivity(mapIntent);
                }
            }
        });
        holder.bankAddree.setText(bloodBankItem.getmBankName());
        holder.bankPincode.setText(String.valueOf(bloodBankItem.getmDestinationAddress()));
        holder.bankDistence.setText(((bloodBankItem.getmDistanceValue())/1000)+" KM");
    }

    @Override
    public int getItemCount() {
        //Log.d(TAG, "getItemCount: BankListSixe: = "+mBloodBanlList.size());
        return mBankLocationDataList.size();
        //return ((mBloodBanlList != null) && (mBloodBanlList.size() !=0) ? mBloodBanlList.size() : 0);
    }

    public class BankDataHolder extends RecyclerView.ViewHolder {
        TextView bankName, bankAddree,bankPincode,bankDistence;

        public BankDataHolder(@NonNull View itemView) {
            super(itemView);
            this.bankName = itemView.findViewById(R.id.textViewBankName);
            this.bankAddree = itemView.findViewById(R.id.textViewBankAddress);
            this.bankPincode = itemView.findViewById(R.id.textViewPinCode);
            this.bankDistence = itemView.findViewById(R.id.textViewDistence);
        }
    }
}
