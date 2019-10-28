package com.jonas.util.jdbc;

import java.sql.ResultSet;

/**
 * <p>
 * </p>
 *
 * @author shenjiayun
 * @since 2019-10-25
 */
public interface ResultSetHandler<T> {
    T handle(ResultSet resultSet);
}
