package com.example.findmyblod;

public class LocationData {
    private String mDestinationAddress;
    private String mOriginAddress;
    private String mDistanceText;
    private int mDistanceValue;
    private String mDurationText;
    private int mDurationValue;
    private String mBankName;
    private String mBankCordinates;

    public LocationData(String DestinationAddress, String OriginAddress, String DistanceText, int DistanceValue, String DurationText, int DurationValue,String BankName,String BankCordinates) {
        this.mDestinationAddress = DestinationAddress;
        this.mOriginAddress = OriginAddress;
        this.mDistanceText = DistanceText;
        this.mDistanceValue = DistanceValue;
        this.mDurationText = DurationText;
        this.mDurationValue = DurationValue;
        this.mBankName = BankName;
        this.mBankCordinates = BankCordinates;
    }

    public String getmDestinationAddress() {
        return mDestinationAddress;
    }

    public String getmOriginAddress() {
        return mOriginAddress;
    }

    public String getmDistanceText() {
        return mDistanceText;
    }

    public int getmDistanceValue() {
        return mDistanceValue;
    }

    public String getmDurationText() {
        return mDurationText;
    }

    public int getmDurationValue() {
        return mDurationValue;
    }

    public String getmBankName(){
        return mBankName;
    }
    public String getmBankCordinates(){
        return mBankCordinates;
    }

}
