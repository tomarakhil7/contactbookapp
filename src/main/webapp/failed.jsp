<%@ page import ="java.util.*" %>
<!DOCTYPE html>
<html>
<body>
<center>
<h1>
    operation failed
</h1>
<%
result= request.getAttribute("failure operation");
out.println("<br>We have <br><br>");
out.println("operation failed is "+result +"<br>");

%>
<form action="/home" method="post">
    <input type="submit" name="Home" value="Home" />
</form>
</center>
</body>
</html>