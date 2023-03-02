package hu.vodafone.Database;

public interface DatabaseManipulationInterface {
    void insertDeviceIPandHostname(String hostname, String serial, String version);

    void updateDeviceIP(String hostname);
    void updateLastRun(String hostname);
    String getIP();
}
