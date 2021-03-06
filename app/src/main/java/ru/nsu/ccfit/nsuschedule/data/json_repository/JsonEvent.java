package ru.nsu.ccfit.nsuschedule.data.json_repository;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.Objects;

import ru.nsu.ccfit.nsuschedule.domain.entities.Event;
import ru.nsu.ccfit.nsuschedule.domain.entities.EventDate;
import ru.nsu.ccfit.nsuschedule.domain.entities.EventInfo;
import ru.nsu.ccfit.nsuschedule.domain.entities.Repeating;

public class JsonEvent {
    @SerializedName("description")
    private String description;

    @SerializedName("summary")
    private String summary;

    @SerializedName("location")
    private String location;

    @SerializedName("repeating")
    private Repeating repeating;

    @SerializedName("startDate")
    private Date startDate;

    @SerializedName("endDate")
    private Date endDate;

    @SerializedName("repeatUntilDate")
    private Date repeatUntilDate;

    @SerializedName("notificationsOn")
    private Boolean notificationsOn;

    @SerializedName("alarmsOn")
    private Boolean alarmsOn;

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setRepeating(Repeating repeating) {
        this.repeating = repeating;
    }

    public void setNotificationsOn(Boolean notificationsOn) {
        this.notificationsOn = notificationsOn;
    }

    public void setAlarmsOn(Boolean alarmsOn) {
        this.alarmsOn = alarmsOn;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setRepeatUntilDate(Date repeatUntilDate) {
        this.repeatUntilDate = repeatUntilDate;
    }

    public JsonEvent(Event event) {
        summary = event.getInfo().getSummary();
        description = event.getInfo().getDescription();
        location = event.getInfo().getLocation();
        startDate = event.getDate().getStartDate();
        endDate = event.getDate().getEndDate();
        repeating = event.getDate().getRepeating();
        repeatUntilDate = event.getDate().getRepeatUntilDate();
        alarmsOn = event.isAlarmsOn();
        notificationsOn = event.isNotificationsOn();
    }

    public JsonEvent() {
    }

    public Event getEvent() {
        EventInfo eventInfo = new EventInfo(summary, description, location);
        EventDate eventDate = new EventDate(startDate, endDate, repeating, repeatUntilDate);
        if (alarmsOn != null && notificationsOn != null)
            return new Event(eventInfo, eventDate, notificationsOn, alarmsOn);
        else
            return new Event(eventInfo, eventDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JsonEvent jsonEvent = (JsonEvent) o;
        return description.equals(jsonEvent.description) &&
                summary.equals(jsonEvent.summary) &&
                location.equals(jsonEvent.location) &&
                repeating == jsonEvent.repeating &&
                startDate.equals(jsonEvent.startDate) &&
                endDate.equals(jsonEvent.endDate) &&
                alarmsOn.equals(jsonEvent.alarmsOn) &&
                notificationsOn.equals(jsonEvent.notificationsOn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, summary, location, repeating, startDate, endDate, alarmsOn, notificationsOn);
    }
}
