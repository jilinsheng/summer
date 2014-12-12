package com.mingda.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

import com.mingda.common.BaseHibernateDAO;
import com.mingda.entity.BizTRepoptcheckchildidea;

/**
 	* A data access object (DAO) providing persistence and search support for BizTRepoptcheckchildidea entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.mingda.entity.BizTRepoptcheckchildidea
  * @author MyEclipse Persistence Tools 
 */

public class BizTRepoptcheckchildideaDAO extends BaseHibernateDAO  {
    private static final Log log = LogFactory.getLog(BizTRepoptcheckchildideaDAO.class);
	//property constants
	public static final String CHILDMONEY = "childmoney";
	public static final String CHILDNOTE = "childnote";
	public static final String STATUS = "status";



    
    public void save(BizTRepoptcheckchildidea transientInstance) {
        log.debug("saving BizTRepoptcheckchildidea instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(BizTRepoptcheckchildidea persistentInstance) {
        log.debug("deleting BizTRepoptcheckchildidea instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public BizTRepoptcheckchildidea findById( java.math.BigDecimal id) {
        log.debug("getting BizTRepoptcheckchildidea instance with id: " + id);
        try {
            BizTRepoptcheckchildidea instance = (BizTRepoptcheckchildidea) getSession()
                    .get("com.mingda.entity.BizTRepoptcheckchildidea", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(BizTRepoptcheckchildidea instance) {
        log.debug("finding BizTRepoptcheckchildidea instance by example");
        try {
            List results = getSession()
                    .createCriteria("com.mingda.entity.BizTRepoptcheckchildidea")
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
      log.debug("finding BizTRepoptcheckchildidea instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from BizTRepoptcheckchildidea as model where model." 
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
		log.debug("finding all BizTRepoptcheckchildidea instances");
		try {
			String queryString = "from BizTRepoptcheckchildidea";
	         Query queryObject = getSession().createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public BizTRepoptcheckchildidea merge(BizTRepoptcheckchildidea detachedInstance) {
        log.debug("merging BizTRepoptcheckchildidea instance");
        try {
            BizTRepoptcheckchildidea result = (BizTRepoptcheckchildidea) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(BizTRepoptcheckchildidea instance) {
        log.debug("attaching dirty BizTRepoptcheckchildidea instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(BizTRepoptcheckchildidea instance) {
        log.debug("attaching clean BizTRepoptcheckchildidea instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}