package com.mingda.common.listener;

public class StoreFactory {
	    /** *//**
	     * ����ʵ��IStatStore�ӿڵ�ĳ�ֶ���
	     *<br/>
	     *���룺wallimn     ʱ�䣺2008-4-3  ����10:54:05<br/>
	     *����������ֵ��<br/>
	     *  @return ʵ��IStatStore�ӿڵ�ĳ�ֶ���
	     */
	    public static IStatStore getStore(){
	        //����Ӧ����Ƴɴ������ļ��ж�ȡ����ʱ������д��
	        //�������ϵͳ��ʹ��jdbc�ķ�ʽ��ʵ��ͳ�ƹ��ܣ��򷵻�JdbcStat���󼴿ɡ�
	        return new MemStat();
	    }
	
}
