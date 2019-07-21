package com.demo.service.impl;

import com.demo.entity.TUser;
import com.demo.mapper.TUserMapper;
import com.demo.service.TUserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author
 * @since 2019-06-23
 */
@Service
public class TUserServiceImpl extends ServiceImpl<TUserMapper, TUser> implements TUserService,UserDetailsService{
	
	@Autowired
	TUserMapper tUserMapper;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public TUser findTUser(String username,String password) {
		TUser user = new TUser();
		user.setUsername(username);
		user.setPassword(password);
		
		//tUserMapper.selectOne(entity);
		
		return null;
	}

	@Override
	public TUser register(String username,String password) {
		String id = UUID.randomUUID().toString().replaceAll("-", "");
		String encodePassword = passwordEncoder.encode(password);
		TUser tUser = new TUser();
		tUser.setUsername(username);
		tUser.setPassword(encodePassword);
		tUser.setId(id);
		int row = tUserMapper.insert(tUser);
		if(row > 0) {
			return tUser;
		}
		return null;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//构建角色
		List<GrantedAuthority> grantAuths = new ArrayList<>();
		
		grantAuths.add(new SimpleGrantedAuthority("ROLE_SELLER"));
		
		TUser searchUser = new TUser();
		searchUser.setUsername(username);
		TUser user = tUserMapper.selectOne(searchUser);
		return new User(username,user.getPassword(),grantAuths);
	}

}
