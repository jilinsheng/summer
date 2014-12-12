/************************************************************************************************************
����
��������
************************************************************************************************************/
//=================================������ʽBEG================================
//������ʽƥ��
//�����ַ�������
function MatchMatch(str,s)
{
	var r, re; // ����������
	//g ��ȫ�Ĳ��ҳ��ֵ����� pattern�� 
	//i �����Դ�Сд�� 
	//m �����в��ң�
	//re   =   /s/gi;
	re = new RegExp(s,"gi"); // ����������ʽ����
	r = str.match(re); // ���ַ��� s �в���ƥ�䡣						 
	return(r);	
}
//������ʽ����
//���ص�һ��ƥ��λ��
function MatchSearch(str,s)
{
	var r, re; // ����������
	if (str == null){
		return;		
	}
	//g ��ȫ�Ĳ��ҳ��ֵ����� pattern�� 
	//i �����Դ�Сд�� 
	//m �����в��ң�
	//re   =   /s/gi;	
	re = new RegExp(s,"gi"); // ����������ʽ����
	r = str.search(re); // ���ַ��� str �в���ƥ�䡣
	return (r);	//ƥ���λ��
}
//������ʽת��
//�����滻������ַ���
function MatchReplace(str,olestr,newstr)
{
	var r, re; // ����������
	//g ��ȫ�Ĳ��ҳ��ֵ����� pattern�� 
	//i �����Դ�Сд�� 
	//m �����в��ң�
	//re   =   /olestr/gi;
	re   =  new RegExp(olestr,"gi"); // ����������ʽ����      
  	r = str.replace(re,newstr);	
  	return(r);   	
}
//=================================������ʽEND================================
//=================================������BEG=================================
//ѡ��ȷ�����λ�� 
function storeCaret (textEl) {
	if (textEl.createTextRange) 
		textEl.caretPos = document.selection.createRange().duplicate(); 
}
//��괦��������
function insertAtCaret (textEl, text) {
	if (textEl.createTextRange && textEl.caretPos) {
		var caretPos = textEl.caretPos;
		caretPos.text =caretPos.text.charAt(caretPos.text.length - 1) == ' ' ?text + ' ' : text; 
	} 
	else 
		textEl.value = text;
} 
//ɾ��ѡ������
function deleteAtCaret (textEl) {
	if (textEl.createTextRange && textEl.caretPos) {
		var caretPos = textEl.caretPos;
		document.selection.clear();
	} 
}
//=================================������END==================================	
//==================================�ַ�ȥ����β�ո�BEG=================================

//1���������ڶ�sString�ַ�������ǰ�ո�س�    
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
		//��ʾsString�е������ַ����ǿո�,�򷵻ؿմ�  
		return "";
	}          
	else {   
		return sString.substring(iStart)   ;
	}   
} 
//2   ���������ڶ�sString�ַ������к�ո�س�
function JHshRTrim(sString){     
	var  sStr,i,sResult = "",sTemp = "";   
	  
    if (sString.length  == 0) { 
    	return   "";
    }   //   ����sString�ǿմ�   
	sStr =  sString.split(""); 
	//���ַ������е���  
	for (i = sStr.length - 1;i>=0;i--)   
	{     
		sResult = sResult + sStr[i];     
	} 
	//�����ַ���ǰ�ո�س�   
	sTemp = JHshLTrim(sResult);    
	if (sTemp == "") {
		return  "";
	}     
	sStr  =  sTemp.split("");   
	sResult  = ""; 
	//�����������ַ����ٽ��е���  
	for (i = sStr.length - 1;i >= 0;i--)   
	{   
		sResult = sResult + sStr[i];   
	}   
	return  sResult;   
}  
//3���������ڶ�sString�ַ�������ǰ��ո�س�
function JHshTrim(sString)   
{   
	if (sString.length  == 0) { 
    	return  "";
    } 
	var strTmp; 			
	strTmp = JHshRTrim(JHshLTrim(sString));
	return strTmp;   
}
//=================================�ַ�ȥ����β�ո�END===========================