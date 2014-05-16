package com.sunny.web.core.base;

import java.util.List;

public interface BaseDao<T> {
	void insert(T t);
	void delete(T t);
	void update(T t);
	T queryById(String id);
	List<T> queryAll();
}
