package hu.vodafone.versionControll;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;


import java.io.*;


public class VersionControllImp {
    public void downloadUpdatemaker(String localFilename) throws IOException {
        String FILE_URL = "http://172.22.227.50/UpdateMaker-1.0-SNAPSHOT.jar";
        String FILE_NAME = "/home/pointmediauser/Vodafone/UpdateMaker-1.0-SNAPSHOT.jar";
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(FILE_URL);
        HttpResponse response = client.execute(request);
        HttpEntity entity = response.getEntity();
        int responseCode = response.getStatusLine().getStatusCode();
        InputStream inputStream = entity.getContent();
        String fileName = FILE_NAME;
        FileOutputStream fos = new FileOutputStream(fileName);
        Integer bite;
        while ((bite = inputStream.read()) != -1) {
            fos.write(bite);
        }
    }

    public void actionviewerdownloadercode(String localFilename) throws IOException {
        String FILE_URL = "http://172.22.227.50/ActionViewer-1.0-SNAPSHOT.jar";
        String FILE_NAME = "/home/pointmediauser/Vodafone/ActionViewer-1.0-SNAPSHOT.jar";
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(FILE_URL);
        HttpResponse response = client.execute(request);
        HttpEntity entity = response.getEntity();
        int responseCode = response.getStatusLine().getStatusCode();
        InputStream inputStream = entity.getContent();
        String fileName = FILE_NAME;
        FileOutputStream fos = new FileOutputStream(fileName);
        Integer bite;
        while ((bite = inputStream.read()) != -1) {
            fos.write(bite);
        }

    }


    public void pointerLiveDeviceDownloader(String localFilename) throws IOException {
        String FILE_URL = "http://172.22.227.50/PointerLiveDevice.jar";
        String FILE_NAME = "/home/pointmediauser/PointerLiveDevice/PointerLiveDevice.jar";
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(FILE_URL);
        HttpResponse response = client.execute(request);
        HttpEntity entity = response.getEntity();
        int responseCode = response.getStatusLine().getStatusCode();
        InputStream inputStream = entity.getContent();
        String fileName = FILE_NAME;
        FileOutputStream fos = new FileOutputStream(fileName);
        Integer bite;
        while ((bite = inputStream.read()) != -1) {
            fos.write(bite);
        }
    }

    public void starterScriptActionViewerDownloader(String localFilename) throws IOException {
        String FILE_URL = "http://172.22.227.50/actionviewer.sh";
        String FILE_NAME = "/home/pointmediauser/Vodafone/actionviewer.sh";
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(FILE_URL);
        HttpResponse response = client.execute(request);
        HttpEntity entity = response.getEntity();
        int responseCode = response.getStatusLine().getStatusCode();
        InputStream inputStream = entity.getContent();
        String fileName = FILE_NAME;
        FileOutputStream fos = new FileOutputStream(fileName);
        Integer bite;
        while ((bite = inputStream.read()) != -1) {
            fos.write(bite);
        }
    }

    public void starterScriptUpdateMakerDownloader(String localFilename) throws IOException {
        String FILE_URL = "http://172.22.227.50/updatemaker.sh";
        String FILE_NAME = "/home/pointmediauser/Vodafone/updatemaker.sh";
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(FILE_URL);
        HttpResponse response = client.execute(request);
        HttpEntity entity = response.getEntity();
        int responseCode = response.getStatusLine().getStatusCode();
        InputStream inputStream = entity.getContent();
        String fileName = FILE_NAME;
        FileOutputStream fos = new FileOutputStream(fileName);
        Integer bite;
        while ((bite = inputStream.read()) != -1) {
            fos.write(bite);
        }
    }

    public void PointerVersionDownloader(String localFilename) throws IOException {
        String FILE_URL = "http://172.22.227.50/Pointerversion.txt";
        String FILE_NAME = "/home/pointmediauser/Vodafone/Pointerversion.txt";
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(FILE_URL);
        HttpResponse response = client.execute(request);
        HttpEntity entity = response.getEntity();
        int responseCode = response.getStatusLine().getStatusCode();
        InputStream inputStream = entity.getContent();
        String fileName = FILE_NAME;
        FileOutputStream fos = new FileOutputStream(fileName);
        Integer bite;
        while ((bite = inputStream.read()) != -1) {
            fos.write(bite);
        }
    }

    public void ActionVersionDownloader(String localFilename) throws IOException {
        String FILE_URL = "http://172.22.227.50/actionViewerVersion.txt";
        String FILE_NAME = "/home/pointmediauser/Vodafone/actionViewerVersion.txt";
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(FILE_URL);
        HttpResponse response = client.execute(request);
        HttpEntity entity = response.getEntity();
        int responseCode = response.getStatusLine().getStatusCode();
        InputStream inputStream = entity.getContent();
        String fileName = FILE_NAME;
        FileOutputStream fos = new FileOutputStream(fileName);
        Integer bite;
        while ((bite = inputStream.read()) != -1) {
            fos.write(bite);
        }
    }

    public void UpdateMakerDownloader(String localFilename) throws IOException {
        String FILE_URL = "http://172.22.227.50/updateMakerVersion.txt";
        String FILE_NAME = "/home/pointmediauser/Vodafone/updateMakerVersion.txt";
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(FILE_URL);
        HttpResponse response = client.execute(request);
        HttpEntity entity = response.getEntity();
        int responseCode = response.getStatusLine().getStatusCode();
        InputStream inputStream = entity.getContent();
        String fileName = FILE_NAME;
        FileOutputStream fos = new FileOutputStream(fileName);
        Integer bite;
        while ((bite = inputStream.read()) != -1) {
            fos.write(bite);
        }
    }


    public void copyFileUsingApacheCommonsIO(String sourceFolder, String destination) throws IOException {
        File source = new File(sourceFolder);
        File dest = new File(destination);
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
    }/*
    public void updateDeviceAppVersion(String hostname) {
        String versionInDB = datafromDb.getVersionFromDatabase();
            UpdateAppVersion(hostname,versionInDB);
    }
    public void runApp() {
              String[] runterminal = new String[]{"/bin/sh", "/home/pointmediauser/PointerLiveDevice/reboot.sh"};
        try {
            Process process = Runtime.getRuntime().exec(runterminal);
        }catch (IOException e) {
            e.printStackTrace();
        }
        runCommand("/bin/sh","/home/pointmediauser/PointerLiveDevice/restartApp.sh");
    }
  private void UpdateAppVersion(String hostname,String versionInDB){
        String sql = "UPDATE ip_addresses SET version="+versionInDB+" where serial_number ='"+hostname+"'";
        try{
            databaseConnection.connection();
            s = databaseConnection.getConnection().createStatement();
            s.execute(sql);
        }catch (SQLException ex){
            System.out.println(ex);
        }
    }*/


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
    public void runActionViewer(){
        runCommand("java","-jar","/home/pointmediauser/Vodafone/ActionViewer-1.0-SNAPSHOT.jar");
    }

}
