package com.mingda.action.policy.approval2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.dom4j.Document;

import com.mingda.action.info.search.DBUtils;
import com.mingda.action.policy.comm.PublicMoney;
import com.mingda.common.page.PageView;

public class Policychecksql {
	static Logger log = Logger.getLogger(Policychecksql.class);
	/**
	 * 生成审批页面表格
	 * @param hashmap
	 * @return
	 */
	public StringBuffer getCheckPageTable(HashMap hashmap) {
		StringBuffer shtml= new StringBuffer("");
		//
		Policycheckpage policycheckpage = new Policycheckpage();
		//========字典类型处理===========//
		PageView pv = new PageView();
		//字典值
    	Document dictionary = (Document) hashmap.get("dictionary");
    	//
		//========字典类型处理===========//
		//
		String jtabid = hashmap.get("jtabid").toString();
		//
		String sql = hashmap.get("sql").toString();
		String xmlth = hashmap.get("xmlth").toString();
		//
		String accflag = hashmap.get("accflag").toString();
		//
		String onecheck = hashmap.get("onecheck").toString();
		String endcheck = hashmap.get("endcheck").toString();
		String usercheck = hashmap.get("usercheck").toString();
		String morecheck = hashmap.get("morecheck").toString();
		String usermorecheck = hashmap.get("usermorecheck").toString();
		String report = hashmap.get("report").toString();
		//
		String smorecheck = "0";		
		if(usercheck.equals("1")){
			smorecheck = "1";
			if(morecheck.equals("1")){
				smorecheck = "2";
				if(!usermorecheck.equals("1")){
					smorecheck = "1";
				}
			}
		}
		//===================列描述========================================================================
		//家庭ID,家庭编号,户主姓名,保障人口,计算收入,保障类型,上期救助金,拟发救助金,救助状态,评议结果,所属,所属ID,信息变化标识
		//屏蔽[所属ID]列
		//===================列描述========================================================================
		//=============================================table
		String[] columu_name = null;      
        int columnCount =0;       
        //
        columu_name = xmlth.split(",");
        columnCount = columu_name.length;
        //
        shtml.append("<table id = 'requesttb' width='100%' cellpadding='0' cellspacing='0'>");  		  	
		//=============================th
  		shtml.append("<tr class='colField'>");
  		if(jtabid.equals("6")){	//全部名单标签查询
  			//无
  		}else if(jtabid.equals("5")){	//已审批名单标签查询
  			shtml.append("<td height='25'>撤销</td>");
  		}else if(jtabid.equals("4")){	//上期顺延名单标签查询
  			//无
  		}else{
  			shtml.append("<td height='25'>操作</td>");
  		}  		
        for (int i = 1; i < columnCount-2; i++) {    //屏蔽[所属ID]列          
        	//查询字段列名称
        	columu_name[i] = columu_name[i].replace(".",",");
        	String[] th_name = columu_name[i].split(",");
        	String thname = th_name[1];
        	shtml.append("<td height='25'>"+thname+"</td>");         
        }
        shtml.append("<td height='25'>相关操作</td>");
        shtml.append("</tr>");
        //=============================th
        //=============================tr
        //log.debug(xmlth);
        //log.debug(sql);
	    Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        ResultSet rs = null;                    //声明ResultSet对象
        int irow = 0;
        try {
            conn = DBUtils.getConnection();     //获取数据库连接
            pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement   
            rs = pstmt.executeQuery();
            //
            while (rs.next()) {
            	String fmid = "",fmno = "",ischange = "0";
            	//
            	fmid = rs.getString(1);
            	fmno = rs.getString(2);
            	ischange = rs.getString(13);
            	//单双行
				int mathrow = irow%2;				
				if(mathrow == 1){//背景颜色
					shtml.append("<tr style = 'background: #F2F5F7;'>");						
				}else{
					shtml.append("<tr>");
				}
				//信息列
				if("2".equals(accflag)){	//结算完成状态
					if(jtabid.equals("6")){	//全部名单标签查询
			  			//无
			  		}else if(jtabid.equals("5")){	//已审批名单标签查询
			  			shtml.append("<td height='25' class='colValue'>" 
							+ "<input type='radio' name='rd[]' disabled = 'disabled' /></td>");
			  		}else if(jtabid.equals("4")){	//上期顺延名单标签查询
			  			//无
			  		}else{
			  			shtml.append("<td height='25' class='colValue'>" 
							+ "<input type='radio' name='rd[]' disabled = 'disabled' /></td>");
			  		}					
				}else{						//未结算状态
					if(jtabid.equals("6")){			//全部名单标签查询
//						shtml.append("<td height='25' class='colValue'>" 
//								+ "<input type='radio' name='rd[]'  disabled = 'disabled' /></td>");
					}else if(jtabid.equals("5")){	//已审批名单标签查询
						if(smorecheck.equals("0")){		//无审批权限
							shtml.append("<td height='25' class='colValue'>" 
									+ "<input type='radio' name='rd[]' disabled = 'disabled' /></td>");
						}else{							//有审批权限
							//单个撤销审批结果
							shtml.append("<td height='25' class='colValue'>" 
									+ "<input type='radio' name='rd[]' onclick= \"DelIdea('"+fmid+"')\"/></td>");
						}
					}else if(jtabid.equals("4")){	//上期顺延名单标签查询
//						shtml.append("<td height='25' class='colValue'>" 
//								+ "<input type='radio' name='rd[]'  disabled = 'disabled' /></td>");
					}else{							//其他标签查询
						if(smorecheck.equals("0")){		//无审批权限
							shtml.append("<td height='25' class='colValue'>" 
									+ "<input type='radio' name='rd[]' disabled = 'disabled' /></td>");
						}else if(smorecheck.equals("1")){//单个审批
							shtml.append("<td height='25' class='colValue'>" 
									+ "<input type='radio' name='rd[]' onclick= \"CallOneIdea('"+fmid+"')\"/></td>");
						}else if(smorecheck.equals("2")){//批量审批
							shtml.append("<td height='25' class='colValue'>" 
									+ "<input type='checkbox' name='cbx[]' id = '"+fmid+"' onclick= \"chkTbRow(this)\"/></td>");
						}
		            }
				}				
				//家庭编号
				shtml.append("<td height='25' class='colValue pointer' title = '查看家庭信息' " 
					+ "onclick= \"infoaction('"+fmid+"')\">"+fmno+"</td>");
				//
          		for (int i = 2; i < columnCount-2; i++) {	//屏蔽[所属ID]列
          			//开始处理各列值
          			if(i==5){			//保障类型
          				String tem = rs.getString(i+1);
          				tem = pv.getDictionartHandle().getDictsortToValue(dictionary,tem);
        				if(null == tem || "".equals(tem)){
        					tem = "无";
        				}
        				//信息有变动
        				if("1".equals(ischange)){
        					shtml.append("<td height='25' class='colValue' style = 'color: red;'>"+ tem +"</td>");
        				}else{
        					shtml.append("<td height='25' class='colValue' >"+ tem +"</td>");
        				}
          					            		
          			}else if(i==6){			//上期救助金
          				String tem = PublicMoney.parseMoney(rs.getString(i+1));
          				shtml.append("<td height='25' class='colValue'>"+ tem +"</td>");	            		
          			}else if(i==7){		//拟发救助金
          				String tem = PublicMoney.parseMoney(rs.getString(i+1));
          				shtml.append("<td height='25' class='colValue'>"+ tem +"</td>");	             		
          			}else if(i==8){		//救助状态
          				shtml.append("<td height='25' class='colValue'  style = 'color: #6BA6FF;'>"
	            				+ policycheckpage.repalcePolicyMoneyFlag(rs.getString(i+1))+"</td>");	            		
          			}else if(i==9){		//审批结果
          				if(jtabid.equals("4") ){			//上期顺延名单标签查询
          					shtml.append("<td height='25' class='colValue' style = 'color: #6BA6FF;'>顺延</td>");
          				}else{
          					shtml.append("<td height='25' class='colValue pointer' title = '审批详细' " 
          						+ "onclick= \"GetCheckIdeaFlow('"+fmid+"')\">"
    	            			+ policycheckpage.repalcePolicyCheckFlag(rs.getString(i+1))+"</td>");
          				}
          			}else{          				
          				shtml.append("<td height='25' class='colValue'>"+rs.getString(i+1)+"</td>");
                	}         
                }
          		//相关操作列
          		shtml.append("<td height='25' class='colValue'>" 
          				+ "<span  class = 'pointer'>" 
          				+ "<img src='/db/page/images/checkrepidea.png' alt= '相关信息' " 
          				+ "onclick=\"GetCheckPolicyInfosHtml('"+fmid+"','')\"/> "
          				+ "<img src='/db/page/images/checkreqidea.png' alt= '走访登记' " 
          				+ "onclick=\"CallInterviewDialog("+fmid+")\"/>");
          				//编辑家庭信息
		          		if("1".equals(onecheck)){	//第一级审批机构
		          			shtml.append("&nbsp;<img src='/db/page/images/checkfamily.png' alt= '编辑家庭' " 
		          				+ "onclick= \"infoeditaction('"+fmid+"')\"/>");
		          		}
		          		shtml.append("</span>");
		        shtml.append("</td>");
          		shtml.append("</tr>");
          		//
          		irow++;
            }
        } catch (SQLException e) {
            log.debug(e.toString());            
        } finally {
            //DBUtils.close(rs);                  //关闭结果集
            DBUtils.close(pstmt);               //关闭PreparedStatement
            DBUtils.close(conn);                //关闭连接
        }
        
        //=============================tr        
        shtml.append("</table>");
        //=============================================table
		return shtml;
	}
}
