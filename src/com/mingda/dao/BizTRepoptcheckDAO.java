package com.mingda.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

import com.mingda.common.BaseHibernateDAO;
import com.mingda.entity.BizTRepoptcheck;

/**
 	* A data access object (DAO) providing persistence and search support for BizTRepoptcheck entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.mingda.entity.BizTRepoptcheck
  * @author MyEclipse Persistence Tools 
 */

public class BizTRepoptcheckDAO extends BaseHibernateDAO  {
    private static final Log log = LogFactory.getLog(BizTRepoptcheckDAO.class);
	//property constants
	public static final String COUNTMONEY = "countmoney";
	public static final String MONEYAOUT = "moneyaout";
	public static final String CHECKMONEY = "checkmoney";
	public static final String CHECKFLAG1 = "checkflag1";
	public static final String CHECKFLAG2 = "checkflag2";
	public static final String CHECKFLAG3 = "checkflag3";
	public static final String CHECKFLAG4 = "checkflag4";
	public static final String CHECKFLAG5 = "checkflag5";
	public static final String CHECKCHILDMONEY = "checkchildmoney";
	public static final String ADJUSTMONEY = "adjustmoney";
	public static final String IFOVER = "ifover";
	public static final String RESULT = "result";
	public static final String RECHECKMONEY = "recheckmoney";



    
    public void save(BizTRepoptcheck transientInstance) {
        log.debug("saving BizTRepoptcheck instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(BizTRepoptcheck persistentInstance) {
        log.debug("deleting BizTRepoptcheck instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public BizTRepoptcheck findById( java.math.BigDecimal id) {
        log.debug("getting BizTRepoptcheck instance with id: " + id);
        try {
            BizTRepoptcheck instance = (BizTRepoptcheck) getSession()
                    .get("com.mingda.entity.BizTRepoptcheck", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(BizTRepoptcheck instance) {
        log.debug("finding BizTRepoptcheck instance by example");
        try {
            List results = getSession()
                    .createCriteria("com.mingda.entity.BizTRepoptcheck")
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
      log.debug("finding BizTRepoptcheck instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from BizTRepoptcheck as model where model." 
         						+ propertyName + "= ?";
         Query queryObject = getSession().createQuery(queryString);
		 queryObject.setParameter(0, value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByCountmoney(Object countmoney
	) {
		return findByProperty(COUNTMONEY, countmoney
		);
	}
	
	public List findByMoneyaout(Object moneyaout
	) {
		return findByProperty(MONEYAOUT, moneyaout
		);
	}
	
	public List findByCheckmoney(Object checkmoney
	) {
		return findByProperty(CHECKMONEY, checkmoney
		);
	}
	
	public List findByCheckflag1(Object checkflag1
	) {
		return findByProperty(CHECKFLAG1, checkflag1
		);
	}
	
	public List findByCheckflag2(Object checkflag2
	) {
		return findByProperty(CHECKFLAG2, checkflag2
		);
	}
	
	public List findByCheckflag3(Object checkflag3
	) {
		return findByProperty(CHECKFLAG3, checkflag3
		);
	}
	
	public List findByCheckflag4(Object checkflag4
	) {
		return findByProperty(CHECKFLAG4, checkflag4
		);
	}
	
	public List findByCheckflag5(Object checkflag5
	) {
		return findByProperty(CHECKFLAG5, checkflag5
		);
	}
	
	public List findByCheckchildmoney(Object checkchildmoney
	) {
		return findByProperty(CHECKCHILDMONEY, checkchildmoney
		);
	}
	
	public List findByAdjustmoney(Object adjustmoney
	) {
		return findByProperty(ADJUSTMONEY, adjustmoney
		);
	}
	
	public List findByIfover(Object ifover
	) {
		return findByProperty(IFOVER, ifover
		);
	}
	
	public List findByResult(Object result
	) {
		return findByProperty(RESULT, result
		);
	}
	
	public List findByRecheckmoney(Object recheckmoney
	) {
		return findByProperty(RECHECKMONEY, recheckmoney
		);
	}
	

	public List findAll() {
		log.debug("finding all BizTRepoptcheck instances");
		try {
			String queryString = "from BizTRepoptcheck";
	         Query queryObject = getSession().createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public BizTRepoptcheck merge(BizTRepoptcheck detachedInstance) {
        log.debug("merging BizTRepoptcheck instance");
        try {
            BizTRepoptcheck result = (BizTRepoptcheck) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(BizTRepoptcheck instance) {
        log.debug("attaching dirty BizTRepoptcheck instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(BizTRepoptcheck instance) {
        log.debug("attaching clean BizTRepoptcheck instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}