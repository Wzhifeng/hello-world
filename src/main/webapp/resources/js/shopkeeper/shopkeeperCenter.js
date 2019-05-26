$(function () {

    var canCreateShopUrl = "/shopkeeperAdmin/canCreateShop";
    var createShopUrl = "/shopkeeper/createShop";
    var logoutUrl = "/userAdmin/logout";
    var addProductCategoryUrl = "/shopkeeper/addProductCategory";
    var productCategoryListUrl = "/shopkeeper/productCategoryList";
    var addProductUrl = "/shopkeeper/addProduct";
    var productListUrl = "/shopkeeper/productList";
    var customerCommentsUrl = "/shopkeeper/customerComments";
    var financialStatementUrl = "/shopkeeper/financialStatement";

    //点击财务报表
    $("#financialStatement").click(function () {
        window.location.href = financialStatementUrl;
    });

    //点击客户评论
    $("#customerComments").click(function () {
        window.location.href = customerCommentsUrl;
    });

    //点击商品列表
    $("#productList").click(function () {
        window.location.href = productListUrl;
    });

    //点击新增商品
    $("#addProduct").click(function () {
        window.location.href = addProductUrl;
    });

    //点击新增商品分类
    $("#addProductCategory").click(function () {
        window.location.href = addProductCategoryUrl;
    });

    //点击商品分类列表
    $("#productCategoryList").click(function () {
        window.location.href = productCategoryListUrl;
    });

    //点击创建
    $("#createShop").click(function () {
        //判断是否已经拥有商铺
        $.ajax({
            url: canCreateShopUrl,
            type: 'POST',
            success: function (data) {
                //没有则可以创建
                if (data.success) {
                    window.location.href = createShopUrl;
                } else {
                    //已经拥有则不可创建
                    $.toast(data.errMsg);
                }
            }
        });
    });

    //点击退出后
    $("#logout").click(function () {
        $.ajax({
            url: logoutUrl,
            type: 'POST',
            success: function (data) {
                if (data.success) {
                    window.location.href = data.url;
                } else {
                    $.toast(data.errMsg);
                }
            }
        });
    });


    // var inOperation = '营业中';
    // var outOperation = '休息中';
    //
    // $(document).on("click", "#business", function () {
    //
    //     //如果营业中则变成休息,休息则变成营业
    //     if ($("#business").text() == inOperation) {
    //         $("#business").text(outOperation);
    //         $(".page").addClass("theme-dark");
    //     } else {
    //         $("#business").text(inOperation);
    //         $(".page").removeClass("theme-dark");
    //     }
    // });
})