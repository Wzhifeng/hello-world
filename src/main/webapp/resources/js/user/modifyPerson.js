$(function () {
    var getPersonInfoUrl = "/userAdmin/getPersonInfo";
    var modifyPersonUrl = "/userAdmin/modifyPerson";

    function initPersonInfo() {
        $.getJSON(getPersonInfoUrl, function (data) {
            if(data.success) {
                var person = data.person;
                $("#username").val(person.username);
                $("#email").val(person.email);
                $("#phone").val(person.phone);
                $("#question").val(person.question);
                $("#answer").val(person.answer);
            } else {
                window.location.href = "login";
            }
        });
    }

    initPersonInfo();

    $(document).on("click", "#submit", function () {
        var person = {};
        person.username = $("#username").val();
        person.password = $("#password").val();
        person.email = $("#email").val();
        person.phone = $("#phone").val();
        person.question = $("#question").val();
        person.answer = $("#answer").val();

        var formData = new FormData();
        formData.append("personStr", JSON.stringify(person));

        $.ajax({
            url: modifyPersonUrl,
            type: 'POST',
            data: formData,
            contentType: false,
            processData: false,
            cache: false,
            success:function (data) {
                if(data.success) {
                    $.alert("修改成功");
                    window.location.href = "personalCenter";
                }
            }
        });

    });
});