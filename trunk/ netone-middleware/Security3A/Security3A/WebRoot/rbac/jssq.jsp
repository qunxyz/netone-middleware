<%@ page language="java" pageEncoding="GBK"%>
<%@page import="oe.security3a.client.rmi.CupmRmi"%>
<%@page import="oe.rmi.client.RmiEntry"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
CupmRmi cupm=(CupmRmi)RmiEntry.iv("cupm");
String permissiontree=cupm.fetchConfig("permissionTreeList");
%>

<script type="text/javascript">
		
			function addpermission(){
				var node = document.frames['tree'].document.all.selectedNode.value ;
				
				if(node==""){
					alert("请先选择一个节点!");
					return false;
				}
				
				var dn = node.split("#")[0];
				
				
				var parser = new Ajax.Request(
						"servlet/FindOuDispname",
						{method:"get",parameters:"ou="+dn,asynchronous:false}
						);
		        var text = parser.transport.responseText;
				
				
				var range = document.all.range.value;
				
				var objx = document.getElementById("action"); //selectid

				var indexx = objx.selectedIndex; 
				
				var action = objx.options[indexx].value.split("#")[0];
				
				var textx = objx.options[indexx].text; // 选中文本
				
				text = '['+textx+']'+text+range ;

				
				var optionvalue = dn+range + "#" + action+"#"+text;
				
				var option = new Option(text,optionvalue);
				var permission = document.all.permission;
				permission.add(option);
				
				setPermissions();
			}
			
			function delpermission(){
				var permission = document.all.permission;
				var index = permission.selectedIndex;
				while(index != -1){
					permission.remove(index);
					index = permission.selectedIndex;
				}
				
				setPermissions();
			}
			
			
			function setPermissions(){
				var permission = document.all.permission;
				var value = "";
				for(var i=0 ; i<permission.length ; i++){
					value += permission.options[i].value + "@" ;
				}
				document.all.allPermission.value = value ;
				//alert(document.all.allPermission.value);
			}
					
	</script>

<input type="hidden" name="allPermission" value="" />
<table width="100%" height="100%" border="0" align="center"
	cellpadding="0" cellspacing="1" id="table_td">
	<tr>
		<td class="td_titt_bg" width="40%">
			资源信息
		</td>
		<td class="td_titt_bg">
			权限列表
		</td>
	</tr>
	<tr>
		<td valign="top" bgcolor="#FFFFFF" width="40%" height="100%">
			<iframe name="tree" width="100%" height="100%" frameborder="0"
				marginheight="0" marginwidth="0" scrolling="auto"
				src="rsinfo/system/SystemSelect.do?func=jssq&naturalname=<%=permissiontree %>">
			</iframe>
		</td>
		<td align="left" valign="top" bgcolor="#FFFFFF">
			<select name="range">
				<option value=".*">
					包含本身及子节点
				</option>
				<option value=".-">
					仅包含子节点
				</option>
				<option value="">
					仅包含本身
				</option>
			</select>
			<select name="action" id='action'>
				<c:forEach items="${levellist}" var="level">
					<option value="${level.extendattribute}#${level.naturalname}">
						${level.name}
					</option>
				</c:forEach>
			</select>
			<input type="button" value=" + " class="butt"
				onclick="addpermission();">
			<input type="button" value=" - " class="butt"
				onclick="delpermission();">
			<br>
			<select name="permission" multiple="multiple" size="18" style="width: 350px">
			</select>
		</td>
	</tr>
</table>

<c:if test="${permissions != null}">
	<script type="text/javascript">
      		var permission = document.all.permission ;
      		<c:forEach items="${permissions}" var="per">
      			{
      				var operationid = ${per.operationid} ;
      				var value = '${per.dninfo}#${per.operationid}#${per.comments}';
                    
      				var option = new Option('${per.comments}', value );
      				permission.add(option);
      			}
      		</c:forEach>
      		setPermissions();
      	</script>
</c:if>

