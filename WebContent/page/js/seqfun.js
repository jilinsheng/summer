/************************************************************************************************************
秀秀
公共函数
************************************************************************************************************/
//=================================正则表达式BEG================================
//正则表达式匹配
//返回字符串数组
function MatchMatch(str,s)
{
	var r, re; // 声明变量。
	//g （全文查找出现的所有 pattern） 
	//i （忽略大小写） 
	//m （多行查找）
	//re   =   /s/gi;
	re = new RegExp(s,"gi"); // 创建正则表达式对象。
	r = str.match(re); // 在字符串 s 中查找匹配。						 
	return(r);	
}
//正则表达式查找
//返回第一个匹配位置
function MatchSearch(str,s)
{
	var r, re; // 声明变量。
	if (str == null){
		return;		
	}
	//g （全文查找出现的所有 pattern） 
	//i （忽略大小写） 
	//m （多行查找）
	//re   =   /s/gi;	
	re = new RegExp(s,"gi"); // 创建正则表达式对象。
	r = str.search(re); // 在字符串 str 中查找匹配。
	return (r);	//匹配的位置
}
//正则表达式转换
//返回替换后的新字符串
function MatchReplace(str,olestr,newstr)
{
	var r, re; // 声明变量。
	//g （全文查找出现的所有 pattern） 
	//i （忽略大小写） 
	//m （多行查找）
	//re   =   /olestr/gi;
	re   =  new RegExp(olestr,"gi"); // 创建正则表达式对象。      
  	r = str.replace(re,newstr);	
  	return(r);   	
}
//=================================正则表达式END================================
//=================================光标控制BEG=================================
//选中确定光标位置 
function storeCaret (textEl) {
	if (textEl.createTextRange) 
		textEl.caretPos = document.selection.createRange().duplicate(); 
}
//光标处插入文字
function insertAtCaret (textEl, text) {
	if (textEl.createTextRange && textEl.caretPos) {
		var caretPos = textEl.caretPos;
		caretPos.text =caretPos.text.charAt(caretPos.text.length - 1) == ' ' ?text + ' ' : text; 
	} 
	else 
		textEl.value = text;
} 
//删除选中文字
function deleteAtCaret (textEl) {
	if (textEl.createTextRange && textEl.caretPos) {
		var caretPos = textEl.caretPos;
		document.selection.clear();
	} 
}
//=================================光标控制END==================================	
//==================================字符去掉首尾空格BEG=================================

//1本函数用于对sString字符串进行前空格截除    
function  JHshLTrim(sString){ 			  
	var   sStr,i,iStart,sResult="";  
	sStr = sString.split("");   
	iStart = -1   ;   
	for (i=0;i<sStr.length;i++){   
		if (sStr[i]!=" "){   
			iStart = i;   
			break;   
		}   
	}   
	if (iStart == -1){  
		//表示sString中的所有字符均是空格,则返回空串  
		return "";
	}          
	else {   
		return sString.substring(iStart)   ;
	}   
} 
//2   本函数用于对sString字符串进行后空格截除
function JHshRTrim(sString){     
	var  sStr,i,sResult = "",sTemp = "";   
	  
    if (sString.length  == 0) { 
    	return   "";
    }   //   参数sString是空串   
	sStr =  sString.split(""); 
	//将字符串进行倒序  
	for (i = sStr.length - 1;i>=0;i--)   
	{     
		sResult = sResult + sStr[i];     
	} 
	//进行字符串前空格截除   
	sTemp = JHshLTrim(sResult);    
	if (sTemp == "") {
		return  "";
	}     
	sStr  =  sTemp.split("");   
	sResult  = ""; 
	//将经处理后的字符串再进行倒序  
	for (i = sStr.length - 1;i >= 0;i--)   
	{   
		sResult = sResult + sStr[i];   
	}   
	return  sResult;   
}  
//3本函数用于对sString字符串进行前后空格截除
function JHshTrim(sString)   
{   
	if (sString.length  == 0) { 
    	return  "";
    } 
	var strTmp; 			
	strTmp = JHshRTrim(JHshLTrim(sString));
	return strTmp;   
}
//=================================字符去掉首尾空格END===========================