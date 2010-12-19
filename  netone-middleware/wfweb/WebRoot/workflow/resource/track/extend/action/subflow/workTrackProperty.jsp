<%@ page contentType="text/html; charset=GB2312"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%String path = request.getContextPath();%>
<html>
	<head>
		<link href="../../../../../../include/css/workflowtrack/style.css
" rel="stylesheet" type="text/css">

		<script src="../../../../../../include/js/workflowtrack/action/workTrackProperty.js"></script>
		<title>
			定义节点属性
		</title>
	</head>
	<body onLoad="(new event()).getAttribute();new condition()">
	<input id="pathinfo" name="pathinfo" type="hidden" value=<%=path%>>
		<div>
			<table  width="310" border="0"  cellpadding="0" cellspacing="0" height="27" style="background-image:url(../../../../../../image/wf/lct.jpg);border:1px solid #398ecf;">
				<tr>
					<td align="left">
						<input id="buttonBasic" class=Menu style="color:#132c7c;border:none; height:25; width:61;background-image:url(../../../../../../image/wf/titChange.jpg);"  type="submit" name="Submit1" value="基本属性 " onclick="button('basic')">
						<input id="buttonForwardCondition" class=MenuS style="color:#132c7c;border:none; height:25; width:61;background-image:url(../../../../../../image/wf/lct.jpg);"  type="submit" name="Submit5" value="汇聚条件" onclick="button('forwardCondition');">
						<input id="buttonAfterCondition" class=MenuS style="color:#132c7c;border:none; height:25; width:61;background-image:url(../../../../../../image/wf/lct.jpg);"  type="submit" name="Submit4" value="分支条件" onclick="button('afterCondition');">
						<input id="buttonExtended" class=MenuS style="color:#132c7c;border:none; height:25; width:61;background-image:url(../../../../../../image/wf/lct.jpg);"  type="submit" name="Submit3" value="子流程" onclick="button('extended')">
					</td>
				</tr>
			</table>
		</div>
		<div id="extended" align="left" class="divNone">
			<table width="330" border="0" align="center" cellpadding="0" cellspacing="0">
				<br>
				<tr>
					<td height="135" colspan="2" align="center">
						<table width="100%" height="86%" border="1" cellpadding=0 cellspacing=0>
							<tr>
								<td width="272" height="133" valign="top">
									<table width="100%" border="1" cellpadding=0 cellspacing=0 bgcolor="#CCCCCC" style=" BORDER-COLLAPSE: collapse">
										<tr>
											<td width="40%">
												<div align="center">
													子流程属性
												</div>
											</td>
											<td width="60%">
												<div align="center">
													属性值
												</div>
											</td>
										</tr>
									</table>
									<IFRAME frameBorder=0 id=eAttributeList name=elistFrame src="extendAttributeList.html" style="HEIGHT: 100%; VISIBILITY: inherit; WIDTH: 100%; Z-INDEX: 1"></IFRAME>
								</td>
								<td width="58" align="center" valign="top">
									<label>
										<input type="submit" name="Submit3" value="选择" onclick="subflow();">
									</label>
									<br>
									<input type="submit" name="Submit4" value="删除" onclick="deleteSubflowExtend();">
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<div align="center">
							<input type="button" name="Submit" value="提交" onClick="(new event()).setAttribute();">
							&nbsp;

							<input type="button" name="Submit" value="关闭" onClick="self.close();">
						</div>
					</td>
				</tr>
			</table>
		</div>

		<div id="basic" class="div">
			<table width="330" height="165" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td align="center">
						类型：
					</td>
					<td>
						子流程
					</td>
				</tr>
				<tr>
					<td width="120" align="center">
						标 识：
					</td>
					<td width="230">
						<input id="actionId" type="text" name="textfield">
					</td>
				</tr>
				<tr>
					<td align="center">
						名 字：
					</td>
					<td>
						<input id="actionName" type="text" name="textfield2">
					</td>
				</tr>
				<tr>
				
						<input id="actionType" type="hidden" name="textfield3" readOnly>
			
				</tr>

				<input id="deadline" type="hidden" name="textfield4">

				<tr>
					<td>
						&nbsp;
					</td>
					<td align="right">
						<div align="left">
							<input type="button" name="Submit" value="提交" onClick="(new event()).setAttribute();">
						
							<input type="button" name="Submit" value="关闭" onClick="self.close();">
						</div>
					</td>
				</tr>
			</table>
		</div>

		<div id="afterCondition" class="divNone">
			<table width="330" height="165" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td width="76" align="center">
						&nbsp;
					</td>
					<td width="81">
						&nbsp;
					</td>
					<td width="95">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td align="center">
						分支类型
					</td>
					<td>
						<select id="afterConditionId">
							<option value="XOR">
								异步
							</option>
							<option value="AND">
								同步
							</option>
						</select>
					</td>
					<td>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
					<td>
						&nbsp;
					</td>
					<td>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
					<td colspan="2" align="right">
						<div align="left">
							<input type="button" name="Submit" value="提交" onClick="(new event()).setAttribute();">
						
							<input type="button" name="Submit" value="关闭" onClick="self.close();">
						</div>
					</td>
				</tr>
			</table>
		</div>

		<div id="forwardCondition" class="divNone">
			<table width="330" height="165" border="0" align="center" cellpadding="0" cellspacing="0" bordercolor="#9BD07B" bgcolor="#E8E8E8" style="font-size:12px;">
				<tr>
					<td width="76" align="center">
						&nbsp;
					</td>
					<td width="81">
						&nbsp;
					</td>
					<td width="95">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td align="center">
						汇集类型
					</td>
					<td>
						<select id="forwardConditionId">
							<option value="XOR">
								异步汇聚
							</option>
							<option value="AND">
								同步汇聚
							</option>
						</select>
					</td>
					<td>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
					<td>
						&nbsp;
					</td>
					<td>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
					<td colspan="2" align="right">
						<div align="left">
							<input type="button" name="Submit" value="提交" onClick="(new event()).setAttribute();">
						
							<input type="button" name="Submit" value="关闭" onClick="self.close();">
						</div>
					</td>
				</tr>
			</table>
		</div>
	</body>
<html>