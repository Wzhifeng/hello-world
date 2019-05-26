$(function () {
    var addProductCategoryUrl = "/shopkeeperAdmin/addProductCategory";


    $("#submit").click(function () {
        var productCategory = {};
        productCategory.productCategoryName = $("#prodoctCategoryName").val();
        productCategory.priority = $("#priority").val();

        var formData = new FormData();
        formData.append("productCategoryStr", JSON.stringify(productCategory));

        $.ajax({
            url: addProductCategoryUrl,
            contentType: false,
            processData: false,
            cache: false,
            data: formData,
            type: 'POST',
            success: function (data) {
                if (data.success) {
                    $.toast("新增商品分类成功");
                } else {
                    $.toast(data.errMsg);
                }
            }
        });
    });
});