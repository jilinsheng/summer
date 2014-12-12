package com.mingda.common.myjdbc;

import java.sql.ResultSet;  
import java.sql.SQLException;  
 
public interface RowMapper<E>  
{  
    /**  
     *   
     * {将结果集中的一行转化为JAVA对象}  
     *   
     * @param rs  
     * @return  
     * @author:LJ  
     */ 
    public E map(ResultSet rs) throws SQLException;  
} 