$(function () {

    var getOrderByShopUrl = "/shopkeeperAdmin/getOrderByShop";
    var changeOrderStatusUrl = "/shopkeeperAdmin/changeOrderStatus?orderNo=";


    function initOrder() {
        $.getJSON(getOrderByShopUrl, function (data) {
            if (data.success) {
                var orderList = data.orderList;
                var tempHtml = "";
                orderList.map(function (item, index) {
                    var repastWayText = "堂食";
                    if (item.repastWay == 1) {
                        repastWayText = "外带";
                    }
                    tempHtml += '<div class="card">'
                        + '<div class="card-header my-vertical-padding-0" id="username">'
                        + item.username
                        + '</div>'
                        + '<div class="card-content">'
                        + '<div class="list-block media-list">'
                        + '<ul>'
                        + '<li class="item-content">'
                        + '<div class="item-media">'
                        + '<img id="productImg" src="'
                        + item.productImg
                        + '" width="44">'
                        + '</div>'
                        + '<div class="item-inner">'
                        + '<div class="item-title-row">'
                        + '<div class="item-title" id="productName">'
                        + item.productName
                        + '</div>'
                        + '<div class="item-title">数量x<span id="number">'
                        + item.number
                        + '</span></div>'
                        + '</div>'
                        + '<div class="item-title-row">'
                        + '<div class="item-subtitle">备注：<span id="remark">'
                        + item.remark
                        + '</span></div>'
                        + '<div class="item-subtitle" id="repastWay">'
                        + repastWayText
                        + '</div>'
                        + '</div></div></li></ul></div></div>'
                        + '<div class="card-footer my-vertical-padding-0">'
                        + ' <span id="createTime">'
                        + getMyDate(item.createTime)
                        + '</span>'
                        + '<span><a href="#" id="changeOrderStatus" data-no="'
                        + item.orderNo
                        + '" class="button button-warning external">未完成订单</a></span>'
                        + '</div></div>';
                });
                $("#order-list-wrap").html(tempHtml);
            } else {
                $.toast(data.errMsg);
            }
        });
    }

    initOrder();

    $(document).on("click", "#changeOrderStatus", function () {
        $.getJSON(changeOrderStatusUrl + $(this).attr("data-no"),function (data) {
            if (data.success) {
                $.toast("订单已完成");
                initOrder();
            }else {
                $.toast(data.errMsg);
            }
        });
    });

    //日期函数
    function getMyDate(str) {
        var oDate = new Date(str),
            oYear = oDate.getFullYear(),
            oMonth = oDate.getMonth() + 1,
            oDay = oDate.getDate(),
            oHour = oDate.getHours(),
            oMin = oDate.getMinutes(),
            oSen = oDate.getSeconds(),
            oTime = oYear + '-' + getzf(oMonth) + '-' + getzf(oDay) + ' ' + getzf(oHour) + ':' + getzf(oMin) + ':' + getzf(oSen);//最后拼接时间
        return oTime;
    };

    //补0操作
    function getzf(num) {
        if (parseInt(num) < 10) {
            num = '0' + num;
        }
        return num;
    }
});