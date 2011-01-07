<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>

<%@ taglib uri="http://www.oesee.com/netone/dy" prefix="dy"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.oesee.com/netone/portal" prefix="portal"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>Insert title here</title>
		<script type="text/javascript">
		var nowchice=0;
		function link(values,valuemax){
			document.frames["now"].location='<portal:envget envkey="WEBSER_FCK"/>/PagelistViewSvl?pagename=simplefcklist&chkid='+values;	
		    nowchice=valuemax;
		}
		
		function linkMax(){
			var url='<portal:envget envkey="WEBSER_FCK"/>/PagelistViewSvl?pagename=simplefcklist&chkid='+nowchice;
			window.open(url,'_blank');
		}
	</script>
	</head>
	<body>
		<h3>
			标准公告-纵向布局
		</h3>
		通过获取动态表单中的数据，对数据进行处理，比如获得多采文本的资源ID，调用多采文本进行显示。 其中为了显示不同新闻分类的数据需要在URL里加上 fatherlsh参数
		<br>
		<b>测试例子</b>
		<br>
		<b>参数包括</b><br>
		fatherlsh:father流水号<br>
		prepage:页面显示条数，如果不传递，默认为10<br>
		使用动态表单标签，获得数据，显示页面
		<hr>
		<c:set var="pagesize" value="${param.prepage>0?param.prepage:10}"></c:set>
		<dy:dydata dataname="mydata"
			fatherlsh="${param.fatherlsh}"
			formcode="017806cfb92311dda932b9bba8510e29_" prepage="${pagesize}"/>
		<table border='1' width='800' height='220'>
			<tr>

				<td>
					<iframe id='now'
						src='<portal:envget envkey="WEBSER_FCK"/>/PagelistViewSvl?chkid=${fn:substring(mydata[0].extendattribute,6,38)}&pagename=simplefcklist'
						frameborder='0' width='560' height='210'></iframe>
					<br>
					<a href='javascript:linkMax()'><font color='#0066CC' size='2'>[详细信息]</font></a>

				</td>
			</tr>
			<tr>
				<td>
					<table width='200'>
						<c:forEach items="${mydata}" var="data">
							<tr>
								<td>
									<strong><font size='2'>[${fn:substring(data.created,0,10)}]</font>
									</strong>
								
									<strong> <a
										href='javascript:link("${fn:substring(data.extendattribute,6,38)}","${fn:substring(data.extendattribute,47,79)}")'><font
											color='#0066CC' size='2'>${data.column3}</font> </a> </strong>
								</td>
							</tr>

						</c:forEach>
					</table>
				</td>
			</tr>
		</table>

	</body>
</html>