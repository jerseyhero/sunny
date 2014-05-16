package com.sunny.web.business.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunny.web.business.user.dao.UserDao;
import com.sunny.web.business.user.model.User;
import com.sunny.web.business.user.service.UserService;
@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserDao userDao;

	@Override
	public User queryByName(String userName) {
		return userDao.queryByName(userName);
	}
	
}
