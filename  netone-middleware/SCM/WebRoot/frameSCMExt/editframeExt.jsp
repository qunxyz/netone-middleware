<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<%@ taglib uri="http://www.oesee.com/netone/portal" prefix="portal"%>
<%
	String path = request.getContextPath();
	String cssURL = request.getContextPath()
	+ "/script/theme/main/blue/images";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<jsp:include page="/WEB-INF/jsp/common/metaExt.jsp"></jsp:include>
		<jsp:include page="/WEB-INF/jsp/common/metaJQuery-min.jsp"></jsp:include>
		<jsp:include page="/WEB-INF/jsp/common/metaJQuery-ui-tab.jsp" />
		<script type="text/javascript" src="<%=path%>/script/jquery-plugin/jquery.highlight-3.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=path%>/My97DatePicker/WdatePicker.js" charset="gb2312"></script>
		<script type="text/javascript" src="<%=path%>/tiny_mce/jquery.tinymce.js"></script>	
		<!-- 样式文件 -->	
		${linkcss}
		<!-- 时间控件脚本 -->
		${datecompFunc}
		
		<title><c:choose><c:when test="${!empty htmltitleinfo}">${htmltitleinfo} - ${activityName}</c:when><c:otherwise>${activityName}</c:otherwise></c:choose></title>
	</head>
	<body>
		<center>
		<BR>
		<BR>
		<div id="app-header" style="z-index:9999;" align="left">
		<center>
		<div id="top_nd_bg_1" style="height:35px;background-image: url('<%=cssURL%>/top_nd_bg.gif');z-index:9999;">
			<div id="top_nd_bg" style="background-image: url('<%=cssURL%>/top_nd_bg.gif');text-align: left;width:1000px;z-index:9999;">
					<div id="new_1">
						<div id="btn_nd">
						<c:if test="${param.query!='look'}">
							<input id="deleteBtn_1" type="button" value=" 作 废 " class="btn"
								onclick="javascript:_delete();" />
							<input id="saveBtn_1" type="button" value=" 保 存 " class="btn"
								onclick="javascript:_save();" />
						</c:if>
							<input id="closeBtn_1" type="button" value=" 关 闭 " class="btn"
								onclick="javascript:window.close();" />	
						</div>
					</div>
			</div>
		</div>
		<center>
		</div>
			
		<div align="center" style="width:1000px;">
		<div id="tabs" style="height: 100%;">
			<ul>
				<li>
					<a href="#tabs-1" class="ui-tabs-selected">流程表单</a>
				</li>
			</ul>
			<div id="tabs-1">
				<!-- 动态表单 start-->
					${form}
				<!-- 动态表单 end-->	
			</div>
		</div>
		</div>
		<jsp:include page="footer.jsp"></jsp:include>
		</center>
	</body>
</html>
<script>
/*
Auto-growing textareas; technique ripped from Facebook
(Textarea need set style "overflow:hidden" under IE)
*/
(function($) {
function times(string, number) {
  for (var i = 0, r = ''; i < number; i ++) r += string;
  return r;
}

$.fn.autogrow = function(options) {
  this.filter('textarea').each(function() {
    this.timeoutId = null;
    var $this = $(this), minHeight = $this.height();
    var shadow = $('<div></div>').css({
      position:   'absolute',
      wordWrap:   'break-word',
      top:        0,
      left:       -9999,
      display:    'none',
      width:      $this.width(),
      fontSize:   $this.css('fontSize'),
      fontFamily: $this.css('fontFamily'),
      lineHeight: $this.css('lineHeight')
    }).appendTo(document.body);

    var update = function() {
      var val = this.value.replace(/</g, '&lt;')
        .replace(/>/g, '&gt;')
        .replace(/&/g, '&amp;')
        .replace(/\n$/, '<br/>&nbsp;')
        .replace(/\n/g, '<br/>')
        .replace(/ {2,}/g, function(space) { return times('&nbsp;', space.length -1) + ' ' });
      shadow.html(val);
      $(this).css('height', Math.max(shadow.height(), minHeight));
    }
    
    var updateTimeout = function() {
      clearTimeout(this.timeoutId);
      var that = this;
      this.timeoutId = setTimeout(function(){ update.apply(that); }, 100);
    };

    $(this).change(update).keyup(updateTimeout).keydown(updateTimeout);
    update.apply(this);
  });
  return this;
}
})(jQuery);

/**
 * @author 愚人码头
 * 类似于新浪微博新消息提示的定位框 DON修改
 * 更多
 */
(function($){
    $.fn.capacityFixed = function(options) {
        var opts = $.extend({},$.fn.capacityFixed.deflunt,options);
        var FixedFun = function(element) {
            var top = opts.top;
            element.css({
                "top":top
            });
            $(window).resize(function(){
                element.css('left',0)
            });
            $(window).scroll(function() {
                var scrolls = $(this).scrollTop();
                if (scrolls > top) {
					
                    if (window.XMLHttpRequest) {
                        element.css({
                            position: "fixed",
                            top: 0							
                        });
                    } else {
                        element.css({
                            top: scrolls
                        });
                    }
                }else {
                    element.css({
                        position: "absolute",
                        top: top
                    });
                }
            });
        };
        return $(this).each(function() {
            FixedFun($(this));
        });
    };
    
    $.fn.capacityFixed.deflunt={
        top:0
	};
})(jQuery);

	$(function() {
		<c:if test="${!empty permission && permission=='false'}">
			/**
			Ext.MessageBox.show({
		        title: '提示',
		        width: 250,
		        closable:false,
		        msg: '对不起！您无权创建。'
		    });
		    **/
			//alert('该业务无权创建该工单');
			//window.opener=null;
			//window.close();
		</c:if>
		
		$("#tabs").tabs();
		$('#tabs').tabs('select', "tabs-1");
		
	    if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()){
	        $("html").css("overflowY","scroll");
	    }
	    
	    $("#app-header").capacityFixed();
		$("#app-header").css({
	       position: "absolute",
	       top: 0,
	       left: 0
	    });
	});
	
	var selectObjVar = null;//全局变量 存放需要选择资源返回值的对象
	function $select(o,url){
		selectObjVar=o;
		openWinCenter("选择",encodeURI(encodeURI(url)),800,420,true);
	}

function validateForm(){//验证
${ValidateScript}
//return true;
}

function $todo(thisObj,formcode,jsonStr){
	var deliter = '';
	if (jsonStr!='')  deliter = ',';
	var column1,column2,column3,column4,column5,column6,column7,column8,column9,column10;
	var column11,column12,column13,column14,column15,column16,column17,column18,column19,column20;
	var column21,column22,column23,column24,column25,column26,column27,column28,column29,column30;
	var column31,column32,column33,column34,column35,column36,column37,column38,column39,column40;
	var column41,column42,column43,column44,column45,column46,column47,column48,column49,column50;
 	
 	var len = 0;
	for(var i=1;i<=50;i++){
		var _o = thisObj.find('#column'+i);
		if (_o){
			var $val_ =  _o.val();
			if ($val_=='undefined') $val_=null;
			if ($val_!=null && $val_!=''){
				eval("column" + i + " = '"+HTMLEncode($val_)+"';");
			} else {
				$isnull = true;len=len+1;
			}
		} else {
			len=len+1;
		}
	}
	    
	 var w = {
		formcode : formcode,
		column1 : column1,column2 : column2,column3 : column3,column4 : column4,column5 : column5,
		column6 : column6,column7 : column7,column8 : column8,column9 : column9,column10 : column10,
		column11 : column11,column12 : column12,column13 : column13,column14 : column14,column15 : column15,
		column16 : column16,column17 : column17,column18 : column18,column19 : column19,column20 : column20,
		column21 : column21,column22 : column22,column23 : column23,column24 : column24,column25 : column25,
		column26 : column26,column27 : column27,column28 : column28,column29 : column29,column30 : column30,
		column31 : column31,column32 : column32,column33 : column33,column34 : column34,column35 : column35,
		column36 : column36,column37 : column37,column38 : column38,column39 : column39,column40 : column40,
		column41 : column41,column42 : column42,column43 : column43,column44 : column44,column45 : column45,
		column46 : column46,column47 : column47,column48 : column48,column49 : column49,column50 : column50
	};
	var json___ = Ext.util.JSON.encode(w);
	if (json___.indexOf('column')>0 && len!=50){
		jsonStr += deliter + json___;
	    deliter = ',';
	}
	return jsonStr;
}

function _save(){
	 if(validateForm()){	
			var msgTip = Ext.MessageBox.show({
		        title: '提示',
		        width: 250,
		        closable:false,
		        msg: '正在保存信息请稍候......'
		    });
		    
		    
		    var $isnull = false;
		    var jsonStr = '';
		    $("body").find("table").each(function(){   
			    if ($(this).attr('id')!='${formcode}')
			    {
				  var formcode = $(this).attr('id');
				  $(this).find('tr.table_tr_content').each(function(){jsonStr=$todo($(this),formcode,jsonStr);});
				  //集成展示 子表单
				  if ($(this).attr("class")=='table_form')
				  $(this).each(function(){jsonStr=$todo($(this),formcode,jsonStr);});
				}//end if
			});
			if ($isnull == true) {
		        //Ext.MessageBox.alert('提示', '子表单不能为空!');
		        //return;
		    }
		    /**
		     * - 保存信息及明细
		     */
		    jsonStr = '[' + jsonStr + ']';
		    Ext.Ajax.request({
		        url: "<c:url value='/frame.do?method=onSavaOrUpdateExt' />",
		        // 请求的服务器地址
		        form: '_FRAME_FORM_ID_',
		        // 指定要提交的表单id
		        method: 'POST',
		        sync: true,
		        isUpload: false,
		        params: {
		            subform: jsonStr,
		            fatherlsh:'${param.fatherlsh}',
		            formcode:'${param.formcode}'
		        },
		        success: function (response, options) {
		            msgTip.hide();
		            var result = Ext.util.JSON.decode(response.responseText);
		            var auditing_ = true;
		
		            if (result.error != null) {
		                Ext.MessageBox.alert('提示', result.tip);
		            } else {
		            	if (result.lsh!=null){
		            		//激活作废按钮
		            		document.getElementById('lsh').value=result.lsh;
		            		document.getElementById('unid').value=result.lsh;
			            }
		            	var paramlsh = result.lsh;
		                Ext.ux.Toast.msg("", result.tip);
		                _refreshOpenerWin();
		            }
		        },
		        failure: function (response, options) {
		            msgTip.hide();
		            checkAjaxStatus(response);
		            var result = Ext.util.JSON.decode(response.responseText);
		            Ext.MessageBox.alert('提示', result.tip);
		        }
		    });
    
	  }
}

function _delete(t){
		var url = "<c:url value='/frame.do?method=onDeleteExt' />";
		if (!confirm('确认要删除?')){return;};
		var msgTip = Ext.MessageBox.show({
	        title: '提示',
	        width: 250,
	        closable:false,
	        msg: '正在执行操作请稍候......'
	    });
		 Ext.Ajax.request({
	        url: url,
	        // 请求的服务器地址
	        form: '_FRAME_FORM_ID_',
	        // 指定要提交的表单id
	        method: 'POST',
	        params:{formcode:'${param.formcode}',lsh:document.getElementById('lsh').value},
	        success: function (response, options) {
	            msgTip.hide();
	            var result = Ext.util.JSON.decode(response.responseText);
	            if (result.error != null) {
	                Ext.MessageBox.alert('提示', result.tip);
	            } else {
	            	document.getElementById('lsh').value='';
	            	document.getElementById('unid').value='';
	                Ext.ux.Toast.msg("", result.tip);
	                _refreshOpenerWin();
	            }
	        },
	        failure: function (response, options) {
	            msgTip.hide();
	            checkAjaxStatus(response);
	            var result = Ext.util.JSON.decode(response.responseText);
	            Ext.MessageBox.alert('提示', result.tip);
	        }
	    });
}

function _refreshOpenerWin() {
	if (opener && opener.location) {
		if (opener.document.getElementById('openerWinId')){
			opener.refresh();;
		}
    }
}

function HTMLEncode(html) 
{ 
var temp = document.createElement ("div"); 
(temp.textContent != null) ? (temp.textContent = html) : (temp.innerText = html); 
var output = temp.innerHTML; 
temp = null; 
return output; 
} 
</script>