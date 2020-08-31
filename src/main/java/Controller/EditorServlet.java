package Controller;

import Model.*;
import connectionToDB.IStorageMonitoring;
import connectionToDB.StorageMonitoring;
import lombok.SneakyThrows;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class EditorServlet extends HttpServlet {
    private IStorageMonitoring storageMonitoring = StorageMonitoring.getInstance();

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getParameter("url");
        Url monitoredURL = storageMonitoring.getURL(url);

        int maxTime = Integer.valueOf(req.getParameter("maxResponseTime"));
        int minTime = Integer.valueOf(req.getParameter("minResponseTime"));
        int monitoringTimeSeconds = Integer.valueOf(req.getParameter("monitoringTimeSeconds"));
        int responseCode = Integer.valueOf(req.getParameter("responseCode"));
        int minSize = Integer.valueOf(req.getParameter("minSize"));
        int maxSize = Integer.valueOf(req.getParameter("maxSize"));

        req.setAttribute("monitoredURL", monitoredURL);
        req.setAttribute("url", url);

        if (monitoredURL != null){
            if (maxTime != monitoredURL.getMaxResponseTime()){
                updateMaxResponseTime(url, maxTime);
            }
            if (minTime != monitoredURL.getMinResponseTime()){
                updateMinResponseTime(url, minTime);
            }
            if (monitoringTimeSeconds != monitoredURL.getMonitoringTimeSeconds()){
                updateMonitoringTime(url, monitoringTimeSeconds);
            }
            if (responseCode != monitoredURL.getResponseCode()){
                updateResponseCode(url, responseCode);
            }
            if (minSize != monitoredURL.getMinSize()){
                updateMinSize(url, minSize);
            }
            if (maxSize != monitoredURL.getMaxSize()){
                updateMaxSize(url, maxSize);
            }
        }

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("mainUrlsTable");
        requestDispatcher.forward(req, resp);
    }

    private void updateMinResponseTime(String targetUrl, int updatedMinResponseTime) throws SQLException {
        storageMonitoring.updateMinResponseTime(targetUrl, updatedMinResponseTime);
    }

    private void updateMaxResponseTime(String targetUrl, int updatedMaxResponseTime) throws SQLException {
        storageMonitoring.updateMaxResponseTime(targetUrl, updatedMaxResponseTime);
    }

    private  void updateMonitoringTime(String targetUrl, int updateMonitoringTime) throws SQLException {
        storageMonitoring.updateMonitoringTime(targetUrl, updateMonitoringTime);
    }

    private  void updateResponseCode(String targetUrl, int updatedResponseCode) throws SQLException {
        storageMonitoring.updateResponseCode(targetUrl, updatedResponseCode);
    }

    private void updateMinSize(String targetUrl, int updateMinSize) throws SQLException {
        storageMonitoring.updateMinSize(targetUrl, updateMinSize);
    }

    private void updateMaxSize(String targetUrl, int updateMaxSize) throws SQLException {
        storageMonitoring.updateMaxSize(targetUrl, updateMaxSize);
    }
}
