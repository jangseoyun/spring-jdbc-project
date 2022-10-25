package java.spring.domain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

public class LocalConnectionImpl implements ConnectionMaker {
    @Override
    public Connection dbConnection() throws SQLException {
        Map<String, String> env = System.getenv();
        Connection conn = DriverManager.getConnection(
                  env.get("DB_HOST")
                , env.get("DB_USER")
                , env.get("DB_PASSWORD"));
        return conn;
    }
}
