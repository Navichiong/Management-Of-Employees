// 检查部门名合法性
function check_deptName() {
    var deptName = $('#add_departName').val();
    var regx = /(^[\u2E80-\u9FFF]{1,10}$)/;
    if(regx.test(deptName)){
        show_validate_msg(true,'#add_departName','');
        return true;
    }else{
        show_validate_msg(false,'#add_departName','部门名称只能为1~10位的中文');
        return false;
    }
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

// 表单重置
function reset_form(ele) {
    $(ele)[0].reset();
    //清空表单样式
    $(ele).find("*").removeClass("has-error has-success");
    $(ele).find(".help-block").text("");
}

$(function () {

    // 模态框弹出
    $('#dept_add_btn').click(function () {
        reset_form('#dept-add-form');
        $('#dept-add-modal').modal({
            backdrop:'static'
        })
    });

    // 数据校验
    $('#add_departName').blur(check_deptName);

    $('#add-dept-save').click(function () {
        if(!check_deptName()){
            return false;
        }

        // 检验部门名是否重复
        $.ajax({
            url:'/ssm1/department/addDept',
            type:'post',
            data:$('#dept-add-form').serialize(),
            success:function (result) {
                console.log(result);
                if (result.code === 100) {
                    //    关闭模态框
                    $('#dept-add-modal').modal('hide');
                    //    显示最后一页的数据
                    to_dept_page(deptTotalRecord);
                } else if (result.code === 200) {
                    var fieldErrors = result.data_return.errorMsg;
                    if (fieldErrors) {
                        //有哪个字段的错误信息就显示哪个字段的；
                        if (fieldErrors.deptName) {
                            //显示错误信息
                            show_validate_msg(false, "#add_departName", fieldErrors.deptName);
                        }
                        if(fieldErrors.uniqueDept){
                            show_validate_msg(false,"#add_departName", fieldErrors.uniqueDept);
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
