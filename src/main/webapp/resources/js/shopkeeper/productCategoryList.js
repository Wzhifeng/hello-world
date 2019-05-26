$(function () {
    var getProductCategoryListUrl = "/shopkeeperAdmin/getProductCategoryList";
    var modifyProductCategoryUrl = "/shopkeeper/modifyProductCategory";
    var deleteProductCategoryUrl = "/shopkeeperAdmin/deleteProductCategory";

    function getProductCategoryList() {
        $.getJSON(getProductCategoryListUrl, function (data) {
            if (data.success) {
                //接受productCategoryList
                var productCategoryList = data.productCategoryList;
                var tempHtml = "";

                //遍历productCategoryList
                productCategoryList.map(function (item, index) {
                    //拼接每个productCategory的信息
                    tempHtml += '<div class="row productCategoryList-row">'
                        + '<div class="col-33">'
                        + item.productCategoryName
                        + '</div>'
                        + '<div class="col-33">'
                        + item.priority
                        + '</div>'
                        + '<div class="col-33">'
                        + '<a href="#" class="external edit" '
                        + 'data-id="'
                        + item.productCategoryId
                        + '">编辑</a> '
                        + '<a href="#" class="external delete" '
                        + 'data-id="'
                        + item.productCategoryId
                        + '">删除</a>'
                        + '</div>'
                        + '</div>'
                });

                $(".productCategoryList-wrap").html(tempHtml);
            } else {
                $.toast(data.errMsg);
            }
        });
    }

    getProductCategoryList();

    $('.productCategoryList-wrap').on('click', 'a', function () {
        if ($(this).hasClass('edit')) {
            window.location.href = modifyProductCategoryUrl + "?productCategoryId="
                + $(this).attr("data-id");
        } else if ($(this).hasClass('delete')) {
            $.ajax({
                url: deleteProductCategoryUrl + "?productCategoryId=" + $(this).attr("data-id"),
                type: 'POST',
                success: function (data) {
                    if (data.success) {
                        getProductCategoryList();
                    } else {
                        $.toast(data.errMsg);
                    }
                }
            });
        }
    });
});