package com.mingda.dao;

import com.mingda.common.BaseHibernateDAO;
import com.mingda.entity.BizTCheckpower;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 	* A data access object (DAO) providing persistence and search support for BizTCheckpower entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.mingda.entity.BizTCheckpower
  * @author MyEclipse Persistence Tools 
 */

public class BizTCheckpowerDAO extends BaseHibernateDAO  {
    private static final Log log = LogFactory.getLog(BizTCheckpowerDAO.class);
	//property constants
	public static final String CHECKFLAG = "checkflag";
	public static final String REPORTFLAG = "reportflag";
	public static final String CHECKMOREFLAG = "checkmoreflag";



    
    public void save(BizTCheckpower transientInstance) {
        log.debug("saving BizTCheckpower instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(BizTCheckpower persistentInstance) {
        log.debug("deleting BizTCheckpower instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public BizTCheckpower findById( java.math.BigDecimal id) {
        log.debug("getting BizTCheckpower instance with id: " + id);
        try {
            BizTCheckpower instance = (BizTCheckpower) getSession()
                    .get("com.mingda.entity.BizTCheckpower", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(BizTCheckpower instance) {
        log.debug("finding BizTCheckpower instance by example");
        try {
            List results = getSession()
                    .createCriteria("com.mingda.entity.BizTCheckpower")
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
      log.debug("finding BizTCheckpower instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from BizTCheckpower as model where model." 
         						+ propertyName + "= ?";
         Query queryObject = getSession().createQuery(queryString);
		 queryObject.setParameter(0, value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByCheckflag(Object checkflag
	) {
		return findByProperty(CHECKFLAG, checkflag
		);
	}
	
	public List findByReportflag(Object reportflag
	) {
		return findByProperty(REPORTFLAG, reportflag
		);
	}
	
	public List findByCheckmoreflag(Object checkmoreflag
	) {
		return findByProperty(CHECKMOREFLAG, checkmoreflag
		);
	}
	

	public List findAll() {
		log.debug("finding all BizTCheckpower instances");
		try {
			String queryString = "from BizTCheckpower";
	         Query queryObject = getSession().createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public BizTCheckpower merge(BizTCheckpower detachedInstance) {
        log.debug("merging BizTCheckpower instance");
        try {
            BizTCheckpower result = (BizTCheckpower) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(BizTCheckpower instance) {
        log.debug("attaching dirty BizTCheckpower instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(BizTCheckpower instance) {
        log.debug("attaching clean BizTCheckpower instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}