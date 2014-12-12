package com.mingda.common.listener;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.mingda.common.SessionFactory;
import com.mingda.dao.SysTEmployeeDAO;
import com.mingda.entity.SysTEmployee;

/** */
/**
 * 依托内存实现在线用户统计功能。适合于在线用户数量较少的情况。 <br/>
 * 
 * @version : V1.0<br/>
 * @author : wallimn(Email: wallimn@sohu.com QQ: 54871876)<br/>
 * @date : 2008-4-4 下午01:53:36<br/>
 */
public class MemStat implements IStatStore {

	private static Map userMap = null;
	static {
		userMap = new HashMap();
	}

	public void login(SysTEmployee employee, Date date, String sessionId) {
		// 时间: 2008-4-3 上午10:09:13
		/*
		 * Object[] object = new Object[3]; object[0]=employee; object[1] =date;
		 * object[2]=sessionId; userMap.put(employee.getEmployeeId(), object);//
		 * 如果存在，会覆盖已有的值
		 */
		Session session = SessionFactory.getSession();
		Transaction tx = session.beginTransaction();
		try {
			
			SysTEmployeeDAO edao = new SysTEmployeeDAO();
			edao.updateLine("1", employee.getEmployeeId().toString());
			tx.commit();
		
		} catch (RuntimeException re) {
			tx.rollback();
			re.printStackTrace();
		} finally {
			session.close();
		}

	}

	public void logoff(SysTEmployee employee) {
		// 时间: 2008-4-3 上午10:09:13
		//userMap.remove(employee.getEmployeeId());
		
		Session session = SessionFactory.getSession();
		Transaction tx = session.beginTransaction();
		try {
			
			SysTEmployeeDAO edao = new SysTEmployeeDAO();
			edao.updateLine("0", employee.getEmployeeId().toString());
			tx.commit();
		
		} catch (RuntimeException re) {
			tx.rollback();
			re.printStackTrace();
		} finally {
			session.close();
		}
	}

	public List<SysTEmployee> getUsers() {
		// 时间: 2008-4-3 上午10:23:34
		/*List list = new LinkedList();
		List dlist = new LinkedList();
		List slist = new LinkedList();
		;
		SysTEmployee user = null;
		for (Iterator it = userMap.keySet().iterator(); it.hasNext();) {
			Long empid = (Long) it.next();
			Object[] object = new Object[2];
			object = (Object[]) userMap.get(empid);
			list.add(object[0]);
			dlist.add(object[1]);
			slist.add(object[2]);
		}
		Object[] clist = new Object[3];
		clist[0] = list;
		clist[1] = dlist;
		clist[2] = slist;*/
		List list =null;

		Session session = SessionFactory.getSession();
		Transaction tx = session.beginTransaction();
		try {
			
			SysTEmployeeDAO edao = new SysTEmployeeDAO();
			list=edao.getEmpOnline();
			tx.commit();
		
		} catch (RuntimeException re) {
			tx.rollback();
			re.printStackTrace();
		} finally {
			session.close();
		}
		return list;
	}

	public int getCount() {
		// 时间: 2008-4-4 下午01:53:25
		return userMap.size();
	}

}
