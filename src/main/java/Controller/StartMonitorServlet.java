package Controller;

import Model.*;
import Model.connectionToDB.IStorageMonitoring;
import Model.connectionToDB.StorageMonitoring;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StartMonitorServlet extends HttpServlet {
    private MonitoredUrls monitors = new MonitoredUrls();;
    private IStorageMonitoring storageMonitoring;

    public StartMonitorServlet() throws SQLException {
        storageMonitoring = StorageMonitoring.getInstance();
        storageMonitoring.getURLs().forEach(monitoredUrl -> monitors.add(new Monitor(monitoredUrl)));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean isWarning = false;
        boolean isCritical = false;
        List<Results> dataList = new ArrayList<>();
        StringStatus stringStatus = new StringStatus(Status.OK);

        monitors.forEach(Monitor::start);
        monitors.forEach(monitor -> dataList.add(monitor.getMonitoringResult()));

        for (Results data : dataList) {
            Status statusCode = null;
            Status statusTime = null;
            Status statusSize = null;
            try {
                statusCode = checkResponseCode(data);
                statusTime = checkResponseTime(data);
                statusSize = checkSize(data);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            List<Status> statuses = new ArrayList<>(Arrays.asList(statusCode, statusTime, statusSize));
            System.out.println(statuses);
            if(statuses.contains(Status.CRITICAL)){
                stringStatus.changeStatus(Status.CRITICAL);
            }else if(statuses.contains(Status.WARNING)){
                stringStatus.changeStatus(Status.WARNING);
            }else {
                stringStatus.changeStatus(Status.OK);
            }
            data.setStatus(stringStatus.getStringStatus());
            if (stringStatus.getStringStatus().equals("WARNING")) {
                isWarning = true;
            } else if (stringStatus.getStringStatus().equals("CRITICAL")) {
                System.out.println("Critical");
                isCritical = true;
            }

        }
        req.setAttribute("isWarning", isWarning);
        req.setAttribute("isCritical", isCritical);
        req.setAttribute("gatheredData", dataList);

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("monitoringTable.jsp");
        requestDispatcher.forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String urlRun = req.getParameter("buttonRun");
        String urlStop = req.getParameter("buttonStop");

        if (urlRun != null) {
            monitors.get(urlRun).startMonitoredURL();
            doGet(req, resp);
        }

        if (urlStop != null){
            monitors.get(urlStop).stopMonitoredURL();
            doGet(req, resp);
        }
    }

    private Status checkResponseCode(Results data) throws SQLException {
        Url url = storageMonitoring.getURL(data.getUrl());

        if (url.getResponseCode() == data.getResponseCode()) {
            return Status.OK;
        }
        return Status.CRITICAL;
    }

    private Status checkResponseTime(Results data) {
        synchronized (this) {
            Url url = storageMonitoring.getURL(data.getUrl());
            long currentTime = data.getResponseTime();
            long maxTime = url.getMaxResponseTime();
            long fast = maxTime / 3;

            if (currentTime <= fast) {
                return Status.OK;
            } else if (currentTime >= maxTime) {
                return Status.CRITICAL;
            }
        }
        return Status.WARNING;
    }

    private Status checkSize(Results data) {
            Url url = storageMonitoring.getURL(data.getUrl());
            int minSize = url.getMinSize();
            int maxSize = url.getMaxSize();
            int size = data.getPageSize();

            if (size >= minSize && size <= maxSize) {
                return Status.OK;
            } else if (size < 0){
                return Status.WARNING;
            }
        return Status.CRITICAL;
    }
}
