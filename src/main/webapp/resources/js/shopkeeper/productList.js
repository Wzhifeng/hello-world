$(function () {
    var getProductListUrl = "/shopkeeperAdmin/getProductList";
    var modifyProductUrl = "/shopkeeper/modifyProduct";
    var deleteProductUrl = "/shopkeeperAdmin/deleteProduct";
    var changeProductStatus = "/shopkeeperAdmin/changeProductStatus";

    function getProductList() {
        //向后台获取商品列表
        $.getJSON(getProductListUrl, function (data) {
            if (data.success) {
                //接受productList
                var productList = data.productList;
                var tempHtml = "";

                //遍历productList
                productList.map(function (item, index) {
                    var textOp = "下架";
                    var productStatus = 0;

                    if (item.productStatus == 0) {
                        textOp = "上架";
                        productStatus = 1;
                    } else {
                        productStatus = 0;
                    }

                    //拼接每个product的信息
                    tempHtml += '<div class="row productList-row">'
                        + '<div class="col-33">'
                        + item.productName
                        + '</div>'
                        + '<div class="col-33">'
                        + item.priority
                        + '</div>'
                        + '<div class="col-33">'
                        + '<a href="#" class="external edit" '
                        + 'data-id="'
                        + item.productId
                        + '">编辑</a> '
                        + '<a href="#" class="external status" '
                        + 'data-id="'
                        + item.productId
                        + '" data-status="'
                        + productStatus
                        + '">'
                        + textOp
                        + '</a> '
                        + '<a href="#" class="external delete" '
                        + 'data-id="'
                        + item.productId
                        + '">删除</a>'
                        + '</div>'
                        + '</div>'
                });

                $(".productList-wrap").html(tempHtml);
            } else {
                $.toast(data.errMsg);
            }
        });
    }

    getProductList();

    $('.productList-wrap').on('click', 'a', function (e) {
        if ($(this).hasClass('edit')) {
            // 如果有class edit则进入点击店铺编辑信息页面，并且带有productId
            window.location.href = modifyProductUrl + "?productId="
                + $(this).attr("data-id");
        } else if ($(this).hasClass('status')) {
            // 如果有class status则调用后台上下架相关商品，并带有productId参数
            $.ajax({
                url: changeProductStatus + "?productId=" + $(this).attr("data-id"),
                type :'POST',
                success:function (data) {
                    if (data.success) {
                        getProductList();
                    } else {
                        $.toast(data.errMsg);
                    }
                }
            })
        } else if ($(this).hasClass('delete')) {
            $.ajax({
                url: deleteProductUrl + "?productId=" + $(this).attr("data-id"),
                type :'POST',
                success:function (data) {
                    if (data.success) {
                        getProductList();
                    } else {
                        $.toast(data.errMsg);
                    }
                }
            });
        }
    });

});