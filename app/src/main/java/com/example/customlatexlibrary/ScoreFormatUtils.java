package com.example.customlatexlibrary;


import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hao on 2017/10/21.
 */

public class ScoreFormatUtils {

    public static List<Map> getQuestion(String msg){
        String ss = msg.substring(1, msg.length()-1);
        StringBuilder sb = new StringBuilder();
        String[] content = ss.split("/");
        Map<String, String> conMap = new HashMap<String, String>();
        Map<String, String[]> answerMap = new HashMap<String, String[]>();
        List<Map> questions = new ArrayList<Map>();
        StringBuilder con = new StringBuilder();
        for(int i=0;i<content.length;i++){
            if(RegexUtils.isScoreInput(content[i])){
                String answer = content[i].substring(content[i].indexOf("『")+1, content[i].indexOf("』"));
                sb.append(answer);
                sb.append(",");
            }else{
                con.append(content[i]);
            }
        }

        String[] answers = sb.toString().split(",");
        conMap.put("content", con.toString());
        answerMap.put("answer", answers);
        questions.add(conMap);
        questions.add(answerMap);
        return questions;
    }

    /**
     * 获取不是输入框的分数值
     * @param answer
     * @return
     */
    public static String getScoreNoInput(String answer){
        String ss = answer.substring(1, answer.length()-1);
        String[] content = ss.split("/");
        StringBuilder con = new StringBuilder();
        for(int i=0;i<content.length;i++){
            if(!RegexUtils.isScoreInput(content[i])){
                con.append(content[i]);
                con.append(",");
            }
        }
        return con.toString();
    }

    /**
     * 获取分数的答案
     * @param msg
     * @return
     */
    public static String[] getScoreAnswers(String msg){
        String ss = msg.substring(1, msg.length()-1);
        StringBuilder sb = new StringBuilder();
        String[] content = ss.split("/");
        for(int i=0;i<content.length;i++){
            if(RegexUtils.isScoreInput(content[i])){
                String answer = content[i].substring(content[i].indexOf("『")+1, content[i].indexOf("』"));
                sb.append(answer);
                sb.append(",");
            }
        }
        String[] answers = sb.toString().split(",");
        return answers;
    }

    /**
     * 判断分数输入框上下的位置
     * @param msg
     * @return
     */
    public static int inputPosition(String msg){
        String ss = msg.substring(1, msg.length()-1);
        String[] content = ss.split("/");
        if(RegexUtils.isScoreInput(content[0]) && RegexUtils.isScoreInput(content[1])){
            return 2;
        }else if(RegexUtils.isScoreInput(content[0])){
            return 0;
        }else if (RegexUtils.isScoreInput(content[1])){
            return 1;
        }else {
            return 3;
        }
    }



    public static String getSocre(String msg){
        return msg.substring(1, msg.length()-1);
    }

    public static String getCycleText(String cycle){
        return cycle.substring(1,cycle.length()-1);
    }

    /**
     * 获取分数分子分母
     * @param msg
     * @return
     */
    public static String[] getFormatScore(String msg){
        String[] strs = new String[3];
        if (!TextUtils.isEmpty(msg)){
            if (RegexUtils.isFraction(msg)){
                strs[0] = msg.substring(0, msg.indexOf("|"));
                if (msg.length() > msg.indexOf("|")+1 && msg.length() > msg.indexOf("/") && msg.indexOf("|") > 0 && msg.indexOf("/") > 0){
                    strs[1] = msg.substring(msg.indexOf("|")+1,msg.indexOf("/"));
                }
                if (msg.length() > msg.indexOf("/")+1 && msg.indexOf("/") > 0){
                    strs[2] = msg.substring(msg.indexOf("/")+1, msg.length());
                }
            }else if (RegexUtils.isScore(msg)){
                strs[0] = msg.substring(0, msg.indexOf("/"));
                if (msg.length() > msg.indexOf("/")+1 && msg.indexOf("/") > 0){
                    strs[1] = msg.substring(msg.indexOf("/")+1,msg.length());
                }else if (msg.indexOf("/") == 0){
                    strs[1] = msg.substring(msg.indexOf("/")+1,msg.length());
                }
            }
        }
        return strs;
    }

    public static ArrayList<String[]> getFormatJiaoBiao(String msg) {
        ArrayList<String[]> arrayList = new ArrayList<>();
        if (!TextUtils.isEmpty(msg)) {
            msg = msg.replaceAll("[&]", "");
            if (msg.contains("|") && msg.contains(">")) {
                Log.d("后台返回的公式", "下角标：多组数据如下：");
                String[] strAll = msg.split("[|]");
                for (int i = 0; i < strAll.length; i++) {
                    String[] temp = strAll[i].split("[>]");
                    arrayList.add(temp);
                }
                return arrayList;
            } else {
                Log.d("后台返回的公式", "下角标：一组数据如下：");
                String[] strss = new String[3];
                strss[0] = msg.substring(0, msg.indexOf(">"));
                if (msg.length() > msg.indexOf(">") + 1 && msg.indexOf(">") > 0) {
                    strss[1] = msg.substring(msg.indexOf(">") + 1, msg.length());
                } else if (msg.indexOf(">") == 0) {
                    strss[1] = msg.substring(msg.indexOf(">") + 1, msg.length());
                }

                arrayList.add(strss);
                return arrayList;

            }
        }

        return arrayList;
    }

}
