package hu.vodafone.Database;

public interface DatafromDb {
    Boolean hostnameIsInTheDatabase(String hostname);

    String getVersionFromDatabase();

    String getVersionofActionViewer();

    String getVersionofUpdateMaker();

    String getVersionofPointer();

    String getVersionFromPC();
}
