package cn.service.serviceImpl;

import cn.dao.OrderDao;
import cn.dao.ProductDao;
import cn.dto.ProductExecution;
import cn.entity.Order;
import cn.entity.Product;
import cn.enums.ProductStateEnum;
import cn.exceptions.ProductException;
import cn.service.ProductService;
import cn.util.BigDecimalUtil;
import cn.util.ImageHolder;
import cn.util.ImageUtil;
import cn.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * ProductService 实现类
 */
@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;

    @Autowired
    private OrderDao orderDao;

    /**
     * 添加商品
     *
     * @param product
     * @param productImg
     * @return
     */
    @Override
    public ProductExecution addProduct(Product product, ImageHolder productImg) {
        if (product == null) {
            return new ProductExecution(ProductStateEnum.PRODUCT_INFO_NULL);
        }

        //商品信息初始化
        product.setProductStatus(ProductStateEnum.PRODUCT_POYAWAY.getState());
        product.setPraise(0);
        product.setSales(0);

        //向数据库插入商品
        try {
            int effectedNum = productDao.insertProduct(product);
            if (effectedNum <= 0) {
                return new ProductExecution(ProductStateEnum.ADD_PRODUCT_FAIL);
            } else {
                //添加商品图片地址
                addProductImg(product, productImg);

                //更新商品
                effectedNum = productDao.updateProduct(product);
                if (effectedNum <= 0) {
                    return new ProductExecution(ProductStateEnum.ADD_PRODUCT_FAIL);
                } else {
                    return new ProductExecution(ProductStateEnum.SUCCESS);
                }

            }
        } catch (Exception e) {
            throw new ProductException(ProductStateEnum.ADD_PRODUCT_FAIL.getStateInfo() + ":" + e.toString());
        }
    }

    /**
     * 查询商品列表
     *
     * @return
     */
    @Override
    public ProductExecution getProductList(Integer shopId) {
        List<Product> productList = productDao.queryProductList(shopId);
        if (productList.size() > 0) {
            return new ProductExecution(ProductStateEnum.SUCCESS, productList);
        } else {
            return new ProductExecution(ProductStateEnum.PRODUCT_INFO_NULL);
        }
    }

    /**
     * 查询商品
     *
     * @param productCondition
     * @return
     */
    @Override
    public ProductExecution getProductByCondition(Product productCondition) {
        if (productCondition == null) {
            return new ProductExecution(ProductStateEnum.PRODUCT_CONDITION_NULL);
        }

        List<Product> productList = productDao.queryProductByCondition(productCondition);
        if (productList.size() > 0) {
            return new ProductExecution(ProductStateEnum.SUCCESS, productList);
        } else {
            return new ProductExecution(ProductStateEnum.PRODUCT_INFO_NULL);
        }

    }

    /**
     * 更新商品
     *
     * @param product
     * @param productImg
     * @return
     */
    @Override
    public ProductExecution modifyProduct(Product product, ImageHolder productImg) {

        //设置更新时间
        product.setUpdateTime(new Date());

        //如果有新图片则删除现有图片
        if (productImg != null) {
            Product tempProduct = new Product();
            tempProduct.setProductId(product.getProductId());
            tempProduct = productDao.queryProductByCondition(tempProduct).get(0);
            if (tempProduct.getProductImg() != null) {
                ImageUtil.deteFileOrPath(tempProduct.getProductImg());
            }
            //添加新的图片
            addProductImg(product, productImg);
        }

        //更新商品信息
        int effectedNum = productDao.updateProduct(product);
        if (effectedNum > 0) {
            return new ProductExecution(ProductStateEnum.SUCCESS);
        } else {
            return new ProductExecution(ProductStateEnum.UPDATE_PRODUCT_FAIL);
        }

    }

    /**
     * 根据Id删除product
     *
     * @param productId
     * @return
     */
    @Override
    public boolean deleteProduct(int productId) {

        //检查该商品是否有图片 有则将其删除
        Product product = new Product();
        product.setProductId(productId);
        product = productDao.queryProductByCondition(product).get(0);

        if (product.getProductImg() != null) {
            ImageUtil.deteFileOrPath(product.getProductImg());
        }

        int effectedNum = productDao.deleteProduct(productId);

        if (effectedNum > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 修改商品状态
     *
     * @param productId
     * @return
     */
    @Override
    public boolean changeProductStatus(int productId) {
        Product product = new Product();
        product.setProductId(productId);
        product = productDao.queryProductByCondition(product).get(0);

        //修改商品状态
        if (product.getProductStatus() == ProductStateEnum.PRODUCT_POYAWAY.getState()) {
            product.setProductStatus(ProductStateEnum.PRODUCT_REMOVE_OFF.getState());
        } else {
            product.setProductStatus(ProductStateEnum.PRODUCT_POYAWAY.getState());
        }

        //更新商品
        int effectedNum = productDao.updateProduct(product);
        if (effectedNum > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 为商品增加赞
     *
     * @param productId
     * @return
     */
    @Override
    public Integer addPraise(Integer productId) {
        //新建商品查询
        Product product = new Product();
        product.setProductId(productId);


        //查询原来商品的赞
        product = productDao.queryProductByCondition(product).get(0);
        int praise = product.getPraise();

        //设置新的赞
        product.setPraise(praise + 1);

        //更新商品
        int num = productDao.updateProduct(product);
        if (num > 0) {
            return praise + 1;
        }
        return null;
    }

    /**
     * 根据id获取商品
     *
     * @param productId
     * @return
     */
    @Override
    public Product getProductById(Integer productId) {
        return productDao.queryProductById(productId);
    }

    /**
     * 增加商品销量
     *
     * @param productId
     * @param number
     * @return
     */
    @Override
    public boolean addSales(Integer productId, Integer number) {
        Product product = productDao.queryProductById(productId);
        Integer sales = product.getSales() + number;
        product.setSales(sales);

        int effectedNum = productDao.updateProduct(product);
        if (effectedNum > 0) {
            return true;
        }
        return false;
    }

    /**
     * 根据商铺和商家选取的时间获取收入
     *
     * @param shopId
     * @param timestamp
     * @return
     */
    @Override
    public Map<String, Double> getIncomeByCondition(Integer shopId, Long timestamp) {
        //根据时间戳生成时间
        Date date = new Date(timestamp);
        Map<String, Double> incomeMap = new HashMap<>();
        //定义年 月 日收入
        double allIncome = 0;
        double yearIncome = 0;
        double monthIncome = 0;
        double dayIncome = 0;

        //按照年、月、日查询订单
        List<Order> allOrders = orderDao.queryAllOrders(shopId);
        List<Order> yearOrders = orderDao.queryOrderByYear(shopId, date);
        List<Order> monthOrders = orderDao.queryOrderByMonth(shopId, date);
        List<Order> dayOrders = orderDao.queryOrderByDay(shopId, date);

        //获取年、月、日收入
        allIncome = caculateAllIncome(allOrders);
        yearIncome = calculateYearIncome(yearOrders);
        monthIncome = calculateMonthIncome(monthOrders);
        dayIncome = calculateDayIncome(dayOrders);

        incomeMap.put("allIncome", allIncome);
        incomeMap.put("yearIncome", yearIncome);
        incomeMap.put("monthIncome", monthIncome);
        incomeMap.put("dayIncome", dayIncome);

        return incomeMap;
    }

    /**
     * 根据条件查询商品销量
     *
     * @param shopId
     * @param timestamp
     * @param salesScope
     * @return
     */
    @Override
    public List<Product> getSalesByCondition(Integer shopId, Long timestamp, String salesScope) {
        List<Product> productList = new ArrayList<>();
        List<Order> orderList = null;
        Date date = new Date(timestamp);

        if (salesScope.equals("allSales")) {
            //查询全部的销量
            orderList = orderDao.queryAllSales(shopId);
            productList = assembleProductList(orderList);
            return productList;
        } else if (salesScope.equals("yearSales")) {
            //根据年查询销量
            orderList = orderDao.querySalesByYear(shopId, date);
            productList = assembleProductList(orderList);
            return productList;
        } else if (salesScope.equals("monthSales")) {
            //根据月查询销量
            orderList = orderDao.querySalesByMonth(shopId, date);
            productList = assembleProductList(orderList);
            return productList;
        } else if (salesScope.equals("daySales")) {
            //根据日查询销量
            orderList = orderDao.querySalesByDay(shopId, date);
            productList = assembleProductList(orderList);
            return productList;
        }
        return null;
    }

    /**
     * 按照订单信息组装Product
     *
     * @param orderList
     * @return
     */
    private List<Product> assembleProductList(List<Order> orderList) {
        List<Product> productList = new ArrayList<>();
        Product product = null;
        for (Order order : orderList) {
            product = productDao.queryProductById(order.getProductId());
            product.setSales(order.getNumber());
            productList.add(product);
        }
        return productList;
    }

    private double caculateAllIncome(List<Order> allOrders) {
        double allIncome = 0;
        for (Order order : allOrders) {
            allIncome += Double.valueOf(order.getPayment());
        }
        return allIncome;
    }

    private double calculateDayIncome(List<Order> dayOrders) {
        double dayIncome = 0;
        for (Order order : dayOrders) {
            dayIncome += Double.valueOf(order.getPayment());
        }

        //保留两位小数
        return dayIncome;
    }

    private double calculateMonthIncome(List<Order> monthOrders) {
        double monthIncome = 0;
        for (Order order : monthOrders) {
            monthIncome += Double.valueOf(order.getPayment());
        }

        //保留两位小数点
        return monthIncome;
    }

    private double calculateYearIncome(List<Order> yearOrders) {
        double yearIncome = 0;
        for (Order order : yearOrders) {
            yearIncome += Double.valueOf(order.getPayment());
        }
        return yearIncome;
    }


    /**
     * 添加商品地址
     *
     * @param product
     * @param productImg
     */
    private void addProductImg(Product product, ImageHolder productImg) {
        String targetAddr = PathUtil.getShopImagePath(product.getShopId());
        String relativeAddr = ImageUtil.generateThumbnail(productImg, targetAddr);
        product.setProductImg(relativeAddr);
    }
}
