package ru.nsu.ccfit.nsuschedule.domain.usecases;

import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class LoadFileFromServerUseCase {

    public File loadByUrl(URL url, String fileDestinationPath) throws IOException {
        File result = new File(fileDestinationPath);
        try (ReadableByteChannel rbc = Channels.newChannel(url.openConnection().getInputStream());
             FileOutputStream nsuScheduleFOS = new FileOutputStream(result)
        ) {
            if (result.createNewFile())
                Log.i("FileLoader", "File for downloading created: " + fileDestinationPath);
            nsuScheduleFOS.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        }
        return result;
    }
}
