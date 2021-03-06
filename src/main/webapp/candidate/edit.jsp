<%--
  Created by IntelliJ IDEA.
  User: gurov
  Date: 28.01.2022
  Time: 16:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="ru.job4j.dream.model.Candidate" %>
<%@ page import="ru.job4j.dream.model.City" %>
<%@ page import="ru.job4j.dream.store.DbStore" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
          integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
            integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"
            integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI"
            crossorigin="anonymous"></script>
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>

    <title>Работа мечты</title>
    <script type="text/javascript">
        function validate() {
            if ($('#name').val() === '') {
                alert('Заполните поле "Имя"');
                return false;
            }
            if ($('#cityList').val() === '') {
                alert('Заполните поле "Город"');
                return false;
            }
        }

        $(document).ready(function () {
            $.ajax({
                type: 'GET',
                url: 'http://localhost:8080/dreamjob/cities',
                dataType: 'json'
            }).done(function (data) {
                for (var city of data) {
                    $('#cityList').append(`<option value="${city.id}">${city.city}</option>`)
                }
            }).fail(function (err) {
                alert(err);
            });
        });
    </script>

</head>
<body>
<%
    String id = request.getParameter("id");
    Candidate candidate = new Candidate(0, "", 0, LocalDateTime.now());
    if (id != null) {
        candidate = DbStore.instOf().findCandidateById(Integer.parseInt(id));
    }
%>
<div class="container pt-3">

    <div class="row">
        <ul class="nav">
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/index.do">Главное меню</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/index.jsp">Пользователь <c:out value="${user.name}"/></a>
            </li>
            <c:if test="${user != null}">
                <li class="nav-item">
                    <a class="nav-link" href="<%=request.getContextPath()%>/logout.do">Выйти из профиля</a>
                </li>
            </c:if>
        </ul>
    </div>

    <div class="row">
        <div class="card" style="width: 100%">
            <div class="card-header">
                <% if (id == null) { %>
                Новый кандидат.
                <% } else { %>
                Редактирование кандидата.
                <% } %>
            </div>
            <div class="card-body">
                <form action="<%=request.getContextPath()%>/candidate.do" method="post">
                    <input type="text" hidden class="form-control" name="id" value="<%=candidate.getId()%>" id="id">
                    <div class="form-group">
                        <label>Имя</label>
                        <input type="text" class="form-control" name="name" value="<%=candidate.getName()%>" id="name">
                    </div>
                    <div class="form-group">
                        <label for="cityList">Город</label>
                        <select class="form-control" id="cityList" name="cityId">
                            <option></option>
                        </select>
                    </div>
                    <button type="submit" class="btn btn-primary" onclick="return validate()">Сохранить</button>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
