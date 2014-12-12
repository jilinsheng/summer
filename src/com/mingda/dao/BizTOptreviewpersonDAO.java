package com.mingda.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

import com.mingda.common.BaseHibernateDAO;
import com.mingda.entity.BizTOptreviewperson;

/**
 	* A data access object (DAO) providing persistence and search support for BizTOptreviewperson entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.mingda.entity.BizTOptreviewperson
  * @author MyEclipse Persistence Tools 
 */

public class BizTOptreviewpersonDAO extends BaseHibernateDAO  {
    private static final Log log = LogFactory.getLog(BizTOptreviewpersonDAO.class);
	//property constants
	public static final String NAME = "name";
	public static final String OFFICEPHONE = "officephone";
	public static final String OFFICENAME = "officename";
	public static final String POST = "post";
	public static final String ADDRESS = "address";
	public static final String ORGANIZATION_ID = "organizationId";
	public static final String STATUS = "status";



    
    public void save(BizTOptreviewperson transientInstance) {
        log.debug("saving BizTOptreviewperson instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(BizTOptreviewperson persistentInstance) {
        log.debug("deleting BizTOptreviewperson instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public BizTOptreviewperson findById( Long id) {
        log.debug("getting BizTOptreviewperson instance with id: " + id);
        try {
            BizTOptreviewperson instance = (BizTOptreviewperson) getSession()
                    .get("com.mingda.entity.BizTOptreviewperson", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(BizTOptreviewperson instance) {
        log.debug("finding BizTOptreviewperson instance by example");
        try {
            List results = getSession()
                    .createCriteria("com.mingda.entity.BizTOptreviewperson")
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
      log.debug("finding BizTOptreviewperson instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from BizTOptreviewperson as model where model." 
         						+ propertyName + "= ?";
         Query queryObject = getSession().createQuery(queryString);
		 queryObject.setParameter(0, value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByName(Object name
	) {
		return findByProperty(NAME, name
		);
	}
	
	public List findByOfficephone(Object officephone
	) {
		return findByProperty(OFFICEPHONE, officephone
		);
	}
	
	public List findByOfficename(Object officename
	) {
		return findByProperty(OFFICENAME, officename
		);
	}
	
	public List findByPost(Object post
	) {
		return findByProperty(POST, post
		);
	}
	
	public List findByAddress(Object address
	) {
		return findByProperty(ADDRESS, address
		);
	}
	
	public List findByOrganizationId(Object organizationId
	) {
		return findByProperty(ORGANIZATION_ID, organizationId
		);
	}
	
	public List findByStatus(Object status
	) {
		return findByProperty(STATUS, status
		);
	}
	

	public List findAll() {
		log.debug("finding all BizTOptreviewperson instances");
		try {
			String queryString = "from BizTOptreviewperson";
	         Query queryObject = getSession().createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public BizTOptreviewperson merge(BizTOptreviewperson detachedInstance) {
        log.debug("merging BizTOptreviewperson instance");
        try {
            BizTOptreviewperson result = (BizTOptreviewperson) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(BizTOptreviewperson instance) {
        log.debug("attaching dirty BizTOptreviewperson instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(BizTOptreviewperson instance) {
        log.debug("attaching clean BizTOptreviewperson instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}