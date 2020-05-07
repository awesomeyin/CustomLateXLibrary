package com.example.customlatexlibrary;

public class MathFormatUtils {
    /**
     * 获取根号内容
     * @param sqrt
     * @return
     */
    public static String getMathSqrt(String sqrt){
        if (sqrt.length() > 1){
            return sqrt.substring(1, sqrt.length()-1);
        }
        return sqrt;
    }

    public static String[] getMathSquare(String square){
        String[] squares = new String[2];
        if (square.length() < 2){
            squares[0] = square.substring(0, 1);
        }else {

            squares[0] = square.substring(0, square.indexOf("*"));
            String s2 = square.substring(square.indexOf("*")+1, square.length());
            squares[1] = s2.substring(0, s2.indexOf("*"));
//            squares[1] = square.substring(2, square.length()-1);
        }
//        Logger.e("分割后："+squares[0]+";"+squares[1]);
        return squares;
    }
}
