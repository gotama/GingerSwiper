package com.gautamastudios.gingerswiper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

import com.gautamastudios.gingerswiper.adapter.SwipeListViewAdapter;
import com.gautamastudios.gingerswiper.view.SwipeView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private List<String> mMessages = new ArrayList<String>();
    private SwipeListViewAdapter mAdapter;
    boolean mSwiping = false;
    boolean mItemPressed = false;
    private static final int SWIPE_DURATION = 350;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView) findViewById(R.id.listview);
        for (int i = 0; i < Gingers.famousGingers.length; ++i) {
            mMessages.add(Gingers.famousGingers[i]);
        }

        mAdapter = new SwipeListViewAdapter(this, mMessages, mOnTouchListener);
        mListView.setAdapter(mAdapter);
    }

    View.OnTouchListener mOnTouchListener = new View.OnTouchListener() {

        private float mDownX;
        private float xSlopDistance;
        private float ySlopDistance;
        private float lastX;
        private float lastY;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            SwipeView swipeView = (SwipeView) v;

            if (swipeView.isOpen()) {
                if (event.getX() > (swipeView.getWidth() - swipeView.getTrashCan().getWidth())) {
                    //                    Toast.makeText(getApplicationContext(), "The on click worked", Toast
                    // .LENGTH_SHORT).show();
                }
                return true;
            }
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (swipeView.isAnimating()) {
                        return true;
                    }
                    for (int i = 0; i < mListView.getChildCount(); ++i) {
                        SwipeView child = (SwipeView) mListView.getChildAt(i);
                        if (child.isOpen() && !child.equals(swipeView)) {
                            child.animateCloseSwipe(-150, 0, 500);
                        }
                    }

                    mItemPressed = true;
                    xSlopDistance = ySlopDistance = 0f;
                    mDownX = event.getX();
                    break;
                case MotionEvent.ACTION_CANCEL:
                    float cancelX = event.getX();
                    float cancelDeltaX = cancelX - mDownX;
                    float cancelDeltaXAbs = Math.abs(cancelDeltaX);
                    float cancelEndX;

                    if (cancelDeltaXAbs > 150 / 7 && cancelDeltaX < 0) {
                        cancelEndX = -150;
                        swipeView.animateOpenSwipe(0, cancelEndX, SWIPE_DURATION);
                        mSwiping = false;
                    }
                    mItemPressed = false;
                    break;
                case MotionEvent.ACTION_MOVE: {
                    if (swipeView.isAnimating()) {
                        return true;
                    }
                    final float curX = event.getX();
                    final float curY = event.getY();
                    xSlopDistance += Math.abs(curX - lastX);
                    ySlopDistance += Math.abs(curY - lastY);
                    lastX = curX;
                    lastY = curY;

                    float deltaX = curX - mDownX;
                    if (!mSwiping) {
                        if (xSlopDistance > ySlopDistance) {
                            mSwiping = true;
                            mListView.requestDisallowInterceptTouchEvent(true);
                        } else {
                            return true;
                        }
                    }
                    if (mSwiping && deltaX < 0) {
                        swipeView.setSwipePosition(deltaX);
                    }
                }
                break;
                case MotionEvent.ACTION_UP: {
                    if (swipeView.isAnimating()) {
                        return true;
                    }
                    if (swipeView.isSwiping()) {
                        float x = event.getX();
                        float deltaX = x - mDownX;
                        float deltaXAbs = Math.abs(deltaX);
                        float endX;

                        if (deltaXAbs > 150 / 7 && deltaX < 0) {
                            endX = -150;
                            swipeView.animateOpenSwipe(0, endX, SWIPE_DURATION);
                        } else {
                            endX = 0;
                            swipeView.animateCloseSwipe(deltaXAbs, endX, SWIPE_DURATION);
                        }

                        mSwiping = false;
                    } else {
                        mItemPressed = false;
                    }
                }
                break;
                default:
                    return false;
            }
            return true;
        }
    };
}
