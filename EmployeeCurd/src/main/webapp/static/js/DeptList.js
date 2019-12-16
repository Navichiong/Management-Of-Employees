// 全局变量，记录总页数
deptTotalRecord = NaN;
deptCurrentPage = NaN;

$(function () {
    to_dept_page(1)
});

function to_dept_page(pageNum) {
    $.ajax({
        url: '/ssm1/department/getDeptsByPageNum',
        data: {'pageNum': pageNum},
        type: 'get',
        success: function (result) {
            console.log(result);
            //1、解析并显示员工数据
            build_dept_table(result);
            //2、解析并显示分页信息
            build_dept_pageInfo(result);
            //3、解析显示分页条数据
            build_dept_nav(result);
        },
        error: function () {
            alert('出错了')
        }
    })
}

// 构建表格
function build_dept_table(result) {
    var deptTable = $('#dept_table');
    var tbody = $('#dept_table tbody');
    tbody.empty();

    var depts = result.data_return.pageInfo.list;
    $.each(depts, function (index, item) {
        // 行对象
        var tr = $('<tr></tr>');

        //单选框
        var td_checkbox = $('<td></td>').append($('<input type="checkbox" class="singleCheck">'));
        var td_deptId = $('<td></td>').append(item.depId);
        var td_deptName = $('<td></td>').append(item.deptName);

        // <button type="button" class="btn btn-primary">（首选项）Primary</button>
        var add_btn = $('<button></button>').addClass('btn btn-primary btn-xs editBtn').append($('<span></span>')
            .addClass('glyphicon glyphicon-pencil')).append('编辑').attr('dept-id',item.depId);
        var del_btn = $('<button></button>').addClass('btn btn-danger btn-xs delBtn').append($('<span></span>')
            .addClass('glyphicon glyphicon glyphicon-trash')).append('删除').attr('dept-id',item.depId);
        var btn_td = $('<td></td>');
        btn_td.append(add_btn).append(' ').append(del_btn);

        tr.append(td_checkbox).append(td_deptId).append(td_deptName).append(btn_td).appendTo(deptTable);

    })

}

// 构建分页信息
function build_dept_pageInfo(result) {

    var entry = $('#paging_bar ul');
    entry.empty();

    // 上一页及首页
    var pageInfo = result.data_return.pageInfo;

    var prePageLi = $('<li></li>').append($('<a></a>').append('&laquo;'));
    var firstPageLi = $('<li></li>').append($('<a></a>').append('首页').prop('href', '#'));

    if (pageInfo.hasPreviousPage) {
        firstPageLi.click(function () {
            to_dept_page(1);
        });

        prePageLi.click(function () {
            to_dept_page(pageInfo.pageNum - 1);
        });
    } else {
        firstPageLi.addClass('disabled');
        prePageLi.addClass('disabled');
    }
    entry.append(firstPageLi).append(prePageLi);

    // 页码
    var navigatepageNums = pageInfo.navigatepageNums;
    $.each(navigatepageNums, function (index, item) {
        var pagination = $('<li></li>').append($('<a></a>').prop('href', '#').append(item));
        if (item === pageInfo.pageNum) {
            pagination.addClass('active');
        }

        pagination.click(function () {
            to_dept_page(item);
        });
        entry.append(pagination);
    });


    // 下一页及末页
    var lastPageLi = $('<li></li>').append($('<a></a>').append('末页').prop('href', '#'));
    var nextPageLi = $('<li></li>').append($('<a></a>').append('&raquo;'));

    if (pageInfo.hasNextPage) {
        lastPageLi.click(function () {
            to_dept_page(pageInfo.pages);
        });

        nextPageLi.click(function () {
            to_dept_page(pageInfo.nextPage);
        });
    } else {
        lastPageLi.addClass('disabled');
        nextPageLi.addClass('disabled');
    }
    entry.append(nextPageLi).append(lastPageLi);
}

// 构建分页条数据
function build_dept_nav(result) {

    // 获取pageInfo对象
    var page_info = $("#page_info");
    page_info.empty();

    var pageInfo = result.data_return.pageInfo;

    page_info.append("当前第").append($("<span></span>").addClass("label label-primary").append(pageInfo.pageNum)).append("页,");
    page_info.append("共").append($("<span></span>").addClass("label label-primary").append(pageInfo.pages)).append("页,");
    page_info.append("共").append($("<span></span>").addClass("label label-primary").append(pageInfo.total)).append("条记录");

    deptTotalRecord = pageInfo.total;
    deptCurrentPage = pageInfo.pageNum;
}