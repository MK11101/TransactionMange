package com.mk.Account;

public interface AccountDao {
    public void addMoney(String username,Double money);
    public void subMoney(String username,Double money);
    public String queryAccountMoney(String username);
    public void tranfAccount(String sourceName,String targetName,Double money);
}
