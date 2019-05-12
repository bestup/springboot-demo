package com.service;

import com.entity.User;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2019-05-12
 */
public interface UserService extends IService<User> {
	
	public User findOne(Integer id);
}
