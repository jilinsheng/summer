var request = null;
var currentNode = null;
var states = new Array();

//初始化XMLHttpRequest对象
function ready(){
  //根据window.XMLHttpRequest对象是否存在使用不同的创建方式
    if (window.XMLHttpRequest) {
       request = new XMLHttpRequest();                  //FireFox、Opera等浏览器支持的创建方式
    } else {
       request = new ActiveXObject("Microsoft.XMLHTTP");//IE浏览器支持的创建方式
    }
  request.onreadystatechange=loadNode; 
  
}
//回调函数，等到数据放在子节点内
function loadNode(){
  if(request.readyState==4){  	 
     if (request.status == 200||request.status == 0) {
	 	
	   var newContent = request.responseText;
	   //删除提示
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
  //spid父节点div
  //surl连接servlet
  //sid子节点div
  //saction父或子节点参数
  //smode调用模块参数[只查询]
  var smode = "query";
  
  ready();  
  var sel = document.getElementById(spid);
  currentNode = sel;
  action =saction;  
  open(surl,sid,saction,smode);  	
}
function doclick(surl,sid,saction){
  	//surl连接servlet
  	//sid子节点div
  	//saction父或子节点参数
  	//smode调用模块参数[只查询]
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
  //spid父节点div
  //surl连接servlet
  //sid子节点div
  //saction父或子节点参数
  //smode调用模块参数[可控制]
  var smode = "console";
  ready();  
  var sel = document.getElementById(spid);
  currentNode = sel;
  action =saction;  	
  open(surl,sid,saction,smode);
}
function doclickconsole(surl,sid,saction){
  	//surl连接servlet
  	//sid子节点div
  	//saction父或子节点参数
  	//smode调用模块参数[可控制]
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
   
   //现在装载数据提示
   var dataHit=document.createElement("div");
   dataHit.setAttribute("id","dataHit");
   dataHit.innerHTML="正在装载数据...";
   if(currentNode!=null){
 	  currentNode.appendChild(dataHit);
   }
   
   var img = document.getElementById("img"+sid);
   if(img!=null) 
   img.setAttribute('src',"/db/page/images/nolines_minus.gif");
   //与服务器端通讯
   ready();//IE比较奇怪，每次都要新建XMLHttpRequest对象   
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