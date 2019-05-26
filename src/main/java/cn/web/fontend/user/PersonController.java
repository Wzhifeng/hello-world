package cn.web.fontend.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 用户前台转发类
 */
@Controller
@RequestMapping(value = "/user/", method = RequestMethod.GET)
public class PersonController {

    @RequestMapping(value = "register")
    public String register() {
        return "user/register";
    }

    @RequestMapping(value = "login")
    public String login() {
        return "user/login";
    }

    @RequestMapping(value = "index")
    public String index() {
        return "user/index";
    }

    @RequestMapping(value = "shopCart")
    public String shopCart() {
        return "user/shopCart";
    }

    @RequestMapping(value = "personalCenter")
    public String personalCenter() {
        return "user/personalCenter";
    }

    @RequestMapping(value = "shopList")
    public String shopList() {
        return "user/shopList";
    }

    @RequestMapping(value = "shopDetail")
    public String shopDetail() {
        return "user/shopDetail";
    }

    @RequestMapping(value = "productDetail")
    public String productDetail() {
        return "user/productDetail";
    }

    @RequestMapping(value = "clearing")
    public String clearing() {
        return "user/clearing";
    }

    @RequestMapping(value = "modifyPerson")
    public String modifyPerson() {
        return "user/modifyPerson";
    }


    @RequestMapping(value = "purchaseHistory")
    public String purchaseHistory() {
        return "user/purchaseHistory";
    }


    @RequestMapping(value = "forgetPassword")
    public String forgetPassword() {
        return "user/forgetPassword";
    }
}
