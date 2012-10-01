<%@page import="java.io.PrintWriter"%>
<%@page import="java.io.StringWriter"%>
<%@page isErrorPage="true" %>

<h2>Internal error</h2>
<h3>Class : <%=exception.getClass().getName() %></h3>
<h3>Message : <%=exception.getMessage() %></h3>
<h3>Stack trace</h3>

<% 
try {

	Throwable ex = exception;
	if (exception instanceof ServletException) {
		// It's a ServletException: we should extract the root cause
		ServletException sex = (ServletException) exception;
		Throwable rootCause = sex.getRootCause();
		if (rootCause == null) {
			ex = exception;
		}
		out.println("<h3>Root cause is: "+ rootCause.getMessage() + "</h3>");
	}

    StringWriter sw = new StringWriter();
    exception.printStackTrace(new PrintWriter(sw)); 

    String stackTrace = sw.toString();
    stackTrace = stackTrace.replaceAll("\n", "<br>");
    out.write(stackTrace);
	
	
	
	// Display cookies
	out.println("<h3>Cookies:</h3>");
	Cookie[] cookies = request.getCookies();
	if (cookies != null) {
    	for (int i = 0; i < cookies.length; i++) {
      		out.println(cookies[i].getName() + "=[" + cookies[i].getValue() + "]");
		}
	}
	    
} catch (Exception ex) { 
	ex.printStackTrace(new java.io.PrintWriter(out));
}
%>

<p/>
<br/>