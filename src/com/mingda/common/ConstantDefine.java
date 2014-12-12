package com.mingda.common;

public class ConstantDefine {

	/** 查询条件选项定制默认设置* */
	/** 数据库描述* */
	public final static String ADMIN_ID = "-1";// 管理员ID
	/** 选择下拉框* */
	public final static String NOTSELECT_ID = "-1";// 选择下拉框选择无
	public final static String NOTSELECT_NAME = "不选";// 选择下拉框选择不选

	/** 业务分类定义* */
	/** 页面操作描述* */
	public final static String POLICYOBJTY = "全部";// 业务对象分类

	public final static String POLICYMENUCODE = "-2";// 救助业务
	public final static String POLICYOBJTYCODE = "-1";// 业务对象分类

	public final static String POLICYOBJTY_0 = "城市业务";// 业务对象分类
	public final static String POLICYOBJTY_1 = "农村业务";// 业务对象分类
	public final static String POLICYOBJTY_2 = "全部业务";// 业务对象分类

	public final static String POLICYOBJTYCODE_0 = "0";// 业务对象分类
	public final static String POLICYOBJTYCODE_1 = "1";// 业务对象分类
	public final static String POLICYOBJTYCODE_2 = "2";// 业务对象分类

	public final static String POLICYMENU_0 = "基本救助";// 救助业务
	public final static String POLICYMENU_1 = "专项救助";// 救助业务
	public final static String POLICYMENU_2 = "临时救助";// 救助业务
	public final static String POLICYMENU_3 = "其它救助";// 救助业务

	public final static String POLICYMENUCODE_0 = "3";// 救助业务
	public final static String POLICYMENUCODE_1 = "4";// 救助业务
	public final static String POLICYMENUCODE_2 = "5";// 救助业务
	public final static String POLICYMENUCODE_3 = "6";// 救助业务

	/** 逻辑查询条件解析模式* */
	/** 页面操作描述* */
	public final static String REPLACESQL_EXE = "0";// 解析逻辑查询条件[执行]SQL语句
	public final static String REPLACESQL_RES = "1";// 解析逻辑查询条件[返回]SQL语句

	/** 查询逻辑条件转换字符* */
	/** 页面操作描述* */
	/** 数据库描述* */
	public final static String REPLACEEXP_CHR = "☆";// 解析逻辑查询条件转换符号标识

	/** 为和字典值分开,做相应的定义,字典值为大于0的值* */
	/** 页面操作描述* */
	public final static String FIELDTYPE_EXP = "-5";// 运算符字段相关标识
	public final static String FIELDTYPE_DEPT = "-4";// 机构字段相关标识
	public final static String FIELDTYPE_DATE = "-3";// 日期字段相关标识
	public final static String FIELDTYPE_NUM = "-2";// 数值字段相关标识
	public final static String FIELDTYPE_INT = "-1";// 整型字段相关标识
	public final static String FIELDTYPE_CHR = "0";// 字符字段相关标识

	/** 业务查询条件设置* */
	/** 页面操作描述* */
	public final static String POLICYQUERY_11 = "不选";// 业务选择查询标识
	public final static String POLICYQUERY_10 = "全部";// 业务选择查询标识
	public final static String POLICYQUERY_T = "已终审";// 业务省厅选择查询标识
	public final static String POLICYQUERY_5 = "社区(村)";// 业务社区选择查询标识
	public final static String POLICYQUERY_4 = "街道(乡镇)";// 业务街道选择查询标识
	public final static String POLICYQUERY_3 = "区(县)";// 业务区县选择查询标识
	public final static String POLICYQUERY_2 = "市(州)";// 业务市(州)选择查询标识
	public final static String POLICYQUERY_1 = "省厅";// 业务省厅选择查询标识
	/** 函数操作描述* */
	public final static String POLICYQUERYCODE_11 = "-1";// 业务选择查询标识
	public final static String POLICYQUERYCODE_10 = "0";// 业务选择查询标识
	public final static String POLICYQUERYCODE_T = "T";// 业务所属选择查询标识
	public final static String POLICYQUERYCODE_5 = "5";// 业务社区选择查询标识和机构级别相同
	public final static String POLICYQUERYCODE_4 = "4";// 业务街道选择查询标识和机构级别相同
	public final static String POLICYQUERYCODE_3 = "3";// 业务区县选择查询标识和机构级别相同
	public final static String POLICYQUERYCODE_2 = "2";// 业务市(州)选择查询标识和机构级别相同
	public final static String POLICYQUERYCODE_1 = "1";// 业务省厅选择查询标识和机构级别相同
	
	/** 页面操作描述* */
	public final static String POLICYCHECKMONEY_NOTALL = "不选";// 业务核算标准相关标识
	public final static String POLICYCHECKMONEY_ALL = "全部";// 业务核算标准相关标识
	public final static String POLICYCHECKMONEY_NEW = "新增";// 业务核算标准相关标识
	public final static String POLICYCHECKMONEY_OLE = "顺延";// 业务核算标准相关标识
	public final static String POLICYCHECKMONEY_TOP = "调高";// 业务核算标准相关标识
	public final static String POLICYCHECKMONEY_BOM = "降低";// 业务核算标准相关标识
	public final static String POLICYCHECKMONEY_REM = "到期待续";// 业务核算标准相关标识
	public final static String POLICYCHECKMONEY_STOP = "上期暂停";// 业务核算标准相关标识
	public final static String POLICYCHECKMONEY_NOT = "不符合条件";// 业务核算标准相关标识
	/** 数据库代码* */
	public final static String POLICYCHECKMONEYCODE_NOTALL = "-1";// 业务核算标准相关标识
	public final static String POLICYCHECKMONEYCODE_ALL = "0";// 业务核算标准相关标识
	public final static String POLICYCHECKMONEYCODE_NEW = "1";// 业务核算标准相关标识
	public final static String POLICYCHECKMONEYCODE_OLE = "2";// 业务核算标准相关标识
	public final static String POLICYCHECKMONEYCODE_TOP = "3";// 业务核算标准相关标识
	public final static String POLICYCHECKMONEYCODE_BOM = "4";// 业务核算标准相关标识
	public final static String POLICYCHECKMONEYCODE_REM = "5";// 业务核算标准相关标识
	public final static String POLICYCHECKMONEYCODE_STOP = "6";// 业务核算标准相关标识
	public final static String POLICYCHECKMONEYCODE_NOT = "7";// 业务核算标准相关标识
	/** 页面操作描述* */
	public final static String POLICYCHECK_NOTALL = "不选";// 本级审批相关标识
	public final static String POLICYCHECK_ALL = "全部";// 本级审批相关标识
	public final static String POLICYCHECK_NEW = "未评议";// 本级审批相关标识
	public final static String POLICYCHECK_OK = "同意救助";// 本级审批相关标识	
	public final static String POLICYCHECK_RENEW = "重新审批";// 本级审批相关标识
	public final static String POLICYCHECK_MOVE = "渐退";// 本级审批相关标识
	public final static String POLICYCHECK_OLE = "延续";// 本级审批相关标识
	public final static String POLICYCHECK_STOP = "暂停";// 本级审批相关标识
	public final static String POLICYCHECK_REM = "恢复";// 本级审批相关标识
	public final static String POLICYCHECK_NOTOK = "取消救助";// 本级审批相关标识
	public final static String POLICYCHECK_NOT = "不同意救助";// 本级审批相关标识

	/** 数据库代码* */
	public final static String POLICYCHECKCODE_NOTALL = "-1";// 本级审批相关标识
	public final static String POLICYCHECKCODE_ALL = "0";// 本级审批相关标识
	public final static String POLICYCHECKCODE_NEW = "1";// 本级审批相关标识
	public final static String POLICYCHECKCODE_OK = "2";// 本级审批相关标识	
	public final static String POLICYCHECKCODE_RENEW = "3";// 本级审批相关标识
	public final static String POLICYCHECKCODE_MOVE = "4";// 本级审批相关标识
	public final static String POLICYCHECKCODE_OLE = "5";// 本级审批相关标识
	public final static String POLICYCHECKCODE_STOP = "6";// 本级审批相关标识
	public final static String POLICYCHECKCODE_REM = "7";// 本级审批相关标识
	public final static String POLICYCHECKCODE_NOTOK = "8";// 本级审批相关标识
	public final static String POLICYCHECKCODE_NOT = "9";// 本级审批相关标识
	
	/** 页面操作描述* */
	public final static String POLICYFLAG_NOTALL = "不选";// 业务所属相关标识
	public final static String POLICYFLAG_ALL = "全部";// 业务所属相关标识
	public final static String POLICYFLAG_NEW = "普通户";// 业务所属相关标识
	public final static String POLICYFLAG_IS = "在保户";// 业务所属相关标识
	public final static String POLICYFLAG_NOT = "停保户";// 业务所属相关标识
	public final static String POLICYFLAG_STOP = "暂停户";// 业务所属相关标识

	/** 数据库代码* */
	public final static String POLICYFLAGCODE_NOTALL = "-1";// 业务所属相关标识
	public final static String POLICYFLAGCODE_ALL = "0";// 业务所属相关标识
	public final static String POLICYFLAGCODE_NEW = "1";// 业务所属相关标识
	public final static String POLICYFLAGCODE_IS = "2";// 业务所属相关标识
	public final static String POLICYFLAGCODE_NOT = "3";// 业务所属相关标识
	public final static String POLICYFLAGCODE_STOP = "4";// 业务所属相关标识
	/** 上传路径 * */
	/*public final static String UPLOADPATH = "z:/usr/upload/";*/
	public final static String UPLOADPATH = "z:\\nongcun\\usr\\upload\\";
	public final static String UPLOADPATHX = "/usr/upload/";
	/** 分类状态常量* */
	/** 数据库代码* */
	/** 字典代码定义* */
	public final static String CLASSSTATUS_ID = "210";
	/** 数据库代码* */
	public final static String CLASSSTATUS_YES = "280";
	public final static String CLASSSTATUS_NO = "281";
	public final static String CLASSSTATUS_COMMIT = "282";
	/** 分类字典值* */
	public final static String CLASSSTATUS_YESCHN = "是";
	public final static String CLASSSTATUS_NOCHN = "否";
	public final static String CLASSSTATUS_COMMITCHN = "未核实";
	/** 与户主关系* */
	public final static String RELMASTER = "112";
	/** 身份证编号* */
	public final static String CARDID = "390";
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	/** 页面操作描述* */
	public final static String POLICYNEWREQUEST_NEW = "新申请";// 业务申请受理相关标识
	public final static String POLICYNEWREQUEST_REM = "续保";// 业务申请受理相关标识
	public final static String POLICYNEWREQUEST_AOUT = "自动筛选";// 业务申请受理相关标识
	public final static String POLICYNEWREQUEST_IN = "迁入";// 业务申请受理相关标识

	/** 数据库代码* */
	public final static String POLICYNEWREQUESTCODE_NEW = "0";// 业务申请受理相关标识
	public final static String POLICYNEWREQUESTCODE_REM = "1";// 业务申请受理相关标识
	public final static String POLICYNEWREQUESTCODE_AOUT = "2";// 业务申请受理相关标识
	public final static String POLICYNEWREQUESTCODE_IN = "3";// 业务申请受理相关标识

	/** 页面操作描述* */
	public final static String POLICYNEWCHECK_NOTALL = "不选";// 业务核算标准相关标识
	public final static String POLICYNEWCHECK_ALL = "全部";// 业务核算标准相关标识
	public final static String POLICYNEWCHECK_NEW = "新增";// 业务核算标准相关标识
	public final static String POLICYNEWCHECK_OLE = "顺延";// 业务核算标准相关标识
	public final static String POLICYNEWCHECK_TOP = "调高";// 业务核算标准相关标识
	public final static String POLICYNEWCHECK_BOM = "降低";// 业务核算标准相关标识
	public final static String POLICYNEWCHECK_REM = "到期待续";// 业务核算标准相关标识
	public final static String POLICYNEWCHECK_STOP = "上期暂停";// 业务核算标准相关标识
	/** 数据库代码* */
	public final static String POLICYNEWCHECKCODE_NOTALL = "-1";// 业务核算标准相关标识
	public final static String POLICYNEWCHECKCODE_ALL = "0";// 业务核算标准相关标识
	public final static String POLICYNEWCHECKCODE_NEW = "1";// 业务核算标准相关标识
	public final static String POLICYNEWCHECKCODE_OLE = "2";// 业务核算标准相关标识
	public final static String POLICYNEWCHECKCODE_TOP = "3";// 业务核算标准相关标识
	public final static String POLICYNEWCHECKCODE_BOM = "4";// 业务核算标准相关标识
	public final static String POLICYNEWCHECKCODE_REM = "5";// 业务核算标准相关标识
	public final static String POLICYNEWCHECKCODE_STOP = "6";// 业务核算标准相关标识
	
	/** 页面操作描述* */
	public final static String POLICYAOUTCHECK_MATCH = "待定是否符合标准";// 业务自动核算标准相关标识
	public final static String POLICYAOUTCHECK_ISMATCH = "符合标准";// 业务自动核算标准相关标识
	public final static String POLICYAOUTCHECK_NOMATCH = "不符标准";// 业务自动核算标准相关标识
	/** 数据库代码* */
	public final static String POLICYAOUTCHECKCODE_MATCH = "0";// 业务自动核算标准相关标识
	public final static String POLICYAOUTCHECKCODE_ISMATCH = "1";// 业务自动核算标准相关标识
	public final static String POLICYAOUTCHECKCODE_NOMATCH = "2";// 业务自动核算标准相关标识
	/** 页面操作描述* */
	public final static String POLICYAOUTCHECK_NEWCHECK = "新家庭评议";// 业务自动核算标准相关标识
	public final static String POLICYAOUTCHECK_OLECHECK = "在保户复审";// 业务自动核算标准相关标识
	/** 数据库代码* */
	public final static String POLICYAOUTCHECKCODE_NEWCHECK = "101";// 业务自动核算标准相关标识
	public final static String POLICYAOUTCHECKCODE_OLECHECK = "102";// 业务自动核算标准相关标识
	/** 页面操作描述* */
	public final static String POLICYAOUTCHECK_MONEY = "拟发放名单";// 业务自动核算标准相关标识	
	public final static String POLICYAOUTCHECK_NOMONEY = "拟不发名单";// 业务自动核算标准相关标识
	public final static String POLICYAOUTCHECK_REMCHECK = "重新核查";// 业务自动核算标准相关标识

	/** 数据库代码* */
	public final static String POLICYAOUTCHECKCODE_MONEY = "200";// 业务自动核算标准相关标识	
	public final static String POLICYAOUTCHECKCODE_NOMONEY = "201";// 业务自动核算标准相关标识
	public final static String POLICYAOUTCHECKCODE_REMCHECK = "202";// 业务自动核算标准相关标识

	/** 页面操作描述* */
	public final static String POLICYNEWCHECK_NEWNOCHECK = "新增";// 业务核算标准相关标识
	public final static String POLICYNEWCHECK_NEWRECHECK = "重新核查";// 业务核算标准相关标识
	/** 数据库代码* */
	public final static String POLICYNEWCHECKCODE_NEWNOCHECK = "111";// 业务核算标准相关标识
	public final static String POLICYNEWCHECKCODE_NEWRECHECK = "112";// 业务核算标准相关标识
	
	/** 页面操作描述* */	
	public final static String POLICYNEWCHECK_NEWTOP = "调高";// 业务核算标准相关标识
	public final static String POLICYNEWCHECK_NEWBOM = "降低";// 业务核算标准相关标识
	public final static String POLICYNEWCHECK_NEWOLE = "顺延";// 业务核算标准相关标识
	public final static String POLICYNEWCHECK_NEWSTOP = "到期待续";// 业务核算标准相关标识
	public final static String POLICYNEWCHECK_NEWREM = "上期暂停";// 业务核算标准相关标识
	public final static String POLICYNEWCHECK_NEWREMCHECK = "重新核查";// 业务核算标准相关标识
	public final static String POLICYNEWCHECK_NEWNOMATCH = "不达标";// 业务核算标准相关标识
	/** 数据库代码* */	
	public final static String POLICYNEWCHECKCODE_NEWTOP = "121";// 业务核算标准相关标识
	public final static String POLICYNEWCHECKCODE_NEWBOM = "122";// 业务核算标准相关标识
	public final static String POLICYNEWCHECKCODE_NEWOLE = "123";// 业务核算标准相关标识
	public final static String POLICYNEWCHECKCODE_NEWSTOP = "124";// 业务核算标准相关标识
	public final static String POLICYNEWCHECKCODE_NEWREM = "125";// 业务核算标准相关标识
	public final static String POLICYNEWCHECKCODE_NEWREMCHECK = "126";// 业务核算标准相关标识
	public final static String POLICYNEWCHECKCODE_NEWNOMATCH = "127";// 业务核算标准相关标识
	
	/** 页面操作描述* */
	public final static String POLICYNEWRESULT_NEW = "新增";// 业务核算标准相关标识
	public final static String POLICYNEWRESULT_OLE = "顺延";// 业务核算标准相关标识
	public final static String POLICYNEWRESULT_TOP = "调高";// 业务核算标准相关标识
	public final static String POLICYNEWRESULT_BOM = "降低";// 业务核算标准相关标识
	public final static String POLICYNEWRESULT_MOVE = "渐退";// 业务核算标准相关标识
	public final static String POLICYNEWRESULT_REM = "恢复";// 业务核算标准相关标识
	/** 数据库代码* */
	public final static String POLICYNEWRESULTCODE_NEW = "211";// 业务核算标准相关标识
	public final static String POLICYNEWRESULTCODE_OLE = "212";// 业务核算标准相关标识
	public final static String POLICYNEWRESULTCODE_TOP = "213";// 业务核算标准相关标识
	public final static String POLICYNEWRESULTCODE_BOM = "214";// 业务核算标准相关标识
	public final static String POLICYNEWRESULTCODE_MOVE = "215";// 业务核算标准相关标识
	public final static String POLICYNEWRESULTCODE_REM = "216";// 业务核算标准相关标识
	/** 页面操作描述* */	
	public final static String POLICYNEWRESULT_STOP = "暂停";// 业务核算标准相关标识
	public final static String POLICYNEWRESULT_DEL = "终止";// 业务核算标准相关标识
	public final static String POLICYNEWRESULT_NOMATCH = "未达标";// 业务核算标准相关标识
	public final static String POLICYNEWRESULT_MATCHNORES = "未评议";// 业务核算标准相关标识
	/** 数据库代码* */	
	public final static String POLICYNEWRESULTCODE_STOP = "221";// 业务核算标准相关标识
	public final static String POLICYNEWRESULTCODE_DEL = "222";// 业务核算标准相关标识
	public final static String POLICYNEWRESULTCODE_NOMATCH = "223";// 业务核算标准相关标识
	public final static String POLICYNEWRESULTCODE_MATCHNORES = "224";// 业务核算标准相关标识
	/** 页面操作描述* */	
	public final static String POLICYNEWRESULT_REMCHECK = "重新核查";// 业务核算标准相关标识
	/** 数据库代码* */	
	public final static String POLICYNEWRESULTCODE_REMCHECK = "231";// 业务核算标准相关标识

	
	
	
	
}
