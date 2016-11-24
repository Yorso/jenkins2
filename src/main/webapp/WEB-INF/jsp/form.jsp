<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<fmt:setBundle basename="messages" var="msg"/>
<fmt:message key="form.submit" bundle="${msg}" var="agesub"/>

<form:form method="POST" modelAttribute="defaultUser">
	<fmt:message key="form.name" bundle="${msg}"/>
	<form:input type="text" id="firstName" path="firstName" /> 
	<br/>
	<fmt:message key="form.age" bundle="${msg}"/>
	<form:select path="age" id="ageComb" name="ageComb" items="${ages}" />
	<br/>
	<input type="submit" id="submit" value="${agesub}"/>
</form:form>
${message}
