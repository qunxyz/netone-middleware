<%@ page language="java" pageEncoding="GBK"%>

<HTML dir="LTR" lang="zh-CN">
	<HEAD>
		<TITLE></TITLE>
		<SCRIPT TYPE="text/javascript">
		</SCRIPT>
	</HEAD>

	<BODY bgcolor="#FFFFFF">
		<form>
			参数1: ${var1} <br>
			
			参数2: ${var2} <br>
			
			参数3: ${var3} <br>
			
			参数4: ${var4.a}, ${var4.b} <br>
			
			参数5: ${var5.c.c} <br>
			
			参数6: ${var6[0]},${var6[1]} <br>
			
			参数7: ${var7.name},${var7.age} <br>
			
			参数8: ${var8[0].name} <br>
			
			参数9: ${var9} <br>
			
			密码:<input type='password' value='' name='var9' />
			
			<input type='submit' value='todo' />
		</form>
	</BODY>
</HTML>
