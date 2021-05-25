package ru.nsu.ccfit.nsuschedule.ui.import_schedule;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import net.fortuna.ical4j.data.ParserException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import ru.nsu.ccfit.nsuschedule.domain.usecases.AddScheduleFromUrl;

public class ImportScheduleViewModel extends ViewModel {
    private final AddScheduleFromUrl addScheduleFromUrl;
    private final String nsuLinkForGroup;
    private final MutableLiveData<Void> scheduleReady = new MutableLiveData<>();

    public ImportScheduleViewModel(AddScheduleFromUrl addScheduleFromUrl, String nsuLinkForGroup) {
        this.addScheduleFromUrl = addScheduleFromUrl;
        this.nsuLinkForGroup = nsuLinkForGroup;
    }

    public void downloadFromUrl(String url) {
        try {
            URL url1 = new URL(url);
            new Thread(() -> {
                try {
                    addScheduleFromUrl.add(url1);
                    scheduleReady.postValue(null);
                } catch (IOException | ParserException e) {
                    e.printStackTrace();
                }
            }
            ).start();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void downloadFromNsu(String s) {
        downloadFromUrl(nsuLinkForGroup + s);
    }

    public LiveData<Void> getScheduleReady() {
        return scheduleReady;
    }
}