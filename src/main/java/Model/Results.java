package Model;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Results {

    private String status;
    private String url;
    private long responseTime;
    private int responseCode;
    private int pageSize;
    private long monitoringTimeLeft;

    public Results(String url, long responseTime, int responseCode, int pageSize, long monitoringTimeLeft) {
        this.url = url;
        this.responseTime = responseTime;
        this.responseCode = responseCode;
        this.pageSize = pageSize;
        this.monitoringTimeLeft = monitoringTimeLeft;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
