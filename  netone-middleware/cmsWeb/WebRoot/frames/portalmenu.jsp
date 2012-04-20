<%@ page language="java" pageEncoding="gbk"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title></title>
	    <link rel="stylesheet" type="text/css" href="include/ext/resources/css/ext-all.css"/>
	    <script type="text/javascript" src="include/ext/adapter/ext/ext-base.js"></script>
	    <script type="text/javascript" src="include/ext/ext-all.js"></script>
	    
	    
</head>

<body>
    <script type="text/javascript"> 
   	function link(values){
		document.frames["proletright"].location="frames.do?task="+values ;
		//document.location.href="frames.do?task="+values;
	}	
Ext.onReady(function(){
	var tb = new Ext.Toolbar({  
        width: '100%',  
        renderTo: 'Tbar'  
    });
    var menu = new Array();
    var menuone = new Array();
    //var menu=new Ext.menu.Menu();
    tb.render('Toolbar');
    //第一级目录循法
    var i = -1;
    var j = -1;
    <c:forEach items="${childrenlist}" var="getCol">
    i++;
    menu[i]=new Ext.menu.Menu();
    text ="${getCol.name}";
       tb.add('-',{   
       text:text,
       menu:menu[i] 
     });
     //第二级目录循法
      <c:forEach items="${map}" var="map">
	    <c:if test="${map.key == getCol.naturalname}">
	     j++;
	      <c:forEach items="${map.value}" var="list">
	        menuone[j]=new Ext.menu.Menu();
		    var text1 = "${list.name}";
		    var text2 = "${list.extendattribute}";
		    	if(text2=="final"){
			    	menu[i].add({text:text1
			    	});
			    }else{
			    	menu[i].add({text:text1,
			    	menu:menuone[j]
			    	});
			    }
		    //第三级级目录循法
		    <c:forEach items="${Mapone}" var="Mapone">
		      <c:if test="${Mapone.key == list.naturalname}">
	            <c:forEach items="${Mapone.value}" var="listone">
	             var text2 = "${listone.name}";
				 menuone[j].add({text:text2,
				           handler:function(){
				           link("${listone.naturalname}");
				       	}  
			    });
	            </c:forEach>
	          </c:if>
		    </c:forEach>
		  </c:forEach>
	    </c:if>
      </c:forEach>
    </c:forEach> 
    tb.doLayout();
});              
    </script>
    <div id ="Tbar">
    </div>
    <iframe id="proletright" src="${fn:replace(initurl,'$@','&')}" scrolling="no" resize="no" marginheight="0"
		    height="98%" width="100%"></iframe>
	
    </body>
</html>
