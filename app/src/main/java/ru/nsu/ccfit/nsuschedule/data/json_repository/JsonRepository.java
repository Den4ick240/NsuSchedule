package ru.nsu.ccfit.nsuschedule.data.json_repository;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import ru.nsu.ccfit.nsuschedule.domain.entities.Event;
import ru.nsu.ccfit.nsuschedule.domain.repository.Repository;
import ru.nsu.ccfit.nsuschedule.domain.repository.RepositoryException;

public class JsonRepository implements Repository {

    private static final String USER_EVENTS_JSON = "/userEvents.json";
    private final File userEventsFile;

    public JsonRepository(Context context) throws IOException {
        String pathname = context.getFilesDir().getPath() + USER_EVENTS_JSON;
        userEventsFile = new File(pathname);
        if (userEventsFile.createNewFile()) {
            Log.i(getClass().getName(), "New file created: " + pathname);
        }
    }

    @Override
    public List<Event> getEventsInRange(Date startDate, Date endDate) throws RepositoryException {
        EventList eventList = readEventList();
        return eventList.getEventList().stream()
                .filter(event ->
                        event.getDate().getStartDate().after(startDate) && event.getDate().getStartDate().before(endDate))
                .collect(Collectors.toList());
    }

    @Override
    public void addEvent(Event event) throws RepositoryException {
        changeEventList(eventList -> {
            eventList.addEvent(event);
            return eventList;
        });
    }

    @Override
    public void removeEvent(Event event) throws RepositoryException {
        changeEventList(eventList -> {
            eventList.remove(event);
            return eventList;
        });
    }

    private void changeEventList(UnaryOperator<EventList> function) throws RepositoryException {
        writeEventList(function.apply(readEventList()));
    }

    private EventList readEventList() throws RepositoryException {
        try (
                BufferedReader reader = new BufferedReader(new FileReader(userEventsFile))
        ) {
            EventList eventList = new Gson().fromJson(reader, EventList.class);
            if (eventList == null)
                return new EventList();
            return eventList;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RepositoryException(e.getMessage(), e.getCause());
        }
    }

    private void writeEventList(EventList eventList) throws RepositoryException {
        try (
                BufferedWriter writer = new BufferedWriter(new FileWriter(userEventsFile))
        ) {
            writer.write(new Gson().toJson(eventList));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RepositoryException(e.getMessage(), e.getCause());
        }
    }
}
