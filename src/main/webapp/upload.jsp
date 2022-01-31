<%--
  Created by IntelliJ IDEA.
  User: gurov
  Date: 31.01.2022
  Time: 15:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" pageEncoding="UTF-8" session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Upload</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
</head>
<body>

<div class="container">
  <h2>Загрузка фото кандидата</h2>
  <form action="<c:url value='/upload?id=${param.id}'/>" method="post" enctype="multipart/form-data">
    <div class="checkbox">
      <input type="file" name="file">
    </div>
    <button type="submit" class="btn btn-default">Добавить</button>
  </form>
</div>

</body>
</html>
