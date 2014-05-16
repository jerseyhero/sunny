package com.sunny.bis.user.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sunny.bis.user.model.User;
import com.sunny.core.base.impl.BaseDao;
@Repository
public class UserDao extends BaseDao<User>{
	
	public boolean checkLoginInfo(User user){
		List<User> userList =  super.find("form user where userName = ? and passWord = ?", new Object[]{user.getUserName(),user.getPassWord()});
		if(userList!=null&&userList.size()==1) return true;
		return false;
	}
}
