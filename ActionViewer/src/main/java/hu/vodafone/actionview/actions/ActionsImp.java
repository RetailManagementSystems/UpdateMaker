package hu.vodafone.actionview.actions;

import hu.vodafone.actionview.database.DbConnection;
import hu.vodafone.actionview.readWrite.ReadWrite;
import org.asynchttpclient.*;

import java.io.*;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.concurrent.ExecutionException;

public class ActionsImp implements ActionsInterface {
    DbConnection dbConnection = new DbConnection();
    ReadWrite readWrite = new ReadWrite();
    Statement s = null;

    @Override
    public void reboot() {
        runCommand("reboot");
    }

    @Override
    public void systemupdate() {
        String[] runterminal = new String[]{"/bin/sh", "/home/pointmediauser/PointerLiveDevice/systemUpdate.sh"};
        try {
            Process process = Runtime.getRuntime().exec(runterminal);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void apprestart() {
        String[] runterminal = new String[]{"/bin/sh", "/home/pointmediauser/PointerLiveDevice/reboot.sh"};
        try {
            Process process = Runtime.getRuntime().exec(runterminal);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void refreshIP() {
        String ip = getIP();
        String sql = "UPDATE ip_addresses SET ip ='" + getIP() + "' where serial ='" + serialnum() + "'";
        try {
            dbConnection.connection(readWrite.getPIDfromFile());
            s = dbConnection.getConnection().createStatement();
            s.execute(sql);
        }
        catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public String serialnum() {
        String serial = "";

        return serial;
    }

    @Override
    public void AppUpdate() {
        String[] runterminal = new String[]{"/bin/sh", "/home/pointmediauser/PointerLiveDevice/appUpdate.sh"};
        try {
            Process process = Runtime.getRuntime().exec(runterminal);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void hostnamechange(String hostname) {
        runCommand("hostnamectl", "set-hostname", hostname);
    }

    public void copyFileUsingApacheCommonsIO() throws IOException {
        File source = new File("/home/pointmediauser/updater/PointmediaLiveDevice.jar");
        File dest = new File("/home/pointmediauser/PointerLiveDevice/PointmediaLiveDevice.jar");
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = is.read(buf)) > 0) {
                os.write(buf, 0, bytesRead);
            }
        } finally {
            is.close();
            os.close();
        }
    }

    public void DownloadNewerVersion(String localFilename) throws ExecutionException, InterruptedException, IOException {
        final FileOutputStream stream = new FileOutputStream(localFilename);
        AsyncHttpClient client = Dsl.asyncHttpClient();
        String url = "http://192.168.0.207/PointmediaLiveDevice.jar";
        client.prepareGet(url)
                .execute(new AsyncCompletionHandler<FileOutputStream>() {

                    @Override
                    public State onBodyPartReceived(HttpResponseBodyPart bodyPart) throws Exception {
                        stream.getChannel()
                                .write(bodyPart.getBodyByteBuffer());
                        return State.CONTINUE;
                    }

                    @Override
                    public FileOutputStream onCompleted(Response response) throws Exception {
                        copyFileUsingApacheCommonsIO();
                        System.out.println("KÉSZ");
                        return stream;
                    }
                })
                .get();

        stream.getChannel().close();
        client.close();
    }

    public String getIP() {
        String ip = "";
        NetworkInterface ni = null;
        try {
            ni = NetworkInterface.getByName("wlp2s0");
        }
        catch (SocketException e) {
            e.printStackTrace();
        }
        Enumeration<InetAddress> inetAddresses = ni.getInetAddresses();


        while (inetAddresses.hasMoreElements()) {
            InetAddress ia = inetAddresses.nextElement();
            if (!ia.isLinkLocalAddress()) {
                ip = ia.getHostAddress();
                // System.out.println("IP: " + ia.getHostAddress());
            }
        }
        return ip;
    }

    @Override
    public void runCommand(String... command) {
        ProcessBuilder processBuilder = new ProcessBuilder().command(command);
        try {
            Process process = processBuilder.start();
            //read the output
            InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String output = null;
            while ((output = bufferedReader.readLine()) != null) {
                System.out.println(output);
            }
            //wait for the process to complete
            process.waitFor();
            //close the resources
            bufferedReader.close();
            process.destroy();
        }
        catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String runCommandReturn(String... command) {
        String returnData = "";
        ProcessBuilder processBuilder = new ProcessBuilder().command(command);
        try {
            Process process = processBuilder.start();
            //read the output
            InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String output = null;
            while ((output = bufferedReader.readLine()) != null) {
                System.out.println(output);
                returnData = output;
            }
            //wait for the process to complete
            process.waitFor();
            //close the resources
            bufferedReader.close();
            process.destroy();
        }
        catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return returnData;
    }

    @Override
    public String getUptime() {
        String[] command = {"/bin/sh", "/home/pointmediauser/Vodafone/getuptime.sh" };
        String output = "";
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    process.getInputStream()));
            String s;
            while ((s = reader.readLine()) != null) {
                System.out.println("Script output: " + s);
                output = s;
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
       return output;
    }

    @Override
    public String getMemprc() {

        String[] command = {"/bin/sh", "/home/pointmediauser/Vodafone/memoryusage.sh" };
        String output = "";
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    process.getInputStream()));
            String s;
            while ((s = reader.readLine()) != null) {
                System.out.println("Script output: " + s);
                output = s;
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        return output;
    }

    @Override
    public String getPrcprc() {
        String[] command = {"/bin/sh", "/home/pointmediauser/Vodafone/proc.sh" };
        String output = "";
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    process.getInputStream()));
            String s;
            while ((s = reader.readLine()) != null) {
                System.out.println("Script output: " + s);
                output = s;
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        return output;
    }

    @Override
    public String getStrprc() {
        String[] command = {"/bin/sh", "/home/pointmediauser/Vodafone/storageproc.sh" };
        String output = "";
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    process.getInputStream()));
            String s;
            while ((s = reader.readLine()) != null) {
                System.out.println("Tárhely: " + s);
                output =s;
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        return output;
    }
}

