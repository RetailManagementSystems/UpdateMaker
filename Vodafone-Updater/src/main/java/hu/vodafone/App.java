package hu.vodafone;

import hu.vodafone.Database.*;
import hu.vodafone.ReadWriteData.ReadWriteImp;
import hu.vodafone.ReadWriteData.ReadWriteInt;
import hu.vodafone.versionControll.VersionControllImp;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;

public class App {
    public static void main(String[] args) {
        try {
            DatabaseManipulationInterface databaseManipulationInterface = new DatabaseManipulationImplementation();
            DatabaseConnection databaseConnection = new DatabaseConnection();
            DatafromDb datafromDb = new DatafromDbImp();
            ReadWriteInt readWriteInt = new ReadWriteImp();
            VersionControllImp versionControllImp = new VersionControllImp();
            String hostname = "";
            versionControllImp.runCommand("./githubget.sh");

            try {
                hostname = InetAddress.getLocalHost().getHostName();
                databaseManipulationInterface.updateLastRun(hostname);
            }
            catch (UnknownHostException ex) {
                System.out.println(ex);
            }
            try {
                databaseConnection.connection(readWriteInt.getPIDfromFile());
                System.out.println(hostname);
                if (!hostname.equals("pointmediauser-Gemini-T34")) {
                    if (datafromDb.hostnameIsInTheDatabase(hostname) == true) {
                        if (!datafromDb.getVersionofActionViewer().equals(readWriteInt.getActionViewerVersion())) {
                            try {
                                versionControllImp.actionviewerdownloadercode("ActionViewer-1.0-SNAPSHOT.jar");
                                versionControllImp.starterScriptActionViewerDownloader("actionviewer.sh");
                                versionControllImp.ActionVersionDownloader("actionViewerVersion.txt");
                                readWriteInt.writeDatatoTextFile("/home/pointmediauser/Vodafone/actionViewerVersion.txt", datafromDb.getVersionofActionViewer());

                            }
                            catch (Exception ex) {
                                System.out.println(ex);
                            }
                        }
                        else if (!datafromDb.getVersionofUpdateMaker().equals(readWriteInt.getUpdateMakerVersion())) {
                            try {
                                versionControllImp.downloadUpdatemaker("UpdateMaker-1.0-SNAPSHOT.jar");
                                versionControllImp.starterScriptUpdateMakerDownloader("updatemaker.sh");
                                versionControllImp.ActionVersionDownloader("updateMakerVersion.txt");
                                readWriteInt.writeDatatoTextFile("/home/pointmediauser/Vodafone/updateMakerVersion.txt", datafromDb.getVersionofActionViewer());

                            }
                            catch (Exception ex) {
                                System.out.println(ex);
                            }
                        }
                        else if (!datafromDb.getVersionofPointer().equals(readWriteInt.getPointerVersion())) {
                            try {
                                versionControllImp.downloadUpdatemaker("PointerLiveDevice.jar");
                                versionControllImp.PointerVersionDownloader("Pointer.txt");
                                readWriteInt.writeDatatoTextFile("/home/pointmediauser/Vodafone/Pointerversion.txt", datafromDb.getVersionofPointer());
                            }
                            catch (Exception ex) {
                                System.out.println(ex);
                            }

                        }
                        else {
                            System.out.println("Minden up to date");
                        }
                        System.out.println("FUTTATÁS INDUL");
                        databaseConnection.getConnection().close();
                    }
                    else {
                        databaseConnection.getConnection().close();

                    }
                }
            }
            catch (SQLException e) {
                System.out.println(e);
            } finally {
                versionControllImp.runActionViewer();
                Thread.sleep(200000);
                System.exit(2);
            }
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}


