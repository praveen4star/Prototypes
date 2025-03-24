package org.example.helper;
import io.lettuce.core.*;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.protocol.RedisCommand;

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
}
