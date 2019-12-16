<%--
  Created by IntelliJ IDEA.
  User: Nuukatsukiy
  Date: 2019/9/23
  Time: 18:40
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>全体员工</title>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
        <title>Bootstrap HelloWorld</title>

        <!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
        <script src="/ssm1/static/js/jquery-3.2.1.min.js"></script>

        <!-- Bootstrap -->
        <link href="/ssm1/static/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">

        <!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
        <script src="/ssm1/static/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>

    </head>
    <body>
        <div class="container">
            <!-- 标题 -->
            <div class="row">
                <div class="col-md-12">
                    <h1>SSM-CRUD</h1>
                </div>
            </div>

            <!-- 全局新增及删除按钮 -->
            <div class="row">
                <div class="col-md-4 col-md-offset-8">
                    <button class="btn btn-primary">新增</button>
                    <button class="btn btn-danger">删除</button>
                </div>
            </div>

            <!-- 表格 -->
            <div class="row">
                <div class="col-md-12">
                    <table class="table table-hover">
                        <tr>
                            <th>#</th>
                            <th>员工姓名</th>
                            <th>员工邮箱</th>
                            <th>员工性别</th>
                            <th>部门</th>
                            <th>操作</th>
                        </tr>

                        <%-- 遍历表格数据 --%>
                        <c:forEach items="${pageInfo.list}" var="emp">
                            <tr>
                                <td>${emp.id}</td>
                                <td>${emp.empname}</td>
                                <td>${emp.email}</td>
                                <td>${emp.gender == 1 ? "男":"女"}</td>
                                <td>${emp.department.deptName}</td>
                                <td>
                                    <button class="btn btn-info btn-xs">
                                        <span class="glyphicon glyphicon-pencil"></span>
                                        修改
                                    </button>

                                    <button class="btn btn-warning btn-xs">
                                        <span class="glyphicon glyphicon-trash"></span>
                                        删除
                                    </button>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </div>

            <!-- 分页信息及分页条 -->
            <div class="row">
                <!--分页文字信息  -->
                <div class="col-md-6">
                    当前<span class="label label-primary">${pageInfo.pageNum}</span>页,
                    共<span class="label label-primary">${pageInfo.pages}</span>页,
                    共<span class="label label-primary">${pageInfo.total}</span>条记录</div>
                <div class="col-md-6">
                    <nav>
                        <ul class="pagination">
                            <li><a href="/ssm1/employee/getEmps?pageNum=1">首页</a></li>
                            <%-- 判断是否有上一页，如果有则显示 --%>
                            <c:if test="${pageInfo.hasPreviousPage}">
                                <li>
                                    <a href="/ssm1/employee/getEmps?pageNum=${pageInfo.pageNum - 1}"
                                       aria-label="Previous">
                                        <span aria-hidden="true">&laquo;</span>
                                    </a>
                                </li>
                            </c:if>

                            <c:forEach items="${pageInfo.navigatepageNums}" var="page_Num">
                                <c:if test="${pageInfo.pageNum == page_Num}">
                                    <li class="active">
                                        <a href="/ssm1/employee/getEmps?pageNum=${page_Num}">${page_Num}</a>
                                    </li>
                                </c:if>

                                <c:if test="${pageInfo.pageNum != page_Num}">
                                    <li>
                                        <a href="/ssm1/employee/getEmps?pageNum=${page_Num}">${page_Num}</a>
                                    </li>
                                </c:if>
                            </c:forEach>
                            <%-- 判断是否有下一页，有则显示 --%>
                            <c:if test="${pageInfo.hasNextPage}">
                                <li>
                                    <a href="/ssm1/employee/getEmps?pageNum=${pageInfo.pageNum + 1}" aria-label="Next">
                                        <span aria-hidden="true">&raquo;</span>
                                    </a>
                                </li>
                            </c:if>
                            <li><a href="/ssm1/employee/getEmps?pageNum=${pageInfo.pages}">尾页</a></li>
                        </ul>
                    </nav>
                </div>
            </div>
        </div>
    </body>
</html>
