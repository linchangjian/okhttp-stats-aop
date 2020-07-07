package com.aniu.aspectjeasy;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @author zmh
 * @date 2020-01-07
 * <p>
 * Gzip压缩字符串工具类
 */
public class GzipUtils {

    public static final String GZIP_ENCODE_UTF_8 = "UTF-8";

    /**
     * base64 编码
     *
     * @param bytes 传入bytes
     * @return 编码成string类型的base64返回
     */
    public static String base64encode(byte[] bytes) {
        return new String(Base64.getEncoder().encode(bytes));
    }

    /**
     * base64 解码
     *
     * @param string 传入string类型的base64编码
     * @return 解码成byte类型返回
     */
    public static byte[] base64decode(String string) {
        return Base64.getDecoder().decode(string);
    }

    /**
     * 压缩字符串
     *
     * @param string 需要压缩的字符串
     * @return 压缩后内容 并转base64 返回
     */
    public static String gzip(String string) {
        String result = "";
//        if (StringUtils.isBlank(string)) {
//            return result;
//        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip;
        try {
            gzip = new GZIPOutputStream(out);
            gzip.write(string.getBytes(GZIP_ENCODE_UTF_8));
            gzip.close();
            out.close();
            result = base64encode(out.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 解压缩字符串
     *
     * @param string base64格式的压缩后字符串
     * @return 解码并解压缩后返回
     */
    public static String unGzip(String string) {
        String result = "";
//        if (StringUtils.isBlank(string)) {
//            return result;
//        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in;
        GZIPInputStream ungzip;
        byte[] bytes = base64decode(string);
        try {
            in = new ByteArrayInputStream(bytes);
            ungzip = new GZIPInputStream(in);
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = ungzip.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            ungzip.close();
            out.close();
            in.close();
            result = out.toString(GZIP_ENCODE_UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
        long temp = System.currentTimeMillis();
        String test = "Gzip压压压压缩缩缩缩缩测试测试测试测试测试试试试试试试试试试试aaaabbbbbccccccaaaabbbbbccccccaaaabbbbbccccccdddddd111111111111111111111111111111111111111";
        String gzip = gzip(test);
        String unGzip = unGzip(gzip);
        System.out.println("原文:" + test);
        System.out.println("Gzip压缩:" + gzip);
        System.out.println("Gzip解压:" + unGzip);
        System.out.println("整体消耗时间: " + (System.currentTimeMillis() - temp) + " ms");
    }
}