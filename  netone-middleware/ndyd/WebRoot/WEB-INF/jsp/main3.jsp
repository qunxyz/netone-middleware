<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.oesee.com/netone" prefix="rs"%>
<%@ taglib uri="http://www.oesee.com/netone/portal" prefix="portal"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%String path = request.getContextPath();

	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<jsp:include page="common/metaJQuery-min.jsp"></jsp:include>
		<jsp:include page="common/metaJQuery-easyui.jsp"></jsp:include>
		<script type="text/javascript" src="<%=path%>/script/AppUtil.js"></script>
		<!--[if lte IE 6]>
		<script type="text/javascript" src="<%=path%>/script/belatedPNG.js"></script>
		<![endif]-->
		<script type="text/javascript" src="<%=path%>/script/jquery-plugin/jquery.blockUI.js"></script>
		<title></title>
		<style>
		.close {  display:block; width:16px; height:16px; background:url(images/appImage/btn/cancel.png) no-repeat;outline:none; }
		</style>
		<!--[if lte IE 6]>
		<script type="text/javascript">
		if(!window.XMLHttpRequest) {
			// 解决IE6透明PNG图片BUG
			DD_belatedPNG.fix(".png");
			// 解决IE6不缓存背景图片问题
			document.execCommand("BackgroundImageCache", false, true);
		}		
		</script>
		<![endif]-->
		<script type="text/javascript">
		function GetRandomNum(Min,Max){  
	        var Range = Max - Min;  
	        var Rand = Math.random();  
	        return(Min + Math.round(Rand * Range));  
		}
		var welcomePic = new Array();//欢迎图片数据
		var picRoot = '<%=path%>/images/welcome/';
		
		welcomePic[0] = picRoot+'zy01.jpg';
		welcomePic[1] = picRoot+'zy02.jpg';
		welcomePic[2] = picRoot+'zy03.jpg';
		
		if($.browser.msie){//IE
		    var height = 0;
		    var width = 0;
			if( document.documentElement && ( document.documentElement.clientWidth || document.documentElement.clientHeight ) ) {
			    height = document.documentElement.clientHeight;
			    width = document.documentElement.clientWidth;
			} else if( document.body && ( document.body.clientWidth || document.body.clientHeight ) ) {
			    height = document.body.clientHeight;
			    width = document.body.clientWidth;
		    }
			var leftW = width/2-50;   
			var top = height/2;
			
			var _html = "<div id='___loading' style='position:absolute;left:0;width:100%;height:"+height+"px;top:0;background:#E0ECFF;opacity:0.8;filter:alpha(opacity=80);'><div style='position:absolute;  cursor1:wait;left:"+leftW+"px;top:"+top+"px;width:auto;height:16px;padding:12px 5px 10px 30px;border:2px solid #ccc;color:#000;'>正在加载，请稍候...</div></div>";   
			    
			window.onload = function(){
			 	var _mask = document.getElementById('___loading');   
	    		_mask.parentNode.removeChild(_mask);   
			}
			document.write(_html);  
		}
    	$(function(){
     	   ${hiddenBar}
    	    /**
    	    var w=600,h=400;
    	    var welcomeLogo = '<div><div align=\"right\"><a href=\"#\" class=\"close\" title=\"关闭\"></a></div><img src=\"'+welcomePic[GetRandomNum(0,welcomePic.length-1)]+'\" />'
    	    welcomeLogo += '<div>正在加载界面...</div></div>'
    		$.blockUI({
    		message: welcomeLogo,
    		overlayCSS:{backgroundColor: '#fff'},
    		css:{
	    		top:($(window).height()-h)/2+'px',
	    		left:($(window).width()-w)/2+'px',
	    		width:w+'px',
	    		border: 'none', 
	    		background: 'none' 
    		}
    		});
    		$('.blockOverlay').attr('title','单击关闭').click($.unblockUI);   //遮罩层属性的设置以及当鼠标单击遮罩层可以关闭弹出层  
    		$('.close').click($.unblockUI);
    		**/
    		
     	   if(!$.browser.msie){ //not IE
     	    // 在FF/Linux下启用遮罩层透明
     	    $.blockUI.defaults.applyPlatformOpacityRules = false;
    	    $.blockUI({message: '<div style=\"padding:12px 5px 10px 30px;height:16px\">正在加载，请稍候...</div>',css:{border:'2px solid #ccc'},overlayCSS:{backgroundColor: '#E0ECFF',opacity:.8}});		   	
     	   }
    		    		    		
   	   	    var mainTheme=getCookie('mainTheme');
	   	    var appTheme=getCookie('appTheme');
	   	    if(mainTheme==null || mainTheme==''){
			    // mainTheme='brown';
			    mainTheme='blue';
		     }
		     if(appTheme==null || appTheme==''){
			     //appTheme='brown';
			     appTheme='blue';
		      } 
			AppStyleUtils.swapStyleSheet('mainTheme', '<%=path%>/script/theme/jtheme/'+ mainTheme+'/style.css');
			AppStyleUtils.swapStyleSheet('appTheme', '<%=path%>/script/theme/main/'+appTheme+'/style.css');
    		
	    	$('#theme').combobox({
			    'onSelect':function(record){
			    	var theme = record.value.split('|');
				    var a = new Date();
					a.setDate(a.getDate() + 300);
					var url = "<%=path%>";
					var extTheme = theme[0];
					var appTheme = theme[1];
					var mainTheme = theme[2];
					setCookie("extTheme", extTheme, a, url);
					setCookie("appTheme", appTheme, a, url);
					setCookie("mainTheme", mainTheme, a, url);
					AppStyleUtils.swapStyleSheet('mainTheme', '<%=path%>/script/theme/jtheme/'+ mainTheme+'/style.css');
					AppStyleUtils.swapStyleSheet('appTheme', '<%=path%>/script/theme/main/'+appTheme+'/style.css');
					var o = document.getElementById("contentFrame");
					if(o.contentWindow.AppStyleUtils) o.contentWindow.AppStyleUtils.swapStyleSheet('extTheme', '<%=path%>/script/ext/resources/css/'+ extTheme);
			    }
			});
			if(!$.browser.msie){ //not IE
				$.unblockUI();
			}
		})
		</script>
	</head>
	<body class="easyui-layout">
		<div region="north" style="height: 68px; overflow: hidden;">
			<div id="app-header">
				<div id="header-left">
				</div>
				<div id="header-right">
					<div id="setting">
						<a href="<%=path%>/portal/help.jsp" target="blank">帮助</a> |
						<a href="<%=basePath %>/logoutsvl?gotourl=<%=basePath%>" >注销</a>
					</div>
				</div>
			</div>
		</div>
		<div region="west" style="width: 200px; padding1: 1px; overflow: hidden;" spilt="true" title="功能菜单" iconCls="icon-company_menu"><!-- 第一层 -->
			<div class="easyui-accordion" fit="true" border="false"><!-- 第二层 -->

				<div title="首页" selected="true" class="LBody">
					<ul id="tt1" class="easyui-tree" animate="true" dnd="true">
						<li>
							<span><a href="javascript:void(0)"
								onClick="linkUrl('待办任务','<portal:envget envkey="WEBSER_WFWEB"/>/workList.do?method=onMainView')">待办任务</a>
							</span>
						</li>
					</ul>
				</div>
				
				
				<rs:permission action="7" resource="BUSSENV.BUSSENV.SECURITY.ROLE.ZY">
				<div title="基础信息" class="LBody">
					<ul id="tt1" class="easyui-tree" animate="true" dnd="true">
						<li>
							<span><a href="javascript:void(0)"
								onClick="linkUrl('组织机构','<c:url value='/department/department.do?method=onDepartmentManageView' />')">组织机构</a></span>
						</li>
						<li>
							<span><a href="javascript:void(0)"
								onClick="linkUrl('组织机构-查看','<c:url value='/department/department.do?method=onDepartmentView' />')">组织机构-查看</a></span>
						</li>
					</ul>
				</div>
				</rs:permission>

				<rs:permission action="31" resource="BUSSENV.BUSSENV.SECURITY.ROLE.ZY">
				<div title="系统管理" class="LBody">
					<ul id="tt1" class="easyui-tree" animate="true" dnd="true">
						<li>
							<span><a href="javascript:void(0)"
								onClick="linkUrl('组织机构','<portal:envget envkey='WEBSER_SECURITY3A'/>/rsinfo/dept/frameIndex.jsp')">组织机构</a></span>
						</li>
						<li>
							<span><a href="javascript:void(0)"
								onClick="linkUrl('角色权限','<portal:envget envkey='WEBSER_SECURITY3A'/>/rsinfo/dept2/frameIndex.jsp')">角色权限</a></span>
						</li>
						<li>
							<span><a href="javascript:void(0)"
								onClick="linkUrl('数据导入','<c:url value='/portal/import.jsp' />')">数据导入</a></span>
						</li>
						<li>
							<span><a href="javascript:void(0)"
								onClick="linkUrl('其它管理','<c:url value='/portal/special.jsp' />')">其它管理</a></span>
						</li>
					</ul>
				</div>
				</rs:permission>
				<div title="资源资产管理" class="LBody">
					<ul id="tt1" class="easyui-tree" animate="true" dnd="true">
						<li>
							<span><a href="javascript:void(0)"
								onClick="linkUrl('财务物购','<c:url value='/shopping/shopping.do?method=onMainView' />')">财务物购</a></span>
						</li>
						
					</ul>
				</div>
				<div title="公司应用" class="LBody">
					<ul id="tt1" class="easyui-tree" animate="true" dnd="true">
						<li>
							<span><a href="javascript:void(0)"
								onClick="linkUrl('集团终端个性化审批流程','<c:url value='/groupTerminal/groupTerminal.do?method=onMainView' />')">集团终端个性化审批流程</a></span>
						</li>
						
					</ul>
				</div>
				<div title="效能督办" class="LBody">
					<ul id="tt1" class="easyui-tree" animate="true" dnd="true">
						<li>
							<span><a href="javascript:void(0)"
								onClick="linkUrl('待办效能督办工单','<c:url value='/censorship/censorship.do?method=onMainView&status=0' />')">待办效能督办工单</a></span>
						</li>
						<li>
							<span><a href="javascript:void(0)"
								onClick="linkUrl('已处理效能督办工单','<c:url value='/censorship/censorship.do?method=onMainView&status=1' />')">已处理效能督办工单</a></span>
						</li>
						<li>
							<span><a href="javascript:void(0)"
								onClick="linkUrl('已办结效能督办工单','<c:url value='/censorship/censorship.do?method=onMainView&status=2' />')">已办结效能督办工单</a></span>
						</li>
						<li>
							<span><a href="javascript:void(0)"
								onClick="linkUrl('所有效能督办工单','<c:url value='/censorship/censorship.do?method=onMainView&status=' />')">所有效能督办工单</a></span>
						</li>
					</ul>
				</div>
			</div><!-- 第二层 -->  
		</div><!-- 第一层 -->
		<div region="center" title="&nbsp;" iconCls="icon-destop">
			<rs:noPermission action="15" resource="BUSSENV.BUSSENV.SECURITY.ROLE.ZY">
			<iframe id="contentFrame" src="<portal:envget envkey="WEBSER_QUICKACCESS"/>" style="width: 100%; height: 100%;"
				frameborder="0" scrolling="auto"></iframe>
			</rs:noPermission>
			<rs:permission action="15" resource="BUSSENV.BUSSENV.SECURITY.ROLE.ZY">
				<iframe id="contentFrame" src="<c:url value='/map.do?method=onQMap' />" style="width: 100%; height: 100%;"
				frameborder="0" scrolling="auto"></iframe>
			</rs:permission>
			
			
		</div>
		<div region="south" style="height: 30px; padding: 2px;" class="foot">
			主题:<!-- ext样式|main样式|jquery样式 -->
			<select id="theme" class="easyui-combobox" name="theme">
				<option value="xtheme-black.css|red|black" selected="selected">默认</option>
				<option value="xtheme-brown.css|brown|brown">黄金时代</option>
				<option value="xtheme-olive.css|olive|olive">绿色生机</option>
				<option value="xtheme-green.css|green|green">清秀淡雅</option>
				<option value="ext-all.css|blue|blue">蓝色</option>
				<option value="xtheme-pink.css|pink|pink">粉色</option>
				<option value="xtheme-chocolate.css|chocolate|chocolate">巧克力</option>
				<option value="xtheme-gray.css|blue|gray">灰-蓝</option>
				<option value="xtheme-gray.css|brown|gray">灰-黄</option>
				<option value="xtheme-gray.css|red|gray">灰-红</option>
				<option value="xtheme-gray.css|pink|gray">灰-粉</option>
				<option value="xtheme-gray.css|chocolate|gray">灰-巧克力</option>
				<option value="xtheme-pink.css|brown|pink">粉-黄</option>
				<option value="xtheme-pink.css|red|pink">粉-红</option>
				<option value="xtheme-chocolate.css|brown|chocolate">巧克力-黄</option>
				<option value="xtheme-chocolate.css|red|chocolate">巧克力-红</option>
				<option value="xtheme-black.css|brown|black">黑-黄</option>
				<option value="xtheme-silverCherry.css|brown|silverCherry">红-白-黄</option>
				<option value="xtheme-silverCherry.css|red|silverCherry">红-白-红</option>
			</select>
			&nbsp;&nbsp;
			${info}
			<a href="<%=basePath %>/logoutsvl?gotourl=<%=basePath %>" >[注销]</a>
				<a href='<rs:changepassword/>' target='_blank'>【修改密码】</a>
	<a href='<rs:userinfo/>' target='_blank'>【个人信息】</a>
		</div>
	</body>
</html>
<script type="text/javascript">
	function linkUrl(title,url){
		if (isie6()){
			var load = function(){document.getElementById("contentFrame").src=url;}
			setTimeout(load,10);
		} else {
			document.getElementById("contentFrame").src=url;
		}
		$('.easyui-layout').layout('panel', 'center').panel({ title: title});
	}
	
	function isie6() {//ie6
	    if ($.browser.msie) {
	        if ($.browser.version == "6.0") return true;
	    }
	    return false;
	}
</script>