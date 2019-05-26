$(function () {

    //Url
    var getShopCategoryListUrl = "/adminManagement/getShopCategoryList";
    var modifyShopCategoryUrl = "/admin/modifyShopCategory";
    var deleteShopCategoryUrl = "/adminManagement/deleteShopCategory";

    //向后台获取ShopCategory
    function getShopCategoryList() {
        $.getJSON(getShopCategoryListUrl, function (data) {
            if (data.success) {

                //接受ShopCategoryList
                var shopCategoryList = data.shopCategoryList;
                var tempHtml = "";

                //遍历shopCategoryList
                shopCategoryList.map(function (item, index) {
                    //拼接每个shopCategory的信息
                    tempHtml += '<div class="row shopCategoryList-row">'
                        + '<div class="col-33">'
                        + item.shopCategoryName
                        + '</div>'
                        + '<div class="col-33">'
                        + item.priority
                        + '</div>'
                        + '<div class="col-33">'
                        + '<a href="#" class="external edit" '
                        + 'data-id="'
                        + item.shopCategoryId
                        + '">编辑</a> '
                        + '<a href="#" class="external delete" '
                        + 'data-id="'
                        + item.shopCategoryId
                        + '">删除</a>'
                        + '</div>'
                        + '</div>'
                });

                $(".shopCategoryList-wrap").html(tempHtml);
            } else {
                $.toast(data.errMsg);
            }
        });
    }

    getShopCategoryList();

    $('.shopCategoryList-wrap').on('click', 'a', function (e) {
        if ($(this).hasClass('edit')) {
            // 如果有class edit则进入点击商铺分类编辑信息页面，并且带有shopCategoryId
            window.location.href = modifyShopCategoryUrl + "?shopCategoryId="
                + $(this).attr("data-id");
        } else if ($(this).hasClass('delete')) {
            $.ajax({
                url: deleteShopCategoryUrl + "?shopCategoryId=" + $(this).attr("data-id"),
                type :'POST',
                success:function (data) {
                    if (data.success) {
                        getShopCategoryList();
                    } else {
                        $.toast(data.errMsg);
                    }
                }
            });
        }
    });
});