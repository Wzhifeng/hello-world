/**
 * 通用js代码
 */


/**
 * 点击更换验证码
 * @param img
 */
function changeVerifyCode(img) {
    img.src = "/Kaptcha?" + Math.floor(Math.random() * 100);
}

/**
 * 获取地址栏的Id
 * @param name
 * @returns {string}
 */
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) {
        return decodeURIComponent(r[2]);
    }
    return '';
}