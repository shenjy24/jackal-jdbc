package com.jonas.dao;

import com.jonas.util.jdbc.BeanHandler;
import com.jonas.util.jdbc.DruidUtils;
import com.jonas.util.jdbc.JdbcTemplate;
import com.jonas.util.jdbc.TransactionTask;

import java.sql.*;
import java.util.List;

public abstract class BaseDAO {
    protected void createIfAbsent(String tableName, String sql) {
        Connection connection = null;
        try {
            connection = DruidUtils.getConnection();
            if (!JdbcTemplate.checkIfExist(connection, tableName)) {
                JdbcTemplate.execute(connection, sql);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection);
        }
    }

    /**
     * 判断表是否存在
     */
    protected boolean checkExist(String tableName) {
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            connection = DruidUtils.getConnection();
            if (null != connection) {
                DatabaseMetaData metaData = connection.getMetaData();
                String[] type = {"TABLE"};
                resultSet = metaData.getTables(connection.getCatalog(), connection.getSchema(), tableName, type);
                return resultSet.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResultSet(resultSet);
            closeConnection(connection);
        }
        return false;
    }

    protected <T> T findOne(String sql, Class<T> clazz, Object...args) {
        Connection connection = null;
        try {
            connection = DruidUtils.getConnection();
            return JdbcTemplate.findOne(connection, sql, new BeanHandler<>(clazz), args);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection);
        }
        return null;
    }

    protected <T> List<T> findAll(String sql, Class<T> clazz, Object...args) {
        Connection connection = null;
        try {
            connection = DruidUtils.getConnection();
            return JdbcTemplate.findAll(connection, sql, new BeanHandler<>(clazz), args);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection);
        }
        return null;
    }

    protected boolean execute(String sql, Object...args) {
        Connection connection = null;
        try {
            connection = DruidUtils.getConnection();
            return JdbcTemplate.executeUpdate(connection, sql, args);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection);
        }
        return false;
    }

    protected void doTransaction(TransactionTask task) {
        Connection connection = null;
        try {
            connection = DruidUtils.getConnection();
            connection.setAutoCommit(false);
            boolean result = task.doTask(connection);
            if (result) {
                connection.commit();
            } else {
                connection.rollback();
            }
        } catch (SQLException e) {
            rollback(connection);
            e.printStackTrace();
        } finally {
            closeConnection(connection);
        }
    }

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
