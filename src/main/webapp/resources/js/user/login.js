$(function () {
    //各个跳转地址
    var loginUrl = "/userAdmin/login";
    var personalCenter = "/user/index";
    var shopkepperCenter = "/shopkeeper/shopkeeperCenter";
    var adminCenter = "/admin/adminCenter";

    //用户角色
    var admin = "0";
    var user = "1";
    var shopkeeper = "2";


    /**
     * 点击登录按钮后触发函数
     */
    $("#login").click(function () {
        //组装Person对象
        var person = {};
        person.account = $("#account").val();
        person.password = $("#password").val();
        var verifyCodeActual = $("#captcha").val();

        if (verifyCodeActual == null || verifyCodeActual == "") {
            $.toast('请输入验证码');
            return;
        }

        //拼接Person对象与验证码
        var formData = new FormData();
        formData.append('personStr', JSON.stringify(person));
        formData.append('verifyCodeActual', verifyCodeActual);

        //向后台提交登陆数据
        $.ajax({
                type: "POST",
                url: loginUrl,
                data: formData,
                contentType: false,
                processData: false,
                cache: false,
                success: function (data) {
                    if (data.success) {
                        var userType = data.userType;

                        if (userType == admin) {
                            window.location.href = adminCenter;
                        } else if (userType == shopkeeper) {
                            window.location.href = shopkepperCenter;
                        } else if (userType == user) {
                            window.location.href = personalCenter;
                        }

                    } else {
                        $("#captcha_img").click();
                        $.toast(data.errMsg);
                    }
                }
            }
        );
    });
});
