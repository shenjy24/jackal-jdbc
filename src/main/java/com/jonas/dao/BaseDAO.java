package com.jonas.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * <p>
 * DAO基类
 * </p>
 *
 * @author shenjiayun
 * @since 2019-10-25
 */
public abstract class BaseDAO {

    /**
     * 表不存在则创建
     */
    public abstract void createIfAbsent();

    protected void closeResultSet(ResultSet resultSet) {
        try {
            if (null != resultSet) {
                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void closeStatement(Statement statement) {
        try {
            if (null != statement) {
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void closeConnection(Connection connection) {
        try {
            if (null != connection) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void rollback(Connection connection) {
        try {
            if (null != connection) {
                connection.setAutoCommit(true);
                connection.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
