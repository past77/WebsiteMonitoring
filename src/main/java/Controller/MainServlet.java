package Controller;

import Model.Url;
import connectionToDB.IStorageMonitoring;
import connectionToDB.StorageMonitoring;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainServlet extends HttpServlet {
    private IStorageMonitoring storageMonitoring;
    private List<Url> allURLS = new ArrayList<>();

    public MainServlet(){
        storageMonitoring = StorageMonitoring.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        synchronized (this) {
            allURLS.clear();
            try {
                allURLS = storageMonitoring.getURLs();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        req.setAttribute("monitoredURLS", allURLS);

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("mainUrlsTable.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
