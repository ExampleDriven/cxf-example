<%@ page language="java" import="java.util.*" %>
<html>
<body>
Host name : <%=java.net.InetAddress.getLocalHost().getHostName() %> <br />
Session attributes:
<%
  for (Enumeration e = session.getAttributeNames(); e.hasMoreElements(); ) {     
    String attribName = (String) e.nextElement();
    Object attribValue = session.getAttribute(attribName);
%>
<h3><%= attribName %></h3> <%= attribValue %>

<%
}
%>
</body>
</html>