<%@taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@page contentType="text/html; charset=GBK"%>

<%
String path = request.getContextPath();
%>

<html:html>
<head>
	<title>组织结构</title>
	<link href="css/style.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="js/xtree2.js"></script>
	<script type="text/javascript" src="js/xmlextras.js"></script>
	<script type="text/javascript" src="js/xloadtree2.js"></script>
	<script type="text/javascript" src="js/prototype.js"></script>
	<script type="text/javascript">
	
	function opeReady(typeinfo,tipinfo){
	    if(document.all.currdir.value=="")
        {
          alert("请先选择一个部门！");
          return;
        }
        document.all.optrtype.value=typeinfo;
        document.all.newdir.value=tipinfo;
        document.all.opecore.style.display = "";
	}
	
	function opeDone(){
	  var optrtype=document.all.optrtype.value;
	  var currid=$("currdirid").value;
	  var newdir=$("newdir").value;
      var paramer="optrtype="+optrtype+"&currdirid="+currid+"&newdir="+newdir;
	  var url = $("pathInfo").value + "/fi/dirOptrAction.do";

	  var parser =new Ajax.Request(url, {method:"get", parameters:"" + paramer,asynchronous:false});
      var restr = parser.transport.responseText;
      var tree = parent.document.frames["filetree"].tree;
      var selnode = tree.getSelected();
      
      document.all.opecore.style.display = 'none'
      
      var restrInfo=restr.split(';');

      if(optrtype=='del'){
       if("false"==restrInfo[0]){
         if(""==restrInfo[1]){
	       alert("您没有权限");
	       
	     }else{
	       alert(restrInfo[1]);
	     }
	     return;
        //删除
        }
      	selnode.remove();
      }else if(optrtype=='add'){
      	if("false"==restrInfo[0]){
	     alert("您没有权限");
	     return;
	  	}
      	parent.document.frames["filetree"].addnode.add(restrInfo[2],restrInfo[1]);
      	
      }else if(optrtype=='mdi'){
        if("false"==restrInfo[0]){
	     alert("您没有权限");
	     return;
	  	}
        selnode.setText(restrInfo[2]);
      }	
	}
      
  </script>
</head>
<body style="margin: 0;">
	<html:form action="/dirOptrAction.do" method="POST">
		<br>
		<div style='overflow:auto;'>
			<font color='red'>部门结构维护</font>

			<input type="hidden" name="pathInfo" value="<%=path%>" />
			<input type="hidden" name="optrtype" value="" />
			<input type="hidden" name="currdirid" />

			<input name="currdir" type="text" size="24" disabled="disabled"
				style="display:none" />
			<input name="modify" type="button" class="but" value="修改"
				onclick="opeReady('mdi','请输入新的部门名')" />
			<input name="add" type="button" class="but" value="新增"
				onclick="opeReady('add','请输入新的部门名')" />
			<input name="delete" type="button" class="but" value="删除"
				onclick="opeReady('del','真的要删除吗?')" />
		</div>
		<br>
		<div id='opecore'
			style='display:none;position:absolute;z-index:4;left:0;top:0;border: 2 solid;left: 0; top: 10; background-color:#E2E2E2'>

			<input name="newdir" type="text" size="16"
				onclick="javascript:this.value='';" />

			<input name="submit1" type="button" class="but" value="确定"
				onclick="opeDone();" />

			<input name="submit1" type="button" class="but" value="取消"
				onclick="document.all.opecore.style.display = 'none'" />

		</div>

	</html:form>
</body>
</html:html>
