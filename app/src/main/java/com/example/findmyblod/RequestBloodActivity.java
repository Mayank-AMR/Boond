package com.example.findmyblod;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RequestBloodActivity extends AppCompatActivity {
    private static final String TAG = "RequestBloodActivity";

    private DonaterDataRecyclerAdapter mDonaterDataRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_blood);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        HashMap<Integer,String> map = new HashMap<>();
        
        
        RecyclerView recyclerView = findViewById(R.id.requestBloodRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(RequestBloodActivity.this));
        mDonaterDataRecyclerAdapter = new DonaterDataRecyclerAdapter(this, new ArrayList<DonaterData>());
        recyclerView.setAdapter(mDonaterDataRecyclerAdapter);
        mDonaterDataRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: ");
        super.onResume();
        DonaterDataDownload ddDownload = new DonaterDataDownload("jaipur", 1);
        ddDownload.execute();
        String si = ddDownload.doInBackground();
        //Log.d(TAG, "onResume: "+si);

        List<DonaterData> donaterDataList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(si);
            for (int i =0; i<jsonArray.length(); i++){
                String mName = jsonArray.getJSONObject(i).getString("name");
                String mEmail = jsonArray.getJSONObject(i).getString("email");
                String mMobileNo = jsonArray.getJSONObject(i).getString("mobile_no");
                String mBloodGroup = jsonArray.getJSONObject(i).getString("blood_group");
                String mAddress = jsonArray.getJSONObject(i).getString("address");
                int mPin = jsonArray.getJSONObject(i).getInt("pincode");
                String mState = jsonArray.getJSONObject(i).getString("state");
                String mCity = jsonArray.getJSONObject(i).getString("city");
                String mLatitude = jsonArray.getJSONObject(i).getString("latitude");
                String mLongitude = jsonArray.getJSONObject(i).getString("longitude");
                Log.d(TAG, "onResume: "+mAddress);

                DonaterData donaterData = new DonaterData(mName,mEmail,mMobileNo,MainActivity.map.get(Integer.parseInt(mBloodGroup)),mAddress,mPin,mState,mCity,mLatitude,mLongitude);
                donaterDataList.add(donaterData);


            }
            Log.d(TAG, "onResume: RecyclerView");
            mDonaterDataRecyclerAdapter.loadNewData(donaterDataList);
            Log.d(TAG, "onResume: Recycler set");
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
