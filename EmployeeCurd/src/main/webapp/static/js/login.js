$(function () {

    checkIsLogin();

    $(".txtb input").focus(function () {
        $(this).addClass("focus");
    });

    $(".txtb input").blur(function () {
        if ($(this).val() === "")
            $(this).removeClass("focus");
    });

    $("#chk_img").click(function () {
        var date = new Date();
        $(this).attr("src", "/ssm1/checkCode/imgHandle?time=" + date);
    });

    $('.logbtn').click(function () {
        var username = $('#username').val();
        var regx = /^[A-Za-z0-9_-]{3,16}$/;
        console.log(username);
        console.log(regx.test(username));
        if (regx.test(username)) {
            $('#username').tooltip('hide');
        } else {
            $('#username').tooltip('show');
            return false;
        }

        var password = $('#password').val();
        var regx = /^[A-Za-z0-9_-]{6,18}$/;
        console.log(password);
        console.log(regx.test(password));
        if (regx.test(password)) {
            $('#password').tooltip('hide')
        } else {
            $('#password').tooltip('show');
            return false;
        }

        $.ajax({
            url: '/ssm1/user/login',
            data: $('.login-form').serialize(),
            async: false,
            type: 'post',
            success: function (result) {
                console.log(result);
                if (result.code === 100) {
                    location.href = "/ssm1/list.html";
                } else {
                    alert(result.msg);
                    //刷新验证码
                    $('#chk_img').click();
                }
            },
            error: function () {
                alert('出错了....')
            }
        })
    })
});

function checkIsLogin() {

    $.ajax({
        url: '/ssm1/user/isLogin',
        type: 'get',
        success: function (result) {
            console.log(result);
            if (result.code === 100) {
                location.href = "/ssm1/list.html";
            } else {
                if(result.data_return.isLogin === 'N'){
                    alert('你还未登录，请先登录！')
                }
            }
        },
        error: function () {
            alert('error....')
        }
    });
}