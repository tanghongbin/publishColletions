package com.android_base.core.views.bindingViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.android_base.R;
import com.android_base.core.interfaces.BindNetAdapter;
import com.android_base.core.interfaces.BindNetMode;
import com.android_base.core.interfaces.BindRefreshListener;
import com.android_base.core.interfaces.NetRefreshViewInterface;
import com.android_base.core.views.ClickableView;
import com.android_base.core.views.DefaultErrorView;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * 绑定网络状态的view，外部接受接口为List，
 * 可根据list的内容来判断是否可以上拉加载更过，刷新，或者当无内容时显示默认视图,暂时支持pullRefreshListView,
 * PullRreshGridView,PullRefreshExpandListView
 * 用法：
 * List strings = new ArrayList();
 * string.add("1");
 * string.add("2");
 * <p>
 * BindingListView listView = new BindingListView(context);
 * BaseAdapter adapter = new BaseAdapter(context,listView.getMList());//这里如果要给adapter传递
 * 一个集合，使用listview.getMList();方法
 * listView.setBindNetAdapter(adapter);
 * listView.bindList(strings);
 * listView.notifyObserverDataChanged();//通知数据已经更新
 */
public abstract class BindingNetView<VIEW extends NetRefreshViewInterface,
        BASEADAPER extends BindNetAdapter> extends FrameLayout {
    private static final String NO_CONTENT_VIEW = "no_content_view";
    private static final String TAG_LOADING = "tag_loading";

    private final String TAG = getClass().getSimpleName();
    private List mList = new ArrayList();
    private List itemList;
    private int pageNo = 1;//初始页开始数量
    private int initPage = pageNo;//用来记住最开设置的启动页数，方便后面重置状态时使用
    private BindRefreshListener bindRefreshListener;
    private OnClickListener noContentClick;
    private String loadingClass;//加载时的view
    private View mLoadingView;
    private String mErrorViewClass;//errorview必须继承自ClickableView
    private ClickableView mErrorView;
    private int mErrorImageRes;
    private String mErrorHintText;
    protected VIEW refreshView;
    protected BindNetAdapter adapter;
    /**
     * 是否启动无内容时的默认图显示，默认为1，启动，2禁止
     */
    protected boolean mEnabledShowDefaultViewWhenNoContent;

    public ClickableView getmErrorView() {
        return mErrorView;
    }


    public void setmErrorView(ClickableView mErrorView) {
        this.mErrorView = mErrorView;
    }

    public String getLoadingClass() {
        return loadingClass;
    }

    public void setLoadingClass(String loadingClass) {
        this.loadingClass = loadingClass;
    }

    public View getmLoadingView() {
        return mLoadingView;
    }

    public void setmLoadingView(View mLoadingView) {
        this.mLoadingView = mLoadingView;
    }

    /**
     * 废弃此方法
     *
     * @param noContentClass
     */
    @Deprecated
    public static void setNoContentClass(Class<? extends ClickableView> noContentClass) {
    }

    public void setOnItemClickListener(final AdapterView.OnItemClickListener onItemClickListener) {
        refreshView.setBindNetOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (onItemClickListener != null) {
                    int count = 0;//条目点击的时候必须去掉头部view
                    if (refreshView.getBindNetRefreshableView() instanceof ListView) {
                        ListView absListView = (ListView) refreshView.getBindNetRefreshableView();
                        count = absListView.getHeaderViewsCount();
                    }
                    onItemClickListener.onItemClick(parent, view, position - count, id);
                }
            }
        });
    }


    public BindingNetView(Context context) {
        super(context);
        init(context, null);
    }


    /**
     * 上下文环境对象
     */
    protected Context mContext;
    /**
     * 指定集合返回的满足的一页的数量
     */
    protected int pageSize = 10;

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
        initPage = pageNo;
    }

    public BindRefreshListener getBindRefreshListener() {
        return bindRefreshListener;
    }

    public void setBindRefreshListener(BindRefreshListener bindRefreshListener) {
        this.bindRefreshListener = bindRefreshListener;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public BindNetAdapter getAdapter() {
        return adapter;
    }

    public BindingNetView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public VIEW getRefreshView() {
        return refreshView;
    }

    //初始化
    protected final void init(Context context, AttributeSet attrs) {
        mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BindingNetView);
        loadingClass = typedArray.getString(R.styleable.BindingNetView_loadingClass);
        mErrorViewClass = typedArray.getString(R.styleable.BindingNetView_errorViewClass);
        mErrorImageRes = typedArray.getResourceId(R.styleable.BindingNetView_errorImage, 0);
        mErrorHintText = typedArray.getString(R.styleable.BindingNetView_errorHint);
        mEnabledShowDefaultViewWhenNoContent = typedArray.getBoolean(R.styleable.BindingNetView_showDefaultContentIfEmpty, true);
        typedArray.recycle();
        generateLoadingView();
        generateErrorView();//生成数据为空或者数据加载错误的图像
        generaRefreshView();
    }

    private void generaRefreshView() {
        refreshView = generateBindView();
        refreshView.setBindNetOnRefreshListener(new BindRefreshListener() {
            @Override
            public void pullDownRefresh() {
                pageNo = initPage;
                if (bindRefreshListener != null) {
                    bindRefreshListener.pullDownRefresh();
                }
            }

            @Override
            public void pullUpToLoadMore() {
                if (bindRefreshListener != null) {
                    bindRefreshListener.pullUpToLoadMore();
                }
            }
        });
        refreshView.setBindNetLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        //如果默认的展示图不为空，那么就将它加入进去,然后需要隐藏listview

        if (getmLoadingView() != null) {
            addView(getmLoadingView());
        } else {
            addView(refreshView.getBindView());
        }
    }

    private void generateErrorView() {
        try {
            if (TextUtils.isEmpty(mErrorViewClass)) {
                mErrorView = getnerateErrorView(mContext);
            } else {
                Class errorViewClass = Class.forName((mErrorViewClass));
                Constructor con = getConstructor(errorViewClass);
                View view = (ClickableView) con.newInstance(mContext);
                if (view instanceof ClickableView == false) {
                    throw new RuntimeException("mErrorView必须继承自ClickableView");
                }
                mErrorView = (ClickableView) view;
            }
            mErrorView.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
            ));
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Constructor getConstructor(Class errorViewClass) {
        try {
            return errorViewClass.getDeclaredConstructor(Context.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }


    private void generateLoadingView() {
        //如果默认的loadingview不为空，那么在加载前显示loadingview

        try {
            Class loadViewClass = Class.forName(getLoadingClass());
            Constructor con = getConstructor(loadViewClass);
            mLoadingView = (View) con.newInstance(mContext);
            mLoadingView.setTag(TAG_LOADING);
            mLoadingView.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
            ));
        } catch (InstantiationException e) {
            Log.d(TAG, "没有默认的加载类");
        } catch (Exception e) {

        }

    }

    /**
     * 生成绑定的view
     *
     * @return
     */
    protected abstract VIEW generateBindView();

    public void setAdapter(BASEADAPER adapter) {
        this.adapter = adapter;
        refreshView.setBindNetAdapter(adapter);
    }

    /**
     * 已经废弃改为在attar文件中写入errorview
     *
     * @param view
     */
    @Deprecated
    public void setNoContentView(View view) {

    }

    /**
     * 通知数据发生改变
     */
    public void notifyObserverDataChanged() {
        notifyObserverDataChanged("");
    }

    public void notifyObserverDataChanged(String errorMsg) {
        if (adapter == null) {
            throw new NullPointerException("adater 不能为空");
        }
        adapter.notifyMetaDataChange();
        bindingListChanged();
        mErrorHintText = errorMsg;
    }

    /**
     * 启动刷新
     */
    public void enabledRefresh() {
        refreshView.setModeBoth();
    }

    public void disabledRefresh() {
        refreshView.setModeDisabled();
    }


    /**
     * 绑定的集合数据发生变化，自动映射到listview
     */
    private void bindingListChanged() {
        refreshView.onBindNetRefreshComplete();

        //总数量集合为空时，填充默认视图并且隐藏refreshview
        //每次通知前，如果发现内部有loadingview，那么需要先把它去掉
        if (findViewWithTag(TAG_LOADING) != null) {
            removeView(mLoadingView);
        }
        if ((mList == null || mList.size() == 0) &&
                mEnabledShowDefaultViewWhenNoContent) {
            if (findViewWithTag(NO_CONTENT_VIEW) == null) {//如果容器中没有errorview，创建一个新的并加入进去
                if (mErrorView == null) {
                    mErrorView = getnerateErrorView(mContext);
                    mErrorView.setTag(NO_CONTENT_VIEW);
                }
            } else {
                if (mErrorView.getParent() != null) {
                    ViewGroup group = (ViewGroup) mErrorView.getParent();
                    group.removeView(mErrorView);
                }
            }
            removeView(refreshView.getBindView());
            addErrorView(this);
        }
        //如果有数据，并且布局里面的视图有noContentView，那么清空所有view，只显示listview
        if (mList.size() > 0) {
            removeAllViews();
            addView(refreshView.getBindView());
        }

        //每次请求的集合数量
        if (itemList == null || itemList.size() == 0) {
            setInternalMode(BindNetMode.PULL_FROM_UP);
            return;
        }

        if (itemList.size() < pageSize) {
            setInternalMode(BindNetMode.PULL_FROM_UP);
        }

        if (itemList.size() >= pageSize) {
            setInternalMode(BindNetMode.BOTH);
            pageNo++;
        }

    }

    protected void addErrorView(FrameLayout viewBindingNetView) {
        if (mErrorView.getParent() != null) {
            ViewGroup viewGroup = (ViewGroup) mErrorView.getParent();
            viewGroup.removeView(mErrorView);
        }
        addView(mErrorView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    /**
     * 重置view的显示状态，显示refreshview，隐藏noContentView,并且将刷新模式设置为BOTH
     */

    protected void resetViewStatus() {
        removeAllViews();
        refreshView.setModeBoth();
        addView(refreshView.getBindView());
    }

    /**
     * 当默认显示内容为空时，填充默认视图
     */
    protected ClickableView getnerateErrorView(Context context) {
        DefaultErrorView defaultErrorView = new DefaultErrorView(context);
        if (mErrorImageRes != 0) {
            defaultErrorView.setErrorImage(mErrorImageRes);
        }
        return defaultErrorView;
    }

    //外界只需要调用此方法维护集合
    public void bindList(List itemList) {
        this.itemList = itemList;
        if (pageNo == initPage) {
            mList.clear();
        }
        if (itemList != null && itemList.size() > 0) {
            mList.addAll(itemList);
        }
    }

    public void setMode(BindNetMode mode) {
        switch (mode) {
            case BOTH:
                refreshView.setModeBoth();
                break;
            case PULL_FROM_UP:
                refreshView.setModePullFromUp();
                break;
            case PULL_FROM_DOWN:
                refreshView.setModePullFromDown();
                break;
            case DISABLED:
                refreshView.setModeDisabled();
                break;
        }
    }

    /**
     * 如果初始化为禁用刷新，那么在数据状态改变下则不会改变刷新模式
     *
     * @param mode
     */
    private void setInternalMode(BindNetMode mode) {
        if (refreshView.getBindNetMode() == BindNetMode.DISABLED) {
            return;
        }
        setMode(mode);
    }

    public List getmList() {
        return mList;
    }

    /**
     * 将pageNo重置为
     *
     * @return
     */
    public int resetPage() {
        pageNo = initPage;
        return pageNo;
    }


    public void onRefreshComplete() {
        refreshView.onBindNetRefreshComplete();
    }

    public void setNoContentClick(OnClickListener contentClick) {
        this.noContentClick = contentClick;
        if (mErrorView != null) {
            mErrorView.setNoContentClickListener(contentClick);
        }
    }


}