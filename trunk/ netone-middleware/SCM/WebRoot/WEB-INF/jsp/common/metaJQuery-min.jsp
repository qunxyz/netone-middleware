<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String jqueryScriptPath = request.getContextPath()+"/script";
%>
<script src="<%=jqueryScriptPath%>/jquery-1.5.2.min.js" type="text/javascript"></script>
<!-- dateformat -->
<script src="<%=jqueryScriptPath%>/dateformat.js" type="text/javascript"></script>

<!--
<LINK rel=stylesheet type=text/css href="<%=jqueryScriptPath%>/jquery-plugin/flexigrid/css/flexigrid.pack.css">
<script src="<%=jqueryScriptPath%>/jquery-plugin/flexigrid/flexigrid.js" type="text/javascript"></script>
-->


<link type="text/css" href="<%=jqueryScriptPath%>/jquery-plugin/autocomplete/jquery.autocomplete.css" rel="stylesheet" />
<script type="text/javascript" src="<%=jqueryScriptPath%>/jquery-plugin/autocomplete/jquery.autocomplete.pack.js"></script>


<link type="text/css" href="<%=jqueryScriptPath%>/jquery-plugin/jpaper/jpaper.css" rel="stylesheet" />
<script type="text/javascript" src="<%=jqueryScriptPath%>/jquery-plugin/jpaper/jpaper.js"></script>

<script type="text/javascript" src="<%=jqueryScriptPath%>/jquery-plugin/ajaxfileupload/ajaxfileupload.js"></script>

<script>
function makeUUID() {
    var S4 = function () {
        return (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1);
    };
    return (S4() + S4() + "-" + S4() + "-" + S4() + "-" + S4() + "-" + S4() + S4() + S4());
}

function $deleteone(unid,IDSTR){
		var msgTip = Ext.MessageBox.show({
			title:'系统提示',
			width : 250,
			msg:'正在删除文件,请稍后......'
		});
		if(unid!="undefined"&&unid!=null&&unid.trim()!=""){
		  	Ext.Ajax.request({
					url:"<%=request.getContextPath()%>/file.do?method=delete"+"&unid="+unid,//请求的服务器地址
					method:'POST',
					success : function(response,options){
						msgTip.hide();
						var result = Ext.util.JSON.decode(response.responseText);
						if (result.error != null) {
			                Ext.MessageBox.alert('提示', result.tip);
			            } else {
			            	if (IDSTR!=''){
			            		var tmpid = $('#fileform'+IDSTR).find('#TMPID'+IDSTR).val();
			            		var $fileform = $('#fileform'+IDSTR);
			            		if($fileform) {
			            			$fileform.parent().parent().find('#'+tmpid).val('');
			            			$('#f_type'+IDSTR).val('');
			            			$('#filename'+IDSTR).val('');
			            			$fileform.show();
			            			$('#filetext'+IDSTR).hide();
			            		}
			            	}
			                Ext.ux.Toast.msg("", result.tip);
			            }
					},
					failure : function(response,options){
						msgTip.hide();
						checkAjaxStatus(response);
					}
			});
		}else{
			Ext.MessageBox.alert('提示','对不起，您必须先选中一个文件才能够执行相应的操作！');
			return;
		}
}

function $getFilename(fid){
var filename = '';
Ext.Ajax.request({
		url:"<%=request.getContextPath()%>/file.do?method=onGetFilename"+"&fid="+fid,//请求的服务器地址
		method:'POST',
		sync:true,
		success : function(response,options){
			filename= response.responseText;
			return filename;
		},
		failure : function(response,options){
			return '';
		}
});
return filename;
}

function $getFileFullName(id){//上传文件时取文件名
	var filepath = document.getElementById("files"+id).value; 
	var fullfilename = $getFullFileName(filepath);
	document.getElementById("filename"+id).value=fullfilename;//获取文件名
}
function $getFullFileName(filepath){ //完整文件名getFullFileName(filepath)
	var p=filepath.lastIndexOf('\\');
	return filepath.substr(++p,filepath.length-p);
}
	
function $uploadone(id){//上传
		var filepath = document.getElementById("files"+id).value; 
		if(filepath==""){
			Ext.MessageBox.alert('提示', '附加文件为空!');
			return;
		}
		
		if (filepath.match(/.+\.exe$|.+\.bat$|.+\.com$|.+\.pif$|.+\.vbs$|.+\.js$|.+\.inf$|.+\.vb$|.+\.hta$/ig)) {
			Ext.MessageBox.alert('警告提示','由于可能会带来安全隐患，不能上传可执行类型的文件！');
			filepath.outerHTML=filepath.outerHTML;  //请空文件选择内容
			return ;
		}
		
		var d_unid = '';;
		var filename = encodeURI(encodeURI(document.getElementById("filename"+id).value));
		var filenamestr=document.getElementById("filename"+id).value;
		var f_type = "file";
		var msgTip = Ext.MessageBox.show({
				        title: '提示',
				        width: 250,
				        closable:false,
				        msg: '正在保存信息请稍候......'
				    });
		 $.ajaxFileUpload({
              url:"<%=request.getContextPath()%>/file.do?method=onUploadFile&ext=files"+id+"&id="+d_unid+"&filename="+filename+"&f_type="+f_type,
              secureuri:false,
              fileElementId:'files'+id,//与页面处理代码中file相对应的ID值
              dataType: 'text',//返回数据类型:text，xml，json，html,scritp,jsonp五种
              success: function (data, status){
              		msgTip.hide();
					if (data==null || data==''){
						Ext.MessageBox.alert('提示', '上传附件失败');
					} else {
						var str = data;
						var arr= str.split('(~|~|~)');
						var tmpid = $('#fileform'+id).find('#TMPID'+id).val();
	            		var $fileform = $('#fileform'+id);
	            		if($fileform) {
	            			$fileform.parent().parent().find('#'+tmpid).val(arr[0]);
	            			$('#f_type'+id).val(f_type);
	            			$('#filename'+id).val(filenamestr);
	            			$fileform.hide();
	            			var btn = '<input type="button" onclick="$deleteone(\''+arr[0]+'\',\''+id+'\')" value="删除">';
	            			$('#filetext'+id).html(filenamestr+btn);
	            			$('#filetext'+id).show();
	            		}
						Ext.ux.Toast.msg("", '上传附件成功');
					}
              },
              error: function (data, status, e){
              	  msgTip.hide();
                  Ext.MessageBox.alert('提示', '上传附件失败');
              }
          });
	}
</script>

