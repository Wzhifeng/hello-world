$(function () {
    var shopId = getQueryString("shopId");

    var getShopDetailInfoUrl = "/userAdmin/getShopDetailInfo?shopId=" + shopId;
    var getProductByProductCategoryUrl = "/userAdmin/getProductByProductCategory";

    initInfo();

    //初始店铺信息
    function initInfo() {
        $.getJSON(getShopDetailInfoUrl, function (data) {
            if (data.success) {
                var shop = data.shop;
                var productCategoryList = data.productCategoryList;
                var productList = data.productList;

                //填充商铺信息
                $("#shopName").text(shop.shopName);
                $("#shopImg").attr("src", shop.shopImg);
                $("#shopDesc").text(shop.shopDesc);
                $("#shopAddr").text(shop.shopAddr);
                $("#shopPhone").text(shop.shopPhone);

                var productCategoryListHtml = '';
                var productListHtml = '';

                //填充商品分类信息
                productCategoryList.map(function (item, index) {
                    productCategoryListHtml += '<div class="col-33">'
                        + '<a href="#"'
                        + ' data-id="'
                        + item.productCategoryId
                        + '" class="button my-margin-top productCategory">'
                        + item.productCategoryName
                        + '</a>'
                        + '</div>';
                });

                //填充商品列表
                productList.map(function (item, index) {
                    productListHtml += '<li>'
                        + '<a href="productDetail?productId='
                        + item.productId
                        + '" class="item-link item-content external">'
                        + '<div class="item-media">'
                        + '<img src="'
                        + item.productImg
                        + '"width="80" height="80">'
                        + '</div>'
                        + '<div class="item-inner">'
                        + '<div class="item-title-row">'
                        + '<div class="item-title">'
                        + item.productName
                        + '</div>'
                        + '<div class="item-after">￥'
                        + item.price
                        + '</div>'
                        + '</div>'
                        + '<div class="item-subtitle my-margin-top color-gray">'
                        + item.productDesc
                        + '</div>'
                        + '</div>'
                        + '</a>'
                        + '</li>';
                });

                $("#product-category-list-wrap").html(productCategoryListHtml);
                $("#product-list-wrap").html(productListHtml);
            }
        });
    }

    //点击商品类别
    $("#product-category-list-wrap").on("click", "a", function () {
        $(".productCategory").removeClass("button-fill");
        $(this).addClass("button-fill");


        $.getJSON(getProductByProductCategoryUrl + "?productCategoryId=" + $(this).attr("data-id"), function (data) {
            if (data.success) {
                var productList = data.productList;
                var productListHtml = '';
                //重新填充商品列表

                productList.map(function (item, index) {
                    productListHtml += '<li>'
                        + '<a href="productDetail?productId='
                        + item.productId
                        + '" class="item-link item-content external">'
                        + '<div class="item-media">'
                        + '<img src="'
                        + item.productImg
                        + '"width="80" height="80">'
                        + '</div>'
                        + '<div class="item-inner">'
                        + '<div class="item-title-row">'
                        + '<div class="item-title">'
                        + item.productName
                        + '</div>'
                        + '<div class="item-after">￥'
                        + item.price
                        + '</div>'
                        + '</div>'
                        + '<div class="item-subtitle my-margin-top color-gray">'
                        + item.productDesc
                        + '</div>'
                        + '</div>'
                        + '</a>'
                        + '</li>';
                });

                $("#product-list-wrap").html(productListHtml);

            }
        })
    });

});