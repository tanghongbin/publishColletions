package com.android_base.core.common.adapter;

import android.content.Context;
import android.view.View;
import android.widget.GridView;

import java.util.List;

/**
 * 固定行数和数量显示的adapter,可用于listview和gridview,调用方法setRows,setMaxCount
 */
public abstract class FixedCountAdapter<OBJ> extends CustomBaseAdapter<OBJ> {

    protected List<OBJ> mList;
    protected GridView mGridView;
    protected Context mContext;
    /**
     * gridview的宽度,记录用来计算每一行显示的数量
     */
    protected int mGridOldWidth;
    /**
     * 每行的数量,不能手动进行更改，设置方式有2种：1.设置gridview的numColumns，
     * 2.设置gridview的模式为strenchMode=columnWidth,然后通过计算获得
     */
    private int mCountEachRow = 0;

    /**
     *  共多少行数,如果为0，则不限制
     */
    private int rows;



    /**
     * 最多显示的数量
     */
    private int maxCount;
    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
        notifyDataSetChanged();
    }

    public int getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
        notifyDataSetChanged();
    }

    public int getCountEachRow() {
        return mCountEachRow;
    }

    /**
     * 如果是作用于listview就不用传
     * @param mContext
     * @param list
     * @param gridView
     */
    public FixedCountAdapter(Context mContext,List<OBJ> list ,GridView gridView) {
        super(mContext,list);
        this.mContext = mContext;
        this.mGridView = gridView;
        this.mList = list;
        settingGridView();
    }

    /**
     * 添加布局绘制完成后的监听，来计算每一行显示的数量
     */
    private void settingGridView() {
        if (mGridView == null) {
            return;
        }
        mGridView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {

                //如果数量有设置，那么直接使用,否则通过计算获取
                if (mGridView.getNumColumns() == GridView.AUTO_FIT){
                    if (mGridOldWidth != right-left){
                        computeCountEachRow(left, right);
                        mGridOldWidth = right-left;
                        notifyDataSetChanged();
                    }
                }else {
                    mCountEachRow = mGridView.getNumColumns();
                }
            }
        });
    }

    /**
     * 计算strenchMode==ColumnWidth && numColunms==auto_fit模式下的每行显示的数量
     * @param left
     * @param right
     */
    private void computeCountEachRow(int left, int right) {
        int gridWidth = right-left-mGridView.getPaddingLeft()-mGridView.getPaddingRight();
        int itemWidth = mGridView.getColumnWidth()+mGridView.getHorizontalSpacing();
        mCountEachRow =(gridWidth+mGridView.getHorizontalSpacing())/itemWidth;
    }

    /**
     * 获取集合中显示对象的数量
     * @return
     */
    protected  int getListSize(){
        return mList == null ? 0 : mList.size();
    }

    @Override
    public final int getCount() {
        //如果行数不为0，则最多只显示多少行
        if (rows != 0){
            int limitSize = rows * mCountEachRow;
            int minSize = Math.min(Math.min(limitSize,getListSize()),maxCount);
            //如果最小的值是maxcount，那么则返回，要排除maxcount==1
            if (maxCount != 0 && minSize == maxCount){
                return maxCount;
            }
            //限制最多行数，则最多只能显示row行
            if (limitSize > getListSize()){
                return getListSize();
            }else {
                return limitSize;
            }
        }
        return getListSize();
    }

}