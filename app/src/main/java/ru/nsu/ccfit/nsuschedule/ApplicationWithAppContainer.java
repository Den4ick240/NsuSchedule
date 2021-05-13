package ru.nsu.ccfit.nsuschedule;

import android.app.Application;

public class ApplicationWithAppContainer extends Application {
    private AppContainer appContainer;

    @Override
    public void onCreate() {
        super.onCreate();
        appContainer = new AppContainer();
    }

    public AppContainer getAppContainer() {
        return appContainer;
    }
}
