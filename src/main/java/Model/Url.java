package Model;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class Url {
    private String url;
    private int minResponseTime;
    private int maxResponseTime;
    private int monitoringTimeSeconds;
    private int responseCode;
    private int minSize;
    private int maxSize;
    private boolean isStopped = false;
    private long beginningTime;

    public Url(String url) {
        this.url = url;
    }

    public Url(String url, int minResponseTime, int maxResponseTime, int monitoringTimeSeconds,
               int responseCode, int minSize, int maxSize){
        this.url = url;
        this.minResponseTime = minResponseTime;
        this.maxResponseTime = maxResponseTime;
        this.monitoringTimeSeconds = monitoringTimeSeconds;
        this.responseCode = responseCode;
        this.minSize = minSize;
        this.maxSize = maxSize;
    }
}
