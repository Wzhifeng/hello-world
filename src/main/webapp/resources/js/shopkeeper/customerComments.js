$(function () {
    var getCustomerCommentsUrl = "/shopkeeperAdmin/getCunstomerComments";

    //初始化信息
    function initInfo() {
        $.getJSON(getCustomerCommentsUrl, function (data) {
            if(data.success){
                var tempHtml = "";
                var customerComments = data.customerComments;
                customerComments.map(function (item, index) {
                    tempHtml += '<div class="list-block"><ul><li class="item-content"><div class="item-media"><i class="icon icon-f7"></i></div>'
                        + '<div class="item-inner"><div class="item-title">商品名称</div><div class="item-after">'
                        + item.productName
                        + '</div></div></li>'
                        + '<li class="item-content"><div class="item-media"><i class="icon icon-f7"></i></div>'
                        + '<div class="item-inner"><div class="item-title">评价</div><div class="item-after">'
                        + item.commentContent
                        + '</div></div></li></ul></div>';
                });
                $("#customer-comments-list-wrap").html(tempHtml);
            } else {
                $.toast(data.errMsg);
            }
        });
    }

    initInfo();
});