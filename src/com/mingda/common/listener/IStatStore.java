package com.mingda.common.listener;

import java.util.Date;
import java.util.List;

import com.mingda.entity.SysTEmployee;

/** */
/**
 * 在线人数统计的接口，可以使用多种方式实现，如：内存、数据库等，只要实现这个接口就可以了。 <br/>
 * 
 * @version : V1.0<br/>
 * @author : wallimn(Email: wallimn@sohu.com QQ: 54871876)<br/>
 * @date : 2008-4-4 下午02:01:07<br/>
 */
public interface IStatStore {
	/** */
	/**
	 * 记录登录状态 <br/> 编码：wallimn 时间：2008-4-3 上午10:08:05<br/> 参数及返回值：<br/>
	 * 
	 * @param username
	 */
	public void login(SysTEmployee employee ,Date date ,String sessionId);

	/** */
	/**
	 * 取消登录状态 <br/> 编码：wallimn 时间：2008-4-3 上午10:08:17<br/> 参数及返回值：<br/>
	 * 
	 * @param username
	 */
	public void logoff(SysTEmployee employee);

	/** */
	/**
	 * 返回登录用户及信息。 <br/> 编码：wallimn 时间：2008-4-3 上午10:20:08<br/> 参数及返回值：<br/>
	 * 
	 * @return　链表里的对象是个数组，数组包含两个元素，０：用户名，１：登录时间。这样是为了便于为jstl标签处理
	 */
	public List<SysTEmployee> getUsers();

	/** */
	/**
	 * 在线用户数量 <br/> 编码：wallimn 时间：2008-4-4 下午01:52:55<br/> 参数及返回值：<br/>
	 * 
	 * @return
	 */
	public int getCount();
}
