package Controller;

import Model.connectionToDB.IStorageMonitoring;
import Model.connectionToDB.StorageMonitoring;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MainServletTest {
    IStorageMonitoring st;
    private HttpServletRequest req;
    private HttpServletResponse resp;
    private MainServlet mainServlet;


    @Before
    public void setUp() throws Exception {
        st= mock(StorageMonitoring.class);
        mainServlet = mock(MainServlet.class);
        req = mock(HttpServletRequest.class);
        resp = mock(HttpServletResponse.class);
    }

    @Test
    public void shouldGetTrueCountRecords() throws ServletException, IOException, SQLException {
        when(st.getCount()).thenReturn(4);

        mainServlet.doGet(req, resp);
        assertEquals(4, st.getCount());
    }
}