<%@taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@page contentType="text/html; charset=GBK"%>

<%
String path = request.getContextPath();
%>

<html:html>
<head>
	<title>��֯�ṹ</title>
	<link href="css/style.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="js/xtree2.js"></script>
	<script type="text/javascript" src="js/xmlextras.js"></script>
	<script type="text/javascript" src="js/xloadtree2.js"></script>
	<script type="text/javascript" src="js/prototype.js"></script>
	<script type="text/javascript">
	
	function opeReady(typeinfo,tipinfo){
	    if(document.all.currdir.value=="")
        {
          alert("����ѡ��һ�����ţ�");
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
	       alert("��û��Ȩ��");
	       
	     }else{
	       alert(restrInfo[1]);
	     }
	     return;
        //ɾ��
        }
      	selnode.remove();
      }else if(optrtype=='add'){
      	if("false"==restrInfo[0]){
	     alert("��û��Ȩ��");
	     return;
	  	}
      	parent.document.frames["filetree"].addnode.add(restrInfo[2],restrInfo[1]);
      	
      }else if(optrtype=='mdi'){
        if("false"==restrInfo[0]){
	     alert("��û��Ȩ��");
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
			<font color='red'>���Žṹά��</font>

			<input type="hidden" name="pathInfo" value="<%=path%>" />
			<input type="hidden" name="optrtype" value="" />
			<input type="hidden" name="currdirid" />

			<input name="currdir" type="text" size="24" disabled="disabled"
				style="display:none" />
			<input name="modify" type="button" class="but" value="�޸�"
				onclick="opeReady('mdi','�������µĲ�����')" />
			<input name="add" type="button" class="but" value="����"
				onclick="opeReady('add','�������µĲ�����')" />
			<input name="delete" type="button" class="but" value="ɾ��"
				onclick="opeReady('del','���Ҫɾ����?')" />
		</div>
		<br>
		<div id='opecore'
			style='display:none;position:absolute;z-index:4;left:0;top:0;border: 2 solid;left: 0; top: 10; background-color:#E2E2E2'>

			<input name="newdir" type="text" size="16"
				onclick="javascript:this.value='';" />

			<input name="submit1" type="button" class="but" value="ȷ��"
				onclick="opeDone();" />

			<input name="submit1" type="button" class="but" value="ȡ��"
				onclick="document.all.opecore.style.display = 'none'" />

		</div>

	</html:form>
</body>
</html:html>
