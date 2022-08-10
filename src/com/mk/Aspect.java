package com.mk;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

//通过切面编程的形式实现事务管理
@Component("aspect")
@org.aspectj.lang.annotation.Aspect
public class Aspect {
    @Autowired
    @Qualifier("transactionManager")
    DataSourceTransactionManager transactionManager;
    @Around("execution(* com.mk.Account.AccountDaoImpl.tranfAccount2())")
    public void TransactionAspectBefore(ProceedingJoinPoint joinPoint) throws Throwable {
        //环绕前通知
        DefaultTransactionDefinition tdefiniton=new DefaultTransactionDefinition();
        tdefiniton.setPropagationBehavior(DefaultTransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus ts=transactionManager.getTransaction(tdefiniton);

        joinPoint.proceed();

        //环绕后通知,提交事务
        transactionManager.commit(ts);
    }
}
