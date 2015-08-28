package com.sminger.vdhdemo.widget;

import android.content.Context;
import android.graphics.Point;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by sminger on 2015/8/28.
 */
public class EdgeDragLayout extends LinearLayout {
    private final ViewDragHelper mDragHelper;
    private View mEdgeTrackerView;
    private DragCallBack mDragCallBack;
    private Point mAutoBackOriginPos = new Point();

    public EdgeDragLayout(Context context) {
        this(context, null);
    }

    public EdgeDragLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EdgeDragLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mDragCallBack = new DragCallBack();
        mDragHelper = ViewDragHelper.create(this, 1.0f, mDragCallBack);
        mDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_RIGHT);

    }

    public class DragCallBack extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView (View view , int index) {
            return view == mEdgeTrackerView;
        }
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx)
        {
            final int leftBound = getPaddingLeft();
            final int rightBound = getWidth() - getPaddingRight() - child.getWidth();
            final int newLeft = Math.min(rightBound, Math.max(left, leftBound));
            return newLeft;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy)
        {
            final int topBound = getPaddingTop();
            final int bottomBound = getHeight() - getPaddingBottom() - child.getHeight();
            final int newtop = Math.min(bottomBound, Math.max(top, topBound));
            return newtop;
        }


        //手指释放的时候回调
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel)
        {
            if (releasedChild == mEdgeTrackerView)
            {
                //  mDragHelper.settleCapturedViewAt(mAutoBackOriginPos.x, mAutoBackOriginPos.y);
                invalidate();
            }
        }

        //在边界拖动时回调
        @Override
        public void onEdgeDragStarted(int edgeFlags, int pointerId)
        {
            mDragHelper.captureChildView(mEdgeTrackerView, pointerId);
        }

        @Override
        public int getViewHorizontalDragRange(View child)
        {
            return getMeasuredWidth()-child.getMeasuredWidth();
        }

        @Override
        public int getViewVerticalDragRange(View child)
        {
            return getMeasuredHeight()-child.getMeasuredHeight();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event)
    {
        return mDragHelper.shouldInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        mDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll()
    {
        if(mDragHelper.continueSettling(true))
        {
            invalidate();
        }
    }

    @Override



    protected void onLayout(boolean changed, int l, int t, int r, int b)
    {
        super.onLayout(changed, l, t, r, b);

        mAutoBackOriginPos.x = getMeasuredWidth() / 2;
        mAutoBackOriginPos.y = getMeasuredHeight() / 2;
    }

    @Override
    protected void onFinishInflate()
    {
        super.onFinishInflate();
        mEdgeTrackerView = getChildAt(0);
    }

}
