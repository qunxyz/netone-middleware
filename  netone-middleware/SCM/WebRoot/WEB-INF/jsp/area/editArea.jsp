<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<%@ include file="../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<jsp:include page="../common/metaExt.jsp"></jsp:include>
		<jsp:include page="../common/metaJQuery-min.jsp"></jsp:include>
		<script language="javascript" type="text/javascript" src="<%=path%>/script/jquery-plugin/easyui/easyloader.js" ></script>
		<title>::编辑窗口::</title>
	</head>
	<body>
		<div id="panel" style='width: 100%; height: 100%'>
			<div id="div1" style='height: 400px'>
				<form id="areaform" action="" method="post">
					<input type="hidden" id="areaId" name="areaId">
					<table class="main">
						<tr>
							<td width="16%" nowrap="nowrap" class="label">
								行政区划编号:
							</td>
							<td>
								<input type="text" id="areaCode" name="areaCode"
									style="width: 160px" />
							</td>
							<td nowrap="nowrap" class="label">
								&nbsp;&nbsp;行政区划名称:
							</td>
							<td>
								<input type="text" id="areaName" name="areaName"
									style="width: 160px" />
							</td>
						</tr>
						<tr>
							<td class="label" width="80px">
								上级行政区划:
								<input type="hidden" name="parentAreaId"
									id="parentAreaId" />
							</td>
							<td colspan="3">
								<div id="comboBoxTree"></div>
							</td>
						</tr>
						<!-- 
						<tr>
							<td nowrap="nowrap" class="label" align="left">
								行政区划类型:
							</td>
							<td nowrap="nowrap" colspan="3" align="left">
								<input type="radio" id="bussType" name="bussType" value="-1"
									title="区域(不列入分析依据)">
							</td>
						</tr>
						 -->
						<input type="hidden" id="batchCount" name="batchCount"  value="1"/>
						<!--  
						<tr>
							<td nowrap="nowrap" class="label" align="left">
							批量新建次数:
							</td>
							<td nowrap="nowrap" colspan="3" align="left">
								<select id="batchCount" name="batchCount">
									<option value="1" selected="selected">1</option>
									<option value="2" >2</option>
									<option value="3" >3</option>
									<option value="4" >4</option>
									<option value="5" >5</option>
									<option value="6" >6</option>
									<option value="7" >7</option>
									<option value="8" >8</option>
									<option value="9" >9</option>
									<option value="10" >10</option>
								</select>
							</td>
						</tr>	
						-->		
					</table>
				</form>
			</div>
		</div>
	</body>
</html>
<script type="text/javascript">
	var nodeid='0';
	var nodename='行政区划';
	var parentnodeid='0';
   //创建工具栏组件
   var toolbar = new Ext.Toolbar([
 		 {text : '保存 ',iconCls:'saveIcon',handler:onSaveButton},
		 {text : '退出 ',iconCls:'exitIcon',handler:function(){if(parent){parent._jwinClose(); }}}
	]);
	
	var areaTree={//加载产品分类树
	   url: '<c:url value="/department/area.do?method=onFindAreaTree" />',
	   
       init: function(){
	     this.comboBoxTree = new Ext.ux.ComboBoxTree({//上级分类选项
					renderTo : 'comboBoxTree',
					width : 430,
					height : 120,
					tree : {
						xtype:'treepanel',
						id:'comboBoxTree',
					    loader: new Ext.tree.TreeLoader({dataUrl: this.url}),
			       	 	root : new Ext.tree.AsyncTreeNode({id:nodeid,text:nodename}),
			       	 	listeners : {
							click : function(node,e){
							   if(node.attributes.id != nodeid){
							       Ext.get('parentAreaId').dom.value = node.attributes.id;	  
							   }else{
							       Ext.get('parentAreaId').dom.value = nodeid;
								   areaTree.comboBoxTree.setValue({id: nodeid, text: nodename});
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
	areaTree.init();
	var parentAreaId = '';
	var parentAreaName = '';
	/*var id = '${param.areaId}';
	if (id==''){//新建
		if (window.dialogArguments){
			parentAreaId=window.dialogArguments[0];
			parentAreaName=window.dialogArguments[1];
			Ext.get('parentAreaId').dom.value=parentAreaId;
		}
	}*/
	if(parent){
		parentAreaId = parent.Ext.get('areaId').getValue();
		parentAreaName = parent.Ext.get('areaName').getValue();
		if(parentAreaId!=""){
			Ext.get('parentAreaId').dom.value=parentAreaId;
		}else{
			Ext.get('parentAreaId').dom.value=nodeid;
		}
	}
	var obj = {id:parentAreaId,text:parentAreaName};
	areaTree.setValue(obj);
 });
  function validateForm(){ //弹出出错提示  验证
	 		var str = '保存行政区划失败!提示如下:<BR>';
			var i=1;
			var blank = '';
			
			if(Ext.get('areaCode').dom.value == ''){
			   str+= blank+ i+ '、此行政区划编号为空。<BR>';
			   i++;
			}
			if(Ext.get('areaName').dom.value == ''){
			   str+= blank+ i+ '、此行政区划名称为空。';
			   i++;
			}
			if(i>1){
			   Ext.MessageBox.alert('提示',str);
			   return false;
			}
			return true;
   }
   function onSaveButton(){//保存
	   	if(validateForm()){
	   		var msgTip = Ext.MessageBox.show({
							title:'系统提示',
							width : 200,
							msg:'正在保存信息请稍后......'
		  	});
		     Ext.Ajax.request({
					url :"<c:url value='/department/area.do?method=onSaveOrUpdate' />",//请求的服务器地址
					form : 'areaform',//指定要提交的表单id
					method : 'POST',
					success : function(response,options){
						msgTip.hide();
						var result = Ext.util.JSON.decode(response.responseText);
						if(result.error==null){
							var pid = Ext.get('parentAreaId').getValue();
							parent._reloadTree(pid);
							parent.refreshComboBoxTree(pid);
							Ext.ux.Toast.msg("", result.tip);
							Ext.get('areaCode').dom.value = '';
							Ext.get('areaName').dom.value = '';
						}else{
							Ext.MessageBox.alert('提示',result.tip);
						}
					},
					failure : function(response,options){
						checkAjaxStatus(response);
						var result = Ext.util.JSON.decode(response.responseText);
						Ext.MessageBox.alert('提示',result.tip);
					}
		　   });
		}
    }
    
	function refreshComboBoxTree(){
		Ext.getCmp('comboBoxTree').root.reload();
	}
	
	$(function(){
		easyloader.base = '<%=path%>/script/jquery-plugin/easyui/';    // set the easyui base directory
		easyloader.locale = 'zh_CN';
		easyloader.load('validatebox', function(){// load the specified module
		    $("#areaCode").validatebox({required:true});
			$("#areaName").validatebox({required:true});
		});
	});
</script>
