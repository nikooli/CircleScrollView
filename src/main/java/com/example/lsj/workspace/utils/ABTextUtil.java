package com.example.lsj.workspace.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.DisplayMetrics;
import android.util.Log;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Created with IntelliJ IDEA.
 * Author: wangjie  email:tiantian.china.2@gmail.com
 * Date: 14-3-28
 * Time: ����10:57
 */
public class ABTextUtil {
    public static final String TAG = ABTextUtil.class.getSimpleName();

    /**
     * �������������ܶ�
     *
     * @param context
     * @return
     */
    public static float getScaledDensity(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.scaledDensity;
    }

    /**
     * �����ֻ��ķֱ��ʴ� dp �ĵ�λ ת��Ϊ px(����)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * �����ֻ��ķֱ��ʴ� px(����) �ĵ�λ ת��Ϊ dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    /**
     * ��pxֵת��Ϊspֵ����֤���ִ�С����
     *
     * @param pxValue
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * ��spֵת��Ϊpxֵ����֤���ִ�С����
     *
     * @param spValue
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }


    /**
     * *************************************************************
     */

    public static boolean isEmpty(Collection collection) {
        return null == collection || collection.isEmpty();
    }

    public static boolean isEmpty(Map map) {
        return null == map || map.isEmpty();
    }

    public static boolean isEmpty(Object[] objs) {
        return null == objs || objs.length <= 0;
    }

    public static boolean isEmpty(int[] objs) {
        return null == objs || objs.length <= 0;
    }

    public static boolean isEmpty(CharSequence charSequence) {
        return null == charSequence || charSequence.length() <= 0;
    }

    public static boolean isBlank(CharSequence charSequence) {
        return null == charSequence || charSequence.toString().trim().length() <= 0;
    }


    /**
     * �滻�ı�ΪͼƬ
     *
     * @param charSequence
     * @param regPattern
     * @param drawable
     * @return
     */
    public static SpannableString replaceImageSpan(CharSequence charSequence, String regPattern, Drawable drawable) {
        SpannableString ss = charSequence instanceof SpannableString ? (SpannableString) charSequence : new SpannableString(charSequence);
        try {
            ImageSpan is = new ImageSpan(drawable);
            Pattern pattern = Pattern.compile(regPattern);
            Matcher matcher = pattern.matcher(ss);
            while (matcher.find()) {
                String key = matcher.group();
                ss.setSpan(is, matcher.start(), matcher.start() + key.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            }
        } catch (Exception ex) {
            Log.e(TAG, ex.toString());
        }

        return ss;
    }


    /**
     * ѹ���ַ�����Zip
     *
     * @param str
     * @return ѹ�����ַ���
     * @throws IOException
     */
    public static String compress(String str) throws IOException {
        if (str == null || str.length() == 0) {
            return str;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip = new GZIPOutputStream(out);
        gzip.write(str.getBytes());
        gzip.close();
        return out.toString("ISO-8859-1");
    }

    /**
     * ��ѹZip�ַ���
     *
     * @param str
     * @return ��ѹ���ַ���
     * @throws IOException
     */
    public static String uncompress(String str) throws IOException {
        if (str == null || str.length() == 0) {
            return str;
        }
        ByteArrayInputStream in = new ByteArrayInputStream(str
                .getBytes("UTF-8"));
        return uncompress(in);
    }

    /**
     * ��ѹZip�ַ���
     *
     * @param inputStream
     * @return ��ѹ���ַ���
     * @throws IOException
     */
    public static String uncompress(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPInputStream gunzip = new GZIPInputStream(inputStream);
        byte[] buffer = new byte[256];
        int n;
        while ((n = gunzip.read(buffer)) >= 0) {
            out.write(buffer, 0, n);
        }
        return out.toString();
    }

    /**
     * InputStream convert to string
     *
     * @param in
     * @return
     * @throws IOException
     */
    public static String inputStream2String(InputStream in) throws IOException {
        StringBuffer out = new StringBuffer();
        byte[] b = new byte[4096];
        for (int n; (n = in.read(b)) != -1; ) {
            out.append(new String(b, 0, n));
        }
        return out.toString();
    }


    /**
     * ��ѹGzip��ȡ
     *
     * @return
     */
    /*public static String inputStream2StringFromGZIP(InputStream is) {
        StringBuilder resultSb = new StringBuilder();
        BufferedInputStream bis = null;
        InputStreamReader reader = null;
        try {
            bis = new BufferedInputStream(is);
            bis.mark(2);
            // ȡǰ�����ֽ�
            byte[] header = new byte[2];
            int result = bis.read(header);
            // reset����������ʼλ��
            bis.reset();
            // �ж��Ƿ���GZIP��ʽ
            int headerData = getShort(header);
            // Gzip����ǰ�����ֽ���0x1f8b
            if (result != -1 && headerData == 0x1f8b) {
                is = new GZIPInputStream(bis);
            } else {
                is = bis;
            }
            reader = new InputStreamReader(is, "utf-8");
            char[] data = new char[100];
            int readSize;
            while ((readSize = reader.read(data)) > 0) {
                resultSb.append(data, 0, readSize);
            }
        } catch (Exception e) {
            Logger.e(TAG, e.toString());
        } finally {
            ABIOUtil.closeIO(is, bis, reader);
        }
        return resultSb.toString();
    }*/

    private static int getShort(byte[] data) {
        return (int) ((data[0] << 8) | data[1] & 0xFF);
    }


}
