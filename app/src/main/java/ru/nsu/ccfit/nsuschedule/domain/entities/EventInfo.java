package ru.nsu.ccfit.nsuschedule.domain.entities;

public class EventInfo {

    public EventInfo(String summary, String description, String location) {
        this.summary = summary;
        this.description = description;
        this.location = location;
    }

    private final String description;
    private final String summary;
    private final String location;

    public String getDescription() {
        return description;
    }

    public String getSummary() {
        return summary;
    }

    public String getLocation() {
        return location;
    }
}
