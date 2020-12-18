package com.jonas.dao;

import com.jonas.domain.BigNumber;
import com.jonas.domain.GameNumber;
import com.jonas.util.jdbc.BeanHandler;
import com.jonas.util.jdbc.DruidUtils;
import com.jonas.util.jdbc.JdbcTemplate;
import org.apache.commons.collections.CollectionUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author shenjiayun
 * @since 2019-11-21
 */
public class BigNumberDAO extends BaseDAO {

    private final String TABLE = "big_number";
    private final String CREATE = "CREATE TABLE `big_number` (\n" +
            "  `id` int(11) AUTO_INCREMENT NOT NULL COMMENT '逻辑主键',\n" +
            "  `money` varchar(255) NOT NULL COMMENT '字符串长度',\n" +
            "  `count` int(11) NOT NULL DEFAULT '0' COMMENT '次数',\n" +
            "  PRIMARY KEY (`id`)\n" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='大数据索引测试'";

    public BigNumberDAO() {
        createIfAbsent();
    }

    @Override
    public void createIfAbsent() {
        Connection connection = null;
        try {
            connection = DruidUtils.getConnection();
            if (!JdbcTemplate.checkIfExist("big_number")) {
                JdbcTemplate.execute(CREATE);
            }
        } finally {
            closeConnection(connection);
        }
    }

    public void save(BigNumber bigNumber) {
        Connection connection = null;
        try {
            String sql = "insert into big_number(money, count) values (?,?)";
            connection = DruidUtils.getConnection();
            Object[] args = new Object[]{bigNumber.getMoney(), bigNumber.getCount()};
            JdbcTemplate.execute(sql, args);
        } finally {
            closeConnection(connection);
        }
    }

    public int count() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DruidUtils.getConnection();
            statement = connection.createStatement();
            String sql = String.format("select count(*) from `%s`", TABLE);
            resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResultSet(resultSet);
            closeStatement(statement);
            closeConnection(connection);
        }
        return 0;
    }
}
