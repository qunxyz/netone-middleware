<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://www.oesee.com/netone/portal" prefix="portal"%>
<%@ taglib uri="http://www.oesee.com/netone" prefix="rs"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";

	String cssURL = request.getContextPath() + "/theme/erp/default";

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<base href="<%=basePath%>">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<jsp:include page="/WEB-INF/jsp/common/metaExt.jsp"></jsp:include>
		<title>张裕销售与分销系统</title>
		<!–[if lt IE 7]>
			<script src=”http://ie7-js.googlecode.com/svn/version/2.0(beta3)/IE7.js” type=”text/javascript”></script>
		<![endif]–>
	</head>
	<body onLoad="onload1()" class="BODY">
		<table width="1000" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td class="Header" width="1000">
					<img src="<%=cssURL%>/style/header.gif" border="0" usemap="#Map"
						width="999" height="66" border="0">
				</td>
			</tr>
			<tr>
				<td>
					<table width="1000" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="200" class="LeftTablebody">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td>
											<table width="100%" border="0" cellspacing="0"
												cellpadding="0" class="Tablebody">
												<tr>
													<td>
														<table width="100%" border="0" cellspacing="0"
															cellpadding="0" onClick="open6();" style="cursor: hand;">
															<tr>
																<td class="L1CTitleIcon">&nbsp;</td>
																<td class="L1CTitle">
																	个人区域
																</td>
																<td class="L1CTitleIconR">
																	<div name="imgPerson" class="L1CTitleIconUp" id="imgPerson" />
																</td>
															</tr>
														</table>
													</td>
												</tr>
												<tr>
													<td class="L1CBody">
														<table width="100%" border="0" cellpadding="0"
															cellspacing="0" id="PersonModel">
															<tr>
																<td class="L1CItemIcon">&nbsp;</td>
																<td class="L1CItem1">
																	<a href="javascript:void(0)"
																		onClick="linkUrl('<c:url value='/workList.do?method=onMainView' />',0,3)">待办任务</a> 
																		
																		<a href="javascript:void(0)"
																		onClick="linkUrl('<c:url value='/workList.do?method=onMainView&cache=no' />',0,3)">[刷新]</a>
																</td>
															</tr>
														</table>
													</td>
												</tr>
											</table>
										</td>
									</tr>
<c:if test="${sgs!='store'}">
									<tr>
										<td>
											<table width="100%" border="0" cellspacing="0"
												cellpadding="0" class="Tablebody">
												<tr>
													<td>
														<table width="100%" border="0" cellspacing="0"
															cellpadding="0" onClick="open8();" style="cursor: hand;">
															<tr>
																<td class="L1CTitleIcon">&nbsp;</td>
																<td class="L1CTitle">
																	经营分析
																</td>
																<td class="L1CTitleIconR">
																	<div name="imgPerson1" class="L1CTitleIconUp" id="imgPerson1" /></td>
														  </tr>
														</table>
													</td>
												</tr>
												<tr>
													<td class="L1CBody">
														<table width="100%" border="0" cellpadding="0"
															cellspacing="0" id="PersonModel1">
															<c:if test="${sgs=='sgs' ||adminx=='adminx'}">
															<tr >
																<td class="L1CItemIcon">&nbsp;</td>
																<td class="L1CItem1">
																	<a href="javascript:void(0)"
																		onClick="linkUrl('<c:url value='/portal/ChartAll.jsp' />',0,3)">综合图表分析</a>
																</td>
															</tr>
															<tr style='display:none'>
																<td class="L1CItemIcon">&nbsp;</td>
																<td class="L1CItem1">
																	<a href="javascript:void(0)"
																		onClick="linkUrl('<c:url value='/storage/outStorage.do?method=onReportForms' />',0,3)">普通图表分析</a>
																</td>
															</tr>
															</c:if>
														
															<tr>
																<td class="L1CItemIcon">&nbsp;</td>
																<td class="L1CItem1">
																	<a href="javascript:void(0)"
																		onClick="linkUrl('<c:url value='/portal/reportPaymentDetail.jsp' />',0,3)">财务报表分析</a>
																</td>
															</tr>
															<tr>
																<td class="L1CItemIcon">&nbsp;</td>
																<td class="L1CItem1">
																	<a href="javascript:void(0)"
																		onClick="linkUrl('<c:url value='/portal/reportSellDetail.jsp' />',0,3)">销售报表分析</a>
																</td>
															</tr>
															<c:if test="${sgs=='sgs'}">
															<tr>
																<td class="L1CItemIcon">&nbsp;</td>
																<td class="L1CItem1">
																	<a href="javascript:void(0)"
																		onClick="linkUrl('<c:url value='/portal/reportStorageDetail.jsp' />',0,3)">库存报表分析</a>
																</td>

															</tr>
															</c:if>
														</table>
													</td>
												</tr>
											</table>
										</td>
									</tr>
</c:if>
									<!--  -->
									<!-- 仓库管理 -->
									<c:if test="${sgs!='kh'}">
									<tr>
										<td>
											<table width="100%" border="0" cellspacing="0"
												cellpadding="0" class="Tablebody">
												<tr>
													<td>
														<table width="100%" border="0" cellspacing="0"
															cellpadding="0" onClick="open1();" style="cursor: hand;">
															<tr>
																<td class="L1CTitleIcon">&nbsp;</td>
																<td class="L1CTitle">
																	仓库管理
																</td>
																<td class="L1CTitleIconR">
																	<div name="imgStorage" class="L1CTitleIconDown" id="imgStorage" />
																</td>
															</tr>
														</table>
													</td>
												</tr>
												<tr>
													<td class="L1BBody">
														<table width="100%" border="0" cellpadding="0"
															cellspacing="0" id="storageModel" style="display: none">
															<tr>
																<td class="L1CItemIcon">&nbsp;</td>
																<td class="L1CItem1">
																	<a href="javascript:void(0)"
																		onClick="linkUrl('<c:url value='/storage/storage.do?method=onMainView' />',0,1)">库容管理</a>
																</td>
															</tr>
															<c:if test="${adminx=='adminx'|| sgs=='store'}">
																<tr>
																	<td width="20" class="L1CItemIcon">&nbsp;</td>
																	<td class="L1CItem1">
																		<a href="javascript:void(0)" onClick="query(2);">配送管理</a>
																	</td>
																</tr>
															</c:if>
															<!--  
															<c:if
																test="${adminx=='adminx' || sgs=='sgs' || sgs=='store'}">
																<tr>
																	<td class="L1CItemIcon">&nbsp;</td>
																	<td class="L1CItem1">
																		<a href="javascript:void(0)"
																			onClick="linkUrl('<c:url value='/client/importFile.do?method=onImportClientdFileView' />',0,1)">三级码上传</a>
																	</td>
																</tr>
															</c:if>
															-->
															<c:if
																test="${adminx=='adminx'|| sgs=='store'}">
																<tr>
																	<td class="L1CItemIcon">
																		&nbsp;
																	</td>
																	<td class="L1CItem1">
																		<a href="javascript:void(0)"
																			onClick="linkUrl('<c:url value='/storage/storageDamaged.do?method=onMainView' />',0,5)">库内破损管理</a>
																	</td>
																</tr>
															</c:if>
															<!-- 
															<c:if test="${sgs=='kh'}">
																<tr>
																	<td class="L1CItemIcon">&nbsp;</td>
																	<td class="L1CItem1">
																		<a href="javascript:void(0)"
																			onClick="linkUrl('<%=basePath%>/UserFile/${loginname}',0,4)">三级码下载</a>
																	</td>
																</tr>
															</c:if>
															-->
																<c:if
																test="${adminx=='adminx'|| sgs=='store'}">
																<tr>
																	<td class="L1CItemIcon">
																		&nbsp;
																	</td>
																	<td class="L1CItem1">
																		<a href="javascript:void(0)"
																			onClick="linkUrl('<c:url value='/storage/produceLine.do?method=onMainView' />',0,5)">生产管理</a>
																	</td>
																</tr>
																<tr>
																	<td class="L1CItemIcon">
																		&nbsp;
																	</td>
																	<td class="L1CItem1">
																		<a href="javascript:void(0)"
																			onClick="linkUrl('<c:url value='/storage/adjustmentFrom.do?method=onMainView' />',0,5)">仓库调账</a>
																	</td>
																</tr>																

															</c:if>
														</table>
													</td>
												</tr>
											</table>
										</td>
									</tr>
									</c:if>
									<c:if test="${sgs!='store'&&sgs!='kh'}">
										<tr>
											<td>
												<table width="100%" border="0" cellspacing="0"
													cellpadding="0" class="Tablebody">
													<tr>
														<td>
															<table width="100%" border="0" cellspacing="0"
																cellpadding="0" onClick="open2();" style="cursor: hand;">
																<tr>
																	<td class="L1CTitleIcon">&nbsp;</td>
																	<td class="L1CTitle">
																		财务管理
																	</td>
																	<td class="L1CTitleIconR">
																		<div name="imgFinance" class="L1CTitleIconDown" id="imgFinance" />
																	</td>
																</tr>
															</table>
														</td>
													</tr>
													<tr>
														<td class="L1CBody">
															<table width="100%" border="0" cellpadding="0"
																cellspacing="0" id="financeModel" style="display: none">
																<c:if test="${sgs=='dzgs' || sgs=='sgs' || adminx=='adminx'}">
																<tr>
																	<td class="L1CItemIcon">&nbsp;</td>
																	<td class="L1CItem1">
																		<a href="javascript:void(0)" onClick="linkUrl('<c:url value='/finance/payment.do?method=onMainView' />',0,5)">应收账款</a>
																	</td>
																</tr>
																</c:if>
																<c:if test="${sgs=='sgs' || adminx=='adminx'}">
																<tr>
																	<td class="L1CItemIcon">&nbsp;</td>
																	<td class="L1CItem1">
																		<a href="javascript:void(0)" onClick="query(6);">差价投入</a>
																	</td>
																</tr>
																</c:if>
																<c:if test="${sgs=='dzgs' || sgs=='sgs' || adminx=='adminx'}">
																	<tr>
																		<td class="L1CItemIcon">&nbsp;</td>
																		<td class="L1CItem1">
																			<a href="javascript:void(0)"
																				onClick="linkUrl('<c:url value='/finance/sellinvoice.do?method=onMainView' />',0,2)">销售发票记账</a>
																		</td>
																	</tr>
																</c:if>
																<c:if test="${sgs=='sgs' || adminx=='adminx'}">
																<tr>
																	<td width="20" class="L1CItemIcon">&nbsp;</td>
																	<td class="L1CItem1">
																		<a href="javascript:void(0)" onClick="query(7);">标识费往来账</a>
																	</td>
																</tr>
                                                                <tr>
																	<td class="L1CItemIcon">&nbsp;</td>
																	<td class="L1CItem1">
																		<a href="javascript:void(0)" onClick="query(8);">保证金往来账</a>

																	</td>
																</tr>
																</c:if>
															</table>
														</td>
													</tr>
												</table>
											</td>
										</tr>
										</c:if>
										<!--  -->
										<c:if test="${sgs!='store'}">
										<tr>
											<td>
												<table width="100%" border="0" cellspacing="0"
													cellpadding="0" class="Tablebody">
													<tr>
														<td>
															<table width="100%" border="0" cellspacing="0"
																cellpadding="0" onClick="open3();" style="cursor: hand;">
																<tr>
																	<td class="L1CTitleIcon">&nbsp;</td>
																	<td class="L1CTitle">
																		销售管理
																	</td>
																	<td class="L1CTitleIconR">
																		<div name="imgSell" class="L1CTitleIconDown" id="imgSell" />
																	</td>
																</tr>
															</table>
														</td>
													</tr>
													<tr>
														<td class="L1CBody">
															<table width="100%" border="0" cellpadding="0"
																cellspacing="0" id="sellModel" style="display: none">
																<c:if
																	test="${sgs=='dzgs' || adminx=='adminx' || sgs=='kh' || sgs=='sgs'}">
																	<tr>
																		<td class="L1CItemIcon">&nbsp;</td>
																		<td class="L1CItem1">
																			<a href="javascript:void(0)"
																				onClick="linkUrl('<c:url value='/sell/indent.do?method=onMainView' />',0,3)">订单列表</a>
																		</td>
																	</tr>
																</c:if>
																<tr>
																	<td class="L1CItemIcon">&nbsp;</td>
																	<td class="L1CItem1">
																		<a href="javascript:void(0)" onClick="linkUrl('<c:url value='/sell/plan.do?method=onMainView' />',0,4)">产销列表</a>
																	</td>
																</tr>
																
																<c:if test="${sgs=='kh'}">
																<tr>
																	<td class="L1CItemIcon">&nbsp;</td>
																	<td class="L1CItem1">
																		<a href="javascript:void(0)" onClick="linkUrl('<c:url value='/client/client.do?method=onClienttReceipt' />',0,3)">配送列表</a>
																	</td>
																</tr>
																<tr>
																	<td class="L1CItemIcon">&nbsp;</td>
																	<td class="L1CItem1">
																		<a href="javascript:void(0)"
																			onClick="linkUrl('<c:url value='/storage/outStorageManage.do?method=onMainView' />',0,3)">出库列表</a>
																	</td>
																</tr>
																</c:if>
															</table>
														</td>
													</tr>
												</table>
											</td>
										</tr>
									</c:if>
									<!--基础信息  -->
									<c:if test="${adminx=='adminx' || sgs=='sgs'}">
										<tr>
											<td>
												<table width="100%" border="0" cellspacing="0"
													cellpadding="0" class="Tablebody">
													<tr>
														<td>
															<table width="100%" border="0" cellspacing="0"
																cellpadding="0" onClick="open5();" style="cursor: hand;">
																<tr>
																	<td class="L1CTitleIcon">&nbsp;</td>
																	<td class="L1CTitle">
																		基础信息
																	</td>
																	<td class="L1CTitleIconR">
																		<div name="imgInfo" class="L1CTitleIconDown" id="imgInfo" />
																	</td>
																</tr>
															</table>
														</td>
													</tr>
													<tr>
														<td class="L1BBody">
															<table width="100%" border="0" cellpadding="0"
																cellspacing="0" id="infoModel" style="display: none">

																<tr>
																	<td class="L1CItemIcon">&nbsp;</td>
																	<td class="L1CItem1">
																		<a href="javascript:void(0)"
																			onClick="linkUrl('<c:url value='/products/product.do?method=onProductList' />',0,5)">产品信息</a>
																	</td>
																</tr>
																<tr>
																	<td class="L1CItemIcon">&nbsp;</td>
																	<td class="L1CItem1">
																		<a href="javascript:void(0)"
																			onClick="linkUrl('<c:url value='/products/group.do?method=onGCList' />',0,5)">分组信息</a>
																	</td>
																</tr>
																<tr>
																	<td class="L1CItemIcon">&nbsp;</td>
																	<td class="L1CItem1">
																		<a href="javascript:void(0)"
																			onClick="linkUrl('<c:url value='/products/products.do?method=onPCList' />',0,5)">分类信息</a>
																	</td>
																</tr>
																<tr>
																	<td class="L1CItemIcon">&nbsp;</td>
																	<td class="L1CItem1">
																		<a href="javascript:void(0)"
																			onClick="linkUrl('<c:url value='/client/client.do?method=onClientTonsMain' />',0,5)">吨数配置</a>
																	</td>
																</tr>
																<tr>
																	<td class="L1CItemIcon">
																		&nbsp;
																	</td>
																	<td class="L1CItem1">
																	<a href="javascript:void(0)" onClick="query(1);">标识物料管理</a>
																	</td>
																</tr>
															</table>
														</td>
													</tr>
												</table>
											</td>
										</tr>
									</c:if>
									<!--系统管理  -->
									<c:choose>
										<c:when test="${adminx=='adminx'}">
											<tr>
												<td>
													<table width="100%" border="0" cellspacing="0"
														cellpadding="0" class="Tablebody">
														<tr>
															<td>
																<table width="100%" border="0" cellspacing="0"
																	cellpadding="0" onClick="open4();"
																	style="cursor: hand;">
																	<tr>
																		<td class="L1CTitleIcon">&nbsp;</td>
																		<td class="L1CTitle">
																			系统管理
																		</td>
																		<td class="L1CTitleIconR">
																			<div name="imgSystem" class="L1CTitleIconDown" id="imgSystem" />
																		</td>
																	</tr>
																</table>
															</td>
														</tr>

														<tr>
															<td class="L1CBody">
																<table width="100%" border="0" cellpadding="0"
																	cellspacing="0" id="systemModel" style="display: none">
																	<tr>
																		<td class="L1CItemIcon">&nbsp;</td>
																		<td class="L1CItem1">
																			<a href="javascript:void(0)"
																				onClick="linkUrl('<portal:envget envkey="WEBSER_SECURITY3A"/>/rsinfo/dept/frameIndex.jsp',0,5)">组织机构</a>

																			<!--  人员组织机构-->
																		</td>
																	</tr>
																	<tr>
																		<td class="L1CItemIcon">&nbsp;</td>
																		<td class="L1CItem1">
																			<a href="javascript:void(0)"
																				onClick="linkUrl('<portal:envget envkey="WEBSER_SECURITY3A"/>/rsinfo/dept2/frameIndex.jsp',0,4)">角色权限</a>
																		</td>
																	</tr>
																	<tr>
																		<td class="L1CItemIcon">&nbsp;</td>
																		<td class="L1CItem1">
																			<a href="javascript:void(0)"
																				onClick="linkUrl('<c:url value='/portal/import.jsp' />',0,6)">数据导入</a>
																		</td>
																	</tr>
																	<tr>
																		<td class="L1CItemIcon">&nbsp;</td>
																		<td class="L1CItem1">
																		<a href="javascript:void(0)"
																				onClick="linkUrl('<c:url value='/portal/special.jsp' />',0,9)">其他管理</a>
																		</td>
																	</tr>
																</table>
															</td>
														</tr>
														<!--  -->
													</table>
												</td>
											</tr>
										</c:when>
									</c:choose>

									
								</table>
							</td>

							<td width="15" class="LeftList">
								<img class=LeftImg />
							</td>

							<!-- 综合查询 -->
							<td class="RightTable" height="600">
								<table width="100%" height="600" border="0" cellspacing="0"
									align="center" cellpadding="0">
									<tr>
										<td>
											<table width="100%" border="0" cellspacing="0"
												cellpadding="0">
												<tr>
													<td>
														<table width="100%" border="0" cellspacing="0"
															cellpadding="0" id="qusu">
															<tr>
																<td width="100%" align='left' colspan="23" class="info">
																	${info }
																</td>
															</tr>
															<tr>
																<td width="362">&nbsp;</td>
																<td width="20">&nbsp;</td>

																<td width="10"></td>
																<td width="120" id="queryxs1" style="display: none">
																</td>
																<td width="10"></td>
																<td width="100" id="query1" style="display: none">
																	<table width="100%" border="0" cellspacing="0"
																		cellpadding="0">
																		
																	</table>
																</td>

																<td width="10"></td>
																<td width="170" id="queryx1" style="display: none">
																	<table width="100%" border="0" cellspacing="0"
																		cellpadding="0">
																		<tr>
																			<td class="TabLeftA">
																				&nbsp;
																			</td>
																			<td>
																				<ul id="nav">
																						<li class="TabA" onmouseover="displaySubMenu(this)"
																							onmouseout="hideSubMenu(this)">
																							<a href="javascript:void(0)" id=""
																								class="top_link"><span class="down">标识物料管理</span>
																							</a>
																							<ul class="sub" style="width: 110px">
																								
																									<li>
																										<a href="javascript:void(0)"
																											onClick="linkUrl('<c:url value='/storage/tagPriceInStorage.do?method=onMainView' />',1,1)">标识物料入库</a>
																									</li>
																									<li>
																										<a href="javascript:void(0)"
																											onClick="linkUrl('<c:url value='/storage/tagPriceDamage.do?method=onMainView' />',1,1)">破损登记</a>
																									</li>
																							</ul>
																						</li>
																					</ul>
																				</td>
																			<td class="TabRightA">
																				&nbsp;
																			</td>
																		</tr>
																	</table>
																</td>


																<!-- 配送单 -->
																<td width="10"></td>
																<td width="100" id="query2" style="display: none">
																	<table width="100%" border="0" cellspacing="0"
																		cellpadding="0">

																	</table>
																</td>
																<td width="10"></td>
																<td width="100" id="queryx2" style="display: none">
																	<table width="100%" border="0" cellspacing="0"
																		cellpadding="0">


																	</table>
																</td>
																<td width="10"></td>
																<td width="120" id="queryxs2" style="display: none">
																	<table width="100%" border="0" cellspacing="0"
																		cellpadding="0">
																		<tr>
																			<td class="TabLeftA">&nbsp;</td>
																			<td>
																				<ul id="nav">
																					<li class="TabA" onMouseOver="displaySubMenu(this)"
																						onmouseout="hideSubMenu(this)">
																						<a href="javascript:void(0)" id=""
																							class="top_link"><span class="down">配送管理</span>
																						</a>
																						<ul class="sub" style="width: 110px">
																							<c:if
																								test="${loginType=='CKBGS' || loginType=='CG' || loginType=='STORAGEMANAGER' || adminx=='adminx'}">
																								<li>
																									<a href="javascript:void(0)"
																										onClick="linkUrl('<c:url value='/storage/allocateCargo.do?method=onMainView' />',1,2)">配送单管理</a>
																								</li>
																							</c:if>
																							
																								<li>
																									<a href="javascript:void(0)"
																										onClick="linkUrl('<c:url value='/client/client.do?method=onClienttReceipt' />',1,2)">收货确认</a>
																								</li>
																							
																						</ul>
																					</li>
																				</ul>
																			</td>
																			<td class="TabRightA">&nbsp;</td>
																		</tr>
																	</table>
																</td>
																<!-- 财务模块 右菜单  -->
																<!-- 货款记账开始  -->
																<td width="100" id="queryxs5" style="display: none">
																	<table width="100%" border="0" cellspacing="0"
																		cellpadding="0">
																	</table>
																</td>
																<td width="10"></td>
																<td width="100" id="query5" style="display: none">
																	<table width="100%" border="0" cellspacing="0"
																		cellpadding="0">
																	</table>


																</td>
																<td width="10"></td>
																<td width="100" id="queryx5" style="display: none">
																	<table width="100%" border="0" cellspacing="0"
																		cellpadding="0">
																	</table>
																</td>
																<!-- 货款记账结束 -->
																<!-- 差价投入开始 -->
																<td width="100" id="queryxs6" style="display: none">
																	<table width="100%" border="0" cellspacing="0"
																		cellpadding="0">
																	</table>
																</td>
																<td width="10"></td>
																<td width="100" id="query6" style="display: none">
																	<table width="100%" border="0" cellspacing="0"
																		cellpadding="0">
																	</table>
																</td>

																<td width="10"></td>
																<td width="100" id="queryx6" style="display: none">
																	<table width="100%" border="0" cellspacing="0"
																		cellpadding="0">
																		<c:if test="${sgs=='zgs' || sgs=='dzgs' || sgs=='sgs' || adminx=='adminx'}">
																			<tr>
																				<td class="TabLeftA">&nbsp;</td>
																				<td>
																					<ul id="nav">
																						<li class="TabA"
																							onmouseover="displaySubMenu(this)"
																							onmouseout="hideSubMenu(this)">
																							<a href="javascript:void(0)" id=""
																								class="top_link"><span class="down">记账</span>
																							</a>
																							<ul class="sub" style="width: 110px">
																								<li>
																									<a href="javascript:void(0)"
																										onClick="linkUrl('<c:url value='/finance/incentivePaymentCash.do?method=onMainView' />',1,6)">差价投入兑付记账</a>
																								</li>
																								<li>
																									<a href="javascript:void(0)"
																										onClick="linkUrl('<c:url value='/finance/zpayment.do?method=onMainView' />',1,6)">转线下投入记账</a>
																								</li>
																							</ul>
																						</li>
																					</ul>
																				</td>
																				<td class="TabRightA">&nbsp;</td>
																			</tr>
																		</c:if>
																	</table>
																</td>
																<!-- 差价投入结束 -->
																<!-- 标识费记账 开始 -->
																<td width="100" id="queryxs7" style="display: none">
																	<table width="100%" border="0" cellspacing="0"
																		cellpadding="0">
																	</table>
																</td>

																<td width="10"></td>
																<td width="100" id="query7" style="display: none">

																	<table width="100%" border="0" cellspacing="0"
																		cellpadding="0">
																		
																	</table>

																</td>

																<td width="10"></td>
																<td width="100" id="queryx7" style="display: none">
																	<table width="100%" border="0" cellspacing="0"
																		cellpadding="0">
																		<c:if test="${sgs=='zgs' || sgs=='dzgs' || sgs=='sgs' || adminx=='adminx'}">
																			<tr>
																				<td class="TabLeftA">&nbsp;</td>
																				<td>
																					<ul id="nav">

																						<li class="TabA"
																							onmouseover="displaySubMenu(this)"
																							onmouseout="hideSubMenu(this)">
																							<a href="javascript:void(0)" id=""
																								class="top_link"><span class="down">记账</span>
																							</a>
																							<ul class="sub" style="width: 110px">
																								<li>
																									<a href="javascript:void(0)"
																										onClick="linkUrl('<c:url value='/finance/managePayment.do?method=onMainView' />',1,7)">标识管理收费记账</a>
																								</li>
																								<li>
																									<a href="javascript:void(0)"
																										onClick="linkUrl('<c:url value='/finance/otherPayment.do?method=onMainView' />',1,7)">标识使用费其他记账</a>
																								</li>
																							</ul>
																						</li>
																					</ul>
																				</td>
																				<td class="TabRightA">&nbsp;</td>
																			</tr>
																		</c:if>
																	</table>
																</td>
																<!-- 标识费记账 结束 -->
																<!-- 保证金往开始 -->
																<td width="100" id="queryxs8" style="display: none">
																	<table width="100%" border="0" cellspacing="0"
																		cellpadding="0">
																	</table>
																</td>
																<td width="10"></td>
																<td width="100" id="query8" style="display: none">

																	<table width="100%" border="0" cellspacing="0"
																		cellpadding="0">
																	</table>
																</td>

																<td width="10"></td>
																<td width="100" id="queryx8" style="display: none">
																	<table width="100%" border="0" cellspacing="0"
																		cellpadding="0">
																		<c:if test="${adminx=='adminx' || sgs=='sgs'}">
																			<tr>
																				<td class="TabLeftA">&nbsp;</td>
																				<td>
																					<ul id="nav">
																						<li class="TabA"
																							onmouseover="displaySubMenu(this)"
																							onmouseout="hideSubMenu(this)">
																							<a href="javascript:void(0)" id=""
																								class="top_link"><span class="down">记账</span>
																							</a>
																							<ul class="sub" style="width: 110px">
																								<li>
																									<a href="javascript:void(0)"
																										onClick="linkUrl('<c:url value='/finance/collectionPayment.do?method=onMainView' />',1,8)">收款记账</a>
																								</li>
																								<li>
																									<a href="javascript:void(0)"
																										onClick="linkUrl('<c:url value='/finance/reimbursePayment.do?method=onMainView' />',1,8)">退款记账</a>
																								</li>
																							</ul>
																						</li>
																					</ul>
																				</td>
																				<td class="TabRightA">&nbsp;</td>
																			</tr>
																		</c:if>
																	</table>
																</td>
																<!-- 保证金往来账结束 -->

																<!-- 防止因权限而减少菜单显示，导致出现在多个菜单问题处理方法 -->
																<td width="100" id="queryxs9" style="display: none">
																	<table width="100%" border="0" cellspacing="0"
																		cellpadding="0">
																	</table>
																</td>
																<!-- 产销计划开始 -->
																<!-- 财务查询 -->

																<td width="100" id="queryxs4" style="display: none">
																	<table width="100%" border="0" cellspacing="0"
																		cellpadding="0">
																	</table>
																</td>
																<td width="10"></td>

																<td width="100" id="query4" style="display: none">

																	<table width="100%" border="0" cellspacing="0"
																		cellpadding="0">
																	<c:if test="${sgs=='zgs' || sgs=='dzgs' || sgs=='sgs' || adminx=='adminx'}">
																			<tr>
																				<td class="TabLeftA">&nbsp;</td>
																				<td>
																					<ul id="nav">
																						<li class="TabA">
																							<a href="javascript:void(0)"
																								onClick="linkUrl('<c:url value='/sell/plan.do?method=onMainView' />',1,4)"
																								id="" class="top_link"><span class="down">制单</span>
																							</a>
																						</li>
																					</ul>
																				</td>
																				<td class="TabRightA">&nbsp;</td>
																			</tr>
																		</c:if>
																	</table>

																</td>

																<td width="10"></td>
																<td width="100" id="queryx4" style="display: none">
																	<table width="100%" border="0" cellspacing="0"
																		cellpadding="0">
																		<tr>
																			<td class="TabLeftA">&nbsp;</td>
																			<td>
																				<ul id="nav">
																					<li class="TabA" onMouseOver="displaySubMenu(this)"
																						onmouseout="hideSubMenu(this)">
																						<a href="javascript:void(0)" id=""
																							class="top_link"><span class="down">查询</span>
																						</a>
																						<ul class="sub">
																							<li>
																								<a href="javascript:void(0)"
																									onClick="linkUrl('<c:url value='/sell/plan.do?method=onPlanCollectMainView' />',1,4)">计划明细查询</a>
																							</li>
																							<li>
																								<a href="javascript:void(0)"
																									onClick="linkUrl('<c:url value='/sell/indent.do?method=onShoearRangFrankMainView' />',1,4)">履约率查询</a>
																							</li>
																						</ul>
																					</li>

																				</ul>
																			</td>
																			<td class="TabRightA">&nbsp;</td>
																		</tr>
																	</table>
																</td>

																<!-- 销售查询 -->
																<td width="160px" id="query3" style="display: none">
																	<table width="100%" border="0" cellspacing="0"
																		cellpadding="0">
																		<tr>
																			<td class="TabLeftA">&nbsp;</td>
																			<td>
																				<ul id="nav">
																					<li class="TabA" onMouseOver="displaySubMenu(this)"
																						onmouseout="hideSubMenu(this)">
																						<a href="javascript:void(0)" id=""
																							class="top_link"><span
																							class="down">销售查询</span> </a>
																						<ul class="sub">
																							<li>
																								<a href="javascript:void(0)"
																									onClick="linkUrl('<c:url value='/finance/report2.do?method=onQueryIntegrateDetailView' />',1,3)">销售明细查询</a>
																							</li>
																							<li>
																								<a href="javascript:void(0)"
																									onClick="linkUrl('<c:url value='/finance/report2.do?method=onQueryIntegrateDetailView&specialCondition=unpass' />',1,3)">未审批订单</a>
																							</li>
																							<li>
																								<a href="javascript:void(0)"
																									onClick="linkUrl('<c:url value='/finance/report2.do?method=onQueryIntegrateDetailView&specialCondition=unallocate' />',1,3)">未配送订单</a>
																							</li>
																						</ul>

																					</li>
																				</ul>
																			</td>
																			<td class="TabRightA">&nbsp;</td>
																		</tr>
																	</table>
																</td>
																<td width="10"></td>
																<td width="100px" id="queryx3" style="display: none">
																	<table width="100%" border="0" cellspacing="0"
																		cellpadding="0">
																		<tr>
																			<td class="TabLeftA">&nbsp;</td>
																			<td>
																				<ul id="nav">
																					<li class="TabA" onMouseOver="displaySubMenu(this)"
																						onmouseout="hideSubMenu(this)">
																						<a href="javascript:void(0)" id=""
																							class="top_link"><span class="down">统计</span>
																						</a>
																						<ul class="sub">
																							<li>
																								<a href="javascript:void(0)"
																									onClick="linkUrl('<c:url value='/finance/report2.do?method=onQueryBackPaymentStatDetailView' />',1,3)">销售回款统计</a>
																							</li>
																							<li>
																								<a href="javascript:void(0)"
																									onClick="linkUrl('<c:url value='/finance/report3.do?method=onSellDetailQueryMain' />',1,3)">销售交货统计</a>
																							</li>
																						</ul>
																					</li>
																				</ul>
																			</td>
																			<td class="TabRightA">&nbsp;</td>
																		</tr>
																	</table>
																</td>
																<td width="10"></td>
																<td width="100px" id="queryxs3" style="display: none">
																	<table width="100%" border="0" cellspacing="0"
																		cellpadding="0">
																		<tr>
																			<td class="TabLeftA">&nbsp;</td>
																			<td>
																				<ul id="nav">
																					<li class="TabA" onMouseOver="displaySubMenu(this)"
																						onmouseout="hideSubMenu(this)">
																						<a href="javascript:void(0)" id=""
																							class="top_link"><span class="down">分析</span>
																						</a>
																						<ul class="sub">
																							<li>
																								<a href="javascript:void(0)"
																									onClick="linkUrl('<c:url value='/client/sellTargetNew.do?method=onTargetAnalysisMainView' />',1,3)">销售指标分析</a>
																							<li>
																								<a href="javascript:void(0)"
																									onClick="linkUrl('<c:url value='/finance/report3.do?method=onClientOutStorageQueryMain' />',1,3)">分销商销售分析</a>
																						</ul>
																					</li>

																				</ul>

																			</td>
																			<td class="TabRightA">&nbsp;</td>
																		</tr>
																	</table>
																</td>
															</tr>
														</table>
													</td>
												</tr>
												<tr>
													<td>
														<table width="100%" height="100%" border="0"
															cellspacing="0" cellpadding="0">
															<tr>
																<td class="TABBOTTON">&nbsp;</td>
															</tr>
															<tr>
																<td>
																	<iframe id="contentFrame"
																		src="<c:url value='/workList.do?method=onMainView' />"
																		style="width: 100%; height: 500px;" frameborder="0"
																		scrolling="no"></iframe>
																</td>
															</tr>
														</table>
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<table>
							<tr>
								<td class="Footer" width="1000px">&nbsp;</td>
							</tr>
						</table>
					</table>
					<map name="Map">

						<area shape="rect" coords="916,25,987,61"
							href="<%=basePath%>logoutsvl?gotourl=<%=basePath%>"
							target="_self">

						<area shape="rect" coords="682,24,770,59"
							href="<rs:userinfo/>"
							target="_blank">

						<!-- 帮助文档 -->
						<area shape="rect" coords="781,31,850,61" href="<%=basePath%>/portal/help.jsp" target="_blank" />
					</map>
	</body>
</html>
<script type="text/javascript">
	//加个condition来判断到底是使用了哪个查询模块
	function linkUrl(url,sty,condition){
		var query = document.getElementById('query'+condition);
		var queryhz = document.getElementById('queryx'+condition);
		var queryxs = document.getElementById('queryxs'+condition);
		var qusu=document.getElementById("qusu");
		var all=qusu.getElementsByTagName("table").length/2;
		for(var i=1;i<=all;i++){
			var querya=document.getElementById('query'+i);
			var queryb=document.getElementById('queryx'+i);
			var queryc=document.getElementById('queryxs'+i);
			if(querya!=null){
				querya.style.display = 'none';
				queryb.style.display = 'none';
				queryc.style.display = 'none';
			}
			
		}
		if(sty==1){
		    if(query!=null){
		    	query.style.display = 'block';
				queryhz.style.display = 'block';
				queryxs.style.display = 'block';
		    }
			document.getElementById('contentFrame').src=url;
		}else{
			document.getElementById('contentFrame').src=url;
			if(query!=null){
			query.style.display = 'none';
			queryhz.style.display = 'none';
			queryxs.style.display = 'none';
			}
		}
	}
	function onload1(){
		var storageModel = document.getElementById('storageModel');
	}
	
	/*function storageQuery(){
		*var query = document.getElementById('query');
		*var queryhz = document.getElementById('queryhz');
		*document.getElementById('contentFrame').src='';
		*query.style.display = 'block';
		*queryhz.style.display = 'block';
	}*/
	
	//根据条件来选择到底执行哪个dom,条件为数字,这里的财务为2
	//condition:代表模块号
	//请按顺序标记condition，比如1，2，3，4，5这样的
	function query(condition){
		var qusu=document.getElementById("qusu");
		var all=qusu.getElementsByTagName("table").length/3;
		for(var i=1;i<=all;i++){
			var query=document.getElementById('query'+i);
			var queryx=document.getElementById('queryx'+i);
			var queryxs=document.getElementById('queryxs'+i);
			query.style.display = 'none';
			queryx.style.display = 'none';
			queryxs.style.display = 'none';
		}
		var query=document.getElementById('query'+condition);
		var queryx=document.getElementById('queryx'+condition);
		var queryxs=document.getElementById('queryxs'+condition);
		document.getElementById('contentFrame').src="<c:url value='watermark.jsp' />";
		query.style.display = 'block';
		queryx.style.display = 'block';
		queryxs.style.display = 'block';
	}
	function open1(){
		var open1= document.getElementById('storageModel');
		var img1 = document.getElementById('imgStorage');
		if(open1.style.display == 'none'){
			open1.style.display='block';
			img1.className="L1CTitleIconUp";
		}else{
			open1.style.display='none';
			img1.className="L1CTitleIconDown";
		}
	}
	function open2(){
		var open2= document.getElementById('financeModel');
		var img2 = document.getElementById('imgFinance');
		if(open2.style.display == 'none'){
			open2.style.display='block';
			img2.className="L1CTitleIconUp";
		}else{
			open2.style.display='none';
			img2.className="L1CTitleIconDown";
		}
	}
	
	function open3(){
		var open3= document.getElementById('sellModel');
		var img3 = document.getElementById('imgSell');
		if(open3.style.display == 'none'){
			open3.style.display='block';
			img3.className="L1CTitleIconUp";
		}else{
			open3.style.display='none';
			img3.className="L1CTitleIconDown";
		}
	}
	
	function open4(){
		var open4= document.getElementById('systemModel');
		var img4 = document.getElementById('imgSystem');
		if(open4.style.display == 'none'){
			open4.style.display='block';
			img4.className="L1CTitleIconUp";
		}else{
			open4.style.display='none';
			img4.className="L1CTitleIconDown";
		}
	}

	function open5(){
		var open5= document.getElementById('infoModel');
		var img5= document.getElementById('imgInfo');
		if(open5.style.display == 'none'){
			open5.style.display='block';
			img5.className="L1CTitleIconUp";
		}else{
			open5.style.display='none';
			img5.className="L1CTitleIconDown";
		}
	}
	
	function open6(){
		var open6= document.getElementById('personModel');
		var img6 = document.getElementById('imgPerson');
		if(open6.style.display == 'none'){
			open6.style.display='block';
			img6.className="L1CTitleIconUp";
		}else{
			open6.style.display='none';
			img6.className="L1CTitleIconDown";
		}
		
	}
	
	function open8(){
		var open8= document.getElementById('personModel1');
		var img8 = document.getElementById('imgPerson1');
		
		if(open8.style.display == 'none'){
			open8.style.display='block';
			img8.className="L1CTitleIconUp";
		}else{
			open8.style.display='none';
			img8.className="L1CTitleIconDown";
		}
		
	}
	
	function open7(){
		var open7= document.getElementById('clientModel');
		var img7 = document.getElementById('imgClient');
		if(open7.style.display == 'none'){
			open7.style.display='block';
			img7.className="L1CTitleIconUp";
		}else{
			open7.style.display='none';
			img7.className="L1CTitleIconDown";
		}
		
	}
	function displaySubMenu(li) {
		var subMenu = li.getElementsByTagName("ul")[0];
		subMenu.style.display = "block";
	}
	function hideSubMenu(li) {
		var subMenu = li.getElementsByTagName("ul")[0];
		subMenu.style.display = "none";
	}
</script>
