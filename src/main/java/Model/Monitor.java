package Model;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.CountingInputStream;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class Monitor {
    private Url monUrl;
    private Results result;
    private HttpURLConnection connection;
    private long beginningTime;

    public Monitor(Url mon_url) {
        if (mon_url != null) {
            this.monUrl = mon_url;
        } else {
            throw new RuntimeException("No url to update");
        }
        beginningTime = System.currentTimeMillis();
    }

    public void start() {
        try {
            connection = (HttpURLConnection) new URL(monUrl.getUrl()).openConnection();
            connection.setRequestProperty("Accept-Encoding", "identity");
        } catch (IOException e) {
            e.printStackTrace();
        }
        long monitoringLeftTime = getMonitoringTimeLeft();

        if (monUrl.isStopped()) {
            return;
        }

        if (monitoringLeftTime < 0){
            result.setMonitoringTimeLeft(0);
            return;
        }
        else {
            result = new Results(monUrl.getUrl(),
                    getCurrentResponseTime(),
                    getCurrentResponseCode(),
                    (int) getCurrentSize(),
                    monitoringLeftTime);
        }
    }

    private long getCurrentResponseTime() {
        try {
            System.out.println(result);
            long startTime = System.currentTimeMillis();
            long responseTime;
            connection.connect();

            responseTime = System.currentTimeMillis() - startTime;

            return responseTime;
        } catch (IOException e){
            e.printStackTrace();
        }
        throw new RuntimeException("Response time is absent");
    }
    private long getCurrentSize() {
        //return 1;
        CountingInputStream counter = null;
        try {
            counter = new CountingInputStream(connection.getInputStream());
            String output = IOUtils.toString(counter);
            return counter.getByteCount();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

    }

    private int getCurrentResponseCode(){
        try {
            return connection.getResponseCode();
        } catch (IOException e){
            e.printStackTrace();
        }
        throw new RuntimeException("Response code is absent");
    }

    private long getMonitoringTimeLeft(){
        long monitoringTimeMillis = monUrl.getMonitoringTimeSeconds() * 1000;
        long deltaTime = System.currentTimeMillis() - beginningTime;

        return monitoringTimeMillis - deltaTime;
    }

    public void stopMonitoredURL(){
        monUrl.setStopped(true);
        monUrl.setMonitoringTimeSeconds(Math.toIntExact((result.getMonitoringTimeLeft() / 1000)));
    }

    public void startMonitoredURL() {
        monUrl.setStopped(false);
        setBeginningTime(System.currentTimeMillis());
    }

    public void setBeginningTime(long beginningTime) {
        this.beginningTime = beginningTime;
    }

    public Url getMonitoredURL() {
        return monUrl;
    }

    public Results getMonitoringResult() {
        return result;
    }
}