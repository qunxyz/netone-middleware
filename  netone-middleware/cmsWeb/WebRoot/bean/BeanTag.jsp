<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="xxxxxportal" prefix="portaltag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	</head>
	<body>
		<portaltag:beanTag varname="bxx" beanname="BUSSBEAN.BUSSBEAN.SAMPLE1"
			sex='true' age='100' name='mike' />
		
		TYPES:${bxx.values.types}
	</body>
</html>