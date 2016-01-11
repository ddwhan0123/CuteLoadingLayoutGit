package pro.cuteloadingview.wjj.cuteloadingview.CustomView;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;

import pro.cuteloadingview.wjj.cuteloadingview.R;
import pro.cuteloadingview.wjj.cuteloadingview.tools.JumpingBeans;

/**
 * Created by jiajiewang on 16/1/10.
 */
public class CuteLoadingLayout extends LinearLayout {
    ObjectAnimator objectAnimator1;
    Context context;
    CuteLoadingView cuteLoadingView;
    TextView textView;
    int image_width, image_height;
    String text, Image_AnimType;
    JumpingBeans jumpingBeans;
    //默认字体大小
    int textSize = 15;

    public CuteLoadingLayout(Context context) {
        super(context);
        this.context = context;
    }

    public CuteLoadingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initValue(context, attrs);
    }

    public CuteLoadingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initValue(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CuteLoadingLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        initValue(context, attrs);
    }

    //初始化
    private void initValue(Context context, AttributeSet attrs) {
        //获取标签数组
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CuteLoadingView);
        try {
            image_width = (int) typedArray.getDimension(R.styleable.CuteLoadingView_image_width, 120);
            image_height = (int) typedArray.getDimension(R.styleable.CuteLoadingView_image_height, 120);
            text = typedArray.getString(R.styleable.CuteLoadingView_image_text);
            Image_AnimType = typedArray.getString(R.styleable.CuteLoadingView_image_AnimType);
        } catch (Exception e) {
            LogUtils.e("Unable to parse attributes due to: " + e.getMessage());
        } finally {
            typedArray.recycle();
        }
        if (text == null || text.length() < 1) {
            text = "加载中..";
        }

        LogUtils.d("--->CuteLoadingLayout initValue image_width : " + image_width + " image_height : " + image_height + " Image_AnimType : " + Image_AnimType);

        //设置水平
        this.setOrientation(LinearLayout.VERTICAL);
        this.setGravity(Gravity.CENTER);
        if (cuteLoadingView == null) {
            cuteLoadingView = new CuteLoadingView(context, attrs, Image_AnimType);
            cuteLoadingView.setLayoutParams(new LayoutParams(
                    image_width, image_height));
            this.addView(cuteLoadingView);
        }
        if (textView == null) {
            // 添加TextView组件
            textView = new TextView(context);
            // 设置组件TextView的内容
            textView.setText(text);
            textView.setTextSize(textSize);

            makeJump();

            // 设置组件的高，宽
            textView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT));
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, 30, 0, 0);
            textView.setLayoutParams(lp);
            // 将组件添加到布局中
            this.addView(textView);
        }
    }

    //开始动画
    public void startAnim() {
        cuteLoadingView.startAnim();
        objectAnimator1 = ObjectAnimator.ofFloat(this, "alpha", 0.0f, 1.0f).setDuration(500);
        objectAnimator1.start();
        makeJump();
    }

    //关闭动画
    public void stopAnim() {
        cuteLoadingView.stopAnim();
        objectAnimator1 = ObjectAnimator.ofFloat(this, "alpha", 1.0f, 0.0f).setDuration(500);
        objectAnimator1.start();
        jumpingBeans.stopJumping();
    }

    //设置颜色为初始颜色
    public void setDefaultColor() {
        cuteLoadingView.setDefaultColor();
    }

    //设置边界颜色
    public void setColor(int[] value) {
        cuteLoadingView.setColor(value);
    }

    //设置文字内容
    public void setText(String string) {
        textView.setText(string);
    }

    //隐藏Text
    public void hideTextView() {
        textView.setVisibility(View.INVISIBLE);
    }

    //关闭跳字动画
    public void stopTextJump() {
        jumpingBeans.stopJumping();
    }

    //设置字体大小
    public void setTextSize(int value) {
        textView.setTextSize(value);
    }

    private void makeJump() {
        jumpingBeans = JumpingBeans.with(textView)
                .makeTextJump(0, textView.getText().toString().indexOf(' '))
                .setIsWave(false)
                .setLoopDuration(1000)  // ms
                .build();
    }
}
