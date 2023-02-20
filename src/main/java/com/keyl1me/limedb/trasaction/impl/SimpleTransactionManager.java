package com.keyl1me.limedb.trasaction.impl;

import com.keyl1me.limedb.exceptions.FileExistException;
import com.keyl1me.limedb.exceptions.FileNotExistException;
import com.keyl1me.limedb.exceptions.FileWriteException;
import com.keyl1me.limedb.trasaction.TransactionManager;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SimpleTransactionManager implements TransactionManager {

    private static final int TID_HEAD_LENGTH = 8;
    private static final int TID_TRANSACTION_LENGTH = 1;

    private static final byte TID_STATUS_ACTIVE = 0;
    private static final byte TID_STATUS_COMMITTED = 1;
    private static final byte TID_STATUS_ABORTED = 2;

    private static final byte TID_STATUS_SUPER = 0;

    private static final String TID_SUFFIX = ".tid";

    private long transactionCount;

    private FileChannel channel; // 文件通道

    private SimpleTransactionManager(FileChannel channel, long transactionCount) {
        this.channel = channel;
        this.transactionCount = transactionCount;
    }

    public static SimpleTransactionManager create(String path) {
        Path filePath = Paths.get(path);
        try {
            Files.createFile(filePath);
        } catch (IOException e) {
            throw new FileExistException("同名事务文件已存在");
        }

        File file = new File(path);
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            throw new FileNotExistException("事务文件不存在");
        }
        FileChannel channel = fileOutputStream.getChannel();
        ByteBuffer buf = ByteBuffer.wrap(new byte[TID_HEAD_LENGTH]);
        try {
            channel.position(0);
            channel.write(buf);
        } catch (IOException e) {
            throw new FileWriteException("写入头文件错误");
        }
        return new SimpleTransactionManager(channel, 0);
    }

    public static SimpleTransactionManager open(String path) {
        File file = new File(path);
        FileInputStream fileInputStream;
        try {
             fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new FileNotExistException("事务文件不存在");
        }
        FileChannel channel = fileInputStream.getChannel();
        ByteBuffer buf = ByteBuffer.allocate(TID_HEAD_LENGTH);
        try {
            channel.position(0);
            channel.read(buf);
        } catch (IOException e) {
            throw new FileWriteException("读取头文件错误");
        }
        long transactionCount = buf.getLong();
        System.out.println(transactionCount);
        return new SimpleTransactionManager(channel, transactionCount);
    }

    @Override
    public long begin() {
        return 0;
    }

    @Override
    public void commit(long tid) {

    }

    @Override
    public void abort(long tid) {

    }

    @Override
    public boolean isActive(long tid) {
        return false;
    }

    @Override
    public boolean isCommitted(long tid) {
        return false;
    }

    @Override
    public boolean isAborted(long tid) {
        return false;
    }

    @Override
    public void close() {

    }

    private boolean checkTransactionFile() throws IOException {
        return true;
    }
}
