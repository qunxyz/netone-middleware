<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
	<head>
		<base href="<%=basePath%>">
		<title>�½��ӽڵ�</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css" href="rsinclude/css/css.css">
		<script type="text/javascript" src="rsinclude/pagelist/pagelist.js"></script>
		<script type="text/javascript" src="include/js/quartzlist.js"></script>
		<script type="text/javascript" src="include/js/calendar.js"></script>
		<script type="text/javascript">
		
		//����ѡ��ҳ��
		function searchtree() {
			window.open("SelectSvl?appname=BUSSENV&pagename="+document.all.pagename.value,'_blank', 'height=380, width=600, top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no');
		}
		//ִ�е�����ڵ����
		function addSelectedOuteruse(name, id, ou, naturalname, parentdir, url, appid){
			document.all.objecttype.value = name+"["+naturalname+"]";
		}
		</script>
	</head>
	<body style="font-size: 12px;margin: 22px">
		<c:if test="${CreateSuccess == 'y'}">
			<script type="text/javascript">
				alert("�½��ɹ���")
				opener.search();
			</script>
		</c:if>
		<c:if test="${CreateSuccess == 'n'}">
			<script type="text/javascript">
				alert("${errorinfo}");
			</script>
		</c:if>
		<div style="width: 100%;height: 100%">
			<form action="PagelistAddSvl?task=addsave" method="post" name="createForm">&nbsp; 
				<input type="hidden" name="pagename" value="${pagename}" />
				<input type="hidden" name="id" value="${id}" />
				<input type="hidden" name="appid" value="${appid}" />
				<input type="hidden" name="ou" value="${ou}" />
				<input type="hidden" name="inclusion" value="${inclusion}" />
				<table width="96%" border="0" align="center" cellpadding="0"
					cellspacing="1" id="lie_table">
					<tr>
						<td width="15%">
							��&nbsp;&nbsp;&nbsp;&nbsp;Ч
						</td>
						<td>
							<input type="checkbox" name="active" value="1" checked />
						</td>
					</tr>
					<tr>
						<td width="15%">
							��&nbsp;&nbsp;&nbsp;&nbsp;��
						</td>
						<td>
							<input type="text" name="naturalname" value=""
								class="textinput_td" />
						</td>
					</tr>
					<tr>
						<td width="15%">
							��������
						</td>
						<td>
							<input type="text" name="name" value="" class="textinput_td" />
						</td>
					</tr>
					<tr>
						<td width="15%">
							<a href="javascript:searchtree();"><font color='blue'>ѡ�����</font>
							</a>
						</td>
						<td width='300'>
							<input type="text" name="objecttype" value=""
								class="textinput_td" readonly="readonly">
						</td>
					</tr>
					<tr>
						<td width="15%">
							����
						</td>
						<td>
							<input type="text" name="actionurl" id="actionurl" value="" class="textinput_td" />
						</td>
					</tr>
					
					
					<tr>
						<td width="15%">
						    <textarea rows="8" cols="60" id="extendattribute" name="extendattribute" style="display:none"></textarea>
							����ʱ��
						</td>
						<td>
						    &nbsp;&nbsp;&nbsp;����ʱ�䣺<input type="text" name="startTime" id="startTime" size="25" onfocus="calendar()">
						    <script type="text/javascript">
						        //��ʼ������ʱ��
						        var now=new Date();
						        document.getElementById("startTime").value=now.getFullYear()+"-"+(now.getMonth()+1)+"-"+now.getDate()+" "+now.getHours()+":"+now.getMinutes()+":"+now.getSeconds();
						    </script>
						</td>
					</tr>
					<tr style="line-height:200%">
					    <td width="15%">
					        ������ʽ
					    </td>
					    <td>
					       <br>&nbsp;&nbsp;&nbsp;&nbsp;
					       <input type="radio" name="chooseByRadio" value="chooseByDay" onclick="chooseByDay()" checked="checked">����ѡ��&nbsp;&nbsp;&nbsp;&nbsp;
					       <input type="radio" name="chooseByRadio" value="chooseByWeek" onclick="chooseByWeek()">������ѡ��&nbsp;&nbsp;&nbsp;&nbsp;
					       <input type="hidden" name="chooseMode" id="chooseMode" value="chooseByDay">
					       <br><br>
					       &nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="startYear" id="startYear" value="0" size="5" onkeyup="value=value.replace(/[^\d]/g,'');changeMonth();"onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))" maxlength="4" onchange="changeMonth()">
					       <script type="text/javascript">
					           //Ĭ����Ϊ��ǰ��
					           document.getElementById("startYear").value=now.getFullYear();
					       </script>
					       &nbsp;��&nbsp;&nbsp;&nbsp;&nbsp;
					       
					       <select name="startMonth" id="startMonth" onchange="changeMonth()">
					               <option value="*" checked="checked">ÿ&nbsp;&nbsp;</option>
					           <c:forEach begin="1" end="12" step="1" var="totalMonth">
					               <option value="${totalMonth}">${totalMonth}</option>
					           </c:forEach>
					       </select>
					       ��&nbsp;
					       
					       <span style="display:" id="selectDaySpan">
					       &nbsp;&nbsp;
					       <select name="startDay" id="startDay">
					               <option value="*" checked="checked">ÿ&nbsp;&nbsp;</option>
					               <c:forEach begin="1" end="28" step="1" var="totalDay">
					                   <option value="${totalDay}">${totalDay}</option>
					               </c:forEach>
					       </select>��</span>
					       
					      <span style="display:none" id="selectWeekSpan">
					       ��<select name="startNumWeek" id="startNumWeek">
					           <option value="1">1</option>
					           <option value="2">2</option>
					           <option value="3">3</option>
					           <option value="4">4</option>
					           <option value="5">5</option>
					       </select>
					      ������<select name="startWeek" id="startWeek">
					           <option value="1">��</option>
					           <option value="2">һ</option>
					           <option value="3">��</option>
					           <option value="4">��</option>
					           <option value="5">��</option>
					           <option value="6">��</option>
					           <option value="7">��</option>
					       </select>
					       
					      </span>
					       <br><br>
					       &nbsp;&nbsp;&nbsp;
					       <select name="startHour" id="startHour">
					           <option value="*">ÿ&nbsp;&nbsp;</option>
					           <c:forEach begin="00" end="23" step="1" var="hourOption">
					               <option value="${hourOption}">${hourOption}</option>
					           </c:forEach>
					       </select>
					       ʱ&nbsp;&nbsp;&nbsp;&nbsp;
					       
					      <select name="startMinute" id="startMinute">
					           <option value="*">ÿ&nbsp;&nbsp;</option>
					           <c:forEach begin="00" end="59" step="1" var="minuteOption">
					               <option value="${minuteOption}">${minuteOption}</option>
					           </c:forEach>
					       </select>
					       ��&nbsp;&nbsp;&nbsp;&nbsp;
					       
					      <select name="startSecond" id="startSecond">
					           <option value="*">ÿ&nbsp;&nbsp;</option>
					           <c:forEach begin="00" end="59" step="1" var="secondOption">
					               <option value="${secondOption}">${secondOption}</option>
					           </c:forEach>
					       </select>
					       ��&nbsp;&nbsp;
					       
					       <script type="text/javascript">
					           document.getElementById("startHour").value="8";
					           document.getElementById("startMinute").value="0";
					           document.getElementById("startSecond").value="0";
					       </script>
					       <br><br>
					    </td>
					</tr>
					<tr>
					    <td width="15%">
					        ����ʱ��
					    </td>
					    <td>
					        <input type="hidden" id="stopMode" value="stopByCount">
					        <div style="display:" id="stopByTimeDiv">
					            &nbsp;&nbsp;&nbsp;����ʱ�䣺<input type="text" name="stopTime" id="stopTime" size="25" onfocus="calendar()">
					            <script type="text/javascript">
						            document.getElementById("stopTime").value=now.getFullYear()+"-"+(now.getMonth()+1)+"-"+now.getDate()+" "+now.getHours()+":"+(now.getMinutes()+1)+":"+"00";
						        </script>
					            <br>
					        </div>
					    </td>
					</tr>
					<tr>
					    <td width="15%">
					        �־û�
					    </td>
					    <td>
					        <input type="checkbox" name="needSerilaizer" value="1" />�־û�
					    </td>
					</tr>

					<tr>
						<td width="15%">
							����
						</td>
						<td>
							<textarea rows="4" cols="60" name="description"></textarea>
						</td>
					</tr>
				</table>
				<br>
				<div align="center">
					<input type="button" name="btnnew" value="�� ��" onclick="create();"
						class="butt">
					<input type="button" name="btncancel" value="ȡ ��"
						onclick="javascript:window.close();" class="butt">
				</div>
			</form>
		</div>
	</body>
</html>
