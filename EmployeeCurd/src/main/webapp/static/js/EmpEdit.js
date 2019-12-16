function getEmp(id) {
    $.ajax({
        url: '/ssm1/employee/emp/' + id,
        type: 'get',
        success: function (result) {
            console.log(result);
            var emp = result.data_return.emp;
            $('#p_empname').text(emp.empname);
            $('#update_email').val(emp.email);
            $('#emp_update_modal input[type=radio]').val([emp.gender]);
            $('#update_dept').val([emp.dId]);
            console.log("finish")
        },
        error: function () {
            alert('出错了')
        }
    })
}

$(function () {

    $(document).on('click', '.edit-btn', function () {
        // 重置表单
        reset_form('#emp_update_form');

        // 先查出所有部门
        getDepts('#update_dept');

        // 查出员工信息
        var emp_id = $(this).attr('emp_id');
        getEmp(emp_id);

        //给修改按钮绑定当前员工的id
        $('#emp_update_btn').attr("emp_id", emp_id);

        $('#emp_update_modal').modal({
            backdrop: "static"
        })
    });

    $('#update_email').blur(checkEmail2);

    // 保存
    $('#emp_update_btn').click(function () {
        if (!checkEmail2()) {
            return;
        }
        $.ajax({
            url: '/ssm1/employee/emp/' + $('#emp_update_btn').attr('emp_id'),
            type: 'put',
            data: $('#emp_update_form').serialize(),
            success: function (result) {
                console.log(result);
                $('#emp_update_modal').modal('hide');
                to_page(currentPage);
            },
            error: function () {
                alert('出错啦')
            }
        })
    });


    // 校验邮箱
    function checkEmail2() {
        var email = $('#update_email').val();
        var email_regx = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
        if (!email_regx.test(email)) {
            show_validate_msg(false, '#update_email', "邮箱格式不正确");
            return false;
        } else {
            show_validate_msg(true, '#update_email', "");
            return true;
        }
    }
});