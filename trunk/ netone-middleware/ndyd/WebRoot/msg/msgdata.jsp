<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:forEach var="list" items="${msglist}">
	<li>
		<div>${list.sendername}</div>
		<div>${list.context}</div>
		<div>
			<c:if test="${!empty list.atturl}">
				<a href="${list.atturl}" target="_blank" title="打开原图">
				<img width="160" height="160" src="${list.atturl}" />
				</a>
			</c:if>
			<BR>
			<c:if test="${!empty list.atturlzip}">
              	<a href="${list.atturlzip}">[附件下载]</a>
              	</c:if>
		</div>
		<div class="pubInfo">
                      <span style="float: left">
                      ${list.timex}
                      </span>
                      <div  style="float: right">
                      <c:if test="${param.type!='05'}">
                      <a num="0" class="relay" href="javascript:void(0)" onclick="openForwardWin()">转发</a><span>|</span>
                      <a num="0" class="comt" href="javascript:void(0)" onclick="openCommentWin()">评论</a><span>|</span>
                      </c:if>
                      <c:if test="${empty param.type || param.type=='01' ||  param.type=='05'}">
                      <a num="0" class="comt" href="javascript:void(0)" onclick="$del('${list.lsh}')">删除</a><span>|</span>
                      </c:if>
                      </div>
              </div>
	</li>
</c:forEach>
