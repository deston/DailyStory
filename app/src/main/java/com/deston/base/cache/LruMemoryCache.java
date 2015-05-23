package com.deston.base.cache;

/**
 * 页面网络请求获取到的数据缓存，生命周期伴随整个应用的生命周期
 * 基于最近最少使用的策略
 */
public class LruMemoryCache implements Cache{

    public Object get(String key) {
        return null;
    }

    @Override
    public void put(String key, Object value) {

    }



}
