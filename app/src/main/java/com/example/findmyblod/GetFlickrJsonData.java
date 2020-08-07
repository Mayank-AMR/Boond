package com.example.findmyblod;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

class GetFlickrJsonData extends AsyncTask<String, String, List<BloodBank>> implements GetRawData.OnDownloadComplete {
    private static final String TAG = "GetFlickrJsonData";
    private List<BloodBank> mBloodBankList = null;
    private String mBaseURL;
    private String mUserBloodGroup;
    private String mBloodQuantity;

    private final OnDataAvailable mCallBack;
    private boolean runningOnSameThread = false;

    interface OnDataAvailable {
        void onDataAvailable(List<BloodBank> data, DownloadStatus status);
    }

    public GetFlickrJsonData(OnDataAvailable callBack, String baseURL, String userBloodGroup, String  bloodQuantity) {
        Log.d(TAG, "GetFlickrJsonData called");
        mBaseURL = baseURL;
        mCallBack = callBack;
        mUserBloodGroup = userBloodGroup;
        mBloodQuantity = bloodQuantity;
    }

    void executeOnSameThread() {
        Log.d(TAG, "executeOnSameThread starts");
        runningOnSameThread = true;
        String destinationUri = createUri(mUserBloodGroup, String.valueOf(mBloodQuantity));

        GetRawData getRawData = new GetRawData(this);
        getRawData.execute(destinationUri);
        Log.d(TAG, "executeOnSameThread ends");
    }

    @Override
    protected void onPostExecute(List<BloodBank> photos) {
        Log.d(TAG, "onPostExecute starts");

        if(mCallBack != null) {
            mCallBack.onDataAvailable(mBloodBankList, DownloadStatus.OK);
        }
        Log.d(TAG, "onPostExecute ends");
    }

    @Override
    protected List<BloodBank> doInBackground(String... params) {
        Log.d(TAG, "GetFlickerData: doInBackground starts");
        String destinationUri = createUri(mUserBloodGroup, String.valueOf(mBloodQuantity));

        GetRawData getRawData = new GetRawData(this);
        getRawData.runInSameThread(destinationUri);
        Log.d(TAG, "GetFlickerData: doInBackground ends");
        return mBloodBankList;
    }

    private String createUri(String userBloodGroup, String bloodQuantity) {
        Log.d(TAG, "createUri starts");

        return Uri.parse(mBaseURL).buildUpon()
                .appendQueryParameter("blood_group", userBloodGroup)
                .appendQueryParameter("total_ml", bloodQuantity)
                .build().toString();
    }

    @Override
    public void onDownloadComplete(String data, DownloadStatus status) {
        Log.d(TAG, "onDownloadComplete starts. Status = " + status);

        if(status == DownloadStatus.OK ) {
            mBloodBankList = new ArrayList<>();
            try {
                //JSONObject jsonData = new JSONObject(data);
                JSONArray itemsArray = new JSONArray(data);

                for(int i=0; i<itemsArray.length(); i++) {
                    JSONObject jsonBloodBank = itemsArray.getJSONObject(i);

                    String name = jsonBloodBank.getString("name");
                    String address = jsonBloodBank.getString("address");
                    int pincode = jsonBloodBank.getInt("pincode");
                    int state = jsonBloodBank.getInt("state");
                    String city = jsonBloodBank.getString("city");
                    String latitude = jsonBloodBank.getString("latitude");
                    String longitude = jsonBloodBank.getString("longitude");
                    Log.d(TAG, "onDownloadComplete:");

                    BloodBank bloodBankObject = new BloodBank(name,address,pincode,state,city,latitude,longitude);
                    mBloodBankList.add(bloodBankObject);

                    Log.d(TAG, "onDownloadComplete: bloodBankObject " + bloodBankObject.toString());
                }
            } catch(JSONException jsone) {
                jsone.printStackTrace();
                Log.e(TAG, "onDownloadComplete: Error processing Json data " + jsone.getMessage());
                status = DownloadStatus.FAILED_OR_EMPTY;
            }
        }
        if(runningOnSameThread && mCallBack != null) {
            // now inform the caller that processing is done - possibly returning null if there
            // was an error
            mCallBack.onDataAvailable(mBloodBankList, status);
        }

        Log.d(TAG, "onDownloadComplete ends");
    }
}





















