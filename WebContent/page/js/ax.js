	  var req;
      var id;
  function retrieveURL(url,num) {
    req=false;
    id=num;
    //Do the Ajax call
    if (window.XMLHttpRequest) { // Non-IE browsers
      req = new XMLHttpRequest();
      req.onreadystatechange = processStateChange;
      try {
      	req.open("GET", url, true); //was get
      } catch (e) {
        alert("Problem Communicating with Server\n"+e);
      }
      req.send(null);
    } else if (window.ActiveXObject) { // IE
      
      req = new ActiveXObject("Microsoft.XMLHTTP");
      if (req) {
        req.onreadystatechange = processStateChange;
        req.open("GET", url, true);
        req.send();
      }
    }
  }
    function processStateChange() {
      if (req.readyState==1){
       document.getElementById("c"+id).innerHTML="正在读取数据......";
      }
  	  if (req.readyState == 4) { // Complete
      if (req.status == 200) { // OK response
     	 viewinfo(req,id);    
      } else {
        alert("Problem with server response:\n " + req.statusText);
      }
    }
  }