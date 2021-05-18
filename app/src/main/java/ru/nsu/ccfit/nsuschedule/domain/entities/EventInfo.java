package ru.nsu.ccfit.nsuschedule.domain.entities;

import org.jetbrains.annotations.NotNull;

public class EventInfo {

    public EventInfo(String summary, String description, String location) {
        this.summary = summary;
        this.description = description;
        this.location = location;
    }

    public EventInfo() {
    }

    private String description;
    private String summary;
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

    @NotNull
    @Override
    public String toString() {
        return "descr=" + description + " sum=" + summary + " locat=" + location;
    }
}
