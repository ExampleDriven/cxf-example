<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>CXF Rest test application</title>
<style>
	.error {color : red;}
</style>
</head>
<body>

<h2>Technologies</h2>
<ul>
<li>CXF, JAX-RS (REST), JAX-WS (SOAP)</li>
<li>Spring Security</li>
<li>Spring bean validation, JSR 303</li>
</ul>

<a href="<c:url value="/cxf/services/"/>">WSDL / WADL location</a>



<c:if test="${not empty locationDataList}">

<hr />
All locations : <a href="<c:url value="/cxf/rest/location/*"/>">/rest/location/*</a>
<h2>Result</h2>

<table border="1">
<tr>
<th>Result</th>
<th>Rest URLs</th>
</tr>

<c:forEach items="${locationDataList}" var="locationData">


<tr>
<td>

Location ID is : ${locationData.id}
<br />
Timezone is : ${locationData.timezone}
<br />
Time is : ${locationData.date}
<br />
Location is : ${locationData.location}
<br />

</td>
<td>
	<a href="<c:url value="/cxf/rest/location/${locationData.location}"/>">/cxf/rest/location/${locationData.location}</a>
<br />
	<a href="<c:url value="/cxf/rest/location/${locationData.location}.xml"/>">/cxf/rest/location/${locationData.location}.xml</a>
<br />
	<a href="<c:url value="/cxf/rest/location/${locationData.location}.json"/>">/cxf/rest/location/${locationData.location}.json</a>
<br />
</td>
</c:forEach>

</table>
<hr />

</c:if>




<h2>Read Location</h2>
<form method="get" action="read">

<label for="SOAP_read">SOAP
<input class="radio" id="SOAP_read" type="radio" name="protocol" value="SOAP">
</label>

<label for="REST_read">REST
<input class="radio" checked id="REST_read" type="radio" name="protocol" value="REST">
</label>
<br />


<label for="location">Location 
<input id="location" name="location"/>
</label>

<br />
<input type="submit" value="read" />

</form>

<hr />
<h2>Read All Location</h2>
<form method="get" action="readall">
<label for="SOAP_readall">SOAP
<input class="radio" id="SOAP_readall" type="radio" name="protocol" value="SOAP">
</label>

<label for="REST_readall">REST
<input class="radio" checked id="REST_readall" type="radio" name="protocol" value="REST">
</label>
<br />

<input type="submit" value="read all" />
</form>


<hr />
<h2>Create Location</h2>
<form:form method="post" action="create" commandName="locationData">

<label for="SOAP_create">SOAP
<input class="radio" id="SOAP_create" type="radio" name="protocol" value="SOAP">
</label>

<label for="REST_create">REST
<input class="radio" checked id="REST_create" type="radio" name="protocol" value="REST">
</label>
<br />

<label for="locationCreate">Location (min 5 max 20 char) 
<form:input id="locationCreate" path="location"/>
<form:errors path="location" cssClass="error"/>
</label>

<label for="timezone">Timezone 
<input id="timezone" name="timezone"/>
</label>

<label for="date">Date (Format : yyyy-mm-dd) 
<input id="date" name="date"/>
</label>


<br />
<input type="submit" value="create"/>

</form:form>

<hr />
<h2>Update or create Location</h2>
<form method="post" action="update">

<label for="SOAP_update">SOAP
<input class="radio" id="SOAP_update" type="radio" name="protocol" value="SOAP">
</label>

<label for="REST_update">REST
<input class="radio" checked id="REST_update" type="radio" name="protocol" value="REST">
</label>
<br />

<label for="locationUpdate">Location 
<input id="locationUpdate" name="location"/>
</label>

<label for="timezoneUpdate">Timezone 
<input id="timezoneUpdate" name="timezone"/>
</label>

<label for="dateUpdate">Date (Format : yyyy-mm-dd) 
<input id="dateUpdate" name="date"/>
</label>

<br />
<input type="submit" value="Update"/>

</form>

<hr />
<h2>Delete Location</h2>
<form method="get" action="delete">

<label for="SOAP_delete">SOAP
<input class="radio" id="SOAP_delete" type="radio" name="protocol" value="SOAP">
</label>

<label for="REST_delete">REST
<input class="radio" checked id="REST_delete" type="radio" name="protocol" value="REST">
</label>
<br />

<label for="locationDelete">Location 
<input id="locationDelete" name="location"/>
</label>

<br />
<input type="submit" value="delete" />

</form>

<hr />
<h2>Delete All Locations</h2>
<form method="get" action="deleteall">

<label for="SOAP_delete_all">SOAP
<input class="radio" id="SOAP_delete_all" type="radio" name="protocol" value="SOAP">
</label>

<label for="REST_delete_all">REST
<input class="radio" checked id="REST_delete_all" type="radio" name="protocol" value="REST">
</label>
<br />

<input type="submit" value="delete all" />

</form>

</body>
</html>