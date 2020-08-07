package com.example.findmyblod;

public class DonaterData {
    private String mName;
    private String mEmail;
    private String mMobileNo;
    private String mBloodGroup;
    private String mAddress;
    private int mPin;
    private String mState;
    private String mCity;
    private String mLatitude;
    private String mLongitude;

    public DonaterData(String mName, String mEmail, String mMobileNo, String mBloodGroup, String mAddress, int mPin, String mState, String mCity, String mLatitude, String mLongitude) {
        this.mName = mName;
        this.mEmail = mEmail;
        this.mMobileNo = mMobileNo;
        this.mBloodGroup = mBloodGroup;
        this.mAddress = mAddress;
        this.mPin = mPin;
        this.mState = mState;
        this.mCity = mCity;
        this.mLatitude = mLatitude;
        this.mLongitude = mLongitude;
    }

    public String getmName() {
        return mName;
    }

    public String getmEmail() {
        return mEmail;
    }

    public String getmMobileNo() {
        return mMobileNo;
    }

    public String getmBloodGroup() {
        return mBloodGroup;
    }

    public String getmAddress() {
        return mAddress;
    }

    public int getmPin() {
        return mPin;
    }

    public String getmState() {
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
