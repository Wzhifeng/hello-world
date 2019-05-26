package cn.web.backend.user;

import cn.dto.*;
import cn.entity.*;
import cn.enums.*;
import cn.service.*;
import cn.util.BigDecimalUtil;
import cn.util.CodeUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User后台控制器
 */

@Controller
@RequestMapping(value = "/userAdmin/")
public class PersonAdminController {

    @Autowired
    private PersonService personService;

    @Autowired
    private HeadLineService headLineService;

    @Autowired
    private ShopService shopService;

    @Autowired
    private ShopCategoryService shopCategoryService;

    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private ShopCartService shopCartService;

    @Autowired
    private OrderService orderService;


    /**
     * 添加评论
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "addComment", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addComment(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();

        //获取当前用户 评论内容 评论的产品id
        Person person = (Person) request.getSession().getAttribute("current_user");
//        Integer productId = Integer.valueOf(request.getParameter("productId"));
//        String commentContent = request.getParameter("commentContetn");
//        Comment comment = new Comment();
//        comment.setUserId(person.getUserId());
//        comment.setProductId(productId);
//        comment.setCommentConetent(commentContent);
        String commentStr = request.getParameter("commentStr");
        Comment comment = null;
        ObjectMapper mapper = new ObjectMapper();

        try {
            comment = mapper.readValue(commentStr, Comment.class);
        } catch (IOException e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }

        if (comment != null) {
            comment.setUserId(person.getUserId());
        }


        boolean addSuccess = commentService.addComment(comment);
        if (addSuccess) {
            modelMap.put("success", true);
            return modelMap;
        } else {
            modelMap.put("success", false);
            return modelMap;
        }
    }

    /**
     * 获取用户消费记录
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "getOrderListByUser", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getOrderListByUser(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        Person person = (Person) request.getSession().getAttribute("current_user");

        //判断用户是否登陆
        if (person == null) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请您先登录~");
            return modelMap;
        }

        //查询该用户的订单记录并组装成消费记录VO
        List<PurchaseHistoryVO> purchaseHistoryVOList = orderService.getOrderListByUserId(person.getUserId());
        if (purchaseHistoryVOList != null) {
            modelMap.put("success", true);
            modelMap.put("purchaseHistoryList", purchaseHistoryVOList);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请您先登录~");
        }

        return modelMap;
    }

    /**
     * 修改用户信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "modifyPerson", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> modifyPerson(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        Person person = (Person) request.getSession().getAttribute("current_user");

        //判断用户是否登陆
        if (person == null) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请您先登录~");
            return modelMap;
        }

        Integer userId = person.getUserId();

        //初始化参数
        ObjectMapper mapper = new ObjectMapper();
        String personStr = request.getParameter("personStr");

        try {
            person = mapper.readValue(personStr, Person.class);
        } catch (IOException e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }

        person.setUserId(userId);

        boolean updateSuccess = personService.updatePerson(person);
        if (updateSuccess) {
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
        }

        return modelMap;
    }

    /**
     * 获取用户信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "getPersonInfo", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getPersonInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        Person person = (Person) request.getSession().getAttribute("current_user");

        //判断用户是否登陆
        if (person == null) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请您先登录~");
            return modelMap;
        }

        person = personService.getPersonById(person.getUserId());

        modelMap.put("success", true);
        modelMap.put("person", person);
        return modelMap;
    }

    /**
     * 创建订单
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "createOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> createOrder(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        //获取当前用户
        Person person = (Person) request.getSession().getAttribute("current_user");

        //判断用户是否登陆
        if (person == null) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请您先登录~");
            return modelMap;
        }
        //接受前端传来的参数
        String orderStr = request.getParameter("orderStr");

        //初始化参数
        ObjectMapper mapper = new ObjectMapper();
        Order order = null;

        try {
            order = mapper.readValue(orderStr, Order.class);
        } catch (IOException e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "订单创建失败，请重新下单");
            return modelMap;
        }

        //查询订单所属商铺
        Integer shopId = productService.getProductById(order.getProductId()).getShopId();

        //计算订单价格
        Integer number = order.getNumber();
        String price = productService.getProductById(order.getProductId()).getPrice();
        BigDecimal payment = new BigDecimal("0");
        payment = BigDecimalUtil.mul(number, Double.parseDouble(price));

        //初始化订单
        order.setShopId(shopId);
        order.setUserId(person.getUserId());
        order.setPayment(String.valueOf(payment));
        order.setPaymentType(OrderStateEnum.CASH_PAY.getState());
        order.setOrderStatus(OrderStateEnum.ALREARDY_PAY.getState());

        OrderExecution orderExecution = orderService.addOrder(order);
        if (orderExecution.getState() == OrderStateEnum.SUCCESS.getState()) {
            //商品销量甲增加
            Product product = new Product();
            boolean updateSuccess = productService.addSales(order.getProductId(), order.getNumber());
            if (updateSuccess == true) {
                modelMap.put("success", true);
            } else {
                modelMap.put("success", false);
            }
            return modelMap;
        } else {
            modelMap.put("success", false);
            return modelMap;
        }
    }

    /**
     * 获取商品信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "getProduct", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getProduct(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();

        Integer productId = Integer.valueOf(request.getParameter("productId"));

        Product product = productService.getProductById(productId);
        if (product != null) {
            modelMap.put("success", true);
            modelMap.put("product", product);
        } else {
            modelMap.put("success", false);
        }

        return modelMap;
    }

    /**
     * 删除用户购物车
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "deleteShopCartByUser", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> deleteShopCartByUser(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();

        //获取当前用户
        Person person = (Person) request.getSession().getAttribute("current_user");
        Integer productId = Integer.valueOf(request.getParameter("productId"));

        boolean deleteSuccess = shopCartService.deleteShopCart(person.getUserId(), productId);
        if (deleteSuccess) {
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
        }
        return modelMap;
    }

    /**
     * 获取某用户的购物车列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "getShopCartByUser", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getShopCartByUser(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        //获取当前用户
        Person person = (Person) request.getSession().getAttribute("current_user");

        //判断用户是否登陆
        if (person == null) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请您先登录~");
            return modelMap;
        }

        //查询用户的购物车
        ShopCart shopCartCondition = new ShopCart();
        shopCartCondition.setUserId(person.getUserId());

        List<ShopCart> shopCartList = shopCartService.getShopCartByCondition(shopCartCondition);
        if (shopCartList.size() == 0 || shopCartList == null) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "购物车空空如也~赶紧去添加商品吧~");
            return modelMap;
        } else {
            //封装ShopCartVO对象
            List<ShopCartVO> shopCartVOList = new ArrayList<>();
            for (ShopCart shopCart : shopCartList) {
                Product product = productService.getProductById(shopCart.getProductId());

                ShopCartVO shopCartVO = new ShopCartVO();
                shopCartVO.setNumber(shopCart.getNumber());
                shopCartVO.setProductDesc(product.getProductDesc());
                shopCartVO.setProductName(product.getProductName());
                shopCartVO.setProductImg(product.getProductImg());
                shopCartVO.setProductId(product.getProductId());
                shopCartVO.setPrice(product.getPrice());

                shopCartVOList.add(shopCartVO);
            }

            modelMap.put("success", true);
            modelMap.put("shopCartList", shopCartVOList);
            return modelMap;
        }
    }


    /**
     * 添加购物车
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "addShopCart", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> addShopCart(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();

        //获取productId
        int productId = Integer.parseInt(request.getParameter("productId"));
        //获取当前用户
        Person person = (Person) request.getSession().getAttribute("current_user");

        //判断用户是否登陆
        if (person == null) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请您先登录~");
            return modelMap;
        }

        //设置shopCart查询条件
        ShopCart shopCart = new ShopCart();
        shopCart.setProductId(productId);
        shopCart.setUserId(person.getUserId());

        ShopCartExecution shopCartExecution = null;

        //查询shopCart 如果没有则新增shopCart 如果则在当前shopCart上数量加1
        List<ShopCart> shopCartList = shopCartService.getShopCartByCondition(shopCart);

        if (shopCartList == null || shopCartList.size() == 0) {
            //设置当前条件
            shopCart.setProductId(productId);
            shopCart.setUserId(person.getUserId());
            shopCart.setNumber(1);

            shopCartExecution = shopCartService.addShopCart(shopCart);
        } else {
            shopCart = shopCartList.get(0);
            //数量增加1
            int num = shopCart.getNumber() + 1;
            shopCart.setNumber(num);

            shopCartExecution = shopCartService.modifyShopCart(shopCart);
        }

        if (shopCartExecution.getState() == ShopCartStateEnum.SUCCESS.getState()) {
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", shopCartExecution.getStateInfo());
        }
        return modelMap;
    }

    /**
     * 点赞商品
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "addPraise", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> addPraise(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();

        Integer productId = Integer.valueOf(request.getParameter("productId"));
        Integer praise = productService.addPraise(productId);

        if (praise != null) {
            modelMap.put("success", true);
            modelMap.put("praise", praise.intValue());
        }
        return modelMap;
    }

    /**
     * 获取商品信息和该商品的评论信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "getProductDetailAndCommentByProductId", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getProductDetailAndCommentByProductId(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();

        Integer productId = Integer.valueOf(request.getParameter("productId"));

        //设置product查询条件
        Product productCondition = new Product();
        productCondition.setProductId(productId);

        //设置Comment查询条件
        Comment commentCondition = new Comment();
        commentCondition.setProductId(productId);

        ProductExecution productExecution = productService.getProductByCondition(productCondition);
        CommentExecution commentExecution = commentService.getCommentByCondition(commentCondition);

        //创建CommentVo 包含commentContent commentUserName
        List<CommentVO> commentVOList = new ArrayList<CommentVO>();

        if (commentExecution.getCommentList() != null) {
            //封装评论
            for (Comment comment :
                    commentExecution.getCommentList()) {
                Integer userId = comment.getUserId();
                Person person = personService.getPersonById(userId);

                //设置commentVo的各值
                CommentVO commentVO = new CommentVO();
                commentVO.setCommentContent(comment.getCommentContent());
                commentVO.setCommentUserName(person.getUsername());
                commentVOList.add(commentVO);
            }
        }

        if (productExecution.getState() == ProductStateEnum.SUCCESS.getState()) {
            modelMap.put("success", true);
            modelMap.put("product", productExecution.getProductList().get(0));
            modelMap.put("commentList", commentVOList);
        }
        return modelMap;
    }


    /**
     * 根据商品分类获取商品
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "getProductByProductCategory", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getProductByProductCategory(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        //获取商品分类id
        Integer productCategoryId = Integer.valueOf(request.getParameter("productCategoryId"));
        Product productCondition = new Product();
        productCondition.setProductCategoryId(productCategoryId);
        productCondition.setProductStatus(ProductStateEnum.PRODUCT_POYAWAY.getState());

        ProductExecution productExecution = productService.getProductByCondition(productCondition);
        if (productExecution.getState() == ProductStateEnum.SUCCESS.getState()) {
            modelMap.put("success", true);
            modelMap.put("productList", productExecution.getProductList());
        }
        return modelMap;
    }

    /**
     * 根据商铺id获取商品详细信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "getShopDetailInfo", method = RequestMethod.GET)
    @ResponseBody

    public Map<String, Object> getShopDetailInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();

        //获取商铺id
        Integer shopId = Integer.valueOf(request.getParameter("shopId"));
        Shop shop = new Shop();
        shop.setShopId(shopId);

        Product productCondition = new Product();
        productCondition.setProductStatus(ProductStateEnum.PRODUCT_POYAWAY.getState());
        productCondition.setShopId(shopId);

        ProductCategoryExecution productCategoryExecution = productCategoryService.getProductCategoryList(shopId);
        ProductExecution productExecution = productService.getProductByCondition(productCondition);
        ShopExecution shopExecution = shopService.getShopListByShopCondition(shop);

        if (productCategoryExecution.getState() == ProductCategoryStateEnum.SUCCESS.getState()
                && productExecution.getState() == ProductStateEnum.SUCCESS.getState()
                && shopExecution.getState() == ShopStateEnum.SUCCESS.getState()) {

            modelMap.put("success", true);
            modelMap.put("productCategoryList", productCategoryExecution.getProductCategoryList());
            modelMap.put("productList", productExecution.getProductList());
            //由于返回的是list 所有要获取第一个
            modelMap.put("shop", shopExecution.getShopList().get(0));
            return modelMap;
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "获取商铺信息失败");
            return modelMap;
        }

    }

    /**
     * 获取商铺列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "getShopList", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getShopList(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();

        ObjectMapper mapper = new ObjectMapper();
        //获取shopJson字符串
        String shopStr = request.getParameter("shopStr");
        Shop shopCondition = new Shop();

        //转化shop实体
        try {
            shopCondition = mapper.readValue(shopStr, Shop.class);
        } catch (IOException e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "获取商铺列表失败");
            return modelMap;
        }

        ShopExecution shopExecution = shopService.getShopListByShopCondition(shopCondition);
        if (shopExecution.getState() == ShopStateEnum.SUCCESS.getState()) {
            modelMap.put("success", true);
            modelMap.put("shopList", shopExecution.getShopList());
            return modelMap;
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", shopExecution.getStateInfo());
            return modelMap;
        }
    }

    /**
     * 获取商铺分类列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "getShopCategoryList", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getShopCategoryList(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        ShopCategoryExecution shopCategoryExecution = shopCategoryService.getShopCategoryList();
        if (shopCategoryExecution.getState() == ShopCategoryEnum.SUCCESS.getState()) {
            modelMap.put("success", true);
            modelMap.put("shopCategoryList", shopCategoryExecution.getShopCategoryList());
            return modelMap;
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "验证码输入有误!");
            return modelMap;
        }
    }

    /**
     * 获取头条信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "getHeadLine", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getHeadLine(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        HeadLine headLine1 = headLineService.getHeadLine(HeadLineStateEnum.HEAD_LINE_1.getState());
        HeadLine headLine2 = headLineService.getHeadLine(HeadLineStateEnum.HEAD_LINE_2.getState());
        HeadLine headLine3 = headLineService.getHeadLine(HeadLineStateEnum.HEAD_LINE_3.getState());

        Shop headLine1Shop = shopService.getShopById(headLine1.getShopId());
        Shop headLine2Shop = shopService.getShopById(headLine2.getShopId());
        Shop headLine3Shop = shopService.getShopById(headLine3.getShopId());

        modelMap.put("headLine1Shop", headLine1Shop);
        modelMap.put("headLine2Shop", headLine2Shop);
        modelMap.put("headLine3Shop", headLine3Shop);
        modelMap.put("success", true);
        return modelMap;
    }

    /**
     * 用户注册
     *
     * @return
     */
    @RequestMapping(value = "register", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> resgister(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();

        //判断验证码状态
        if (!CodeUtil.checkVerifyCoide(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "验证码输入有误!");
            return modelMap;
        }


        try {
            //接受前端传来的参数
            ObjectMapper mapper = new ObjectMapper();
            Person person = null;
            String personStr = (String) request.getParameter("personStr");
            //将JSON字符串转化成Person实体类
            person = mapper.readValue(personStr, Person.class);
            //将Person进行注册
            PersonExecution personExecution = personService.addPerson(person);

            //判断注册是否成功
            //注册失败
            if (personExecution.getState() != PersonStateEnum.SUCCESS.getState()) {
                modelMap.put("success", false);
                modelMap.put("errMsg", personExecution.getStateInfo());
                return modelMap;
            }

            //注册成功
            modelMap.put("success", true);
            modelMap.put("sucMsg", "注册成功");
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }
        return modelMap;
    }

    /**
     * 用户登录
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> login(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();

        //判断验证码状态
        if (!CodeUtil.checkVerifyCoide(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "验证码输入有误!");
            return modelMap;
        }

        try {
            //接受前端传来的参数
            ObjectMapper mapper = new ObjectMapper();
            Person person = null;

            //将JSON字符串转化成Person实体类
            String personStr = request.getParameter("personStr");
            person = mapper.readValue(personStr, Person.class);

            //进行登录
            PersonExecution personExecution = personService.login(person);

            if (personExecution.getState() == PersonStateEnum.SUCCESS.getState()) {

                //如果登陆成功则将用户信息存进session
                request.getSession().setAttribute(
                        "current_user",
                        personExecution.getPerson());
                //将用户序号 用户类型返回给前端
                modelMap.put("userType", personExecution.getPerson().getUserType());
                modelMap.put("success", true);
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "账号或密码不正确");
            }

        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "账号或者密码不正确！");
        }

        return modelMap;
    }

    /**
     * 登出管理员
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "logout", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> logout(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();

        //从session中删除用户信息
        request.getSession().removeAttribute("current_user");

        if (request.getSession().getAttribute("current_user") == null) {
            modelMap.put("success", true);
            modelMap.put("url", "/user/login");
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "系统繁忙 请稍后再试");
        }
        return modelMap;
    }
}
