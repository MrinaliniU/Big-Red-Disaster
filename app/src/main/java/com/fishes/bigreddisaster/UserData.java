package com.fishes.bigreddisaster;

import android.os.Parcel;
import android.os.Parcelable;

public class UserData implements Parcelable {

    private String uid;
    private String coOrdinates;
    private String isEmergency;

    public UserData() {
    }


    protected UserData(Parcel in) {
        this.uid = in.readString();
        this.coOrdinates = in.readString();
        this.isEmergency = in.readString();
    }

    public static final Parcelable.Creator<UserData> CREATOR = new Parcelable.Creator<UserData>() {
        @Override
        public UserData createFromParcel(Parcel source) {
            return new UserData(source);
        }

        @Override
        public UserData[] newArray(int size) {
            return new UserData[size];
        }
    };

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCoOrdinates() {
        return coOrdinates;
    }

    public void setCoOrdinates(String coOrdinates) {
        this.coOrdinates = coOrdinates;
    }

    public String getIsEmergency() {
        return isEmergency;
    }

    public void setIsEmergency(String isEmergency) {
        this.isEmergency = isEmergency;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.uid);
        dest.writeString(this.coOrdinates);
        dest.writeString(this.isEmergency);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserData note = (UserData) o;

        if (uid != null ? !uid.equals(note.uid) : note.uid != null) return false;
        if (coOrdinates != null ? !coOrdinates.equals(note.coOrdinates) : note.coOrdinates != null) return false;
        return isEmergency != null ? isEmergency.equals(note.isEmergency) : note.isEmergency == null;

    }

    @Override
    public int hashCode() {
        int result = uid != null ? uid.hashCode() : 0;
        result = 31 * result + (coOrdinates != null ? coOrdinates.hashCode() : 0);
        result = 31 * result + (isEmergency != null ? isEmergency.hashCode() : 0);
        return result;
    }

}

