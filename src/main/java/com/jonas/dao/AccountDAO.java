package com.jonas.dao;

import com.jonas.domain.Account;
import com.jonas.util.PropertyUtils;
import com.jonas.util.jdbc.BeanHandler;
import com.jonas.util.jdbc.DruidUtils;
import com.jonas.util.jdbc.JdbcTemplate;
import org.apache.commons.collections4.CollectionUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author shenjiayun
 * @since 2019-10-25
 */
public class AccountDAO extends BaseDAO {

    private static final String CREATE = "CREATE TABLE `tb_account` (\n" +
            "  `account_id` int(11) NOT NULL auto_increment comment '账户ID',\n" +
            "  `account` varchar(32) DEFAULT NULL comment '账户',\n" +
            "  `ctime` datetime DEFAULT NULL comment '创建时间',\n" +
            "  `utime` datetime DEFAULT NULL comment '更新时间',\n" +
            "  PRIMARY KEY (`account_id`)\n" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 comment '账户信息表'";

    private static final String DELETE_ALL = "delete from tb_account";
    private static final String INSERT = "insert into tb_account (account_id, account, ctime, utime) value (?,?,?,?)";
    private static final String DELETE = "delete from tb_account where account_id = ?";
    private static final String DELETE_ACCOUNT = "delete from tb_account where account = ?";

    public AccountDAO() {
        createIfAbsent();
    }

    @Override
    public void createIfAbsent() {
        if (!JdbcTemplate.checkIfExist("tb_account")) {
            JdbcTemplate.execute(CREATE);
        }
    }

    public void save(Account account) throws IllegalAccessException {
        JdbcTemplate.execute(INSERT, PropertyUtils.findFieldValue(account));
    }

    public void delete(Long accountId) {
        JdbcTemplate.execute(DELETE, accountId);
    }

    public void delete(String account) {
        JdbcTemplate.execute(DELETE_ACCOUNT, account);
    }

    public void update(Account account) {
        String sql = "update tb_account set account = ?, utime = ? where account_id = ?";
        Object[] params = new Object[]{account.getAccount(), new Timestamp(System.currentTimeMillis()), account.getAccountId()};
        JdbcTemplate.execute(sql, params);
    }

    public Account get(Long accountId) {
        String sql = "select * from tb_account where account_id = ?";
        List<Account> accounts = JdbcTemplate.query(sql, new BeanHandler<>(Account.class), accountId);
        return CollectionUtils.isEmpty(accounts) ? null : accounts.get(0);
    }

    public void reset(List<Account> accounts) {
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = DruidUtils.getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(DELETE_ALL);
            statement.execute();

            statement = connection.prepareStatement(INSERT);
            for (Account account : accounts) {
                statement.setLong(1, account.getAccountId());
                statement.setString(2, account.getAccount());
                statement.setTimestamp(4, account.getCtime());
                statement.setTimestamp(5, account.getUtime());
                statement.addBatch();
            }
            statement.executeBatch();
            connection.commit();
        } catch (Exception e) {
            rollback(connection);
        } finally {
            closeStatement(statement);
            closeConnection(connection);
        }
    }

    public void batchDelete(List<Account> accounts) {
        PreparedStatement deleteStat = null;
        Connection connection = null;
        try {
            connection = DruidUtils.getConnection();
            connection.setAutoCommit(false);
            deleteStat = connection.prepareStatement(DELETE);
            for (Account account : accounts) {
                deleteStat.setLong(1, account.getAccountId());
                deleteStat.addBatch();
            }
            deleteStat.executeBatch();
            connection.commit();
        } catch (Exception e) {
            rollback(connection);
        } finally {
            closeStatement(deleteStat);
            closeConnection(connection);
        }
    }

    public void batchInsert(List<Account> accounts) {
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = DruidUtils.getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(INSERT);
            for (Account account : accounts) {
                statement.setLong(1, account.getAccountId());
                statement.setString(2, account.getAccount());
                statement.setTimestamp(4, account.getCtime());
                statement.setTimestamp(5, account.getUtime());
                statement.addBatch();
            }
            statement.executeBatch();
            connection.commit();
        } catch (Exception e) {
            rollback(connection);
        } finally {
            closeStatement(statement);
            closeConnection(connection);
        }
    }

    public List<Account> list() {
        String sql = "select * from tb_account";
        return JdbcTemplate.query(sql, new BeanHandler<>(Account.class));
    }
}
