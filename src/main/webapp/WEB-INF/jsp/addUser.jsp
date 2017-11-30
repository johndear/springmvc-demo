<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Spring4 MVC -HelloWorld</title>
</head>
<body>
	<form:form action="../user/add" method="post" modelAttribute="user"> id:<form:input
			path="id" />
		<form:errors path="id" />
		<br> name:<form:input path="name" />
		<form:errors path="name" />
		<br> birth:<form:input path="birth" />
		<form:errors path="birth" />
		<input type="submit" value="submit">
	</form:form>
</body>
</html>