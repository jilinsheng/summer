package com.mingda.common.myjdbc;

import java.sql.Connection;  
import java.sql.ResultSet;  
import java.sql.SQLException;  
import java.sql.Statement;  
 
public class DBUtil  
{  
    /**  
     *   
     * {关闭连接}  
     *   
     * @param con  
     * @author:LJ  
     */ 
    public static void closeQuietly(Connection con)  
    {  
        try 
        {  
            if (con != null)  
            {  
                con.close();  
            }  
        }  
        catch (SQLException e)  
        {  
            e.printStackTrace();  
        }  
    }  
 
    /**  
     *   
     * {关闭Statement声明}  
     *   
     * @param stmt  
     * @author:LJ 
     */ 
    public static void closeQuietly(Statement stmt)  
    {  
        try 
        {  
            if (stmt != null)  
            {  
                stmt.close();  
            }  
        }  
        catch (SQLException e)  
        {  
            e.printStackTrace();  
        }  
    }  
 
    /**  
     *   
     * {关闭结果集}  
     *   
     * @param rs
     * @author:LJ 
     */ 
    public static void closeQuietly(ResultSet rs)  
    {  
        try 
        {  
            if (rs != null)  
            {  
                rs.close();  
            }  
        }  
        catch (SQLException e)  
        {  
            e.printStackTrace();  
        }  
    }  
} 