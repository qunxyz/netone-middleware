<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.oesee.com/netone" prefix="rs"%>
<%@ taglib uri="http://www.oesee.com/netone/portal" prefix="portal"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	
<%@ include file="../common/taglibs.jsp"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<jsp:include page="../common/metaExt.jsp"></jsp:include>
		<jsp:include page="../common/metaJQuery-min.jsp"/>
        <script language="javascript" type="text/javascript" src="<%=path%>/script/jquery-plugin/easyui/easyloader.js" ></script>
        <script language="javascript" type="text/javascript"
			src="<%=path%>/My97DatePicker/WdatePicker.js" charset="gb2312"></script>
		<title>::编辑窗口::</title>
	</head>
	<body>
		<div id="panel" style='width: 100%; height: 100%'>
			<div id="div1" style='width: 95%; height: 400px'>
				<form id="form1" action="" method="post">
				<input type="hidden" id="userId" name="userId" value="${user['userId']}"/>
					<table class="main">
						<tr>
							<td nowrap="nowrap" class="label">
								用户编码:
							</td>
							<td>
							<div style=" width:155px">
								<input type="text" id="userCode" name="userCode" value="${user['userCode']}"  <c:if test="${!empty user['userId']}">readonly="readonly"</c:if>/>
							</div>
							</td>
							<td nowrap="nowrap" class="label">
								用户名称:
							</td>
							<td>
							<div style=" width:155px">
								<input type="text" id="userName" name="userName" value="${user['userName']}"/>
						   </div>
							</td>
							<td td nowrap="nowrap" class="label">
						                  帐号类型:
						   </td>
						   <td>
						   <div style=" width:105px">
						   <select id="accounttypes" name="accounttypes">
									<option value=""></option>
									<option value="1" <c:if test="${user['accounttypes'] == '1'}">selected="selected"</c:if>>
										可登录账号
									</option>
									<option value="0" <c:if test="${user['accounttypes'] == '0'}">selected="selected"</c:if>>
									         不可登陆帐号
									</option>									
								</select>
								</div>
						   </td>
						   <td nowrap="nowrap" class="label">
						                   职务:
						   </td>
						   <td>
						   <div style=" width:155px">
						       <input type="text" id="zw" name="zw" value="${user['zw']}"/>
						       </div>
						   </td>
						</tr>
						<tr>
						<td style="padding-left:10px;font-weight: bold;">组织机构</td>
						</tr>
						<tr>
						<td nowrap="nowrap" class="label">
								隶属关系:
								<input type="hidden" id="departmentId" name="departmentId" value="${user['departmentId']}"/>
							</td>
							<td colspan="3">
								<div id="comboBoxTree"></div>
							</td>
							<td nowrap="nowrap" class="label">
								类型:
							</td>
							<td>
							<div style=" width:55px">
								<select id="types" name="types">
									<option value=""></option>
									<option value="x" <c:if test="${user['types'] == 'x'}">selected="selected"</c:if>>
										公司
									</option>
									<option value="4" <c:if test="${user['types'] == '4'}">selected="selected"</c:if>>
										客户
									</option>									
								</select>
								</div>
							</td>	
						</tr>
						<tr>
						<td style="padding-left: 10px;font-weight: bold;">离开办公室</td>
						</tr>
						<tr>
						   <td nowrap="nowrap" class="label" >
						               离开时间:
						   </td>
						   <td>
						   <div style=" width:155px">
						      <input type="text" id="leaveTime" name="leaveTime"
													value="${user['leavetime']}"
													onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"/>
													</div>
						   </td>
						   <td nowrap="nowrap" class="label">
						             回来时间:
						   </td>
						   <td>
						   <div style=" width:155px">
						       <input type="text" id="backTime" name="backTime"
													value="${user['backtime']}"
													onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"/>
													</div>
						   </td>
						   	   <td nowrap="nowrap" class="label">
						         代理人:
						   </td>
						   <td colspan="3">
						   <span style=" width:155px">
						   <input type="hidden" name="departmentIdx" id="departmentIdx"/>
											<input type="text" name="departmentNamex"
												id="departmentNamex" <c:if test="${!empty user.dlr}">value="${user.dlr}"</c:if>>
											<input type="button" onclick="_choiceDepartmentx()" value="…"
												class="btn" style="margin-left: -5px" />
												</span>
						   </td>
						</tr>
						<tr>
						<td style="padding-left: 10px;font-weight: bold;">通知接收</td>
						</tr>
						<tr>
						<td nowrap="nowrap" class="label">
								电话号码:
							</td>
							<td>
							<div style=" width:155px">
								<input type="text" id="phone" name="phone" value="${user['phone']}"/>
								</div>
							</td>
							<td nowrap="nowrap" class="label">
								邮箱:
							</td>
							<td>
							<div style=" width:155px">
								<input type="text" id="email" name="email" value="${user['email']}"/>
								</div>
							</td>
						   <td nowrap="nowrap" class="label">
						        通知方式:
						   </td>
						   <td colspan="2">
						     <input type="checkbox" id="mail" name="mail" value="0" <c:if test="${user['notice']=='0'||user['notice']=='3'}">checked="checked" </c:if>>接收邮件
						     <input type="checkbox" id="msg" name="msg" value="1" <c:if test="${user['notice']=='1'||user['notice']=='3'}">checked="checked" </c:if>>接收短信
						   </td>
						</tr>
						<tr>
							<td nowrap="nowrap" class="label">
								性别:
							</td>
							<td>
								<select id="sex" name="sex">
									<option value="0" <c:if test="${user['sex'] == '0'}">selected="selected"</c:if>>
										男
									</option>
									<option value="1" <c:if test="${user['sex'] == '1'}">selected="selected"</c:if>>
										女
									</option>				
								</select>
							</td>
							<td nowrap="nowrap" class="label">
								婚否:
							</td>
							<td>
								<select id="marriage" name="marriage">
									<option value="0" <c:if test="${user['marriage'] == '0'}">selected="selected"</c:if>>
										否
									</option>
									<option value="1" <c:if test="${user['marriage'] == '1'}">selected="selected"</c:if>>
										是
									</option>				
								</select>
							</td>						
						</tr>
						<tr>
						<td nowrap="nowrap" class="label">
								地址:
							</td>
							<td colspan="3">
							<div style=" width:420px">
								<input type="text" style="width: 99%;" id="addressinfo" name="addressinfo" value="${user['addressinfo']}"/>
							</div>
						</td> 
						<td nowrap="nowrap" class="label">
								排序:
							</td>
							<td>
							<input id="orders" name="orders" type="text" />
						</td>	
                        </tr>
						<tr>
							<td nowrap="nowrap" class="label">
								描述:
							</td>
							<td colspan="3">
							<div style=" width:420px;height:10px:">
								<textarea rows="2" cols="50" id="description" name="description">${user['description']}</textarea>
								</div>
							</td>
						</tr>
						<tr id="p" style="display:none;line-height: 20px;">
						<td style="padding-left: 10px;font-weight: bold;">后台系统:</td>
					    <td><input type="button" name="button" value="4A后台配置"  onclick="acc()"></td>
						</tr>																																			
					</table>
				</form>
			</div>
		</div>
	</body>
</html>
<script type="text/javascript">
function acc(){
	var userCode = document.getElementById('userCode').value;
	//alert(userCode);
	window.open('<portal:envget envkey="WEBSER_SECURITY3A"/>/humanModify.do?task=modify&description='+userCode);
}
function _choiceDepartmentx() {
	openWinCenter("人员选择", "../department/user.do?method=onSingleSelectUser", 680, 400);
}
function _choiceUser(ids, values, texts) {
	document.getElementById("departmentIdx").value = ids;
	document.getElementById("departmentNamex").value = texts;
}

	var nodeid='${node}';
	var nodename = '${nodeName}'
	var parentnodeid='${parentNode}';
	$(function(){
		easyloader.base = '<%=path%>/script/jquery-plugin/easyui/';    // set the easyui base directory
		easyloader.locale = 'zh_CN';
		easyloader.load('validatebox', function(){// load the specified module
		    $("#userCode").validatebox({required:true});
			$("#userName").validatebox({required:true});
			$("#types").validatebox({required:true});
		});
	});
   //创建工具栏组件
   var toolbar = new Ext.Toolbar([
 		 {text : '保存 ',iconCls:'saveIcon',handler:onSaveButton},
		 {text : '退出 ',iconCls:'exitIcon',handler:function(){
				 if ('${param.type}'=='1'){
				 	if(opener){window.close(); }
				 } else {
				 	if(parent){parent._jwinClose(); }
				 }
		 	}}
	]);
	
	var deptTree={//加载树
	   url: '<c:url value="/department/department.do?method=onFindDeptTree" />',
	   
       init: function(){
	     this.comboBoxTree = new Ext.ux.ComboBoxTree({//上级分类选项
					renderTo : 'comboBoxTree',
					width : 430,
					height : 120,
					tree : {
						xtype:'treepanel',
					    loader: new Ext.tree.TreeLoader({dataUrl: this.url}),
			       	 	root : new Ext.tree.AsyncTreeNode({id:nodeid,text:'组织机构'}),
			       	 	listeners : {
							click : function(node,e){
							   if(node.attributes.id != nodeid){
							       Ext.get('departmentId').dom.value = node.attributes.id;	  
							   }else{
							       Ext.get('departmentId').dom.value = nodeid;
								   deptTree.comboBoxTree.setValue({id: nodeid, text: nodename});
							   }
							 }
						 }
			       	 
			    	},
			    	//all:所有结点都可选中
					//exceptRoot：除根结点，其它结点都可选(默认)
					//folder:只有目录（非叶子和非根结点）可选
					//leaf：只有叶子结点可选
					selectNodeModel:'folder'
	      });
	  	},
	    clear: function(){
		     var comboBoxTree = document.getElementById('comboBoxTree');
			 comboBoxTree.innerHTML = '';
		},
		
		setValue: function(value){
		     this.comboBoxTree.setValue(value);
		}
	 }
	
 Ext.onReady(function(){
    var panel = new Ext.Panel({
	      tbar:toolbar,
          contentEl :'div1',//加载本地资源	        
          applyTo :'panel',
          border:false
	}); 
	deptTree.init();
	//var username = document.getElementById('userName').value;
	var userCode = '${user['userCode']}'
	//alert(userCode);
	if(!userCode==''){
		document.getElementById('p').style.display='block';
	}
	var departmentId = '${user['departmentId']}';
	var departmentName = '${user['departmentName']}';
	if (departmentId==''){
		var id = opener.document.getElementById('departmentId').value;
		if (id!=''){//新建
			departmentId=id;
			departmentName=opener.document.getElementById('departmentName').value;
			Ext.get('departmentId').dom.value=departmentId;
		}else{
			Ext.get('departmentId').dom.value=nodeid;
		}
	}
	var obj = {id:departmentId,text:departmentName};
	deptTree.setValue(obj);
 });
  function validateForm(){ //弹出出错提示  验证
	 		var str = '保存失败!提示如下:<BR>';
			var i=1;
			var blank = '';
			
			if(Ext.get('userCode').dom.value == ''){
			   str+= blank+ i+ '、此用户编码为空。<BR>';
			   i++;
			}
			if(Ext.get('userName').dom.value == ''){
			   str+= blank+ i+ '、此用户名称为空。<BR>';
			   i++;
			}
			/**
			if(Ext.get('zw').dom.value == ''){
			   str+= blank+ i+ '、此用户职务为空。';
			   i++;
			}
			if(Ext.get('departmentNamex').dom.value == ''){
			   str+= blank+ i+ '、此用户代理人为空。';
			   i++;
			}
			if(Ext.get('leaveTime').dom.value == ''){
			   str+= blank+ i+ '、此用户离开时间为空。';
			   i++;
			}
			if(Ext.get('backTime').dom.value == ''){
			   str+= blank+ i+ '、此用户回来时间为空。';
			   i++;
			}
			**/
			if(Ext.get('accounttypes').dom.value == ''){
			   str+= blank+ i+ '、此用户帐号类型为空。<BR>';
			   i++;
			}
			/**
			if(!Ext.get('mail').dom.checked && !Ext.get('msg').dom.checked){
			   str+= blank+ i+ '、此用户通知方式为空。';
			   i++;
			}
			if(Ext.get('types').dom.value == ''){
			   str+= blank+ i+ '、此类型为空。';
			   i++;
			}
			**/
			if(Ext.get('departmentId').dom.value == '' || Ext.get('departmentId').dom.value == '0'){
			   str+= blank+ i+ '、此隶属关系为空。<BR>';
			   i++;
			}			
			if(i>1){
			   Ext.MessageBox.alert('提示',str);
			   return false;
			}
			return true;
   }
   function onSaveButton(){//保存
	   var notice;
       if(Ext.get('mail').dom.checked&&Ext.get('msg').dom.checked){
    	   notice=3;
       }else if(Ext.get('mail').dom.checked){
		   notice=0;
	   }else if(Ext.get('msg').dom.checked){
    	   notice=1;
       }
	   	if(validateForm()){
	   		var msgTip = Ext.MessageBox.show({
							title:'系统提示',
							width : 200,
							msg:'正在保存信息请稍后......'
		  	});
		     Ext.Ajax.request({
					url :"<c:url value='/department/user.do?method=onSaveOrUpdateUser' />&notice="+notice,//请求的服务器地址
					form : 'form1',//指定要提交的表单id
					method : 'POST',
					success : function(response,options){
						msgTip.hide();
						var result = Ext.util.JSON.decode(response.responseText);
						if(result.error==null){
							Ext.get('userCode').dom.value = '';
							Ext.get('userName').dom.value = '';
							Ext.ux.Toast.msg("", result.tip);
							if ('${param.type}'=='1'){
								if (opener) opener.refresh();
							} else {
								parent.refresh();
							}
							
						}else{
							Ext.MessageBox.alert('提示',result.tip);
						}
					},
					failure : function(response,options){
						msgTip.hide();
						checkAjaxStatus(response);
						var result = Ext.util.JSON.decode(response.responseText);
						Ext.MessageBox.alert('提示',result.tip);
					}
		　   });
		}
    }
    
  	function _reloadTree(){
		Ext.getCmp('comboBoxTree').root.reload();
	}
	
</script>
