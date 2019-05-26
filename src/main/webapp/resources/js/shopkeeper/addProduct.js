$(function () {
    var addProductUrl = "/shopkeeperAdmin/addProduct";
    var getProductCategoryListUrl = "/shopkeeperAdmin/getProductCategoryList";

    //向后台获取该店铺的商品分类列表填充至页面
    function getProductCategoryList() {
        $.getJSON(getProductCategoryListUrl, function (data) {
            if (data.success) {

                //接受ProductCategoryList
                var productCategoryList = data.productCategoryList;
                var tempHtml = "";

                //遍历shopCategoryList
                productCategoryList.map(function (item, index) {
                    //拼接每个shopCategory的信息
                    tempHtml += '<option value="'
                        + item.productCategoryId
                        + '">'
                        + item.productCategoryName
                        + '</option>'
                });

                $("#productCategory").html(tempHtml);
            } else {
                $.toast(data.errMsg);
            }
        });
    }

    getProductCategoryList();


    //点击提交后
    $("#submit").click(function () {
        //组装product数据
        var product = {};
        product.productCategoryId = $("#productCategory").val();
        product.productName = $("#productName").val();
        product.productDesc = $("#productDesc").val();
        product.price = $("#price").val();
        product.priority = $("#priority").val();

        //组装验证码
        var verifyCodeActual = $("#captcha").val();

        //组装图片
        var thumbnail = $("#productImg")[0].files[0];

        var formData = new FormData();
        formData.append("productStr", JSON.stringify(product));
        formData.append('thumbnail', thumbnail);
        formData.append("verifyCodeActual", verifyCodeActual);

        //将数据提交至后台处理
        $.ajax({
            url: addProductUrl,
            type: 'POST',
            data: formData,
            contentType: false,
            processData: false,
            cache: false,
            success: function (data) {
                if (data.success) {
                    $.toast("添加商品成功");
                    $("#captcha_img").click();
                } else {
                    $.toast(data.errMsg);
                    $("#captcha_img").click();
                }
            }
        });
    });
});