package com.wjyxsy.utils;

import com.wjyxsy.Annotation.MyAutowired;
import com.wjyxsy.Annotation.MyService;

import java.sql.SQLException;

@MyService("transactionManager")
public class TransactionManager {

    @MyAutowired("connectionUtils")
    private ConnectionUtils connectionUtils;

    public void beginTransaction() throws SQLException {
        connectionUtils.getConn().setAutoCommit(false);
    }

    public void commit() throws SQLException {
        connectionUtils.getConn().commit();
    }

    public void rollback() throws SQLException {
        connectionUtils.getConn().rollback();
    }
}
