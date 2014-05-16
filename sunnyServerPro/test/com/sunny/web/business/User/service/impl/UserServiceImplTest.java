package com.sunny.web.business.User.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.sunny.web.business.user.dao.UserDao;
import com.sunny.web.business.user.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
@Transactional
public class UserServiceImplTest {
	@Autowired
	private UserDao userDao;
	
	@Test
	public void testQueryByName() {
		User user =  userDao.queryByName("2");
		System.out.println(user.getPassWord());
	}

}
