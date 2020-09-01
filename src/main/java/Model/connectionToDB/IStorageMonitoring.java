package Model.connectionToDB;

import Model.Url;

import java.sql.SQLException;
import java.util.List;

public interface IStorageMonitoring {

    public abstract Url getURL(String url);

    public abstract List<Url> getURLs() throws SQLException;

    public abstract void addURL(Url monitoredURL) throws SQLException;

    public abstract void removeURL(String url) throws SQLException;

    public abstract void updateMinResponseTime(String targetUrl, int updatedMinResponseTime) throws SQLException;

    public abstract void updateMaxResponseTime(String targetUrl, int updatedMaxResponseTime) throws SQLException;

    public abstract void updateMonitoringTime(String targetUrl, int updateMonitoringTime) throws SQLException;

    public abstract void updateResponseCode(String targetUrl, int updatedResponseCode) throws SQLException;

    public abstract void updateMinSize(String targetUrl, int updateMinSize) throws SQLException;

    public abstract void updateMaxSize(String targetUrl, int updateMaxSize) throws SQLException;
}
