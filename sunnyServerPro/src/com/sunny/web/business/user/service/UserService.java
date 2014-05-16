package com.sunny.web.business.user.service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sunny.web.business.user.model.User;

@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
public interface UserService {
	User queryByName(String userName);
}
