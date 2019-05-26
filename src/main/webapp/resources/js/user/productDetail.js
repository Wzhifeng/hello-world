$(function () {
    //获取productId
    var productId = getQueryString("productId");
    var getProductDetailAndCommentByProductIdUrl = "/userAdmin/getProductDetailAndCommentByProductId?productId=" + productId;
    var addPraiseUrl = "/userAdmin/addPraise?productId="+productId;
    var addShopCartUrl = "/userAdmin/addShopCart?productId=" + productId;


    function initInfo() {
        $.getJSON(getProductDetailAndCommentByProductIdUrl, function (data) {
            if (data.success) {
                var product = data.product;
                var commentList = data.commentList;
                var tempHtml = '';

                //填充页面信息
                $("#productName").text(product.productName);
                $("#productImg").attr("src", product.productImg);
                $("#productDesc").text(product.productDesc);
                $("#praise").text(product.praise);
                $("#sales").text(product.sales);

                commentList.map(function (item, index) {
                    tempHtml += '<div class="card">'
                        + '<div class="card-header">'
                        + item.commentUserName
                        + '</div>'
                        + '<div class="card-content">'
                        + '<div class="card-content-inner">'
                        + item.commentContent
                        + '</div>'
                        + '</div>'
                        + '</div>';
                });
                $("#tab2").html(tempHtml);
            }
        })
    }

    initInfo();

    //点赞
    $(".card-footer").on("click", "a:first-child", function () {
        $.getJSON(addPraiseUrl, function (data) {
            if (data.success) {
                $.confirm('谢谢您的支持', function () {
                    $("#praise").text(data.praise);
                });
            }
        });
    });

    //加入购物车
    $(document).on("click", "#addShopCart", function () {
        $.getJSON(addShopCartUrl, function (data) {
            if (data.success) {
                $.toast("添加购物车成功");
            } else {
                $.toast(data.errMsg);
            }
        });
    });

    //立即购买
    $(document).on("click", "#buy", function () {
        window.location.href = "clearing?productId="+productId+"&number=1";
    });
});