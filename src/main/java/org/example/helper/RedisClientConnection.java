package org.example.helper;
import io.lettuce.core.*;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;


public class RedisClientConnection {
    StatefulRedisConnection<String, String> connection;
    public RedisClientConnection() {
        RedisURI redisURI = RedisURI.Builder.redis("localhost", 6379).build();
        RedisClient client = RedisClient.create(redisURI);
        this.connection = client.connect();
    }
    public StatefulRedisConnection<String, String> getConnection() {
        return connection;
    }
    public void set(String key, String value) {
        RedisCommands<String, String> sync = connection.sync();
        sync.set(key, value);
    }
    public boolean setIfAbsent(String key, String value) {
        RedisCommands<String, String> sync = connection.sync();
        Boolean result = sync.setnx(key, value);
        if (result) {
            sync.expire(key, 10); // Set expiry to 1 second if key was set
        }
        return result;
    }
    public String get(String key) {
        RedisCommands<String, String> sync = connection.sync();
        return sync.get(key);
    }
    public String eval(String script, String key, String value) {
        RedisCommands<String, String> sync = connection.sync();
        
        // Modify your Lua script to return strings instead of numbers
        // For example, if your script returns 1 or 0, change it to return '1' or '0'
        return sync.eval(script, ScriptOutputType.VALUE, new String[]{key}, value);
    }
}
