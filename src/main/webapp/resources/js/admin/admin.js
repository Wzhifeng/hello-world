$(function () {
    //获取UserId
    var userId = getQueryString('userId');

    //各个Url
    var addShopCategoryUrl = "/admin/addShopCategory";
    var shopCategoryListUrl = "/admin/shopCategoryList";
    var logoutUrl = "/userAdmin/logout";


    //点击增加商铺分类
    $("#addShopCategory").click(function () {
        window.location.href = addShopCategoryUrl;
    });

    //点击编辑商铺分类
    $("#shopCategoryList").click(function () {
        window.location.href = shopCategoryListUrl;
    });

    //点击退出后
    $("#logout").click(function () {
        $.ajax({
            url: logoutUrl,
            type : 'POST',
            success: function (data) {
                if (data.success) {
                    window.location.href = data.url;
                } else {
                    $.toast(data.errMsg);
                }
            }
        });
    });
});