package com.jonas.util.jdbc;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * <p>
 * </p>
 *
 * @author shenjiayun
 * @since 2019-10-25
 */
public class DruidUtils {
    private DruidUtils() {}

    private static DataSource dataSource;

    static {
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties");
        Properties properties = new Properties();
        try {
            properties.load(in);
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("获取数据库连接异常");
    }


}
