package com.example.findmyblod;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BloodBankDetailActivity extends AppCompatActivity implements GetFlickrJsonData.OnDataAvailable {
    private static final String TAG = "BloodBankDetailActivity";
    private String mUserBloodGroup = null;
    private String mBloodQuantity = null;
    private BankRecyclerviewAdapter mBankRecyclerviewAdapter;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: start..");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_bank_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mUserBloodGroup = extras.getString("bloodGroup");
            mBloodQuantity = extras.getString("bloodQuantity");
        }

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(BloodBankDetailActivity.this));
        mBankRecyclerviewAdapter = new BankRecyclerviewAdapter(this, new ArrayList<LocationData>());
        recyclerView.setAdapter(mBankRecyclerviewAdapter);
        mBankRecyclerviewAdapter.notifyDataSetChanged();

        Log.d(TAG, "onCreate: end..");
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume starts");
        super.onResume();
        GetFlickrJsonData getFlickrJsonData = new GetFlickrJsonData(this, "http://boond.pythonanywhere.com/api/bloodbanks?", mUserBloodGroup, mBloodQuantity);
        //getFlickrJsonData.executeOnSameThread();
        getFlickrJsonData.execute();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait..");
        progressDialog.setMessage("");
        progressDialog.show();


        Log.d(TAG, "onResume ends");
    }

    @Override
    public void onDataAvailable(List<BloodBank> data, DownloadStatus status) {
        Log.d(TAG, "onDataAvailable: BloodBankDataAvailable.");
        progressDialog.dismiss();
        if (status == DownloadStatus.OK && MainActivity.userCurrentLatitude!=0.0 && MainActivity.userCurrentLongitude!=0.0) {
            Log.d(TAG, "onDataAvailable: status " + status);
            progressDialog.show();

            Log.d(TAG, "onDataAvailable: latitude " + MainActivity.userCurrentLatitude + " Longitude " + MainActivity.userCurrentLongitude);
//            if(MainActivity.userCurrentLatitude!=0.0 && MainActivity.userCurrentLongitude!=0.0){

            List<LocationData> lData = new ArrayList<>();
            for (int i = 0; i < data.size(); i++) {
                GoogleMatrixDataDownload cddfa = new GoogleMatrixDataDownload(data.get(i).getmLatitude(), data.get(i).getmLongitude());
                cddfa.execute();
                String si = cddfa.doInBackground();

                //---------------------------------------------------------------------------------
                try {
                    JSONObject js = new JSONObject(si);
                    JSONArray destination_addresses = js.getJSONArray("destination_addresses");
                    String da = destination_addresses.getString(0);
                    JSONArray origin_addresses = js.getJSONArray("origin_addresses");
                    String oa = origin_addresses.getString(0);

                    JSONArray rows = js.getJSONArray("rows");
                    JSONArray elements = rows.getJSONObject(0).getJSONArray("elements");

                    String textDestence = elements.getJSONObject(0).getJSONObject("distance").getString("text");
                    int valueDistence = Integer.parseInt(elements.getJSONObject(0).getJSONObject("distance").getString("value"));

                    //Log.d(TAG, "onDataAvailable: "+textDestence);
                    String textDuration = elements.getJSONObject(0).getJSONObject("duration").getString("text");
                    int valueDuration = Integer.parseInt(elements.getJSONObject(0).getJSONObject("duration").getString("value"));

                    //Log.d(TAG, "onDataAvailable: "+textDuration);
//              String DestinationAddress, String OriginAddress, String DistanceText, String DistanceValue, String DurationText, String DurationValue
                    LocationData locationData = new LocationData(da, oa, textDestence, valueDistence, textDuration, valueDuration, data.get(i).getmName(), data.get(i).getmLatitude() + "," + data.get(i).getmLongitude());
                    lData.add(locationData);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            Collections.sort(lData, new SortByDistance());

            progressDialog.dismiss();

            Log.d(TAG, "onDataAvailable: Recycler view Initiatised");

            mBankRecyclerviewAdapter.loadNewData(lData);


        } else {
            // download or processing failed
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(BloodBankDetailActivity.this);
                    alertDialog.setCancelable(false);
                    alertDialog.setTitle("No data found.");
                            //.setMessage("Please Login to access cart item.")
                            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Continue with Login operation
                                    startActivity(new Intent(BloodBankDetailActivity.this,MainActivity.class));
                                }
                            })
//                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    startActivity(new Intent(BloodBankDetailActivity.this,MainActivity.class));
//                                }
//                            })
                            // A null listener allows the button to dismiss the dialog and take no further action.
                            .setNegativeButton("", null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

            Log.e(TAG, "onDataAvailable failed with status " + status);
        }

    }

    class SortByDistance implements Comparator<LocationData> {
        @Override
        public int compare(LocationData o1, LocationData o2) {
            return o1.getmDistanceValue() - o2.getmDistanceValue();
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
