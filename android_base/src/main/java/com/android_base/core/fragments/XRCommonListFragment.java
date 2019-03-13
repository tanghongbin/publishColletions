package com.android_base.core.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android_base.R;
import com.android_base.core.common.adapter.CustomBaseAdapter;
import com.android_base.core.interfaces.BindRefreshListener;
import com.android_base.core.interfaces.Callback;
import com.android_base.core.module.BaseListDataModule;
import com.android_base.core.views.bindingViews.BindingListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class XRCommonListFragment extends XRCommonRefreshFragment {
    protected View mRootView;
    protected BindingListView mListView;
    protected BaseListDataModule baseListDataModule;
    protected CustomBaseAdapter xrBaseAdapter;
    protected Configration mConfigration;
    protected OnListViewItemClickListener mOnListViewItemClickListener;

    public OnListViewItemClickListener getmOnListViewItemClickListener() {
        return mOnListViewItemClickListener;
    }

    public void setOnItemClickListener(OnListViewItemClickListener mOnListViewItemClickListener) {
        this.mOnListViewItemClickListener = mOnListViewItemClickListener;
    }

    public Configration getmConfigration() {
        return mConfigration;
    }

    public void setmConfigration(Configration mConfigration) {
        this.mConfigration = mConfigration;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        if(mRootView != null) {
            return mRootView;
        }
        mRootView= inflater.inflate(R.layout.common_list_fragment, container,false);
        findView();
        initModule();
        loadListData();
        return mRootView;
    }


    /**
     * 加载列表数据
     */
    private void loadListData() {
        baseListDataModule.loadDataList(getArguments(),mListView.getPageNo(),
                new Callback<List>() {
            @Override
            public void onSuccess(List noticesBeen) {
                mListView.bindList(noticesBeen);
                mListView.notifyObserverDataChanged();
            }

            @Override
            public void onFailed(String message) {
                mListView.bindList(null);
                mListView.notifyObserverDataChanged(message);
            }
        });
    }

    private void initModule() {
        try {
            Class moduleClass = Class.forName(mConfigration.moduleClassName);
            Constructor moduleConstructor = moduleClass.getConstructor();
            baseListDataModule = (BaseListDataModule) moduleConstructor.newInstance();
            if (mConfigration.pageSize != 0){
                mListView.setPageSize(mConfigration.pageSize);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void findView(){
        mListView = (BindingListView) mRootView.findViewById(R.id.bindListView);
        mListView.setNoContentClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListView.resetPage();
                loadListData();
            }
        });

        mListView.setBindRefreshListener(new BindRefreshListener() {
            @Override
            public void pullDownRefresh() {
                mListView.resetPage();
                loadListData();
            }

            @Override
            public void pullUpToLoadMore() {
                loadListData();
            }
        });
        ListView listView = (ListView) mListView.getRefreshView().getBindNetRefreshableView();
        listView.setDividerHeight(0);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (getmOnListViewItemClickListener() != null){
                    getmOnListViewItemClickListener().onItemClick(position,mListView.getmList());
                }
            }
        });
        checkModuleAndAdapter();
        xrBaseAdapter = createAdapter();
        mListView.setAdapter(xrBaseAdapter);
    }

    /**
     * 创建适配器
     * @return
     */
    private CustomBaseAdapter createAdapter() {
        CustomBaseAdapter adapter = null;
        try {
            Class adapterClass = Class.forName(mConfigration.adapterClassName);
            Constructor struction = adapterClass.getConstructor(Context.class, List.class);
            adapter = (CustomBaseAdapter) struction.newInstance(_mActivity, mListView.getmList());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return adapter;
    }

    /**
     * 检查模型和适配器
     */
    private void checkModuleAndAdapter() {
        if (mConfigration == null || TextUtils.isEmpty(mConfigration.adapterClassName) ||
                TextUtils.isEmpty(mConfigration.moduleClassName)){
            throw new NullPointerException("模型和适配器类名不能为空");
        }
    }

    public interface OnListViewItemClickListener{
        void onItemClick(int position,List list);
    }

}