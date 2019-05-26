$(function () {
    var getOrderListByUserUrl = "/userAdmin/getOrderListByUser";
    var addCommentUrl = "/userAdmin/addComment";

    //获取消费记录
    function initInfo() {
        $.getJSON(getOrderListByUserUrl, function (data) {
            if (data.success) {
                var purchaseHistoryList = data.purchaseHistoryList;
                var tempHtml = "";
                //遍历每个order
                purchaseHistoryList.map(function (item, index) {
                    tempHtml += '<div class="card">'
                        + '<div class="card-header my-vertical-padding-0">'
                        + '<a class="external my-font-color-black" href="#">'
                        + item.shopName
                        + '</a></div>'
                        + '<div class="card-content"><div class="list-block media-list">'
                        + '<ul><li class="item-content"><div class="item-media">'
                        + '<img src="'
                        + item.productImg
                        + '" width="44"></div>'
                        + '<div class="item-inner"><div class="item-title-row"><div class="item-title">'
                        + item.productName
                        + '</div><div class="item-after">￥'
                        + item.price
                        + '</div></div>'
                        + '<div class="item-title-row"><div class="item-subtitle color-gray">'
                        + item.productDesc
                        + '</div>'
                        + '<div class="item-after"><small>数量x'
                        + item.number
                        + '</small></div></div></div></li></ul></div></div>'
                        + '<div class="card-footer my-vertical-padding-0">'
                        + '<span>'
                        + getMyDate(item.createTime)
                        + '</span>'
                        + '<span><a href="#" data-id="'
                        + item.productId
                        + '" class="button button-warning external prompt-ok">评价</a></span></div></div>';
                });
                $(".content").html(tempHtml);
            }else {
                window.location.href = "login";
            }
        })
    }

    initInfo();


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

    $(document).on('click', '.prompt-ok', function () {
        var productId = $(this).attr("data-id");
        $.prompt('请输入您的评价', function (value) {
            var comment = {};
            comment.productId = productId;
            comment.commentContent = value;
            var formData = new FormData();
            formData.append("commentStr", JSON.stringify(comment));
            $.ajax({
                type: 'POST',
                url: addCommentUrl,
                contentType: false,
                processData: false,
                cache: false,
                dataType: 'json',
                data: formData,
                success: function (data) {
                    if (data.success) {
                        $.toast("感谢您的评价，我们会做得更好~");
                    }
                }
            });
        });
    });
});