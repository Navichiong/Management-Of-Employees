$(function () {
    $(document).on('click', '.del-btn', function () {
        var empname = $(this).parents('tr').find('td:eq(2)').text();
        if (confirm('确定要删除员工' + empname + '吗？')) {

            $.ajax({
                url: '/ssm1/employee/emp/' + $(this).attr('emp_id'),
                type: 'delete',
                success: function (result) {
                    if (result.code === 100) {
                        to_page(currentPage)
                    }
                },
                error: function () {
                    alert('出错啦！')
                }
            })
        }
    });

    // 全选
    $('#check_all').click(function () {
        $('.signal_checkbox').prop('checked', $(this).prop('checked'))
    });

    // 单选
    $(document).on('click', '.signal_checkbox', function () {
        var flag = $('.signal_checkbox:checked').length === $('.signal_checkbox').length;
        $('#check_all').prop('checked', flag);
    });

    // 批量删除按钮
    $('#emp_del_btn').click(function () {

        var empNames = '';
        var empIds = '';
        $.each($('.signal_checkbox:checked'), function () {
            empNames += $(this).parents('tr').find('td:eq(2)').text() + ',';
            empIds += $(this).parents('tr').find('td:eq(1)').text() + '-'
        });

        empNames = empNames.substr(0, empNames.length - 1);
        empIds = empIds.substr(0, empIds.length - 1);

        if (!empNames) {
            alert('亲，你还未选中任何员工哦！');
            return;
        }
        if (confirm('确定要删除以下员工【' + empNames + '】吗？')) {
            $.ajax({
                url: '/ssm1/employee/emp/' + empIds,
                type: 'delete',
                success: function (result) {
                    if (result.code === 100) {
                        to_page(currentPage)
                    }
                },
                error: function () {
                    alert('出错啦！')
                }
            })
        }
    })
});