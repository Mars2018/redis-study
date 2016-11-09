package com.mars.redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * redis to cache
 * Created by MarsWang on 2016/10/13.
 */
@Service
public class RedisCache<T> {

    @Autowired
    @Qualifier("redisTemplate")
    public RedisTemplate redisTemplate;

    /**
     * 缓存基本对象， Integer, String, 实体类等
     * @param key 缓存的键值
     * @param value 缓存的值
     * @return 缓存的对象
     */
    public ValueOperations<String, T> setCacheObject(String key, T value){
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        operation.set(key, value);
        return operation;
    }

    /**
     * 获得缓存的基本对象
     * @param key 缓存的键值
     * @return 缓存键值对应的数据
     */
    public T getCacheObject(String key){
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        return operation.get(key);
    }

    /**
     * 缓存list对象
     * @param key 缓存的键值
     * @param dataList 待缓存的list数据
     * @return 缓存的对象
     */
    public ListOperations<String, T> setCacheList(String key, List<T> dataList){
        ListOperations<String, T> operation = redisTemplate.opsForList();
        if(null != dataList){
            int size = dataList.size();
            for(int i = 0; i < size; ++i){
                operation.rightPush(key, dataList.get(i));
            }
        }
        return operation;

    }

    /**
     * 获得缓存的list对象
      * @param key 缓存的键值
     * @return 缓存键值对应的数据
     */
    public List<T> getCacheList(String key){
        ListOperations<String , T> operation = redisTemplate.opsForList();
        List<T> dataList = new ArrayList<T>();
        Long size = operation.size(key);
        for (int i = 0; i < size; ++i)
            dataList.add((T)operation.leftPop(key));
        return dataList;
    }

    /**
     * 缓存Set
     * @param key 缓存的键值
     * @param dataSet 缓存的数据
     * @return 缓存的数据对象
     */
    public BoundSetOperations<String, T> setCacheSet(String key, Set<T> dataSet){
        BoundSetOperations<String, T> operation = redisTemplate.boundSetOps(key);

        Iterator<T> iterator = dataSet.iterator();
        while (iterator.hasNext())
            operation.add(iterator.next());
        return operation;
    }

    /**
     * 获得缓存的Set对象
     * @param key 缓存的键值
     * @return 缓存键值对应的数据
     */
    public Set<T> getCacheSet(String key){
        Set<T> dataSet = new HashSet<>();
        BoundSetOperations<String, T> operation = redisTemplate.boundSetOps(key);
        Long size = operation.size();
        for (int i= 0; i < size; ++i)
            dataSet.add(operation.pop());
        return dataSet;
    }

    /**
     * 缓存Map
     * @param key 缓存的键值
     * @param dataMap 缓存的数据
     * @return 缓存的数据对象
     */
    public HashOperations<String, String, T> setCacheMap(String key, Map<String, T> dataMap){
        HashOperations<String, String, T> operation = redisTemplate.opsForHash();
        if (null != dataMap){
            for (Map.Entry<String , T> entry : dataMap.entrySet())
                operation.put(key, entry.getKey(), entry.getValue());
        }
        return operation;
    }

    /**
     * 获得缓存的Map
     * @param key 缓存的键值
     * @return 缓存键值对应的数据
     */
    public Map<String, T> getCacheMap(String key){
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 缓存Integer Map
     * @param key 缓存的键值
     * @param dataMap 缓存的数据
     * @return 缓存的数据对象
     */
    public  HashOperations<String , Integer, T> setCacheIntegerMap(String key, Map<Integer, T> dataMap){
        HashOperations<String, Integer, T> operation = redisTemplate.opsForHash();
        if(dataMap != null){
            for (Map.Entry<Integer, T> entry : dataMap.entrySet())
                operation.put(key, entry.getKey(), entry.getValue());
        }
        return operation;
    }

    /**
     * 获得缓存的Integer Map
     * @param key 缓存的键值
     * @return 缓存键值对应的数据
     */
    public Map<Integer, T> getIntegerMap(String key){
        return redisTemplate.opsForHash().entries(key);
    }


}
