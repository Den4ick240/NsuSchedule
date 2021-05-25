package ru.nsu.ccfit.nsuschedule.ui.downloaded_schedule;

import ru.nsu.ccfit.nsuschedule.AppContainer;
import ru.nsu.ccfit.nsuschedule.ui.week_schedule.WeekScheduleViewModel;

public class DownloadedScheduleViewModel extends WeekScheduleViewModel {
    @Override
    public String getFlowName() {
        return AppContainer.DOWNLOADED_SCHEDULE_FLOW;
    }

    public void addAll() {
//TODO
    }
}