$(function () {

    $(document).on('click', '.editBtn', function () {

        reset_form('#dept-edit-form');

        $.ajax({
            url: '/ssm1/department/getDeptById',
            data: {'deptId': $(this).attr('dept-id')},
            type: 'get',
            success: function (result) {
                console.log(result);
                var dept = result.data_return.dept;
                $('#edit_deptId').text(dept.depId);
                $('#edit_departName').val(dept.deptName);
                $('#edit-dept-id').val(dept.depId);
            },
            error: function () {
                alert('出错啦...')
            }
        });

        $('#dept-edit-modal').modal({
            backdrop: 'static'
        })
    });

    $('#edit_departName').blur(check_deptName2);

    $('#edit-dept-save').click(function () {
        if(!check_deptName2()){
            return false;
        }

        // 发送ajax请求，修改部门名称，同时修改员工的部门名称
        // 检验部门名是否重复
        $.ajax({
            url:'/ssm1/department/updateDept/'+$('#edit-dept-id').val(),
            type:'put',
            data:$('#dept-edit-form').serialize(),
            success:function (result) {
                console.log(result);
                if (result.code === 100) {
                    //    关闭模态框
                    $('#dept-edit-modal').modal('hide');
                    //    显示当前页的数据
                    to_dept_page(deptCurrentPage);
                } else if (result.code === 200) {
                    var fieldErrors = result.data_return.errorMsg;
                    if (fieldErrors) {
                        //有哪个字段的错误信息就显示哪个字段的；
                        if (fieldErrors.deptName) {
                            //显示错误信息
                            show_validate_msg(false, "#edit_departName", fieldErrors.deptName);
                        }
                        if(fieldErrors.uniqueDept){
                            show_validate_msg(false,"#edit_departName", fieldErrors.uniqueDept);
                        }
                    }
                }
            },
            error:function () {
                alert('出错了');
            }
        })
    })

});

function check_deptName2() {
    var deptName = $('#edit_departName').val();
    var regx = /(^[\u2E80-\u9FFF]{1,10}$)/;
    if (regx.test(deptName)) {
        show_validate_msg(true, '#edit_departName', '');
        return true;
    } else {
        show_validate_msg(false, '#edit_departName', '部门名称只能为1~10位的中文');
        return false;
    }
}