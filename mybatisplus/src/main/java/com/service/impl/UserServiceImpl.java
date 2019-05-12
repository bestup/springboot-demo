package com.service.impl;

import com.entity.User;
import com.mapper.UserMapper;
import com.service.UserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author
 * @since 2019-05-12
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

	@Autowired
	private UserMapper userMapper;
	
	@Override
	public User findOne(Integer id) {
		// TODO Auto-generated method stub
		return userMapper.findOne(id);
	}

}
