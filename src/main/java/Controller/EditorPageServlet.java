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

public class EditorPageServlet extends HttpServlet {
    private IStorageMonitoring storageMonitoring;

    public EditorPageServlet(){
        storageMonitoring = StorageMonitoring.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getParameter("url");
        Url monitoredURL = null;
        monitoredURL = storageMonitoring.getURL(url);

        req.setAttribute("monitoredURL", monitoredURL);
        req.setAttribute("url", url);

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("editorPage.jsp");
        requestDispatcher.forward(req, resp);
    }
}