package demo.utils;

import demo.cfg.CommonStr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class OCRHelper implements CommonStr {

    private static Logger logger = LoggerFactory.getLogger(OCRHelper.class);
    
    private final String LANG_OPTION = "-l";
    private final String EOL = System.getProperty("line.separator");

    /**
     *  Tesseract-OCR的安装路径
     */
    private String tessPath = "C://Program Files (x86)//Tesseract-OCR";
    //private String tessPath = new File("tesseract").getAbsolutePath();

    /**
     * @param imageFile   传入的图像文件
     * @return 识别后的字符串
     */
    public String recognizeText(File imageFile) throws Exception {
        logger.info("解析图片开始...");
        /**
         * 设置输出文件的保存的文件目录
         */
        File outputFile = new File(imageFile.getParentFile(), "output");

        StringBuffer strB = new StringBuffer();
        List<String> cmd = new ArrayList<String>();

        cmd.add(tessPath + "\\tesseract");

        cmd.add("");
        cmd.add(outputFile.getName());
        cmd.add(LANG_OPTION);
        cmd.add("chi_sim");
        //cmd.add("eng");

        ProcessBuilder pb = new ProcessBuilder();
        /**
         *Sets this process builder's working directory.
         */
        pb.directory(imageFile.getParentFile());
        cmd.set(1, imageFile.getName());
        pb.command(cmd);
        pb.redirectErrorStream(true);
        long startTime = System.currentTimeMillis();
        logger.info("开始时间：" + startTime);
        Process process = pb.start();
        // tesseract.exe 1.jpg 1 -l chi_sim
        //不习惯使用ProcessBuilder的，也可以使用Runtime，效果一致
        // Runtime.getRuntime().exec("tesseract.exe 1.jpg 1 -l chi_sim");
        /**
         * the exit value of the process. By convention, 0 indicates normal
         * termination.
         */
//      logger.info(cmd.toString());
        int w = process.waitFor();
        if (w == 0)// 0代表正常退出
        {
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    new FileInputStream(outputFile.getAbsolutePath() + ".txt"),
                    "UTF-8"));
            String str;

            while ((str = in.readLine()) != null) {
                strB.append(str).append(EOL);
            }
            in.close();

            long endTime = System.currentTimeMillis();
            logger.info("结束时间：" + endTime);
            logger.info("耗时：" + (endTime - startTime) + "毫秒");
        } else {
            String msg;
            switch (w) {
                case 1:
                    msg = "Errors accessing files. There may be spaces in your image's filename.";
                    break;
                case 29:
                    msg = "Cannot recognize the image or its selected region.";
                    break;
                case 31:
                    msg = "Unsupported image format.";
                    break;
                default:
                    msg = "Errors occurred.";
            }
            throw new RuntimeException(msg);
        }
        new File(outputFile.getAbsolutePath() + ".txt").delete();
        logger.info("解析图片结束...:"+strB);
        return strB.toString().replaceAll("\\s*", "");
    }

    public static String resolvImg(){
        return resolvImg(tempPath1);
    }
    /**
     * @author top37 2018/6/10
     * 解析二值化图片
     */
    public static String resolvImg(String tessPath){
        File file = new File(tessPath);
        try {
            return new OCRHelper().recognizeText(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
