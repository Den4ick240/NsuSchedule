package ru.nsu.ccfit.nsuschedule.domain.entities;

import com.google.gson.annotations.SerializedName;

public class EventInfo {

    @SerializedName("description")
    private String description;

    @SerializedName("summary")
    private String summary;

    @SerializedName("location")
    private String location;

    public String getDescription() {
        return description;
    }

    public String getSummary() {
        return summary;
    }

    public String getLocation() {
        return location;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
