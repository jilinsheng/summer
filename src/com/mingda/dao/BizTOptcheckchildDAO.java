package com.mingda.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

import com.mingda.common.BaseHibernateDAO;
import com.mingda.entity.BizTOptcheckchild;

/**
 	* A data access object (DAO) providing persistence and search support for BizTOptcheckchild entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.mingda.entity.BizTOptcheckchild
  * @author MyEclipse Persistence Tools 
 */

public class BizTOptcheckchildDAO extends BaseHibernateDAO  {
    private static final Log log = LogFactory.getLog(BizTOptcheckchildDAO.class);
	//property constants
	public static final String CHECKCHILDMONEY = "checkchildmoney";
	public static final String CHECKCHILDMONEY1 = "checkchildmoney1";
	public static final String CHECKCHILDMONEY2 = "checkchildmoney2";
	public static final String CHECKCHILDMONEY3 = "checkchildmoney3";
	public static final String CHECKCHILDMONEY4 = "checkchildmoney4";
	public static final String CHECKCHILDMONEY5 = "checkchildmoney5";



    
    public void save(BizTOptcheckchild transientInstance) {
        log.debug("saving BizTOptcheckchild instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(BizTOptcheckchild persistentInstance) {
        log.debug("deleting BizTOptcheckchild instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public BizTOptcheckchild findById( java.math.BigDecimal id) {
        log.debug("getting BizTOptcheckchild instance with id: " + id);
        try {
            BizTOptcheckchild instance = (BizTOptcheckchild) getSession()
                    .get("com.mingda.entity.BizTOptcheckchild", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(BizTOptcheckchild instance) {
        log.debug("finding BizTOptcheckchild instance by example");
        try {
            List results = getSession()
                    .createCriteria("com.mingda.entity.BizTOptcheckchild")
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
      log.debug("finding BizTOptcheckchild instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from BizTOptcheckchild as model where model." 
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
		log.debug("finding all BizTOptcheckchild instances");
		try {
			String queryString = "from BizTOptcheckchild";
	         Query queryObject = getSession().createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public BizTOptcheckchild merge(BizTOptcheckchild detachedInstance) {
        log.debug("merging BizTOptcheckchild instance");
        try {
            BizTOptcheckchild result = (BizTOptcheckchild) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(BizTOptcheckchild instance) {
        log.debug("attaching dirty BizTOptcheckchild instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(BizTOptcheckchild instance) {
        log.debug("attaching clean BizTOptcheckchild instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}