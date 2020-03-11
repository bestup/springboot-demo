package com.demo.service.impl;

import com.demo.entity.User;
import com.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tanglong
 * @date 2020-03-11
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private static final Map<Integer, User> DATABASES = new HashMap<>();

    static {
        DATABASES.put(1, new User(1, "u1", "p1"));
        DATABASES.put(2, new User(2, "u2", "p2"));
        DATABASES.put(3, new User(3, "u3", "p3"));
    }

    @Cacheable(value = "user", key = "#id")
    @Override
    public User get(Integer id) {
        // TODO 我们就假设它是从数据库读取出来的
        log.info("进入 get 方法");
        return DATABASES.get(id);
    }

    @CachePut(value = "user", key = "#user.id")
    @Override
    public User saveOrUpdate(User user) {
        DATABASES.put(user.getId(), user);
        log.info("进入 saveOrUpdate 方法");
        return user;
    }

    @CacheEvict(value = "user", key = "#id")
    @Override
    public void delete(Integer id) {
        DATABASES.remove(id);
        log.info("进入 delete 方法");
    }
}
