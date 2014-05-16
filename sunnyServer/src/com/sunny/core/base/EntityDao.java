package com.sunny.core.base;

import java.util.List;

public interface EntityDao<T> {
	
	/** 
	* @author sqy 
	* @email jerseyhero@foxmail.com 
	* @date 2014年5月13日 下午9:47:30 
	* @Description: TODO 通过HQL，参数 获取 所查询的结果集
	*/
	public List<T> find(String hql,Object[] obj);
	
	
}
