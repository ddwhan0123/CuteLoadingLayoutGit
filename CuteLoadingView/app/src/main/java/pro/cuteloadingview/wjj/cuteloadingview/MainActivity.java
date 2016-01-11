package pro.cuteloadingview.wjj.cuteloadingview;


import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.apkfuns.logutils.LogUtils;

import pro.cuteloadingview.wjj.cuteloadingview.CustomView.CuteLoadingLayout;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button rotationX, stopAnim;
    Resources resources;
    CuteLoadingLayout cuteLoadingLayout;
    int[] colorArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cuteLoadingLayout = (CuteLoadingLayout) findViewById(R.id.cute);
//        设置字体大小
//        cuteLoadingLayout.setTextSize(30);
        resources = getResources();
//        设置颜色
        colorArray = new int[6];
        colorArray[0] = resources.getColor(R.color.Red);
        colorArray[1] = resources.getColor(R.color.DarkMagenta);
        colorArray[2] = resources.getColor(R.color.CadetBlue);
        colorArray[3] = resources.getColor(R.color.LightPink);
        colorArray[4] = resources.getColor(R.color.Black);
        colorArray[5] = resources.getColor(R.color.ForestGreen);
//        cuteLoadingLayout.setColor(colorArray);

        //设置默认的颜色(如果没有改过颜色可以不设置)
//        cuteLoadingLayout.setDefaultColor();

        //设置字
//        cuteLoadingLayout.setText("啦啦啦啦..");

        //隐藏字符串
//        cuteLoadingLayout.hideTextView();


        rotationX = (Button) findViewById(R.id.rotationXY);
        stopAnim = (Button) findViewById(R.id.stopAnim);
        stopAnim.setOnClickListener(this);
        rotationX.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int flag = v.getId();
        switch (flag) {
            case R.id.rotationXY:
                LogUtils.d("--->onClick rotationXY");
                cuteLoadingLayout.startAnim();
                break;

            case R.id.stopAnim:
                LogUtils.d("--->onClick stopAnim");
                cuteLoadingLayout.stopAnim();
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        cuteLoadingLayout.stopTextJump();
    }
}
