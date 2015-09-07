package com.deston.base.cache;

import android.support.v4.util.LruCache;

/**
 * 页面网络请求获取到的数据缓存，生命周期伴随整个应用的生命周期
 * 基于最近最少使用的策略
 */
public class LruMemoryCache implements Cache{
    private LruCache<String, Object> lruCache = new LruCache<String, Object>(1024*1024);
    public Object get(String key) {
        return lruCache.get(key);
    }

    @Override
    public void put(String key, Object value) {
        lruCache.put(key, value);
    }



}
