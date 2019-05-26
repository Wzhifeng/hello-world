$(function () {
    var getShopListUrl = "/adminManagement/getShopList";
    var changeShopStatusUrl = "/adminManagement/changeShopStatus";

    function initInfo() {
        //向后台获取数据
        $.getJSON(getShopListUrl, function (data) {
            if (data.success) {
                var shopList = data.shopList;
                var tempHtml = "";
                shopList.map(function (item, index) {
                    var statusText = "通过";
                    var textOp = "下架";
                    if (item.enableStatus == 0) {
                        statusText = "等待审核";
                        textOp = "通过";
                    }
                    tempHtml += '<div class="row shop-row">'
                        + '<div class="col-33">'
                        + item.shopName
                        + '</div>'
                        + '<div class="col-33">'
                        + statusText
                        + '</div>'
                        + '<div class="col-33">'
                        + '<a id="changeShopStatus" href="#" class="external" data-id="'
                        + item.shopId
                        + '">'
                        + textOp
                        + '</a>'
                        + '</div>'
                        + '</div>';
                });

                $(".shopList-wrap").html(tempHtml);
            } else {
                $.toast(data.errMsg);
            }
        })
    }

    initInfo();

    //点击商品操作
    $(document).on('click', "#changeShopStatus", function () {
        $.ajax({
            url: changeShopStatusUrl + "?shopId=" + $(this).attr("data-id"),
            type: 'POST',
            success: function (data) {
                if (data.success) {
                    initInfo();
                }
            }
        });
    });
});