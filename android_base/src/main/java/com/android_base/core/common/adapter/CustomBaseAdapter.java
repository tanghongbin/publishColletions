package com.android_base.core.common.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.android_base.core.interfaces.BindNetAdapter;

import java.util.List;

/**
 * Created by tanghongbin on 2017/2/23.
 */

public abstract class CustomBaseAdapter<T> extends BaseAdapter implements BindNetAdapter{
    protected Context mContext;
    protected List<T> mList;

    public CustomBaseAdapter(Context mContext, List<T> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }


    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void notifyMetaDataChange() {
        notifyDataSetChanged();
    }
}
