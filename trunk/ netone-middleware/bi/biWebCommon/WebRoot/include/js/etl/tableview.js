

//Ԥ�����
function btnforecast() {
	if(document.all.flowpage.value==""){
		//window.open(pagecontext+'/forecastSelect.do');
		window.open(pagecontext + "/forecastSelect.do", "", "scrollbars=yes,resizable=yes,width=550,height=400,left=250,top=150");
	} else {
		window.open(pagecontext + "/forecastSelect.do?flowpage=flowpage&endTime="+document.all.endTime.value+"&timelevel="+document.all.timelevel.value+"&dataModelid="+document.all.dataModelid.value+"&hour="+document.all.hour.value, "", "scrollbars=yes,resizable=yes,width=550,height=400,left=250,top=150");
	}
}

//ָ�����
function btntgfilter() {
	window.open(pagecontext + "/tgFilter.do", "", "scrollbars=yes,resizable=yes,width=450,height=210,left=300,top=250");
}

//����excel
function btnexcel() {
	window.open(pagecontext + "/excel.do", "", "scrollbars=yes,resizable=yes,width=550,height=400,left=250,top=150");
}
function btnshowchart() {
	if (document.all.flowpage.value == "") {
		parent.etlchartFrame.submitsel();
	} else {
		if (document.all.flowpage.value == "flowpage") {
			window.open(pagecontext + "/bi/etl/wizard/AnalyseSubject/chart.jsp?showactive="+document.all.showactive.value);
		}
	}
}

//����ָ�����
function dotgfilter(tgfiltvalue) {
	document.vmcreateform.tgfiltvalue.value = tgfiltvalue;
	document.vmcreateform.act.value = "tableView";
	document.vmcreateform.submit();
}

//����Ԥ�����
function doforecast(fcvalue) {
	document.vmcreateform.forecastvalue.value = fcvalue;
	document.vmcreateform.act.value = "tableView";
	document.vmcreateform.submit();
}

