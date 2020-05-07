package com.example.customlatexlibrary;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;

import androidx.annotation.ArrayRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Locale;

/**
 * Created by hao on 2017/9/25.
 */

public class TextUtil {
    private static final String TAG = "com.limi.base.TextUtil";

    /**
     * 比较两个字符串是否相等。
     *
     * @param text1 字符串
     * @param text2 字符串
     * @return 如果两个字符串都是 null 则返回 true，否则调用  方法进行判断。
     */
    public static boolean equals(@Nullable String text1, @Nullable String text2) {
        if (text1 == null) {
            return text2 == null;
        }
        return text1.equals(text2);
    }

    /**
     * 封装 {@link } 方法，避免空指针异常。
     *
     * @param text 字符串
     * @return trim 后的字符串
     */
    @Nullable
    public static String trim(@Nullable String text) {
        if (text == null) {
            return null;
        }
        return text.trim();
    }

    /**
     * 判断指定的字符串是否为空（忽略字符串开头、结尾的空格）。
     *
     * @param text 字符串
     * @return 是否为空
     */
    public static boolean isEmpty(@Nullable CharSequence text) {
        return (text == null || text.toString().trim().length() == 0);
    }

    /**
     * 封装 {@link Resources#getString(int)} 方法，处理异常。
     *
     * @param context Context
     * @param resId 字符串资源的 ID
     * @return 若发生异常返回 ""
     */
    public static String getString(@NonNull Context context, @StringRes int resId) {
        try {
            return context.getString(resId);
        } catch (Exception e) {
        }
        return "";
    }

    /**
     * 封装 {@link Resources#getText(int)} 方法，处理异常。
     *
     * @param context Context
     * @param resId 字符串资源的 ID
     * @return 若发生异常返回 ""
     */
    public static CharSequence getText(@NonNull Context context, @StringRes int resId) {
        try {
            return context.getText(resId);
        } catch (Exception e) {
        }
        return "";
    }

    /**
     * 封装 {@link Resources#getTextArray(int)} 方法，处理异常。
     *
     * @param context Context
     * @param resId 字符串资源的 ID
     * @return 若发生异常返回 ""
     */
    public static CharSequence[] getTextArray(@NonNull Context context, @ArrayRes int resId) {
        try {
            return context.getResources().getTextArray(resId);
        } catch (Exception e) {
        }
        return new CharSequence[0];
    }

    /**
     * 封装 {@link } 方法，格式化字符串。占位符格式为：%[index]$[type]。
     *
     * @param context Context
     * @param resId 字符串资源的 ID
     * @param args 格式化的参数
     * @return 格式化后的字符串
     */
    public static String formatStrings(
            @NonNull Context context, @StringRes int resId, Object... args) {
        return formatStrings(getString(context, resId), args);
    }

    /**
     * 封装 {@link } 方法，格式化字符串。占位符格式为：%[index]$[type]。
     *
     * @param format 需要格式化的字符串
     * @param args 格式化的参数
     * @return 格式化后的字符串
     */
    public static String formatStrings(@NonNull String format, Object... args) {
        return String.format(format, args);
    }

    /**
     * 封装 {@link } 方法，格式化字符串。占位符格式为：%[index]$[type]。
     *
     * @param format 需要格式化的字符串
     * @param arg 如果该值为 null 则自动替换为 ""
     * @return 格式化后的字符串
     */
    public static String formatString(@NonNull String format, Object arg) {
        if (arg == null) {
            return String.format(format, "");
        }
        return String.format(format, arg);
    }

    /**
     * 封装 {@link } 方法，格式化字符串。占位符格式为：%[index]$[type]。
     *
     * @param context Context
     * @param resId 字符串资源的 ID
     * @param arg 如果该值为 null 则自动替换为 ""
     * @return 格式化后的字符串
     */
    public static String formatString(@NonNull Context context, @StringRes int resId, Object arg) {
        String format = getString(context, resId);
        if (arg == null) {
            return String.format(format, "");
        }
        return String.format(format, arg);
    }

    /**
     * 把指定的字符串的第一个字母变成大写。
     *
     * @param text 字符串
     * @return 转换后的字符串
     */
    public String toUpperCaseFirstLetter(@NonNull String text) {
        if (TextUtils.isEmpty(text)) {
            return "";
        }

        if (text.length() == 1) {
            return text.toUpperCase(Locale.getDefault());
        }
        String firstLetter = text.substring(0, 1).toUpperCase(Locale.getDefault());
        return firstLetter + text.substring(1, text.length());
    }

    /**
     * 对指定的字符串做 URLEncode，并把 encode 结果中的 "+" 全部替换成 "%20"，"*" 替换成 "%2A"。
     *
     * @param text 字符串
     * @param encoding 编码
     * @return URLEncode 后的字符串
     */
    public static String urlEncode(@NonNull String text, @NonNull String encoding) {
        String result = "";
        try {
            result = URLEncoder.encode(text, encoding);
            result = result.replaceAll("\\+", "%20");
            result = result.replaceAll("\\*", "%2A");
        } catch (Exception e) {
        }
        return result;
    }

    /**
     * 使用指定的编码对字符串做 URLDecode。
     *
     * @param text 字符串
     * @param encoding 编码
     * @return URLDecode 后的字符串
     */
    public static String urlDecode(@NonNull String text, @NonNull String encoding) {
        try {
            return URLDecoder.decode(text, encoding);
        } catch (Exception e) {
        }
        return "";
    }

    /**
     * 根据指定的编码把 InputStream 转换为字符串。
     *
     * @param is InputStream
     * @param encoding 编码
     * @return 字符串，如果发生异常返回 ""
     */
    public static String readStream(InputStream is, String encoding) {
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = is.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            byte[] result = baos.toByteArray();
            return new String(result, encoding);
        } catch (Exception e) {
        } finally {
            IOUtil.closeQuietly(baos);
            IOUtil.closeQuietly(is);
        }
        return "";
    }
}
