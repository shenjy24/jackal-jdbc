package com.jonas.dao;

import com.jonas.domain.User;
import com.jonas.util.DruidUtils;
import com.jonas.util.JdbcTemplate;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author shenjiayun
 * @since 2019-10-25
 */
public class UserDAO extends BaseDAO {

    private static final String CREATE = "CREATE TABLE `tb_user` (\n" +
            "  `uid` int(11) NOT NULL comment '玩家ID',\n" +
            "  `user_name` varchar(32) DEFAULT NULL comment '玩家名',\n" +
            "  `password` varchar(32) DEFAULT NULL comment '密码',\n" +
            "  `name` varchar(32) DEFAULT NULL comment '昵称',\n" +
            "  `age` int(10) DEFAULT NULL comment '年龄',\n" +
            "  `sex` int(2) DEFAULT NULL comment '性别',\n" +
            "  `birthday` date DEFAULT NULL comment '生日',\n" +
            "  `ctime` datetime DEFAULT NULL comment '创建时间',\n" +
            "  `utime` datetime DEFAULT NULL comment '更新时间',\n" +
            "  PRIMARY KEY (`uid`)\n" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 comment '玩家信息表'";
    private static final String DELETE_ALL = "delete from tb_user";
    private static final String DELETE = "delete from tb_user where uid = ?";
    private static final String INSERT = "insert into tb_user(uid, user_name, password, name, age, sex, birthday, ctime, utime) values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_ALL = "select * from tb_user";

    @Override
    public void createIfAbsent() {
        if (!JdbcTemplate.checkIfExist("tb_user")) {
            JdbcTemplate.execute(CREATE);
        }
    }

    public void reset(List<User> users) throws SQLException {
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = DruidUtils.getConnection();
            statement = connection.prepareStatement(DELETE_ALL);
            statement.execute();

            statement = connection.prepareStatement(INSERT);
            for (User user : users) {
                statement.setInt(1, user.getUid());
                statement.setString(2, user.getUserName());
                statement.setString(3, user.getPassword());
                statement.setString(4, user.getName());
                statement.setInt(5, user.getAge());
                statement.setInt(6, user.getSex());
                statement.setDate(7, user.getBirthday());
                statement.setTimestamp(8, user.getCtime());
                statement.setTimestamp(9, user.getUtime());
                statement.addBatch();
            }
            statement.executeBatch();
        } finally {
            closeStatement(statement);
            closeConnection(connection);
        }
    }

    public void batchDelete(List<User> users) {
        PreparedStatement deleteStat = null;
        Connection connection = null;
        try {
            connection = DruidUtils.getConnection();
            connection.setAutoCommit(false);
            deleteStat = connection.prepareStatement(DELETE);
            for (User user : users) {
                deleteStat.setInt(1, user.getUid());
                deleteStat.addBatch();
            }
            deleteStat.executeBatch();
            connection.commit();
        } catch (Exception e) {
            try {
                if (null != connection) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            closeStatement(deleteStat);
            closeConnection(connection);
        }
    }

    public void batchInsert(List<User> users) {

        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = DruidUtils.getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(INSERT);
            for (User user : users) {
                statement.setInt(1, user.getUid());
                statement.setString(2, user.getUserName());
                statement.setString(3, user.getPassword());
                statement.setString(4, user.getName());
                statement.setInt(5, user.getAge());
                statement.setInt(6, user.getSex());
                statement.setDate(7, user.getBirthday());
                statement.setTimestamp(8, user.getCtime());
                statement.setTimestamp(9, user.getUtime());
                statement.addBatch();
            }
            statement.executeBatch();
            connection.commit();
        } catch (Exception e) {
            try {
                if (null != connection) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            closeStatement(statement);
            closeConnection(connection);
        }
    }

    public List<User> queryAll() {
        Statement statement = null;
        ResultSet resultSet = null;
        Connection connection = null;
        try {
            connection = DruidUtils.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SELECT_ALL);
            List<User> scores = new ArrayList<>();
            while (resultSet.next()) {
                User user = new User();
                user.setUid(resultSet.getInt(1));
                user.setUserName(resultSet.getString(2));
                user.setPassword(resultSet.getString(3));
                user.setName(resultSet.getString(4));
                user.setAge(resultSet.getInt(5));
                user.setSex(resultSet.getInt(6));
                user.setBirthday(resultSet.getDate(7));
                user.setCtime(resultSet.getTimestamp(8));
                user.setUtime(resultSet.getTimestamp(9));

                scores.add(user);
            }
            return scores;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResultSet(resultSet);
            closeStatement(statement);
            closeConnection(connection);
        }

        return Collections.EMPTY_LIST;
    }

    public void deleteAll() {
        Statement statement = null;
        Connection connection = null;
        try {
            connection = DruidUtils.getConnection();
            statement = connection.createStatement();
            statement.execute(DELETE_ALL);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatement(statement);
            closeConnection(connection);
        }
    }
}
