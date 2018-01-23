package com.scut.originsystem.util;

public class PairUtil<K,V>{
    private K key;
    private V value;

    public PairUtil(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }
}
