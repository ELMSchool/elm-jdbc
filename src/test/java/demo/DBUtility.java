package demo;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DBUtility {

    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;
    private static int rowCount;


    public static void setDBConnection() throws SQLException {

        System.out.println(ConfigReader.getPropertyValue("url"));
        connection = DriverManager.getConnection(ConfigReader.getPropertyValue("url"),
                ConfigReader.getPropertyValue("user"),
                ConfigReader.getPropertyValue("password"));
    }

    public static int getRowCount(String query) throws SQLException {
        statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        resultSet = statement.executeQuery(query);
        resultSet.last();
        return resultSet.getRow();
    }


    public static List<Map<String, Object>> runQuery(String query) throws SQLException {

        statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        resultSet = statement.executeQuery(query);

        ResultSetMetaData rsMetaData = resultSet.getMetaData();
        int colCount = rsMetaData.getColumnCount();
        List<Map<String, Object>> data = new ArrayList<>();


        while (resultSet.next()) {

            Map<String, Object> rowMap = new LinkedHashMap<>();

            for (int i = 1; i <= colCount; i++) {
                String colName = rsMetaData.getColumnName(i);
                Object cellData = resultSet.getObject(i);
                rowMap.put(colName, cellData);
            }
            data.add(rowMap);

        }

        return data;
    }

    public static void closeConnection(){
        try{
            if (resultSet != null){
                resultSet.close();
            }
            if (statement != null){
                statement.close();
            }
            if (connection != null){
                connection.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
