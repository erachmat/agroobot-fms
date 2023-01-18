package com.example.agroobot_fms.model.get_one;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.agroobot_fms.R;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Data {

    @SerializedName("activity")
    @Expose
    private List<Activity> activity = null;
    @SerializedName("observation")
    @Expose
    private List<Observation> observation = null;
    @SerializedName("documentation")
    @Expose
    private List<Documentation> documentation = null;
    @SerializedName("rating")
    @Expose
    private List<Rating> rating = null;

    public List<Activity> getActivity() {
        return activity;
    }

    public void setActivity(List<Activity> activity) {
        this.activity = activity;
    }

    public List<Observation> getObservation() {
        return observation;
    }

    public void setObservation(List<Observation> observation) {
        this.observation = observation;
    }

    public List<Documentation> getDocumentation() {
        return documentation;
    }

    public void setDocumentation(List<Documentation> documentation) {
        this.documentation = documentation;
    }

    public List<Rating> getRating() {
        return rating;
    }

    public void setRating(List<Rating> rating) {
        this.rating = rating;
    }
}
