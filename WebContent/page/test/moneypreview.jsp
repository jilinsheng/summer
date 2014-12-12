<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
</head>
<style type="text/css">
 	<!--
	body {
		margin: 0;
		padding: 0;
		font-family: "宋体";
		font-size: 12px;
	}
	.myspace{
		border-top:1px solid #DEEFFF;
		border-bottom:1px solid #DEEFFF;
		border-left:1px solid #DEEFFF;
		border-right:1px solid #DEEFFF;
		font-size:12px
	}
	.mybackground{
		background: url('/db/page/images/newscenter.gif');
	}
	.tab {
		width: 100%;		
		margin: 0 auto;
		overflow: hidden;				
	}
	.list {
		padding: 4px;
		border: 3px double #f5a89a;
	}		
	.legend {
		font-size: 12px;
		font-weight: lighter;
		color: #000000;
	}
	.btn { 
		BORDER-RIGHT: #002D96 1px solid; PADDING-RIGHT: 0px; BORDER-TOP: 
	    #002D96 1px solid; PADDING-LEFT: 0px; FONT-SIZE: 12px; FILTER: 
	    progid:DXImageTransform.Microsoft.Gradient(GradientType=0, 
	    StartColorStr=#FFFFFF, EndColorStr=#9DBCEA); BORDER-LEFT: #002D96 
	    1px solid; CURSOR: hand; COLOR: black; PADDING-TOP: 0px; PADDING-BOTTOM: 0px;
	    BORDER-BOTTOM: #002D96 1px solid
    }
	.colField {
		font-family: "宋体";
		font-size: 9pt;
		text-align: center;
		border-top-width: 1px;
		border-right-width: 0px;
		border-bottom-width: 0px;
		border-left-width: 1px;
		border-top-style: solid;
		border-right-style: solid;
		border-bottom-style: solid;
		border-left-style: solid;
		color: #000000;
		background: #F5A89A;
		height:23;
	}	
	.colValue {
		font-family: "宋体";
		font-size: 9pt;
		text-align: center;
		border-top-width: 1px;
		border-right-width: 0px;
		border-bottom-width: 0px;
		border-left-width: 1px;
		border-top-style: solid;
		border-right-style: solid;
		border-bottom-style: solid;
		border-left-style: solid;
	}
	.pointer {
		cursor: pointer;
	}
	.SortDescCss{
		background-image:url(/db/page/images/tabledesc.gif);
		background-repeat:no-repeat;
		background-position:right center;
	}
	.SortAscCss{
		background-image:url(/db/page/images/tableasc.gif);
		background-repeat:no-repeat;
		background-position:right center;
	}		
	#pagestatusdiv{
		position:absolute;
		z-index:2;
		color: #FF0099;
		font-weight: bold;
		font-size:14px;
		display:none;
	}
	#resultstatusdiv{
		border-top: 1px solid buttonhighlight; 
		border-left: 1px solid buttonhighlight;  
		border-bottom: 1px solid buttonshadow;  
		border-right: 1px solid buttonshadow; 
		position:absolute;
		z-index:2;
		font-weight: bold;
		color: #FF0099;
		font-size:14px;
		text-align:center;
		background-color: #FFCCCC;
		display:none;
	}
	-->
  </style>
<body>
	<fieldset id = "familyitems" class='list'>
        <legend class='legend'>各地区资金预算设置</legend>			
        <table class="tab" width="100%" border="0" cellspacing="0" cellpadding="0">	   		  		
            <tr align="center">
                <td width="30%" valign="top" class = "myspace">
                	<legend>各地区资金列表</legend>			
                    <table class="tab" width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr align="center">
                            <td width="100%" class = "colField">机构名称</td>	
                        </tr> 
                        <tr align="center">
                            <td width="100%" class = "colField"><img src="1.2.2.7-1.jpg" /></td>	
                        </tr>                         		   				    	    	
                    </table> 
                </td>	
                <td width="60%" valign="top" class = "myspace">
                	<legend>吉林省－2008年10月资金预算申请</legend>			
                    <table class="tab" width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr align="center" >
                            <td width="30%" class = "colField">属性名</td>	
                            <td width="60%" class = "colField">属性值</td>
                        </tr>
                        <tr align="center" >
                            <td height="23" style="color: #660099;background-color:#ECECEC;text-align:center;font-size:12px;">上期预算</td>	
                            <td height="23" class = "colValue">100,100,000.00元</td>
                        </tr>	
                        <tr align="center">
                            <td height="23" style="color: #660099;background-color:#ECECEC;text-align:center;font-size:12px;">上期下拨</td>	
                            <td height="23" class = "colValue">100,100,000.00元</td>
                        </tr>
                        <tr align="center">
                            <td height="23" style="color: #660099;background-color:#ECECEC;text-align:center;font-size:12px;">上期发放</td>
                            <td class = "colValue">101,190,000.00元</td>
                        </tr>
                        <tr align="center">
                            <td height="23" style="color: #660099;background-color:#ECECEC;text-align:center;font-size:12px;">上期结余</td>
                            <td class = "colValue">11,000.00元</td>
                        </tr>
                        <tr align="center">
                            <td height="23" style="color: #660099;background-color:#ECECEC;text-align:center;font-size:12px;">本期预算日期</td>
                            <td class = "colValue" style = "text-align:left;"><input name="input" type="text" style="width:80% " value="2008-09-20" /></td>
                        </tr>	
                        <tr align="center">
                            <td height="23" style="color: #660099;background-color:#ECECEC;text-align:center;font-size:12px;">本期预算 </td>	
                            <td class = "colValue" style = "text-align:left;"><input name="input2" type="text" style="width:80% " value="100,100,000.00" />
                            元</td>
                        </tr> 
                        <tr align="center">
                            <td height="23" style="color: #660099;background-color:#ECECEC;text-align:center;font-size:12px;">下级地方财政</td>	
                            <td class = "colValue"><label>1,190,000.00元</label></td>
                        </tr>
                        <tr align="center">
                            <td height="23" style="color: #660099;background-color:#ECECEC;text-align:center;font-size:12px;">本级财政</td>	
                            <td class = "colValue"><span class="colValue" style="text-align:left;">
                                <input name="input3" type="text" style="width:80%" value="1,100,000.00" />
元</span></td>
                        </tr>
                        <tr align="center">
                            <td height="23" style="color: #660099;background-color:#ECECEC;text-align:center;font-size:12px;">上期追加申请</td>	
                            <td class = "colValue">200,000.00元</td>
                        </tr>
                        <tr align="center" >                            
                            <td  colspan="2" height="23" class = "colValue"><input name="" type="button" value="保存" /></td>	   		   				    	    	
                    </table> 
                </td>
            </tr>		    		   				    	    	
        </table> 	
    </fieldset>  
    <fieldset id = "familyitems" class='list'>
        <legend class='legend'>吉林省预算</legend>			
        <table class="tab" width="100%" border="0" cellspacing="0" cellpadding="0">	   		  		
            <tr align="center">
            	<td class = "colField">预算机构</td>	
                <td class = "colField">上期预算</td>	
                <td class = "colField">上期下拨</td>
                <td class = "colField">上期发放</td>	
                <td class = "colField">上期结余</td>
                <td class = "colField">本期预算日期</td>	
                <td class = "colField">本期预算</td>
                <td class = "colField">下级地方财政</td>
                <td class = "colField">本级财政</td>	
                <td class = "colField">上期追加申请</td>
            </tr>
            <tr align="center">
            	<td class = "colValue" height="23">长春</td>	
                <td class = "colValue" height="23">10,100,000.00</td>	
                <td class = "colValue" height="23">10,100,000.00</td>
                <td class = "colValue" height="23">10,100,000.00</td>	
                <td class = "colValue" height="23">0.00</td>
                <td class = "colValue" height="23">2008-09-10</td>	
                <td class = "colValue" height="23">10,100,000.00</td>
                <td class = "colValue" height="23">1,100,000.00</td>
                <td class = "colValue" height="23">9,100,000.00</td>	
                <td class = "colValue" height="23">0.00</td>
            </tr>
            <tr align="center">
            	<td class = "colValue" height="23">吉林</td>	
                <td class = "colValue" height="23">8,100,000.00</td>	
                <td class = "colValue" height="23">8,100,000.00</td>
                <td class = "colValue" height="23">8,100,000.00</td>	
                <td class = "colValue" height="23">0.00</td>
                <td class = "colValue" height="23">2008-09-10</td>	
                <td class = "colValue" height="23">8,100,000.00</td>
                <td class = "colValue" height="23">800,000.00</td>
                <td class = "colValue" height="23">900,000.00</td>	
                <td class = "colValue" height="23">0.00</td>
            </tr>	
            <tr align="center">
            	<td class = "colValue" height="23">合计:</td>	
                <td class = "colValue" height="23">18,100,000.00</td>	
                <td class = "colValue" height="23">18,100,000.00</td>
                <td class = "colValue" height="23">18,100,000.00</td>	
                <td class = "colValue" height="23">0.00</td>
                <td class = "colValue" height="23"></td>	
                <td class = "colValue" height="23">18,100,000.00</td>
                <td class = "colValue" height="23">1,900,000.00</td>
                <td class = "colValue" height="23">1,000,000.00</td>	
                <td class = "colValue" height="23">0.00</td>
            </tr>					    		   				    	    	
        </table> 	
    </fieldset>  
    <fieldset id = "familyitems" class='list'>
        <legend class='legend'>救助资金来源</legend>			
        <table class="tab" width="100%" border="0" cellspacing="0" cellpadding="0">	   		  		
            <tr align="center">
               <td width="30%" valign="top" class = "myspace">
                	<legend>各地区资金列表</legend>			
                    <table class="tab" width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr align="center">
                            <td width="100%" class = "colField">机构名称</td>	
                        </tr> 
                        <tr align="center">
                            <td width="100%" class = "colField"><img src="1.2.2.7-1.jpg" /></td>	
                        </tr>                         		   				    	    	
                    </table> 
                </td>
                 <td width="60%" valign="top" class = "myspace">
                	<legend>吉林省－2008年10月资金预算登记</legend>			
                    <table class="tab" width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr align="center" >
                            <td width="30%" class = "colField">属性名</td>	
                            <td width="60%" class = "colField">属性值</td>
                        </tr>
                        <tr align="center" >
                            <td height="23" style="color: #660099;background-color:#ECECEC;text-align:center;font-size:12px;">上级下拨</td>	
                            <td height="23" class = "colValue">100,100,000.00元</td>
                        </tr>
                        <tr align="center" >
                            <td height="23" style="color: #660099;background-color:#ECECEC;text-align:center;font-size:12px;">资金来源</td>	
                            <td height="23" class = "colValue"><select>
                                <option value="0" selected="selected">社会捐赠</option>
                            </select></td>
                        </tr>
                        <tr align="center" >
                            <td height="23" style="color: #660099;background-color:#ECECEC;text-align:center;font-size:12px;">资金总数</td>	
                            <td height="23" class = "colValue"><span class="colValue" style="text-align:left;">
                                <input name="input4" type="text" style="width:80%" value="10,000.00" />
元</span></td>
                        </tr>
                        <tr align="center" >                            
                            <td  colspan="2" height="23" class = "colValue"><input name="" type="button" value="保存" /></td>
                        </tr>
                        <tr align="center" >                            
                            <td  colspan="2" height="23" class = "colValue">
                            	<table class="tab" width="100%" border="0" cellspacing="0" cellpadding="0">	   		  		
                                <tr align="center">
                                    <td class = "colField">资金来源单位</td>	
                                    <td class = "colField">资金来源类别</td>
                                    <td class = "colField">资金总数</td>	
                                    <td class = "colField">操作</td>	
                                </tr>
                                <tr align="center">
                                    <td class = "colValue" height="23">吉林省福利彩票</td>	
                                    <td class = "colValue" height="23">福利彩票</td>	
                                    <td class = "colValue" height="23">100,000.00</td>
                                    <td class = "colValue" height="23"><img src=/db/page/images/check2.png></img></td>    
                                </tr>
                                <tr align="center">
                                    <td class = "colValue" height="23">吉林省少年教育资金</td>	
                                    <td class = "colValue" height="23">社会捐赠</td>	
                                    <td class = "colValue" height="23">100,000.00</td>
                                    <td class = "colValue" height="23"><img src=/db/page/images/check2.png></img></td>    
                                </tr>
                                <tr align="center">
                                    <td class = "colValue" height="23">合计:</td>	
                                    <td class = "colValue" height="23"></td>	
                                    <td class = "colValue" height="23">200,000.00</td>
                                    <td class = "colValue" height="23"></td>
                                </tr>					    		   				    	    	
                            </table>
                            </td>
                        </tr>
                    </table> 
                </td>	 
            </tr>
        </table> 	
    </fieldset>  
    <fieldset id = "familyitems" class='list'>
        <legend class='legend'>救助资下拨管理</legend>			
        <table class="tab" width="100%" border="0" cellspacing="0" cellpadding="0">	   		  		
            <tr align="center">
               <td width="30%" valign="top" class = "myspace">
                	<legend>各地区资金列表</legend>			
                    <table class="tab" width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr align="center">
                            <td width="100%" class = "colField">机构名称</td>	
                        </tr> 
                        <tr align="center">
                            <td width="100%" class = "colField"><img src="1.2.2.7-1.jpg" /></td>	
                        </tr>                         		   				    	    	
                    </table> 
                </td>
                 <td width="60%" valign="top" class = "myspace">
                	<legend>吉林省－2008年10月资金下拨</legend>			
                    <table class="tab" width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr align="center" >
                            <td width="30%" class = "colField">属性名</td>	
                            <td width="60%" class = "colField">属性值</td>
                        </tr>                        
                        <tr align="center" >
                            <td height="23" style="color: #660099;background-color:#ECECEC;text-align:center;font-size:12px;">资金来源</td>	
                            <td height="23" class = "colValue"><select>
                                <option value="0" selected="selected">财政拨付</option>
                            </select></td>
                        </tr>
                        <tr align="center" >
                            <td height="23" style="color: #660099;background-color:#ECECEC;text-align:center;font-size:12px;">资金总数</td>	
                            <td height="23" class = "colValue"><span class="colValue" style="text-align:left;">
                                <input name="input4" type="text" style="width:80%" value="9,100,000.00" />
元</span></td>
                        </tr>
                        <tr align="center" >
                            <td height="23" style="color: #660099;background-color:#ECECEC;text-align:center;font-size:12px;">下拨日期</td>	
                            <td height="23" class = "colValue"><span class="colValue" style="text-align:left;">
<span class="colValue" style="text-align:left;">
<input name="input5" type="text" style="width:80%" value="2008-10-09" />
</span></span></td>
                        </tr>
                        <tr align="center" >                            
                            <td  colspan="2" height="23" class = "colValue"><input name="" type="button" value="保存" /></td>
                        </tr>
                        <tr align="center" >                            
                            <td  colspan="2" height="23" class = "colValue">
                            	<table class="tab" width="100%" border="0" cellspacing="0" cellpadding="0">	   		  		
                                <tr align="center">
                                    <td class = "colField">接收机构</td>	
                                    <td class = "colField">资金来源类别</td>
                                    <td class = "colField">资金总数</td>	
                                    <td class = "colField">下拨日期</td>	
                                    <td class = "colField">操作</td>	
                                </tr>
                                <tr align="center">
                                    <td class = "colValue" height="23">吉林省</td>	
                                    <td class = "colValue" height="23">财政拨付</td>	
                                    <td class = "colValue" height="23">9,100,000.00</td>
                                    <td class = "colValue" height="23">2008-10-09</td>
                                    <td class = "colValue" height="23"><img src=/db/page/images/check2.png></img></td>    
                                </tr>
                                <tr align="center">
                                    <td class = "colValue" height="23">吉林省</td>	
                                    <td class = "colValue" height="23">财政拨付</td>	
                                    <td class = "colValue" height="23">1,100,000.00</td>
                                    <td class = "colValue" height="23">2008-10-08</td>
                                    <td class = "colValue" height="23"><img src=/db/page/images/check2.png></img></td>    
                                </tr>
                                <tr align="center">
                                    <td class = "colValue" height="23">合计:</td>	
                                    <td class = "colValue" height="23"></td>	
                                    <td class = "colValue" height="23">10,100,000.00</td>
                                    <td class = "colValue" height="23"></td>
                                </tr>					    		   				    	    	
                            </table>                            </td>
                        </tr>
                    </table> 
                </td>	 
            </tr>
        </table> 	
    </fieldset>  
</body>
</html>
