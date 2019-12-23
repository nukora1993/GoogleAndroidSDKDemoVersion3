package com.example.googlesdkversion3demo.chapter4;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

import com.example.googlesdkversion3demo.R;

public class MyViewGroup extends ViewGroup {
    private static final String TAG="Chapter4Log";
    private Scroller mScroller;
    private VelocityTracker mVelocitiyTracker;

    private int mScrollingX=0;
    private int mCurrentLayoutFlag=0;
    private float mLastMovingX;
    private static final int FLING_VELOCITY=1000;
    private final static int REST_STATE=0x001;
    private final static int SCROLLING_STATE=0x002;
    private int mTouchState=REST_STATE;
    private int mScaledTouchSlop=0;

    public MyViewGroup(Context context){
        super(context);
        mScroller=new Scroller(context);
        mScaledTouchSlop= ViewConfiguration.get(getContext()).getScaledTouchSlop();

        setLayoutParams(new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.MATCH_PARENT
        ));

    }

    public MyViewGroup(Context context, AttributeSet attrs){
        super(context,attrs);
        mScroller=new Scroller(context);
        ViewConfiguration.get(getContext()).getScaledTouchSlop();
        setLayoutParams(new LayoutParams(
                LayoutParams.WRAP_CONTENT,LayoutParams.MATCH_PARENT
        ));

        //获得属性集合
        TypedArray mTypedArray=getContext().obtainStyledAttributes(
                attrs, R.styleable.SlideStyledAttributes
        );

        //获取设置的值
        mCurrentLayoutFlag=mTypedArray.getInteger(R.styleable.SlideStyledAttributes_view_screen,0);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //如果是滑动事件，并且当前状态不是REST_STATE，那么需要拦截以控制滑动
        if((ev.getAction()==MotionEvent.ACTION_MOVE)&&
                (mTouchState!=REST_STATE)){
            return true;
        }

        //否则说明要么不是滑动，要么是REST_STATE
        switch (ev.getAction()){
            case MotionEvent.ACTION_MOVE:
                //move距离,如果超过阈值，认为用户在滑动，修改状态
                int intShiftX=(int)Math.abs(ev.getX()-mLastMovingX);
                boolean bMovingDiff=intShiftX>mScaledTouchSlop;
                if(bMovingDiff){
                    mTouchState=SCROLLING_STATE;
                }
                break;
                //在刚按下的时候，记录当前坐标
            case MotionEvent.ACTION_DOWN:
                mLastMovingX=ev.getX();
                mTouchState=mScroller.isFinished()?REST_STATE:SCROLLING_STATE;
                break;
            case MotionEvent.ACTION_CANCEL:
                //感觉并不会被调用，因为一旦决定拦截，那么该函数不会被再次执行，所以应该不会监听到UP
            case MotionEvent.ACTION_UP:
                mTouchState=REST_STATE;
                break;

        }
        return mTouchState!=REST_STATE;

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(mVelocitiyTracker==null){
            mVelocitiyTracker=VelocityTracker.obtain();
        }

        mVelocitiyTracker.addMovement(event);
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
            {
                if(!mScroller.isFinished()){
                    mScroller.abortAnimation();
                }
                mLastMovingX=event.getX();
                break;
            }
            case MotionEvent.ACTION_UP:{
                VelocityTracker velocityTracker=mVelocitiyTracker;
                velocityTracker.computeCurrentVelocity(1000);
                float velocityX=velocityTracker.getXVelocity();
                //手指向右移动表示向左划
                if(velocityX>FLING_VELOCITY&&mCurrentLayoutFlag>0){
                    snapToScreen(mCurrentLayoutFlag-1);
                }
                else if(velocityX<-FLING_VELOCITY&&mCurrentLayoutFlag<getChildCount()-1){
                    snapToScreen(mCurrentLayoutFlag+1);
                }
                else{
                    snapToDestination();
                }
                if(mVelocitiyTracker!=null){
                    mVelocitiyTracker.recycle();
                    mVelocitiyTracker=null;
                }
                mTouchState=REST_STATE;
                break;
            }
            case MotionEvent.ACTION_CANCEL:{
                mTouchState=REST_STATE;
            }

        }

        mScrollingX=getScrollX();
        return true;
    }

    private void snapToDestination(){
        int screenWidth=getWidth();
        //滑倒哪个layout
        int whichScreen=(mScrollingX+(screenWidth/2))/screenWidth;
        snapToScreen(whichScreen);
    }

    private void snapToScreen(int whichScreen){
        mCurrentLayoutFlag=whichScreen;

        final int newX=whichScreen*getWidth();
        final int delta=newX-mScrollingX;

        //scroller负值表示向右滑动
        mScroller.startScroll(
                mScrollingX,
                0,
                delta,
                0,
                Math.abs(delta)*2
        );
        invalidate();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childLeft=0;
        final int count=getChildCount();
        for (int i = 0; i <count ; i++) {
            final View child=getChildAt(i);
            if(child.getVisibility()!= View.GONE){
                final int childWidth=child.getMeasuredWidth();
                child.layout(childLeft,
                        0,
                        childLeft+childWidth,
                        child.getMeasuredHeight());

                childLeft+=childWidth;
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final int width=MeasureSpec.getSize(widthMeasureSpec);
        final int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        if(widthMode!=MeasureSpec.EXACTLY){
            throw new IllegalArgumentException("errmode.");
        }

        final int heightMode=MeasureSpec.getMode(heightMeasureSpec);
        if(heightMode!=MeasureSpec.EXACTLY){
            throw new IllegalArgumentException("errmode.");
        }

        final int count=getChildCount();
        for (int i = 0; i <count ; i++) {
            getChildAt(i).measure(widthMeasureSpec,heightMeasureSpec);
        }
        Log.d(TAG,"onMeasure");
        scrollTo(mCurrentLayoutFlag*width,0);
    }

    @Override
    public void computeScroll() {
        if(mScroller.computeScrollOffset()){
            Log.d(TAG,"computeScroll");
            mScrollingX=mScroller.getCurrX();
            scrollTo(mScrollingX,0);
            postInvalidate();
        }
    }
}
