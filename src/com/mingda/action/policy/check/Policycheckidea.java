package com.mingda.action.policy.check;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

import com.mingda.action.info.search.DBUtils;
import com.mingda.action.info.search.TableInfoQuery;
import com.mingda.action.policy.manage.PolicyManageCheckManage;
import com.mingda.action.policy.query.PolicyManageCheckQuery;
import com.mingda.common.ConstantDefine;


public class Policycheckidea {
	static Logger log = Logger.getLogger(Policycheckidea.class);
	/**
	 * 取得审批已经填写表单Html
	 * @return
	 */
	public StringBuffer GetCheckIdeaHtml() {
		StringBuffer shtml= new StringBuffer("");
		String html = "";
		//
		html +="<table width='100%' border='0' cellspacing='0' cellpadding='0'>";
		html +="<tr align='center' id = 'checktypetr'>";
			html +="<td width = '100px' style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>致困原因:</td>";
			html +="<td><div style = 'width:100%' id = 'requesttypechoice'></div></td>";
			html +="<td width = '24px' style='color: #000000;background-color:#ECECEC;text-align:left;font-size:12px;'>";
				html +="<img style = 'cursor: pointer;' src='/db/page/images/pen.gif' alt= '添加致困原因' onclick='addDictValue()' />";
			html +="</td>";
			html +="<td width = '64px' style='color: #000000;background-color:#ECECEC;text-align:right;font-size:12px;'>";
			html +="<button class = 'btn' onclick='ChoiceType()'>选择</button>";
		html +="</td>";
			html +="<td style = 'width:40%;text-align:left;'>";
				html +="<input style = 'display:none' type='text' id = 'checktypesid'></input>";
				html +="<input style = 'width:100%;' type='text' id='checktypes' readonly='readonly'></input>";
			html +="</td>";
			html +="<td style = 'width:6%;text-align:right;'>";	    			
				html +="<button class = 'btn' onclick='ClearType()'>置空</button>";
			html +="</td>";
		html +="</tr>";	
		html +="<tr align='center'>";
			html +="<td width = '100px' style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>评议人:</td>";
			html +="<td><div style = 'width:100%' id = 'ideamanchoice'></div></td>";
			html +="<td width = '24px' style='color: #000000;background-color:#ECECEC;text-align:left;font-size:12px;'>";
				html +="<img style = 'cursor: pointer;' src='/db/page/images/pen.gif' alt= '添加评议人' onclick='addCheckPerson()' />";
			html +="</td>";
			html +="<td width = '64px' style='color: #000000;background-color:#ECECEC;text-align:right;font-size:12px;'>";	    			
				html +="<button class = 'btn' onclick='ChoiceIdeaman()'>选择</button>";			
			html +="</td>";
			html +="<td style = 'width:40%;text-align:left;'>";
				html +="<input style = 'width:100%;' type='text' id = 'checkideaman'></input>";	    			
			html +="</td>";
			html +="<td style = 'width:6%;text-align:right;'>";	    			
				html +="<button class = 'btn' onclick='ClearIdeaman()'>置空</button>";
			html +="</td>";
		html +="</tr>";    	
		html +="<tr align='center'>";
			html +="<td width = '100px' style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>评议结果:</td>";
			html +="<td><div style = 'width:100%' id = 'checkideachoice'></div></td>";
			html +="<td colspan = '2' width = '88px' style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>评议日期:</td>";
			html +="<td colspan = '2' style = 'text-align:left;'>";
				html +="<input style = 'width:60%;' type='text' name='checkideadt' id='checkideadt' onFocus='setday(this)'></input>";
			html +="</td>";
		html +="</tr>";
		html +="<tr align='center' id = 'redatetr'>";
			html +="<td width = '100px' style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>救助开始日期:</td>";
			html +="<td><input style = 'width:100%;' type='text' name='rebegdt' id='rebegdt' onFocus='setday(this)'></input></td>";
			html +="<td colspan = '2' width = '88px' style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>救助结束日期:</td>";
			html +="<td colspan = '2' style = 'font-size:12px;text-align:left;' >";
				html +="<input style = 'width:60%;' type='text' name='reenddt' id='reenddt' onFocus='setday(this)'></input>";
				html +="<input style = 'width:10%;' type='checkbox' name='checkb' id='checkb' onclick='SetEndDt()'/>无限期";
			html +="</td>";
		html +="</tr>";
			html +="<tr align='center'>";
			html +="<td width = '100px' style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>评议意见填写:</td>";
			html +="<td colspan = '5'><textarea style = 'width:100%' rows='2' id = 'checkideatxt'></textarea></td>";
		html +="</tr>";	    	
		html +="</table>"; 
			
		html +="<div align='center'>";
			html +="<button  id = 'btncheck' style = 'width:64;height:32;' class = 'btn' onclick='SetMoreCheckIdea()'>确 定</button>";    		
		html +="</div>";
		shtml.append(html);
		return shtml;
	}
	/**
     * 取得业务申请原因分类选择下拉框
     * 字典编码为[115]
     * @param sname
     * @return
     */
    public StringBuffer getPolicyRequestTypeChoice(String sname){
    	StringBuffer shtml= new StringBuffer("");
    	String id = "",name = "";
    	String sql = "select  dictionary_id, item from sys_t_dictionary where dictsort_id = '115' and status = '1'";      
        //log.debug(sql);
    	shtml.append("<select id=\""+sname+"\" style = \"width:100%;font-size:12px\">");
          //
          Connection conn = null;                 //声明Connection对象
          PreparedStatement pstmt = null;         //声明PreparedStatement对象
          ResultSet rs = null;                    //声明ResultSet对象
          try {
              conn = DBUtils.getConnection();     //获取数据库连接
              pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement
              //参数
              rs = pstmt.executeQuery();
              
              while (rs.next()) {  
                id = rs.getString("dictionary_id");
                name = rs.getString("item");
                shtml.append("<option value=\""+id+"\">"+name+"</option>");
              }
          } catch (SQLException e) {
              log.debug(e.toString());
          } finally {
              DBUtils.close(rs);                  //关闭结果集
              DBUtils.close(pstmt);               //关闭PreparedStatement
              //DBUtils.close(conn);                //关闭连接
          }
        shtml.append("</select>");
    	return shtml;
    }
    /**
     * 取得业务审批意见下拉框选择
     * @param sname
     * @param smode
     * @return
     */
    public StringBuffer getPolicyCheckIdeaChoice(String sname,String smode){
    	StringBuffer shtml= new StringBuffer("");
    	String t1 = "",t2 = "",t3 = "",t4 = "";
    	String t1n = "",t2n = "",t3n = "",t4n = "";
    	String t5 = "",t6 = "",t7 = "",t8 = "";
    	String t5n = "",t6n = "",t7n = "",t8n = "";
    	//常量定义
        ConstantDefine constantdefine = new ConstantDefine(); 
        t1 = constantdefine.POLICYCHECKCODE_OK;
        t2 = constantdefine.POLICYCHECKCODE_RENEW;
        t3 = constantdefine.POLICYCHECKCODE_MOVE;
        t4 = constantdefine.POLICYCHECKCODE_OLE;
        t5 = constantdefine.POLICYCHECKCODE_STOP;
        t6 = constantdefine.POLICYCHECKCODE_REM;
        t7 = constantdefine.POLICYCHECKCODE_NOTOK;
        t8 = constantdefine.POLICYCHECKCODE_NEW; 
        t1n = constantdefine.POLICYCHECK_OK;
        t2n = constantdefine.POLICYCHECK_RENEW;
        t3n = constantdefine.POLICYCHECK_MOVE;
        t4n = constantdefine.POLICYCHECK_OLE;
        t5n = constantdefine.POLICYCHECK_STOP;
        t6n = constantdefine.POLICYCHECK_REM;
        t7n = constantdefine.POLICYCHECK_NOTOK;
        t8n = constantdefine.POLICYCHECK_NEW;
        shtml.append("<select id=\""+sname+"\" style = \"width:100%;font-size:12px\" onchange='SetIdeaResult()'>"); 
        	if(smode.equals(constantdefine.POLICYNEWCHECKCODE_NEWNOCHECK)){
        		shtml.append("<option value='"+t1+"'>"+t1n+"</option>");
        		shtml.append("<option value='"+t2+"'>"+t2n+"</option>");
        		shtml.append("<option value='"+t3+"'>"+t3n+"</option>");
        		shtml.append("<option value='"+t7+"'>"+t7n+"</option>");
        	}else if(smode.equals(constantdefine.POLICYNEWCHECKCODE_NEWRECHECK)){
        		shtml.append("<option value='"+t1+"'>"+t1n+"</option>");
        		shtml.append("<option value='"+t2+"'>"+t2n+"</option>");
        		shtml.append("<option value='"+t3+"'>"+t3n+"</option>");
        		shtml.append("<option value='"+t7+"'>"+t7n+"</option>");
        	}else if(smode.equals(constantdefine.POLICYNEWCHECKCODE_NEWOLE)){        		
        		shtml.append("<option value='"+t2+"'>"+t2n+"</option>");
        		shtml.append("<option value='"+t3+"'>"+t3n+"</option>");
        		shtml.append("<option value='"+t5+"'>"+t5n+"</option>");
        		shtml.append("<option value='"+t7+"'>"+t7n+"</option>");
        	}else if(smode.equals(constantdefine.POLICYNEWCHECKCODE_NEWTOP)){
        		shtml.append("<option value='"+t1+"'>"+t1n+"</option>");
        		shtml.append("<option value='"+t2+"'>"+t2n+"</option>");
        		shtml.append("<option value='"+t3+"'>"+t3n+"</option>");
        		shtml.append("<option value='"+t5+"'>"+t5n+"</option>");
        		shtml.append("<option value='"+t7+"'>"+t7n+"</option>");
        	}else if(smode.equals(constantdefine.POLICYNEWCHECKCODE_NEWBOM)){
        		shtml.append("<option value='"+t1+"'>"+t1n+"</option>");
        		shtml.append("<option value='"+t2+"'>"+t2n+"</option>");
        		shtml.append("<option value='"+t3+"'>"+t3n+"</option>");
        		shtml.append("<option value='"+t5+"'>"+t5n+"</option>");
        		shtml.append("<option value='"+t7+"'>"+t7n+"</option>");
        	}else if(smode.equals(constantdefine.POLICYNEWCHECKCODE_NEWREM)){        		
        		shtml.append("<option value='"+t2+"'>"+t2n+"</option>");
        		shtml.append("<option value='"+t3+"'>"+t3n+"</option>");
        		shtml.append("<option value='"+t4+"'>"+t4n+"</option>");
        		shtml.append("<option value='"+t5+"'>"+t5n+"</option>");
        		shtml.append("<option value='"+t7+"'>"+t7n+"</option>");
        	}else if(smode.equals(constantdefine.POLICYNEWCHECKCODE_NEWSTOP)){
        		shtml.append("<option value='"+t2+"'>"+t2n+"</option>");
        		shtml.append("<option value='"+t6+"'>"+t6n+"</option>");
    			shtml.append("<option value='"+t7+"'>"+t7n+"</option>");
        	}else if(smode.equals(constantdefine.POLICYNEWCHECKCODE_NEWREMCHECK)){        		
        		shtml.append("<option value='"+t2+"'>"+t2n+"</option>");
        		shtml.append("<option value='"+t3+"'>"+t3n+"</option>");
        		shtml.append("<option value='"+t4+"'>"+t4n+"</option>");
        		shtml.append("<option value='"+t5+"'>"+t5n+"</option>");
        		shtml.append("<option value='"+t7+"'>"+t7n+"</option>");
        	}else if(smode.equals(constantdefine.POLICYNEWCHECKCODE_NEWNOMATCH)){        		
        		shtml.append("<option value='"+t2+"'>"+t2n+"</option>");
        		shtml.append("<option value='"+t5+"'>"+t5n+"</option>");
    			shtml.append("<option value='"+t7+"'>"+t7n+"</option>");
        	}else{
        		shtml.append("<option value='"+t8+"'>"+t8n+"</option>");
        		shtml.append("<option value='"+t1+"'>"+t1n+"</option>");
        		shtml.append("<option value='"+t2+"'>"+t2n+"</option>");
        		shtml.append("<option value='"+t3+"'>"+t3n+"</option>");
        		shtml.append("<option value='"+t4+"'>"+t4n+"</option>");
        		shtml.append("<option value='"+t5+"'>"+t5n+"</option>");
        		shtml.append("<option value='"+t6+"'>"+t6n+"</option>");
        		shtml.append("<option value='"+t7+"'>"+t7n+"</option>");        		
        	}
		shtml.append("</select>");
		return shtml;
    }
    /**
     * 取得业务审批处理标识
     * @param pid
     * @param empid
     * @return
     */
    public String getCheckPolicyFlagsXml(String pid,String empid) {
    	String srep = "";
    	String pmoreflag = "0";
    	String empdepth = "",onedepth = "",enddepth = "",allaccflag = "0",flowflag = "0",checkmoreflag = "0",oneflowflag = "0",endflowflag = "0";
    	String pcheckflag = "0",pcheckmoreflag = "0",preportflag = "0",checkmoneyflag = "0",acctypeflag = "0";
    	// 表查询处理类
	    TableInfoQuery tableinfoquery = new TableInfoQuery();
    	//政策审批处理类
		PolicyManageCheckQuery policymanagecheckquery = new PolicyManageCheckQuery();	
		//政策审批处理类
		PolicyManageCheckManage policymanagecheckmanage = new PolicyManageCheckManage();		
			
	    //
	    empdepth = tableinfoquery.getempdepth(empid);
		onedepth = policymanagecheckquery.getPolicyOneFlowFromId(pid);
		enddepth = policymanagecheckquery.getPolicyEndFlowFromId(pid);

        
        Document doc = DocumentHelper.createDocument();
        doc.setXMLEncoding("GB18030");
        Element root = doc.addElement("root");
        Element data = root.addElement("data");
        //
        
        //是否已经终审结算标识       
        Element eIsAllAccFlag=data.addElement("allaccflag");
        Element eIsAllAccFlagChild =eIsAllAccFlag.addElement("flag");
        allaccflag = policymanagecheckmanage.getPolicyAllAccFlag(pid,empid);
        eIsAllAccFlagChild.setText(allaccflag);
        //该业务是否可以批量审批 
        Element eCheckflag=data.addElement("checkmoreflag");
        Element eCheckflagChild =eCheckflag.addElement("flag");
        checkmoreflag = policymanagecheckmanage.getPolicyCheckFlag(pid);
        eCheckflagChild.setText(checkmoreflag);
        //该用户机构是否存在审批流程  
        Element eIsFlowFlag=data.addElement("flowflag");
        Element eIsFlowFlagChild =eIsFlowFlag.addElement("flag");
        flowflag = policymanagecheckmanage.getPolicyFlowFlag(pid,empid);
        eIsFlowFlagChild.setText(flowflag); 
        //
        //该用户机构是否第一审批流程 
        Element eIsOneFlowFlag=data.addElement("oneflowflag");
        Element eIsOneFlowFlagChild =eIsOneFlowFlag.addElement("flag");
        if(empdepth.equals(onedepth)){
        	oneflowflag="1";
        	eIsOneFlowFlagChild.setText(oneflowflag);
        }else{
        	oneflowflag="0";
        	eIsOneFlowFlagChild.setText(oneflowflag);
        }
        //该用户机构是否终审流程    
        Element eIsEndFlowFlag=data.addElement("endflowflag");
        Element eIsEndFlowFlagChild =eIsEndFlowFlag.addElement("flag");
        if(empdepth.equals(enddepth)){
        	endflowflag="1";
        	eIsEndFlowFlagChild.setText(endflowflag);
        }else{
        	endflowflag="0";
        	eIsEndFlowFlagChild.setText(endflowflag);
        }     
        //
        //该用户岗位业务审批权限标识 
        pmoreflag = policymanagecheckmanage.getPolicyPostPowerFlags(pid,empid);
        if(pmoreflag.equals("")){
        	pcheckflag = "0";
        	pcheckmoreflag = "0";
        	preportflag = "0";
        }else{
        	String [] amoreflag = pmoreflag.split(",");
        	pcheckflag = amoreflag[0];//用户所属岗位是否有审批权限标识
        	pcheckmoreflag = amoreflag[1];//用户所属岗位是否有批量审批权限标识
        	preportflag = amoreflag[2];//用户所属岗位是否有确认审批名单权限标识
        }
        //用户所属岗位是否有审批权限标识
        Element ePcheckflag=data.addElement("pcheckflag");
        Element ePcheckflagChild =ePcheckflag.addElement("flag");
        ePcheckflagChild.setText(pcheckflag);
        //用户所属岗位是否有批量审批权限标识
        Element ePcheckmoreflag=data.addElement("pcheckmoreflag");
        Element ePcheckmoreflagChild =ePcheckmoreflag.addElement("flag");
        ePcheckmoreflagChild.setText(pcheckmoreflag);
        //用户所属岗位是否有确认审批名单权限标识       
        Element ePreportflag=data.addElement("preportflag");
        Element ePreportflagChild =ePreportflag.addElement("flag");
        ePreportflagChild.setText(preportflag);    
        //当前业务救助金可填写标识       
        Element eCheckMoneyFlag=data.addElement("checkmoneyflag");
        Element eCheckMoneyFlagChild =eCheckMoneyFlag.addElement("flag");
        checkmoneyflag = policymanagecheckmanage.getPolicyUserAccFlag(pid);
        eCheckMoneyFlagChild.setText(checkmoneyflag);
        //当前业务救助金核算类型标识       
        Element eAccTypeFlag=data.addElement("acctypeflag");
        Element eAccType =eAccTypeFlag.addElement("flag");
		acctypeflag = policymanagecheckmanage.getPolicyAccTypeFlag(pid);
        eAccType.setText(acctypeflag);
        //
        
        //
        Node node=  root.selectSingleNode("/root/data");
        //log.debug(node.asXML()+"   :xiuxiuxiuxiux:    "+doc.asXML());
        srep = node.asXML();
        //        
        return srep;
    }
    /**
     * 取得业务成员更多审批意见表格Html
     * @param pid
     * @param fmid
     * @param empid
     * @return
     */
    public StringBuffer getPolicyMoreCheckIdeaHtml(String pid,String checkid,String empid) {
    	StringBuffer shtml= new StringBuffer("");
    	StringBuffer shtml1= new StringBuffer("");
    	StringBuffer shtml2= new StringBuffer("");
    	//
    	String sdeptname1 = "",sdeptname2 = "",sdeptname3 = "",sdeptname4 = "",sdeptname5 = "";
    	String sflow1 = "",sflow2 = "",sflow3 = "",sflow4 = "",sflow5 = "";
    	String sdeptname = "",dempth = "",depth = "";
    	
    	//常量定义
    	ConstantDefine constantdefine = new ConstantDefine();
    	//政策审批处理类
		PolicyManageCheckQuery policymanagecheckquery = new PolicyManageCheckQuery(); 
		
    	//
    	//社区级别
    	sdeptname1 = constantdefine.POLICYQUERY_5;
    	dempth = constantdefine.POLICYQUERYCODE_5;
    	sflow1 = policymanagecheckquery.getPolicyFlowFlagFromId(pid,dempth);
    	//街道级别
    	sdeptname2 = constantdefine.POLICYQUERY_4;
    	dempth = constantdefine.POLICYQUERYCODE_4;
    	sflow2 = policymanagecheckquery.getPolicyFlowFlagFromId(pid,dempth);
    	//区级别
    	sdeptname3 = constantdefine.POLICYQUERY_3;
    	dempth = constantdefine.POLICYQUERYCODE_3;
    	sflow3 = policymanagecheckquery.getPolicyFlowFlagFromId(pid,dempth);
    	//市级别
    	sdeptname4 = constantdefine.POLICYQUERY_2;
    	dempth = constantdefine.POLICYQUERYCODE_2;
    	sflow4 = policymanagecheckquery.getPolicyFlowFlagFromId(pid,dempth);
    	//省级别
    	sdeptname5 = constantdefine.POLICYQUERY_1;
    	dempth = constantdefine.POLICYQUERYCODE_1;
    	sflow5 = policymanagecheckquery.getPolicyFlowFlagFromId(pid,dempth); 
    	//
    	shtml2.append("<tr height='23' align='center' class='colField'>");
	    	shtml2.append("<td>救助开始日期</td>");
	    	shtml2.append("<td>救助结束日期</td>");
	    	shtml2.append("<td>评议结果</td>");
	    	shtml2.append("<td>评议日期</td>");
	    	shtml2.append("<td>评议人</td>"); 
	    	shtml2.append("<td>致困原因</td>");
	    	shtml2.append("<td>评议意见</td>");
	    	shtml2.append("<td>评议确认人</td>"); 
	    	shtml2.append("<td>评议确认时间</td>");
	    	shtml2.append("<td>操作</td>");
    	shtml2.append("</tr>");
        //
        if(sflow1.equals("1")){
    		sdeptname = sdeptname1;
    		//
    		shtml.append("<fieldset>");
        	shtml.append("<legend>"+sdeptname+"</legend>");
        	//
        	shtml.append("<table class = 'tab' width='100%' border='0' cellspacing='0' cellpadding='0'>");
            //
            shtml.append(shtml2);
            //
            depth = constantdefine.POLICYQUERYCODE_5;
            shtml1 = getPolicyOneCheckIdeaHtml(pid,checkid,depth,empid);
            shtml.append(shtml1);
            //
          	shtml.append("</table>");
            shtml.append("</fieldset>");
        }
        if(sflow2.equals("1")){
    		sdeptname = sdeptname2;
    		//
    		shtml.append("<fieldset>");
        	shtml.append("<legend>"+sdeptname+"</legend>");
        	//
        	shtml.append("<table class = 'tab' width='100%' border='0' cellspacing='0' cellpadding='0'>");
        	//
            shtml.append(shtml2);
            //
            depth = constantdefine.POLICYQUERYCODE_4;
            shtml1 = getPolicyOneCheckIdeaHtml(pid,checkid,depth,empid);
            shtml.append(shtml1);
            //
          	shtml.append("</table>");
            shtml.append("</fieldset>");
        }
        if(sflow3.equals("1")){
    		sdeptname = sdeptname3;
    		//
    		shtml.append("<fieldset>");
        	shtml.append("<legend>"+sdeptname+"</legend>");
        	//
        	shtml.append("<table class = 'tab' width='100%' border='0' cellspacing='0' cellpadding='0'>");
        	//
            shtml.append(shtml2);
            //
            depth = constantdefine.POLICYQUERYCODE_3;
            shtml1 = getPolicyOneCheckIdeaHtml(pid,checkid,depth,empid);
            shtml.append(shtml1);
            //
          	shtml.append("</table>");
            shtml.append("</fieldset>");
        }
        if(sflow4.equals("1")){
    		sdeptname = sdeptname4;
    		//
    		shtml.append("<fieldset>");
        	shtml.append("<legend>"+sdeptname+"</legend>");
        	//
        	shtml.append("<table class = 'tab' width='100%' border='0' cellspacing='0' cellpadding='0'>");
        	//
            shtml.append(shtml2);
            //
            depth = constantdefine.POLICYQUERYCODE_2;
            shtml1 = getPolicyOneCheckIdeaHtml(pid,checkid,depth,empid);
            shtml.append(shtml1);
            //
          	shtml.append("</table>");
            shtml.append("</fieldset>");
        }
        if(sflow5.equals("1")){
    		sdeptname = sdeptname5;
    		//
    		shtml.append("<fieldset>");
        	shtml.append("<legend>"+sdeptname+"</legend>");
        	//
        	shtml.append("<table class = 'tab' width='100%' border='0' cellspacing='0' cellpadding='0'>");
        	//
            shtml.append(shtml2);
            //
            depth = constantdefine.POLICYQUERYCODE_1;
            shtml1 = getPolicyOneCheckIdeaHtml(pid,checkid,depth,empid);
            shtml.append(shtml1);
            //
          	shtml.append("</table>");
            shtml.append("</fieldset>");
        }
        //log.debug(shtml);
    	return shtml;
    }
    /**
     * 获取家庭审批意见
     * @param pid
     * @param checkid
     * @param depth
     * @param empid
     * @return
     */
    public StringBuffer getPolicyOneCheckIdeaHtml(String pid,String checkid,String depth,String empid) {
    	StringBuffer shtml= new StringBuffer("");
    	StringBuffer shtml1= new StringBuffer("");
    	String sql = "",stemp = "",scheckid = "",sideaid = "",empdepth = "";
    	boolean isflag = false; 
    	// 表查询处理类
	    TableInfoQuery tableinfoquery = new TableInfoQuery();
	    //政策审批处理类
		PolicyManageCheckQuery policymanagecheckquery = new PolicyManageCheckQuery();
    	//常量定义
    	ConstantDefine constantdefine = new ConstantDefine();
    	//审批机构级别
    	empdepth = tableinfoquery.getempdepth(empid);
    	//    	
    	//
    	//
    	sql = "select a.optcheckidea_id,a.optcheck_id,a.appresult,a.apptime,a.appideaman,a.apparea,a.rebegdt,a.reenddt,a.checkoper,a.checkdt " +
    			"from biz_t_optcheckidea a,biz_t_optcheck b " +
        			"where a.optcheck_id = b.optcheck_id and a.status = '1' " +
        			"and policy_id = '"+pid+"' and b.optcheck_id = '"+checkid+"' and depth = '"+depth+"' " +
        			"order by checkdt desc";   //定义SQL语句
    	
       	//log.debug(sql);
    	
        Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        ResultSet rs = null;                    //声明ResultSet对象
        try {
            conn = DBUtils.getConnection();     //获取数据库连接
            pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement   
            rs = pstmt.executeQuery();            
            while (rs.next()) {
            	//
            	isflag = true;
            	//
            	sideaid = rs.getString("optcheckidea_id");
            	scheckid = rs.getString("optcheck_id");
            	//
            	//
            	shtml1.append("<tr>");
                //日期格式化
                String tempdt = rs.getString("rebegdt");			                
                if(tempdt==null){
                	stemp = tempdt;
                }else{
                	stemp = policymanagecheckquery.getformatdt(tempdt);
                }
                if(stemp==null){
            		stemp = "无";
            	}
            	shtml1.append("<td width='80' height='23' class='colValue'>"+stemp+"</td>");
            	//
            	//日期格式化
                tempdt = rs.getString("reenddt");			                
                if(tempdt==null){
                	stemp = tempdt;
                }else{
                	stemp = policymanagecheckquery.getformatdt(tempdt);
                }
                if(stemp==null){
            		stemp = "无";
            	}
            	shtml1.append("<td width='80' height='23' class='colValue'>"+stemp+"</td>");
            	//
            	stemp = rs.getString("appresult");
            	if(stemp==null){
            		stemp = "无";
            	}else{
            		if(stemp.equals(constantdefine.POLICYCHECKCODE_NEW)){
	            		stemp = constantdefine.POLICYCHECK_NEW;
	            	}else if(stemp.equals(constantdefine.POLICYCHECKCODE_OK)){
	            		stemp = constantdefine.POLICYCHECK_OK;
	            	}else if(stemp.equals(constantdefine.POLICYCHECKCODE_RENEW)){
	            		stemp = constantdefine.POLICYCHECK_RENEW;
	            	}else if(stemp.equals(constantdefine.POLICYCHECKCODE_MOVE)){
	            		stemp = constantdefine.POLICYCHECK_MOVE;
	            	}else if(stemp.equals(constantdefine.POLICYCHECKCODE_OLE)){
	            		stemp = constantdefine.POLICYCHECK_OLE;
	            	}else if(stemp.equals(constantdefine.POLICYCHECKCODE_STOP)){
	            		stemp = constantdefine.POLICYCHECK_STOP;
	            	}else if(stemp.equals(constantdefine.POLICYCHECKCODE_REM)){
	            		stemp = constantdefine.POLICYCHECK_REM;
	            	}else if(stemp.equals(constantdefine.POLICYCHECKCODE_NOTOK)){
	            		stemp = constantdefine.POLICYCHECK_NOTOK;
	            	}
            	}			            	
            	shtml1.append("<td width='80' height='23' class='colValue'>"+stemp+"</td>");		            	
            	//日期格式化
                tempdt = rs.getString("apptime");			                
                if(tempdt==null){
                	stemp = tempdt;
                }else{
                	stemp = policymanagecheckquery.getformatdt(tempdt);
                }
                if(stemp==null){
            		stemp = "无";
            	}
            	shtml1.append("<td width='80' height='23' class='colValue'>"+stemp+"</td>");
            	stemp = rs.getString("appideaman");
            	if(stemp==null){
            		stemp = "无";
            	}
            	shtml1.append("<td width='80' height='23' class='colValue'>"+stemp+"</td>");
            	//致困原因
            	stemp = policymanagecheckquery.getPolicyCheckMoreTypes(sideaid);
            	if(stemp.equals("")){
            		stemp = "无";
            	}
            	shtml1.append("<td height='23' class='colValue'>"+stemp+"</td>");
            	//
            	stemp = rs.getString("apparea");
            	if(stemp==null){
            		stemp = "无";
            	}
            	shtml1.append("<td height='23' class='colValue' >"+stemp+"</td>");
            	//审批确认人ID            	
            	stemp = rs.getString("checkoper");
            	String checkempid = stemp; 
            	if(stemp==null){
            		stemp = "无";
            	}else{
            		stemp = tableinfoquery.getempname(stemp);
            	}
            	shtml1.append("<td width='80' height='23' class='colValue' >"+stemp+"</td>");            	
            	//时间格式化
                String temptime = rs.getString("checkdt");			                
                if(temptime==null){
                	stemp = temptime;
                }else{
                	stemp = policymanagecheckquery.getformattime(temptime);
                }
                if(stemp==null){
            		stemp = "无";
            	}
            	shtml1.append("<td width='120' height='23' class='colValue' >"+stemp+"</td>");
            	//
            	if(empdepth.equals(depth)){//本机构
            		if(empid.equals(checkempid)){//本人审批确认
            			shtml1.append("<td width='23' height='23' class='colValue' ><img src='/db/page/images/close.gif' alt= '作废' onclick= \"RemoveIdea('"+scheckid+"','"+sideaid+"')\"/></td>");
            		}else{//非本人审批确认
            			shtml1.append("<td width='23' height='23' class='colValue' >无</td>");
            		}
            	}else{//非本机构
            		shtml1.append("<td width='23' height='23' class='colValue' >无</td>");
            	}
            	
	            shtml1.append("</tr>");
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //关闭结果集
            DBUtils.close(pstmt);               //关闭PreparedStatement
            //DBUtils.close(conn);                //关闭连接
        }
        //
        if(isflag){
        	shtml.append(shtml1);
        }else{
        	shtml.append("<tr>");
        	shtml.append("<td width='80' height='23' class='colValue' >无</td>");
        	shtml.append("<td width='80' height='23' class='colValue' >无</td>");
        	shtml.append("<td width='80' height='23' class='colValue' >无</td>");
        	shtml.append("<td width='80' height='23' class='colValue' >无</td>");
        	shtml.append("<td width='80' height='23' class='colValue' >无</td>");
        	shtml.append("<td height='23' class='colValue' >无</td>");
        	shtml.append("<td height='23' class='colValue' >无</td>");
        	shtml.append("<td width='80' height='23' class='colValue' >无</td>");
        	shtml.append("<td width='120' height='23' class='colValue' >无</td>");
        	shtml.append("<td width='23' height='23' class='colValue' >无</td>");
        	shtml.append("</tr>");
        }
        //
    	return shtml;
    }
	/**
	 * 作废审批意见
	 * @param checkid
	 * @param ideaid
	 * @param empid
	 * @return
	 */
	public String removeIdea(String checkid,String ideaid,String empid){
		String srep = "";
		//政策审批处理类
		PolicyManageCheckQuery policymanagecheckquery = new PolicyManageCheckQuery();
		//
		if(!policymanagecheckquery.existsEmpCheckIdea(ideaid,empid)){
			srep = "只能作废您的评议审批意见!";
		}else{
			if(!policymanagecheckquery.existsEmpCheckMoreIdea(checkid,empid)){
				srep = "至少保留一条您的评议审批意见!";
			}else{
				//
				String sql  = "update biz_t_optcheckidea set status = '0' where optcheckidea_id = '"+ideaid+"'";  //SQL
		        //log.debug(sql);
		        
		        Connection conn = null;                 //声明Connection对象
		        PreparedStatement pstmt = null;         //声明PreparedStatement对象
		        try {
		            conn = DBUtils.getConnection();     //获取数据库连接
		            conn.setAutoCommit(false);
		            pstmt = conn.prepareStatement(sql);//根据sql创建PreparedStatement            
		            pstmt.executeUpdate();              //执行            
		            conn.commit();                      //关闭
		            srep = "作废审批意见操作成功!";
		        } catch (SQLException e) {
		        	try {
		        		srep = "作废审批意见操作失败!";
			            conn.rollback();
			        } catch (SQLException e1) {          
			            e1.printStackTrace();
			        }
		        } finally {
		            DBUtils.close(pstmt);               //关闭PreparedStatement
		            //DBUtils.close(conn);                //关闭连接
		        }
			}
		}
		return srep;
	}
	/**
	 * 撤消本级家庭审批
	 * @param empid
	 * @param pid
	 * @param fmid
	 * @return
	 */
	public String removeCheckIdea(String empid,String pid,String fmid){
		String srep = "0",checkid = "";
		String empdepth = "";
        String empcheckflag = "",newresult = "";
        // 表查询处理类
      	TableInfoQuery tableinfoquery = new TableInfoQuery();      	
      	//政策审批处理类
		PolicyManageCheckQuery policymanagecheckquery = new PolicyManageCheckQuery();
	    //常量定义
        ConstantDefine constantdefine = new ConstantDefine();
        //
        empdepth = tableinfoquery.getempdepth(empid);
        //
        newresult = constantdefine.POLICYCHECKCODE_NEW;
        //
        if(empdepth.equals(constantdefine.POLICYQUERYCODE_5)){
        	empcheckflag = "checkflag1";
        }else if(empdepth.equals(constantdefine.POLICYQUERYCODE_4)){
        	empcheckflag = "checkflag2";
        }else if(empdepth.equals(constantdefine.POLICYQUERYCODE_3)){
        	empcheckflag = "checkflag3";
        }else if(empdepth.equals(constantdefine.POLICYQUERYCODE_2)){
        	empcheckflag = "checkflag4";
        }else if(empdepth.equals(constantdefine.POLICYQUERYCODE_1)){
        	empcheckflag = "checkflag5";
        }
		//重新审批该业务该家庭是否存在
		checkid = policymanagecheckquery.getPolicyIsRmCheckId(empid,pid,fmid);
		if(checkid.equals("")){
			srep = "撤消审批意见操作失败!";
			return srep;
		}
		String sql1 = "update biz_t_optcheck set "+empcheckflag+" = '"+newresult +"',ifover = '"+empdepth +"' " +
						"where optcheck_id = '" + checkid + "' ";
        
		String sql2  = "update biz_t_optcheckidea set status = '0' " +
						"where optcheck_id = '"+checkid+"' and depth = '"+empdepth+"'";  //SQL
        //log.debug(sql);
        
        Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        try {
            conn = DBUtils.getConnection();     //获取数据库连接
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(sql1);//根据sql创建PreparedStatement            
            pstmt.executeUpdate();              //执行 
            pstmt = conn.prepareStatement(sql2);//根据sql创建PreparedStatement            
            pstmt.executeUpdate();              //执行
            conn.commit();                      //关闭
            srep = "撤消审批意见操作成功!";
        } catch (SQLException e) {
        	try {
        		srep = "撤消审批意见操作失败!";
	            conn.rollback();
	        } catch (SQLException e1) {          
	            e1.printStackTrace();
	        }
        } finally {
            DBUtils.close(pstmt);               //关闭PreparedStatement
            //DBUtils.close(conn);                //关闭连接
        }
		return srep;
	}
	/**
	 * 获取业务分类施保各级审批明细
	 * @param checkchildid
	 * @return
	 */
	public StringBuffer getPolicyChildCheckIdeasHtml(String checkchildid) {
    	StringBuffer shtml= new StringBuffer("");
    	String html = "",temp = "";
    	//
    	String sdeptname1 = "",sdeptname2 = "",sdeptname3 = "",sdeptname4 = "",sdeptname5 = "";
    	String dempth1 = "",dempth2 = "",dempth3 = "",dempth4 = "",dempth5 = "";
    	//
    	String sid = "";
    	int irow = 0;
    	//常量定义
	    ConstantDefine constantdefine = new ConstantDefine();
	    // 表查询处理类
	    TableInfoQuery tableinfoquery = new TableInfoQuery();
	    //政策审批处理类
		PolicyManageCheckQuery policymanagecheckquery = new PolicyManageCheckQuery();
	    //社区级别
    	sdeptname1 = constantdefine.POLICYQUERY_5;
    	dempth1 = constantdefine.POLICYQUERYCODE_5;
    	//街道级别
    	sdeptname2 = constantdefine.POLICYQUERY_4;
    	dempth2 = constantdefine.POLICYQUERYCODE_4;
    	//区级别
    	sdeptname3 = constantdefine.POLICYQUERY_3;
    	dempth3 = constantdefine.POLICYQUERYCODE_3;
    	//市级别
    	sdeptname4 = constantdefine.POLICYQUERY_2;
    	dempth4 = constantdefine.POLICYQUERYCODE_2;
    	//省级别
    	sdeptname5 = constantdefine.POLICYQUERY_1;
    	dempth5 = constantdefine.POLICYQUERYCODE_1;
	    //
	    
    	html = "<fieldset>";
		html += "<legend>各级分类施保评议</legend>";
		html += "<table width='100%' cellpadding='0' cellspacing='0'>";
		html += "<tr class='colField'>";
			temp ="<td height='23'>评议审批机构</td>";
			html +=temp;
			temp ="<td height='23'>拟发金额</td>";
			html +=temp;
			temp ="<td height='23'>评议确认人</td>";
			html +=temp;
			temp ="<td height='23'>评议确认时间</td>";
			html +=temp;
		html +="</tr>";
		//
        String sql = "select optcheckchildidea_id,depth,childmoney,childcheckoper,childcheckdt " +
        				"from biz_t_optcheckchildidea " +
        					"where status = '1' and optcheckchild_id = '"+checkchildid+"' " +
        					"order by depth,childcheckdt";   //定义SQL语句
        
        //log.debug(sql);
        Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        ResultSet rs = null;                    //声明ResultSet对象
        try {
            conn = DBUtils.getConnection();     //获取数据库连接
            pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement   
            rs = pstmt.executeQuery();
            while (rs.next()) {
            	//
            	irow ++;
            	//
                sid = rs.getString("optcheckchildidea_id");
                
                html +="<tr>";		
		    		//各列值
                	temp = rs.getString("depth");
                	if(temp.equals(dempth1)){
                		temp = sdeptname1;
                	}else if(temp.equals(dempth2)){
                		temp = sdeptname2;
                	}else if(temp.equals(dempth3)){
                		temp = sdeptname3;
                	}else if(temp.equals(dempth4)){
                		temp = sdeptname4;
                	}else if(temp.equals(dempth5)){
                		temp = sdeptname5;
                	}
                	html += "<td height='23' class='colValue'>"+temp+"</td>";
                	//
                	temp = rs.getString("childmoney");
                	html += "<td height='23' class='colValue'>"+temp+"</td>";
                	//审批确认人ID            	
                	temp = rs.getString("childcheckoper");
                	temp = tableinfoquery.getempname(temp);
                	html += "<td height='23' class='colValue'>"+temp+"</td>";
                	//时间格式化
                    String temptime = rs.getString("childcheckdt");			                
                    temp = policymanagecheckquery.getformattime(temptime);
                    html += "<td height='23' class='colValue'>"+temp+"</td>";                    
				html +="</tr>";
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //关闭结果集
            DBUtils.close(pstmt);               //关闭PreparedStatement
            //DBUtils.close(conn);                //关闭连接
        }
        
	    //是否存在
	    if(irow==0){
	    	html += "<tr>";
	    	html += "<td height='23' class='colValue' >无</td>";
	    	html += "<td height='23' class='colValue' >无</td>";
	    	html += "<td height='23' class='colValue' >无</td>";
	    	html += "<td height='23' class='colValue' >无</td>";
	    	html += "</tr>";
	    }
		    //
	    	html +="</table>"; 
	    html += "</fieldset>";
	    //
	    shtml.append(html);
	    //log.debug(shtml);
	    //
        return shtml;
    }
}
