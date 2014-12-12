package com.mingda.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

import com.mingda.common.BaseHibernateDAO;
import com.mingda.entity.BizTMasterinstead;

/**
 	* A data access object (DAO) providing persistence and search support for BizTMasterinstead entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.mingda.entity.BizTMasterinstead
  * @author MyEclipse Persistence Tools 
 */

public class BizTMasterinsteadDAO extends BaseHibernateDAO  {
    private static final Log log = LogFactory.getLog(BizTMasterinsteadDAO.class);
	//property constants
	public static final String OMASTERNAME = "omastername";
	public static final String OMASTERPAPERID = "omasterpaperid";
	public static final String NMASTERNAME = "nmastername";
	public static final String NMASTERPAPERID = "nmasterpaperid";



    
    public void save(BizTMasterinstead transientInstance) {
        log.debug("saving BizTMasterinstead instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(BizTMasterinstead persistentInstance) {
        log.debug("deleting BizTMasterinstead instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public BizTMasterinstead findById( java.math.BigDecimal id) {
        log.debug("getting BizTMasterinstead instance with id: " + id);
        try {
            BizTMasterinstead instance = (BizTMasterinstead) getSession()
                    .get("com.mingda.entity.BizTMasterinstead", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(BizTMasterinstead instance) {
        log.debug("finding BizTMasterinstead instance by example");
        try {
            List results = getSession()
                    .createCriteria("com.mingda.entity.BizTMasterinstead")
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
      log.debug("finding BizTMasterinstead instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from BizTMasterinstead as model where model." 
         						+ propertyName + "= ?";
         Query queryObject = getSession().createQuery(queryString);
		 queryObject.setParameter(0, value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByOmastername(Object omastername
	) {
		return findByProperty(OMASTERNAME, omastername
		);
	}
	
	public List findByOmasterpaperid(Object omasterpaperid
	) {
		return findByProperty(OMASTERPAPERID, omasterpaperid
		);
	}
	
	public List findByNmastername(Object nmastername
	) {
		return findByProperty(NMASTERNAME, nmastername
		);
	}
	
	public List findByNmasterpaperid(Object nmasterpaperid
	) {
		return findByProperty(NMASTERPAPERID, nmasterpaperid
		);
	}
	

	public List findAll() {
		log.debug("finding all BizTMasterinstead instances");
		try {
			String queryString = "from BizTMasterinstead";
	         Query queryObject = getSession().createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public BizTMasterinstead merge(BizTMasterinstead detachedInstance) {
        log.debug("merging BizTMasterinstead instance");
        try {
            BizTMasterinstead result = (BizTMasterinstead) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(BizTMasterinstead instance) {
        log.debug("attaching dirty BizTMasterinstead instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(BizTMasterinstead instance) {
        log.debug("attaching clean BizTMasterinstead instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}