package ru.nsu.ccfit.nsuschedule;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import ru.nsu.ccfit.nsuschedule.data.json_repository.JsonRepository;
import ru.nsu.ccfit.nsuschedule.domain.repository.Repository;
import ru.nsu.ccfit.nsuschedule.domain.usecases.AddEvent;
import ru.nsu.ccfit.nsuschedule.domain.usecases.GetEventsForDay;
import ru.nsu.ccfit.nsuschedule.domain.usecases.RemoveEvent;
import ru.nsu.ccfit.nsuschedule.ui.ScheduleDayViewModel;
import ru.nsu.ccfit.nsuschedule.ui.create_event.CreateEventViewModel;

public class ApplicationWithAppContainer extends Application {
    private AppContainer appContainer;

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            appContainer = createAppContainer();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public AppContainer getAppContainer() {
        return appContainer;
    }

    private AppContainer createAppContainer() throws IOException {
        Repository repository = new JsonRepository(this);
        ViewModelProvider.Factory scheduleDayViewModelFactory =
                new ViewModelProvider.Factory() {
                    @NotNull
                    @Override
                    public <T extends ViewModel> T create(@NotNull Class<T> modelClass) {
                        return (T) new ScheduleDayViewModel(new GetEventsForDay(repository), new RemoveEvent(repository));
                    }
                };
        ViewModelProvider.Factory createEventViewModelFactory =
                new ViewModelProvider.Factory() {
                    @NotNull
                    @Override
                    public <T extends ViewModel> T create(@NotNull Class<T> modelClass) {
                        return (T) new CreateEventViewModel(new AddEvent(repository));
                    }
                };
        return new AppContainer(scheduleDayViewModelFactory, createEventViewModelFactory);
    }
}
