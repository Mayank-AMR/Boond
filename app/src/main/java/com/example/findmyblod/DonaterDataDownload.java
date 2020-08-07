package com.example.findmyblod;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DonaterDataDownload extends AsyncTask<Void,Void,String > {
    private static final String TAG = "DonaterDataDownload";
    private String mCity;
    private int mBloodGroup;


    DonaterDataDownload(String city, int bloodGroup){
        this.mCity = city;
        this.mBloodGroup = bloodGroup;
    }

    @Override
    protected String doInBackground(Void... voids) {
        Log.d(TAG, "doInBackground: ");
        String data = "";
        //Toast.makeText(context, "Background", Toast.LENGTH_SHORT).show();
        //TODO: open http connection
        try {
            URL url = new URL("http://boond.pythonanywhere.com/users/results/group/users?city=Alwar&group=2");
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
        return data;
    }

    @Override
    protected void onPostExecute(String s) {
        Log.d(TAG, "onPostExecute: ");
        super.onPostExecute(s);
        //Log.d(TAG, "onPostExecute: "+s);
//        try {
//            JSONObject jsonObject = new JSONObject(s);
//            JSONArray destination_addresses = jsonObject.getJSONArray("destination_addresses");
//            //Log.d(TAG, "onPostExecute:  "+destination_addresses);
//
////            String origin_addresses = jsonObject.getString("origin_addresses");
////            JSONArray rows = new JSONArray(jsonObject.getString("rows"));
//
////            Log.d(TAG, "onPostExecute: "+rows);
//
//
//
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }
}
