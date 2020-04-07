package com.wjyxsy.dao.impl;

import com.wjyxsy.Annotation.MyAutowired;
import com.wjyxsy.Annotation.MyService;
import com.wjyxsy.dao.AccountDao;
import com.wjyxsy.pojo.Account;
import com.wjyxsy.utils.ConnectionUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MyService("accountDao")
public class AccountDaoImpl implements AccountDao {

    @MyAutowired("connectionUtils")
    private ConnectionUtils connectionUtils;

    @Override
    public Account selectAccount(String cardNo) throws SQLException {
        Connection connection = connectionUtils.getConn();
        String sql = "select * from account where cardno=?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, cardNo);
        ResultSet resultSet = preparedStatement.executeQuery();
        Account account = new Account();
        while(resultSet.next()) {
            account.setCardNo(resultSet.getString("cardNo"));
            account.setName(resultSet.getString("name"));
            account.setMoney(resultSet.getInt("money"));
        }
        resultSet.close();
        preparedStatement.close();

        return account;
    }

    @Override
    public int updateAccount(Account account) throws SQLException {

        Connection connection = connectionUtils.getConn();

        String sql = "update account set money=? where cardNo=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,account.getMoney());
        preparedStatement.setString(2,account.getCardNo());
        int i = preparedStatement.executeUpdate();
        preparedStatement.close();
        return i;
    }

}
