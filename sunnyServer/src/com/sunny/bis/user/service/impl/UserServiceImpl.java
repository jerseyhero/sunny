package com.sunny.bis.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunny.bis.user.dao.UserDao;
import com.sunny.bis.user.model.User;
import com.sunny.bis.user.service.UserService;
@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserDao userDao;
	@Override
	public boolean checkLoginInfo(User user) {
		return userDao.checkLoginInfo(user);
	}

}
