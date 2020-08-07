package com.example.findmyblod;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Button aPositive, aNegative, bPositive, bNegative, abPositive, abNegative, oPositive, oNegative;
    private Button mRequestBloodButton;
    private TextView bloodQuantity;
    private Button findBlood;
    private boolean bloodGroupSelection = false;
    private String bloodGroup;
    private LocationListener locationListener;
    private LocationManager locationManager = null;
    private LatLng userLatLong;
    public static double userCurrentLatitude;
    public static double userCurrentLongitude;
    public static HashMap<Integer,String> map = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: start.");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        if (android.os.Build.VERSION.SDK_INT > 9) {
//            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//            StrictMode.setThreadPolicy(policy);
//        }
        map.put(1,"A-");
        map.put(2,"A+");
        map.put(3,"AB-");
        map.put(4,"AB+");
        map.put(5,"B-");
        map.put(6,"B+");
        map.put(7,"O-");
        map.put(8,"O+");

        locationFinder();
        bloodQuantity = findViewById(R.id.textViewQuantity);
        findBlood = findViewById(R.id.findBlood);
        mRequestBloodButton = findViewById(R.id.buttonRequestBlood);
        mRequestBloodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                TextView textViewEnterBloodQuantity = findViewById(R.id.textViewEnterBloodQuantity);
//                textViewEnterBloodQuantity.setText("Enter origin location");
//                bloodQuantity.setInputType(InputType.TYPE_CLASS_TEXT);
//                bloodQuantity.setHint("City ");
//                TextView textViewMI = findViewById(R.id.textViewMI);
//                textViewMI.setVisibility(View.GONE);
//                mRequestBloodButton.setVisibility(View.GONE);

                Intent requestBloodActivityObject = new Intent(MainActivity.this,RequestBloodActivity.class);
                startActivity(requestBloodActivityObject);
            }
        });

        aPositive = findViewById(R.id.aPositive);
        aPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: A+");
                bloodGroup = "2";
                aPositive.setBackgroundColor(getResources().getColor(R.color.colorRED));
                if (bloodGroupSelection) {
                    aNegative.setBackgroundResource(android.R.drawable.btn_default);
                    bPositive.setBackgroundResource(android.R.drawable.btn_default);
                    bNegative.setBackgroundResource(android.R.drawable.btn_default);
                    abPositive.setBackgroundResource(android.R.drawable.btn_default);
                    abNegative.setBackgroundResource(android.R.drawable.btn_default);
                    oPositive.setBackgroundResource(android.R.drawable.btn_default);
                    oNegative.setBackgroundResource(android.R.drawable.btn_default);
                }
                bloodGroupSelection = true;
            }

        });

        aNegative = findViewById(R.id.aNegative);
        aNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: A-");
                bloodGroup = "1";
                aNegative.setBackgroundColor(getResources().getColor(R.color.colorRED));
                if (bloodGroupSelection) {
                    aPositive.setBackgroundResource(android.R.drawable.btn_default);
                    bPositive.setBackgroundResource(android.R.drawable.btn_default);
                    bNegative.setBackgroundResource(android.R.drawable.btn_default);
                    abPositive.setBackgroundResource(android.R.drawable.btn_default);
                    abNegative.setBackgroundResource(android.R.drawable.btn_default);
                    oPositive.setBackgroundResource(android.R.drawable.btn_default);
                    oNegative.setBackgroundResource(android.R.drawable.btn_default);
                }
                bloodGroupSelection = true;
            }
        });

        bPositive = findViewById(R.id.bPositive);
        bPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: B+");
                bloodGroup = "6";
                bPositive.setBackgroundColor(getResources().getColor(R.color.colorRED));

                if (bloodGroupSelection) {
                    aNegative.setBackgroundResource(android.R.drawable.btn_default);
                    aPositive.setBackgroundResource(android.R.drawable.btn_default);
                    bNegative.setBackgroundResource(android.R.drawable.btn_default);
                    abPositive.setBackgroundResource(android.R.drawable.btn_default);
                    abNegative.setBackgroundResource(android.R.drawable.btn_default);
                    oPositive.setBackgroundResource(android.R.drawable.btn_default);
                    oNegative.setBackgroundResource(android.R.drawable.btn_default);
                }
                bloodGroupSelection = true;
            }
        });
        bNegative = findViewById(R.id.bNegative);
        bNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: B-");
                bloodGroup = "5";
                bNegative.setBackgroundColor(getResources().getColor(R.color.colorRED));
                if (bloodGroupSelection) {
                    aNegative.setBackgroundResource(android.R.drawable.btn_default);
                    bPositive.setBackgroundResource(android.R.drawable.btn_default);
                    aPositive.setBackgroundResource(android.R.drawable.btn_default);
                    abPositive.setBackgroundResource(android.R.drawable.btn_default);
                    abNegative.setBackgroundResource(android.R.drawable.btn_default);
                    oPositive.setBackgroundResource(android.R.drawable.btn_default);
                    oNegative.setBackgroundResource(android.R.drawable.btn_default);
                }
                bloodGroupSelection = true;
            }
        });

        abPositive = findViewById(R.id.abPositive);
        abPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: AB+");
                bloodGroup = "4";
                abPositive.setBackgroundColor(getResources().getColor(R.color.colorRED));

                if (bloodGroupSelection) {
                    aNegative.setBackgroundResource(android.R.drawable.btn_default);
                    bPositive.setBackgroundResource(android.R.drawable.btn_default);
                    bNegative.setBackgroundResource(android.R.drawable.btn_default);
                    aPositive.setBackgroundResource(android.R.drawable.btn_default);
                    abNegative.setBackgroundResource(android.R.drawable.btn_default);
                    oPositive.setBackgroundResource(android.R.drawable.btn_default);
                    oNegative.setBackgroundResource(android.R.drawable.btn_default);
                }
                bloodGroupSelection = true;
            }
        });
        abNegative = findViewById(R.id.abNegative);
        abNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: AB-");
                bloodGroup = "3";
                abNegative.setBackgroundColor(getResources().getColor(R.color.colorRED));

                if (bloodGroupSelection) {
                    aNegative.setBackgroundResource(android.R.drawable.btn_default);
                    bPositive.setBackgroundResource(android.R.drawable.btn_default);
                    bNegative.setBackgroundResource(android.R.drawable.btn_default);
                    abPositive.setBackgroundResource(android.R.drawable.btn_default);
                    aPositive.setBackgroundResource(android.R.drawable.btn_default);
                    oPositive.setBackgroundResource(android.R.drawable.btn_default);
                    oNegative.setBackgroundResource(android.R.drawable.btn_default);
                }
                bloodGroupSelection = true;
            }
        });

        oPositive = findViewById(R.id.oPositive);
        oPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: O+");
                bloodGroup = "8";
                oPositive.setBackgroundColor(getResources().getColor(R.color.colorRED));

                if (bloodGroupSelection) {
                    aNegative.setBackgroundResource(android.R.drawable.btn_default);
                    bPositive.setBackgroundResource(android.R.drawable.btn_default);
                    bNegative.setBackgroundResource(android.R.drawable.btn_default);
                    abPositive.setBackgroundResource(android.R.drawable.btn_default);
                    abNegative.setBackgroundResource(android.R.drawable.btn_default);
                    aPositive.setBackgroundResource(android.R.drawable.btn_default);
                    oNegative.setBackgroundResource(android.R.drawable.btn_default);
                }
                bloodGroupSelection = true;
            }
        });
        oNegative = findViewById(R.id.oNegative);
        oNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: O-");
                bloodGroup = "7";
                oNegative.setBackgroundColor(getResources().getColor(R.color.colorRED));

                if (bloodGroupSelection) {
                    aNegative.setBackgroundResource(android.R.drawable.btn_default);
                    bPositive.setBackgroundResource(android.R.drawable.btn_default);
                    bNegative.setBackgroundResource(android.R.drawable.btn_default);
                    abPositive.setBackgroundResource(android.R.drawable.btn_default);
                    abNegative.setBackgroundResource(android.R.drawable.btn_default);
                    oPositive.setBackgroundResource(android.R.drawable.btn_default);
                    aPositive.setBackgroundResource(android.R.drawable.btn_default);
                }
                bloodGroupSelection = true;
            }
        });


        findBlood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: FindBlood_Button: " + bloodGroup + " " + bloodQuantity.getText().toString());
                if (!TextUtils.isEmpty(bloodQuantity.getText()) && TextUtils.isDigitsOnly(bloodQuantity.getText()) && (Integer.parseInt(bloodQuantity.getText().toString())) >= 100 && bloodGroupSelection) {
                    Log.d(TAG, "onClick: FindBlood: All right.");
                    // Take blood quantity and perform action
                    if (locationManager != null) {
                        Intent bloodBankDetailActivity = new Intent(MainActivity.this, BloodBankDetailActivity.class);
                        Log.d(TAG, "onClick: \n" + bloodGroup + "  " + bloodQuantity.getText().toString());
                        bloodBankDetailActivity.putExtra("bloodGroup", bloodGroup);
                        bloodBankDetailActivity.putExtra("bloodQuantity", bloodQuantity.getText().toString());
                        //------------------------------------------------------------------------------
                        bloodGroupSelection = false;
                        bloodQuantity.setText("");
                        aPositive.setBackgroundResource(android.R.drawable.btn_default);
                        aNegative.setBackgroundResource(android.R.drawable.btn_default);
                        bPositive.setBackgroundResource(android.R.drawable.btn_default);
                        bNegative.setBackgroundResource(android.R.drawable.btn_default);
                        abPositive.setBackgroundResource(android.R.drawable.btn_default);
                        abNegative.setBackgroundResource(android.R.drawable.btn_default);
                        oPositive.setBackgroundResource(android.R.drawable.btn_default);
                        oNegative.setBackgroundResource(android.R.drawable.btn_default);

                        try {
                            // use application level context to avoid unnecessary leaks.
                            InputMethodManager inputManager = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                            assert inputManager != null;
                            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        startActivity(bloodBankDetailActivity);
                    }
                    else {

                    }
                    //------------------------------------------------------------------------------
                    //finish();
                }
                else {
                    /**
                     * Error
                     */
                    //Focus on Blood quantity correction
                    /*AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                    alertDialog.setCancelable(false);
                    alertDialog.setTitle("Not Log in")
                            .setMessage("Please Login to access cart item.")
                            .setPositiveButton("Login", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Continue with Login operation
                                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                                }
                            })
                            // A null listener allows the button to dismiss the dialog and take no further action.
                            .setNegativeButton(android.R.string.no, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();*/
                    if (!bloodGroupSelection){
                        Log.d(TAG, "onClick: FindBlood: BloodGroup not selected.");
                        TextView textViewSelectBloodGroup = findViewById(R.id.textViewSelectBloodGroup);
                        textViewSelectBloodGroup.setError("Please select blood group !");
                        textViewSelectBloodGroup.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(bloodQuantity.getText()) || Integer.parseInt(bloodQuantity.getText().toString()) < 10) {
                        Log.d(TAG, "onClick: FindBlood: Quantity invalid.");
                        bloodQuantity.setError("Please enter valid quantity !");
                        bloodQuantity.requestFocus();
                        return;
                    }
                }


            }
        });

    }

    public void locationFinder(){
        Log.d(TAG, "locationFinder: ");
            // Initialising location manager and location listener.
            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            final List<Double> list = new ArrayList<>();
            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {

                    Log.d(TAG, "onLocationChanged: " + location.getLatitude()+" "+location.getLongitude());
                    if(list.size()==0) {
                        userCurrentLongitude = location.getLongitude();
                        userCurrentLatitude = location.getLatitude();
                        list.add(userCurrentLatitude);
                        list.add(userCurrentLongitude);
                        Log.d(TAG, "onLocationChanged: constant "+userCurrentLatitude+"  "+userCurrentLongitude);

                    }

//                    Toast.makeText(MapsActivity.this, "Latitude:- "+location.getLatitude()+" Longitude:- "+location.getLongitude(), Toast.LENGTH_SHORT).show();
//                    LatLng userLocation = new LatLng(/*-34, 151*/location.getLatitude(),location.getLongitude());
//                    mMap.clear();
//                    mMap.addMarker(new MarkerOptions().position(userLocation).title("User Location"));
//                    mMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation));
                }
                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }
                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            };
        Log.d(TAG, "locationFinder: ");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Log.d(TAG, "locationFinder: Checking user permission....");
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "locationFinder: onMapReady: user permission not granted !!");
                // TODO: Consider calling
                    //    Activity#requestPermissions
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                    Log.d(TAG, "locationFinder: Permission granted successfully.");
                }
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 20, 1, locationListener);
            } else {
                Log.d(TAG, "locationFinder: build version is < M");
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 20, 1, locationListener);
            }
            // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(/*-34, 151*/27.673799, 76.699247);
//        mMap.clear();
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Chikani"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            Log.d(TAG, "locationFinder: ended.");
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 20, 1, locationListener);
                }

            }
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
