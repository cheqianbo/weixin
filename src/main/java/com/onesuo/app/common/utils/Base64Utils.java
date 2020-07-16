package com.onesuo.app.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Decoder;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
@Slf4j
public class Base64Utils {
    /**
     * ofd文件转换为BASE64码
     *
     * @param path 本地路径
     * @return
     */
    public static String OfdToBase64(String path) {
        // 将图片转化为字节数组字符串，并对其进行Base64编码处理
        InputStream in = null;
        byte[] data = null;

        // 读取图片字节数组
        try {
            in = new FileInputStream(path);
            data = new byte[in.available()];
            in.read(data);

            // 关闭流
            in.close();
        } catch (IOException e) {
            log.error("ofd文件转换为BASE64码失败,e={}", e);
        }
        // 对字节数组Base64编码
        return new String(Base64.encodeBase64(data));
    }

    /**
     * Base64转换为ofd文件，并返回文件名称
     *
     * @param imgStr
     * @return
     */
    public static boolean Base64ToOfd(String imgStr,String filePath) {
        if (imgStr == null) {
            return false;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            byte[] b = decoder.decodeBuffer(imgStr);
            // 调整异常数据
            for (int i = 0; i < b.length; i++) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            OutputStream out = new FileOutputStream(filePath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (IOException e) {
            log.error("Base64转换为ofd文件失败,e={}", e);
            return false;
        }
    }

    /**
     * 将base64图片转成ZIP文件 并返回ZIP文件的地址
     */
    public static String base64ToZip(String imgStr, String fileName, String path) {

        BASE64Decoder decoder = new BASE64Decoder();
        ByteArrayInputStream in = null;
        byte[] b = null;
        File file = new File(path + fileName + ".zip");
        if (!file.exists()) {
            if(!file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }
            try {
                file.createNewFile();
            } catch (IOException e) {
                log.error("创建文件失败，e={}", e);
            }
        }
        try (OutputStream out = new FileOutputStream(file); ZipOutputStream zos = new ZipOutputStream(out)) {
            zos.putNextEntry(new ZipEntry("0.jpg"));
            b = decoder.decodeBuffer(imgStr);
            // 调整异常数据
            for (int i = 0; i < b.length; i++) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            in = new ByteArrayInputStream(b);
            int len;
            while ((len = in.read(b)) != -1) {
                zos.write(b, 0, len);
            }
            zos.flush();
            in.close();
        } catch (IOException e) {
            log.error("将base64图片转成ZIP文件,base64转数组失败,e = {}", e);
            return null;
        }
        return path + fileName;
    }
}
