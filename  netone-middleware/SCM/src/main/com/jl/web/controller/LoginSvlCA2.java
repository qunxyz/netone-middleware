package com.jl.web.controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import oe.frame.web.WebCache;
public class LoginSvlCA2 extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String basePath = request.getScheme() + "://"
		+ request.getServerName() + ":" + request.getServerPort();
		String textCert; // 证书号
		String textOriginData;// 原文(随机数)
		String textSignData; // 签名
		HttpSession httpSession = request.getSession();
		WebCache.setCache("textCert", request.getParameter("textCert"), null);
		WebCache.setCache("textOriginData", (String) httpSession.getAttribute("RANDOM"), null);
		WebCache.setCache("textSignData", request.getParameter("textSignData"), null);
		WebCache.setCache("action", request.getParameter("action"), null);
		response.sendRedirect(basePath+"/netone");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}