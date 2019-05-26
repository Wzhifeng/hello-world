$(function () {

    //定义当前时间
    var nowDate = new Date();
    //获取商家的收入情况
    var getIncomeUrl = "/shopkeeperAdmin/getIncome";
    //获取商家的销量情况
    var getSalesUrl = "/shopkeeperAdmin/getSales";

    //查看收入
    $("#income-calendar").calendar({
        value: [nowDate],

        //选择完日期后更新日收入 月收入
        onClose: function () {
            //获取收入情况

            var date = new Date($("#income-calendar").val()).valueOf();
            var formData = new FormData();
            formData.append('date', JSON.stringify(date));
            $.ajax({
                    type: "POST",
                    url: getIncomeUrl,
                    data: formData,
                    contentType: false,
                    processData: false,
                    cache: false,
                    success: function (data) {
                        if (data.success) {
                            //保留两位小数
                            $("#allIncome").val(data.allIncome.toFixed(2));
                            $("#yearIncome").val(data.yearIncome.toFixed(2));
                            $("#monthIncome").val(data.monthIncome.toFixed(2));
                            $("#dayIncome").val(data.dayIncome.toFixed(2));
                        } else {
                            $.toast(data.errMsg);
                        }
                    }
                }
            );

        },
    });

    //查看销量
    $("#sales-calendar").calendar({
        value: [nowDate],

        onClose: function () {
            getSales();
        },
    });

    $("#salesScope").on('change', function () {
        getSales();
    });


    //获取商家销量情况
    function getSales() {
        //获取日期
        var date = new Date($("#sales-calendar").val()).valueOf();
        //获取销量范围
        var salesScope = $("select option:checked").attr("data-value");

        var formData = new FormData();
        formData.append('date', JSON.stringify(date));
        formData.append('salesScope', JSON.stringify(salesScope));
        $.ajax({
                type: "POST",
                url: getSalesUrl,
                data: formData,
                contentType: false,
                processData: false,
                cache: false,
                success: function (data) {
                    if (data.success) {
                        var tempHtml = '';
                        var productList = data.productList;
                        if (productList != null) {
                            productList.map(function (item, index) {
                                tempHtml += '<li><a href="#" class="item-link item-content external">'
                                    + '<div class="item-media"><img src="'
                                    + item.productImg
                                    + '" width="80"></div>'
                                    + '<div class="item-inner"><div class="item-title-row"><div class="item-title">'
                                    + item.productName
                                    + '</div><div class="item-after">销量：<span>'
                                    + item.sales
                                    + '</span></div></div><div class="item-text">'
                                    + item.productDesc
                                    + '</div></div></a></li>'
                            });
                            $('#product-sales-wrap').html(tempHtml);
                        }
                    } else {
                        $.toast(data.errMsg);
                    }
                }
            }
        );
    }
})