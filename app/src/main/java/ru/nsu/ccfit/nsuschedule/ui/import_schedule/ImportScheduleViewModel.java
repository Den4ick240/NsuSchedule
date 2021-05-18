package ru.nsu.ccfit.nsuschedule.ui.import_schedule;

import androidx.lifecycle.ViewModel;

import java.net.MalformedURLException;
import java.net.URL;

import ru.nsu.ccfit.nsuschedule.domain.usecases.AddScheduleFromUrl;

public class ImportScheduleViewModel extends ViewModel {
    private final AddScheduleFromUrl addScheduleFromUrl;
    private final String nsuLinkForGroup;

    public ImportScheduleViewModel(AddScheduleFromUrl addScheduleFromUrl, String nsuLinkForGroup) {
        this.addScheduleFromUrl = addScheduleFromUrl;
        this.nsuLinkForGroup = nsuLinkForGroup;
    }

    public void downloadFromUrl(String url) {
        try {
            URL url1 = new URL(url);
            new Thread(() -> addScheduleFromUrl.add(url1)).start();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void downloadFromNsu(String s) {
        downloadFromUrl(nsuLinkForGroup + s);
    }
}