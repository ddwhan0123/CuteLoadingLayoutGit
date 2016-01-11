package pro.cuteloadingview.wjj.cuteloadingview.CustomView;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

import com.apkfuns.logutils.LogUtils;

import pro.cuteloadingview.wjj.cuteloadingview.R;

/**
 * Created by jiajiewang on 16/1/8.
 */
public class CuteLoadingView extends ImageView {
    //上下文对象
    private Context context;
    private String Image_AnimType;
    private Paint paint;
    private int XCenter, YCenter, XDraw, YDraw, mLength;
    private AnimatorSet animationSet;
    private ObjectAnimator objectAnimator1;
    private Resources resources;

    private int[] defaultColor;

    class AnimType {
        public static final String rotationY = "rotationY";
        public static final String rotationX = "rotationX";

    }

    public CuteLoadingView(Context context) {
        super(context);
        this.context = context;

    }

    public CuteLoadingView(Context context, AttributeSet attrs,String Image_AnimType) {
        super(context, attrs);
        this.context = context;
        this.Image_AnimType=Image_AnimType;
        initValue(context, attrs, Image_AnimType);
    }

    public CuteLoadingView(Context context, AttributeSet attrs, int defStyleAttr,String Image_AnimType) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        this.Image_AnimType=Image_AnimType;
        initValue(context, attrs, Image_AnimType);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CuteLoadingView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes,String Image_AnimType) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        this.Image_AnimType=Image_AnimType;
        initValue(context, attrs, Image_AnimType);
    }

    private void initValue(Context context, AttributeSet attrs,String Image_AnimType) {
        resources = getResources();
        //初始化颜色数组
        defaultColor = new int[6];
        makeColorData();

            //如果输入内容不合法,默认为旋转Y
            if (Image_AnimType != null) {
                if (!Image_AnimType.equals(AnimType.rotationY) &&
                        (!Image_AnimType.equals(AnimType.rotationX))) {
                    LogUtils.d("--->initValue Image_AnimType 输入不合法");
                    this.Image_AnimType = AnimType.rotationY;
                }
                //如果没传参数默认为 Y旋转
            } else {
                LogUtils.d("--->initValue Image_AnimType 为空");
                this.Image_AnimType = AnimType.rotationY;
            }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        XDraw = measureHandler(widthMeasureSpec);
        YDraw = measureHandler(heightMeasureSpec);
        setMeasuredDimension(XDraw, YDraw);
    }

    //尺寸测绘
    private int measureHandler(int measureSpec) {
        int vale = 80;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            vale = specSize;
        } else if (specMode == MeasureSpec.AT_MOST) {
            vale = Math.min(vale, specSize);
        } else {
            vale = 80;
        }
        return vale;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //中心點
        XCenter = XDraw / 2;
        YCenter = YDraw / 2;
        mLength = XDraw / 2;
        double radian30 = 30 * Math.PI / 180;
        float a = (float) (mLength * Math.sin(radian30));
        float b = (float) (mLength * Math.cos(radian30));
        float c = (YDraw - (2 * b)) / 2;
        initPaint();

//        Path path = new Path();
//        path.moveTo(XDraw, YDraw / 2);
//        path.lineTo(XDraw - a, YDraw - c);
//        path.lineTo(XDraw - a - mLength, YDraw - c);
//        path.lineTo(0, YDraw / 2);
//        path.lineTo(a, c);µ
//        path.lineTo(XDraw - a, c);
//        path.close();
//        http://snowcoal.com/article/520.html

        LogUtils.d("--->CuteLoadingView initValue  getMeasuredWidth : " + XDraw + " getMeasuredHeight : " + YDraw
                + " Image_AnimType : " + Image_AnimType);
//        canvas.drawPath(path, paint);
        paint.setColor(defaultColor[0]);
        canvas.drawLine(XDraw, YDraw / 2, XDraw - a, YDraw - c, paint);
        paint.setColor(defaultColor[1]);
        canvas.drawLine(XDraw - a, YDraw - c, XDraw - a - mLength, YDraw - c, paint);
        paint.setColor(defaultColor[2]);
        canvas.drawLine(XDraw - a - mLength, YDraw - c, 0, YDraw / 2, paint);
        paint.setColor(defaultColor[3]);
        canvas.drawLine(0, YDraw / 2, a, c, paint);
        paint.setColor(defaultColor[4]);
        canvas.drawLine(a, c, XDraw - a, c, paint);
        paint.setColor(defaultColor[5]);
        canvas.drawLine(XDraw, YDraw / 2, XDraw - a, c, paint);

        makeAnim(this, Image_AnimType);

    }

    //設置畫筆
    private void initPaint() {
        paint = new Paint();
        //抗鋸齒
        paint.setAntiAlias(true);
        //抖動處理
        paint.setDither(true);
        //空心
        paint.setStyle(Paint.Style.STROKE);
        //筆粗細
        paint.setStrokeWidth(5);
        //透明度
        paint.setAlpha(200);
    }

    //默认出现的位置
    private void makeAnim(View view, String AnimType) {
        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(view, AnimType, 0.0f, 360.0f);
        objectAnimator2.setRepeatCount(ValueAnimator.INFINITE);

        objectAnimator2.setRepeatMode(ValueAnimator.RESTART);

        objectAnimator2.setInterpolator(new AccelerateDecelerateInterpolator());

        animationSet = new AnimatorSet();//组合动画
        animationSet.play(objectAnimator2);
        animationSet.setDuration(1000);
        animationSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                LogUtils.d("--->onAnimationStart");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                LogUtils.d("--->onAnimationEnd");
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                LogUtils.d("--->onAnimationCancel");
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                LogUtils.d("--->onAnimationRepeat");
            }
        });
        animationSet.start();
    }

    private void makeColorData() {
        defaultColor[0] = resources.getColor(R.color.Red);
        defaultColor[1] = resources.getColor(R.color.Orange);
        defaultColor[2] = resources.getColor(R.color.Purple);
        defaultColor[3] = resources.getColor(R.color.Green);
        defaultColor[4] = resources.getColor(R.color.Cyan);
        defaultColor[5] = resources.getColor(R.color.Indigo);
    }

    //设置边界颜色
    public void setColor(int[] value) {
        if (value.length < 6) {
            LogUtils.d("--->传来的颜色数组长度为 : " + value.length);
            try {
                throw new Exception("array length must>6!!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {

            defaultColor = value;
        }
    }

    //设置颜色为初始颜色
    public void setDefaultColor() {
        makeColorData();
    }

    //开始动画
    public void startAnim() {
        if (animationSet.isRunning() == false) {
            objectAnimator1 = ObjectAnimator.ofFloat(this, "alpha", 0.0f, 1.0f).setDuration(500);
            objectAnimator1.start();
            animationSet.start();
        } else {
            LogUtils.d("--->startAnim 已经运行了");
        }
    }

    //关闭动画
    public void stopAnim() {
        if (animationSet.isRunning()) {
            animationSet.end();
            objectAnimator1 = ObjectAnimator.ofFloat(this, "alpha", 1.0f, 0.0f).setDuration(500);
            objectAnimator1.setDuration(500);
            objectAnimator1.start();
        }
    }

}
