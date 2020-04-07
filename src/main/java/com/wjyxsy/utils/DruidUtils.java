package com.wjyxsy.utils;

import com.alibaba.druid.pool.DruidDataSource;

public class DruidUtils {

    private DruidUtils() {}

    private static DruidDataSource druidDataSource = new DruidDataSource();

    static {
        druidDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        druidDataSource.setUrl("jdbc:mysql:///test?useUnicode=true&characterEncoding=utf8");
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("root");
    }

    public static DruidDataSource getInstance() {
        return druidDataSource;
    }
}
