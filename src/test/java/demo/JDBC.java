package demo;

import com.beust.ah.A;
import com.sun.jdi.ObjectReference;
import org.testng.annotations.Test;

import java.sql.*;
import java.util.*;

public class JDBC {
    /*
    Connection => we connect to server
    Statement => execution will happen here
    ResultSet => Retrieve data here
  */
    String url = "jdbc:mysql://localhost:3306";
    String user = "root";
    String password = "admin";

    @Test
    public void jdbcTest() throws SQLException {

        Connection connection = DriverManager.getConnection(url, user, password);
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
//        statement.execute("USE db_qa");
        ResultSet resultSet = statement.executeQuery("SELECT * FROM db_qa.person");

        //how many records in the resultsSet

        //last => brings us to the last row
        resultSet.last();
        int rowsCount = resultSet.getRow();
        System.out.println(rowsCount);

        resultSet.close();
        statement.close();
        connection.close();
    }

    @Test
    public void jdbcMetaData() throws SQLException {
        Connection connection = DriverManager.getConnection(url, user, password);
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
//        statement.execute("USE db_qa");
        ResultSet resultSet = statement.executeQuery("SELECT * FROM db_qa.person");

        String query = "Select * FROM db_qa.person";
        resultSet = statement.executeQuery(query);

        //database metadata
        DatabaseMetaData dbMetaData = connection.getMetaData();
        System.out.println("user: "+ dbMetaData.getUserName());
        System.out.println("database type: "+ dbMetaData.getDatabaseProductName());
        //System.out.println(dbMetaData.getSchemas().next());

        //resultSet metadata
        ResultSetMetaData rsMetaData = resultSet.getMetaData();
        System.out.println("Columns count: "+ rsMetaData.getColumnCount());
        //System.out.println(rsMetaData.getColumnName(5));

        // print all column names
//        for (int i = 1; i<=rsMetaData.getColumnCount(); i++){
//            System.out.println(rsMetaData.getColumnName(i));
//
//        }

//        List <String> books = new ArrayList<>();
//        while (resultSet.next()){
//
//            Object ob = resultSet.getObject("title");
//            books.add(ob.toString());
//        }
//        System.out.println(books);

        int colCount = rsMetaData.getColumnCount(); //5
        List <Map <String, Object>> data = new ArrayList<>();
//[{id=1, name=Farid, last_name=Ahmadov, dob=1994-05-03, gender=M},
//{id=2, name=Lara,    last_name=Alissa,     dob=2010-11-21, 	gender=F},
// ]
        while (resultSet.next()){ // is there next row

            Map<String, Object> rowMap = new LinkedHashMap<>();
                        //5        //5
            for (int i = 1; i<=colCount; i++){
                String colName = rsMetaData.getColumnName(i); //gender
                Object cellData = resultSet.getObject(i); //M
                rowMap.put(colName,cellData);//{id=1, name=Farid, last_name=Ahmadov, dob=1994-05-03, gender=M}
            }
        data.add(rowMap);

        }
        System.out.println(data);
        resultSet.close();
        statement.close();
        connection.close();

       // {id=1, title=The NameSake, author_fname=Jhumpa, author_lnmae=Lahiri, released_year=2003 stock=32, pages=291}
    }

}


