package com.example.findmyblod;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class GoogleMatrixDataDownload extends AsyncTask<Void, Void, String> {
    private static final String TAG = "CartDataDownloadFromAPI";
    //public static final String API_Key = "API Key goes here";
    //Todo: You need to get Google API KEY to download data from Google

    private String mDestinationLatitude;
    private String mDestinationLongitude;


    GoogleMatrixDataDownload(String destinationLatitude, String destinationLongitude){
        this.mDestinationLatitude = destinationLatitude;
        this.mDestinationLongitude = destinationLongitude;
    }

    @Override
    protected String doInBackground(Void... voids) {
        String data = "";
        //Toast.makeText(context, "Background", Toast.LENGTH_SHORT).show();
        //TODO: open http connection
        try {
            URL url = new URL("https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins="+MainActivity.userCurrentLatitude+","+MainActivity.userCurrentLongitude+"&destinations="+mDestinationLatitude+","+mDestinationLongitude+"&key="+ API_Key);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestProperty("key","AIzaSyBVHC0B-6mFNuMk0lBnOmc-JIJojPxKc2Q");
            httpURLConnection.setConnectTimeout(20000);
            httpURLConnection.setReadTimeout(20000);
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bufferedReader.readLine()) != null)
                data = data+line;

            inputStream.close();
            bufferedReader.close();
            httpURLConnection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Log.d(TAG, "doInBackground: Data"+data);
        return data;
    }

    @Override
    protected void onPostExecute(String s) {
        Log.d(TAG, "onPostExecute: ");
        super.onPostExecute(s);
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray destination_addresses = jsonObject.getJSONArray("destination_addresses");
            //Log.d(TAG, "onPostExecute:  "+destination_addresses);

//            String origin_addresses = jsonObject.getString("origin_addresses");
//            JSONArray rows = new JSONArray(jsonObject.getString("rows"));

//            Log.d(TAG, "onPostExecute: "+rows);




        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
