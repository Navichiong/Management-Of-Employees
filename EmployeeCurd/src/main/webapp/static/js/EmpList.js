// 全局变量，记录总页数
totalRecord = NaN;
currentPage = NaN;

$(function () {
    to_page(1);
});

function to_page(pageNum) {

    $.ajax({
        url: "/ssm1/employee/getEmps",
        data: {"pageNum": pageNum},
        type: "get",
        success: function (result) {
            console.log(result);
            //1、解析并显示员工数据
            build_emps_table(result);
            //2、解析并显示分页信息
            build_page_info(result);
            //3、解析显示分页条数据
            build_page_nav(result);
        },
        error: function () {
            alert("出错了..........")
        }
    })
}

//处理员工表格
function build_emps_table(result) {
    //清空table表格内容
    $("#emps_table tbody").empty();

    var emps = result.data_return.pageInfo.list;
    $.each(emps, function (index, item) {

        // checkbox
        var checkbox_td = $('<td><input type="checkbox" class="signal_checkbox"></td>');

        // 员工信息
        var id_td = $("<td></td>").append(item.id);
        var empName_td = $("<td></td>").append(item.empname);
        var email_td = $("<td></td>").append(item.email);
        var gender_td = $("<td></td>").append(item.gender === 1 ? "男" : "女");
        var deptName_td = $("<td></td>").append(item.department.deptName);

        // 操作按钮
        //
        var edit_button = $("<button></button>").addClass("btn btn-info btn-xs edit-btn")
            .append($("<span></span>").addClass("glyphicon glyphicon-pencil")).append("修改");
        edit_button.attr("emp_id", item.id);

        // edit_button.addClass('edit_btn')
        var del_button = $("<button></button>").addClass("btn btn-warning btn-xs del-btn")
            .append($("<span></span>").addClass("glyphicon glyphicon glyphicon-trash")).append("删除");
        del_button.attr("emp_id", item.id);

        var button_td = $("<td></td>").append(edit_button).append(" ").append(del_button);

        //行添加
        $("<tr></tr>").append(checkbox_td).append(id_td).append(empName_td).append(email_td)
            .append(gender_td).append(deptName_td).append(button_td).appendTo($("#emps_table tbody"))
    })
}

// 处理分页汇总信息
function build_page_info(result) {
    // 获取pageInfo对象

    var page_info = $("#page_info");
    page_info.empty();

    var pageInfo = result.data_return.pageInfo;

    page_info.append("当前第").append($("<span></span>").addClass("label label-primary").append(pageInfo.pageNum)).append("页,");
    page_info.append("共").append($("<span></span>").addClass("label label-primary").append(pageInfo.pages)).append("页,");
    page_info.append("共").append($("<span></span>").addClass("label label-primary").append(pageInfo.total)).append("条记录");
    totalRecord = pageInfo.total;
    currentPage = pageInfo.pageNum;
}

// 处理分页条
function build_page_nav(result) {

    var pageInfo = result.data_return.pageInfo;

    var pagingBar = $("#paging_bar");
    // 清空分页条区的数据
    pagingBar.empty();

    var nav = $("<nav></nav>");
    var ul = $("<ul></ul>").addClass("pagination");

    nav.append(ul);
    pagingBar.append(nav);

    //构建元素
    // 首页及上一页
    var firstPageLi = $("<li></li>").append($("<a></a>").append("首页").attr("href", "#"));
    var prePageLi = $("<li></li>").append($("<a></a>").append("&laquo;"));

    if (pageInfo.hasPreviousPage === false) {
        firstPageLi.addClass("disabled");
        prePageLi.addClass("disabled");
    } else {
        firstPageLi.click(function () {
            to_page(1);
        });
        prePageLi.click(function () {
            to_page(pageInfo.pageNum - 1);
        })
    }

    ul.append(firstPageLi).append(prePageLi);

    // 分页码
    $.each(pageInfo.navigatepageNums, function (index, item) {
        var pagination = $("<li></li>").append($("<a></a>").attr("href", "#").append(item));
        pagination.click(function () {
            to_page(item)
        });

        if (pageInfo.pageNum === item) {
            pagination.addClass("active")
        }

        ul.append(pagination)
    });

    // 末页及下一页
    var endPageLi = $("<li></li>").append($("<a></a>").append("末页").attr("href", "#"));
    var nextPageLi = $("<li></li>").append($("<a></a>").append("&raquo;"));

    if (pageInfo.hasNextPage === false) {
        endPageLi.addClass("disabled");
        nextPageLi.addClass("disabled");
    } else {
        endPageLi.click(function () {
            to_page(pageInfo.pages)
        });

        nextPageLi.click(function () {
            to_page(pageInfo.pageNum + 1)
        })
    }

    ul.append(nextPageLi).append(endPageLi)
}