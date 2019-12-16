function checkLogin() {

    var flag = false;
    alert('发请求');
    $.ajax({
        url:'/ssm1/employee/getEmps',
        type:'get',
        async : false,
        success:function (result) {
            if(result.code === 200){
                alert(result.msg);
            }else{
                flag = true;
            }
        }
    });
    return flag;
}