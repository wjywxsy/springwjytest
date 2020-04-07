package com.wjyxsy.dao;

import com.wjyxsy.pojo.Account;

import java.sql.SQLException;

public interface AccountDao {

    Account selectAccount(String cardNo) throws SQLException;

    int updateAccount(Account account) throws SQLException;
}
