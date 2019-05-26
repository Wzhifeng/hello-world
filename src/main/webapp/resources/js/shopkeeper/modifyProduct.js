$(function () {
    //获取地址栏的productId
    var productId = getQueryString("productId");

    var getProductUrl = "/shopkeeperAdmin/getProduct?productId=" + productId;
    var updateProductUrl = "/shopkeeperAdmin/updateProduct";

    //向后台获取该商品的信息填充至页面
    function getProduct() {
        $.getJSON(getProductUrl, function (data) {
            if (data.success) {
                var product = data.product;
                $("#productName").val(product.productName);
                $("#productDesc").val(product.productDesc);
                $("#price").val(product.price);
                $("#priority").val(product.priority);


                //获取该商品的商品类别以及商品分类列表
                var productCategoryHtml = "";
                var selectedProdcutCategoryId = product.productCategoryId;
                var productCategoryList = data.productCategoryList;
                productCategoryList.map(function (item, index) {
                    var isSelected = selectedProdcutCategoryId === item.productCategoryId ?
                        'selected' : '';
                    productCategoryHtml += '<option value="'
                        + item.productCategoryId
                        + '"'
                        + isSelected
                        + '>'
                        + item.productCategoryName
                        + '</option>';
                });

                $("#productCategory").html(productCategoryHtml);
            } else {
                $.toast(data.errMsg);
            }
        });
    }

    getProduct();

    $("#submit").click(function () {
        var product = {};
        product.productId = productId;
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
            url: updateProductUrl,
            type: 'POST',
            data: formData,
            contentType: false,
            processData: false,
            cache: false,
            success: function (data) {
                if (data.success) {
                    window.location.href = "/shopkeeper/productList";
                } else {
                    $("#captcha_img").click()
                    $.toast(data.errMsg);
                }
            }
        });
    });

});