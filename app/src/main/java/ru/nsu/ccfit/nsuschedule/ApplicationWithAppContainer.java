package ru.nsu.ccfit.nsuschedule;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import ru.nsu.ccfit.nsuschedule.data.json_repository.JsonRepository;
import ru.nsu.ccfit.nsuschedule.domain.entities.Repeating;
import ru.nsu.ccfit.nsuschedule.domain.repository.Repository;
import ru.nsu.ccfit.nsuschedule.domain.usecases.AddEvent;
import ru.nsu.ccfit.nsuschedule.domain.usecases.AddScheduleFromUrl;
import ru.nsu.ccfit.nsuschedule.domain.usecases.GetEventsForDay;
import ru.nsu.ccfit.nsuschedule.domain.usecases.RemoveEvent;
import ru.nsu.ccfit.nsuschedule.ui.ScheduleDayViewModel;
import ru.nsu.ccfit.nsuschedule.ui.create_event.CreateEventViewModel;
import ru.nsu.ccfit.nsuschedule.ui.import_schedule.ImportScheduleViewModel;

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

    private ViewModelProvider.Factory createFactory(Function<Class, ViewModel> factory) {
        return new ViewModelProvider.Factory() {
            @NonNull
            @NotNull
            @Override
            public <T extends ViewModel> T create(@NonNull @NotNull Class<T> modelClass) {
                return (T) factory.apply(modelClass);
            }
        };
    }

    private AppContainer createAppContainer() throws IOException {
        String filePath = getFilesDir().getPath() + "/downloadedFile.ics";
        Repository repository = new JsonRepository(this);
        ViewModelProvider.Factory scheduleDayViewModelFactory = createFactory(unused ->
                new ScheduleDayViewModel(new GetEventsForDay(repository), new RemoveEvent(repository)));
        ViewModelProvider.Factory createEventViewModelFactory = createFactory(unused ->
                new CreateEventViewModel(getRepeatingEnumTranslationMap(), new AddEvent(repository)));
        ViewModelProvider.Factory importScheduleViewModelFactory = createFactory(unused ->
                new ImportScheduleViewModel(new AddScheduleFromUrl(filePath, repository)));
        return new AppContainer(scheduleDayViewModelFactory, createEventViewModelFactory, importScheduleViewModelFactory);
    }

    private Map<String, Repeating> getRepeatingEnumTranslationMap() {
        String[] enumValues = getResources().getStringArray(R.array.repeating_enum_values);
        String[] keys = getResources().getStringArray(R.array.repeating_enum_translations);
        Map<String, Repeating> map = new HashMap<>();
        for (int i = 0; i < Math.min(enumValues.length, keys.length); i++) {
            map.put(keys[i], Repeating.valueOf(enumValues[i]));
        }
        return Collections.unmodifiableMap(map);
    }
}
