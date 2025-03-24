package org.example;

import org.example.helper.RedisClientConnection;

import java.util.Arrays;
import java.util.List;

/**
 * RemoteLock is a class that implements a remote lock using Redis.
 * It provides a method to acquire a lock and a method to release a lock.
 * The lock is acquired by setting a key in Redis with a TTL.
 * The lock is released by deleting the key from Redis.
 * The lock is acquired by the first client to set the key.
 * The lock is released by the client that acquired the lock.
 * @usage -> We have one queue and list of consumers, we e
 */
public class RemoteLock {
    static RedisClientConnection redisClientConnection = new RedisClientConnection();
    public static void main(String[] args) {
        String queueKey = "q1";
        for(int i = 0; i<10; i++){
            int finalI = i;
            Thread thread = new Thread(() -> {
                processQueue(queueKey, finalI);
            });
            thread.start();
        }
    }
    public static boolean acquireLock(String lockKey, int consumerId){
        return redisClientConnection.setIfAbsent(lockKey, Integer.toString(consumerId));
    }
    private static void releaseLock(String lockKey, int consumerId){
        String luaScript = 
                "if redis.call('get', KEYS[1]) == ARGV[1] then " +  // Check if we own the lock
                "    redis.call('del', KEYS[1]) " +                  // Delete the key
                "    return '1' " +                                  // Return success
                "else " +
                "    return '0' " +                                  // Return failure
                "end";
        redisClientConnection.eval(luaScript,lockKey, Integer.toString(consumerId));
    }
    private static void processQueue(String queueKey, int consumerId){
        while(true){
            if(acquireLock(queueKey, consumerId)){
                System.out.println("Consumer " + consumerId + " acquired lock");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                releaseLock(queueKey, consumerId);
                System.out.println("Consumer " + consumerId + " released lock");
                break;
            }
            else{
                System.out.println("Consumer " + consumerId + " not acquired lock");
            }
        }
    }
}
