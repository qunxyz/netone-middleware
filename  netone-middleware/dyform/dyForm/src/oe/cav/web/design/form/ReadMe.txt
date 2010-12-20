########################################
欢迎您使用OESEE动态表单设计模板.

使用说明:
1)  在OESEE标准规范工程 webSpecifcation 中webroot目录中新建一个目录(假设为:yyyyyy)
2)  把本目录中四个JSP文件拷贝到该目录中
3)  在dyweb.properties中加入如下四句配置
	yyyyyyList=yyyyyy/LIST_DEMO.jsp
	yyyyyyCreate=yyyyyy/CREATE_DEMO.jsp
	yyyyyyModify=yyyyyy/MODIFY_DEMO.jsp
	yyyyyyQuery=yyyyyy/QUERY_DEMO.jsp
4)  访问 dylist?formcode=$$formcode$$&pagename=yyyyyy    注意:yyyyyy 是您在webroot目录中新建一个目录
