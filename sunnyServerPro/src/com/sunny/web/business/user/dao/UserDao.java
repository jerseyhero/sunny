package com.sunny.web.business.user.dao;

import com.sunny.web.business.user.model.User;
import com.sunny.web.core.base.BaseDao;

public interface UserDao extends BaseDao<User>{
	public User queryByName(String userName);
}
