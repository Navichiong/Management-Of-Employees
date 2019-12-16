$(function () {
    $('#drop_sys').click(function () {
        if (confirm('确定要登出本系统吗？')) {
           $.ajax({
               type:'get',
               url:'/ssm1/user/userLogout'
           })
        }
        return false;
    })
});