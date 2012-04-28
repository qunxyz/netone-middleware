<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="common/taglibs.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String cssURL = request.getContextPath() + "/images/appImage";
%>
<!DOCTYPE html PUBLIC "-//W3C//Dtd HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>合同管理</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<jsp:include page="common/metaExt.jsp"></jsp:include>
		<jsp:include page="common/metaJQuery-min.jsp"></jsp:include>
		<jsp:include page="common/metaJQuery-ui-tab.jsp" />
		<script language="javascript" type="text/javascript"
			src="<%=path%>/script/jquery-plugin/easyui/easyloader.js"></script>
		
        <script type="text/javascript">
			//** iframe自动适应页面 **//
			
			//输入你希望根据页面高度自动调整高度的iframe的名称的列表
			//用逗号把每个iframe的ID分隔. 例如: ["myframe1", "myframe2"]，可以只有一个窗体，则不用逗号。
			
			//定义iframe的ID
			var iframeids=["main"]
			
			//如果用户的浏览器不支持iframe是否将iframe隐藏 yes 表示隐藏，no表示不隐藏
			var iframehide="yes"
			
			function dyniframesize() 
			{
			  var dyniframe=new Array()
			  for (i=0; i<iframeids.length; i++)
			  {
			   if (document.getElementById)
			   {
			    //自动调整iframe高度
			    dyniframe[dyniframe.length] = document.getElementById(iframeids[i]);
			    if (dyniframe[i] && !window.opera)
			    {
			     dyniframe[i].style.display="block"
			     if (dyniframe[i].contentDocument && dyniframe[i].contentDocument.body.offsetHeight) //如果用户的浏览器是NetScape
			      dyniframe[i].height = dyniframe[i].contentDocument.body.offsetHeight; 
			     else if (dyniframe[i].Document && dyniframe[i].Document.body.scrollHeight) //如果用户的浏览器是IE
			      dyniframe[i].height = dyniframe[i].Document.body.scrollHeight;
			    }
			   }
			   //根据设定的参数来处理不支持iframe的浏览器的显示问题
			   if ((document.all || document.getElementById) && iframehide=="no")
			   {
			    var tempobj=document.all? document.all[iframeids[i]] : document.getElementById(iframeids[i])
			    tempobj.style.display="block"
			   }
			  }
			}
			
			if (window.addEventListener)
			window.addEventListener("load", dyniframesize, false)
			else if (window.attachEvent)
			window.attachEvent("onload", dyniframesize)
			else
			window.onload=dyniframesize
			
			//-->
	</script>
	</head>
	<style>
td.label_nd_1 {
	FILTER: progid :         DXImageTransform .         Microsoft .    
		  
		 Gradient(GradientType =             0, StartColorStr =            
		#D9ECF9, EndColorStr =         #62BBE8);
	background: -webkit-gradient(linear, left top, left bottom, from(#D9ECF9),
		to(#62BBE8) ); /* for webkit browsers */
	background: -moz-linear-gradient(top, #D9ECF9, #62BBE8);
	/* for firefox 3.6+ */
	color: #000066;
	text-align: left;
	padding-left: 10px;
	padding-right: 10px;
	font-style: normal;
	border: 1px solid #86B4E5;
	line-height: 20px;
}

td.label_file_td {
	color: #000;
	text-align: left;
	padding-left: 10px;
	padding-right: 10px;
	font-style: normal;
	border-left: 1px dashed #ddd;
	border-right: 1px dashed #ddd;
	border-bottom: 1px dashed #ddd;
	line-height: 24px;
	font-style: 14px;
}
</style>
	<body onload="onLoadIframeHight(0);">
		<input type="hidden" id="d_unid" name="d_unid" value="${param.d_unid}" />
		<input type="hidden" id="d_hidden_row_unid" name="d_hidden_row_unid" />
		<input type="hidden" id="d_hidden_row_unidx" name="d_hidden_row_unidx" />
		<div class="bg_file_btn">
			<!-- 
		<input id="file_btn" type="button" value="附件(共有${fileCount}个文件)" onclick="showDiv()" class="btn_file" style="background-image: url('<%=cssURL%>/fujian.gif');" />
		 -->
		</div>
		<div id="addFile_upload" style="display: block;">
			<form id="form2" method="POST" target="_blank"></form>
			<form id="form1" method="POST" enctype="multipart/form-data">
				<table WIDTH="100%" class="main_nd" CELLPADDING="0" CELLSPACING="0">
					<tr
						style="background-image: url('<%=path%>/FDL/Dubang/Image/index_2323.gif'); width: 100%; color: #a52526; padding-top: 5px; font-weight: bold; height: 25px;">
						<td COLSPAN=11 class="label_file">
							合同附加文件:
							<span style="vertical-align: middle;"> <input type="file"
									id="files" name="files" style="width: 480px;"
									onchange="getFileFullName()" contenteditable="false" /> </span>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="<%=path%>/contract_file.xls">合同管理模版下载</a>
						</td>
					</tr>
					<tr
						style="background-image: url('<%=path%>/FDL/Dubang/Image/index_2323.gif'); width: 100%; color: #a52526; padding-top: 5px; font-weight: bold; height: 25px;">
						<td COLSPAN=11 class="label_file">
							输入文件标题:
							<span style="vertical-align: middle;"> <input type="text"
									id="filename" name="filename" style="width: 320px;" /> </span>
							<span style="vertical-align: middle;"> <input
									type="button" id="fileNameBtn" name="fileNameBtn" value="确定"
									class="btn" onclick="$upload()" /> </span>
						</td>
					</tr>
					<tr
						style="background-image: url('<%=path%>/FDL/Dubang/Image/index_2323.gif'); width: 100%; color: #a52526; padding-top: 5px; font-weight: bold; height: 25px;">
						<td COLSPAN=11 class="label_file">
							批量文件类型:
							<span style="vertical-align: middle;"> <select
									name="f_type" id="f_type" style="width: 325px;">
									<option value="1">
										电子表格
									</option>
								</select> </span>
							<span style="vertical-align: middle;"> <input
									type="button" id="cancelBtn" name="cancelBtn" value="取消"
									class="btn" onclick="cancelFileUpload();" /> </span>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div id="main_div" style="display: block;">
			<table id="filetable" class="main_nd" width="100%" bgcolor="white"
				cellspacing="0" cellpadding="0" align="center">
				<tr
					style="background-image: url('<%=path%>/FDL/Dubang/Image/index_2323.gif'); width: 100%; color: #a52526; padding-top: 5px; font-weight: bold; height: 25px;">
					<td COLSPAN="11" class="label_file">
			            合同管理信息列表
					</td>
				</tr>
				<tr>
					<td>
						<iframe id="contract"
							src="http://127.0.0.1:81/ndyd/frame.do?method=onContractMgr&naturalname=APPFRAME.APPFRAME.HTGL"
							width="100%" height="500" onload=dyniframesize();></iframe>
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>
<script>
	function onLoadIframeHight(str){//自适应高度
		//parent.document.getElementById('fileMainFrame').height=document.body.scrollHeight+str;
		showDiv();
	}
	function getTrId(){//获取附件表中的所有附件ID
		var str="";
		var detail="";
		$("#filetable tr").each(function(){
			var id = $(this).attr('id');
			if (id!=''){
				str+=detail+id;
				detail=",";
			}
		});
		return str;
	}
	function getTrId_(){//获取附件数
		var i=0;
		$("#filetable tr").each(function(){
			var id = $(this).attr('id');
			if (id!=''){
				i++;
			}
		});
		return i;
	}
	function updateFile(d_unid){
		var num = getTrId().length;
		if(d_unid!="" && num>=1){
			Ext.Ajax.request({
		        url: "<c:url value='/file.do?method=onUpdateD_unid' />",
		        // 指定要提交的表单id
		        method: 'POST',
		        sync: true,
		        params: {
		            unid: getTrId(),
		            d_unid:d_unid
		        },
		        success: function (response, options) {
		            var result = Ext.util.JSON.decode(response.responseText);
		            if (result.error != null) {
		               // Ext.MessageBox.alert('提示', result.tip);
		            } else {
		               // Ext.ux.Toast.msg("", result.tip);
		            }
		        },
		        failure: function (response, options) {
		            msgTip.hide();
		            checkAjaxStatus(response);
		            var result = Ext.util.JSON.decode(response.responseText);
		           // Ext.MessageBox.alert('提示', result.tip);
		        }
		    });
		}
	}
	
	function deleteFileByUnidAndD_unid(d_unid){
		var num = getTrId().length;
		if(d_unid!="" && num>=1){
			Ext.Ajax.request({
		        url: "<c:url value='/file.do?method=deleteFileByUnidAndD_unid' />",
		        // 指定要提交的表单id
		        method: 'POST',
		        sync: true,
		        params: {
		            unid: getTrId(),
		            d_unid:d_unid
		        },
		        success: function (response, options) {
		            var result = Ext.util.JSON.decode(response.responseText);
		            if (result.error != null) {
		               // Ext.MessageBox.alert('提示', result.tip);
		            } else {
		               // Ext.ux.Toast.msg("", result.tip);
		               var list = getTrId().split(",")
		               for(var i=0;i<list.length;i++){
		               		deltr(list[i])
		               }
		            }
		        },
		        failure: function (response, options) {
		            msgTip.hide();
		            checkAjaxStatus(response);
		            var result = Ext.util.JSON.decode(response.responseText);
		           // Ext.MessageBox.alert('提示', result.tip);
		        }
		    });
		}
		
	}
	//添加行
	function addtr(str){
		var arr= str.split('(~|~|~)');
		var html = "<tr id='"+arr[0]+"' name='"+arr[0]+"' onclick='getRowDate(this.id,this)' style='cursor: pointer;' ondblclick='dbcQueryByRowId(this.id)'>";
			html += "<td COLSPAN=4 class='label_file_td'>"+arr[1]+"</td><td COLSPAN=2 class='label_file_td'>"+arr[2]+"</td>";
			html += "<td COLSPAN=3 class='label_file_td'>"+arr[3]+"</td>";
			html += "<td class='label_file_td'>"+arr[4]+"</td>";
			html += "<td class='label_file_td'>"+arr[5]+"</td>";
			html += "</tr>";
		
		$('#filetable').append(html);
	}
	
	//删除id为table的行
	function deltr(id){
		$('#'+id).remove();
	}
	
	
	function $delete(){
		var unid=document.getElementById("d_hidden_row_unid").value;
		var msgTip = Ext.MessageBox.show({
			title:'系统提示',
			width : 250,
			msg:'正在删除文件,请稍后......'
		});
		if(unid!="undefined"&&unid!=null&&unid.trim()!=""){
		  	Ext.Ajax.request({
					url:"<c:url value='/file.do?method=delete'/>"+"&unid="+unid,//请求的服务器地址
					//form:'form1',//指定要提交的表单id
					method:'POST',
					success : function(response,options){
						msgTip.hide();
						var result = Ext.util.JSON.decode(response.responseText);
						if (result.error != null) {
			                Ext.MessageBox.alert('提示', result.tip);
			            } else {
			            	deltr(unid);
			            	onLoadIframeHight(0);
			            	$('#d_hidden_row_unid').val('');
			                Ext.ux.Toast.msg("", result.tip);
			            }
					},
					failure : function(response,options){
						msgTip.hide();
						checkAjaxStatus(response);
					}
			});
		}else{
			Ext.MessageBox.alert('<img src="<%=cssURL%>/btn/btn-cancel.png">提示','对不起，您必须先选中一个文件才能够执行相应的操作！');
			return;
		}
	}
	function getFileFullName(){//上传文件时取文件名
		var filepath = document.getElementById("files").value; 
		document.getElementById("filename").innerText=getFullFileName(filepath);//获取文件名
	}
	function $upload(){//上传
		var filepath = document.getElementById("files").value; 
		if(filepath==""){
			Ext.MessageBox.alert('<img src="<%=cssURL%>/btn/btn-cancel.png">提示', '附加文件为空!');
			return;
		}
		
		if (filepath.match(/.+\.exe$|.+\.bat$|.+\.com$|.+\.pif$|.+\.vbs$|.+\.js$|.+\.inf$|.+\.vb$|.+\.hta$/ig)) {
			Ext.MessageBox.alert('<img src="<%=cssURL%>/flag/customer/invalid.png"><font style="font-size:14px;">警告提示</font>','由于可能会带来安全隐患，不能上传可执行类型的文件！');
			filepath.outerHTML=filepath.outerHTML  //请空文件选择内容
			return ;
		}
		
		if (!filepath.match(/.+\.xls$|.+\.xlsx$/ig)) {
			Ext.MessageBox.alert('<img src="<%=cssURL%>/flag/customer/invalid.png"><font style="font-size:14px;">警告提示</font>','请上传EXCEL文件!');
			filepath.outerHTML=filepath.outerHTML  //请空文件选择内容
			return ;
		}
		
		var d_unid = 'contract';
		var filename = encodeURI(encodeURI(document.getElementById("filename").value));
		var f_type = encodeURI(encodeURI(getSelectedText("f_type")));
		
		var msgTip = Ext.MessageBox.show({
			title:'系统提示',
			width : 250,
			msg:'正在上传文件请稍后......'
		});
		
	  	Ext.Ajax.request({
				url:"<c:url value='/file.do?method=onUploadFile'/>&id="+d_unid+"&filename="+filename+"&f_type="+f_type,//请求的服务器地址
				form:'form1',//指定要提交的表单id
				method:'POST',
				sync: true,
				success : function(response,options){
					msgTip.hide();
					if (response.responseText!=null){
						//addtr(response.responseText);
						//onLoadIframeHight(0);
						if (response.responseText){
						Ext.ux.Toast.msg("", '上传附件成功');
						}else{
						Ext.MessageBox.alert('<img src="<%=cssURL%>/btn/btn-cancel.png">提示', '上传附件失败');
						}
						
					} else {
						Ext.MessageBox.alert('<img src="<%=cssURL%>/btn/btn-cancel.png">提示', '上传附件失败');
					}
				},
				failure : function(response,options){
					msgTip.hide();
					checkAjaxStatus(response);
				}
		});
		window.frames['contract'].location.reload();
		//document.getElementById('contract')=window.location.reload();
	}
	function cancelFileUpload(){//取消上传文件
		var files = document.getElementById("files");
		files.outerHTML=files.outerHTML
		document.getElementById("filename").value=""; 
		//document.getElementById('addFile_upload').style.display='none';
		//onLoadIframeHight(20);
	}
	function displayOrHidden(){//添加附件 隐藏显示
		//document.getElementById('addFile_upload').style.display='block';
		//onLoadIframeHight(0);
	}
	
	function cQueryByRowId(){//单击列表查看
		var unid = document.getElementById("d_hidden_row_unid").value;
		if(unid!="undefined"&&unid!=null&&unid.trim()!=""){
			$download('0');
		}else{
			Ext.MessageBox.alert('<img src="<%=cssURL%>/btn/btn-cancel.png">提示','对不起，您必须先选中一个文件才能够执行相应的操作！');
			return;
		}
	}
	
	function $download(isOnLine){
		var unid = document.getElementById("d_hidden_row_unid").value;
		if (unid==''){
			Ext.MessageBox.alert('<img src="<%=cssURL%>/btn/btn-cancel.png">提示','对不起，您必须先选中一个文件才能够执行相应的操作！');
			return;
		} else {
			window.open("<c:url value='/file.do?method=onDownLoadFile'/>&isOnLine="+isOnLine+"&unid="+unid);
		}
	}
	
	function dbcQueryByRowId(id){//双击列表查看
		cQueryByRowId();
	}
	
	function showDiv(){
		//if($('#file_btn')) $('#file_btn').hide();
		//if($('#main_div')) $('#main_div').show();
		
		//onLoadIframeHight(20); 
	}
	
	function hideDiv(){
		/**
		$('#file_btn').show();
		$('#main_div').hide();
		$('#addFile_upload').hide();
		//location.reload(); //隐藏时刷新本窗口
		document.getElementById("file_btn").value="附件(共有"+getTrId_()+"个文件)";  
		onLoadIframeHight(0);
		**/
	}
	
	/** 选中颜色设定 */
	var   curRow;       //全局行号 
	var   curRowId;   //选中行的记录信息的ID 
	var   curColor; 
	function getRowDate(id,tr1){//获取选中后的ID值 
		if(curRow){ 
			curRow.bgColor=curColor; 
			curColor=tr1.bgColor; 
			tr1.bgColor= "#E0EEF7"; 
		}else{ 
			curColor=tr1.bgColor; 
			tr1.bgColor= "#E0EEF7"; 
		} 
		curRow=tr1; 
		curRowId=tr1.id; 
		
		$('#d_hidden_row_unid').val(id);
	}
	
	/** 获取文件路径 文件名 扩展名 */
	function   getFullFileName(filepath){ //完整文件名getFullFileName(filepath)
		var   p=filepath.lastIndexOf('\\'); 
		return   filepath.substr(++p,filepath.length-p); 
	} 
	function   getFileName(filepath){ //文件名getFileName(getFullFileName(filepath))
		var   p=filepath.lastIndexOf('.'); 
		return   filepath.substr(0,p); 
	} 
	function   getExtName(filepath){ //扩展名getExtName(getFullFileName(filepath))
		var   p=filepath.lastIndexOf('.'); 
		return   filepath.substr(++p,filepath.length-p); 
	} 
	
	function getSelectedText(type){//获取select中的文本值
		var obj=document.getElementById(type);
		for(i=0;i<obj.length;i++){
			if(obj[i].selected==true){
				return obj[i].innerText; //关键是通过option对象的innerText属性获取到选项文本
			}
		}
	}
	
</script>