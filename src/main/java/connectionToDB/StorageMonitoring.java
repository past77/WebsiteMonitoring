package connectionToDB;

import Model.Url;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StorageMonitoring implements IStorageMonitoring {

    private static Connection connection;
    private final String DATA_BASE_URL = "jdbc:postgresql://localhost:5432/monitor";
    private final String USER = "postgres";
    private final String PASSWORD = "123";
    private static StorageMonitoring StorageMonitoring;

    private final String REMOVE_URL ="DELETE FROM urls_info WHERE URL = ?";
    private final String UPDATE_MIN_RES ="UPDATE urls_info SET Max_response_time = ? WHERE URL = ?";
    private final String UPDATE_MAX_RES ="UPDATE urls_info SET Max_response_time = ? WHERE URL = ?";
    private final String UPDATE_M0NITOR_TIME ="UPDATE urls_info SET Monitoring_time = ? WHERE URL = ?";
    private final String UPDATE_RES_CODE ="UPDATE urls SET Response_code = ? WHERE URL = ?";
    private final String UPDATE_MIN_SIZE ="UPDATE urls_info SET Min_size = ? WHERE URL = ?";
    private final String UPDATE_MAX_SIZE ="UPDATE urls_info SET Max_size = ? WHERE URL = ?";
    private final String GET_URL ="SELECT * FROM urls_info WHERE URL = ?";
    private final String GET_URLS ="SELECT * FROM urls_info";
    private final String ADD_URL ="INSERT INTO urls_info (URL, Min_response_time, Max_response_time, Monitoring_time, " +
            "Response_code, Min_size, Max_size) VALUES (?, ?, ?, ?, ?, ?, ?)";

    private StorageMonitoring(){
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(DATA_BASE_URL, USER, PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static synchronized Connection getConnection(){
        return connection;
    }

    public static synchronized StorageMonitoring getInstance(){
        if (StorageMonitoring == null){
            StorageMonitoring = new StorageMonitoring();
        }
        return StorageMonitoring;
    }

    @Override
    public Url getURL(String url) {
        Url URL;
        PreparedStatement statement = null;
        try {
            statement = connection
                    .prepareStatement(GET_URL);

            statement.setString(1, url);

            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            URL = new Url(resultSet.getString(1), resultSet.getInt(2),
                    resultSet.getInt(3), resultSet.getInt(4), resultSet.getInt(5),
                    resultSet.getInt(6), resultSet.getInt(7));

            statement.close();

            return URL;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    return  null;
}

    @Override
    public List<Url> getURLs() throws SQLException{
        List<Url> monitoredURLS = new ArrayList<>();

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(GET_URLS);

        while (resultSet.next()){
            monitoredURLS.add(new Url(resultSet.getString(1), resultSet.getInt(2),
                    resultSet.getInt(3), resultSet.getInt(4), resultSet.getInt(5),
                    resultSet.getInt(6), resultSet.getInt(7)));
            }
        statement.close();

        return monitoredURLS;
    }

    @Override
    public void addURL(Url monitoredURL) {
        if (monitoredURL != null) {
            try {
                PreparedStatement preparedStatement = connection
                        .prepareStatement(ADD_URL);

                preparedStatement.setString(1, monitoredURL.getUrl());
                preparedStatement.setInt(2, monitoredURL.getMinResponseTime());
                preparedStatement.setInt(3, monitoredURL.getMaxResponseTime());
                preparedStatement.setInt(4, monitoredURL.getMonitoringTimeSeconds());
                preparedStatement.setInt(5, monitoredURL.getResponseCode());
                preparedStatement.setInt(6, monitoredURL.getMinSize());
                preparedStatement.setInt(7, monitoredURL.getMaxSize());

                preparedStatement.executeUpdate();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void removeURL(String url) throws SQLException{
            PreparedStatement preparedStatement = connection
                    .prepareStatement(REMOVE_URL);

            preparedStatement.setString(1, url);

            preparedStatement.executeUpdate();
            preparedStatement.close();
    }

    @Override
    public void updateMinResponseTime(String targetUrl, int updatedMinResponseTime) throws SQLException{
            PreparedStatement preparedStatement = connection
                    .prepareStatement(UPDATE_MIN_RES);

            preparedStatement.setInt(1, updatedMinResponseTime);
            preparedStatement.setString(2, targetUrl);

            preparedStatement.executeUpdate();
            preparedStatement.close();

    }

    @Override
    public void updateMaxResponseTime(String targetUrl, int updatedMaxResponseTime) throws SQLException{
            PreparedStatement preparedStatement = connection
                    .prepareStatement(UPDATE_MAX_RES);

            preparedStatement.setInt(1, updatedMaxResponseTime);
            preparedStatement.setString(2, targetUrl);

            preparedStatement.executeUpdate();
            preparedStatement.close();
    }

    @Override
    public void updateMonitoringTime(String targetUrl, int updateMonitoringTime) throws SQLException{

            PreparedStatement preparedStatement = connection
                    .prepareStatement(UPDATE_M0NITOR_TIME);

            preparedStatement.setInt(1, updateMonitoringTime);
            preparedStatement.setString(2, targetUrl);

            preparedStatement.executeUpdate();
            preparedStatement.close();

    }

    @Override
    public void updateResponseCode(String targetUrl, int updatedResponseCode) throws SQLException {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(UPDATE_RES_CODE);

            preparedStatement.setInt(1, updatedResponseCode);
            preparedStatement.setString(2, targetUrl);

            preparedStatement.executeUpdate();
            preparedStatement.close();
    }

    @Override
    public void updateMinSize(String targetUrl, int updateMinSize) throws SQLException {
        PreparedStatement preparedStatement = connection
                .prepareStatement(UPDATE_MIN_SIZE);

        preparedStatement.setInt(1, updateMinSize);
        preparedStatement.setString(2, targetUrl);

        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    @Override
    public void updateMaxSize(String targetUrl, int updateMaxSize) throws SQLException {
        PreparedStatement preparedStatement = connection
                .prepareStatement(UPDATE_MAX_SIZE);

        preparedStatement.setInt(1, updateMaxSize);
        preparedStatement.setString(2, targetUrl);

        preparedStatement.executeUpdate();
        preparedStatement.close();
    }
}
