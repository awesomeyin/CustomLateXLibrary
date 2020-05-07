package com.example.customlatexlibrary;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hao on 2017/10/21.
 */

public class RegexUtils {
    /**
     * 判断是否比较大小
     * @param msg
     * @return
     */
    public static boolean isCompareSize(String msg){
        if (!TextUtils.isEmpty(msg)){
            String compare = "[-—=＝*+<>＜＞×÷＋-－≈%≠]";
            Pattern p = Pattern.compile(compare);
            Matcher m = p.matcher(msg);
            return m.find();
        }else {
            return false;
        }
    }

    /**
     * 判断是否是分数
     * @param msg
     * @return
     */
    public static boolean isScore(String msg){
        if (!TextUtils.isEmpty(msg)){
            String score = "[/]";
            Pattern p = Pattern.compile(score);
            Matcher m = p.matcher(msg);
            return m.find();
        }else {
            return false;
        }
    }

    /**
     * 判断是否是假分数
     * @param msg
     * @return
     */
    public static boolean isFraction(String msg){
        if (!TextUtils.isEmpty(msg)){
            String score = "(?=[|]).*[/]";
            Pattern p = Pattern.compile(score);
            Matcher m = p.matcher(msg);
            return m.find();
        }else {
            return false;
        }
    }


    public static boolean isJiaoBiao(String msg){
        if (!TextUtils.isEmpty(msg)){
            String score = "[&]";
            Pattern p = Pattern.compile(score);
            Matcher m = p.matcher(msg);
            return m.find();
        }else {
            return false;
        }
    }

    /**
     * 判断是否是分数输入框
     * @param msg
     * @return
     */
    public static boolean isScoreInput(String msg){
        if (!TextUtils.isEmpty(msg)){
            String score = "[『』]";
            Pattern p = Pattern.compile(score);
            Matcher m = p.matcher(msg);
            return m.find();
        }else {
            return false;
        }
    }

    public static boolean isCycleText(String msg){
        if (!TextUtils.isEmpty(msg)){
            String score = "[##]";
            Pattern p = Pattern.compile(score);
            Matcher m = p.matcher(msg);
            return m.find();
        }else {
            return false;
        }
    }

    /**
     * 判断是否有大写字母
     * @param msg
     * @return
     */
    public static boolean isUpperCase(String msg){
        char[] chars = msg.toCharArray();
        for (int i=0;i<chars.length;i++){
            char c = chars[i];
            if (Character.isUpperCase(c)){//判断是否是大写字母
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否有小写字母
     * @param msg
     * @return
     */
    public static boolean isLowerCase(String msg){
        char[] chars = msg.toCharArray();
        for (int i=0;i<chars.length;i++){
            char c = chars[i];
            if (Character.isLowerCase(c)){//判断是否是小写字母
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否是数字
     * @param msg
     * @return
     */
    public static boolean isDigit(String msg){
        char[] chars = msg.toCharArray();
        for (int i=0;i<chars.length;i++){
            char c = chars[i];
            if (Character.isDigit(c)){//判断是否是数字
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否是符号
     * @param msg
     * @return
     */
    public static boolean isSymbol(String msg){
        char[] chars = msg.toCharArray();
        for (int i=0;i<chars.length;i++){
            char c = chars[i];
            if ("-—=＝*+<>＜＞×÷＋-－≈%≠".contains(String.valueOf(c))){
                return true;
            }
        }
        return false;
    }

    /**
     * 判定输入汉字，通过Unicode表
     *
     * @param c
     * @return
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    /**
     * 判断字符串是否是中文
     * @param str
     * @return
     */
    public static boolean isChinese(String str){
        Pattern p= Pattern.compile("^[\\u4E00-\\u9FA5\\uF900-\\uFA2D]+$");
        Matcher m = p.matcher(str);
        if(m.matches()){
            return true;
        }
        return false;
    }



}