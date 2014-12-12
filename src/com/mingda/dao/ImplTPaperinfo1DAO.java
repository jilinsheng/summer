package com.mingda.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

import com.mingda.common.BaseHibernateDAO;
import com.mingda.entity.ImplTPaperinfo1;

/**
 * A data access object (DAO) providing persistence and search support for
 * ImplTPaperinfo1 entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.mingda.entity.ImplTPaperinfo1
 * @author MyEclipse Persistence Tools
 */

public class ImplTPaperinfo1DAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(ImplTPaperinfo1DAO.class);
	// property constants
	public static final String MASTERNAME = "mastername";
	public static final String MASTERPAPERTYE = "masterpapertye";
	public static final String MASTERPAPERCODE = "masterpapercode";
	public static final String FAMILYNO = "familyno";
	public static final String SERIAL = "serial";
	public static final String MEMSORT = "memsort";
	public static final String AGE = "age";
	public static final String BEGINTIME = "begintime";
	public static final String PAPERKIND = "paperkind";
	public static final String PAPERDATE = "paperdate";
	public static final String POPULATION = "population";
	public static final String ENSURE = "ensure";
	public static final String ADDRESS = "address";
	public static final String RESIADDR = "resiaddr";
	public static final String MASTERUNIT = "masterunit";
	public static final String MEMNAME1 = "memname1";
	public static final String MEMSEX1 = "memsex1";
	public static final String MEMAGE1 = "memage1";
	public static final String MEMRELMASTER1 = "memrelmaster1";
	public static final String MEMPAPER1 = "mempaper1";
	public static final String MEMSORT1 = "memsort1";
	public static final String MEMNAME2 = "memname2";
	public static final String MEMSEX2 = "memsex2";
	public static final String MEMAGE2 = "memage2";
	public static final String MEMRELMASTER2 = "memrelmaster2";
	public static final String MEMPAPER2 = "mempaper2";
	public static final String MEMSORT2 = "memsort2";
	public static final String MEMNAME3 = "memname3";
	public static final String MEMSEX3 = "memsex3";
	public static final String MEMAGE3 = "memage3";
	public static final String MEMRELMASTER3 = "memrelmaster3";
	public static final String MEMPAPER3 = "mempaper3";
	public static final String MEMSORT3 = "memsort3";
	public static final String MEMNAME4 = "memname4";
	public static final String MEMSEX4 = "memsex4";
	public static final String MEMAGE4 = "memage4";
	public static final String MEMRELMASTER4 = "memrelmaster4";
	public static final String MEMPAPER4 = "mempaper4";
	public static final String MEMSORT4 = "memsort4";
	public static final String MEMNAME5 = "memname5";
	public static final String MEMSEX5 = "memsex5";
	public static final String MEMAGE5 = "memage5";
	public static final String MEMRELMASTER5 = "memrelmaster5";
	public static final String MEMPAPER5 = "mempaper5";
	public static final String MEMSORT5 = "memsort5";

	public void save(ImplTPaperinfo1 transientInstance) {
		log.debug("saving ImplTPaperinfo1 instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(ImplTPaperinfo1 persistentInstance) {
		log.debug("deleting ImplTPaperinfo1 instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public ImplTPaperinfo1 findById(Long id) {
		log.debug("getting ImplTPaperinfo1 instance with id: " + id);
		try {
			ImplTPaperinfo1 instance = (ImplTPaperinfo1) getSession().get(
					"com.mingda.entity.ImplTPaperinfo1", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(ImplTPaperinfo1 instance) {
		log.debug("finding ImplTPaperinfo1 instance by example");
		try {
			List results = getSession().createCriteria(
					"com.mingda.entity.ImplTPaperinfo1").add(
					Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding ImplTPaperinfo1 instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from ImplTPaperinfo1 as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByMastername(Object mastername) {
		return findByProperty(MASTERNAME, mastername);
	}

	public List findByMasterpapertye(Object masterpapertye) {
		return findByProperty(MASTERPAPERTYE, masterpapertye);
	}

	public List findByMasterpapercode(Object masterpapercode) {
		return findByProperty(MASTERPAPERCODE, masterpapercode);
	}

	public List findByFamilyno(Object familyno) {
		return findByProperty(FAMILYNO, familyno);
	}

	public List findBySerial(Object serial) {
		return findByProperty(SERIAL, serial);
	}

	public List findByMemsort(Object memsort) {
		return findByProperty(MEMSORT, memsort);
	}

	public List findByAge(Object age) {
		return findByProperty(AGE, age);
	}

	public List findByBegintime(Object begintime) {
		return findByProperty(BEGINTIME, begintime);
	}

	public List findByPaperkind(Object paperkind) {
		return findByProperty(PAPERKIND, paperkind);
	}

	public List findByPaperdate(Object paperdate) {
		return findByProperty(PAPERDATE, paperdate);
	}

	public List findByPopulation(Object population) {
		return findByProperty(POPULATION, population);
	}

	public List findByEnsure(Object ensure) {
		return findByProperty(ENSURE, ensure);
	}

	public List findByAddress(Object address) {
		return findByProperty(ADDRESS, address);
	}

	public List findByResiaddr(Object resiaddr) {
		return findByProperty(RESIADDR, resiaddr);
	}

	public List findByMasterunit(Object masterunit) {
		return findByProperty(MASTERUNIT, masterunit);
	}

	public List findByMemname1(Object memname1) {
		return findByProperty(MEMNAME1, memname1);
	}

	public List findByMemsex1(Object memsex1) {
		return findByProperty(MEMSEX1, memsex1);
	}

	public List findByMemage1(Object memage1) {
		return findByProperty(MEMAGE1, memage1);
	}

	public List findByMemrelmaster1(Object memrelmaster1) {
		return findByProperty(MEMRELMASTER1, memrelmaster1);
	}

	public List findByMempaper1(Object mempaper1) {
		return findByProperty(MEMPAPER1, mempaper1);
	}

	public List findByMemsort1(Object memsort1) {
		return findByProperty(MEMSORT1, memsort1);
	}

	public List findByMemname2(Object memname2) {
		return findByProperty(MEMNAME2, memname2);
	}

	public List findByMemsex2(Object memsex2) {
		return findByProperty(MEMSEX2, memsex2);
	}

	public List findByMemage2(Object memage2) {
		return findByProperty(MEMAGE2, memage2);
	}

	public List findByMemrelmaster2(Object memrelmaster2) {
		return findByProperty(MEMRELMASTER2, memrelmaster2);
	}

	public List findByMempaper2(Object mempaper2) {
		return findByProperty(MEMPAPER2, mempaper2);
	}

	public List findByMemsort2(Object memsort2) {
		return findByProperty(MEMSORT2, memsort2);
	}

	public List findByMemname3(Object memname3) {
		return findByProperty(MEMNAME3, memname3);
	}

	public List findByMemsex3(Object memsex3) {
		return findByProperty(MEMSEX3, memsex3);
	}

	public List findByMemage3(Object memage3) {
		return findByProperty(MEMAGE3, memage3);
	}

	public List findByMemrelmaster3(Object memrelmaster3) {
		return findByProperty(MEMRELMASTER3, memrelmaster3);
	}

	public List findByMempaper3(Object mempaper3) {
		return findByProperty(MEMPAPER3, mempaper3);
	}

	public List findByMemsort3(Object memsort3) {
		return findByProperty(MEMSORT3, memsort3);
	}

	public List findByMemname4(Object memname4) {
		return findByProperty(MEMNAME4, memname4);
	}

	public List findByMemsex4(Object memsex4) {
		return findByProperty(MEMSEX4, memsex4);
	}

	public List findByMemage4(Object memage4) {
		return findByProperty(MEMAGE4, memage4);
	}

	public List findByMemrelmaster4(Object memrelmaster4) {
		return findByProperty(MEMRELMASTER4, memrelmaster4);
	}

	public List findByMempaper4(Object mempaper4) {
		return findByProperty(MEMPAPER4, mempaper4);
	}

	public List findByMemsort4(Object memsort4) {
		return findByProperty(MEMSORT4, memsort4);
	}

	public List findByMemname5(Object memname5) {
		return findByProperty(MEMNAME5, memname5);
	}

	public List findByMemsex5(Object memsex5) {
		return findByProperty(MEMSEX5, memsex5);
	}

	public List findByMemage5(Object memage5) {
		return findByProperty(MEMAGE5, memage5);
	}

	public List findByMemrelmaster5(Object memrelmaster5) {
		return findByProperty(MEMRELMASTER5, memrelmaster5);
	}

	public List findByMempaper5(Object mempaper5) {
		return findByProperty(MEMPAPER5, mempaper5);
	}

	public List findByMemsort5(Object memsort5) {
		return findByProperty(MEMSORT5, memsort5);
	}

	public List findAll() {
		log.debug("finding all ImplTPaperinfo1 instances");
		try {
			String queryString = "from ImplTPaperinfo1";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public ImplTPaperinfo1 merge(ImplTPaperinfo1 detachedInstance) {
		log.debug("merging ImplTPaperinfo1 instance");
		try {
			ImplTPaperinfo1 result = (ImplTPaperinfo1) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(ImplTPaperinfo1 instance) {
		log.debug("attaching dirty ImplTPaperinfo1 instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ImplTPaperinfo1 instance) {
		log.debug("attaching clean ImplTPaperinfo1 instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}