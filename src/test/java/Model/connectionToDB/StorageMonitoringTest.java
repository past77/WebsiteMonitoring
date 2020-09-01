package Model.connectionToDB;

import Model.Url;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class StorageMonitoringTest {

   private static IStorageMonitoring storageMonitoring;

    @Before
    public void setUp() {
        storageMonitoring = StorageMonitoring.getInstance();
    }

    @Test
    public void setConnectionTest() throws SQLException {
        assertTrue(storageMonitoring.isConnected());
    }
    @Test
    public void getURLTest() {
        Url url = storageMonitoring.getURL("https://www.youtube.com/");
        assertEquals("https://www.youtube.com/", url.getUrl());
    }

    @Test
    public void addOneRecordTest() throws SQLException {
        Url url = new Url("https://www.telegram.com/",
                0, 0, 0, 0, 0, 0);
        storageMonitoring.addURL(url);
        assertEquals(storageMonitoring.getURLs().size(), 5);
    }

}