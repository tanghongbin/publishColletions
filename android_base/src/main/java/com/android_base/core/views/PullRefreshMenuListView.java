package com.android_base.core.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;

import com.android_base.BuildConfig;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.handmark.pulltorefresh.library.PullToRefreshAdapterViewBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

/**
 * Created by tanghongbin on 2017/4/5.
 */

 public class PullRefreshMenuListView extends PullToRefreshAdapterViewBase<SwipeMenuListView> {
    private static final String LOG_TAG = PullRefreshMenuListView.class.getSimpleName();
    private boolean DEBUG = BuildConfig.DEBUG;

    public PullRefreshMenuListView(Context context) {
        super(context);
    }

    public PullRefreshMenuListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public Orientation getPullToRefreshScrollDirection() {
        return Orientation.VERTICAL;
    }

    @Override
    protected SwipeMenuListView createRefreshableView(Context context, AttributeSet attrs) {

        SwipeMenuListView swipeMenuListView = new SwipeMenuListView(context,attrs);

        return swipeMenuListView;
    }

    @Override
    protected boolean isReadyForPullEnd() {
        return isLastItemVisible();
    }

    @Override
    protected boolean isReadyForPullStart() {
        return isFirstItemVisible();
    }

    private boolean isFirstItemVisible() {
        final Adapter adapter = getRefreshableView().getAdapter();

        if (null == adapter || adapter.isEmpty()) {
            if (DEBUG) {
                Log.d(LOG_TAG, "isFirstItemVisible. Empty View.");
            }
            return true;

        } else {

            /**
             * This check should really just be:
             * getRefreshableView().getFirstVisiblePosition() == 0, but PtRListView
             * internally use a HeaderView which messes the positions up. For
             * now we'll just add one to account for it and rely on the inner
             * condition which checks getTop().
             */
            if (getRefreshableView().getFirstVisiblePosition() <= 1) {
                final View firstVisibleChild = getRefreshableView().getChildAt(0);
                if (firstVisibleChild != null) {
                    return firstVisibleChild.getTop() >= getRefreshableView().getTop();
                }
            }
        }

        return false;
    }
    private boolean isLastItemVisible() {
        final Adapter adapter = getRefreshableView().getAdapter();

        if (null == adapter || adapter.isEmpty()) {
            if (DEBUG) {
                Log.d(LOG_TAG, "isLastItemVisible. Empty View.");
            }
            return true;
        } else {
            final int lastItemPosition = getRefreshableView().getCount() - 1;
            final int lastVisiblePosition = getRefreshableView().getLastVisiblePosition();

            if (DEBUG) {
                Log.d(LOG_TAG, "isLastItemVisible. Last Item Position: " + lastItemPosition + " Last Visible Pos: "
                        + lastVisiblePosition);
            }

            /**
             * This check should really just be: lastVisiblePosition ==
             * lastItemPosition, but PtRListView internally uses a FooterView
             * which messes the positions up. For me we'll just subtract one to
             * account for it and rely on the inner condition which checks
             * getBottom().
             */
            if (lastVisiblePosition >= lastItemPosition - 1) {
                final int childIndex = lastVisiblePosition - getRefreshableView().getFirstVisiblePosition();
                final View lastVisibleChild = getRefreshableView().getChildAt(childIndex);
                if (lastVisibleChild != null) {
                    return lastVisibleChild.getBottom() <= getRefreshableView().getBottom();
                }
            }
        }

        return false;
    }
}
