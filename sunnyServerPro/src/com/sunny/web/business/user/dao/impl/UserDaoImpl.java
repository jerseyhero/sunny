package com.sunny.web.business.user.dao.impl;

import org.springframework.stereotype.Repository;

import com.sunny.web.business.user.dao.UserDao;
import com.sunny.web.business.user.model.User;
import com.sunny.web.core.base.impl.BaseDaoImpl;
@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao{

	public UserDaoImpl() {
		super(User.class);
		// TODO Auto-generated constructor stub
	}


	@Override
	public User queryByName(String userName) {
		 String hql = "from User u where u.userName = ?";
         return queryForObject(hql, new Object[] { userName });
	}

	

}
