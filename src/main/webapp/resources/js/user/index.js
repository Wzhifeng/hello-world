$(function () {
    $.init();

    //各个url
    var getHeadLineUrl = "/userAdmin/getHeadLine";
    var getShopCategoryListUrl = "/userAdmin/getShopCategoryList";

    function getHeadLine() {
        $.getJSON(getHeadLineUrl, function (data) {
            if (data.success) {
                //接受shopList
                var headLine1 = data.headLine1Shop;
                var headLine2 = data.headLine2Shop;
                var headLine3 = data.headLine3Shop;

                $("#headLine1").attr("src", headLine1.shopImg);
                $("#headLine2").attr("src", headLine2.shopImg);
                $("#headLine3").attr("src", headLine3.shopImg);

                $("#headLine1Shop").attr("href", "shopDetail?shopId=" + headLine1.shopId);
                $("#headLine2Shop").attr("href", "shopDetail?shopId=" + headLine2.shopId);
                $("#headLine3Shop").attr("href", "shopDetail?shopId=" + headLine3.shopId);
            } else {
                $.toast("页面加载异常");
            }
        });
    }

    function getShopCategoryList() {
        $.getJSON(getShopCategoryListUrl, function (data) {
            if (data.success) {
                var shopCategoryList = data.shopCategoryList;
                var tempHtml = '';

                shopCategoryList.map(function (item, index) {
                    tempHtml += '<div class="col-50">'
                        + '<div class="word">'
                        + '<a href="'
                        + 'shopList?shopCategoryId='
                        + item.shopCategoryId
                        + '" class="external">'
                        + '<p class="my-margin-0 my-font-weight my-font-color-black">'
                        + item.shopCategoryName
                        + '</p>'
                        + '<p class="my-margin-0 my-font-color-black">'
                        + '<small>'
                        + item.shopCategoryDesc
                        + '</small>'
                        + '</p>'
                        + '</a>'
                        + '</div>'
                        + '<div class="shop-category-img">'
                        + '<img src="'
                        + item.shopCategoryImg
                        + '" class="my-img-size"/>'
                        + '</div>'
                        + '</div>';
                });
            } else {
                $.toast("页面加载异常");
            }

            $("#shopCategoryList").html(tempHtml);
        });
    }

    getHeadLine();
    getShopCategoryList();
})
