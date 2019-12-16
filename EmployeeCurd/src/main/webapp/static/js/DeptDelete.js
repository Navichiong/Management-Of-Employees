$(function () {
    $(document).on('click', '.delBtn', function () {
        var deptName = $(this).parents('tr').find('td:eq(2)').text();
        if (confirm('确定要删除部门【' + deptName + '】吗？')) {
            $.ajax({
                url: '/ssm1/department/deleteDept/' + $(this).attr('dept-id'),
                type: 'delete',
                success: function (result) {
                    if (result.code === 100) {
                        to_dept_page(deptCurrentPage)
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
        $('.singleCheck').prop('checked', $(this).prop('checked'))
    });

    // 单选
    $(document).on('click', '.singleCheck', function () {
        var flag = $('.singleCheck:checked').length === $('.singleCheck').length;
        $('#check_all').prop('checked', flag);
    });

    // 批量删除按钮
    $('#dept_del_btn').click(function () {

        var deptNames = '';
        var deptIds = '';
        $.each($('.singleCheck:checked'), function () {
            deptNames += $(this).parents('tr').find('td:eq(2)').text() + ',';
            deptIds += $(this).parents('tr').find('td:eq(1)').text() + '-'
        });

        deptNames = deptNames.substr(0, deptNames.length - 1);
        deptIds = deptIds.substr(0, deptIds.length - 1);

        if (!deptNames) {
            alert('亲，你还未选中任何部门哦！');
            return;
        }
        if (confirm('确定要删除以下部门【' + deptNames + '】吗？')) {
            $.ajax({
                url: '/ssm1/department/deleteDept/' + deptIds,
                type: 'delete',
                success: function (result) {
                    if (result.code === 100) {
                        to_dept_page(deptCurrentPage)
                    }
                },
                error: function () {
                    alert('出错啦！')
                }
            })
        }
    })
});