package com.wjyxsy.service.impl;

import com.wjyxsy.Annotation.MyAutowired;
import com.wjyxsy.Annotation.MyService;
import com.wjyxsy.Annotation.MyTranscation;
import com.wjyxsy.dao.AccountDao;
import com.wjyxsy.factory.BeanFactory;
import com.wjyxsy.pojo.Account;
import com.wjyxsy.service.TransferService;

import java.sql.SQLException;

@MyService("transferService")
public class TransferServiceImpl implements TransferService {


    @MyAutowired("accountDao")
    private AccountDao accountDao;

    @MyTranscation
    @Override
    public void transfer(String from, String to, Integer money) throws SQLException {

        Account accountFrom = accountDao.selectAccount(from);
        Account accountTo = accountDao.selectAccount(to);

        accountFrom.setMoney(accountFrom.getMoney()-money);
        accountTo.setMoney(accountTo.getMoney()+money);

        accountDao.updateAccount(accountFrom);
//        int i = 1/0;
        accountDao.updateAccount(accountTo);

    }
}
