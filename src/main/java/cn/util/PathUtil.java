package cn.util;

/**
 * 处理地址的工具类
 */
public class PathUtil {
    private static String seperator = System.getProperty("file.separator");

    /**
     * 返回项目途径根路径
     * @return
     */
    public static String getImgBasePath() {
        String os = System.getProperty("os.name");
        String basePath = "";
        if (os.toLowerCase().startsWith("win")) {
            basePath = "D:/Project/frontafter/DiplomaProject/images";
        } else {
            basePath = "/home/DiplomaProject/image";
        }
        basePath = basePath.replace("/", seperator);
        return basePath;
    }

    /**
     * 返回商铺图片子路径
     * @param shopId
     * @return
     */
    public static String getShopImagePath(int shopId) {
        String imagePath = "/upload/item/shop/" + shopId + "/";
        return imagePath.replace("/", seperator);
    }

    /**
     * 返回商铺分类图片子路径
     * @param shopCategoryId
     * @return
     */
    public static String getShopCategoryImagePath(int shopCategoryId) {
        String imagePath = "/upload/item/shopCategory/" + shopCategoryId + "/";
        return imagePath.replace("/", seperator);
    }
}
