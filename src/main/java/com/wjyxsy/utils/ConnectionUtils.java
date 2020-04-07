package com.wjyxsy.utils;

import com.wjyxsy.Annotation.MyService;

import java.sql.Connection;
import java.sql.SQLException;

@MyService("connectionUtils")
public class ConnectionUtils {

    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    public Connection getConn() throws SQLException {
        Connection connection = threadLocal.get();
        if (connection == null) {
            connection = DruidUtils.getInstance().getConnection();
            threadLocal.set(connection);
        }
        return connection;
    }
}
