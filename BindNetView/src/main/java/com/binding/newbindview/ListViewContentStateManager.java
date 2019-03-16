package com.binding.newbindview;

import android.content.Context;


import java.util.ArrayList;
import java.util.List;

import com.binding.interfaces.BindNetAdapter;
import com.binding.interfaces.BindNetMode;
import com.binding.interfaces.BindRefreshListener;
import com.binding.interfaces.NetRefreshViewInterface;


/***
 * 管理listview内部数据状态的判断，有数据，无数据，网络错误等.
 */
public class ListViewContentStateManager {
    NetRefreshViewInterface mRefreshView;
    private BindNetAdapter mAdapter;
    private NotifyStateChangedListener mNotifyStateChangedListener;

    private int mPageSize = 10;
    private int mInitPage = 1;
    private int mPageNum = mInitPage;

    private List mTotalList = new ArrayList();
    private List mItemList;

    private Context mContext;

    public ListViewContentStateManager(NetRefreshViewInterface mRefreshView, Context mContext) {
        this.mRefreshView = mRefreshView;
        this.mContext = mContext;
    }

    public void bindList(List itemList) {
        this.mItemList = itemList;
        if (mPageNum == mInitPage) {
            mTotalList.clear();
        }
        if (itemList != null && itemList.size() > 0) {
            mTotalList.addAll(itemList);
        }
    }


    public List getmTotalList() {
        return mTotalList;
    }

    /***
     * 通知数据改变
     */
    public void notifyObserverDataChanged() {
        if (mAdapter == null) {
            throw new NullPointerException("adater 不能为空");
        }
        mAdapter.notifyMetaDataChange();
        bindingListChanged();
        // 设置错误提示内容
    }


    /**
     * 绑定的集合数据发生变化，自动映射到listview
     */
    private void bindingListChanged() {

        ViewConverter.ContentStates contentStates = null;

        //每次请求的集合数量
        if (mItemList == null || mItemList.size() == 0) {
            contentStates = buildStateIfEmpty();
            notifyState(contentStates);
            mRefreshView.onBindNetRefreshComplete(false);
            return;
        }

        if (mItemList.size() < mPageSize) {
            contentStates = ViewConverter.ContentStates.CONTENT;
            mRefreshView.onBindNetRefreshComplete(false);
        } else if (mItemList.size() >= mPageSize) {
            contentStates = ViewConverter.ContentStates.CONTENT;
            mPageNum++;
            mRefreshView.onBindNetRefreshComplete(true);
        }

        notifyState(contentStates);

    }

    private void notifyState(ViewConverter.ContentStates contentStates) {
        if (mNotifyStateChangedListener != null) {
            mNotifyStateChangedListener.nofity(contentStates);
        }
    }

    private ViewConverter.ContentStates buildStateIfEmpty() {
        ViewConverter.ContentStates contentStates = null;
        if (mTotalList.isEmpty()){
            if (NetUtil.isNetworkConnected(mContext)){
                contentStates = ViewConverter.ContentStates.EMPTY;
            }else {
                contentStates = ViewConverter.ContentStates.NET_ERROR;
            }

        } else {
            contentStates = ViewConverter.ContentStates.CONTENT;
        }
        return contentStates;
    }


    /**
     * 如果初始化为禁用刷新，那么在数据状态改变下则不会改变刷新模式
     *
     * @param mode
     */
    private void setInternalMode(BindNetMode mode) {
//        if (mRefreshView.getBindNetMode() == BindNetMode.DISABLED) {
//            return;
//        }
        // todo 暂时不用
//        setMode(mode);
    }


    private void setMode(BindNetMode mode) {
        switch (mode) {
            case BOTH:
                mRefreshView.setModeBoth();
                break;
            case PULL_FROM_UP:
                mRefreshView.setModePullFromUp();
                break;
            case PULL_FROM_DOWN:
                mRefreshView.setModePullFromDown();
                break;
            case DISABLED:
                mRefreshView.setModeDisabled();
                break;
        }
    }

    public NetRefreshViewInterface getmRefreshView() {
        return mRefreshView;
    }

    /***
     * 清空数据
     */
    public void clearData() {
        mPageNum = mInitPage;
        mItemList = null;
        mTotalList.clear();
    }

    public void resetPage(){
        mPageNum = mInitPage;
    }

    public void setOnRefreshListener(BindRefreshListener refresshListener) {
        mRefreshView.setBindNetOnRefreshListener(refresshListener);
    }

    public int getCurrentPage() {
        return mPageNum;
    }



    interface NotifyStateChangedListener {
        void nofity(ViewConverter.ContentStates contentState);
    }


    public void setmAdapter(BindNetAdapter mAdapter) {
        this.mAdapter = mAdapter;
        mRefreshView.setBindNetAdapter(mAdapter);
    }

    public void setmNotifyStateChangedListener(NotifyStateChangedListener mNotifyStateChangedListener) {
        this.mNotifyStateChangedListener = mNotifyStateChangedListener;
    }

    public void setmPageNum(int mPageNum) {
        this.mPageNum = mPageNum;
    }

    public void setmInitPage(int mInitPage) {
        this.mInitPage = mInitPage;
    }

    public void setmPageSize(int mPageSize) {
        this.mPageSize = mPageSize;
    }
}
