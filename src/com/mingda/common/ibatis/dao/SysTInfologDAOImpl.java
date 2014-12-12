package com.mingda.common.ibatis.dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.mingda.common.ibatis.data.SysTInfolog;
import com.mingda.common.ibatis.data.SysTInfologExample;

public class SysTInfologDAOImpl implements SysTInfologDAO {
    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table SUMMER.SYS_T_INFOLOG
     *
     * @ibatorgenerated Fri Jul 24 14:47:41 CST 2009
     */
    private SqlMapClient sqlMapClient;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SUMMER.SYS_T_INFOLOG
     *
     * @ibatorgenerated Fri Jul 24 14:47:41 CST 2009
     */
    public SysTInfologDAOImpl(SqlMapClient sqlMapClient) {
        super();
        this.sqlMapClient = sqlMapClient;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SUMMER.SYS_T_INFOLOG
     *
     * @ibatorgenerated Fri Jul 24 14:47:41 CST 2009
     */
    public int countByExample(SysTInfologExample example) throws SQLException {
        Integer count = (Integer)  sqlMapClient.queryForObject("SYS_T_INFOLOG.ibatorgenerated_countByExample", example);
        return count.intValue();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SUMMER.SYS_T_INFOLOG
     *
     * @ibatorgenerated Fri Jul 24 14:47:41 CST 2009
     */
    public int deleteByExample(SysTInfologExample example) throws SQLException {
        int rows = sqlMapClient.delete("SYS_T_INFOLOG.ibatorgenerated_deleteByExample", example);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SUMMER.SYS_T_INFOLOG
     *
     * @ibatorgenerated Fri Jul 24 14:47:41 CST 2009
     */
    public int deleteByPrimaryKey(BigDecimal infologId) throws SQLException {
        SysTInfolog key = new SysTInfolog();
        key.setInfologId(infologId);
        int rows = sqlMapClient.delete("SYS_T_INFOLOG.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SUMMER.SYS_T_INFOLOG
     *
     * @ibatorgenerated Fri Jul 24 14:47:41 CST 2009
     */
    public void insert(SysTInfolog record) throws SQLException {
        sqlMapClient.insert("SYS_T_INFOLOG.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SUMMER.SYS_T_INFOLOG
     *
     * @ibatorgenerated Fri Jul 24 14:47:41 CST 2009
     */
    public void insertSelective(SysTInfolog record) throws SQLException {
        sqlMapClient.insert("SYS_T_INFOLOG.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SUMMER.SYS_T_INFOLOG
     *
     * @ibatorgenerated Fri Jul 24 14:47:41 CST 2009
     */
    public List selectByExampleWithBLOBs(SysTInfologExample example) throws SQLException {
        List list = sqlMapClient.queryForList("SYS_T_INFOLOG.ibatorgenerated_selectByExampleWithBLOBs", example);
        return list;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SUMMER.SYS_T_INFOLOG
     *
     * @ibatorgenerated Fri Jul 24 14:47:41 CST 2009
     */
    public List selectByExampleWithoutBLOBs(SysTInfologExample example) throws SQLException {
        List list = sqlMapClient.queryForList("SYS_T_INFOLOG.ibatorgenerated_selectByExample", example);
        return list;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SUMMER.SYS_T_INFOLOG
     *
     * @ibatorgenerated Fri Jul 24 14:47:41 CST 2009
     */
    public SysTInfolog selectByPrimaryKey(BigDecimal infologId) throws SQLException {
        SysTInfolog key = new SysTInfolog();
        key.setInfologId(infologId);
        SysTInfolog record = (SysTInfolog) sqlMapClient.queryForObject("SYS_T_INFOLOG.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SUMMER.SYS_T_INFOLOG
     *
     * @ibatorgenerated Fri Jul 24 14:47:41 CST 2009
     */
    public int updateByExampleSelective(SysTInfolog record, SysTInfologExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("SYS_T_INFOLOG.ibatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SUMMER.SYS_T_INFOLOG
     *
     * @ibatorgenerated Fri Jul 24 14:47:41 CST 2009
     */
    public int updateByExampleWithBLOBs(SysTInfolog record, SysTInfologExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("SYS_T_INFOLOG.ibatorgenerated_updateByExampleWithBLOBs", parms);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SUMMER.SYS_T_INFOLOG
     *
     * @ibatorgenerated Fri Jul 24 14:47:41 CST 2009
     */
    public int updateByExampleWithoutBLOBs(SysTInfolog record, SysTInfologExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("SYS_T_INFOLOG.ibatorgenerated_updateByExample", parms);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SUMMER.SYS_T_INFOLOG
     *
     * @ibatorgenerated Fri Jul 24 14:47:41 CST 2009
     */
    public int updateByPrimaryKeySelective(SysTInfolog record) throws SQLException {
        int rows = sqlMapClient.update("SYS_T_INFOLOG.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SUMMER.SYS_T_INFOLOG
     *
     * @ibatorgenerated Fri Jul 24 14:47:41 CST 2009
     */
    public int updateByPrimaryKeyWithBLOBs(SysTInfolog record) throws SQLException {
        int rows = sqlMapClient.update("SYS_T_INFOLOG.ibatorgenerated_updateByPrimaryKeyWithBLOBs", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SUMMER.SYS_T_INFOLOG
     *
     * @ibatorgenerated Fri Jul 24 14:47:41 CST 2009
     */
    public int updateByPrimaryKeyWithoutBLOBs(SysTInfolog record) throws SQLException {
        int rows = sqlMapClient.update("SYS_T_INFOLOG.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    /**
     * This class was generated by Apache iBATIS ibator.
     * This class corresponds to the database table SUMMER.SYS_T_INFOLOG
     *
     * @ibatorgenerated Fri Jul 24 14:47:41 CST 2009
     */
    private static class UpdateByExampleParms extends SysTInfologExample {
        private Object record;

        public UpdateByExampleParms(Object record, SysTInfologExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}