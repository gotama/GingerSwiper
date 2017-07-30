package com.gautamastudios.gingerswiper.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.gautamastudios.gingerswiper.R;

/**
 * Created by james on 2017/07/30.
 */

public class SwipeView extends RelativeLayout {
    private float mCurrentX = 0;
    private LinearLayout mVisibleContainer;
    private FrameLayout mHiddenContainer;
    private boolean mAnimating;
    private boolean mSwiping;
    private boolean mIsOpen;
    private ImageView mTrashCan;

    public SwipeView(Context context) {
        super(context);
    }

    public SwipeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SwipeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public boolean isOpen() {
        return mIsOpen;
    }

    public boolean isAnimating() {
        return mAnimating;
    }

    public boolean isSwiping() {
        return mSwiping;
    }

    public ImageView getTrashCan() {
        return mTrashCan;
    }

    public void init() {
        mVisibleContainer = (LinearLayout) findViewById(R.id.visible_container);
        mHiddenContainer = (FrameLayout) findViewById(R.id.hidden_container);
        mTrashCan = (ImageView) mHiddenContainer.findViewById(R.id.trash_can);
    }

    OnClickListener mOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(getContext(), "The on click worked", Toast.LENGTH_SHORT).show();
        }
    };

    public void setSwipePosition(float deltaX) {
        mSwiping = true;
        TranslateAnimation swipeAnim = new TranslateAnimation(deltaX, deltaX, 0, 0);
        mCurrentX = deltaX;
        AnimationSet set = new AnimationSet(true);
        set.addAnimation(swipeAnim);
        set.setFillAfter(true);
        set.setFillEnabled(true);
        mVisibleContainer.startAnimation(set);
    }

    public void animateOpenSwipe(final float startX, final float endX, long duration) {
        mAnimating = true;
        mIsOpen = true;
        TranslateAnimation swipeAnim = new TranslateAnimation(mCurrentX, endX, 0, 0);
        //        AlphaAnimation alphaAnim = new AlphaAnimation(0, 1);
        AnimationSet set = new AnimationSet(true);
        set.addAnimation(swipeAnim);
        //        set.addAnimation(alphaAnim);
        set.setDuration(duration);
        set.setFillAfter(true);
        mVisibleContainer.startAnimation(set);
        final Runnable endAction = new Runnable() {
            public void run() {

                //                mTrashCan.setEnabled(true);
                mSwiping = false;
                mAnimating = false;
            }
        };

        setAnimationEndAction(set, endAction);
    }

    public void animateCloseSwipe(final float startX, final float endX, long duration) {
        mAnimating = true;
        mIsOpen = false;
        TranslateAnimation swipeAnim = new TranslateAnimation(startX, endX, 0, 0);
        AnimationSet set = new AnimationSet(true);
        set.addAnimation(swipeAnim);
        set.setDuration(duration);
        set.setFillAfter(true);
        mVisibleContainer.startAnimation(set);
        final Runnable endAction = new Runnable() {
            public void run() {
                //                ((ImageView) mHiddenContainer.findViewById(R.id.trash_can)).setOnClickListener(null);
                //                mTrashCan = null;
                mSwiping = false;
                mAnimating = false;
            }
        };

        setAnimationEndAction(set, endAction);
    }

    private void setAnimationEndAction(Animation animation, final Runnable endAction) {
        if (endAction != null) {
            animation.setAnimationListener(new AnimationListenerAdapter() {
                @Override
                public void onAnimationEnd(Animation animation) {
                    endAction.run();
                }
            });
        }
    }


    static class AnimationListenerAdapter implements Animation.AnimationListener {

        @Override
        public void onAnimationEnd(Animation animation) {
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }

        @Override
        public void onAnimationStart(Animation animation) {
        }
    }

}
