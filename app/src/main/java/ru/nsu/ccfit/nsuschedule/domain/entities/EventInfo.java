package ru.nsu.ccfit.nsuschedule.domain.entities;

public class EventInfo {
    private final String description;
    private final String summary;
    private final String location;

    public EventInfo(String description, String summary, String location) {
        this.description = description;
        this.summary = summary;
        this.location = location;
    }

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
