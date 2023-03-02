package hu.vodafone.ReadWriteData;

import java.util.ArrayList;

public interface ReadWriteInt {
    String readDataFromTextFile();

    void writeDatatoTextFile(String path, String msg);

    String getPIDfromFile();

    Boolean processwithPidIsAlive(String pid);

    String readUUID();

    String getActionViewerVersion();

    String getPointerVersion();

    String getUpdateMakerVersion();
}
