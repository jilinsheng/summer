package com.mingda.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

import com.mingda.common.BaseHibernateDAO;
import com.mingda.entity.BizTOptcheckchildidea;

/**
 	* A data access object (DAO) providing persistence and search support for BizTOptcheckchildidea entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.mingda.entity.BizTOptcheckchildidea
  * @author MyEclipse Persistence Tools 
 */

public class BizTOptcheckchildideaDAO extends BaseHibernateDAO  {
    private static final Log log = LogFactory.getLog(BizTOptcheckchildideaDAO.class);
	//property constants
	public static final String CHILDMONEY = "childmoney";
	public static final String CHILDNOTE = "childnote";
	public static final String STATUS = "status";



    
    public void save(BizTOptcheckchildidea transientInstance) {
        log.debug("saving BizTOptcheckchildidea instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(BizTOptcheckchildidea persistentInstance) {
        log.debug("deleting BizTOptcheckchildidea instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public BizTOptcheckchildidea findById( java.math.BigDecimal id) {
        log.debug("getting BizTOptcheckchildidea instance with id: " + id);
        try {
            BizTOptcheckchildidea instance = (BizTOptcheckchildidea) getSession()
                    .get("com.mingda.entity.BizTOptcheckchildidea", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(BizTOptcheckchildidea instance) {
        log.debug("finding BizTOptcheckchildidea instance by example");
        try {
            List results = getSession()
                    .createCriteria("com.mingda.entity.BizTOptcheckchildidea")
                    .add(Example.create(instance))
            .list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }    
    
    public List findByProperty(String propertyName, Object value) {
      log.debug("finding BizTOptcheckchildidea instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from BizTOptcheckchildidea as model where model." 
         						+ propertyName + "= ?";
         Query queryObject = getSession().createQuery(queryString);
		 queryObject.setParameter(0, value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByChildmoney(Object childmoney
	) {
		return findByProperty(CHILDMONEY, childmoney
		);
	}
	
	public List findByChildnote(Object childnote
	) {
		return findByProperty(CHILDNOTE, childnote
		);
	}
	
	public List findByStatus(Object status
	) {
		return findByProperty(STATUS, status
		);
	}
	

	public List findAll() {
		log.debug("finding all BizTOptcheckchildidea instances");
		try {
			String queryString = "from BizTOptcheckchildidea";
	         Query queryObject = getSession().createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public BizTOptcheckchildidea merge(BizTOptcheckchildidea detachedInstance) {
        log.debug("merging BizTOptcheckchildidea instance");
        try {
            BizTOptcheckchildidea result = (BizTOptcheckchildidea) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(BizTOptcheckchildidea instance) {
        log.debug("attaching dirty BizTOptcheckchildidea instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(BizTOptcheckchildidea instance) {
        log.debug("attaching clean BizTOptcheckchildidea instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}