$(function () {
    var prouctCategoryId = getQueryString("productCategoryId");
    var getProductCategoryUrl = "/shopkeeperAdmin/getProductCategory?productCategoryId=" + prouctCategoryId;
    var modifyProductCategoryUrl = "/shopkeeperAdmin/modifyProductCategory";
    var getProductCategoryListUrl = "/shopkeeper/productCategoryList";

    //获取商品分类信息
    function getProductCategory() {
        $.getJSON(getProductCategoryUrl, function (data) {
            if (data.success) {
                var productCategory = data.productCategory;
                $("#prodoctCategoryName").val(productCategory.productCategoryName);
                $("#priority").val(productCategory.priority);
            }
        });
    }

    getProductCategory();

    $("#submit").click(function () {
        var productCategory = {};
        productCategory.productCategoryId = prouctCategoryId;
        productCategory.productCategoryName = $("#prodoctCategoryName").val();
        productCategory.priority = $("#priority").val();

        var formData = new FormData();
        formData.append("productCategoryStr", JSON.stringify(productCategory));

        $.ajax({
            url: modifyProductCategoryUrl,
            contentType: false,
            processData: false,
            cache: false,
            data: formData,
            type: 'POST',
            success: function (data) {
                if (data.success) {
                    window.location.href = getProductCategoryListUrl;
                } else {
                    $.toast(data.errMsg);
                }
            }
        });
    });
});