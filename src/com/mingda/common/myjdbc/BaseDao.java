package com.mingda.common.myjdbc;

import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.PreparedStatement;  
import java.sql.ResultSet;  
import java.sql.SQLException;  
import java.sql.Statement;  
import java.util.ArrayList;  
import java.util.List;  
 
public class BaseDao  
{  
    private String driverClassName = "oracle.jdbc.driver.OracleDriver";  
 
    private String username = "scott";  
 
    private String password = "tiger";  
 
    private String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";  
 
    /**  
     *   
     * {查询}  
     *   
     * @param sql  
     * @return  
     * @author:LJ  
     */ 
    @SuppressWarnings({ "unchecked", "rawtypes" })  
    protected List query(String sql, RowMapper<?> rowMapper)  
    {  
        Connection con = null;  
        Statement stmt = null;  
        ResultSet rs = null;  
        List list = new ArrayList();  
        try 
        {  
            Class.forName(driverClassName);  
            con = DriverManager.getConnection(url, username, password);  
            stmt = con.createStatement();  
            rs = stmt.executeQuery(sql);  
            while (rs.next())  
            {  
                list.add(rowMapper.map(rs));  
            }  
        }  
        catch (ClassNotFoundException e)  
        {  
            e.printStackTrace();  
        }  
        catch (SQLException e)  
        {  
            e.printStackTrace();  
        }  
        finally 
        {  
            DBUtil.closeQuietly(rs);  
            DBUtil.closeQuietly(stmt);  
            DBUtil.closeQuietly(con);  
        }  
        return list;  
    }  
 
    /**  
     *   
     * {带参数查询}  
     *   
     * @param sql  
     * @param objs  
     * @return  
     * @author:LJ 
     */ 
    @SuppressWarnings({ "unchecked", "rawtypes" })  
    protected List query(String sql, final Object[] objs, RowMapper rowMapper)  
    {  
        Connection con = null;  
        PreparedStatement preparedStatement = null;  
        ResultSet rs = null;  
        List list = new ArrayList();  
        try 
        {  
            Class.forName(driverClassName);  
            con = DriverManager.getConnection(url, username, password);  
            preparedStatement = con.prepareStatement(sql);  
            if (objs != null && objs.length > 0)  
            {  
                for (int i = 0, n = objs.length; i < n; i++)  
                {  
                    preparedStatement.setObject(i + 1, objs[i]);  
                }  
            }  
            rs = preparedStatement.executeQuery();  
            while (rs.next())  
            {  
                list.add(rowMapper.map(rs));  
            }  
        }  
        catch (SQLException e)  
        {  
            throw new RuntimeException(e);  
        }  
        catch (ClassNotFoundException e)  
        {  
            e.printStackTrace();  
        }  
        finally 
        {  
            DBUtil.closeQuietly(rs);  
            DBUtil.closeQuietly(preparedStatement);  
            DBUtil.closeQuietly(con);  
        }  
        return list;  
    }  
 
    /**  
     *   
     * {修改}  
     *   
     * @param sql  
     * @param objs  
     * @return  
     * @author:LJ  
     */ 
    protected int update(final String sql, final Object[] objs)  
    {  
        Connection con = null;  
        PreparedStatement preparedStatement = null;  
        int retCode = 0;  
        try 
        {  
            Class.forName(driverClassName);  
            con = DriverManager.getConnection(url, username, password);  
            preparedStatement = con.prepareStatement(sql);  
            if (objs != null && objs.length > 0)  
            {  
                for (int i = 0, n = objs.length; i < n; i++)  
                {  
                    preparedStatement.setObject(i + 1, objs[i]);  
                }  
            }  
            retCode = preparedStatement.executeUpdate();  
        }  
        catch (SQLException e)  
        {  
            throw new RuntimeException(e);  
        }  
        catch (ClassNotFoundException e)  
        {  
            e.printStackTrace();  
        }  
        finally 
        {  
            DBUtil.closeQuietly(preparedStatement);  
            DBUtil.closeQuietly(con);  
        }  
        return retCode;  
    }  
 
    /**  
     *   
     * {带参数修改}  
     *   
     * @param sql  
     * @return  
     * @author:LJ  
     */ 
    protected int update(final String sql)  
    {  
        Connection con = null;  
        Statement stmt = null;  
        int retCode = 0;  
        try 
        {  
            Class.forName(driverClassName);  
            con = DriverManager.getConnection(url, username, password);  
            stmt = con.createStatement();  
            retCode = stmt.executeUpdate(sql);  
        }  
        catch (SQLException e)  
        {  
            throw new RuntimeException(e);  
        }  
        catch (ClassNotFoundException e)  
        {  
            e.printStackTrace();  
        }  
        finally 
        {  
            DBUtil.closeQuietly(stmt);  
            DBUtil.closeQuietly(con);  
        }  
        return retCode;  
    }  
 
    /**  
     *   
     * {批量处理}  
     *   
     * @param sql  
     * @return  
     * @author:LJ 
     */ 
    protected int[] batchUpdate(final String[] sql)  
    {  
        Connection con = null;  
        Statement stmt = null;  
        int[] rowsAffected = new int[sql.length];  
        try 
        {  
            Class.forName(driverClassName);  
            con = DriverManager.getConnection(url, username, password);  
            stmt = con.createStatement();  
            for (int i = 0, n = sql.length; i < n; i++)  
            {  
                stmt.addBatch(sql[i]);  
            }  
            rowsAffected = stmt.executeBatch();  
        }  
        catch (SQLException e)  
        {  
            throw new RuntimeException(e);  
        }  
        catch (ClassNotFoundException e)  
        {  
            e.printStackTrace();  
        }  
        finally 
        {  
            DBUtil.closeQuietly(stmt);  
            DBUtil.closeQuietly(con);  
        }  
        return rowsAffected;  
    }  
 
    /**  
     *   
     * {带参数批量处理}  
     *   
     * @param sql  
     * @return  
     * @author:LJ  
     */ 
    public int[] batchUpdate(final String sql, final BatchPreparedStatementSetter pss)  
    {  
 
        Connection con = null;  
        PreparedStatement preparedStatement = null;  
        int[] rowsAffected = null;  
        try 
        {  
            Class.forName(driverClassName);  
            con = DriverManager.getConnection(url, username, password);  
            preparedStatement = con.prepareStatement(sql);  
            for (int i = 0, n = pss.getBatchSize(); i < n; i++)  
            {  
                pss.setValues(preparedStatement, i);  
                preparedStatement.addBatch();  
            }  
            rowsAffected = preparedStatement.executeBatch();  
        }  
        catch (SQLException e)  
        {  
            throw new RuntimeException(e);  
        }  
        catch (ClassNotFoundException e)  
        {  
            e.printStackTrace();  
        }  
        finally 
        {  
            DBUtil.closeQuietly(preparedStatement);  
            DBUtil.closeQuietly(con);  
        }  
        return rowsAffected;  
    }  
} 