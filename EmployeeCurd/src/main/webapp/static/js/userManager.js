$(function () {

    reset_form('#user_update_form');
    reset_form('#user_delete_form');

    $('#new_password').blur(checkNewPwd);

    $('#update_save_btn').click(function () {
        if (!checkNewPwd()) {
            return false;
        }
        $.ajax({
            url: '/ssm1/user/userUpdate',
            type: 'post',
            data: {
                'username': $('#update_username').val().trim(),
                'password': $('#old_password').val().trim(),
                'newPassword': $('#new_password').val().trim()
            },
            success: function (result) {
                if (result.code === 100) {
                    alert('密码修改成功！');
                } else {
                    alert(result.msg);
                }
            },
            error: function () {
                alert('出错啦....')
            }
        })
    });

    $('#delete_save_btn').click(function () {
        if (confirm('确定要注销当前账号吗？')) {
            $.ajax({
                url: '/ssm1/user/userDelete',
                type: 'post',
                data: $('#user_delete_form').serialize(),
                success: function (result) {
                    console.log(result);
                    if (result.code === 100) {
                        alert('删除成功!');
                        location.href = '/ssm1/login_bubble.html';
                    } else {
                        alert(result.msg)
                    }
                },
                error: function () {
                    alert('出错啦....')
                }
            })
        }
    })

});

function checkNewPwd() {
    var $newPassword = $('#new_password');
    var new_password = $newPassword.val();

    var regx = /^[A-Za-z0-9_-]{3,16}$/;
    if (!regx.test(new_password)) {
        show_validate_msg(false, $newPassword, '密码只能由6~18位英文及数字组合');
        return false;
    } else {
        show_validate_msg(true, $newPassword, '');
        return true;
    }
}

// 清空表单
function reset_form(ele) {
    $(ele)[0].reset();
    //清空表单样式
    $(ele).find("*").removeClass("has-error has-success");
    $(ele).find(".help-block").text("");
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