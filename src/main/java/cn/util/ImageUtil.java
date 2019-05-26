package cn.util;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import javax.imageio.ImageIO;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 图片处理工具类
 */
public class ImageUtil {
    // 获得classpath的绝对值
    private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();

    // 定义日期格式
    private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final Random r = new Random();

    /**
     * 处理商铺分类、商铺、产品图片
     *
     * @param thumbnail
     * @param targetAddr
     * @return
     */
    public static String generateThumbnail(ImageHolder thumbnail, String targetAddr) {
        // 文件随机名
        String realFileName = getRandomFileName();
        // 文件扩展名
        String extension = getFileExtension(thumbnail.getImageName());
        // 创建路径
        makeDirPath(targetAddr);

        //文件相对路径
        String relativeAddr = targetAddr + realFileName + extension;
        // 获取文件要保存到的目标路径
        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);

        try {
            // 指定大小
            Thumbnails.of(thumbnail.getImage()).size(500, 500)
                    // 添加水印 位置、图片、透明度
                    .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(PathUtil.getImgBasePath() + "/watermark.jpg")), 0.25f)
                    // 压缩图片 输出位置
                    .outputQuality(0.8f).toFile(dest);
        } catch (Exception e) {
            throw new RuntimeException("创建图片失败：" + e.toString());
        }

        return relativeAddr;
    }

    /**
     * 获取图片扩展名
     *
     * @param fileName
     * @return
     */
    private static String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 创建目标路径涉及到的目录
     *
     * @param targetAddr
     */
    private static void makeDirPath(String targetAddr) {
        String realFileParentPath = PathUtil.getImgBasePath() + targetAddr;
        File dirPath = new File(realFileParentPath);
        if (!dirPath.exists()) {
            dirPath.mkdirs();
        }
    }

    /**
     * 获取随机文件名字 当前年月日小时分钟秒钟+五位随机数
     *
     * @return
     */
    public static String getRandomFileName() {
        // 获取随机的五位数
        int rannum = r.nextInt(89999) + 10000;
        String nowTimeStr = sDateFormat.format(new Date());
        return nowTimeStr + rannum;
    }


    /**
     * 测试是否能运行
     *
     * @param args
     */
    public static void main(String[] args) {
        File file = new File("C:/Users/wuzhifeng/Desktop/test-image/f_f_event_92_s512_f_event_92_0bg.jpg");
        try {
            Thumbnails.of(file).size(500, 500)
                    .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File("C:\\Users\\wuzhifeng\\Desktop\\test-image\\watermark.jpg")),
                            0.8f)
                    .outputQuality(0.8f).toFile(new File("C:/Users/wuzhifeng/Desktop/test-image/image-with-watermark.jpg"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除图片
     * @param path
     */
    public static void deteFileOrPath(String path) {
        File fileOrPath = new File(PathUtil.getImgBasePath() + path);

        if (fileOrPath.exists()) {
            if (fileOrPath.isDirectory()) {
                File[] listFiles = fileOrPath.listFiles();
                for (File file : listFiles) {
                    file.delete();
                }
            } else {
                fileOrPath.delete();
            }
        }
    }
}
