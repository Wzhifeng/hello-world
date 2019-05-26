$(function () {
    $.init();

    var getShopListUrl = "/adminManagement/getShopList";
    var modifyHeadLineShopUrl = "/adminManagement/modifyHeadLineShop";
    var getHeadLineUrl = "/adminManagement/getHeadLine";

    //向后台获取该店铺的商品分类列表填充至页面
    function getShopList() {
        $.getJSON(getShopListUrl, function (data) {
            if (data.success) {
                //接受shopList
                var shopList = data.shopList;
                var tempHtml = "";

                //遍历shopList
                shopList.map(function (item, index) {
                    //拼接每个shopCategory的信息
                    tempHtml += '<option value="'
                        + item.shopId
                        + '">'
                        + item.shopName
                        + '</option>'
                });

                $(".shopList").html(tempHtml);
            } else {
                $.toast(data.errMsg);
            }
        });
    }

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

            } else {
                $.toast(data.errMsg);
            }
        });
    }

    getShopList();
    getHeadLine();


    $("#submit").click(function () {
        var headLine1Shop = $("#headLine1Shop").val();
        var headLine2Shop = $("#headLine2Shop").val();
        var headLine3Shop = $("#headLine3Shop").val();

        // 生成表单对象，用于接收参数并传递给后台
        var formData = new FormData();
        formData.append("headLine1Shop", headLine1Shop);
        formData.append("headLine2Shop", headLine2Shop);
        formData.append("headLine3Shop", headLine3Shop);
        $.ajax({
            url: modifyHeadLineShopUrl,
            type: 'POST',
            data: formData,
            contentType: false,
            processData: false,
            cache: false,
            success: function (data) {
                if (data.success) {
                    getHeadLine();
                } else {
                    $.toast(data.errMsg);
                }
            }
        });
    });
})
