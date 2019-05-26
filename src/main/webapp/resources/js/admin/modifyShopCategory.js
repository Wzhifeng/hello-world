$(function () {
    var shopCategoryId = getQueryString("shopCategoryId");
    var getShopCategoryUrl = "/adminManagement/getShopCategory?shopCategoryId="+shopCategoryId;
    var modifyShopCategoryUrl = "/adminManagement/updateShopCategory";

    //向后台提取该商铺分类的信息填充至页面
    function getShopCateogry() {
        $.getJSON(getShopCategoryUrl,function (data) {
            if (data.success) {
                var shopCategory = data.shopCategory;
                $("#shopCategoryName").val(shopCategory.shopCategoryName);
                $("#shopCategoryDesc").val(shopCategory.shopCategoryDesc);
                $("#priority").val(shopCategory.priority);
            }else {
                $.toast(data.errMsg);
            }
        })
    }

    getShopCateogry();

    //当提交按钮被点击时触发
    $("#submit").click(function () {
        //生成商铺类别对象
        var shopCategory = {};
        shopCategory.shopCategoryId = shopCategoryId;
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
            url: modifyShopCategoryUrl,
            type: 'POST',
            data: formData,
            contentType: false,
            processData: false,
            cache: false,
            success: function (data) {
                if (data.success) {
                    $.toast("更新商铺类别成功");
                } else {
                    $.toast(data.errMsg);
                }
            }
        });
    });
});