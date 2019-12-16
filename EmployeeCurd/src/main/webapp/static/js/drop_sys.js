$(function () {
    $('#drop_sys').click(function () {
        if (confirm('确定要登出本系统吗？')) {
           $.ajax({
               type:'get',
               url:'/ssm1/user/userLogout',
               success:function (resp) {
                   if(resp.code === 100){
                       location.href = '/ssm1/login_bubble.html'
                   }else{
                       alert('退出系统失败。');
                   }
               }
           })
        }
        return false;
    })
});