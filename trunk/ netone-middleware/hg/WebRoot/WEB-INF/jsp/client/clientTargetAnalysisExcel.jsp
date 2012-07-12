<%@ page language="java"
	contentType="application/vnd.ms-excel; charset=utf-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.lang.*"%>
<%@ page import="java.math.BigDecimal"%>
<%@ page import="java.text.DecimalFormat"%>
<%@page import="yc.erp.zy.common.MathHelper"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ include file="../common/taglibs.jsp"%>
<%
	String path = request.getContextPath();
    //response.setContentType("application/vnd.ms-excel"); 
    
    Collection productCategorySet = (Collection) request.getAttribute("productCategorySet");
    DecimalFormat df = new DecimalFormat("##0.00"); 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-

transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type"
			content="application/vnd.ms-excel; charset=utf-8" />
		<title>酒经销商销售分析</title>
	</head>
	<body>
		<table border="0" cellPadding="0" borderColor="#000000"
			style="border-collapse: collapse; text-align: center">

			<tr>
				<c:choose>
			    	<c:when test="${col5=='selected'}">
			    	<td width="730" valign="center" colspan="${showColCount+defaultShowColCount+cCount*3}" align="center">
						<font style="font-size: 20px"><B>经销商销售分析</B></font>
					</td>
			    	</c:when>
			    	<c:otherwise>
			    	<td width="730" valign="center" colspan="${showColCount+defaultShowColCount}" align="center">
						<font style="font-size: 20px"><B>经销商销售分析</B></font>
					</td>
			    	</c:otherwise>
			    </c:choose>
			</tr>
			<tr>
				<c:choose>
			    	<c:when test="${col5=='selected'}">
			    	<td  align="left" colspan="${showColCount-1}"><p>日期范围：${beginDate}至${endDate}</p></td>
			    	</c:when>
			    	<c:otherwise>
			    	<td  align="left" colspan="${showColCount}"><p>日期范围：${beginDate}至${endDate}</p></td>
			    	</c:otherwise>
			    </c:choose>
			    <c:choose>
			    	<c:when test="${col5=='selected'}">
			    		<td  align="right" colspan="${cCount*3+3}"><p>查询日期： ${now}</p></td>
			    	</c:when>
			    	<c:otherwise>
			    		<td align="right" colspan="3"><p>查询日期： ${now}</p></td>
			    	</c:otherwise>
			    </c:choose>
			    
			</tr>
			</table>
			<table border="1" cellPadding="0" borderColor="#000000"
			style="border-collapse: collapse;">
			<tr>
				<c:if test="${col1=='selected'}">
				<td width="64" valign="center" align="center" rowspan="1">
					营销管理公司
				</td>
				</c:if>
				<c:if test="${col2=='selected'}">
				<td width="64" valign="center" align="center" rowspan="1">
					销售部门
				</td>
				</c:if>
				<c:if test="${col3=='selected'}">
				<td width="64" valign="center" align="center" rowspan="1">
					业务主任
				</td>
				</c:if>
				<c:if test="${col4=='selected'}">
				<td width="64" valign="center" align="center" rowspan="1">
					客户编号
				</td>
				<td width="64" valign="center" align="center" rowspan="1">
					客户名称
				</td>
				</c:if>
				<c:if test="${col5=='selected'}">
				<%
				  
					for (Iterator itr1 = productCategorySet.iterator(); itr1.hasNext();) {
						Map map = (Map) itr1.next();
						String categoriesName = (String) map.get("categoriesName");
				%>
				<td width="390" valign="top">
					<table width="391" border="1" cellPadding="0" borderColor="#000000"
						style="border-collapse: collapse;">
						<tr>
							<td colspan="3" align="center"><%=categoriesName%>销售额
							</td>
						</tr>
						<tr>
							<td width="64" align="center">
								销售指标
							</td>
							<td width="64" align="center">
								实际销售
							</td>
							<td width="64" align="center">
								完成比例
							</td>
						</tr>
					</table>
				</td>
			
				<%
					}
				%>
				</c:if>
				<!-- 
				<td colspan="1" align="center">
					应兑付金额
				</td>
				<td width="76" align="center">
					&nbsp;截止本月累计应兑付
				</td>
				<td width="78" align="center">
					截止上月累计已兑付
				</td>
				<td width="64" align="center">
					本月兑付
				</td> -->

				<td>
					<table width="391" border="1" cellPadding="0" borderColor="#000000"
						style="border-collapse: collapse;">
						<tr>
							<td colspan="3" align="center">
								总销售额
							</td>
						</tr>
						<tr>
							<td width="64" align="center">
								销售指标
							</td>
							<td width="64" align="center">
								实际销售
							</td>
							<td width="64" align="center">
								完成比例
							</td>
						</tr>
					</table>
				</td>
			
				<!-- 
				<td align="center">
					应兑付金额
				</td>
				<td width="76" valign="center">
					&nbsp;截止本月累计应兑付
				</td>
				<td width="78" valign="center">
					截止上月累计已兑付
				</td>
				<td width="64" valign="center">
					本月兑付
				</td>
 				-->
			</tr>
			<tr>
				<%
				   
					Map indentDetailMap = (Map) request.getAttribute("indentDetailMap");
					
					 
					for (Iterator itr2 = indentDetailMap.values().iterator(); itr2
							.hasNext();) {
						Map rowMap = (Map) itr2.next();
						String yxManagerCompany = (String) rowMap
								.get("yxManagerCompany");//营销管理公司
						String sellDepartment = (String) rowMap.get("sellDepartment");//销售部门
						String operationDirector = (String)rowMap.get("operationDirector");
						String clientLoginName = (String)rowMap.get("clientCode");//客户编号
						String clientName = (String) rowMap.get("clientName");//客户名称
				%>
				<c:if test="${col1=='selected'}">
				<td width="64" valign="bottom" align="center">
					&nbsp;<%=yxManagerCompany%>&nbsp;
				</td>
				</c:if>
				<c:if test="${col2=='selected'}">
				<td width="64" valign="bottom" align="center">
					&nbsp;<%=sellDepartment%>&nbsp;
				</td>
				</c:if>
				<c:if test="${col3=='selected'}">
				<td width="64" valign="bottom" align="center">
					&nbsp;<%=StringUtils.substringBefore(operationDirector,"[")%>&nbsp;
				</td>
				</c:if>
				<c:if test="${col4=='selected'}">
				<td width="64" valign="bottom" align="center">
					&nbsp;<%=clientLoginName%>&nbsp;
				</td>
				<td width="64" valign="bottom" align="center">
					&nbsp;<%=clientName%>&nbsp;
				</td>
				</c:if>
				<%
					BigDecimal planPaymentAll = new BigDecimal(0);
						BigDecimal xszbAll = new BigDecimal(0);
						BigDecimal indentPaymentAll = new BigDecimal(0);
						BigDecimal awardPaymentAll = new BigDecimal(0);
						Double percentAll = new Double(0);
						
						
						for (Iterator itr3 = productCategorySet.iterator(); itr3
								.hasNext();) {
							Map map = (Map) itr3.next();
							String categoriesCode = (String) map.get("categoriesCode");
							Map pSetMap = (Map) rowMap.get("pKey");
							Map pMap = (Map) pSetMap.get(categoriesCode);

									
							BigDecimal indentPayment = (BigDecimal) pMap
									.get("indentPayment") == null ? new BigDecimal(0)
									: (BigDecimal) pMap.get("indentPayment");
									
							BigDecimal xszb = (BigDecimal) pMap
									.get("xszb") == null ? new BigDecimal(0)
									: (BigDecimal) pMap.get("xszb");//销指标售
										
							Object percent = pMap.get("percent");
									
								
							BigDecimal awardPayment = (BigDecimal) pMap
									.get("awardPayment") == null ? new BigDecimal(0)
									: (BigDecimal) pMap.get("awardPayment");

							xszbAll = xszbAll.add(xszb);//先测试
							
							indentPaymentAll = indentPaymentAll.add(indentPayment);
							awardPaymentAll = awardPaymentAll.add(awardPayment);

							if (xszbAll.doubleValue() == new Double(0)) {
								percentAll = new Double(0);
							} else {
								percentAll = indentPaymentAll.doubleValue()/xszbAll.doubleValue() * 100;
							}
							
				%>
				<c:if test="${col5=='selected'}">
				<td width="390" valign="top">
					<table width="391" border="1" cellPadding="0" borderColor="#000000"
						style="border-collapse: collapse;">
						<tr align="right">
							<td width="64" valign="bottom" >
								<%=(df.format(xszb)).equals(".00") ? "0.00"
							: df.format(xszb)%>
							</td>
							<td width="64" valign="bottom">
								<%=df.format(indentPayment).equals(".00") ? "0.00"
							: df.format(indentPayment)%></td>
							<td width="64" valign="bottom">
								<%=df.format(percent).equals(".00") ? "0.00" : df.format(percent)%>%
							</td>
						</tr>
					</table>
				</td>
			
				</c:if>
				<%
					}
				%>
				
				<!-- 
				<td width="76" valign="center">
					&nbsp;
				</td>
				<td width="78" valign="center"></td>
				<td width="64" valign="center"></td>
				<td width="64" valign="center"></td>
				 -->
				<td width="390" valign="top">
					<table width="391" border="1" cellPadding="0" borderColor="#000000"
						style="border-collapse: collapse;">
						<tr align="right">
							<td width="64" valign="bottom">
								<%=df.format(xszbAll).equals(".00") ? "0.00"
						: df.format(xszbAll)%>
							</td>
							<td width="64" valign="bottom">
								<%=df.format(indentPaymentAll).equals(".00") ? "0.00"
						: df.format(indentPaymentAll)%></td>
							<td width="64" valign="bottom">
								<%=df.format(percentAll).equals(".00") ? "0.00" : df.format(percentAll)%>%
							</td>
						</tr>
					</table>
				</td>
			
			</tr>
			<%
				}
			%>
			<tr>
				<c:set var="flag" value="0"></c:set>
				<c:if test="${col1=='selected'}">
				<c:set var="flag" value="${flag+1}"></c:set>
				</c:if>
				<c:if test="${col2=='selected'}">
				<c:set var="flag" value="${flag+1}"></c:set>
				</c:if>
				<c:if test="${col3=='selected'}">
				<c:set var="flag" value="${flag+1}"></c:set>
				</c:if>
				<c:if test="${col4=='selected'}">
				<c:set var="flag" value="${flag+2}"></c:set>
				</c:if>
				<c:choose>
					<c:when test="${col5=='selected' && col1!='selected' && col2!='selected' && col3!='selected' && col4!='selected'}">
					</c:when>
					<c:otherwise>
					<td align="left" colspan="${flag}">&nbsp;&nbsp;&nbsp;合计</td>
					</c:otherwise>
				</c:choose>

				
				<%
					List listtotal=(List)request.getAttribute("totalinfo");
					
					for (Iterator itr1 = listtotal.iterator(); itr1.hasNext();) {
						double[] value = (double[]) itr1.next();
						
				%>
				<c:if test="${col5=='selected'}">
				<td >
					<table width="391" border="1" cellPadding="0" borderColor="#000000"
						style="border-collapse: collapse;">
						<tr>
							<td width="64" align="right">
							<fmt:formatNumber value="<%=value[0] %>" pattern="###0.##" />
								
							</td>
							<td width="64" align="right">
							<fmt:formatNumber value="<%=value[1] %>" pattern="###0.##" />
								
							</td>
							<td width="64" align="right">
								<fmt:formatNumber value="<%=value[2] %>" pattern="###0.##" />%
							</td>
						</tr>
					</table>
				</td>
			
				</c:if>
				<%
				}
				%>
				<td>
					<table width="391" border="1" cellPadding="0" borderColor="#000000"
						style="border-collapse: collapse;">
						<tr>
							<td width="64" align="right">
							<fmt:formatNumber value="${totalinfoX[0]}" pattern="###0.##" />
								
							</td>
							<td width="64" align="right">
								<fmt:formatNumber value="${totalinfoX[1]}" pattern="###0.##" />
							</td>
							<td width="64" align="right">
								<fmt:formatNumber value="${totalinfoX[2]}" pattern="###0.##" />%
							</td>
						</tr>
					</table>
				</td>
			
			</tr>
			
		</table>
	</body>
</html>
