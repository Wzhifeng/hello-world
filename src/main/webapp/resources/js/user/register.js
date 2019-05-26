$(function () {

    var resgisterUrl = "/userAdmin/register";
    var loginUrl = "/user/login";

    /**
     * 点击提交按钮后
     */
    $("#submit").click(function () {
        //组装Person对象
        var person = {};
        person.userType = $("#user-type").val();
        person.account = $("#account").val();
        person.username = $("#username").val();
        person.password = $("#password").val();
        person.email = $("#email").val();
        person.phone = $("#phone").val();
        person.gender = $("#gender").val();
        person.question = $("#question").val();
        person.answer = $("#answer").val();

        //验证码
        var verifyCodeActual = $("#captcha").val();

        if (verifyCodeActual == null) {
            $.toast('请输入验证码');
            return;
        }

        //拼接Person对象与验证码
        var formData = new FormData();
        formData.append('personStr', JSON.stringify(person));
        formData.append('verifyCodeActual', verifyCodeActual);

        //将数据提交至后台
        $.ajax({
            url: resgisterUrl,
            type: 'POST',
            data: formData,
            contentType: false,
            processData: false,
            cache: false,
            success: function (data) {
                if (data.success) {
                    $.toast(data.sucMsg);
                    window.location.href = loginUrl;
                } else {
                    $('#captcha_img').click();
                    $.toast(data.errMsg);
                }
            }
        });
    });

    /**
     * 点击重置按钮后,将表单数据清空
     */
    $("#reset").click(function () {
        $("#account").val("");
        $("#username").val("");
        $("#password").val("");
        $("#email").val("");
        $("#phone").val("");
        $("#question").val("");
        $("#answer").val("");
        $("#captcha").val("");
    });

})