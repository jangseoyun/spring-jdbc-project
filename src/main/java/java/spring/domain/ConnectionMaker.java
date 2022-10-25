package java.spring.domain;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionMaker {
    Connection dbConnection() throws SQLException;
}
