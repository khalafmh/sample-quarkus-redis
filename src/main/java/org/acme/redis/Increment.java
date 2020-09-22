package org.acme.redis;

public class Increment {
    public String key;
    public Integer value;

    public Increment(String key, Integer value) {
        this.key = key;
        this.value = value;
    }

    public Increment() {
    }
}
