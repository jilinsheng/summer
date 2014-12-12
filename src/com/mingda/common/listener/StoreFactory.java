package com.mingda.common.listener;

public class StoreFactory {
	    /** *//**
	     * 返回实现IStatStore接口的某种对象。
	     *<br/>
	     *编码：wallimn     时间：2008-4-3  上午10:54:05<br/>
	     *参数及返回值：<br/>
	     *  @return 实现IStatStore接口的某种对象
	     */
	    public static IStatStore getStore(){
	        //这里应该设计成从配制文件中读取，暂时先这样写。
	        //如果想在系统中使用jdbc的方式来实现统计功能，则返回JdbcStat对象即可。
	        return new MemStat();
	    }
	
}
