package com.mingda.common.myjdbc;

import java.sql.Connection;  
import java.sql.ResultSet;  
import java.sql.SQLException;  
import java.sql.Statement;  
 
public class DBUtil  
{  
    /**  
     *   
     * {�ر�����}  
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
     * {�ر�Statement����}  
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
     * {�رս����}  
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