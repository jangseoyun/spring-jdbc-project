package com.spring.comtext;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcContext {
    private final DataSource dataSource;

    public JdbcContext(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setWithStatementStrategy(StatementStrategy stmt) {
        try {
            PreparedStatement ps = stmt.makePreparedStatement(dataSource.getConnection());
            int result = ps.executeUpdate();
            System.out.println(result);
            ps.close();
        } catch (SQLException e) {
            e.getMessage();
        }
    }
}
