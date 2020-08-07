package com.example.findmyblod;

import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class GetGoogleLocationData extends AsyncTask<String,String, List<LocationData>> implements GetRawData.OnDownloadComplete{
    private static final String TAG = "GetGoogleLocationData";
    private List<LocationData> mLocationData = null;
    private Location mUserGPSLocation;
    private final OnDataAvailable mCallBack;
    private boolean runningOnSameThread = false;
    private String mBankLatitude;
    private String mBankLongitude;

    interface OnDataAvailable {
        void onDataAvailable(List<LocationData> data, DownloadStatus status);
    }

    public GetGoogleLocationData(OnDataAvailable CallBack, String banklatitude,String bankuserlongitude) {
        this.mBankLatitude = banklatitude;
        this.mBankLongitude = bankuserlongitude;
        this.mCallBack = CallBack;
    }

    private String createUri(String Banklatitude, String Banklongitude) {
        Log.d(TAG, "createUri starts");
        return Uri.parse("https://maps.googleapis.com/maps/api/distancematrix/json?").buildUpon()
                .appendQueryParameter("units", "imperial")
                .appendQueryParameter("origins", mUserGPSLocation.getLatitude()+","+mUserGPSLocation.getLongitude())
                .appendQueryParameter("destinations",Banklatitude+","+Banklongitude)
                .build().toString();
    }


    @Override
    protected List<LocationData> doInBackground(String... strings) {
        Log.d(TAG, "doInBackground starts");
        String destinationUri = createUri(mBankLatitude,mBankLongitude);
        GetRawData getRawData = new GetRawData(this);
        getRawData.runInSameThread(destinationUri);
        Log.d(TAG, "doInBackground ends");
        return mLocationData;
    }

    @Override
    public void onDownloadComplete(String data, DownloadStatus status) {
        Log.d(TAG, "onDownloadComplete: Mayank");

    }
}
