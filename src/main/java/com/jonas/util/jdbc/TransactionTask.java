package com.jonas.util.jdbc;

import java.sql.Connection;

public interface TransactionTask {
    boolean doTask(Connection connection);
}
