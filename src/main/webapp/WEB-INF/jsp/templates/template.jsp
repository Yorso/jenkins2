<!DOCTYPE HTML>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!-- Defining page titles only once using a text attribute -->
<c:set var="titleKey">
	<tiles:getAsString name="title" />
</c:set>

<html>
	<head>
		<meta charset="utf-8">
		<title><spring:message code="${titleKey}"/></title>
	</head>

	<body>
		<h1><spring:message code="${titleKey}" /></h1>

		<!-- Pages will be included here -->
		<tiles:insertAttribute name="body" />
		 
	</body>
</html>