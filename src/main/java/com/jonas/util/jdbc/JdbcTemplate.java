package com.jonas.util.jdbc;

import com.jonas.util.CollectionUtils;

import java.sql.*;
import java.util.List;

public class JdbcTemplate {
    // 工具类，私有化无参构造函数
    private JdbcTemplate() {}

    /**
     * 校验表是否不存在
     *
     * @param tableName
     * @return
     */
    public static boolean checkIfExist(Connection connection, String tableName) {
        ResultSet resultSet = null;
        try {
            if (null != connection) {
                DatabaseMetaData metaData = connection.getMetaData();
                String[] type = {"TABLE"};
                resultSet = metaData.getTables(connection.getCatalog(), connection.getSchema(), tableName, type);
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResultSet(resultSet);
        }
        return false;
    }

    public static <T> List<T> findAll(Connection connection, String sql, ResultSetHandler<T> handler, Object... params) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
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
        }
        return null;
    }


    public static <T> T findOne(Connection connection, String sql, ResultSetHandler<T> handler, Object... params) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            if (null != params && 0 < params.length) {
                for (int i = 0; i < params.length; i++) {
                    statement.setObject(i + 1, params[i]);
                }
            }
            resultSet = statement.executeQuery();
            List<T> list = handler.handle(resultSet);
            return CollectionUtils.isNotEmpty(list) ? list.get(0) : null;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResultSet(resultSet);
            closeStatement(statement);
        }
        return null;
    }

    public static boolean execute(Connection connection, String sql, Object... params) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            if (null != params && 0 < params.length) {
                for (int i = 0; i < params.length; i++) {
                    statement.setObject(i + 1, params[i]);
                }
            }
            return statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatement(statement);
        }
        return false;
    }

    public static boolean executeUpdate(Connection connection, String sql, Object... params) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            if (null != params && 0 < params.length) {
                for (int i = 0; i < params.length; i++) {
                    statement.setObject(i + 1, params[i]);
                }
            }
            return 0 < statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatement(statement);
        }
        return false;
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
