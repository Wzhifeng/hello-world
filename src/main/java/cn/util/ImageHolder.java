package cn.util;

import java.io.InputStream;

/**
 * 图片综合类
 * 记录图片名字与图片输入流
 */
public class ImageHolder {
    private String imageName;
    private InputStream image;

    public ImageHolder() {
    }

    public ImageHolder(String imageName, InputStream image) {
        this.imageName = imageName;
        this.image = image;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public InputStream getImage() {
        return image;
    }

    public void setImage(InputStream image) {
        this.image = image;
    }
}
