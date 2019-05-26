$(function () {
    //获取shopCategoryId
    var shopCategoryId = getQueryString("shopCategoryId");
    var getShopListUrl = "/userAdmin/getShopList";


    function getShopList() {
        var shop = {};
        var text = $("#search").val();
        if(text==''){
            text = null;
        }
        shop.shopCategoryId = shopCategoryId;
        shop.shopName = text;
        shop.shopDesc = text;
        shop.shopAddr = text;

        var formData = new FormData();
        formData.append("shopStr", JSON.stringify(shop));

        $.ajax({
            type: 'POST',
            url: getShopListUrl,
            data: formData,
            contentType: false,
            processData: false,
            success: function (data) {
                if (data.success) {
                    var shopList = data.shopList;
                    var tempHtml = '';

                    shopList.map(function (item, index) {
                        tempHtml += '<div class="card">'
                            + '<a class="card-content external" href="shopDetail?shopId='
                            + item.shopId
                            + '">'
                            + '<div class="list-block media-list">'
                            + '<ul>'
                            + '<li class="item-content item-link">'
                            + '<div class="item-media">'
                            + '<img src="'
                            + item.shopImg
                            + '" class="my-img-size">'
                            + '</div>'
                            + '<div class="item-inner">'
                            + '<div class="item-title-row">'
                            + '<div class="item-title my-font-color-black">'
                            + item.shopName
                            + '</div>'
                            + '</div>'
                            + '<div class="item-subtitle color-gray my-margin-top">'
                            + item.shopDesc
                            + '</div>'
                            + '</div></li></ul></div></a></div>'
                            + ''
                    });

                    $("#shop-list-wrap").html(tempHtml);
                } else {
                    $.toast(data.errMsg);
                }
            }
        });
    }

    getShopList();

    $("#search").change(function () {
        getShopList();
    })

});