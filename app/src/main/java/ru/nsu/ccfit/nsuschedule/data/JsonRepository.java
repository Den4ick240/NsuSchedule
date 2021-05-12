package ru.nsu.ccfit.nsuschedule.data;

import android.content.Context;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ru.nsu.ccfit.nsuschedule.domain.Repository;
import ru.nsu.ccfit.nsuschedule.domain.entities.Event;
import ru.nsu.ccfit.nsuschedule.domain.entities.EventList;

public class JsonRepository implements Repository {

    private final static String userEventsFileName = "/userEvents.json";
    private final File userEventsFile;

    public JsonRepository(Context context) throws IOException {
        userEventsFile = new File(context.getFilesDir().getPath() + userEventsFileName);
        if (!userEventsFile.exists()) {
            userEventsFile.createNewFile();
        }
    }

    @Override
    public List<Event> getEventsInRange(Date startDate, Date endDate) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(userEventsFile));

        Gson gson = new Gson();
        EventList eventList = gson.fromJson(reader, EventList.class);
        reader.close();
        return eventList.getEventList();
    }

    @Override
    public void addEvent(Event event) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(userEventsFile));

        Gson gson = new Gson();
        EventList eventList = gson.fromJson(reader, EventList.class);
        reader.close();
        if (eventList == null) {
            eventList = new EventList();
        }
        eventList.addEvent(event);
        String string = gson.toJson(eventList);

        BufferedWriter writer = new BufferedWriter(new FileWriter(userEventsFile));
        writer.write(string);
        writer.close();
    }
}
