package com.jonas.dao;

import com.jonas.domain.User;
import com.jonas.util.jdbc.JdbcTemplate;
import lombok.Data;
import lombok.SneakyThrows;

import java.util.List;

public class UserDAO extends BaseDAO {

    private final static String TABLE = "user";
    private final static String CREATE = "CREATE TABLE `%s` (\n" +
            "  `user_id` int(11) auto_increment comment '用户id',\n" +
            "  `user_name` varchar(32) not null comment '玩家名',\n" +
            "  `user_age` tinyint(11) not null default '1' comment '玩家年龄',\n" +
            "  `user_status` tinyint(11) not null default '1' comment '玩家状态',\n" +
            "  `user_score` bigint(20) not null default '0' comment '玩家积分',\n" +
            "  `ctime` bigint(20) not null comment '创建时间',\n" +
            "  `utime` bigint(20) not null comment '更新时间',\n" +
            "  PRIMARY KEY (`user_id`)\n" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 comment '用户信息表'";

    private final static String INSERT = "insert into `%s` (user_name, user_age, user_status, user_score, ctime, utime) value (?,?,?,?,?,?)";
    private final static String DELETE = "delete from `%s` where user_id = ?";

    public UserDAO() {
        createIfAbsent(TABLE, String.format(CREATE, TABLE));
    }

    @SneakyThrows
    public void save(User user) {
        Object[] args = new Object[]{user.getUserName(), user.getUserAge(), user.getUserStatus(), user.getUserScore(), user.getCtime(), user.getUtime()};
        execute(String.format(INSERT, TABLE), args);
    }

    public void delete(String userId) {
        execute(String.format(DELETE, TABLE), userId);
    }

    public void update(User user) {
        String sql = "update `%s` set user_name = ?, user_age = ?, user_status = ?, user_score = ?, utime = ? where user_id = ?";
        Object[] params = new Object[]{user.getUserName(), user.getUserAge(), user.getUserStatus(), user.getUserScore(),
                System.currentTimeMillis(), user.getUserId()};
        execute(String.format(sql, TABLE), params);
    }

    public User findOne(String userId) {
        String sql = "select * from `%s` where user_id = ?";
        return findOne(String.format(sql, TABLE), User.class, userId);
    }

    public List<User> findAll() {
        String sql = "select * from `%s`";
        return findAll(String.format(sql, TABLE), User.class);
    }

    public Count count() {
        String sql = "select count(1) as `count`  from `%s`";
        return findOne(String.format(sql, TABLE), Count.class);
    }

    @Data
    public static class Count {
        private int count;
    }

    public void reset(final List<User> users) {
        doTransaction(connection -> {
            //先删除所有旧数据
            String deleteSql = "delete from `%s`";
            JdbcTemplate.executeUpdate(connection, String.format(deleteSql, TABLE));
            //保存新数据
            users.forEach(user -> {
                Object[] params = new Object[]{user.getUserName(), user.getUserAge(), user.getUserStatus(), user.getUserScore(),
                        System.currentTimeMillis(), System.currentTimeMillis()};
                JdbcTemplate.executeUpdate(connection, String.format(INSERT, TABLE), params);
            });
            return true;
        });
    }

    public void batchDelete(final List<String> accountIds) {
        doTransaction(connection -> {
            accountIds.forEach(accountId -> {
                JdbcTemplate.executeUpdate(connection, String.format(DELETE, TABLE), accountId);
            });
            return true;
        });
    }

    public void batchSave(List<User> users) {
        doTransaction(connection -> {
            users.forEach(user -> {
                Object[] params = new Object[]{user.getUserName(), user.getUserAge(), user.getUserStatus(),
                        user.getUserScore(), System.currentTimeMillis(), System.currentTimeMillis()};
                JdbcTemplate.executeUpdate(connection, String.format(INSERT, TABLE), params);
            });
            return true;
        });
    }
}
