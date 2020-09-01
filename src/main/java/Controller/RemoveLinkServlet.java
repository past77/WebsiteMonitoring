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

public class RemoveLinkServlet extends HttpServlet {
    private IStorageMonitoring storageMonitoring;

    public RemoveLinkServlet(){
        storageMonitoring = StorageMonitoring.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getParameter("url");
        Url monitoredURL = null;
        try {
            monitoredURL = storageMonitoring.getURL(url);

            if (monitoredURL != null){
                removeLink(url);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("mainUrlsTable");
        requestDispatcher.forward(req, resp);
    }

    private void removeLink(String url) throws SQLException {
        storageMonitoring.removeURL(url);
    }
}
