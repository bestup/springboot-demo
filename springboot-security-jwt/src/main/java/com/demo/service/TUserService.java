package com.demo.service;

import com.demo.entity.TUser;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2019-06-23
 */
public interface TUserService extends IService<TUser> {
	
	TUser findTUser(String username,String password);
	
	TUser register(String username,String password);

}
