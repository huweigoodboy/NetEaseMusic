package com.huwei.neteasemusic.util.img;

import android.graphics.Bitmap;

import com.google.webp.libwebp;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * 作者: andy
 * 时间: 16-4-8
 * 描述:
 * 修订: 暂无
 */
public class WebpUtils {

    static {
        System.loadLibrary("webp");
    }

    /**
     * 字节数组转换为bitmap对象
     *
     * @param encoded
     * @return
     */
    public static Bitmap byteToBitmap(byte[] encoded) {
        int[] width = new int[]{0};
        int[] height = new int[]{0};
        byte[] decoded = libwebp.WebPDecodeARGB(encoded, encoded.length, width,
                height);

        int[] pixels = new int[decoded.length / 4];
        ByteBuffer.wrap(decoded).asIntBuffer().get(pixels);

        return Bitmap.createBitmap(pixels, width[0], height[0],
                Bitmap.Config.ARGB_8888);
    }

    /**
     * 流转换为字节数组
     *
     * @param in
     * @return
     */
    public static byte[] streamToBytes(InputStream in) {
        ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
        byte[] buffer = new byte[1024];
        int len = -1;
        try {
            while ((len = in.read(buffer)) >= 0) {
                out.write(buffer, 0, len);
                out.flush();
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return out.toByteArray();
    }

    public static boolean isWebp(byte[] data) {
        return data != null && data.length > 12 && data[0] == 'R'
                && data[1] == 'I' && data[2] == 'F' && data[3] == 'F'
                && data[8] == 'W' && data[9] == 'E' && data[10] == 'B'
                && data[11] == 'P';
    }

    /**
     * 根据流判断是否为webP
     * @param is
     * @return
     */
    public static boolean isWebp(InputStream is) {
        int count = 20; //读取20个字节 来判断是否为webP
        byte[] b = new byte[count];
        try {
            //重置位置
            is.reset();

            int readCount = 0; // 已经成功读取的字节的个数
            while (readCount < count) {
                readCount += is.read(b, readCount, count - readCount);
            }

            //重置位置
            is.reset();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //不要 去关闭流   因为读取图片可能还需要
        }
        return isWebp(b);
    }

    /**
     * 获取webp版本
     * 用来测试JNI的调用
     *
     * @return
     */
    public static int getWebpVersion() {
        return libwebp.WebPGetDecoderVersion();
    }

}
