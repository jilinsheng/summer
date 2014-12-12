package com.mingda.dao;

import com.mingda.common.BaseHibernateDAO;
import com.mingda.entity.NetTNotice;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * NetTNotice entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.mingda.entity.NetTNotice
 * @author MyEclipse Persistence Tools
 */

public class NetTNoticeDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(NetTNoticeDAO.class);
	// property constants
	public static final String TITLE = "title";
	public static final String CONTENT = "content";
	public static final String AUTHOR = "author";
	public static final String ORGAN = "organ";
	public static final String STATE = "state";

	public String save(NetTNotice transientInstance) {
		log.debug("saving NetTNotice instance");
		String id = "";
		try {
			getSession().save(transientInstance);
			id = transientInstance.getNoticeId().toString();
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
		return id;
	}

	public void delete(NetTNotice persistentInstance) {
		log.debug("deleting NetTNotice instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public NetTNotice findById(java.lang.Long id) {
		log.debug("getting NetTNotice instance with id: " + id);
		try {
			NetTNotice instance = (NetTNotice) getSession().get(
					"com.mingda.entity.NetTNotice", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(NetTNotice instance) {
		log.debug("finding NetTNotice instance by example");
		try {
			List results = getSession().createCriteria(
					"com.mingda.entity.NetTNotice").add(
					Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List<NetTNotice> findNoticeClient(String organizationid) {
		log.debug("finding NetTNotice instance by example");
		try {
			List<NetTNotice> results = new ArrayList<NetTNotice>();
			List<NetTNotice> notices = this.findNotices(organizationid);
			List<NetTNotice> annouces = this.findAnnouces(organizationid);
			for(NetTNotice notice : notices){
				results.add(notice);
			}
			for(NetTNotice annouce : annouces){
				results.add(annouce);
			}
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List<NetTNotice> findNotices(String organizationid) {
		log.debug("finding NetTNotice instance by example");
		try {
			String sql = "from NetTNotice as model  where model.noticetype='1' and  exists (select 1 from model.netTReceives  reces where reces.sysTOrganization='"
					+ organizationid + "') order by model.issuetime desc";
			List<NetTNotice> results = getSession().createQuery(sql).list();
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List<NetTNotice> findAnnouces(String organizationid) {
		log.debug("finding NetTNotice instance by example");
		try {
			String sql = "from NetTNotice as model  where model.noticetype='2' and instr('a'||'"+organizationid+"', 'a'||model.organ) > 0  order by model.issuetime desc";
			List<NetTNotice> results = getSession().createQuery(sql).list();
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding NetTNotice instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from NetTNotice as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByTitle(Object title) {
		return findByProperty(TITLE, title);
	}

	public List findByContent(Object content) {
		return findByProperty(CONTENT, content);
	}

	public List findByAuthor(Object author) {
		return findByProperty(AUTHOR, author);
	}

	public List findByOrgan(Object organ) {
		return findByProperty(ORGAN, organ);
	}

	public List findByState(Object state) {
		return findByProperty(STATE, state);
	}

	public List findAll() {
		log.debug("finding all NetTNotice instances");
		try {
			String queryString = "from NetTNotice";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public NetTNotice merge(NetTNotice detachedInstance) {
		log.debug("merging NetTNotice instance");
		try {
			NetTNotice result = (NetTNotice) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(NetTNotice instance) {
		log.debug("attaching dirty NetTNotice instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(NetTNotice instance) {
		log.debug("attaching clean NetTNotice instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}