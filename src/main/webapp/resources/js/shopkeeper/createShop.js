$(function () {
    //各个Url
    var getShopCategoryListUrl = "/shopkeeperAdmin/getShopCategoryList";
    var registerShopUrl = "/shopkeeperAdmin/registerShop";
    var shopkepperCenter = "/shopkeeper/shopkeeperCenter";

    //向后台查询商铺分类列表
    function getShopCategoryList() {
        $.getJSON(getShopCategoryListUrl, function (data) {
            if (data.success) {

                //接受ShopCategoryList
                var shopCategoryList = data.shopCategoryList;
                var tempHtml = "";

                //遍历shopCategoryList
                shopCategoryList.map(function (item, index) {
                    //拼接每个shopCategory的信息
                    tempHtml += '<option value="'
                        + item.shopCategoryId
                        + '">'
                        + item.shopCategoryName
                        + '</option>'
                });

                $("#shopCategory").html(tempHtml);
            } else {
                $.toast(data.errMsg);
            }
        });
    };

    getShopCategoryList();

    //点击提交向后台提交数据
    $("#submit").click(function () {
        // 生成商铺对象
        var shop = {};
        shop.shopCategoryId = $("#shopCategory").val();
        shop.shopName = $("#shopName").val();
        shop.shopDesc = $("#shopDesc").val();
        shop.shopAddr = $("#shopAddr").val();
        shop.shopPhone = $("#shopPhone").val();

        //商铺图片
        var thumbnail = $("#shopImg")[0].files[0];

        //验证码
        var verifyCodeActual = $("#captcha").val();

        // 生成表单对象，用于组装参数并传递给后台
        var formData = new FormData();
        formData.append("shopStr", JSON.stringify(shop));
        formData.append("thumbnail", thumbnail);
        formData.append("verifyCodeActual", verifyCodeActual);

        $.ajax({
            url: registerShopUrl,
            type: 'POST',
            data: formData,
            contentType: false,
            processData: false,
            cache: false,
            success: function (data) {
                if (data.success) {
                    window.location.href = shopkepperCenter;
                } else {
                    $.toast(data.errMsg);
                }
            }
        });
    });
});