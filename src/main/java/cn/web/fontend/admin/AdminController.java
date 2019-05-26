package cn.web.fontend.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 管理员的前台转发器
 */
@Controller
@RequestMapping(value = "/admin/", method = RequestMethod.GET)
public class AdminController {
    @RequestMapping(value = "adminCenter")
    public String shopCart() {
        return "admin/adminCenter";
    }

    @RequestMapping(value = "addShopCategory")
    public String addShopCategory() {
        return "admin/addShopCategory";
    }

    @RequestMapping(value = "modifyShopCategory")
    public String modifyShopCategory() {
        return "admin/modifyShopCategory";
    }

    @RequestMapping(value = "shopCategoryList")
    public String shopCategoryList() {
        return "admin/shopCategoryList";
    }

    @RequestMapping(value = "headLine")
    public String headLine() {
        return "admin/headLine";
    }

    @RequestMapping(value = "shopAudit")
    public String shopAudit() {
        return "admin/shopAudit";
    }
}
