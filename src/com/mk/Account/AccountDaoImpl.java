package com.mk.Account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
@Component("accountDaoImpl")
public class AccountDaoImpl implements AccountDao{
//    @Autowired
//    @Qualifier("dataSource")
//    private DataSource dataSource;
    @Autowired
    @Qualifier("jdbcTemplate")
    private JdbcTemplate jdbcTemplate;
//    @Autowired
//    @Qualifier("transactionManager")
//    private DataSourceTransactionManager transactionManager;
    private Connection conn;
//    public void setDataSource(DataSource dataSource) {
//        this.dataSource = dataSource;
//        try {
//            conn=this.dataSource.getConnection();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

//    public DataSource getDataSource() {
//        return dataSource;
//    }

    @Override
    public void addMoney(String username, Double money) {
        PreparedStatement psmt=null;
        try{
//            psmt=conn.prepareStatement("update t_account set money=money+? where username=?");
//            psmt.setDouble(1,money);
//            psmt.setString(2,username);
//            psmt.executeUpdate();
//            System.out.println("账户金额增加成功");
            jdbcTemplate.update("update t_account set money=money+? where username=?",new Object[]{new Double(money),username});
        }catch (Exception e){
            try {
                conn.rollback();
                System.out.println("账户金额增加失败");
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            e.printStackTrace();
        }
    }

    @Override
    public void subMoney(String username, Double money) {
        PreparedStatement psmt=null;
        try {
//            psmt=conn.prepareStatement("update t_account set money=money-? where username=?");
//            psmt.setDouble(1,money);
//            psmt.setString(2,username);
//            psmt.executeUpdate();
//            System.out.println("账户金额减少成功");
            Double latemoney=jdbcTemplate.queryForObject("SELECT money FROM t_account where username=?",new Object[]{username},Double.class);
            if(latemoney<=0){
                throw new Exception();
            }
            jdbcTemplate.update("update t_account set money=money-? where username=?",new Object[]{new Double(money),username});

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    public String queryAccountMoney(String username) {
        return null;
    }

    @Override
    public void tranfAccount(String sourceName, String targetName, Double money) {
        try {
//            setDataSource(dataSource);
            conn.setAutoCommit(false);
            subMoney(sourceName,money);
//            int i=1/0;                   //模拟异常
            addMoney(targetName,money);
            System.out.println("转帐成功");
            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
                System.out.println("转载失败");
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
    }
    @Transactional
    public void tranfAccount2(String sourceName, String targetName, Double money){
//        DefaultTransactionDefinition tdefiniton=new DefaultTransactionDefinition();
//        tdefiniton.setPropagationBehavior(DefaultTransactionDefinition.PROPAGATION_REQUIRED);
//        TransactionStatus ts=transactionManager.getTransaction(tdefiniton);
        try{
            subMoney(sourceName,money);
            System.out.println("转出中....");
//            int i=1/0;                   //模拟异常
            addMoney(targetName,money);
            System.out.println("转入中.....");
//            transactionManager.commit(ts);
            System.out.println("转帐成功....");
        }catch (Exception e){
            e.printStackTrace();
//            transactionManager.rollback(ts);
            System.out.println("转帐失败....");
        }
    }


    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

//    public DataSourceTransactionManager getTransactionManager() {
//        return transactionManager;
//    }
//
//    public void setTransactionManager(DataSourceTransactionManager transactionManager) {
//        this.transactionManager = transactionManager;
//    }
}
