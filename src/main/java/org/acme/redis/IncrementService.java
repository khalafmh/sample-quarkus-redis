package org.acme.redis;

import io.quarkus.redis.client.RedisClient;
import io.quarkus.redis.client.reactive.ReactiveRedisClient;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.redis.client.Response;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class IncrementService {

    @Inject
    RedisClient redisClient;

    @Inject
    ReactiveRedisClient reactiveRedisClient;

    Uni<Void> del(String key) {
        return reactiveRedisClient.del(List.of(key))
                .map(response -> null);
    }

    String get(String key) {
        return redisClient.get(key).toString();
    }

    void set(String key, Integer value) {
        redisClient.set(List.of(key, value.toString()));
    }

    void increment(String key, Integer incrementBy) {
        redisClient.incrby(key, incrementBy.toString());
    }

    Uni<List<String>> keys() {
        return reactiveRedisClient
                .keys("*")
                .map(response -> {
                    ArrayList<String> result = new ArrayList<>();
                    for (Response r : response) {
                        result.add(r.toString());
                    }
                    return result;
                });
    }

    List<String> keysSync() {
        return redisClient.keys("*").stream().map(Object::toString).collect(Collectors.toList());
    }
}
