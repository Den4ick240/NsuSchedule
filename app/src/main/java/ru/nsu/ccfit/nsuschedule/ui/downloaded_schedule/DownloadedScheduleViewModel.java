package ru.nsu.ccfit.nsuschedule.ui.downloaded_schedule;

import ru.nsu.ccfit.nsuschedule.AppContainer;
import ru.nsu.ccfit.nsuschedule.domain.repository.RepositoryException;
import ru.nsu.ccfit.nsuschedule.domain.usecases.AddAllEvents;
import ru.nsu.ccfit.nsuschedule.ui.week_schedule.WeekScheduleViewModel;

public class DownloadedScheduleViewModel extends WeekScheduleViewModel {
    private final AddAllEvents addAllEvents;

    public DownloadedScheduleViewModel(AddAllEvents addAllEvents) {
        this.addAllEvents = addAllEvents;
    }

    @Override
    public String getFlowName() {
        return AppContainer.DOWNLOADED_SCHEDULE_FLOW;
    }

    public void addAll() {
        new Thread(() -> {
            try {
                addAllEvents.add();
            } catch (RepositoryException e) {
                e.printStackTrace();
            }
        }).start();
    }
}