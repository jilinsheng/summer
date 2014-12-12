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
        <legend class='legend'>发放救助数据交换设置</legend>			
        <table class="tab" width="100%" border="0" cellspacing="0" cellpadding="0">	   		  		
            <tr align="center">
                <td width="30%" valign="top" class = "myspace">
                	<legend>发放机构列表</legend>			
                    <table class="tab" width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr align="center">
                            <td width="80%" class = "colField">机构名称</td>	
                            <td width="10%" class = "colField">操作</td>
                        </tr>
                        <tr align="center">
                            <td class = "colValue">中国邮政</td>	
                            <td class = "colValue"><img src=/db/page/images/check2.png></img></td>
                        </tr>	
                        <tr align="center">
                            <td class = "colValue">中国银行</td>	
                            <td class = "colValue"><img src=/db/page/images/check2.png></img></td>
                        </tr>
                        <tr align="center">
                            <td class = "colValue">农村合作信用社</td>	
                            <td class = "colValue"><img src=/db/page/images/check2.png></img></td>
                        </tr>	    		   				    	    	
                    </table> 
                </td>	
                <td width="60%" valign="top" class = "myspace">
                	<legend>中国银行－数据交换准则</legend>			
                    <table class="tab" width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr align="center">
                            <td width="30%" class = "colField">属性名</td>	
                            <td width="60%" class = "colField">属性值</td>
                        </tr>
                        <tr align="center">
                            <td style="color: #660099;background-color:#ECECEC;text-align:center;font-size:12px;">文件名称</td>	
                            <td class = "colValue"><input name="" type="radio" value="" checked="checked" />
                            自定义<input name="" type="radio" value="" />自动生成</td>
                        </tr>	
                        <tr align="center">
                            <td style="color: #660099;background-color:#ECECEC;text-align:center;font-size:12px;">文件格式</td>	
                            <td class = "colValue">文件格式选择<select>
                                <option value="0" selected="selected">XML</option>
                                <option value="1">XLS</option>
                                <option value="2">TXT</option>
                                <option value="3">HTML</option>
                                <option value="4">PDF</option>
                                <option value="5">RTF</option>
                                <option value="6">CSV</option>
                            </select></td>
                        </tr>
                        <tr align="center">
                            <td style="color: #660099;background-color:#ECECEC;text-align:center;font-size:12px;">开始标识符</td>	
                            <td class = "colValue">开始标识符选择<select>
                                <option value="0" selected="selected">BEG</option>
                            </select></td>
                        </tr>	
                        <tr align="center">
                            <td style="color: #660099;background-color:#ECECEC;text-align:center;font-size:12px;">标题</td>	
                            <td class = "colValue"><input name="" type="radio" value="" checked="checked" />
                            自定义<input name="" type="radio" value="" />自动生成</td>
                        </tr> 
                        <tr align="center">
                            <td style="color: #660099;background-color:#ECECEC;text-align:center;font-size:12px;">行开始标识符</td>	
                            <td class = "colValue"><label>
                                <input type="checkbox" name="checkbox2" id="checkbox2" />
                            </label>
自动识别 </td>
                        </tr>
                        <tr align="center">
                            <td style="color: #660099;background-color:#ECECEC;text-align:center;font-size:12px;">列名称</td>	
                            <td class = "colValue">列名称<select>
                                <option value="0" selected="selected">END</option>
                            </select></td>
                        </tr>
                        <tr align="center">
                            <td style="color: #660099;background-color:#ECECEC;text-align:center;font-size:12px;">列值</td>	
                            <td class = "colValue"><input name="" type="radio" value="" />
                            自定义<input name="" type="radio" value=""  checked="checked" />自动数据库读取</td>
                        </tr>
                        <tr align="center">
                            <td style="color: #660099;background-color:#ECECEC;text-align:center;font-size:12px;">列分隔符</td>	
                            <td class = "colValue">分隔符选项<select>
                                <option value="0" selected="selected">逗号</option>
                            </select></td>
                        </tr>
                        <tr align="center">
                            <td style="color: #660099;background-color:#ECECEC;text-align:center;font-size:12px;">行结束标识符</td>	
                            <td class = "colValue"><form id="form1" name="form1" method="post" action="">
                                <label>
                                    <input type="checkbox" name="checkbox" id="checkbox" />
                                </label>
                            自动识别
                            </form>                            </td>
                        </tr>	
                        <tr align="center">
                            <td style="color: #660099;background-color:#ECECEC;text-align:center;font-size:12px;">结束标识符</td>	
                            <td class = "colValue">结束标识符选择<select>
                                <option value="0" selected="selected">END</option>
                            </select></td>
                        </tr>	   		   				    	    	
                    </table> 
                </td>
            </tr>		    		   				    	    	
        </table> 	
    </fieldset>  
    <fieldset id = "familyitems" class='list'>
        <legend class='legend'>生成救助金发放交换文件</legend>			
        <table class="tab" width="100%" border="0" cellspacing="0" cellpadding="0">	   		  		
            <tr align="center">
                <td width="30%" style="color: #660099;background-color:#ECECEC;text-align:center;font-size:12px;">救助金发放单位构名称</td>
                <td width="60%" class = "myspace"><select>
                                <option value="0" selected="selected">中国银行</option>
                            </select>	
                </td>
            </tr>
            <tr align="center">
                <td width="30%" style="color: #660099;background-color:#ECECEC;text-align:center;font-size:12px;">数据加密</td>
                <td width="60%" class = "myspace"><input name="" type="radio" value="" />
                            无加密<input name="" type="radio" value=""  checked="checked" />全文加密<input name="" type="radio" value="" />
                            金额加密
                </td>
            </tr>
            <tr align="center">
                <td width="30%" style="color: #660099;background-color:#ECECEC;text-align:center;font-size:12px;">导出文件目录</td>
                <td width="60%" class = "myspace"><input style="width:80% " name="" type="text" /><input name="" type="button" value="选择" />
                </td>
            </tr>
            <tr align="center">
                <td width="30%" style="color: #660099;background-color:#ECECEC;text-align:center;font-size:12px;">导出文件管理</td>
                <td width="60%" class = "myspace"><input name="" type="radio" value="" />
                            追加<input name="" type="radio" value=""  checked="checked" />覆盖<input name="" type="button" value="导出文件" />
                </td>
            </tr>			    		   				    	    	
        </table> 	
    </fieldset>  
    <fieldset id = "familyitems" class='list'>
        <legend class='legend'>救助发放交换文件解密</legend>			
        <table class="tab" width="100%" border="0" cellspacing="0" cellpadding="0">	   		  		
            <tr align="center">
                <td width="30%" style="color: #660099;background-color:#ECECEC;text-align:center;font-size:12px;">救助发放单位构名称</td>
                <td width="60%" class = "myspace"><select>
                                <option value="0" selected="selected">中国银行</option>
                            </select>	
                </td>
            </tr>
            <tr align="center">
                <td width="30%" style="color: #660099;background-color:#ECECEC;text-align:center;font-size:12px;">数据解密准则</td>
                <td width="60%" class = "myspace"><input style="width:80% " name="" type="text" /><input name="" type="button" value="导入" />
                </td>
            </tr>
            <tr align="center">
                <td width="30%" style="color: #660099;background-color:#ECECEC;text-align:center;font-size:12px;">导入文件解密</td>
                <td width="60%" class = "myspace"><input style="width:80% " name="" type="text" /><input name="" type="button" value="选择" />
                </td>
            </tr>
            <tr align="center">
                <td width="30%" style="color: #660099;background-color:#ECECEC;text-align:center;font-size:12px;">导出文件管理</td>
                <td width="60%" class = "myspace"><input name="" type="radio" value="" />
                            追加<input name="" type="radio" value=""  checked="checked" />覆盖<input name="" type="button" value="生成解密文件" />
                </td>
            </tr>			    		   				    	    	
        </table> 	
    </fieldset>  
</body>
</html>
