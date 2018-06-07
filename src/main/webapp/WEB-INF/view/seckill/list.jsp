<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../common/tag.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>秒杀列表页</title>
    <%--<%@include file="../common/head.jsp" %>--%>
    <link href="${pageContext.request.contextPath}/static/css/bootstrap.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="panel panel-default">
        <div class="panel-heading text-center">
            <h2>秒杀列表</h2>
        </div>
    </div>
    <div class="panel-body">
        <table class="table table-hover">
            <thead>
            <tr>
                <th></th>
                <th>名称</th>
                <th>库存</th>
                <th>开始时间</th>
                <th>结束时间</th>
                <th>创建时间</th>
                <th>详情页</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <c:forEach var="seckill" items="${list}" varStatus="status">
                    <td>${status.index + 1}</td>
                    <td>${seckill.name}</td>
                    <td>${seckill.number}</td>
                    <td><fmt:formatDate value="${seckill.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    <td><fmt:formatDate value="${seckill.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    <td><fmt:formatDate value="${seckill.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    <td>
                        <a class="btn btn-info" href="/seckill/${seckill.secKillId}/detail" target="_blank">详情</a>
                    </td>

                </c:forEach>

            </tr>
            </tbody>
        </table>
    </div>
</div>
<script src="${pageContext.request.contextPath}/static/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/static/js/bootstrap.min.js"></script>
</body>
</html>