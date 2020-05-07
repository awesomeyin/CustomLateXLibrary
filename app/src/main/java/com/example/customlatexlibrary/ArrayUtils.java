package com.example.customlatexlibrary;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

public class ArrayUtils {

    /**
     * 获取填空题
     *
     * @param gap
     * @return
     */
    public static ArrayList<String> getQuestions(Context context, String gap) {
        ArrayList<String> arrayList = new ArrayList<>();
        if (context.getString(R.string.answer_secret).equals(gap)) {
            arrayList.add(gap);
            return arrayList;
        }

        if ("@@".equals(gap)) {
            return arrayList;
        }

        if ("**".equals(gap)) {
            return arrayList;
        }

        if (!TextUtils.isEmpty(gap)) {
            if (gap.contains("[?]")) {
                if (TextUtils.isEmpty(gap.substring(0, gap.indexOf("[")))) {
                    arrayList.add("input");
                } else {
                    getGapMsg(gap.substring(0, gap.indexOf("[")), arrayList);
                }

                if (TextUtils.isEmpty(gap.substring(gap.indexOf("]") + 1, gap.length()))) {
                    if (!"[?]".equals(gap))
                        arrayList.add("input");
                } else {
                    if (!arrayList.contains("input")) {
                        arrayList.add("input");
                    }
                    getGapMsg(gap.substring(gap.indexOf("]") + 1, gap.length()), arrayList);
                }
            } else {
                getGapMsg(gap, arrayList);
            }

        }
        return arrayList;
    }


    public static List<String> getGapMsg(String str, List<String> scores) {
        String s = str;
        if (s.contains("#")) {
            String[] msg = str.split("#");
            for (String string : msg) {
                if (string.contains("@")) {
                    sqrt(string, scores);
                } else if (string.contains("*")) {
                    square(string, scores);
                } else {
                    scores.add(string);
                }
            }

        } else {
            if (s.contains("@")) {
                sqrt(s, scores);
            } else if (s.contains("*")) {
                square(s, scores);
            } else {
                scores.add(s);
            }
        }
        return scores;
    }

    public static List<String> sqrt(String str, List<String> sqrts) {
        String s = str;
        if (s.contains("@")) {
            if (s.indexOf("@") >= 1) {
                String s1 = s.substring(0, s.indexOf("@"));
                if (s.indexOf("@") + 3 > s.length()) {
                    String s2 = s.substring(s.indexOf("@"), s.length());
                    s = s2;
                    if (s1.contains("*")) {
                        square(s1, sqrts);
                    } else {
                        sqrts.add(s1);
                    }
                } else {
                    String s2 = s.substring(s.indexOf("@") + 1, s.length());
                    if (-1 == s2.indexOf("@")) {
                        s = s2;
                    } else {
                        String s3 = s2.substring(0, s2.indexOf("@"));
                        s3 = "@" + s3 + "@";
                        s = s2.substring(s2.indexOf("@") + 1, s2.length());
                        if (s1.contains("*")) {
                            square(s1, sqrts);
                        } else {
                            Log.e("sqrt", "s1==" + s1);
                            sqrts.add(s1);
                        }
                        sqrts.add(s3);
                    }

                }
            } else {
                if (s.indexOf("@") != s.lastIndexOf("@")) {
                    if (s.indexOf("@") == 0) {
                        if (getStrCount(s, "@") % 2 == 0) {
                            char[] strs = s.toCharArray();
                            if (strs != null && strs.length > 0) {
                                StringBuffer sb = new StringBuffer();
                                for (char ch : strs) {
                                    if (ch == '@') {
                                        if (sb.toString().contains("@")) {
                                            sb.append(ch);
                                            String s4 = sb.toString();
                                            sqrts.add(s4);
                                            s = s.substring(s4.length(), s.length());
                                            break;
                                        }
                                        sb.append(ch);
                                    } else {
                                        sb.append(ch);
                                    }
                                }
                            }
                        } else {
                            String s4 = s.substring(s.indexOf("@"), s.lastIndexOf("@"));
                            s4 = s4 + "@";
                            sqrts.add(s4);
                            if (s.lastIndexOf("@") + 1 < s.length()) {
                                s = s.substring(s.lastIndexOf("@") + 1, s.length());
//                            sqrts.add(s5);
                            } else {
                                s = "";
                            }
                        }
                    }

                } else {
                    if (s.indexOf("@") >= 0 && s.length() >= (s.indexOf("@") + 3)) {
                        String s1 = s.substring(0, s.indexOf("@") + 3);
                        s = s.substring(s.indexOf("@") + 3, s.length());
                        sqrts.add(s1);
                    } else {
                        sqrts.add(s);
                    }
                }
            }
            if (s.contains("@") && s.length() < 3) {
                //不执行
                sqrts.add(s);
            } else {
                if (!TextUtil.isEmpty(s)) {
                    if (s.contains("*")) {
                        square(s, sqrts);
                    } else {
                        char[] strs = s.toCharArray();
                        boolean isRecycle = true;
                        if (strs != null && strs.length > 0) {
                            for (char ch : strs) {
                                if (ch != '@') {
                                    isRecycle = false;
                                    break;
                                }
                            }
                        }
                        if (isRecycle) {
                            sqrts.add(s);
                        } else {
                            sqrt(s, sqrts);
                        }
                    }
                }
            }
        } else {
            sqrts.add(s);
        }
        return sqrts;
    }

    public static List<String> square(String str, List<String> squares) {
        String s = str;
        if (s.contains("*")) {
            if (s.indexOf("*") >= 1) {
                String s1 = s.substring(0, s.indexOf("*"));
                if (s.indexOf("*") + 3 > s.length()) {
                    String s2 = s.substring(s.indexOf("*"), s.length());
                    s = s2;
                    squares.add(s1);
                } else {
                    String s2 = s.substring(s.indexOf("*") + 1, s.length());
                    if (-1 == s2.indexOf("*")) {
                        s = s2;
                    } else {
                        String s3 = s2.substring(0, s2.indexOf("*"));
                        s3 = s1 + "*" + s3 + "*";
                        s = s2.substring(s2.indexOf("*") + 1, s2.length());
//                        squares.add(s1);
                        if (s3.contains("@")) {
                            sqrt(s3, squares);
                        } else {
                            squares.add(s3);
                        }
                    }

                }

            } else {
                if (s.indexOf("*") != s.lastIndexOf("*")) {
                    if (s.indexOf("*") == 0) {
                        String s4 = s.substring(s.indexOf("*"), s.lastIndexOf("*"));
                        s4 = s4 + "*";
                        squares.add(s4);
                        if (s.lastIndexOf("*") + 1 < s.length()) {
                            s = s.substring(s.lastIndexOf("*") + 1, s.length());
                        } else {
                            s = "";
                        }
                    }

                } else {
                    if (s.indexOf("*") >= 0 && s.length() >= (s.indexOf("*") + 3)) {
                        String s1 = s.substring(0, s.indexOf("*") + 3);
                        s = s.substring(s.indexOf("*") + 3, s.length());
                        squares.add(s1);
                    } else {
                        squares.add(s);
                    }
                }
            }
            if (s.contains("*") && s.length() < 3) {
                //不执行
                squares.add(s);
            } else {
                if (!TextUtil.isEmpty(s)) {
                    char[] strs = s.toCharArray();
                    boolean isRecycle = true;
                    if (strs != null && strs.length > 0) {
                        for (char ch : strs) {
                            if (ch != '*') {
                                isRecycle = false;
                                break;
                            }
                        }
                    }
                    if (isRecycle) {
                        squares.add(s);
                    } else {
                        square(s, squares);
                    }
                }
            }

        } else {
            squares.add(s);
        }
        return squares;
    }

    /**
     * 获取字符数量
     *
     * @param string
     * @param str
     * @return
     */
    public static int getStrCount(String string, String str) {
        int counter = 0;
        for (int i = 0; i <= string.length() - str.length(); i++) {
            if (string.substring(i, i + str.length()).equals(str)) {
                counter++;
                //substring返回一个新字符串，它是此字符串的一个子字符串。
            }

        }
        return counter;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String joinString(CharSequence delimiter,
                                    Iterable<? extends CharSequence> elements) {
        Objects.requireNonNull(delimiter);
        Objects.requireNonNull(elements);
        StringJoiner joiner = new StringJoiner(delimiter);
        for (CharSequence cs : elements) {
            joiner.add(cs);
        }
        return joiner.toString();
    }
}
