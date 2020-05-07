package com.example.customlatexlibrary;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;


public class ExampleFragment extends Fragment implements OnClickListener {

    public static Fragment newInstance(String latex,
                                       int tag) {
        ExampleFragment fragment = new ExampleFragment();
        fragment.setTag(tag);
        fragment.setFormula(latex);
        return fragment;
    }

    private LaTexTextView mLaTexTextView;
    private String mLatex;
    private float mTextSize = 12;
    private int mTag;
    private EditText mSizeText;
    private TextView tv_custom;

    private void setFormula(String latex) {
        mLatex = latex;
    }

    private void setTag(int tag) {
        mTag = tag;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("latex", mLatex);
        outState.putFloat("textsize", mTextSize);
        outState.putInt("tag", mTag);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mLatex = savedInstanceState.getString("latex");
            mTextSize = savedInstanceState.getFloat("textsize");
            mTag = savedInstanceState.getInt("tag");
        }
    }

    ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        LinearLayout layout = (LinearLayout) inflater.inflate(
                R.layout.fragment_example, container, false);
        mLaTexTextView = (LaTexTextView) layout.findViewById(R.id.logo);
        tv_custom = (TextView) layout.findViewById(R.id.tv_custom);

        mSizeText = (EditText) layout.findViewById(R.id.size);
        layout.findViewById(R.id.set_textsize).setOnClickListener(this);

        return layout;
    }

    @Override
    public void onResume() {
        super.onResume();
//        setformula();
        mLaTexTextView.setLinketext(mLatex);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.set_textsize) {
            mTextSize = Integer.valueOf(mSizeText.getText().toString());
            mLaTexTextView.setTextSize(mTextSize);
            mLaTexTextView.setLinketext(mLatex);
            mLaTexTextView.setTextColor((int) -(Math.random() * (16777216 - 1) + 1));
            // 测试转化后台返回的数据
            Log.d("后台返回的公式", "分数");
            setLayout(getContext(), "#1/2#", 30, true, true);
            Log.d("后台返回的公式", "次方");
            setLayout(getContext(), "3*2*", 30, true, true);
            Log.d("后台返回的公式", "假分数");
            setLayout(getContext(), "#3|1/2#", 30, true, true);
            Log.d("后台返回的公式", "根号");
            setLayout(getContext(), "2@3@", 30, true, true);
            Log.d("后台返回的公式", "多组下角标");
            setLayout(getContext(), "&A>1|B>1&", 30, true, true);
            Log.d("后台返回的公式", "单组下角标");
            setLayout(getContext(), "&X>a&", 30, true, true);
        }
    }
    public void setLayout(Context context, String gap, float size, boolean isShowOriginalText, boolean isNeedPadding) {
        ArrayList<String> gaps = ArrayUtils.getQuestions(context, gap);
        for (int i = 0; i < gaps.size(); i++) {
            if (gaps.get(i).equals("input")) {
                Log.d("后台返回的公式", "input");
//                textView = getEtView(context);
//                addView(textView);
            } else {
                if (gaps.get(i).startsWith("|") || gaps.get(i).endsWith("|")) {
                    if (gaps.get(i).contains("@")) {
                        if (gaps.get(i).startsWith("|")) {
                            Log.d("后台返回的公式", "startsWith："+gaps.get(i).substring(0, gaps.get(i).indexOf("|") + 1));
                        }
                        String sqrt = MathFormatUtils.getMathSqrt(gaps.get(i).substring(gaps.get(i).indexOf("|") + 1, gaps.get(i).length()));
                        Log.d("后台返回的公式", "sqrt:::" + sqrt);
                    } else if (gaps.get(i).contains("*")) {
                        if (gaps.get(i).startsWith("|")) {
                            Log.d("后台返回的公式", ".startsWith(\"|\"):"+gaps.get(i).substring(0, gaps.get(i).indexOf("|") + 1));
                        }
                        if (gaps.get(i).length() < 3) {
                            //不执行
                            Log.d("后台返回的公式", ".不执行：：:"+gaps.get(i));
                        } else if (getString(R.string.answer_secret).equals(gaps.get(i))) {
                            Log.d("后台返回的公式", ".answer_secret：：:"+gaps.get(i)+"size:"+size);

                        } else {
                            String[] squares = MathFormatUtils.getMathSquare(gaps.get(i).substring(gaps.get(i).indexOf("|") + 1, gaps.get(i).length()));
                            if (squares.length < 2) {
                                Log.d("后台返回的公式", "squares[0]：" + gaps.get(i) + "squares[0]:" + squares[0]);
                            } else {
                                for (int j = 0; j < squares.length; j++) {
                                    Log.d("后台返回的公式", "squares[0]：" + gaps.get(i) + "squares[]:" + squares[j]);
                                }
                            }
                        }
                    } else {
                        Log.d("后台返回的公式", "addView：" + gaps.get(i) + "size:" + size);
                    }
                } else if (RegexUtils.isFraction(gaps.get(i))) {
                    //假分数
                    String[] scores = ScoreFormatUtils.getFormatScore(gaps.get(i));
                    for (int j = 0; j < scores.length; j++) {
                        Log.d("后台返回的公式", "假分数：" + scores[j]);
                    }
                } else if (RegexUtils.isScore(gaps.get(i)) && !TextUtil.isEmpty(gap) && gap.contains("#")
                        && gap.substring(gap.indexOf("#"), gap.lastIndexOf("#") + 1).contains(gaps.get(i))) {
                    //分数
                    if (gaps.get(i).contains(getString(R.string.my_answer))) {
                        String[] scores = ScoreFormatUtils.getFormatScore(gaps.get(i).substring(gaps.get(i).indexOf("：") + 1, gaps.get(i).length()));
                        for (int j = 0; j < scores.length; j++) {
                            Log.d("后台返回的公式", "分数的第" + (j + 1) + "项：" + scores[j]);
                        }
                    } else {
                        String[] scores = ScoreFormatUtils.getFormatScore(gaps.get(i));
                        for (int j = 0; j < scores.length; j++) {
                            Log.d("后台返回的公式", "scores：分数的第" + (j + 1) + "项：" + scores[j] + "  size:" + scores.length);
                        }
                    }
                } else if (gaps.get(i).contains("@")) {
                    String sqrt = MathFormatUtils.getMathSqrt(gaps.get(i));
                    Log.d("后台返回的公式", "sqrt 根号 ：" + sqrt);
                } else if (gaps.get(i).contains("*")) {
                    if (gaps.get(i).length() < 3) {
                        //不执行
                        Log.d("后台返回的公式", "不执行：" + gaps.get(i));
                    } else if (getString(R.string.answer_secret).equals(gaps.get(i))) {
                        Log.d("后台返回的公式", "* * *" + gaps.get(i));
                    } else {
                        String[] squares = MathFormatUtils.getMathSquare(gaps.get(i));
                        if (squares.length < 2) {
                            Log.d("后台返回的公式", "squares：" + squares[0]);
                        } else {
                            for (int j = 0; j < squares.length; j++) {
                                Log.d("后台返回的公式", "squares：几次方" + squares[j]);
                            }
                            mLaTexTextView.setLinketext("${" + squares[0] + "^" + squares[1] + "}" + "$");
                        }
                    }
                } else if (!TextUtil.isEmpty(gap) && gap.contains("&") && gap.contains(">")) {
                    ArrayList<String[]> scoresList = ScoreFormatUtils.getFormatJiaoBiao(gaps.get(i));
                    for (int j = 0; j < scoresList.size(); j++) {
                        String[] temp = scoresList.get(j);
                        for (int k = 0; k < temp.length; k++) {
                            Log.d("后台返回的公式", "下角标：" + temp[k]);
                        }
                    }
                }
            }
        }
    }

}
