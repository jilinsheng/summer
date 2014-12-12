var request = null;
var currentNode = null;
var states = new Array();

//��ʼ��XMLHttpRequest����
function ready(){
  //����window.XMLHttpRequest�����Ƿ����ʹ�ò�ͬ�Ĵ�����ʽ
    if (window.XMLHttpRequest) {
       request = new XMLHttpRequest();                  //FireFox��Opera�������֧�ֵĴ�����ʽ
    } else {
       request = new ActiveXObject("Microsoft.XMLHTTP");//IE�����֧�ֵĴ�����ʽ
    }
  request.onreadystatechange=loadNode; 
  
}
//�ص��������ȵ����ݷ����ӽڵ���
function loadNode(){
  if(request.readyState==4){  	 
     if (request.status == 200||request.status == 0) {
	 	
	   var newContent = request.responseText;
	   //ɾ����ʾ
	   var dataHit = document.getElementById("dataHit");
	   if(dataHit!=null){
	   	  if(currentNode!=null){
	         currentNode.removeChild(dataHit);
	      }
	   }
	   var content=document.createElement("div");
	   content.innerHTML = newContent;
	   if(currentNode!=null){
	       currentNode.appendChild(content);
	   }
	 }
  }
}
function loadrootTree(spid,surl,sid,saction){
  //spid���ڵ�div
  //surl����servlet
  //sid�ӽڵ�div
  //saction�����ӽڵ����
  //smode����ģ�����[ֻ��ѯ]
  var smode = "query";
  
  ready();  
  var sel = document.getElementById(spid);
  currentNode = sel;
  action =saction;  
  open(surl,sid,saction,smode);  	
}
function doclick(surl,sid,saction){
  	//surl����servlet
  	//sid�ӽڵ�div
  	//saction�����ӽڵ����
  	//smode����ģ�����[ֻ��ѯ]
   var smode = "query";
   
   var sel = document.getElementById(sid);
   currentNode = sel;
   
   if(states[sid]=="opened"){
       close(sid);
	   states[sid]="closed";
   }else{
       open(surl,sid,saction,smode);
	   states[sid]="opened";
   }	   
}
function loadrootTreeConsole(spid,surl,sid,saction){
  //spid���ڵ�div
  //surl����servlet
  //sid�ӽڵ�div
  //saction�����ӽڵ����
  //smode����ģ�����[�ɿ���]
  var smode = "console";
  ready();  
  var sel = document.getElementById(spid);
  currentNode = sel;
  action =saction;  	
  open(surl,sid,saction,smode);
}
function doclickconsole(surl,sid,saction){
  	//surl����servlet
  	//sid�ӽڵ�div
  	//saction�����ӽڵ����
  	//smode����ģ�����[�ɿ���]
   var smode = "console";
   var sel = document.getElementById(sid);
   currentNode = sel;
   
   if(states[sid]=="opened"){
       close(sid);
	   states[sid]="closed";
   }else{
       open(surl,sid,saction,smode);
	   states[sid]="opened";
   }	   
}
function open(surl,sid,saction,smode){ 
   
   //����װ��������ʾ
   var dataHit=document.createElement("div");
   dataHit.setAttribute("id","dataHit");
   dataHit.innerHTML="����װ������...";
   if(currentNode!=null){
 	  currentNode.appendChild(dataHit);
   }
   
   var img = document.getElementById("img"+sid);
   if(img!=null) 
   img.setAttribute('src',"/db/page/images/nolines_minus.gif");
   //���������ͨѶ
   ready();//IE�Ƚ���֣�ÿ�ζ�Ҫ�½�XMLHttpRequest����   
   request.open('GET',surl+"?id="+sid+"&url=" + surl+"&action=" + saction+"&mode=" + smode+"&randnum=" + Math.random(),true);
   request.send(null);
}

function close(id){
   if(currentNode!=null){
	   var allnodes=currentNode.getElementsByTagName("div");   
	   for(var i=0;i<allnodes.length;i++){   	  
	      currentNode.removeChild(allnodes.item(i));
	   } 
   }  
   var img = document.getElementById("img"+id);
   img.setAttribute('src',"/db/page/images/nolines_plus.gif");   
}