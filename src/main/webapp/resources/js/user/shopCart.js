$(function () {

    var getShopCartByUserUrl = "/userAdmin/getShopCartByUser";
    var deleteShopCartByUserUrl = "/userAdmin/deleteShopCartByUser";

    //获取购物车信息
    function initInfo() {
        $.getJSON(getShopCartByUserUrl, function (data) {
            if (data.success) {
                var tempHtml = "";
                var shopCartList = data.shopCartList;

                shopCartList.map(function (item, index) {
                    tempHtml += '<li><label class="label-checkbox item-content">'
                        + '<input type="radio" name="checkbox"  data-id="'
                        + item.productId
                        + '" data-num="'
                        + item.number
                        + '">'
                        + '<div class="item-media"><i class="icon icon-form-checkbox my-margin-right"></i></div>'
                        + '<div class="item-media my-margin-left">'
                        + '<img src="'
                        + item.productImg
                        + '" class="my-img-size"/></div>'
                        + '<div class="item-inner"><div class="item-title-row">'
                        + '<div class="item-title">'
                        + item.productName
                        + '</div>'
                        + '<div class="item-after">￥'
                        + item.price
                        +'</div>'
                        + '</div><div class="item-title-row my-margin-top">'
                        + '<div class="item-subtitle color-gray">'
                        + item.productDesc
                        + '</div>'
                        + '<small>'
                        + '<div class="item-after">数量:<span>'
                        + item.number
                        + '</span></div>'
                        + '</small></div></div></label></li>';
                });

                $("#shop-cart-list-wrap").html(tempHtml);
            } else {
                $.toast(data.errMsg);
            }
        })
    }

    initInfo();

    $(document).on('click', '.create-actions', function () {
        var buttons1 = [{
            text: '请选择',
            label: true
        },
            {
                text: '结算',
                onClick: function () {
                    var number = $('input:checked').attr("data-num");
                    var productId = $('input:checked').attr("data-id");
                    window.location.href = "clearing?productId="+productId+"&number="+number;
                }
            },
            {
                text: '删除',
                bold: true,
                color: 'danger',
                onClick: function () {
                    var productId = $('input:checked').attr("data-id");
                    $.getJSON(deleteShopCartByUserUrl + "?productId=" + productId, function (data) {
                        if(data.success) {
                            initInfo();
                            $.toast("删除成功");
                        }
                    });
                }
            }
        ];
        var buttons2 = [{
            text: '取消',
            bg: 'danger'
        }];
        var groups = [buttons1, buttons2];
        $.actions(groups);
    });
})