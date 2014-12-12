package com.mingda.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

import com.mingda.common.BaseHibernateDAO;
import com.mingda.entity.BizTRepoptcheckchild;

/**
 	* A data access object (DAO) providing persistence and search support for BizTRepoptcheckchild entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.mingda.entity.BizTRepoptcheckchild
  * @author MyEclipse Persistence Tools 
 */

public class BizTRepoptcheckchildDAO extends BaseHibernateDAO  {
    private static final Log log = LogFactory.getLog(BizTRepoptcheckchildDAO.class);
	//property constants
	public static final String CHECKCHILDMONEY = "checkchildmoney";
	public static final String CHECKCHILDMONEY1 = "checkchildmoney1";
	public static final String CHECKCHILDMONEY2 = "checkchildmoney2";
	public static final String CHECKCHILDMONEY3 = "checkchildmoney3";
	public static final String CHECKCHILDMONEY4 = "checkchildmoney4";
	public static final String CHECKCHILDMONEY5 = "checkchildmoney5";



    
    public void save(BizTRepoptcheckchild transientInstance) {
        log.debug("saving BizTRepoptcheckchild instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(BizTRepoptcheckchild persistentInstance) {
        log.debug("deleting BizTRepoptcheckchild instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public BizTRepoptcheckchild findById( java.math.BigDecimal id) {
        log.debug("getting BizTRepoptcheckchild instance with id: " + id);
        try {
            BizTRepoptcheckchild instance = (BizTRepoptcheckchild) getSession()
                    .get("com.mingda.entity.BizTRepoptcheckchild", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(BizTRepoptcheckchild instance) {
        log.debug("finding BizTRepoptcheckchild instance by example");
        try {
            List results = getSession()
                    .createCriteria("com.mingda.entity.BizTRepoptcheckchild")
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
      log.debug("finding BizTRepoptcheckchild instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from BizTRepoptcheckchild as model where model." 
         						+ propertyName + "= ?";
         Query queryObject = getSession().createQuery(queryString);
		 queryObject.setParameter(0, value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByCheckchildmoney(Object checkchildmoney
	) {
		return findByProperty(CHECKCHILDMONEY, checkchildmoney
		);
	}
	
	public List findByCheckchildmoney1(Object checkchildmoney1
	) {
		return findByProperty(CHECKCHILDMONEY1, checkchildmoney1
		);
	}
	
	public List findByCheckchildmoney2(Object checkchildmoney2
	) {
		return findByProperty(CHECKCHILDMONEY2, checkchildmoney2
		);
	}
	
	public List findByCheckchildmoney3(Object checkchildmoney3
	) {
		return findByProperty(CHECKCHILDMONEY3, checkchildmoney3
		);
	}
	
	public List findByCheckchildmoney4(Object checkchildmoney4
	) {
		return findByProperty(CHECKCHILDMONEY4, checkchildmoney4
		);
	}
	
	public List findByCheckchildmoney5(Object checkchildmoney5
	) {
		return findByProperty(CHECKCHILDMONEY5, checkchildmoney5
		);
	}
	

	public List findAll() {
		log.debug("finding all BizTRepoptcheckchild instances");
		try {
			String queryString = "from BizTRepoptcheckchild";
	         Query queryObject = getSession().createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public BizTRepoptcheckchild merge(BizTRepoptcheckchild detachedInstance) {
        log.debug("merging BizTRepoptcheckchild instance");
        try {
            BizTRepoptcheckchild result = (BizTRepoptcheckchild) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(BizTRepoptcheckchild instance) {
        log.debug("attaching dirty BizTRepoptcheckchild instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(BizTRepoptcheckchild instance) {
        log.debug("attaching clean BizTRepoptcheckchild instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}