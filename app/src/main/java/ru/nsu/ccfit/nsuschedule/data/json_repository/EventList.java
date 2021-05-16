package ru.nsu.ccfit.nsuschedule.data.json_repository;

import com.google.gson.annotations.SerializedName;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import ru.nsu.ccfit.nsuschedule.domain.entities.Event;

public class EventList {

    @SerializedName("eventList")
    private Set<JsonEvent> set = new HashSet<>();

    public List<Event> getEventList() {
        return set.stream().map(JsonEvent::getEvent).collect(Collectors.toList());
    }

    public void addEvent(Event event) {
        set.add(new JsonEvent(event));
    }

    public void remove(Event event) {
        set.remove(new JsonEvent(event));
    }
}
