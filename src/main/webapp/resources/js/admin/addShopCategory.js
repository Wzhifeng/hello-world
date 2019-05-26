$(function () {
    var addShopCategoryUrl = "/adminManagement/addShopCategory";

    //当提交按钮被点击时触发
    $("#submit").click(function () {
        //生成商铺类别对象
        var shopCategory = {};
        shopCategory.shopCategoryName = $("#shopCategoryName").val();
        shopCategory.shopCategoryDesc = $("#shopCategoryDesc").val();
        shopCategory.priority = $("#priority").val();

        //提取商铺类别图片
        var thumbnail = $("#shopCategoryImg")[0].files[0];

        // 生成表单对象，用于接收参数并传递给后台
        var formData = new FormData();

        // 将商铺类别对象转成字符流
        formData.append('shopCategoryStr', JSON.stringify(shopCategory));
        // 将商铺类别图片存入表单对象
        formData.append('thumbnail', thumbnail);

        //将数据提交至后台处理
        $.ajax({
            url: addShopCategoryUrl,
            type: 'POST',
            data: formData,
            contentType: false,
            processData: false,
            cache: false,
            success: function (data) {
                if (data.success) {
                    $.toast("添加商铺类别成功");
                } else {
                    $.toast(data.errMsg);
                }
            }
        });
    });


});