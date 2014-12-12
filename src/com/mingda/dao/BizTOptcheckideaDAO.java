package com.mingda.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

import com.mingda.common.BaseHibernateDAO;
import com.mingda.entity.BizTOptcheckidea;

/**
 	* A data access object (DAO) providing persistence and search support for BizTOptcheckidea entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.mingda.entity.BizTOptcheckidea
  * @author MyEclipse Persistence Tools 
 */

public class BizTOptcheckideaDAO extends BaseHibernateDAO  {
    private static final Log log = LogFactory.getLog(BizTOptcheckideaDAO.class);
	//property constants
	public static final String APPIDEAMAN = "appideaman";
	public static final String APPAREA = "apparea";
	public static final String NOTE = "note";
	public static final String STATUS = "status";



    
    public void save(BizTOptcheckidea transientInstance) {
        log.debug("saving BizTOptcheckidea instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(BizTOptcheckidea persistentInstance) {
        log.debug("deleting BizTOptcheckidea instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public BizTOptcheckidea findById( java.math.BigDecimal id) {
        log.debug("getting BizTOptcheckidea instance with id: " + id);
        try {
            BizTOptcheckidea instance = (BizTOptcheckidea) getSession()
                    .get("com.mingda.entity.BizTOptcheckidea", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(BizTOptcheckidea instance) {
        log.debug("finding BizTOptcheckidea instance by example");
        try {
            List results = getSession()
                    .createCriteria("com.mingda.entity.BizTOptcheckidea")
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
      log.debug("finding BizTOptcheckidea instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from BizTOptcheckidea as model where model." 
         						+ propertyName + "= ?";
         Query queryObject = getSession().createQuery(queryString);
		 queryObject.setParameter(0, value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByAppideaman(Object appideaman
	) {
		return findByProperty(APPIDEAMAN, appideaman
		);
	}
	
	public List findByApparea(Object apparea
	) {
		return findByProperty(APPAREA, apparea
		);
	}
	
	public List findByNote(Object note
	) {
		return findByProperty(NOTE, note
		);
	}
	
	public List findByStatus(Object status
	) {
		return findByProperty(STATUS, status
		);
	}
	

	public List findAll() {
		log.debug("finding all BizTOptcheckidea instances");
		try {
			String queryString = "from BizTOptcheckidea";
	         Query queryObject = getSession().createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public BizTOptcheckidea merge(BizTOptcheckidea detachedInstance) {
        log.debug("merging BizTOptcheckidea instance");
        try {
            BizTOptcheckidea result = (BizTOptcheckidea) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(BizTOptcheckidea instance) {
        log.debug("attaching dirty BizTOptcheckidea instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(BizTOptcheckidea instance) {
        log.debug("attaching clean BizTOptcheckidea instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}