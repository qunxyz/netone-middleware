<%@ page contentType="text/html; charset=gb2312" language="java"  errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%

	String msg = (String)session.getAttribute("msg");
    String flag = (String)request.getParameter("style");

	//int flag = style;
	String style;
	if(flag==null||flag.equals("")){
		 flag = (String)session.getAttribute("style");
	}
    switch(Integer.parseInt(flag))
	{
	case 0:
      	style = "֤����֤�ɹ�!";
      	break;
    case 1:
      	style ="��Ҫʹ�ø���CA����֤���¼!<br>������޷�����������ϵ�����籣�����̻��߲���ͻ���������:0591-968806";
      	break;
	case 9:
		style ="��֤���еĵ�λ��ϵͳ�в�����!<br>������޷�����������ϵ�����籣�����̻��߲���ͻ���������:0591-968806";
		break;
		
		
	case 4001:
		style ="�ڴ����ʧ�ܻ����ݸ�ʽ����!�������Ϊ��4001!<br>������޷�����������ϵ�����籣�����̻��߲���ͻ���������:0591-968806��";
		break;
	case 4002:
		style ="���ݸ�ʽ����!�������Ϊ��4002!<br>������޷�����������ϵ�����籣�����̻��߲���ͻ���������:0591-968806";
		break;
	case 4003:
		style ="�������ݿ�ʱ������ϵͳ����!<br>�������Ϊ��4003!������޷�����������ϵ�����籣�����̻��߲���ͻ���������:0591-968806";
		break;
	case 4004:
		style ="ͨѶ���ݴ������!�������Ϊ��4004!<br>������޷�����������ϵ�����籣�����̻��߲���ͻ���������:0591-968806";
		break;
	case 4005:
		style ="��Ч����!�������Ϊ��4005!<br>������޷�����������ϵ�����籣�����̻��߲���ͻ���������:0591-968806";
		break;
	case 4006:
		style ="�û��ļ�����!�������Ϊ��4006!<br>������޷�����������ϵ�����籣�����̻��߲���ͻ���������:0591-968806";
		break;
	case 4007:
		style ="֤�����!�������Ϊ��4007!<br>������޷�����������ϵ�����籣�����̻��߲���ͻ���������:0591-968806";
		break;

	case 4011:
		style ="��������֤��Ǹ���CAǩ��������֤��!�������Ϊ��4011!<br>������޷�����������ϵ�����籣�����̻��߲���ͻ���������:0591-968806";
		break;
	case 4012:
		style ="��������֤���ѹ��ڣ��뼰ʱ����!������������֤���Ƿ��ѹ��ڣ�"+
		"<br>���߿ͻ���������ʱ���Ƿ���ȷ���������Ϊ��4012!"+
		"<br>������޷�����������ϵ�����籣�����̻��߲���ͻ���������:0591-968806";
		break;
	case 4013:
		style ="��������֤���ѱ�ע��!�������Ϊ��4013!<br>������޷�����������ϵ�����籣�����̻��߲���ͻ���������:0591-968806";
		break;
	case 4014:
		style ="��������֤����ʱδ��ͨ������δ����֤������!�������Ϊ��4014!<br>������޷�����������ϵ�����籣�����̻��߲���ͻ���������:0591-968806";
		break;
	case 4015:
		style ="��������֤����ʱδ��ͨ����֤����Ϣ�������������!�������Ϊ��4015!<br>������޷�����������ϵ�����籣�����̻��߲���ͻ���������:0591-968806";
		break;
	case 4016:
		style ="��������֤�����Ϸ��������ѵ���������ʱ�ɷ�!�������Ϊ��4016!<br>������޷�����������ϵ�����籣�����̻��߲���ͻ���������:0591-968806";
		break;
	case 4021:
		style ="��֤ǩ��ʧ�ܣ�����֤���Ӧ��KEYǩ����!�������Ϊ��4021!<br>������޷�����������ϵ�����籣�����̻��߲���ͻ���������:0591-968806";
		break;
	case 4022:
		style ="ǩ����֤������֤��֤�鲻��ͬһ��!�������Ϊ��4022!<br>������޷�����������ϵ�����籣�����̻��߲���ͻ���������:0591-968806";
		break;
	case 4023:
		style ="����ʧ��!�������Ϊ��4023!<br>������޷�����������ϵ�����籣�����̻��߲���ͻ���������:0591-968806";
		break;
	case 4024:
		style ="����ʧ��!�������Ϊ��4024!<br>������޷�����������ϵ�����籣�����̻��߲���ͻ���������:0591-968806";
		break;
	case 4031:
		style ="ʱ����������ڲ�����!�����������Ϊ��4031!<br>������޷�����������ϵ�����籣�����̻��߲���ͻ���������:0591-968806";
		break;
	case 4032:
		style ="ǩ��ʱ���ʧ��!�������Ϊ��4032!<br>������޷�����������ϵ�����籣�����̻��߲���ͻ���������:0591-968806";
		break;
	case 4033:
		style ="��֤ʱ���ʧ��!�������Ϊ��4033!<br>������޷�����������ϵ�����籣�����̻��߲���ͻ���������:0591-968806";
		break;
	case 4019:
		style ="��Ҫʹ�ø���CA����֤���¼!�������Ϊ��4019!<br>������޷�����������ϵ�����籣�����̻��߲���ͻ���������:0591-968806";
		break;
		
	case 4020:
		style ="��������֤�������籣���������ѵ���������ʱ�ɷ� ��"+
		"<br>������޷�����������ϵ�����籣�����̻��߲���ͻ���������:0591-968806";
		break;		
		
	case 4992:
		style ="��֤��ʧ��.�������Ϊ��4992!<br>������޷�����������ϵ�����籣�����̻��߲���ͻ���������:0591-968806";
		break;	
	case 4993:
		style ="��֤��ʧ��.�������Ϊ��4993!<br>������޷�����������ϵ�����籣�����̻��߲���ͻ���������:0591-968806";
		break;	
	case 4994:
		style ="������ǩ��ʧ��.�������Ϊ��4994!<br>������޷�����������ϵ�����籣�����̻��߲���ͻ���������:0591-968806";
		break;	
	case 4991:
		style ="��ȷ�����ĵ������Ƿ��Ѱ�װ������CA����֤��ͻ�������3.1�汾�����ϰ汾���������Ϊ��4991!"+
		"<br>�������δ��װ���������<a href='http://www.fjca.com.cn/download/downfile.asp?n=FJCAUserTool.exe'>�� ��</a>���°汾������֤��ͻ�������."
		+"<br>������޷�����������ϵ�����籣�����̻��߲���ͻ���������:0591-968806";
		break;
	case 9013:
		style="֤�����!<br>������޷�����������ϵ�����籣�����̻��߲���ͻ���������:0591-968806";
		break;
	case 9016:
		style="��֤ǩ��ʧ��!<br>������޷�����������ϵ�����籣�����̻��߲���ͻ���������:0591-968806";
		break;
	case 9000:
		style="��ȷ���û��Ƿ��Ѿ�����key,�������֤�������µ�½���ٲ���!";
		break;
	case 9999:	
		style="������ϣ���ȡ����֤����Ϣ,���Ժ��ٽ��в���!";
		break;	
	default:
		style ="������ϣ����Ժ��ٽ��в���!";
	}
	
%>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>����ҳ��</title>
<script Language="JavaScript">
document.onreadystatechange= lFuncDocumentReady;
function lFuncDocumentReady(){
  if (document.readyState =="complete"){
     try {
      var msg=document.all.item("msg");
      var style=document.all.item("style");
      if(style!=null){
     	alert(style.value);
     }
      if (msg!=null)
      {

       // alert(msg.value);
      }

     }catch(e){
            alert(e.description);
     }
  }
}
</script>

</head>

<body>

<form  action="" method="post">
   	<td><INPUT type="hidden" name="msg" value="<%=msg %>" ></td>
   	<div align="center">
  <p align="center">&nbsp</p>
  <p align="center">&nbsp</p>
  <p align="center">��¼ʧ����:</p>
  <p align="center">ԭ��:<%=style %></p>
<div >

</form>

</body>
</html>
