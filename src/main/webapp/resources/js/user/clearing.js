$(function () {
    //获取productId number
    var productId = getQueryString("productId");
    var number = getQueryString("number");

    var getProductUrl = "/userAdmin/getProduct?productId=" + productId;
    var createOrderUrl = "/userAdmin/createOrder";
    var repastWay = 0;

    function initInfo() {
        $.getJSON(getProductUrl, function (data) {
            if (data.success) {
                var product = data.product;

                $("#productImg").attr("src", product.productImg);
                $("#productName").text(product.productName);
                $("#productDesc").text(product.productDesc);
                $("#price").text(product.price);
                $("#number").text(number);
            }
        })
    }

    initInfo();

    //选择使用方式 1外卖 0堂食
    $(document).on("click", "a", function () {
        $(".repast-way").removeClass("button-fill");
        $(this).addClass("button-fill");
    });


    $("#submit").click(function () {
        //组装order数据
        var order = {};
        order.productId = productId;
        order.number = number;
        order.remark = $("#remark").val();

        if ($("#takeout").hasClass("button-fill")) {
            order.repastWay = $("#takeout").attr("data-value");
        } else {
            order.repastWay = repastWay;
        }

        var formData = new FormData();
        formData.append("orderStr", JSON.stringify(order));

        $.ajax({
            type: "POST",
            url: createOrderUrl,
            data: formData,
            contentType: false,
            processData: false,
            cache: false,
            success: function (data) {
                if (data.success) {
                    $.toast("下单成功 请稍后前往商铺取商品~");
                    window.location.href = "/user/index";
                } else {
                    $.toast(data.errMsg);
                }
            }
        });
    });

});