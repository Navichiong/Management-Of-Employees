$(function () {
    reset_form('#register_from');

    $("#chk_img").click(function () {
        var date = new Date();
        $(this).attr("src", "/ssm1/checkCode/imgHandle?time=" + date);
    });

    $('#username').blur(checkUsername);
    $('#password').blur(checkPassword);
    $('#checkCode').focus(function () {
        show_validate_msg(true,'#checkCode','');
    });

    $('#register_save_btn').click(function () {
        if ($(this).attr('status') === 'prohibited') {
            return false;
        }
        $.ajax({
            url: '/ssm1/user/userRegister',
            data: {
                'username': $('#username').val().trim(),
                'password': $('#password').val().trim(),
                'checkCode': $('#checkCode').val().trim()
            },
            type: 'post',
            success: function (result) {
                console.log(result);
                if (result.code === 100) {
                    alert('注册成功！即将返回至登录页面！');
                    location.href = "/ssm1/login_bubble.html"
                } else {
                    var errorMsg = result.data_return.errorMsg;

                    if(errorMsg.check_code){
                        show_validate_msg(false,'#checkCode',errorMsg.check_code);
                        $('#chk_img').click()
                    }

                    if(errorMsg.username){
                        show_validate_msg(false,'#username',errorMsg.username)
                    }

                    if(errorMsg.password){
                        show_validate_msg(false,'#password',errorMsg.password)
                    }

                    if(errorMsg.register_error){
                        alert(errorMsg.register_error)
                    }

                    $('#register_save_btn').attr('status', 'prohibited');
                }
            },
            error: function () {
                alert('出错啦....')
            }
        })
    })
});

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

function checkUsername() {

    var $username = $('#username');
    var username = $username.val().trim();
    var regx = /^[A-Za-z0-9_-]{3,16}$/;

    if (regx.test(username)) {
        $.ajax({
            url: '/ssm1/user/checkUsername',
            data: {'username': username},
            type: 'post',
            success: function (result) {
                if (result.code === 200) {
                    $('#register_save_btn').attr('status', 'prohibited');
                    show_validate_msg(false, '#username', result.msg)
                } else {
                    $('#register_save_btn').attr('status', '');
                    show_validate_msg(true, '#username', '用户名可用')
                }
            },
            error: function () {
                alert('出错啦....')
            }
        })
    } else {
        $('#register_save_btn').attr('status', 'prohibited');
        show_validate_msg(false, '#username', "用户名只能由3~16位的英文及数字组合")
    }
}

function checkPassword() {
    var $password = $('#password');
    var password = $password.val().trim();
    var regx = /^[A-Za-z0-9_-]{6,18}$/;

    if (regx.test(password)) {
        $('#register_save_btn').attr('status', '');
        show_validate_msg(true, '#password', '')
    } else {
        $('#register_save_btn').attr('status', 'prohibited');
        show_validate_msg(false, '#password', "密码只能由6~18位的英文及数字组合")
    }
}