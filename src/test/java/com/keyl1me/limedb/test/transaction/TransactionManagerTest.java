package com.keyl1me.limedb.test.transaction;

import com.keyl1me.limedb.trasaction.impl.SimpleTransactionManager;
import org.testng.annotations.Test;

import java.io.IOException;

public class TransactionManagerTest {

    @Test
    public void testTransactionManager() throws IOException {
        SimpleTransactionManager transactionManager = SimpleTransactionManager.create("transaction.tid");
        SimpleTransactionManager transactionManagerUpdate = SimpleTransactionManager.open("transaction.tid");
    }
}
