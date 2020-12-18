package com.jonas.util.jdbc;

import java.sql.ResultSet;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author shenjiayun
 * @since 2019-10-25
 */
public interface ResultSetHandler<T> {
    List<T> handle(ResultSet resultSet);
}
