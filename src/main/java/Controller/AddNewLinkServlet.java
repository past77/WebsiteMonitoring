package Controller;


import Model.*;
import connectionToDB.IStorageMonitoring;
import connectionToDB.StorageMonitoring;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AddNewLinkServlet extends HttpServlet {
    private IStorageMonitoring storageMonitoring;

    public AddNewLinkServlet(){
        storageMonitoring = StorageMonitoring.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getParameter("url");

        if (!isExistedUrl(url)) {
            int maxTime = Integer.valueOf(req.getParameter("maxResponseTime"));
            int minTime = Integer.valueOf(req.getParameter("minResponseTime"));
            int monitoringTimeSeconds = Integer.valueOf(req.getParameter("monitoringTimeSeconds"));
            int responseCode = Integer.valueOf(req.getParameter("responseCode"));
            int minSize = Integer.valueOf(req.getParameter("minSize"));
            int maxSize = Integer.valueOf(req.getParameter("maxSize"));

            addURL(new Url(url, minTime, maxTime, monitoringTimeSeconds, responseCode, minSize, maxSize));

            RequestDispatcher requestDispatcher = req.getRequestDispatcher("mainUrlsTable");
            requestDispatcher.forward(req, resp);
        } else {
            resp.getWriter().write(
                    "<html>" +
                            "<head></head>" +
                            "<body>" +
                            "<script>alert('Url is already exists')</script>" +
                            "</body>" +
                            "</html>");
        }
    }

    public boolean isExistedUrl(String url) {
        List<Url> monitoredURLS = null;
        try {
            monitoredURLS = storageMonitoring.getURLs();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        assert monitoredURLS != null;
        for (Url monitoredURL : monitoredURLS){
            if (monitoredURL.getUrl().equals(url)){
                return true;
            }
        }
        return false;
    }

    private void addURL(Url monitoredURL) {
        try {
            storageMonitoring.addURL(monitoredURL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

