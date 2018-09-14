package demo.utils;


import demo.cfg.CommonStr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

/**
 * @author top37 2018/6/10
 */
public class ImageHelper implements CommonStr {
    
    private static Logger logger = LoggerFactory.getLogger(ImageHelper.class);
    
    /**
     * 二值化（好用）
     * @param src 源图片
     * @param dest 目标图片
     * @return BufferedImage
     */
    private BufferedImage filter(BufferedImage src, BufferedImage dest) {
        int width = src.getWidth();
        int height = src.getHeight();

        // if ( dest == null )
        // dest = createCompatibleDestImage( src, null );

        int[] inPixels = new int[width * height];
        int[] outPixels = new int[width * height];
        // src = super.filter(src, null); // we need to create new one
        getRGB(src, width, height, inPixels);
        int index;
        int means = getThreshold(inPixels);
        for (int row = 0; row < height; row++) {
            int ta, tr, tg, tb;
            for (int col = 0; col < width; col++) {
                index = row * width + col;
                ta = (inPixels[index] >> 24) & 0xff;
                tr = (inPixels[index] >> 16) & 0xff;
                if (tr > means) {
                    tr = tg = tb = 255; // white
                } else {
                    tr = tg = tb = 0; // black
                }
                outPixels[index] = (ta << 24) | (tr << 16) | (tg << 8) | tb;
            }
        }
        setRGB(dest, width, height, outPixels);
        return dest;
    }

    private int getThreshold(int[] inPixels) {
        // maybe this value can reduce the calculation consume;
        int inithreshold = 127;
        int finalthreshold = 0;
        int temp[] = new int[inPixels.length];
        for (int index = 0; index < inPixels.length; index++) {
            temp[index] = (inPixels[index] >> 16) & 0xff;
        }
        List<Integer> sub1 = new ArrayList<>();
        List<Integer> sub2 = new ArrayList<>();
        int means1, means2;
        while (finalthreshold != inithreshold) {
            finalthreshold = inithreshold;
            for (int aTemp : temp) {
                if (aTemp <= inithreshold) {
                    sub1.add(aTemp);
                } else {
                    sub2.add(aTemp);
                }
            }
            means1 = getMeans(sub1);
            means2 = getMeans(sub2);
            sub1.clear();
            sub2.clear();
            inithreshold = (means1 + means2) / 2;
        }
        long start = System.currentTimeMillis();
        logger.info("Final threshold  = " + finalthreshold);
        long endTime = System.currentTimeMillis() - start;
        logger.info("Time consumes : " + endTime);
        return finalthreshold;
    }

    private static int getMeans(List<Integer> data) {
        int result = 0;
        int size = data.size();
        for (Integer i : data) {
            result += i;
        }
        return (result / size);
    }

    private void setRGB(BufferedImage image, int width,
                        int height, int[] pixels) {
        int type = image.getType();
        if (type == BufferedImage.TYPE_INT_ARGB
                || type == BufferedImage.TYPE_INT_RGB)
            image.getRaster().setDataElements(0, 0, width, height, pixels);
        else
            image.setRGB(0, 0, width, height, pixels, 0, width);
    }

    private void getRGB(BufferedImage image, int width,
                        int height, int[] pixels) {
        int type = image.getType();
        if (type == BufferedImage.TYPE_INT_ARGB
                || type == BufferedImage.TYPE_INT_RGB)
            image.getRaster().getDataElements(0, 0, width, height, pixels);
        else
            image.getRGB(0, 0, width, height, pixels, 0, width);
    }

    /**
     * @author top37 2018/6/10
     * 缺省
     */
    public static void to2Val(){
        logger.info("二值化开始...");
        to2Val(jpg,tempPath,tempPath1);
        logger.info("二值化结束...");
    }

    /**
     * @author top37 2018/6/10
     * @param src 源文件路径
     * @param target 目标文件路径
     */
    public static void to2Val(String imgType,String src,String target){
        ImageHelper i = new ImageHelper();
        BufferedImage bi;
        try {
            bi = ImageIO.read(new File(src));
            ImageIO.write(i.filter(bi, bi), imgType, new File(target));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        ImageHelper i = new ImageHelper();
        BufferedImage bi=ImageIO.read(new File("E:\\temp\\temp.jpg"));
        ImageIO.write(i.filter(bi, bi), "jpg", new File("E:\\temp\\temp1.jpg"));
    }

}

