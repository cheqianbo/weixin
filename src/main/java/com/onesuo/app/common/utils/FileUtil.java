package com.onesuo.app.common.utils;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import org.apache.commons.io.IOUtils;
import org.bouncycastle.util.encoders.Base64;

import javax.imageio.stream.FileImageInputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

public class FileUtil {
    private static HashMap<String, String> CONTENT_TYPE_EXT_MAP = new HashMap<>(100);

    static {
        CONTENT_TYPE_EXT_MAP.put("application/pdf", ".pdf");
        CONTENT_TYPE_EXT_MAP.put("application/x-png", ".png");
        CONTENT_TYPE_EXT_MAP.put("image/png", ".png");
        CONTENT_TYPE_EXT_MAP.put("application/x-jpg", ".jpg");
        CONTENT_TYPE_EXT_MAP.put("image/jpeg", ".jpg");
        CONTENT_TYPE_EXT_MAP.put("video/mpeg4", ".mp4");
        CONTENT_TYPE_EXT_MAP.put("audio/mp3", ".mp3");
        CONTENT_TYPE_EXT_MAP.put("video/mpg", ".mpeg");
        CONTENT_TYPE_EXT_MAP.put("application/vnd.ms-excel", ".xls");
        CONTENT_TYPE_EXT_MAP.put("application/x-bmp", ".bmp");
        CONTENT_TYPE_EXT_MAP.put("text/html", ".html");
        CONTENT_TYPE_EXT_MAP.put("application/msword", ".doc");
    }

    /**
     * ContentType转扩展名
     *
     * @param contentType 如application/x-png png
     * @return 扩展名 带.
     */
    public static String contentType2Ext(String contentType) {
        return CONTENT_TYPE_EXT_MAP.get(contentType);
    }

    /**
     * 通过扩展名获取contentType
     * @param ext 带.
     * @return
     */
    public static String ext2ContentType(String ext) {
        Set<Map.Entry<String, String>> entries = CONTENT_TYPE_EXT_MAP.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            if(entry.getValue().equals(ext))
                return entry.getKey();
        }
        return null;
    }

    public static String generateFileName(String ext) {
        int nextInt = new Random().nextInt(10000);
        return System.currentTimeMillis()+""+nextInt+ext;

    }

    public static void main(String[] args) {
        System.out.println(contentType2Ext("video/mpg"));
    }
    /**
     * 下载项目根目录下doc下的文件
     * @param response response
     * @param fileName 文件名
     * @return 返回结果 成功或者文件不存在
     * @author szh
     */
    public static String downloadFile(HttpServletResponse response, String fileName, boolean notIE) {
        InputStream stream = FileUtil.class.getClassLoader().getResourceAsStream("download/" + fileName);
        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        try {
            String name = java.net.URLEncoder.encode(fileName, "UTF-8");
            if (notIE) {
                name = java.net.URLDecoder.decode(name, "ISO-8859-1");
            }
            response.setHeader("Content-Disposition", "attachment;filename=" + name);
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
        }
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            os = response.getOutputStream();
            bis = new BufferedInputStream(stream);
            int i = bis.read(buff);
            while (i != -1) {
                os.write(buff, 0, buff.length);
                os.flush();
                i = bis.read(buff);
            }
        } catch (FileNotFoundException e1) {
            //e1.getMessage()+"系统找不到指定的文件";
            return "系统找不到指定的文件";
        }catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "success";
    }

    /**
     * base64转图片
     * @param base64Str
     * @param filepath
     */
    public static void baseToImgfile(String base64Str,String filepath){
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        try{
            byte[] bytes= Base64.decode(base64Str);
            ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(bytes);
            bis=new BufferedInputStream(byteArrayInputStream);
            File file=new File(filepath);
            File path=file.getParentFile();
            if(!path.exists()){
                path.mkdirs();
            }
            fos=new FileOutputStream(file);
            bos=new BufferedOutputStream(fos);

            byte[] buffer=new byte[1024];
            int length=bis.read(buffer);
            while(length!=-1){
                bos.write(buffer,0,length);
                length=bis.read(buffer);
            }
            bos.flush();
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            try{
                bis.close();
                bos.close();
                fos.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 图片转pdf
     * @param imgFilePath
     * @param imgFilePath2
     * @param pdfFilePath
     * @return
     * @throws IOException
     */
    public static boolean imgToPdf(String imgFilePath,String imgFilePath2, String pdfFilePath)throws IOException {
        File file=new File(imgFilePath);
        if(file.exists()){
            Document document = new Document();
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(pdfFilePath);
                PdfWriter.getInstance(document, fos);

                // 设置文档的大小
                document.setPageSize(PageSize.A4);
                // 打开文档
                document.open();
                // 读取第一个图片
                Image image = Image.getInstance(imgFilePath);
                float imageHeight=image.getScaledHeight();
                float imageWidth=image.getScaledWidth();
                int i=0;
                while(imageHeight>500||imageWidth>500){
                    image.scalePercent(100-i);
                    i++;
                    imageHeight=image.getScaledHeight();
                    imageWidth=image.getScaledWidth();
                }
                image.setAlignment(Image.ALIGN_CENTER);
                if(imageHeight > imageWidth){
                    image.scaleAbsolute(150, 250);
                    image.setRotationDegrees(90f);
                }else {
                    image.scaleAbsolute(250, 150);
                }

                // 读取第二个图片
                Image image2 = Image.getInstance(imgFilePath2);
                float imageHeight2=image2.getScaledHeight();
                float imageWidth2=image2.getScaledWidth();
                int j=0;
                while(imageHeight2>500||imageWidth2>500){
                    image2.scalePercent(100-j);
                    j++;
                    imageHeight2=image2.getScaledHeight();
                    imageWidth2=image2.getScaledWidth();
                }
                image2.setAlignment(Image.ALIGN_CENTER);
                if(imageHeight2 > imageWidth2){
                    image2.scaleAbsolute(150, 250);
                    image2.setRotationDegrees(90f);
                }else {
                    image2.scaleAbsolute(250, 150);
                }
                
                // 插入一个图片
                document.add(image);
                // 插入一个回车符，设置两个图片间距
                document.add(new Paragraph("\n"));
                document.add(image2);
            } catch (DocumentException de) {
                System.out.println(de.getMessage());
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
            }
            document.close();
            fos.flush();
            fos.close();
            return true;
        }else{
            return false;
        }
    }

    /**
     *
     * @param path
     * @return String
     * @description 将文件转base64字符串
     * @date 2019年10月25日
     * @author huyang
     */
    public static  String fileToBase64(String path) {
        String base64 = null;
        InputStream in = null;
        try {
            File file = new File(path);
            in = new FileInputStream(file);
            byte[] bytes=new byte[(int)file.length()];
            in.read(bytes);
            base64 = java.util.Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return base64;
    }

    /**
     * 根据输入流获取文件的base64字符串
     * @param in
     * @return
     */
    public static String getBase64FromInputStream(InputStream in) {
        // 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        byte[] data = null;
        // 读取图片字节数组
        try {
            ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
            byte[] buff = new byte[100];
            int rc = 0;
            while ((rc = in.read(buff, 0, 100)) > 0) {
                swapStream.write(buff, 0, rc);
            }
            data = swapStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        String str = java.util.Base64.getEncoder().encodeToString(data);
        return str;
    }

    private FileUtil()
    {

    }

    /**
     * 创建目录，如果目录已经存在，则将会返回false
     * @author       liandi
     * @since        esign, 2017年10月24日
     * @param file
     * @return
     */
    public static boolean mkdirs(File file)
    {
        if(null == file) return false;

        if(file.exists()) return false;

        return file.mkdirs();
    }

    /**
     * 创建目录，如果目录已经存在，则将会返回false
     * @author       liandi
     * @since        esign, 2017年10月24日
     * @param directory
     * @return
     */
    public static boolean mkdirs(String directory)
    {
        if(StringUtils.isBlank(directory)) return false;

        return mkdirs(new File(directory));
    }

    /**
     * 删除文件
     * @author       liandi
     * @since        esign, 2017年10月24日
     * @param file
     * @return
     */
    public static boolean delete(File file)
    {
        if(null == file) return false;

        if(!file.exists()) return false;

        return file.delete();
    }

    /**
     * 删除文件
     * @author       liandi
     * @since        esign, 2017年10月24日
     * @param file
     * @return
     */
    public static boolean delete(String file)
    {
        if(StringUtils.isBlank(file)) return false;

        return delete(new File(file));
    }

    /**
     * 文件是否存在
     * @author       liandi
     * @since        esign, 2017年12月18日
     * @param fileName   文件的绝对路径
     * @return
     */
    public static boolean exists(String fileName)
    {
        if (StringUtils.isBlank(fileName)) return false;

        File file = new File(fileName);

        return file.exists();
    }

    /**
     * 获取文件头信息
     * @author       liandi
     * @since        esign, 2017年10月24日
     * @param file   文件路径
     * @return
     */
    public static String getFileHeader(String file)
    {
        InputStream in = null;
        try
        {
            in = new FileInputStream(file);
            byte[] header = new byte[4];
            in.read(header, 0, header.length);

            return StringUtils.bytes2Hex(header);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if(null != in)
                {
                    in.close();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        return null;
    }

    /**
     * 获取文件头信息
     * @author       liandi
     * @since        esign, 2017年10月24日
     * @param fileData   文件数据
     * @return
     */
    public static String getFileHeader(byte[] fileData)
    {
        if(null == fileData) return null;

        byte[] header = new byte[4];

        System.arraycopy(fileData, 0, header, 0, header.length);

        return StringUtils.bytes2Hex(header);
    }

    /**
     * 判断文件是否为bmp文件
     * @author       liandi
     * @since        esign, 2017年10月24日
     * @param fileData   文件数据
     * @return
     */
    public static boolean isBmpFile(byte[] fileData)
    {
        if(null == fileData) return false;

        String header = getFileHeader(fileData).toUpperCase(Locale.ENGLISH);
        header = header.substring(0,4);

        return "424D".equals(header);
    }

    /**
     * 判断文件是否为pdf文件
     * @author       liandi
     * @since        esign, 2017年10月24日
     * @param fileData   文件数据
     * @return
     */
    public static boolean isPdfFile(byte[] fileData)
    {
        if(null == fileData) return false;

        String header = getFileHeader(fileData).toUpperCase(Locale.ENGLISH);
        header = header.substring(0,4);

        return "2550".equals(header);
    }

    public static boolean isPdfFile(String filePath)
    {
        if(StringUtils.isBlank(filePath)) return false;

        String header = getFileHeader(filePath).toUpperCase(Locale.ENGLISH);
        header = header.substring(0,4);

        return "2550".equals(header);
    }
    /**
     * 判断文件是否为word excel文件
     * @author       liandi
     * @since        esign, 2017年10月24日
     * @param fileData   文件数据
     * @return
     */
    public static boolean isWordOrExcel(byte[] fileData)
    {
        if(null == fileData) return false;

        String header = getFileHeader(fileData).toUpperCase(Locale.ENGLISH);
        header = header.substring(0,4);

        return "D0CF".equals(header);
    }

    /**
     * 判断文件是否为pfx文件
     * @author       liandi
     * @since        esign, 2017年11月16日
     * @param fileData    文件数据
     * @return
     */
    public static boolean isPfxFile(byte[] fileData)
    {
        if(null == fileData) return false;

        String header = getFileHeader(fileData).toUpperCase(Locale.ENGLISH);
        header = header.substring(0,4);

        return "3082".equals(header);
    }


    /**
     * 通过文件名获取文件的扩展名
     * @author       liandi
     * @since        esign, 2017年10月24日
     * @param fileName
     * @return
     */
    public static String getExpandedNameByFileName(String fileName)
    {
        if(StringUtils.isBlank(fileName)) return null;

        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public byte[] image2byte(String path) {
        byte[] data = null;
        FileImageInputStream input = null;
        try {
            input = new FileImageInputStream(new File(path));
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int numBytesRead = 0;
            while ((numBytesRead = input.read(buf)) != -1) {
                output.write(buf, 0, numBytesRead);
            }
            data = output.toByteArray();
            output.close();
            input.close();
        } catch (FileNotFoundException ex1) {
            ex1.printStackTrace();
        } catch (IOException ex1) {
            ex1.printStackTrace();
        }
        return data;
    }

    public static byte[] readInputStreamToBytes(InputStream in) throws IOException
    {
        if(null == in)
        {
            return null;
        }

        ByteArrayOutputStream out = null;
        try
        {
            out = new ByteArrayOutputStream(4096);

            byte[] data = new byte[4096];
            int len = 0;
            while((len = in.read(data)) > 0)
            {
                out.write(data,0,len);

            }

            return out.toByteArray();
        }
        finally
        {
            IOUtils.closeQuietly(out);
        }
    }
    /**
     * 将文件内容读取成二进制
     * @author       liandi
     * @since        esign, 2018年1月9日
     * @param filePath    文件的绝对路径
     * @return
     */
    public static byte[] readFileTobytes(String filePath)
    {
        InputStream in = null;

        try
        {
            in = new FileInputStream(filePath);
            return IOUtils.toByteArray(in);
        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (null != in)
                {
                    in.close();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        return null;
    }
    /**
     * 将二进制数据写入一个文件
     * @author       liandi
     * @since        esign, 2018年1月9日
     * @param data
     * @param filePath
     */
    public static void write(byte[] data, String filePath)
    {
        OutputStream out = null;
        try
        {
            out = new FileOutputStream(filePath);
            IOUtils.write(data, out);
        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (null != out)
                {
                    out.close();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public static boolean copyFile(String srcFileName, String destFileName,boolean overlay) {
        File srcFile = new File(srcFileName);
        if (!srcFile.exists()) {
            return false;
        }
        if (!srcFile.isFile()) {
            return false;
        }
        File destFile = new File(destFileName);
        if (destFile.exists()) {
            if (overlay) {
                new File(destFileName).delete();
            }
        } else if (!destFile.getParentFile().exists()){
            if (!destFile.getParentFile().mkdirs()) {
                return false;
            }
        }
        int byteread = 0;
        InputStream in = null;
        OutputStream out = null;

        try {
            in = new FileInputStream(srcFile);
            out = new FileOutputStream(destFile);
            byte[] buffer = new byte[1024];

            while ((byteread = in.read(buffer)) != -1) {
                out.write(buffer, 0, byteread);
            }
            buffer=null;
            return true;
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            return false;
        } finally {
            try {
                if (out != null)
                    out.close();
                if (in != null)
                    in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void getFile(byte[] bfile, String filePath,String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(filePath);
            if(!dir.exists()&&dir.isDirectory()){//判断文件目录是否存在
                dir.mkdirs();
            }
            file = new File(filePath + File.separator + fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bfile);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}