package com.mk;

import com.mk.Account.AccountDao;
import com.mk.Account.AccountDaoImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.sql.SQLException;

public class TEST {
    public static void main(String[] args) throws PropertyVetoException, SQLException {
        ApplicationContext ac=new ClassPathXmlApplicationContext("com/mk/AppcationContext.xml");
        AccountDaoImpl accountDao = ac.getBean(AccountDaoImpl.class);
        accountDao.tranfAccount2("jack","rose",500.0);
//        Object dataSource=ac.getBean("dataSource");
//        System.out.println(dataSource);
    }
}
