package com.jonas.util;

import java.sql.*;

/**
 * <p>
 * </p>
 *
 * @author shenjiayun
 * @since 2019-10-25
 */
public class JdbcTemplate {
    // 工具类，私有化无参构造函数
    private JdbcTemplate() {}

    /**
     * 校验表是否不存在
     *
     * @param tableName
     * @return
     */
    public static boolean checkIfExist(String tableName) {
        ResultSet resultSet = null;
        Connection connection = null;
        try {
            connection = DruidUtils.getConnection();
            if (null != connection) {
                DatabaseMetaData metaData = connection.getMetaData();
                String[] type = {"TABLE"};
                resultSet = metaData.getTables(null, null, tableName, type);
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResultSet(resultSet);
            closeConnection(connection);
        }
        return false;
    }

    public static <T> T query(String sql, ResultSetHandler<T> handler, Object... params) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DruidUtils.getConnection();
            statement = connection.prepareStatement(sql);
            if (null != params && 0 < params.length) {
                for (int i = 0; i < params.length; i++) {
                    statement.setObject(i + 1, params[i]);
                }
            }
            resultSet = statement.executeQuery();
            return handler.handle(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResultSet(resultSet);
            closeStatement(statement);
            closeConnection(connection);
        }
        return null;
    }

    public static void execute(String sql, Object... params) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DruidUtils.getConnection();
            statement = connection.prepareStatement(sql);
            if (null != params && 0 < params.length) {
                for (int i = 0; i < params.length; i++) {
                    statement.setObject(i + 1, params[i]);
                }
            }
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatement(statement);
            closeConnection(connection);
        }
    }

    private static void closeStatement(Statement statement) {
        if (null != statement) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void closeConnection(Connection connection) {
        if (null != connection) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void closeResultSet(ResultSet resultSet) {
        if (null != resultSet) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
