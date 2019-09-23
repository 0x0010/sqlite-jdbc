//--------------------------------------
// sqlite-jdbc Project
//
// InsertQueryTest.java
// Since: Apr 7, 2009
//
// $URL$ 
// $Author$
//--------------------------------------
package org.sqlite;

import org.junit.Test;

import java.sql.*;

public class MalformedDatabaseFileQueryTest {

    private String dbFile = "/Users/sam/malformed-db.sqlite";

    @Test
    public void testCreateTableAndInsert() throws SQLException {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:");
            statement = connection.createStatement();
            statement.executeUpdate(
                "CREATE TABLE IF NOT EXISTS user (user_id INTEGER PRIMARY KEY, user_name VARCHAR(32));");
            statement.executeUpdate("INSERT INTO user (user_id, user_name) values (1, 'Sam');");
            statement.executeUpdate("INSERT INTO user (user_id, user_name) values (2, 'Jetty');");
            statement.executeUpdate("INSERT INTO user (user_id, user_name) values (3, 'Bella');");
            statement.executeUpdate("backup to " + dbFile);
        } finally {
            if (null != statement && !statement.isClosed()) {
                statement.close();
            }
            if (null != connection && !connection.isClosed()) {
                connection.close();
            }
        }
    }

    @Test
    public void testQuery() throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
            statement = connection.prepareStatement("select * from user where user_id = ?;");
            statement.setInt(1, 3);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getInt(1) + "," + resultSet.getString(2));
            }
        } finally {
            if (null != statement && !statement.isClosed()) {
                statement.close();
            }
            if (null != connection && !connection.isClosed()) {
                connection.close();
            }
        }
    }

}
