package ru.nsu.ccfit.nsuschedule.data;

import net.fortuna.ical4j.data.ParserException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import ru.nsu.ccfit.nsuschedule.AppContainer;
import ru.nsu.ccfit.nsuschedule.data.nsu.ics.parser.NsuIcsParser;
import ru.nsu.ccfit.nsuschedule.domain.entities.Event;

public class AddScheduleFromUrl {
    private final String filePath;
    private final AppContainer appContainer;

    public AddScheduleFromUrl(String filePath, AppContainer appContainer) {
        this.filePath = filePath;
        this.appContainer = appContainer;
    }

    public void add(URL url) throws IOException, ParserException {
        LoadFileFromServerUseCase loadFileFromServerUseCase = new LoadFileFromServerUseCase();
        File file = loadFileFromServerUseCase.loadByUrl(url, filePath);
        List<Event> eventList = new NsuIcsParser().parse(file);
        appContainer.initDownloadedScheduleViewModelFactory(new CollectionRepository(eventList));
    }
}
