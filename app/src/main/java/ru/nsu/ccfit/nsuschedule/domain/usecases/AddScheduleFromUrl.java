package ru.nsu.ccfit.nsuschedule.domain.usecases;

import net.fortuna.ical4j.data.ParserException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import ru.nsu.ccfit.nsuschedule.data.nsu.ics.parser.NsuIcsParser;
import ru.nsu.ccfit.nsuschedule.domain.entities.Event;
import ru.nsu.ccfit.nsuschedule.domain.repository.Repository;
import ru.nsu.ccfit.nsuschedule.domain.repository.RepositoryException;

public class AddScheduleFromUrl {
    private final String filePath;
    private final Repository repository;

    public AddScheduleFromUrl(String filePath, Repository repository) {
        this.filePath = filePath;
        this.repository = repository;
    }

    public void add(URL url) {
        LoadFileFromServerUseCase loadFileFromServerUseCase = new LoadFileFromServerUseCase();
        try {
            File file = loadFileFromServerUseCase.loadByUrl(url, filePath);
            List<Event> eventList = new NsuIcsParser().parse(file);
            repository.addEvents(eventList);
        } catch (IOException | ParserException | RepositoryException e) {
            e.printStackTrace();
        }
    }
}
