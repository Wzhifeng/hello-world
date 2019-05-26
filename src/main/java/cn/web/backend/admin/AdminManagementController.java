package cn.web.backend.admin;

import cn.dto.ShopCategoryExecution;
import cn.entity.HeadLine;
import cn.entity.Person;
import cn.entity.Shop;
import cn.entity.ShopCategory;
import cn.enums.HeadLineStateEnum;
import cn.enums.Role;
import cn.enums.ShopCategoryEnum;
import cn.service.HeadLineService;
import cn.service.PersonService;
import cn.service.ShopCategoryService;
import cn.service.ShopService;
import cn.util.ImageHolder;
import cn.util.ImageUtil;
import cn.util.PathUtil;
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
 * Admin后台控制器
 */

@Controller
@RequestMapping(value = "/adminManagement/")
public class AdminManagementController {
    @Autowired
    private ShopCategoryService shopCategoryService;

    @Autowired
    private ShopService shopService;

    @Autowired
    private HeadLineService headLineService;


    @RequestMapping(value = "changeShopStatus", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> changeShopStatus(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        //检查管理员权限
        if (!isAdmin(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "没有获得管理员权限，请重新登录管理员！");
            return modelMap;
        }

        //获取需要改变状态的商铺
        Integer shopId = Integer.valueOf(request.getParameter("shopId"));
        boolean changeSuccess = shopService.changeShopStatus(shopId);
        if (changeSuccess) {
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "操作失败，请稍后再试");
        }
        return modelMap;
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
        //检查管理员权限
        if (!isAdmin(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "没有获得管理员权限，请重新登录管理员！");
            return modelMap;
        }

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
     * 修改头条所属商铺
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "modifyHeadLineShop", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> modifyHeadLineShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        //检查管理员权限
        if (!isAdmin(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "没有获得管理员权限，请重新登录管理员！");
            return modelMap;
        }

        int headLine1Shop = Integer.parseInt(request.getParameter("headLine1Shop"));
        int headLine2Shop = Integer.parseInt(request.getParameter("headLine2Shop"));
        int headLine3Shop = Integer.parseInt(request.getParameter("headLine3Shop"));

        boolean changeSuccess = headLineService.modifyHeadLine(headLine1Shop, headLine2Shop, headLine3Shop);

        if (changeSuccess) {
            modelMap.put("success", true);
            return modelMap;
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "更新头条图片失败");
            return modelMap;
        }
    }

    /**
     * 获取商铺列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "getShopList", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getShopList(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        //检查管理员权限
        if (!isAdmin(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "没有获得管理员权限，请重新登录管理员！");
            return modelMap;
        }


        List<Shop> shopList = shopService.getShopList();
        if (shopList != null && shopList.size() > 0) {
            modelMap.put("success", true);
            modelMap.put("shopList", shopList);
            return modelMap;
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "查询商铺列表失败");
            return modelMap;
        }
    }

    /**
     * 根据id更新商铺分类信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "updateShopCategory", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateShopCategory(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        //检查管理员权限
        if (!isAdmin(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "没有获得管理员权限，请重新登录管理员！");
            return modelMap;
        }

        //接受前端参数
        ObjectMapper mapper = new ObjectMapper();
        ShopCategory shopCategory = null;
        ImageHolder shopCategoryImg = null;

        String shopCategoryStr = request.getParameter("shopCategoryStr");
        try {
            //转化实体
            shopCategory = mapper.readValue(shopCategoryStr, ShopCategory.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "商铺分类更新出现错误，请稍后再尝试");
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
                    shopCategoryImg = new ImageHolder(thumbnailFile.getOriginalFilename(),
                            thumbnailFile.getInputStream());
                }
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }

        //更新商铺分类
        ShopCategoryExecution shopCategoryExecution = shopCategoryService.updateShopCategory(shopCategory, shopCategoryImg);
        if (shopCategoryExecution.getState() == ShopCategoryEnum.SUCCESS.getState()) {
            modelMap.put("success", true);
            return modelMap;
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", shopCategoryExecution.getStateInfo());
            return modelMap;
        }

    }


    /**
     * 根据id查询商铺分类信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "getShopCategory", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getShopCategory(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        //检查管理员权限
        if (!isAdmin(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "没有获得管理员权限，请重新登录管理员！");
            return modelMap;
        }

        int shopCategoryId = Integer.parseInt(request.getParameter("shopCategoryId"));
        ShopCategoryExecution shopCategoryExecution = shopCategoryService.getShopCategory(shopCategoryId);

        if (shopCategoryExecution.getState() == ShopCategoryEnum.SUCCESS.getState()) {
            modelMap.put("success", true);
            modelMap.put("shopCategory", shopCategoryExecution.getShopCategory());
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "无法获取商铺分类信息，请稍后再试");
            return modelMap;
        }


        return modelMap;
    }

    /**
     * 删除商铺分类
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "deleteShopCategory", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deleteShopCategory(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        //检查管理员权限
        if (!isAdmin(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "没有获得管理员权限，请重新登录管理员！");
            return modelMap;
        }

        int shopCategoryId = Integer.parseInt(request.getParameter("shopCategoryId"));

        boolean deleteSuccess = shopCategoryService.deleteShopCategory(shopCategoryId);
        if (deleteSuccess) {
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "删除失败，请稍后再试");
        }
        return modelMap;
    }

    /**
     * 增加商铺分类
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "addShopCategory", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addShopCategory(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        //检查管理员权限
        if (!isAdmin(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "没有获得管理员权限，请重新登录管理员！");
            return modelMap;
        }

        //前端传来三个元素 ShopCategoryName ShopCategoryDesc Priority Thumbnail（商铺类别图片）
        //接受前端参数
        ObjectMapper mapper = new ObjectMapper();
        ShopCategory shopCategory = null;
        ImageHolder shopCategoryImage = null;

        try {
            //转化ShpoCategory对象
            String shopCategoryStr = (String) request.getParameter("shopCategoryStr");
            shopCategory = mapper.readValue(shopCategoryStr, ShopCategory.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }

        try {
            //处理Thumbnail
            CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
                    request.getSession().getServletContext()
            );
            //如果存在文件流 则取出相关文件
            if (commonsMultipartResolver.isMultipart(request)) {
                //取出文件流并且构建ImageHolder对象
                MultipartHttpServletRequest httpServletRequest =
                        (MultipartHttpServletRequest) request;
                CommonsMultipartFile thumbnailFile =
                        (CommonsMultipartFile) httpServletRequest.getFile("thumbnail");
                shopCategoryImage = new ImageHolder(thumbnailFile.getOriginalFilename(),
                        thumbnailFile.getInputStream());
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "商铺分类图片不能为空！");
                return modelMap;
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }

        //增加商铺分类
        if (shopCategory != null && shopCategoryImage != null) {
            ShopCategoryExecution shopCategoryExecution = shopCategoryService.addShopCategory(shopCategory, shopCategoryImage);
            if (shopCategoryExecution.getState() == ShopCategoryEnum.SUCCESS.getState()) {
                modelMap.put("success", true);
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", shopCategoryExecution.getStateInfo());
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入商铺分类信息！");
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
        Map<String, Object> modelMap = new HashMap<String, Object>();
        //检查管理员权限
        if (!isAdmin(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "没有获得管理员权限，请重新登录管理员！");
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
     * 检查是否为管理员
     *
     * @param request
     * @return
     */
    private boolean isAdmin(HttpServletRequest request) {
        Person person = (Person) request.getSession()
                .getAttribute("current_user");

        if (person == null) {
            //该用户未登录
            return false;
        }

        //判断用户信息与Session里面的用户信息是否一致
        if (person.getUserType() == Role.ADMIN.getState()) {
            //用户已登录并且是管理员
            return true;
        } else {
            return false;
        }
    }
}
