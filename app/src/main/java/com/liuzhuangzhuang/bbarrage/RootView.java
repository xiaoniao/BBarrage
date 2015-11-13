package com.liuzhuangzhuang.bbarrage;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by liuzhuang on 15/11/13.
 */
public class RootView extends ViewGroup {

    private int perDistance; // 每次移动的距离
    private int textViewCount; // 文字个数
    private int windowWidth;
    private int windowHeight;
    private Handler handler;

    public RootView(Context context) {
        super(context);
        init();
    }

    public RootView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RootView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        windowWidth = Utils.getWindowWidth(getContext());
        windowHeight = Utils.getWindowHeight(getContext());
        perDistance = windowWidth / (16);
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isAttachedToWindow) {
                    return;
                }
                if (textViewCount > 0) {
                    requestLayout();
                } else {

                }
                handler.postDelayed(this, 100);
            }
        }, 100);
    }

    private int generateY() {
        Random random = new Random();
        int y = random.nextInt(windowHeight) + 1;

        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            if (y == layoutParams.y) {
                generateY();
            } else {
                return y;
            }
        }
        return y;
    }

    private int generateColor() {
        Random random = new Random();
        int index = random.nextInt(Color.COLOR.length);
        return Color.COLOR[index];
    }

    public void addText(String message) {
        TextView textView = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.view_text, this, false);
        LayoutParams layoutParams = (LayoutParams) textView.getLayoutParams();
        layoutParams.x = windowWidth;
        layoutParams.y = generateY();
        textView.setText(message);
        textView.setTextColor(generateColor());
        addView(textView);
        textViewCount++;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int layoutWidth = getMeasuredWidth();
        int layoutHeight = getMeasuredHeight();

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            LayoutParams lp = (LayoutParams) child.getLayoutParams();
            lp.x = lp.x - perDistance;
        }
        setMeasuredDimension(resolveSize(layoutWidth, widthMeasureSpec), resolveSize(layoutHeight, heightMeasureSpec));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            LayoutParams lp = (LayoutParams) child.getLayoutParams();
            if (lp.x + child.getMeasuredWidth() < 0) {
                removeView(child);
                i--;
                textViewCount--;
            } else {
                child.layout(lp.x, lp.y, lp.x + child.getMeasuredWidth(), lp.y + child.getMeasuredHeight());
            }
        }
    }

    /**
     * 文字的位置
     */
    public static class LayoutParams extends ViewGroup.LayoutParams {
        int x; //x坐标
        int y; //y坐标

        public LayoutParams(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public LayoutParams(int w, int h) {
            super(w, h);
        }
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p.width, p.height);
    }

    private boolean isAttachedToWindow;

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        isAttachedToWindow = true;
    }

    @Override
    protected void onDetachedFromWindow() {
        isAttachedToWindow = false;
        super.onDetachedFromWindow();
    }

    /**
     * 附着在屏幕上
     * https://github.com/tyrantgit/ExplosionField
     */
    public static RootView attach2Window(Activity activity) {
        ViewGroup rootView = (ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT);
        View view = rootView.findViewWithTag("RootView");
        if (view != null && view instanceof RootView) {
            return (RootView) view;
        }
        view = new RootView(activity);
        view.setTag("RootView");
        rootView.addView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return (RootView) view;
    }

    /**
     * 从屏幕移除
     */
    public static void removeFromWindow(Activity activity) {
        ViewGroup rootView = (ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT);
        View view = rootView.findViewWithTag("RootView");
        if (view != null) {
            rootView.removeView(view);
        } else {
            Log.d("RootView", "view is null");
        }
    }

}