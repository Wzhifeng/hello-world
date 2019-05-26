package cn.web.fontend.shopkeeper;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 商家的前台转发器
 */
@Controller
@RequestMapping(value = "/shopkeeper/", method = RequestMethod.GET)
public class ShopkeekperController {
    @RequestMapping(value = "shopkeeperCenter")
    public String shopCart(){
        return "shopkeeper/shopkeeperCenter";
    }

    @RequestMapping(value = "createShop")
    public String createShop(){
        return "shopkeeper/createShop";
    }

    @RequestMapping(value = "addProductCategory")
    public String addProductCategory(){
        return "shopkeeper/addProductCategory";
    }

    @RequestMapping(value = "productCategoryList")
    public String productCategoryList(){
        return "shopkeeper/productCategoryList";
    }


    @RequestMapping(value = "addProduct")
    public String addProduct(){
        return "shopkeeper/addProduct";
    }


    @RequestMapping(value = "productList")
    public String productList(){
        return "shopkeeper/productList";
    }


    @RequestMapping(value = "modifyProduct")
    public String modifyProduct(){
        return "shopkeeper/modifyProduct";
    }

    @RequestMapping(value = "modifyProductCategory")
    public String modifyProductCategory(){
        return "shopkeeper/modifyProductCategory";
    }

    @RequestMapping(value = "modifyShop")
    public String modifyShop(){
        return "shopkeeper/modifyShop";
    }


    @RequestMapping(value = "newOrder")
    public String newOrder(){
        return "shopkeeper/newOrder";
    }


    @RequestMapping(value = "customerComments")
    public String customerComments(){
        return "shopkeeper/customerComments";
    }


    @RequestMapping(value = "financialStatement")
    public String financialStatement(){
        return "shopkeeper/financialStatement";
    }
}
