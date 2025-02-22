package org.example.database;

import java.sql.Connection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class ConnectionPool {
    private final MySQLConnection connection;
    private final int maxPoolSize;
    private final BlockingQueue<Connection> pool;
    private final AtomicInteger counter = new AtomicInteger(0);
    public ConnectionPool(int minPoolSize, int maxPoolSize) throws InterruptedException {
        this.connection = new MySQLConnection();
        this.maxPoolSize = maxPoolSize;
        this.pool =  new LinkedBlockingQueue<>(maxPoolSize);
        for(int i = 0; i<minPoolSize; i++) {
            pool.put(connection.getConnection());
        }
        counter.set(minPoolSize);
    }
    public Connection getConnection() throws InterruptedException {
        synchronized (this){
            if(counter.get() < maxPoolSize){
                pool.put(connection.getConnection());
                counter.incrementAndGet();
            }
        }
        return pool.take();
    }
    public synchronized void releaseConnection(Connection connection) throws InterruptedException {
        if(connection != null){
            pool.offer(connection);
        }
    }
}
