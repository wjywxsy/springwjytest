package com.wjyxsy.service;

import java.sql.SQLException;

public interface TransferService {

    void transfer(String from,String to, Integer money) throws SQLException;
}
