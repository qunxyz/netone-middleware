<%@ page language="java" pageEncoding="GBK"%>

<HTML dir="LTR" lang="zh-CN">
	<HEAD>
		<TITLE></TITLE>
		<SCRIPT TYPE="text/javascript">
		</SCRIPT>
	</HEAD>

	<BODY bgcolor="#FFFFFF">
		<form>
			����1: ${var1} <br>
			
			����2: ${var2} <br>
			
			����3: ${var3} <br>
			
			����4: ${var4.a}, ${var4.b} <br>
			
			����5: ${var5.c.c} <br>
			
			����6: ${var6[0]},${var6[1]} <br>
			
			����7: ${var7.name},${var7.age} <br>
			
			����8: ${var8[0].name} <br>
			
			����9: ${var9} <br>
			
			����:<input type='password' value='' name='var9' />
			
			<input type='submit' value='todo' />
		</form>
	</BODY>
</HTML>
