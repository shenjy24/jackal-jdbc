package com.jonas.dao;

import com.jonas.domain.Account;
import com.jonas.util.jdbc.JdbcTemplate;
import com.jonas.util.jdbc.PropertyUtils;
import lombok.SneakyThrows;

import java.sql.Timestamp;
import java.util.List;

public class AccountDAO extends BaseDAO {

    private final static String TABLE = "tb_account";
    private final static String CREATE = "CREATE TABLE `%s` (\n" +
            "  `account_id` varchar(64) NOT NULL comment '账户ID',\n" +
            "  `balance` int(11) NOT NULL DEFAULT 0 comment '账户余额',\n" +
            "  `ctime` datetime DEFAULT NULL comment '创建时间',\n" +
            "  `utime` datetime DEFAULT NULL comment '更新时间',\n" +
            "  PRIMARY KEY (`account_id`)\n" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 comment '账户信息表'";

    private final static String INSERT = "insert into `%s` (account_id, balance, ctime, utime) value (?,?,?,?)";
    private final static String DELETE = "delete from `%s` where account_id = ?";

    public AccountDAO() {
        createIfAbsent(TABLE, String.format(CREATE, TABLE));
    }

    @SneakyThrows
    public void save(Account account) {
//        Object[] params = new Object[] {account.getAccountId(), account.getAccount(), account.getCtime(), account.getUtime()};
        execute(String.format(INSERT, TABLE), PropertyUtils.findFieldValue(account));
    }

    public void delete(String accountId) {
        execute(String.format(DELETE, TABLE), accountId);
    }

    public void update(Account account) {
        String sql = "update `%s` set balance = ?, utime = ? where account_id = ?";
        Object[] params = new Object[]{account.getBalance(), new Timestamp(System.currentTimeMillis()), account.getAccountId()};
        execute(String.format(sql, TABLE), params);
    }

    public Account findOne(String accountId) {
        String sql = "select * from `%s` where account_id = ?";
        return findOne(String.format(sql, TABLE), Account.class, accountId);
    }

    public List<Account> findAll() {
        String sql = "select * from `%s`";
        return findAll(String.format(sql, TABLE), Account.class);
    }


    public void reset(final List<Account> accounts) {
        doTransaction(connection -> {
            //先删除所有旧数据
            String deleteSql = "delete from `%s`";
            JdbcTemplate.executeUpdate(connection, String.format(deleteSql, TABLE));
            //保存新数据
            accounts.forEach(account -> {
                Object[] params = new Object[] {account.getAccountId(), account.getBalance(), account.getCtime(), account.getUtime()};
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

    public void batchSave(List<Account> accounts) {
        doTransaction(connection -> {
            accounts.forEach(account -> {
                Object[] params = new Object[] {account.getAccountId(), account.getBalance(), account.getCtime(), account.getUtime()};
                JdbcTemplate.executeUpdate(connection, String.format(INSERT, TABLE), params);
            });
            return true;
        });
    }
}
