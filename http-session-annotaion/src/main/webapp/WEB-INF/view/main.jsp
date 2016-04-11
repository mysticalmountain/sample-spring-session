<%--
  Created by IntelliJ IDEA.
  User: andongxu
  Date: 16-3-28
  Time: 下午12:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>main</h1>
<h1>welcome <%=session.getAttribute("username")%></h1>
<a href="<%=request.getContextPath() + "/logout"%>">logout</a>
</body>
</html>
