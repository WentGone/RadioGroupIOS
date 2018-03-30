package cn.mdruby.radiobuttonforios;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Went_Gone on 2018/3/30.
 */

public class RadioGroupSwitch extends RadioGroup {
    private static final String TAG = "RadioGroupSwitch";
    private int viewWidth,viewHeight;
    private Context context;

    public RadioGroupSwitch(Context context) {
        this(context,null);
    }

    public RadioGroupSwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initAttrs(context,attrs);
        initView(context);
        this.setBackground(getResources().getDrawable(R.drawable.radiogroup_radius_background));
        Log.e(TAG, "RadioGroupSwitch: 初始化" );
    }

    private void initAttrs(Context context, AttributeSet attrs) {
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 获取宽-测量规则的模式和大小
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        // 获取高-测量规则的模式和大小
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        // 设置wrap_content的默认宽 / 高值
        // 默认宽/高的设定并无固定依据,根据需要灵活设置
        // 类似TextView,ImageView等针对wrap_content均在onMeasure()对设置默认宽 / 高值有特殊处理,具体读者可以自行查看
        int mWidth = 40;
        int mHeight = 60;

        // 当布局参数设置为wrap_content时，设置默认值
        if (getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT && getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {

            setMeasuredDimension(mWidth, mHeight);
            viewWidth = mWidth;
            viewHeight = mHeight;
            // 宽 / 高任意一个布局参数为= wrap_content时，都设置默认值
        } else if (getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(mWidth, heightSize);
            viewWidth = mWidth;
            viewHeight = heightSize;
        } else if (getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(widthSize, mHeight);
            viewWidth = widthSize;
            viewHeight = mHeight;
        }else {
            setMeasuredDimension(widthSize, heightSize);
            viewWidth = widthSize;
            viewHeight = heightSize;
            Log.e(TAG, "onMeasure: "+viewWidth+"-"+viewHeight );
        }


        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            //这个很重要，没有就不显示
//            getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);
            getChildAt(i).measure(viewWidth, viewHeight);
        }

//        initView(context);
    }
/*
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }*/

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        int childCount = getChildCount();
        Log.e(TAG, "onLayout: " +viewWidth/childCount+"-"+viewHeight);
        int left = 0;
        int top = 0;
        for (int i = 0; i < childCount; i++) {
            RadioButton rb = (RadioButton) getChildAt(i);
            int orientation = getOrientation();
            if (orientation == RadioGroup.HORIZONTAL){
                rb.setBackgroundColor(i == 0?Color.GREEN:i == 1?Color.YELLOW:Color.BLUE);
                rb.layout(left,0,left+(viewWidth/childCount),viewHeight);
                left += viewWidth/childCount;
            }else {
                rb.setBackgroundColor(i == 0?Color.GREEN:i == 1?Color.YELLOW:Color.BLUE);
                rb.layout(0,top,viewWidth+l,top+(viewHeight/childCount));
                top += viewHeight/childCount;
            }

            initRadiButton(i);
        }
    }

    private void initRadiButton(int position) {
        RadioButton rb = (RadioButton) getChildAt(position);
        rb.setBackground(getResources().getDrawable(R.drawable.radiobutton_left_background));

        GradientDrawable gradientDrawable = getGradientDrawable(rb);
        gradientDrawable.setColor(Color.parseColor("#ff0000"));

     /*   StateListDrawable mySelectorGrad = (StateListDrawable) rb.getBackground();

        try {
            Class slDraClass = StateListDrawable.class;
            Method getStateCountMethod = slDraClass.getDeclaredMethod("getStateCount", new Class[0]);
            Method getStateSetMethod = slDraClass.getDeclaredMethod("getStateSet", int.class);
            Method getDrawableMethod = slDraClass.getDeclaredMethod("getStateDrawable", int.class);
            int count = (Integer) getStateCountMethod.invoke(mySelectorGrad, new Object[]{});//对应item标签
            Log.d(TAG, "state count ="+count);
            for(int i=0;i < count;i++) {
                int[] stateSet = (int[]) getStateSetMethod.invoke(mySelectorGrad, i);//对应item标签中的 android:state_xxxx
                if (stateSet == null || stateSet.length == 0) {
                    Log.d(TAG, "state is null");
                    GradientDrawable drawable = (GradientDrawable) getDrawableMethod.invoke(mySelectorGrad, i);//这就是你要获得的Enabled为false时候的drawable
                    drawable.setColor(Color.parseColor("#ff0000"));
                } else {
                    for (int j = 0; j < stateSet.length; j++) {
                        Log.d(TAG, "state =" + stateSet[j]);
                        Drawable drawable = (Drawable) getDrawableMethod.invoke(mySelectorGrad, i);//这就是你要获得的Enabled为false时候的drawable
                    }
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }*/
    }

    private GradientDrawable getGradientDrawable(View view){
        GradientDrawable gradientDrawable = null;
        StateListDrawable mySelectorGrad = (StateListDrawable) view.getBackground();
        try {
            Class slDraClass = StateListDrawable.class;
            Method getStateCountMethod = slDraClass.getDeclaredMethod("getStateCount", new Class[0]);
            Method getStateSetMethod = slDraClass.getDeclaredMethod("getStateSet", int.class);
            Method getDrawableMethod = slDraClass.getDeclaredMethod("getStateDrawable", int.class);
            int count = (Integer) getStateCountMethod.invoke(mySelectorGrad, new Object[]{});//对应item标签
            Log.d(TAG, "state count ="+count);
            for(int i=0;i < count;i++) {
                int[] stateSet = (int[]) getStateSetMethod.invoke(mySelectorGrad, i);//对应item标签中的 android:state_xxxx
                if (stateSet == null || stateSet.length == 0) {
                    Log.d(TAG, "state is null");
                    GradientDrawable drawable = (GradientDrawable) getDrawableMethod.invoke(mySelectorGrad, i);//这就是你要获得的Enabled为false时候的drawable
                    gradientDrawable = drawable;
                } else {
                    for (int j = 0; j < stateSet.length; j++) {
                        Log.d(TAG, "state =" + stateSet[j]);
//                        Drawable drawable = (Drawable) getDrawableMethod.invoke(mySelectorGrad, i);//这就是你要获得的Enabled为false时候的drawable
                        GradientDrawable drawable = (GradientDrawable) getDrawableMethod.invoke(mySelectorGrad, i);//这就是你要获得的Enabled为false时候的drawable
                        drawable.setColor(Color.BLUE);

                    }
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return gradientDrawable;
    }


    private void initView(Context context) {
        RadioButton rb = new RadioButton(context);
        this.addView(rb);
        RadioButton rb1 = new RadioButton(context);
        this.addView(rb1);
        RadioButton rb2 = new RadioButton(context);
        this.addView(rb2);
    }
}
