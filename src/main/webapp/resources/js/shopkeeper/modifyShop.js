$(function () {
    var getShopUrl = "/shopkeeperAdmin/getShop";
    var updateShopUrl = "/shopkeeperAdmin/updateShop";

    //向后台提取该商铺的信息填充至页面
    function getShop() {
        $.getJSON(getShopUrl, function (data) {
            if (data.success) {
                var shop = data.shop;
                $("#shopName").val(shop.shopName);
                $("#shopDesc").val(shop.shopDesc);
                $("#shopAddr").val(shop.shopAddr);
                $("#shopPhone").val(shop.shopPhone);

                //获取商铺所属类别以及商品分类列表
                var shopCategoryHtml = '';
                var selectedShopCategoryId = shop.shopCategoryId;
                var shopCategoryList = data.shopCategoryList;
                shopCategoryList.map(function (item, index) {
                    var isSelected = selectedShopCategoryId === item.shopCategoryId ?
                        'selected' : '';
                    shopCategoryHtml += '<option value="'
                        + item.shopCategoryId
                        + '"'
                        + isSelected
                        + '>'
                        + item.shopCategoryName
                        + '</option>';
                });

                //填充商铺分类
                $("#shopCategory").html(shopCategoryHtml);

            } else {
                $.toast(data.errMsg);
            }
        });

    }

    getShop();
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
            url: updateShopUrl,
            type: 'POST',
            data: formData,
            contentType: false,
            processData: false,
            cache: false,
            success: function (data) {
                if (data.success) {
                    $.toast("修改商铺信息成功！");
                } else {
                    $.toast(data.errMsg);
                    $("#captcha_img").click();
                }
            }
        });
    });

});

