<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<HTML dir="LTR" lang="zh-CN">
	<HEAD>
		<TITLE></TITLE>

		<SCRIPT TYPE="text/javascript">
		function release(id){
			window.open('/cmsWeb/RmiBeanViewSvl?id=${id}&release='+id,'_self');
		}
		</SCRIPT>
	</HEAD>

	<BODY bgcolor="#FFFFFF">
		<input type='hidden' value='${id}'> 
		<table width="96%" cellspacing="0" border="1" cellpadding="2"
			bordercolordark="#999999" bordercolorlight="#FFFFFF">
			<tr>

				<td>
					BeanName
				</td>
				<td>
					RemoteIp
				</td>
				<td>
					Create Time
				</td>
				<td>
					Manager
				</td>
			</tr>
			<c:forEach items="${list}" var="each">
				<tr>

					<td>
						${each.name}
					</td>
					<td>
						${each.ip}
					</td>
					<td>
						${each.time}
					</td>
					<td>
						<a href="javascript:release('${each.lsh}')"> Õ∑≈</a>
					</td>
				</tr>
			</c:forEach>
		</table>
	</BODY>
</HTML>
