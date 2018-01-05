<%@page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="UTF-8"/>
    <meta http-equiv="Content-Type" content="multipart/form-data" charset="UTF-8"/>
</head>
<body>
<h2>Hello World!</h2>
    <form action="http://116.62.228.3:8080/file/api/gzip/upload" method="post" enctype="multipart/form-data">
        <input type="file" name="file"/>
        <input type="submit" value="提交"/>
    </form>
   <img src="http://116.62.228.3:8080/file/api/gzip/download?fileName=1513155298081u=2465775762,1509670197&fm=72.jpg" alt="图片无法显示"/>
</body>
</html>
