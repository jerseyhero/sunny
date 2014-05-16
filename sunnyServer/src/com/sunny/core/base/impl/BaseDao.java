package com.sunny.core.base.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.sunny.core.base.EntityDao;
@Repository
public class BaseDao<T extends Serializable> extends HibernateDaoSupport implements EntityDao<T>{
	@Override
	public List<T> find(String hql,Object[] obj){
		return getHibernateTemplate().find(hql,obj);
	}
	
}
