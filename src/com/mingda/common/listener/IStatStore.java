package com.mingda.common.listener;

import java.util.Date;
import java.util.List;

import com.mingda.entity.SysTEmployee;

/** */
/**
 * ��������ͳ�ƵĽӿڣ�����ʹ�ö��ַ�ʽʵ�֣��磺�ڴ桢���ݿ�ȣ�ֻҪʵ������ӿھͿ����ˡ� <br/>
 * 
 * @version : V1.0<br/>
 * @author : wallimn(Email: wallimn@sohu.com QQ: 54871876)<br/>
 * @date : 2008-4-4 ����02:01:07<br/>
 */
public interface IStatStore {
	/** */
	/**
	 * ��¼��¼״̬ <br/> ���룺wallimn ʱ�䣺2008-4-3 ����10:08:05<br/> ����������ֵ��<br/>
	 * 
	 * @param username
	 */
	public void login(SysTEmployee employee ,Date date ,String sessionId);

	/** */
	/**
	 * ȡ����¼״̬ <br/> ���룺wallimn ʱ�䣺2008-4-3 ����10:08:17<br/> ����������ֵ��<br/>
	 * 
	 * @param username
	 */
	public void logoff(SysTEmployee employee);

	/** */
	/**
	 * ���ص�¼�û�����Ϣ�� <br/> ���룺wallimn ʱ�䣺2008-4-3 ����10:20:08<br/> ����������ֵ��<br/>
	 * 
	 * @return��������Ķ����Ǹ����飬�����������Ԫ�أ������û�����������¼ʱ�䡣������Ϊ�˱���Ϊjstl��ǩ����
	 */
	public List<SysTEmployee> getUsers();

	/** */
	/**
	 * �����û����� <br/> ���룺wallimn ʱ�䣺2008-4-4 ����01:52:55<br/> ����������ֵ��<br/>
	 * 
	 * @return
	 */
	public int getCount();
}
