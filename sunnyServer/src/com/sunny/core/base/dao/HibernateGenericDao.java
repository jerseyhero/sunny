package com.sunny.core.base.dao;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.sunny.core.base.web.Page;

@Repository("hibernateGenericDao")
public class HibernateGenericDao<T> extends HibernateDaoSupport {

    @Resource(name = "sessionFactory")
    public void setSuperSessionFactory(SessionFactory sessionFactory) {
	super.setSessionFactory(sessionFactory);
    }

    /**
     * 根据hql查询,直接使用HibernateTemplate的find函数.
     * 
     * @param values：可变参数
     */
    @SuppressWarnings("unchecked")
    public List<T> find(String hql, Object... values) throws DataAccessException {
	Assert.hasText(hql);
	return getHibernateTemplate().find(hql, values);
    }

    /**
     * 按HQL分页查询.
     * 
     * @param page 分页参数. 注意不支持其中的orderBy参数.
     * @param hql hql语句.
     * @param values 数量可变的查询参数,按顺序绑定.
     * 
     * <a href="http://my.oschina.net/u/556800" target="_blank" rel="nofollow">@return</a>  分页查询结果, 附带结果列表及所有查询输入参数.
     */
    @SuppressWarnings("unchecked")
    public Page<T> findPage(final Page<T> page, final String hql, final Object... values) {
	Assert.notNull(page, "page不能为空");

	Query q = createQuery(hql, values);

	if (page.isAutoCount()) {
	    long totalCount = countHqlResult(hql, values);
	    page.setTotalCount(totalCount);
	}

	setPageParameterToQuery(q, page);

	List result = q.list();
	page.setResult(result);
	return page;
    }
    
    /**
     * 按HQL分页查询.
     * 
     * @param page 分页参数. 注意不支持其中的orderBy参数.
     * @param hql hql语句.
     * @param values 数量可变的查询参数,按顺序绑定.
     * 
     * <a href="http://my.oschina.net/u/556800" target="_blank" rel="nofollow">@return</a>  分页查询结果, 附带结果列表及所有查询输入参数.
     */
    @SuppressWarnings("unchecked")
    public Page<T> findPage(final Page<T> page, final String hql, Map paramMap) {
	Assert.notNull(page, "page不能为空");
	
	Query q = createQuery(hql, paramMap);
	
	if (page.isAutoCount()) {
	    long totalCount = countHqlResult(hql, paramMap);
	    page.setTotalCount(totalCount);
	}
	
	setPageParameterToQuery(q, page);
	
	List result = q.list();
	page.setResult(result);
	return page;
    }
    
    
    /**
     * 按SQL分页查询.
     * 
     * @param page 分页参数. 注意不支持其中的orderBy参数.
     * @param hql hql语句.
     * @param values 数量可变的查询参数,按顺序绑定.
     * 
     * <a href="http://my.oschina.net/u/556800" target="_blank" rel="nofollow">@return</a>  分页查询结果, 附带结果列表及所有查询输入参数.
     */
    @SuppressWarnings("unchecked")
    public Page findPageBySql(final Page page, final String sql, final Object... values) {
	Assert.notNull(page, "page不能为空");

	Query q = createQuerySQL(sql, values);

	if (page.isAutoCount()) {
	    long totalCount = countSqlResult(sql, values);
	    page.setTotalCount(totalCount);
	}
	q.setFirstResult(page.getFirst() - 1);
	q.setMaxResults(page.getPageSize());
	
	List result = q.list();
	page.setResult(result);
	return page;
    }
    //重载方法 返回Map 对象集合
    @SuppressWarnings("unchecked")
    public Page findPageBySql(final Page page, final String sql,boolean isReturnMap, final Object... values) {
	Assert.notNull(page, "page不能为空");

	Query q = createQuerySQL(sql, values);

	if (page.isAutoCount()) {
	    long totalCount = countSqlResult(sql, values);
	    page.setTotalCount(totalCount);
	}
	q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
	q.setFirstResult(page.getFirst() - 1);
	q.setMaxResults(page.getPageSize());
	
	List result = q.list();
	page.setResult(result);
	return page;
    }    
    
    /**
     * 根据查询SQL与参数列表创建Query对象. 与find()函数可进行更加灵活的操作.
     * 
     * @param values 数量可变的参数,按顺序绑定.
     */
    public Query createQuerySQL(final String sql, final Object... values) {
	Assert.hasText(sql, "sql不能为空");
	Query query = getSession().createSQLQuery(sql);
	if (values != null) {
	    for (int i = 0; i < values.length; i++) {
		query.setParameter(i, values[i]);
	    }
	}
	return query;
    }
    
    /**
     * 执行count查询获得本次sql查询所能获得的对象总数.
     * 
     * 本函数只能自动处理简单的hql语句,复杂的hql查询请另行编写count语句查询.
     */
    protected long countSqlResult(final String hql, final Object... values) {
	String countHql = prepareCountSQL(hql);

	try {
	    Long count = findUniqueSQL(countHql, values);
	    return count;
	} catch (Exception e) {
	    throw new RuntimeException("hql can't be auto count, hql is:" + countHql, e);
	}
    }

    
    /**
     * 根据查询HQL与参数列表创建Query对象. 与find()函数可进行更加灵活的操作.
     * 
     * @param values 数量可变的参数,按顺序绑定.
     */
    public Query createQuery(final String queryString, final Object... values) {
	Assert.hasText(queryString, "queryString不能为空");
	Query query = getSession().createQuery(queryString);
	if (values != null) {
	    for (int i = 0; i < values.length; i++) {
		query.setParameter(i, values[i]);
	    }
	}
	return query;
    }
    /**
     * 根据查询HQL与参数列表创建Query对象. 与find()函数可进行更加灵活的操作.
     * 
     * @param paramMap 过滤条件
     */
    public Query createQuery(final String queryString, final Map<String,?> paramMap) {
	Assert.hasText(queryString, "queryString不能为空");
	Query query = getSession().createQuery(queryString);
	if (paramMap != null) {
	    Iterator<String> it= paramMap.keySet().iterator();
	    while (it.hasNext()) {
		String key = (String) it.next();
		Object value=paramMap.get(key);
		if(value instanceof Object[]){
		    query.setParameterList(key, (Object[])value);
		}else{
		    query.setParameter(key, value);
		}
	    }
	}
	return query;
    }

    /**
     * 执行count查询获得本次SQL查询所能获得的对象总数.
     * 
     * 本函数只能自动处理简单的hql语句,复杂的hql查询请另行编写count语句查询.
     */
    protected long countHqlResult(final String hql, final Object... values) {
	String countHql = prepareCountHql(hql);

	try {
	    Long count = findUnique(countHql, values);
	    return count;
	} catch (Exception e) {
	    throw new RuntimeException("hql can't be auto count, hql is:" + countHql, e);
	}
    }
    /**
     * 执行count查询获得本次SQL查询所能获得的对象总数.
     * 
     * 本函数只能自动处理简单的hql语句,复杂的hql查询请另行编写count语句查询.
     */
    protected long countHqlResult(final String hql, final Map<String, Object> paramMap) {
	String countHql = prepareCountHql(hql);
	
	try {
	    Long count = findUnique(countHql, paramMap);
	    return count;
	} catch (Exception e) {
	    throw new RuntimeException("hql can't be auto count, hql is:" + countHql, e);
	}
    }

    private String prepareCountHql(String orgHql) {
	String fromHql = orgHql;
	// select子句与order by子句会影响count查询,进行简单的排除.
	fromHql = "from " + StringUtils.substringAfter(fromHql, "from");
	fromHql = StringUtils.substringBefore(fromHql, "order by");

	String countHql = "select count(*) " + fromHql;
	return countHql;
    }

    private String prepareCountSQL(String orgSql) {
	String countSql = "select count(*) from (" + orgSql+") tt";
	return countSql;
    }
    
    /**
     * 按SQL查询唯一对象.
     * 
     * @param values 数量可变的参数,按顺序绑定.
     */
    public Long findUniqueSQL(final String sql, final Object... values) {
	return ((BigDecimal) createQuerySQL(sql, values).uniqueResult()).longValue();
    }
    
    /**
     * 按HQL查询唯一对象.
     * 
     * @param values 数量可变的参数,按顺序绑定.
     */
    public Long findUnique(final String hql, final Object... values) {
	return (Long) createQuery(hql, values).uniqueResult();
    }
    /**
     * 按HQL查询唯一对象.
     * 
     * @param paramMap 数量可变的参数,按顺序绑定.
     */
    public Long findUnique(final String hql, final Map<String,Object> paramMap) {
	return (Long) createQuery(hql, paramMap).uniqueResult();
    }

    /**
     * 设置分页参数到Query对象,辅助函数.
     */
    protected Query setPageParameterToQuery(final Query q, final Page<T> page) {

	Assert.isTrue(page.getPageSize() > 0, "Page Size must larger than zero");

	// hibernate的firstResult的序号从0开始
	q.setFirstResult(page.getFirst() - 1);
	q.setMaxResults(page.getPageSize());
	return q;
    }
}