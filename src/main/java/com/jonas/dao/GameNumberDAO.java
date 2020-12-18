package com.jonas.dao;

import com.jonas.domain.GameNumber;
import com.jonas.util.PropertyUtils;
import com.jonas.util.jdbc.BeanHandler;
import com.jonas.util.jdbc.DruidUtils;
import com.jonas.util.jdbc.JdbcTemplate;
import org.apache.commons.collections.CollectionUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author shenjiayun
 * @since 2019-11-21
 */
public class GameNumberDAO extends BaseDAO {

    private final String CREATE = "CREATE TABLE `party_game_number` (\n" +
            "  `player_uuid` varchar(100) NOT NULL COMMENT '玩家唯一标识',\n" +
            "  `player_name` varchar(100) NOT NULL COMMENT '玩家名',\n" +
            "  `number` int(11) NOT NULL DEFAULT '0' COMMENT '游戏场数',\n" +
            "  `ctime` datetime NOT NULL COMMENT '创建时间',\n" +
            "  `utime` datetime NOT NULL COMMENT '更新时间',\n" +
            "  PRIMARY KEY (`player_uuid`),\n" +
            "  KEY `idx_number` (`number`)\n" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='玩家参与派对小游戏场数'";

    public GameNumberDAO() {
        createIfAbsent();
    }

    @Override
    public void createIfAbsent() {
        Connection connection = null;
        try {
            connection = DruidUtils.getConnection();
            if (!JdbcTemplate.checkIfExist("party_game_number")) {
                JdbcTemplate.execute(CREATE);
            }
        } finally {
            closeConnection(connection);
        }
    }

    public void save(GameNumber gameNumber) {
        Connection connection = null;
        try {
            String sql = "insert into party_game_number(player_uuid, player_name, number, ctime, utime) values (?,?,?,?,?)";
            connection = DruidUtils.getConnection();
            JdbcTemplate.execute(sql, PropertyUtils.findFieldValue(gameNumber));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection);
        }
    }

    public void update(String playerUUID, String playerName, int oldNumber, int newNumber) {
        Connection connection = null;
        try {
            connection = DruidUtils.getConnection();
            String sql = "update party_game_number set player_name = ?, number = ?, utime = ? where player_uuid = ? and number = ?";
            Object[] params = new Object[]{playerName, newNumber, new Timestamp(System.currentTimeMillis()), playerUUID, oldNumber};
            JdbcTemplate.execute(sql, params);
        } finally {
            closeConnection(connection);
        }
    }

    public GameNumber get(String playerUUID) {
        Connection connection = null;
        try {
            connection = DruidUtils.getConnection();
            String sql = "select * from party_game_number where player_uuid = ? ";
            List<GameNumber> accounts = JdbcTemplate.query(sql, new BeanHandler<>(GameNumber.class), playerUUID);
            return CollectionUtils.isNotEmpty(accounts) ? accounts.get(0) : null;
        } finally {
            closeConnection(connection);
        }
    }

    public List<GameNumber> querySortByNum(int num) {
        Connection connection = null;
        try {
            connection = DruidUtils.getConnection();
            String sql = "select * from party_game_number order by number desc limit ? ";
            List<GameNumber> accounts = JdbcTemplate.query(sql, new BeanHandler<>(GameNumber.class), num);
            return CollectionUtils.isEmpty(accounts) ? Collections.EMPTY_LIST : accounts;
        } finally {
            closeConnection(connection);
        }
    }
}
