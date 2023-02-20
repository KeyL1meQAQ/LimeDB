package com.keyl1me.limedb.trasaction;

public interface TransactionManager {
    long begin(); // 开启新事务

    void commit(long tid); // 提交一个事务

    void abort(long tid); // 中止一个事务

    boolean isActive(long tid); // 一个事务是否正在进行中

    boolean isCommitted(long tid); // 一个事务是否已经被提交

    boolean isAborted(long tid); // 一个事务是否已经被中止

    void close(); // 关闭事务管理器
}
