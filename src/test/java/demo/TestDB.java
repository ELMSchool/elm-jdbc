package demo;

import org.testng.annotations.Test;

import java.sql.SQLException;

public class TestDB {

    @Test
    public void testDb() throws SQLException {
        DBUtility.setDBConnection();
        System.out.println(DBUtility.runQuery("SELECT * FROM db_qa.person where name = 'Shahin'"));
        DBUtility.closeConnection();

    }

}
