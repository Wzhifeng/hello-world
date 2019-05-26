package cn.web.backend.shopkeeper;

import cn.dto.*;
import cn.entity.*;
import cn.enums.*;
import cn.service.*;
import cn.util.CodeUtil;
import cn.util.ImageHolder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商铺后台控制器
 */

@RequestMapping(value = "/shopkeeperAdmin/")
@Controller
public class ShopkeeperAdminController {

    @Autowired
    private ShopService shopService;

    @Autowired
    private ShopCategoryService shopCategoryService;

    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private CommentService commentService;


    /**
     * 获取商铺下的商品销量
     * @param request
     * @return
     */
    @RequestMapping(value = "getSales", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getSales(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        //判断是否商家
        if (!isShopkeeper(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "没有获得商家权限，请重新登录商家！");
            return modelMap;
        }

        //获取商家选取的日期
        Long timestamp = Long.valueOf(request.getParameter("date"));
        //获取商家选取的销量范围
        String salesScope = request.getParameter("salesScope");

        if (timestamp == null && salesScope == null) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请求参数格式错误！");
            return modelMap;
        }

        //获取商家信息
        Person shopkeeper = (Person) request.getSession().getAttribute("current_user");
        Shop shop = shopService.getShopByUserId(shopkeeper.getUserId());


        if (salesScope != null) {
            salesScope = salesScope.replace('"',' ').trim();
        }
        List<Product> productList = productService.getSalesByCondition(shop.getShopId(), timestamp, salesScope);

        //判断是否有时间段内是否有卖出商品
        if (productList == null || productList.size() == 0) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "您还没卖出任何东西");
            return modelMap;
        }

        modelMap.put("success", true);
        modelMap.put("productList", productList);
        return modelMap;
    }

    /**
     * 获取商家的收入
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "getIncome", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getIncome(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        //判断是否商家
        if (!isShopkeeper(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "没有获得商家权限，请重新登录商家！");
            return modelMap;
        }

        //获取商家选取的日期
        Long timestamp = Long.valueOf(request.getParameter("date"));
        Person shopkeeper = (Person) request.getSession().getAttribute("current_user");
        Shop shop = shopService.getShopByUserId(shopkeeper.getUserId());

        Map<String, Double> incomeMap = productService.getIncomeByCondition(shop.getShopId(), timestamp);

        //判断是否有收入
        if (incomeMap == null || incomeMap.size() == 0) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "尚未查询到任务收入");
            return modelMap;
        }

        modelMap.putAll(incomeMap);
        modelMap.put("success", true);
        return modelMap;
    }

    /**
     * 获取商铺下的评论
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "getCunstomerComments", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getCunstomerComments(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        //判断是否商家
        if (!isShopkeeper(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "没有获得商家权限，请重新登录商家！");
            return modelMap;
        }

        Person shopkeeper = (Person) request.getSession().getAttribute("current_user");
        Shop shop = shopService.getShopByUserId(shopkeeper.getUserId());

        //根据商铺查询评论
        List<CommentVO> commentVOList = commentService.getCustomerComments(shop);

        if (commentVOList != null && commentVOList.size() > 0) {
            modelMap.put("success", true);
            modelMap.put("customerComments", commentVOList);
            return modelMap;
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "暂时没有客户评价");
            return modelMap;
        }
    }

    /**
     * 完成订单
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "changeOrderStatus", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> changeOrderStatus(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        Long orderNo = Long.valueOf(request.getParameter("orderNo"));
        if (orderNo == null) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "系统错误 请稍后再试");
            return modelMap;
        }

        boolean changeSuccess = orderService.finishOrder(orderNo);
        if (changeSuccess) {
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "系统错误 请稍后再试");
        }
        return modelMap;
    }

    /**
     * 获取商铺订单
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "getOrderByShop", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getOrderByShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        //判断是否商家
        if (!isShopkeeper(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "没有获得商家权限，请重新登录商家！");
            return modelMap;
        }

        //获取当前店铺的未完成订单
        Person shopkeeper = (Person) request.getSession().getAttribute("current_user");
        Shop shop = shopService.getShopByUserId(shopkeeper.getUserId());
        List<OrderVO> orderVOList = orderService.getOrderListByShop(shop.getShopId());
        if (orderVOList != null && orderVOList.size() > 0) {
            modelMap.put("success", true);
            modelMap.put("orderList", orderVOList);
        } else {
            modelMap.put("success", true);
            modelMap.put("errMsg", "暂时没有新的订单");
        }
        return modelMap;
    }

    /**
     * 更新商铺信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "updateShop", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        //判断验证码
        if (!CodeUtil.checkVerifyCoide(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "验证码输入错误！");
            return modelMap;
        }

        //判断是否商家
        if (!isShopkeeper(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "没有获得商家权限，请重新登录商家！");
            return modelMap;
        }

        //接受前端传来的参数
        //获取shopStr、thumbnail
        String shopStr = request.getParameter("shopStr");
        Shop shop = null;
        ImageHolder shopImage = null;
        //获取当前商家
        Person shopkeeper = (Person) request.getSession().getAttribute("current_user");
        //用于获取当前商铺id
        Shop tempShop = shopService.getShopByUserId(shopkeeper.getUserId());
        ObjectMapper mapper = new ObjectMapper();

        try {
            //将shopStr转化成shop实体
            shop = mapper.readValue(shopStr, Shop.class);
            //设置shopId
            shop.setShopId(tempShop.getShopId());
            shop.setShopOwnerId(shopkeeper.getUserId());
        } catch (IOException e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "商铺更新出现错误，请稍后再尝试");
            return modelMap;
        }

        try {
            //处理Thumbnail
            CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
                    request.getSession().getServletContext());
            //如果存在文件流 则取出相关文件
            if (commonsMultipartResolver.isMultipart(request)) {
                //取出文件流并且构建ImageHolder对象
                MultipartHttpServletRequest httpServletRequest =
                        (MultipartHttpServletRequest) request;
                CommonsMultipartFile thumbnailFile =
                        (CommonsMultipartFile) httpServletRequest.getFile("thumbnail");
                if (thumbnailFile == null) {
                } else {
                    shopImage = new ImageHolder(thumbnailFile.getOriginalFilename(), thumbnailFile.getInputStream());
                }
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }

        ShopExecution shopExecution = shopService.updateShop(shop, shopImage);

        if (shopExecution.getState() == ShopStateEnum.SUCCESS.getState()) {
            modelMap.put("success", true);
            return modelMap;
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", shopExecution.getStateInfo());
            return modelMap;
        }

    }

    /**
     * 获取当前商铺信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "getShop", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        //判断是否商家
        if (!isShopkeeper(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "没有获得商家权限，请重新登录商家！");
            return modelMap;
        }

        //当前商家
        Person shopkeeper = (Person) request.getSession().getAttribute("current_user");

        //当前商铺
        Shop shop = shopService.getShopByUserId(shopkeeper.getUserId());
        ShopCategoryExecution shopCategoryExecution = shopCategoryService.getShopCategoryList();

        if (shop != null && shopCategoryExecution.getState() == ShopCategoryEnum.SUCCESS.getState()) {
            modelMap.put("success", true);
            modelMap.put("shop", shop);
            modelMap.put("shopCategoryList", shopCategoryExecution.getShopCategoryList());
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "无法获取商铺信息，请稍后再试");
            return modelMap;
        }

        return modelMap;
    }

    /**
     * 删除商品分类
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "deleteProductCategory", method = RequestMethod.POST)
    @ResponseBody

    public Map<String, Object> deleteProductCategory(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        //判断是否商家
        if (!isShopkeeper(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "没有获得商家权限，请重新登录商家！");
            return modelMap;
        }

        int productCategoryId = Integer.parseInt(request.getParameter("productCategoryId"));
        boolean isDelete = productCategoryService.deleteProductCategory(productCategoryId);
        if (isDelete) {
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "删除失败，请稍后再试");
        }
        return modelMap;
    }


    /**
     * 编辑商品分类
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "modifyProductCategory", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> modifyProductCategory(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        //判断是否商家
        if (!isShopkeeper(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "没有获得商家权限，请重新登录商家！");
            return modelMap;
        }
        //接受前端参数
        ObjectMapper mapper = new ObjectMapper();
        ProductCategory productCategory = null;
        String productCategoryStr = request.getParameter("productCategoryStr");
        Person shopkeeper = (Person) request.getSession().getAttribute("current_user");
        //提取shopId
        Shop shop = shopService.getShopByUserId(shopkeeper.getUserId());

        try {
            //转化ProductCategory实体
            productCategory = mapper.readValue(productCategoryStr, ProductCategory.class);
            productCategory.setShopId(shop.getShopId());
            //添加商品类别
            ProductCategoryExecution productCategoryExecution = productCategoryService.modifyProductCategory(productCategory);
            if (productCategoryExecution.getState() == ProductCategoryStateEnum.SUCCESS.getState()) {
                modelMap.put("success", true);
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", productCategoryExecution.getStateInfo());
                return modelMap;
            }
        } catch (IOException e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }
        return modelMap;
    }

    /**
     * 根据商品分类id获取商品分类信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "getProductCategory", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getProductCategory(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        //判断是否商家
        if (!isShopkeeper(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "没有获得商家权限，请重新登录商家！");
            return modelMap;
        }

        //取出商品分类id
        int productCategoryId = Integer.parseInt(request.getParameter("productCategoryId"));

        ProductCategoryExecution productCategoryExecution = productCategoryService.getProductCategory(productCategoryId);

        if (productCategoryExecution.getState() == ProductCategoryStateEnum.SUCCESS.getState()) {
            modelMap.put("success", true);
            modelMap.put("productCategory", productCategoryExecution.getProductCategory());
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", productCategoryExecution.getStateInfo());
        }

        return modelMap;
    }

    /**
     * 改变商品状态
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "changeProductStatus", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> changeProductStatus(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        //判断是否商家
        if (!isShopkeeper(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "没有获得商家权限，请重新登录商家！");
            return modelMap;
        }

        int productId = Integer.parseInt(request.getParameter("productId"));

        boolean changeSuccess = productService.changeProductStatus(productId);
        if (changeSuccess) {
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "状态修改失败，请稍后重试");
        }
        return modelMap;
    }

    /**
     * 删除商品
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "deleteProduct", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deleteProduct(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        //判断是否商家
        if (!isShopkeeper(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "没有获得商家权限，请重新登录商家！");
            return modelMap;
        }

        int productId = Integer.parseInt(request.getParameter("productId"));

        boolean deleteSuccess = productService.deleteProduct(productId);
        if (deleteSuccess) {
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "删除失败，请稍后再试");
        }
        return modelMap;
    }

    /**
     * 更新商品
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "updateProduct", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateProduct(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();

        //判断验证码
        if (!CodeUtil.checkVerifyCoide(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "验证码输入错误！");
            return modelMap;
        }

        //判断是否商家
        if (!isShopkeeper(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "没有获得商家权限，请重新登录商家！");
            return modelMap;
        }

        //当前商家
        Person shopkeeper = (Person) request.getSession().getAttribute("current_user");
        //当前商铺
        Shop shop = shopService.getShopByUserId(shopkeeper.getUserId());

        //接受前端参数
        ObjectMapper mapper = new ObjectMapper();
        Product product = null;
        ImageHolder productImg = null;

        String productStr = request.getParameter("productStr");
        try {
            product = mapper.readValue(productStr, Product.class);
            product.setShopId(shop.getShopId());
        } catch (IOException e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "商品更新出现错误，请稍后再尝试");
            return modelMap;
        }

        try {
            //处理Thumbnail
            CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
                    request.getSession().getServletContext());
            //如果存在文件流 则取出相关文件
            if (commonsMultipartResolver.isMultipart(request)) {
                //取出文件流并且构建ImageHolder对象
                MultipartHttpServletRequest httpServletRequest =
                        (MultipartHttpServletRequest) request;
                CommonsMultipartFile thumbnailFile =
                        (CommonsMultipartFile) httpServletRequest.getFile("thumbnail");
                if (thumbnailFile == null) {
                } else {
                    productImg = new ImageHolder(thumbnailFile.getOriginalFilename(),
                            thumbnailFile.getInputStream());
                }
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }

        //更新商品
        ProductExecution productExecution = productService.modifyProduct(product, productImg);
        if (productExecution.getState() == ProductStateEnum.SUCCESS.getState()) {
            modelMap.put("success", true);
            return modelMap;
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", productExecution.getStateInfo());
            return modelMap;
        }

    }

    /**
     * 添加商品
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "addProduct", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addProduct(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();

        //判断验证码
        if (!CodeUtil.checkVerifyCoide(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "验证码输入错误！");
            return modelMap;
        }

        //判断是否商家
        if (!isShopkeeper(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "没有获得商家权限，请重新登录商家！");
            return modelMap;
        }

        //接受前端传来的元素 product thumbnail
        //初始化参数
        ObjectMapper mapper = new ObjectMapper();
        Product product = null;
        ImageHolder productImg = null;
        Person shopkeeper = (Person) request.getSession().getAttribute("current_user");
        //商品所属的商铺
        Shop shop = shopService.getShopByUserId(shopkeeper.getUserId());
        String productStr = request.getParameter("productStr");

        try {
            //将JSON字符串转化为product实体
            product = mapper.readValue(productStr, Product.class);
            product.setShopId(shop.getShopId());
        } catch (IOException e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }

        try {
            //处理Thumbnail
            CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
                    request.getSession().getServletContext());
            //如果存在文件流 则取出相关文件
            if (commonsMultipartResolver.isMultipart(request)) {
                //取出文件流并且构建ImageHolder对象
                MultipartHttpServletRequest httpServletRequest =
                        (MultipartHttpServletRequest) request;
                CommonsMultipartFile thumbnailFile =
                        (CommonsMultipartFile) httpServletRequest.getFile("thumbnail");
                productImg = new ImageHolder(thumbnailFile.getOriginalFilename(),
                        thumbnailFile.getInputStream());
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "商品图片不能为空！");
                return modelMap;
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }

        //添加商品
        if (product != null && productImg != null) {
            ProductExecution productExecution = productService.addProduct(product, productImg);
            if (productExecution.getState() == ProductStateEnum.SUCCESS.getState()) {
                modelMap.put("success", true);
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", productExecution.getStateInfo());
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "商品信息不能为空！");
            return modelMap;
        }


        return modelMap;
    }


    /**
     * 查询商品信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "getProduct", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getProductById(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();

        //判断是否商家
        if (!isShopkeeper(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "没有获得商家权限，请重新登录商家！");
            return modelMap;
        }

        //接受前端参数
        int productId = Integer.parseInt(request.getParameter("productId"));
        Product productCondition = new Product();
        //设置productId
        productCondition.setProductId(productId);

        //当前商家
        Person shopkeeper = (Person) request.getSession().getAttribute("current_user");
        //当前商铺
        Shop shop = shopService.getShopByUserId(shopkeeper.getUserId());

        try {
            //根据productId查询Product
            ProductExecution productExecution = productService.getProductByCondition(productCondition);
            //获取商铺的商品列表
            ProductCategoryExecution productCategoryExecution = productCategoryService
                    .getProductCategoryList(shop.getShopId());

            if (productExecution.getState() == ProductStateEnum.SUCCESS.getState()
                    && productCategoryExecution.getState() == ProductCategoryStateEnum.SUCCESS.getState()) {
                modelMap.put("product", productExecution.getProductList().get(0));
                modelMap.put("productCategoryList", productCategoryExecution.getProductCategoryList());
                modelMap.put("success", true);
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "没有查到该商品信息！");
            return modelMap;
        }

        return modelMap;
    }

    /**
     * 获取该商铺的商品列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "getProductList", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getProductList(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        //判断是否商家
        if (!isShopkeeper(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "没有获得商家权限，请重新登录商家！");
            return modelMap;
        }

        //当前用户
        Person shopkeeper = (Person) request.getSession().getAttribute("current_user");
        //当前商铺
        Shop shop = shopService.getShopByUserId(shopkeeper.getUserId());

        ProductExecution productExecution = productService.getProductList(shop.getShopId());
        if (productExecution.getState() == ProductStateEnum.SUCCESS.getState()
                && productExecution.getProductList().size() > 0) {
            modelMap.put("success", true);
            modelMap.put("productList", productExecution.getProductList());
            return modelMap;
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", productExecution.getStateInfo());
            return modelMap;
        }
    }


    /**
     * 获取商铺下商品分类列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "getProductCategoryList", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getProductCategoryList(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();

        //判断是否商家
        if (!isShopkeeper(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "没有获得商家权限，请重新登录商家！");
            return modelMap;
        }

        Person shopkeeper = (Person) request.getSession().getAttribute("current_user");
        Shop shop = shopService.getShopByUserId(shopkeeper.getUserId());

        ProductCategoryExecution productCategoryExecution = productCategoryService.getProductCategoryList(shop.getShopId());
        if (productCategoryExecution.getState() == ProductCategoryStateEnum.SUCCESS.getState()
                && productCategoryExecution.getProductCategoryList().size() > 0) {
            modelMap.put("success", true);
            modelMap.put("productCategoryList", productCategoryExecution.getProductCategoryList());
            return modelMap;
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", productCategoryExecution.getStateInfo());
            return modelMap;
        }
    }

    /**
     * 增加商品分类
     *
     * @return
     */
    @RequestMapping(value = "addProductCategory", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addProductCategory(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();

        //判断是否商家
        if (!isShopkeeper(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "没有获得商家权限，请重新登录商家！");
            return modelMap;
        }

        //接受前端参数
        ObjectMapper mapper = new ObjectMapper();
        ProductCategory productCategory = null;
        String productCategoryStr = request.getParameter("productCategoryStr");
        Person shopkeeper = (Person) request.getSession().getAttribute("current_user");
        //提取shopId
        Shop shop = shopService.getShopByUserId(shopkeeper.getUserId());

        try {
            //转化ProductCategory实体
            productCategory = mapper.readValue(productCategoryStr, ProductCategory.class);


            productCategory.setShopId(shop.getShopId());
            //添加商品类别
            ProductCategoryExecution productCategoryExecution = productCategoryService.addProductCategory(productCategory);
            if (productCategoryExecution.getState() == ProductCategoryStateEnum.SUCCESS.getState()) {
                modelMap.put("success", true);
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", productCategoryExecution.getStateInfo());
                return modelMap;
            }
        } catch (IOException e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }


        return modelMap;
    }

    /**
     * 检查用户是否有创建商铺的权限
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "canCreateShop", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> canCreateShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();

        //判断是否商家
        if (!isShopkeeper(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "没有获得商家权限，请重新登录商家！");
            return modelMap;
        }
        //取出Session的商家信息
        Person shopkeeper = (Person) request.getSession().getAttribute("current_user");
        //查询该用户是否已经拥有商铺
        Shop shop = shopService.getShopByUserId(shopkeeper.getUserId());
        if (shop == null) {
            //没有店铺 允许创建店铺
            modelMap.put("success", true);
        } else {
            //拥有店铺 拒绝创建店铺
            modelMap.put("success", false);
            modelMap.put("errMsg", "已经拥有店铺了，不能贪心哦~");
        }
        return modelMap;
    }


    /**
     * 获取商铺分类列表
     *
     * @return
     */
    @RequestMapping(value = "getShopCategoryList", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getShopCategoryList(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();

        //判断是否商家
        if (!isShopkeeper(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "没有获得商家权限，请重新登录商家！");
            return modelMap;
        }
        ShopCategoryExecution shopCategoryExecution = shopCategoryService.getShopCategoryList();
        if (shopCategoryExecution.getState() == ShopCategoryEnum.SUCCESS.getState()
                && shopCategoryExecution.getShopCategoryList().size() > 0) {
            modelMap.put("success", true);
            modelMap.put("shopCategoryList", shopCategoryExecution.getShopCategoryList());
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", shopCategoryExecution.getStateInfo());
        }
        return modelMap;
    }


    /**
     * 注册店铺
     *
     * @return
     */
    @RequestMapping(value = "registerShop", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> registerShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();

        //判断验证码
        if (!CodeUtil.checkVerifyCoide(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "验证码输入错误！");
            return modelMap;
        }

        //判断是否商家
        if (!isShopkeeper(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "没有获得商家权限，请重新登录商家！");
            return modelMap;
        }

        //接受前端传来的参数
        //获取shopStr、thumbnail
        String shopStr = request.getParameter("shopStr");
        Shop shop = null;
        ImageHolder shopImage = null;
        Person shopkeeper = (Person) request.getSession().getAttribute("current_user");
        ObjectMapper mapper = new ObjectMapper();

        try {
            //将shopStr转化成shop实体
            shop = mapper.readValue(shopStr, Shop.class);
        } catch (IOException e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }

        //处理图片
        CommonsMultipartFile thumbnail = null;
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        //判断本次请求是否有图片
        if (commonsMultipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            thumbnail = (CommonsMultipartFile) multipartHttpServletRequest.getFile("thumbnail");
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "商铺图片不能为空！");
            return modelMap;
        }

        //注册店铺
        if (shop != null && thumbnail != null) {
            try {
                //设置shop的商家
                shop.setShopOwnerId(shopkeeper.getUserId());
                //创建shopIageHolder
                shopImage = new ImageHolder(thumbnail.getOriginalFilename(), thumbnail.getInputStream());
                ShopExecution shopExecution = shopService.addShop(shop, shopImage);
                if (shopExecution.getState() == ShopStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", shopExecution.getStateInfo());
                    return modelMap;
                }
            } catch (IOException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }

        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入商铺信息！");
            return modelMap;
        }
        return modelMap;
    }

    /**
     * 检测是否拥有商家权限
     *
     * @param request
     * @return
     */
    private boolean isShopkeeper(HttpServletRequest request) {
        //从Session获取该用户
        Person person = (Person) request.getSession()
                .getAttribute("current_user");

        if (person == null) {
            //该用户未登录
            return false;
        }

        //判断用户信息与Session里面的用户信息是否一致
        if (person.getUserType() == Role.SHOPKEEPER.getState()) {
            //用户已登录并且是商家
            return true;
        } else {
            return false;
        }
    }
}
