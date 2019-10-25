package com.jonas.dao;

import com.jonas.domain.Account;
import com.jonas.util.BeanHandler;
import com.jonas.util.JdbcTemplate;
import org.apache.commons.collections4.CollectionUtils;

import javax.sql.DataSource;
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
            "  `account_id` int(11) NOT NULL auto increment comment '账户ID',\n" +
            "  `account` varchar(32) DEFAULT NULL comment '账户',\n" +
            "  `ctime` datetime DEFAULT NULL comment '创建时间',\n" +
            "  `utime` datetime DEFAULT NULL comment '更新时间',\n" +
            "  PRIMARY KEY (`account_id`)\n" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 comment '账户信息表'";

    public AccountDAO() {
        createIfAbsent();
    }

    @Override
    public void createIfAbsent() {
        if (!JdbcTemplate.checkIfExist("tb_account")) {
            JdbcTemplate.execute(CREATE);
        }
    }

    public void save(Account account) {
        String sql = "insert into tb_account (account_id, account, ctime, utime) value (?,?,?,?)";
        Object[] params = new Object[] {account.getAccountId(), account.getAccount(), account.getCtime(), account.getUtime()};
        JdbcTemplate.execute(sql, params);
    }

    public void delete(Long accountId) {
        String sql = "delete from tb_account where account_id = ?";
        JdbcTemplate.execute(sql, accountId);
    }

    public void update(Account account) {
        String sql = "update tb_account set account = ?, utime = ? where account_id = ?";
        Object[] params = new Object[] {account.getAccount(), new Timestamp(System.currentTimeMillis()), account.getAccountId()};
        JdbcTemplate.execute(sql, params);
    }

    public Account get(Long accountId) {
        String sql = "select account_id, account, ctime, utime from tb_account where account_id = ?";
        List<Account> accounts = JdbcTemplate.query(sql, new BeanHandler<>(Account.class), accountId);
        return CollectionUtils.isEmpty(accounts) ? null : accounts.get(0);
    }

    public List<Account> list() {
        String sql = "select * from tb_account";
        return JdbcTemplate.query(sql, new BeanHandler<>(Account.class));
    }
}
