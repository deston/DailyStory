package com.deston.base.cache;

public interface Cache<T> {
    public T get(String key);
    public void put(String key, T value);
}
