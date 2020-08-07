package com.example.findmyblod;

public class BloodBank {
    private String mName;
    private String mAddress;
    private int mPincode;
    private int mState;
    private String mCity;
    private String mLatitude;
    private String mLongitude;

    public BloodBank(String mName, String mAddress, int mPincode, int mState, String mCity, String mLatitude, String mLongitude) {
        this.mName = mName;
        this.mAddress = mAddress;
        this.mPincode = mPincode;
        this.mState = mState;
        this.mCity = mCity;
        this.mLatitude = mLatitude;
        this.mLongitude = mLongitude;
    }

    public String getmName() {
        return mName;
    }

    public String getmAddress() {
        return mAddress;
    }

    public int getmPincode() {
        return mPincode;
    }

    public int getmState() {
        return mState;
    }

    public String getmCity() {
        return mCity;
    }

    public String getmLatitude() {
        return mLatitude;
    }

    public String getmLongitude() {
        return mLongitude;
    }
}
