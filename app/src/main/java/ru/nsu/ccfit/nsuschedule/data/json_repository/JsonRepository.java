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
import java.util.Optional;

import ru.nsu.ccfit.nsuschedule.data.AbstractRepository;
import ru.nsu.ccfit.nsuschedule.domain.repository.RepositoryException;

public class JsonRepository extends AbstractRepository {

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
    protected EventList readEventList() throws RepositoryException {
        try (
                BufferedReader reader = new BufferedReader(new FileReader(userEventsFile))
        ) {
            return Optional.ofNullable(new Gson().fromJson(reader, EventList.class))
                    .orElse(new EventList());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RepositoryException(e.getMessage(), e.getCause());
        }
    }

    @Override
    protected void writeEventList(EventList eventList) throws RepositoryException {
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
