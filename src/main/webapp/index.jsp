<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>

<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <%@page contentType="text/html; charset=UTF-8" %>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>

    <title>Работа мечты</title>
</head>
<body>
<div class="container">
    <div class="row">
        <ul class="nav">
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/posts.do">Вакансии</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/candidate.do">Кандидаты</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/post/edit.jsp">Добавить вакансию</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/candidate/edit.jsp">Добавить кандидата</a>
            </li>
            <c:if test="${user == null}">
                <li class="nav-item">
                    <a class="nav-link" href="<%=request.getContextPath()%>/login.jsp">Войти</a>
                </li>
            </c:if>
            <c:if test="${user != null}">
                <li class="nav-item">
                    <a class="nav-link" href="<%=request.getContextPath()%>/index.jsp">Пользователь <c:out value="${user.name}"/></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="<%=request.getContextPath()%>/logout.do">Выйти</a>
                </li>
            </c:if>
        </ul>
    </div>
    <div class="row">
        <div class="card" style="width: 100%">
            <div class="card-header">
                Сегодняшние вакансии.
            </div>
            <div class="card-body">
                <c:choose>
                    <c:when test="${postsSize == 0}">
                        Новых вакансий за сегодня нет
                    </c:when>
                    <c:when test="${postsSize != 0}">
                        <table class="table">
                            <thead>
                            <tr>
                                <th scope="col">Названия</th>
                                <th scope="col">Дата создания</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${posts}" var="post">
                                <tr>
                                    <td>
                                        <c:out value="${post.name}"/>
                                    </td>
                                    <td>
                                        <c:out value="${post.created}"/>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </c:when>
                </c:choose>
            </div>
        </div>
    </div>
    <div class="row pt-3">
        <div class="card" style="width: 100%">
            <div class="card-header">
                Сегодняшние кандидаты.
            </div>
            <div class="card-body">
                        <table class="table">
                            <thead>
                            <tr>
                                <th scope="col">Имя</th>
                                <th scope="col">Город</th>
                                <th scope="col">Фото</th>
                                <th scope="col">Удалить кандидата</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${candidates}" var="can">
                                <tr>
                                    <td>
                                        <c:out value="${can.name}"/>
                                    </td>
                                    <td>
                                        <c:forEach items="${cities}" var="city">
                                            <c:if test="${can.cityId == city.id}">
                                                <c:out value="${city.city}"/>
                                            </c:if>
                                        </c:forEach>
                                    </td>
                                    <td>
                                        <img src="<c:url value='/download?id=${can.id}'/>" width="100px" height="100px"/>
                                    </td>
                                    <td>
                                        <a href="<c:url value='/delete?id=${can.id}'/>">Удалить кандидата</a>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>
