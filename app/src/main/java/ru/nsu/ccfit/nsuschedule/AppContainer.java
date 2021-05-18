package ru.nsu.ccfit.nsuschedule;

import androidx.lifecycle.ViewModelProvider;

public class AppContainer {
    public final ViewModelProvider.Factory scheduleDayViewModelFactory;
    public final ViewModelProvider.Factory createEventViewModelFactory;
    public final ViewModelProvider.Factory importScheduleViewModelFactory;

    public AppContainer(ViewModelProvider.Factory scheduleDayViewModelFactory,
                        ViewModelProvider.Factory createEventViewModelFactory, ViewModelProvider.Factory importScheduleViewModelFactory) {
        this.scheduleDayViewModelFactory = scheduleDayViewModelFactory;
        this.createEventViewModelFactory = createEventViewModelFactory;
        this.importScheduleViewModelFactory = importScheduleViewModelFactory;
    }

}
