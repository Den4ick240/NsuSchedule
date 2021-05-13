package ru.nsu.ccfit.nsuschedule.data.json_repository;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ru.nsu.ccfit.nsuschedule.domain.entities.Event;

public class EventList {

    @SerializedName("eventList")
    private ArrayList<JsonEvent> eventList = new ArrayList<>();

    public List<Event> getEventList() {
        return eventList.stream().map(JsonEvent::getEvent).collect(Collectors.toList());
    }

    public void setEventList(ArrayList<JsonEvent> eventList) {
        this.eventList = eventList;
    }

    public void addEvent(Event event) {
        eventList.add(new JsonEvent(event));
    }
}
