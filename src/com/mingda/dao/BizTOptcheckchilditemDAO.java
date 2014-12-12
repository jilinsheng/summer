package com.mingda.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

import com.mingda.common.BaseHibernateDAO;
import com.mingda.entity.BizTOptcheckchilditem;

/**
 	* A data access object (DAO) providing persistence and search support for BizTOptcheckchilditem entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.mingda.entity.BizTOptcheckchilditem
  * @author MyEclipse Persistence Tools 
 */

public class BizTOptcheckchilditemDAO extends BaseHibernateDAO  {
    private static final Log log = LogFactory.getLog(BizTOptcheckchilditemDAO.class);
	//property constants
	public static final String ITEMMONEY1 = "itemmoney1";
	public static final String ITEMMONEY2 = "itemmoney2";
	public static final String ITEMMONEY3 = "itemmoney3";
	public static final String ITEMMONEY4 = "itemmoney4";
	public static final String ITEMMONEY5 = "itemmoney5";
	public static final String ITEMMONEY = "itemmoney";



    
    public void save(BizTOptcheckchilditem transientInstance) {
        log.debug("saving BizTOptcheckchilditem instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(BizTOptcheckchilditem persistentInstance) {
        log.debug("deleting BizTOptcheckchilditem instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public BizTOptcheckchilditem findById( java.math.BigDecimal id) {
        log.debug("getting BizTOptcheckchilditem instance with id: " + id);
        try {
            BizTOptcheckchilditem instance = (BizTOptcheckchilditem) getSession()
                    .get("com.mingda.entity.BizTOptcheckchilditem", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(BizTOptcheckchilditem instance) {
        log.debug("finding BizTOptcheckchilditem instance by example");
        try {
            List results = getSession()
                    .createCriteria("com.mingda.entity.BizTOptcheckchilditem")
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
      log.debug("finding BizTOptcheckchilditem instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from BizTOptcheckchilditem as model where model." 
         						+ propertyName + "= ?";
         Query queryObject = getSession().createQuery(queryString);
		 queryObject.setParameter(0, value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByItemmoney1(Object itemmoney1
	) {
		return findByProperty(ITEMMONEY1, itemmoney1
		);
	}
	
	public List findByItemmoney2(Object itemmoney2
	) {
		return findByProperty(ITEMMONEY2, itemmoney2
		);
	}
	
	public List findByItemmoney3(Object itemmoney3
	) {
		return findByProperty(ITEMMONEY3, itemmoney3
		);
	}
	
	public List findByItemmoney4(Object itemmoney4
	) {
		return findByProperty(ITEMMONEY4, itemmoney4
		);
	}
	
	public List findByItemmoney5(Object itemmoney5
	) {
		return findByProperty(ITEMMONEY5, itemmoney5
		);
	}
	
	public List findByItemmoney(Object itemmoney
	) {
		return findByProperty(ITEMMONEY, itemmoney
		);
	}
	

	public List findAll() {
		log.debug("finding all BizTOptcheckchilditem instances");
		try {
			String queryString = "from BizTOptcheckchilditem";
	         Query queryObject = getSession().createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public BizTOptcheckchilditem merge(BizTOptcheckchilditem detachedInstance) {
        log.debug("merging BizTOptcheckchilditem instance");
        try {
            BizTOptcheckchilditem result = (BizTOptcheckchilditem) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(BizTOptcheckchilditem instance) {
        log.debug("attaching dirty BizTOptcheckchilditem instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(BizTOptcheckchilditem instance) {
        log.debug("attaching clean BizTOptcheckchilditem instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}