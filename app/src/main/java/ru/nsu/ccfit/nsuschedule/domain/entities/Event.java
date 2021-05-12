package ru.nsu.ccfit.nsuschedule.domain.entities;

import com.google.gson.annotations.SerializedName;

public class Event {

    @SerializedName("info")
    private EventInfo info;

    @SerializedName("date")
    private EventDate date;

    public EventInfo getInfo() {
        return info;
    }

    public EventDate getDate() {
        return date;
    }

    public void setInfo(EventInfo info) {
        this.info = info;
    }

    public void setDate(EventDate date) {
        this.date = date;
    }
}
