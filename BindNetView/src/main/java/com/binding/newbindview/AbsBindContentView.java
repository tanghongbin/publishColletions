package com.binding.newbindview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.binding.ClickableView;
import com.binding.DefaultErrorView;
import com.binding.R;
import com.binding.interfaces.BindNetAdapter;
import com.binding.interfaces.BindNetMode;
import com.binding.interfaces.BindRefreshListener;
import com.binding.interfaces.NetRefreshViewInterface;

public abstract class AbsBindContentView extends FrameLayout implements ListViewContentStateManager.NotifyStateChangedListener {

    private ListViewContentStateManager mStateManager;
    private ViewConverter mViewConverter;
    protected Context mContext;
    private String errorViewClass;



    public AbsBindContentView(@NonNull Context context) {
        super(context);
        init(context,null);
    }

    public AbsBindContentView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public abstract View getRefreshView();

    //初始化
    protected final void init(Context context, AttributeSet attrs) {
        mContext = context;

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AbsBindContentView);
        int mErrorImageRes = typedArray.getResourceId(R.styleable.AbsBindContentView_errorImage, 0);
        int mEmptyImageRes = typedArray.getResourceId(R.styleable.AbsBindContentView_emptyImage, 0);
        String mEmptyMessage = typedArray.getString(R.styleable.AbsBindContentView_emptyMessage);
        String mErrorMessage = typedArray.getString(R.styleable.AbsBindContentView_errorMessage);
        errorViewClass = typedArray.getString(R.styleable.AbsBindContentView_errorViewClass);
        boolean enabledPreLoadView = typedArray.getBoolean(R.styleable.AbsBindContentView_enabledPreLoadView,true);
        typedArray.recycle();

        HintConfigration hintConfigration = new HintConfigration(mEmptyImageRes,mEmptyMessage,
                mErrorImageRes,mErrorMessage);

        TwoTuples<NetRefreshViewInterface, Map<ViewConverter.ContentStates, View>> twoTuples =
                generateViews(hintConfigration);

        mStateManager = new ListViewContentStateManager(twoTuples.getM(),mContext);
        mViewConverter = new ViewConverter();

        mViewConverter.setConvertedContainer(this);

        mViewConverter.setStatusMap(twoTuples.getK());


        // 设置默认的加载视图,如果不启用那么则是contentview
        ViewConverter.ContentStates initStatus = null;
        if (enabledPreLoadView) {
            initStatus = ViewConverter.ContentStates.PRE_LOAD;
        } else  {
            initStatus = ViewConverter.ContentStates.CONTENT;
        }
        mViewConverter.convertView(initStatus);

        mStateManager.setmNotifyStateChangedListener(this);
    }

    /***
     * 生成各种不同类型的view
     *
     */
    private TwoTuples<NetRefreshViewInterface,Map<ViewConverter.ContentStates,View>>
    generateViews(HintConfigration hintConfigration) {

        //预加载view
        View preLoadView = generatePrdloadView();

        //listview或者gridview显示视图
        NetRefreshViewInterface netRefreshViewInterface = generateBindView();
        View contentView = netRefreshViewInterface.getBindView();

        // 空视图，无内容时
        View emptyView = generateEmptyView(hintConfigration.getEmptyResId(),
                hintConfigration.getEmptyMessage());

        // 错误view
        View errorView = generateErrorView(hintConfigration.getErrorResId(),
                hintConfigration.getErrorMessage());

        Map<ViewConverter.ContentStates,View> viewMap = new HashMap<>();

        viewMap.put(ViewConverter.ContentStates.PRE_LOAD,preLoadView);
        viewMap.put(ViewConverter.ContentStates.CONTENT,contentView);
        viewMap.put(ViewConverter.ContentStates.EMPTY,emptyView);
        viewMap.put(ViewConverter.ContentStates.NET_ERROR,errorView);

        return new TwoTuples<>(netRefreshViewInterface,viewMap);


    }

    private View generateEmptyView(int mEmptyImageRes,String mErrorHintText) {
        return generateErrorView(mEmptyImageRes, mErrorHintText);
    }


    /**
     * 当默认显示内容为空时，填充默认视图
     */
    protected ClickableView getnerateErrorView(Context context, int errorRes,String errorMsg) {
        // 如果是自定义错误view的话就不使用errorRes和errorMsg
        ClickableView clickableView = null;
        if (!TextUtils.isEmpty(errorViewClass)){
            try {
                Class errorView = Class.forName(errorViewClass);
                Constructor construct = errorView.getConstructor(Context.class);
                clickableView = (ClickableView) construct.newInstance(context);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } else  {
            clickableView = new DefaultErrorView(context);
            if (errorRes != 0) {
                ((DefaultErrorView) clickableView).setErrorImage(errorRes);
            }

            ((DefaultErrorView) clickableView).setErrorMessage(errorMsg);
        }

        return clickableView;
    }


    /**
     * 生成错误的view
     * @param resId
     * @return
     */
    private View generateErrorView(int resId, String mErrorMsg) {

            View errorView = getnerateErrorView(mContext,resId,mErrorMsg);

            errorView.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
            ));

            return errorView;

    }


    private View generatePrdloadView() {
        //如果默认的loadingview不为空，那么在加载前显示loadingview

        View preloadView = View.inflate(mContext,R.layout.pre_load,null);

        return preloadView;

    }

    /**
     * 生成绑定的view
     *
     * @return
     */
    protected abstract NetRefreshViewInterface generateBindView();

    public void setAdapter(BindNetAdapter adapter) {
        mStateManager.setmAdapter(adapter);
    }

    @Override
    public void nofity(ViewConverter.ContentStates contentState) {
        mViewConverter.convertView(contentState);
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        mStateManager.getmRefreshView().setBindNetOnItemClickListener(onItemClickListener);
    }

    public void clearData() {
        mStateManager.clearData();
    }

    public static class TwoTuples<M,K>{
        M m;
        K k;

        public TwoTuples(M m, K k) {
            this.m = m;
            this.k = k;
        }

        public M getM() {
            return m;
        }

        public K getK() {
            return k;
        }
    }

    public int getPageSize(){
        return mStateManager.getmPageSize();
    }

    public void setPageSize(int pageSize){
        mStateManager.setmPageSize(pageSize);
    }


    public int getCurrntPage(){
        return mStateManager.getCurrentPage();
    }

    public List getTotalList(){
        return mStateManager.getmTotalList();
    }


    public void resetPage(){
        mStateManager.resetPage();
    }


    /***
     * 绑定数据,此方法调用之后必须调用 notifyObserverDataChanged 方法
     * @param itemList
     */
    public void bindList(List itemList){
        mStateManager.bindList(itemList);
    }


    public void setOnRefresshListener(BindRefreshListener refresshListener){
        mStateManager.setOnRefreshListener(refresshListener);
    }


    /***
     * 通知数据改变
     */
    public void notifyObserverDataChanged() {
        notifyObserverDataChanged(BindViewNotifyStatus.ALL_CHANGE,-1);
    }


    /***
     * 通知数据改变,
     * position: 开始位置,
     * count 数量 --  仅为notifyRange时有效
     */
    public void notifyObserverDataChanged(BindViewNotifyStatus status,int position,int count) {
        mStateManager.notifyObserverDataChanged(status,position,count);
    }

    public void notifyObserverDataChanged(BindViewNotifyStatus status,int position){
        notifyObserverDataChanged(status,position,-1);
    }



    /***
     * 通知数据改变,并添加提示信息
     */
    public void notifyObserverDataChanged(String hintMsg) {
        // 暂不支持
        throw new NullPointerException("暂不支持此方法");
    }


    public void setMode(BindNetMode mode){
        mStateManager.setMode(mode);
    }

    /***
     * 给空view和错误view添加添加事件
     * @param onClickListener
     */
    public void setNoContentClick(OnClickListener onClickListener){

        Map<ViewConverter.ContentStates, View> viewMaps = mViewConverter.getmStatusMap();
        ClickableView emptyView = (ClickableView) viewMaps.get(ViewConverter.ContentStates.EMPTY);
        emptyView.setNoContentClickListener(onClickListener);

        ClickableView errorView = (ClickableView) viewMaps.get(ViewConverter.ContentStates.NET_ERROR);
        errorView.setNoContentClickListener(onClickListener);

    }
}
