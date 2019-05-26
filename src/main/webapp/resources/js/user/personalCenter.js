$(function(){
    var logoutUrl = "/userAdmin/logout";

	$("#conversion-history").on("click", function(){
		$.toast("该功能尚未开启，敬请期待~");
	});


	//退出登录
    $("#logout").click(function () {
        $.ajax({
            url: logoutUrl,
            type: 'POST',
            success: function (data) {
                if (data.success) {
                    window.location.href = data.url;
                } else {
                    $.toast(data.errMsg);
                }
            }
        });
    });
})
