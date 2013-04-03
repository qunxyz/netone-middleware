<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	response.setHeader("X-UA-Compatible","IE=EmulateIE8");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<jsp:include page="/WEB-INF/jsp/common/metaExt.jsp"></jsp:include>
		<jsp:include page="/WEB-INF/jsp/common/metaJQuery-min.jsp"></jsp:include>
        <jsp:include page="/WEB-INF/jsp/common/metaJQuery-ui-dialog.jsp"/>
		<script language="javascript" type="text/javascript" src="<%=path%>/My97DatePicker/WdatePicker.js" charset="gb2312"></script>
		<script>function $WdatePicker(t){if (t==1){		WdatePicker({isShowClear:true,dateFmt:"yyyy-MM-dd HH:mm:ss"});	} else if(t==2){		WdatePicker({isShowClear:true,dateFmt:"yyyy-MM-dd"});	} else if(t=3){		WdatePicker({isShowClear:true,dateFmt:"HH:mm:ss"});	}}</script>
		<title>导出发货通知单</title>
	</head>
	<body>
	<center>
	<form id="formx" action="<c:url value='/app.do?method=exportDeliveryInfo' />" target="_blank" method="post">
		<p id="conditionDiv">
		日期：
		<input type="text" id="FStartDate"
			name="FStartDate" class="Wdate"
			onFocus="$WdatePicker(2);" value="${beginDate}" />
		至
		<input type="text" id="FEndDate" name="FEndDate"
			 class="Wdate"
			onFocus="$WdatePicker(2);" value="${endDate}" />
		</p>
		<p>
		客户名称:<input id="FCustIDName" name="FCustIDName" />
		</p>
		<p>
		单据编号:<input id="FBillNo" name="FBillNo" />
		</p>
		
		<input type="button"  value="导出" onclick='$export();'   />
	</form>	
	</center>	
	</body>
</html>
<script type="text/javascript">
<!--
function $export(){
	/**
	var url = "<c:url value='/app.do?method=exportsellinfo' />";
	Ext.Ajax.request({
        url: url,
        // 请求的服务器地址
        // 指定要提交的表单id
        method: 'POST',
        params:{FBillNo:$('#FBillNo').val(),FStartDate:$('#FStartDate').val(),FEndDate:$('#FEndDate').val(),sale:$('#sale').val()},
        success: function (response, options) {
        },
        failure: function (response, options) {
            checkAjaxStatus(response);
            var result = Ext.util.JSON.decode(response.responseText);
            Ext.MessageBox.alert('提示', result.tip);
        }
    });
    
    window.open(url+"&FSupplyIDName="+$('#FSupplyIDName').val()+"&FBillNo="+$('#FBillNo').val()+"&FStartDate="+$('#FStartDate').val()+"&FEndDate="+$('#FEndDate').val()+"&sale="+$('#sale').val())
    **/
    document.getElementById('formx').submit();
}
//-->
</script>
