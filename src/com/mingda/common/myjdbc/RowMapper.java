package com.mingda.common.myjdbc;

import java.sql.ResultSet;  
import java.sql.SQLException;  
 
public interface RowMapper<E>  
{  
    /**  
     *   
     * {��������е�һ��ת��ΪJAVA����}  
     *   
     * @param rs  
     * @return  
     * @author:LJ  
     */ 
    public E map(ResultSet rs) throws SQLException;  
} 