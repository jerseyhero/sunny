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
     * ����hql��ѯ,ֱ��ʹ��HibernateTemplate��find����.
     * 
     * @param values���ɱ����
     */
    @SuppressWarnings("unchecked")
    public List<T> find(String hql, Object... values) throws DataAccessException {
	Assert.hasText(hql);
	return getHibernateTemplate().find(hql, values);
    }

    /**
     * ��HQL��ҳ��ѯ.
     * 
     * @param page ��ҳ����. ע�ⲻ֧�����е�orderBy����.
     * @param hql hql���.
     * @param values �����ɱ�Ĳ�ѯ����,��˳���.
     * 
     * <a href="http://my.oschina.net/u/556800" target="_blank" rel="nofollow">@return</a>  ��ҳ��ѯ���, ��������б����в�ѯ�������.
     */
    @SuppressWarnings("unchecked")
    public Page<T> findPage(final Page<T> page, final String hql, final Object... values) {
	Assert.notNull(page, "page����Ϊ��");

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
     * ��HQL��ҳ��ѯ.
     * 
     * @param page ��ҳ����. ע�ⲻ֧�����е�orderBy����.
     * @param hql hql���.
     * @param values �����ɱ�Ĳ�ѯ����,��˳���.
     * 
     * <a href="http://my.oschina.net/u/556800" target="_blank" rel="nofollow">@return</a>  ��ҳ��ѯ���, ��������б����в�ѯ�������.
     */
    @SuppressWarnings("unchecked")
    public Page<T> findPage(final Page<T> page, final String hql, Map paramMap) {
	Assert.notNull(page, "page����Ϊ��");
	
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
     * ��SQL��ҳ��ѯ.
     * 
     * @param page ��ҳ����. ע�ⲻ֧�����е�orderBy����.
     * @param hql hql���.
     * @param values �����ɱ�Ĳ�ѯ����,��˳���.
     * 
     * <a href="http://my.oschina.net/u/556800" target="_blank" rel="nofollow">@return</a>  ��ҳ��ѯ���, ��������б����в�ѯ�������.
     */
    @SuppressWarnings("unchecked")
    public Page findPageBySql(final Page page, final String sql, final Object... values) {
	Assert.notNull(page, "page����Ϊ��");

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
    //���ط��� ����Map ���󼯺�
    @SuppressWarnings("unchecked")
    public Page findPageBySql(final Page page, final String sql,boolean isReturnMap, final Object... values) {
	Assert.notNull(page, "page����Ϊ��");

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
     * ���ݲ�ѯSQL������б���Query����. ��find()�����ɽ��и������Ĳ���.
     * 
     * @param values �����ɱ�Ĳ���,��˳���.
     */
    public Query createQuerySQL(final String sql, final Object... values) {
	Assert.hasText(sql, "sql����Ϊ��");
	Query query = getSession().createSQLQuery(sql);
	if (values != null) {
	    for (int i = 0; i < values.length; i++) {
		query.setParameter(i, values[i]);
	    }
	}
	return query;
    }
    
    /**
     * ִ��count��ѯ��ñ���sql��ѯ���ܻ�õĶ�������.
     * 
     * ������ֻ���Զ�����򵥵�hql���,���ӵ�hql��ѯ�����б�дcount����ѯ.
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
     * ���ݲ�ѯHQL������б���Query����. ��find()�����ɽ��и������Ĳ���.
     * 
     * @param values �����ɱ�Ĳ���,��˳���.
     */
    public Query createQuery(final String queryString, final Object... values) {
	Assert.hasText(queryString, "queryString����Ϊ��");
	Query query = getSession().createQuery(queryString);
	if (values != null) {
	    for (int i = 0; i < values.length; i++) {
		query.setParameter(i, values[i]);
	    }
	}
	return query;
    }
    /**
     * ���ݲ�ѯHQL������б���Query����. ��find()�����ɽ��и������Ĳ���.
     * 
     * @param paramMap ��������
     */
    public Query createQuery(final String queryString, final Map<String,?> paramMap) {
	Assert.hasText(queryString, "queryString����Ϊ��");
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
     * ִ��count��ѯ��ñ���SQL��ѯ���ܻ�õĶ�������.
     * 
     * ������ֻ���Զ�����򵥵�hql���,���ӵ�hql��ѯ�����б�дcount����ѯ.
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
     * ִ��count��ѯ��ñ���SQL��ѯ���ܻ�õĶ�������.
     * 
     * ������ֻ���Զ�����򵥵�hql���,���ӵ�hql��ѯ�����б�дcount����ѯ.
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
	// select�Ӿ���order by�Ӿ��Ӱ��count��ѯ,���м򵥵��ų�.
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
     * ��SQL��ѯΨһ����.
     * 
     * @param values �����ɱ�Ĳ���,��˳���.
     */
    public Long findUniqueSQL(final String sql, final Object... values) {
	return ((BigDecimal) createQuerySQL(sql, values).uniqueResult()).longValue();
    }
    
    /**
     * ��HQL��ѯΨһ����.
     * 
     * @param values �����ɱ�Ĳ���,��˳���.
     */
    public Long findUnique(final String hql, final Object... values) {
	return (Long) createQuery(hql, values).uniqueResult();
    }
    /**
     * ��HQL��ѯΨһ����.
     * 
     * @param paramMap �����ɱ�Ĳ���,��˳���.
     */
    public Long findUnique(final String hql, final Map<String,Object> paramMap) {
	return (Long) createQuery(hql, paramMap).uniqueResult();
    }

    /**
     * ���÷�ҳ������Query����,��������.
     */
    protected Query setPageParameterToQuery(final Query q, final Page<T> page) {

	Assert.isTrue(page.getPageSize() > 0, "Page Size must larger than zero");

	// hibernate��firstResult����Ŵ�0��ʼ
	q.setFirstResult(page.getFirst() - 1);
	q.setMaxResults(page.getPageSize());
	return q;
    }
}