package hu.vodafone.actionview.actions;

public interface ActionsInterface {
    void reboot();

    void systemupdate();

    void apprestart();

    void refreshIP();

    void AppUpdate();

    void hostnamechange(String hostname);

    void runCommand(String... command);

    String runCommandReturn(String... command);
    String getUptime();
    String getMemprc();
    String getPrcprc();
    String getStrprc();
}
