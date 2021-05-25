package ru.nsu.ccfit.nsuschedule;

import android.app.Application;

import java.io.IOException;

public class ApplicationWithAppContainer extends Application {
    private AppContainer appContainer;

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            appContainer = new AppContainer(this);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public AppContainer getAppContainer() {
        return appContainer;
    }
}
