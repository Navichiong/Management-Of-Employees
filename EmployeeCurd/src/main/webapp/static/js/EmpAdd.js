function reset_form(ele) {
    $(ele)[0].reset();
    //清空表单样式
    $(ele).find("*").removeClass("has-error has-success");
    $(ele).find(".help-block").text("");
}

// 打开模态框时查出所有部门
function getDepts(ele) {
    // 清空之前的部门列表
    $(ele).empty();
    $.ajax({
        url: "/ssm1/department/getDepts",
        type: "get",
        success: function (result) {
            console.log(result);
            console.log(totalRecord);
            var dept = $(ele);
            $.each(result.data_return.depts, function (index, item) {
                var option = $('<option></option>').attr("value", item.depId).append(item.deptName);
                dept.append(option)
            })

        },
        error: function () {
            alert("出错了")
        }
    })
}

// 展示元素错误或成功
function show_validate_msg(flag, element, msg) {
    //清除当前元素的校验状态
    $(element).parent().removeClass("has-success has-error");
    $(element).next("span").text("");

    if (flag) {
        $(element).parent().addClass("has-success");
        $(element).next("span").text("")
    } else {
        $(element).parent().addClass("has-error");
        $(element).next("span").text(msg)
    }
}

// 校验员工姓名
function checkEmpname() {
    // 校验员工姓名
    var empname = $('#add_empname').val();
    var empname_regx = /(^[a-zA-Z0-9_-]{3,16}$)|(^[\u2E80-\u9FFF]{2,5}$)/;
    if (!empname_regx.test(empname)) {
        show_validate_msg(false, '#add_empname', "员工姓名只能为3到16位的英文字母、数字及下划线的组合或是2到5位的中文");

        return false;
    } else {
        if ($('#add_empname').next("span").text()) {
            return false;
        } else {
            show_validate_msg(true, '#add_empname', "");
            return true
        }
    }
}

// 校验邮箱
function checkEmail() {
    var email = $('#add_email').val();
    var email_regx = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
    if (!email_regx.test(email)) {
        show_validate_msg(false, '#add_email', "邮箱格式不正确");
        return false;
    } else {
        show_validate_msg(true, '#add_email', "");
        return true;
    }
}

$(function () {

    $('#emp_add_btn').click(function () {

        // 重置表单
        reset_form('#emp_add_modal form');

        // 先查出所有部门
        getDepts('#add_dept');
        $('#emp_add_modal').modal({
            backdrop: "static"
        })
    });

    $('#add_empname').blur(checkEmpname);
    $('#add_email').blur(checkEmail);

    // 校验员工名是否可用
    $('#add_empname').change(function () {
        var empname = $(this).val();
        $.ajax({
            url: "/ssm1/employee/checkEmpnameUsed",
            data: {'empname': empname},
            type: 'post',
            success: function (result) {
                if (result.code === 200) {
                    $('#emp_save_btn').attr('status', 'error');
                    show_validate_msg(false, '#add_empname', '用户名已被注册');
                } else {
                    $('#emp_save_btn').attr('status', 'success');
                    show_validate_msg(true, '#add_empname', '');
                }
            },
            error: function () {
                alert('出错啦')
            }
        })
    });

    // 提交
    $('#emp_save_btn').click(function () {
        if (!checkEmail() || !checkEmpname()) {
            return;
        }
        if ($('#emp_save_btn').attr('status') === 'error') {
            return;
        }
        $.ajax({
            url: "/ssm1/employee/emp",
            type: 'post',
            data: $('#add_emp_form').serialize(),
            success: function (result) {
                console.log(result);
                if (result.code === 100) {
                    //    关闭模态框
                    $('#emp_add_modal').modal('hide');
                    //    显示最后一页的数据
                    to_page(totalRecord)
                } else if (result.code === 200) {
                    var fieldErrors = result.data_return.fieldErrors;
                    if (fieldErrors) {
                        //有哪个字段的错误信息就显示哪个字段的；
                        if (fieldErrors.email) {
                            console.log(fieldErrors.email);
                            //显示邮箱错误信息
                            show_validate_msg(false, "#add_email", fieldErrors.email);
                        }
                        if (fieldErrors.empname) {
                            console.log(fieldErrors.empname);
                            //显示员工名字的错误信息
                            show_validate_msg(false, "#add_empname", fieldErrors.empname);
                        }
                    }
                }
            }
            ,
            error: function () {
                alert('出错啦')
            }
        })
    })
});