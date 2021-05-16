package ru.nsu.ccfit.nsuschedule.domain.usecases;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class LoadFileFromServerUseCase {

    public File loadByUrl(URL url, String fileDestinationPath) throws IOException {
        File result = new File(fileDestinationPath);
        ReadableByteChannel rbc = Channels.newChannel(url.openConnection().getInputStream());
        FileOutputStream nsuScheduleFOS = new FileOutputStream(result);
        nsuScheduleFOS.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);

        nsuScheduleFOS.close();
        rbc.close();
        return result;
    }
}
